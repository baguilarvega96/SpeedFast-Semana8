package cl.speedfast.modelo;

import java.util.concurrent.ThreadLocalRandom;

public class Repartidor implements Runnable {

    private final String nombre;
    private final ZonaDeCarga zonaDeCarga;

    public Repartidor(String nombre, ZonaDeCarga zonaDeCarga) {
        this.nombre = nombre;
        this.zonaDeCarga = zonaDeCarga;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public void run() {

        while (true) {

            Pedido pedido = zonaDeCarga.retirarPedido();

            // Si no quedan pedidos, el repartidor termina su trabajo.
            if (pedido == null) {
                System.out.println(
                        "[Repartidor - " + nombre + "] No quedan pedidos por entregar."
                );
                break;
            }

            // Asigna el repartidor al pedido.
            pedido.asignarRepartidor(nombre);

            System.out.println(
                    "[Repartidor - " + nombre + "] Retirando pedido #" +
                            pedido.getId() + "..."
            );

            System.out.println(
                    "[Repartidor - " + nombre + "] Estado: " +
                            pedido.getEstado()
            );

            System.out.println(
                    "[Repartidor - " + nombre + "] Entregando pedido #" +
                            pedido.getId() + " en " +
                            pedido.getDireccionEntrega() + "..."
            );

            try {

                // Simula una entrega entre 1 y 3 segundos.
                int tiempoEntrega =
                        ThreadLocalRandom.current().nextInt(1000, 3001);

                Thread.sleep(tiempoEntrega);

            } catch (InterruptedException e) {

                Thread.currentThread().interrupt();

                System.out.println(
                        "[Repartidor - " + nombre +
                                "] La entrega fue interrumpida."
                );

                break;
            }

            // El pedido queda marcado como entregado.
            pedido.setEstado(EstadoPedido.ENTREGADO);

            System.out.println(
                    "[Repartidor - " + nombre + "] Pedido #" +
                            pedido.getId() + " entregado."
            );

            System.out.println(
                    "[Repartidor - " + nombre + "] Estado: " +
                            pedido.getEstado()
            );

            System.out.println("----------------------------------------");
        }
    }
}