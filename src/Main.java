import models.Proceso;
import scheduling.MLQScheduler;
import utils.Archivo;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        String archivo = args.length > 0 ? args[0] : "mlq007.txt";
        try {
            List<Proceso> lista = Archivo.leerProcesos(archivo);
            MLQScheduler scheduler = new MLQScheduler(lista);
            List<Proceso> resultado = scheduler.ejecutar();
            Archivo.escribirSalida(archivo, resultado);
            System.out.println("Simulación MLQ completada. Revisa el archivo de salida.");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
