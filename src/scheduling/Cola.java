package scheduling;

import models.Proceso;
import java.util.*;

public class Cola {
    private int nivel;
    private String tipo; // "RR" o "SJF"
    private int quantum;
    private Queue<Proceso> procesos;

    public Cola(int nivel, String tipo, int quantum) {
        this.nivel = nivel;
        this.tipo = tipo;
        this.quantum = quantum;
        this.procesos = new LinkedList<>();
    }

    public int getNivel() { return nivel; }
    public String getTipo() { return tipo; }
    public int getQuantum() { return quantum; }
    public Queue<Proceso> getProcesos() { return procesos; }

    public void agregarProceso(Proceso p) {
        procesos.add(p);
    }

    public boolean estaVacia() {
        return procesos.isEmpty();
    }

    public Proceso siguienteProceso() {
        if (tipo.equals("SJF")) {
            return procesos.stream()
                    .min(Comparator.comparingInt(Proceso::getRemainingTime))
                    .orElse(null);
        } else {
            return procesos.poll();
        }
    }

    public void eliminarProceso(Proceso p) {
        procesos.remove(p);
    }
}
