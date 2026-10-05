package cl.speedfast.modelo;

public class PedidoEncomienda extends Pedido {

    private double pesoKg = 3.5;

    public PedidoEncomienda(String idPedido, String direccionEntrega, double distanciaKm) {
        super(idPedido, direccionEntrega, "Encomienda", distanciaKm);
    }

    @Override
    public void asignarRepartidor() {
        repartidorAsignado = "Daniela Tapia";

        System.out.println("[Pedido Encomienda]");
        System.out.println("Asignando repartidor...");
        System.out.println("→ Validando peso y embalaje... OK");
        System.out.println("→ Peso registrado: " + pesoKg + " kg");
        System.out.println("→ Pedido asignado a " + repartidorAsignado);

        registrarEvento("Repartidor asignado automáticamente: " + repartidorAsignado);
    }

    @Override
    public void asignarRepartidor(String nombreRepartidor) {
        repartidorAsignado = nombreRepartidor;

        System.out.println("→ Validando peso y embalaje... OK");
        System.out.println("→ Pedido asignado a " + nombreRepartidor);

        registrarEvento("Repartidor asignado manualmente: " + nombreRepartidor);
    }

    @Override
    public int calcularTiempoEntrega() {
        return (int) (20 + (1.5 * getDistanciaKm()));
    }
}