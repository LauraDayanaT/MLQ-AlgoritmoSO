package scheduling;

import models.Proceso;
import java.util.*;

public class MLQScheduler {
    private List<Cola> colas;
    private List<Proceso> todos;
    private int tiempoActual;
    private int indiceLlegadas;

    public MLQScheduler(List<Proceso> lista) {
        this.todos = lista;
        this.tiempoActual = 0;
        this.indiceLlegadas = 0;
        this.colas = new ArrayList<>();

        // Creamos las 3 colas según el esquema elegido (RR(1), RR(3), SJF)
        colas.add(new Cola(1, "RR", 1));
        colas.add(new Cola(2, "RR", 3));
        colas.add(new Cola(3, "SJF", 0));

        // Ordenamos la lista por arrivalTime y prioridad
        todos.sort(Comparator.comparingInt(Proceso::getArrivalTime)
                .thenComparing((Proceso p) -> -p.getPriority()));
    }

    public List<Proceso> ejecutar() {
        while (hayProcesosPendientes()) {
            agregarLlegados();
            Cola cola = seleccionarCola();
            if (cola == null) {
                avanzarTiempo();
                continue;
            }

            if (cola.getTipo().equals("RR")) ejecutarRR(cola);
            else ejecutarSJF(cola);
        }
        return todos;
    }

    private boolean hayProcesosPendientes() {
        return indiceLlegadas < todos.size() || colas.stream().anyMatch(c -> !c.estaVacia());
    }

    private void agregarLlegados() {
        while (indiceLlegadas < todos.size() && todos.get(indiceLlegadas).getArrivalTime() <= tiempoActual) {
            Proceso p = todos.get(indiceLlegadas++);
            colas.get(p.getQueue() - 1).agregarProceso(p);
        }
    }

    private Cola seleccionarCola() {
        for (Cola c : colas) if (!c.estaVacia()) return c;
        return null;
    }

    private void avanzarTiempo() {
        if (indiceLlegadas < todos.size())
            tiempoActual = todos.get(indiceLlegadas).getArrivalTime();
    }

    private void ejecutarRR(Cola cola) {
        Proceso p = cola.siguienteProceso();
        if (p == null) return;
        if (p.getFirstResponse() == null) p.setFirstResponse(tiempoActual);

        int quantum = cola.getQuantum();
        int tiempoEjec = Math.min(quantum, p.getRemainingTime());

        for (int i = 0; i < tiempoEjec; i++) {
            tiempoActual++;
            p.setRemainingTime(p.getRemainingTime() - 1);
            agregarLlegados();
            if (!colas.get(0).estaVacia() && cola.getNivel() > 1) break;
        }

        if (p.getRemainingTime() > 0) cola.agregarProceso(p);
        else p.setCompletionTime(tiempoActual);
    }

    private void ejecutarSJF(Cola cola) {
        Proceso p = cola.siguienteProceso();
        if (p == null) return;
        if (p.getFirstResponse() == null) p.setFirstResponse(tiempoActual);

        while (p.getRemainingTime() > 0) {
            tiempoActual++;
            p.setRemainingTime(p.getRemainingTime() - 1);
            agregarLlegados();
        }
        p.setCompletionTime(tiempoActual);
        cola.eliminarProceso(p);
    }
}
