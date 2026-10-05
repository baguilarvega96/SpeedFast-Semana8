package cl.speedfast.modelo;

public class PedidoExpress extends Pedido {

    private boolean disponibilidadInmediata = true;

    public PedidoExpress(String idPedido, String direccionEntrega, double distanciaKm) {
        super(idPedido, direccionEntrega, "Express", distanciaKm);
    }

    @Override
    public void asignarRepartidor() {
        repartidorAsignado = "Camila Soto";

        System.out.println("[Pedido Express]");
        System.out.println("Asignando repartidor...");
        System.out.println("→ Buscando repartidor más cercano...");
        System.out.println("→ Disponibilidad inmediata: "
                + (disponibilidadInmediata ? "OK" : "No disponible"));
        System.out.println("→ Pedido asignado a " + repartidorAsignado);

        registrarEvento("Repartidor asignado automáticamente: " + repartidorAsignado);
    }

    @Override
    public void asignarRepartidor(String nombreRepartidor) {
        repartidorAsignado = nombreRepartidor;

        System.out.println("→ Verificando disponibilidad inmediata... OK");
        System.out.println("→ Pedido asignado a " + nombreRepartidor);

        registrarEvento("Repartidor asignado manualmente: " + nombreRepartidor);
    }

    @Override
    public int calcularTiempoEntrega() {
        if (getDistanciaKm() > 5) {
            return 15;
        }

        return 10;
    }
}