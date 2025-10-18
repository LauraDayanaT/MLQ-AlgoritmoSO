package utils;

import models.Proceso;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Archivo {

    public static List<Proceso> leerProcesos(String ruta) throws IOException {
        List<Proceso> lista = new ArrayList<>();
        List<String> lineas = Files.readAllLines(Paths.get(ruta));
        for (String l : lineas) {
            if (l.startsWith("#") || l.isBlank()) continue;
            String[] p = l.split(";");
            lista.add(new Proceso(
                    p[0].trim(),
                    Integer.parseInt(p[1].trim()),
                    Integer.parseInt(p[2].trim()),
                    Integer.parseInt(p[3].trim()),
                    Integer.parseInt(p[4].trim())
            ));
        }
        return lista;
    }

    public static void escribirSalida(String ruta, List<Proceso> lista) throws IOException {
        Path in = Paths.get(ruta);
        String nombre = in.getFileName().toString().replace(".txt", "_out.txt");
        try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(nombre))) {
            bw.write("# etiqueta; BT; AT; Q; Pr; WT; CT; RT; TAT\n");

            double sumWT = 0, sumCT = 0, sumRT = 0, sumTAT = 0;
            for (Proceso p : lista) {
                int CT = p.getCompletionTime();
                int RT = p.getFirstResponse() - p.getArrivalTime();
                int TAT = CT - p.getArrivalTime();
                int WT = TAT - p.getBurstTime();
                sumWT += WT; sumCT += CT; sumRT += RT; sumTAT += TAT;

                bw.write(String.format("%s;%d;%d;%d;%d;%d;%d;%d;%d\n",
                        p.getId(), p.getBurstTime(), p.getArrivalTime(), p.getQueue(), p.getPriority(),
                        WT, CT, RT, TAT));
            }
            int n = lista.size();
            bw.write(String.format("\nWT=%.2f; CT=%.2f; RT=%.2f; TAT=%.2f;\n",
                    sumWT/n, sumCT/n, sumRT/n, sumTAT/n));
        }
    }
}
