package cl.speedfast;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {

        System.out.println("=== SPEEDFAST - SIMULACIÓN DE ENTREGAS ===");
        System.out.println();

        // Pedidos asignados a Camila
        List<Pedido> pedidosCamila = Arrays.asList(
                new PedidoComida("101", "Av. Italia 456", 4),
                new PedidoExpress("102", "Av. Providencia 850", 6)
        );

        // Pedidos asignados a Luis
        List<Pedido> pedidosLuis = Arrays.asList(
                new PedidoEncomienda("103", "Av. Independencia 123", 5),
                new PedidoComida("104", "Av. Matta 900", 3)
        );

        // Pedidos asignados a Diego
        List<Pedido> pedidosDiego = Arrays.asList(
                new PedidoExpress("105", "Av. Apoquindo 1500", 7),
                new PedidoEncomienda("106", "Av. Grecia 720", 8)
        );

        // Creación de los repartidores
        Repartidor camila = new Repartidor("Camila", pedidosCamila);
        Repartidor luis = new Repartidor("Luis", pedidosLuis);
        Repartidor diego = new Repartidor("Diego", pedidosDiego);

        // ExecutorService con tres hilos
        ExecutorService executor = Executors.newFixedThreadPool(3);

        executor.execute(camila);
        executor.execute(luis);
        executor.execute(diego);

        // Indica que no se recibirán nuevas tareas
        executor.shutdown();

        try {

            // Espera a que todos los repartidores terminen
            if (!executor.awaitTermination(1, TimeUnit.MINUTES)) {
                executor.shutdownNow();
            }

        } catch (InterruptedException e) {

            System.out.println("La ejecución principal fue interrumpida.");

            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }

        System.out.println();
        System.out.println("=== TODAS LAS ENTREGAS HAN FINALIZADO ===");
    }
}