package cl.speedfast.modelo;

public class PedidoComida extends Pedido {

    private boolean requiereMochilaTermica = true;

    public PedidoComida(String idPedido, String direccionEntrega, double distanciaKm) {
        super(idPedido, direccionEntrega, "Comida", distanciaKm);
    }

    @Override
    public void asignarRepartidor() {
        repartidorAsignado = "Luis Díaz";

        System.out.println("[Pedido Comida]");
        System.out.println("Asignando repartidor...");
        System.out.println("→ Verificando mochila térmica... "
                + (requiereMochilaTermica ? "OK" : "No requerida"));
        System.out.println("→ Pedido asignado a " + repartidorAsignado);

        registrarEvento("Repartidor asignado automáticamente: " + repartidorAsignado);
    }

    @Override
    public void asignarRepartidor(String nombreRepartidor) {
        repartidorAsignado = nombreRepartidor;

        System.out.println("→ Verificando mochila térmica... OK");
        System.out.println("→ Pedido asignado a " + nombreRepartidor);

        registrarEvento("Repartidor asignado manualmente: " + nombreRepartidor);
    }

    @Override
    public int calcularTiempoEntrega() {
        return (int) (15 + (2 * getDistanciaKm()));
    }
}