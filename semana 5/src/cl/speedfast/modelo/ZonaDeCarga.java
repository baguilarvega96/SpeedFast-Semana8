package cl.speedfast.modelo;

import java.util.LinkedList;
import java.util.Queue;

public class ZonaDeCarga {

    private final Queue<Pedido> pedidos;

    public ZonaDeCarga() {
        pedidos = new LinkedList<>();
        System.out.println("[Zona de carga inicializada]");
    }

    // Método sincronizado para agregar pedidos de forma segura.
    public synchronized void agregarPedido(Pedido pedido) {

        pedidos.offer(pedido);

        System.out.println(
                "Pedido #" + pedido.getId() +
                        " agregado. Destino: " +
                        pedido.getDireccionEntrega()
        );
    }

    // Método sincronizado para evitar que dos repartidores
    // retiren el mismo pedido al mismo tiempo.
    public synchronized Pedido retirarPedido() {

        if (pedidos.isEmpty()) {
            return null;
        }

        Pedido pedido = pedidos.poll();

        // Al ser retirado de la zona de carga,
        // pasa inmediatamente a EN_REPARTO.
        pedido.setEstado(EstadoPedido.EN_REPARTO);

        return pedido;
    }

    // Permite comprobar si todavía existen pedidos pendientes.
    public synchronized boolean hayPedidos() {
        return !pedidos.isEmpty();
    }

    // Cantidad actual de pedidos en la zona de carga.
    public synchronized int cantidadPedidos() {
        return pedidos.size();
    }
}