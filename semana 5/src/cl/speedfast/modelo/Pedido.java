package cl.speedfast.modelo;

import cl.speedfast.interfaces.Cancelable;
import cl.speedfast.interfaces.Despachable;
import cl.speedfast.interfaces.Rastreable;

import java.util.ArrayList;
import java.util.List;

// Clase base que representa un pedido de SpeedFast.
// Mantiene funcionalidades de semanas anteriores e incorpora
// el manejo de estados requerido para la Semana 5.
public class Pedido implements Despachable, Cancelable, Rastreable {

    // Atributos principales solicitados en Semana 5
    private int id;
    private String direccionEntrega;
    private EstadoPedido estado;

    // Atributos utilizados en semanas anteriores
    private String tipoPedido;
    private double distanciaKm;

    protected String repartidorAsignado = "Sin asignar";

    private boolean despachado = false;
    private boolean cancelado = false;
    private boolean reservado = false;

    private final List<String> historial = new ArrayList<>();

    // Constructor principal de Semana 5
    public Pedido(int id, String direccionEntrega) {
        this.id = id;
        this.direccionEntrega = direccionEntrega;
        this.estado = EstadoPedido.PENDIENTE;
        this.tipoPedido = "General";
        this.distanciaKm = 0;

        historial.add("Pedido #" + id + " creado con estado PENDIENTE.");
    }

    // Constructor compatible con las clases creadas en semanas anteriores
    public Pedido(String idPedido,
                  String direccionEntrega,
                  String tipoPedido,
                  double distanciaKm) {

        this.id = convertirId(idPedido);
        this.direccionEntrega = direccionEntrega;
        this.tipoPedido = tipoPedido;
        this.distanciaKm = distanciaKm;
        this.estado = EstadoPedido.PENDIENTE;

        historial.add(
                "Pedido " + tipoPedido + " #" + id +
                        " creado con estado PENDIENTE."
        );
    }

    // Convierte el identificador antiguo String en un entero.
    // Si contiene letras, utiliza los números encontrados.
    private static int convertirId(String idPedido) {

        if (idPedido == null || idPedido.isBlank()) {
            return 0;
        }

        String numeros = idPedido.replaceAll("\\D", "");

        if (!numeros.isEmpty()) {
            return Integer.parseInt(numeros);
        }

        return Math.abs(idPedido.hashCode());
    }

    // =========================
    // GETTERS
    // =========================

    public int getId() {
        return id;
    }

    // Se mantiene por compatibilidad con Semana 4
    public String getIdPedido() {
        return String.valueOf(id);
    }

    public String getDireccionEntrega() {
        return direccionEntrega;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public String getTipoPedido() {
        return tipoPedido;
    }

    public double getDistanciaKm() {
        return distanciaKm;
    }

    public String getRepartidorAsignado() {
        return repartidorAsignado;
    }

    // =========================
    // SETTERS
    // =========================

    public void setId(int id) {
        this.id = id;
    }

    public void setDireccionEntrega(String direccionEntrega) {
        this.direccionEntrega = direccionEntrega;
    }

    public void setTipoPedido(String tipoPedido) {
        this.tipoPedido = tipoPedido;
    }

    public void setDistanciaKm(double distanciaKm) {
        this.distanciaKm = distanciaKm;
    }

    // Setter utilizando directamente el enum
    public void setEstado(EstadoPedido nuevoEstado) {
        this.estado = nuevoEstado;
        registrarEvento("Estado actualizado a: " + nuevoEstado);
    }

    // Método solicitado expresamente por la pauta
    public void setEstado(String nuevoEstado) {

        try {
            this.estado = EstadoPedido.valueOf(
                    nuevoEstado.trim().toUpperCase()
            );

            registrarEvento("Estado actualizado a: " + estado);

        } catch (IllegalArgumentException e) {

            System.out.println(
                    "Estado inválido para el pedido #" + id +
                            ": " + nuevoEstado
            );
        }
    }

    // =========================
    // MÉTODOS DEL PEDIDO
    // =========================

    public void mostrarResumen() {

        System.out.println("Pedido " + tipoPedido + " #" + id);
        System.out.println("Dirección: " + direccionEntrega);
        System.out.println("Distancia: " + distanciaKm + " km");
        System.out.println("Estado: " + estado);
        System.out.println("Repartidor asignado: " + repartidorAsignado);
    }

    public void asignarRepartidor() {
        System.out.println("Asignando repartidor al pedido #" + id + "...");
    }

    public void asignarRepartidor(String nombreRepartidor) {

        repartidorAsignado = nombreRepartidor;

        registrarEvento(
                "Repartidor asignado: " + nombreRepartidor
        );

        System.out.println(
                "Pedido #" + id +
                        " asignado a: " + nombreRepartidor
        );
    }

    protected void registrarEvento(String evento) {
        historial.add(evento);
    }

    // Registra la reserva del pedido
    public void reservar() {

        reservado = true;

        registrarEvento("Pedido reservado.");

        System.out.println(
                "Pedido #" + id + " reservado correctamente."
        );
    }

    // Despacha el pedido si no se encuentra cancelado
    @Override
    public void despachar() {

        if (cancelado) {

            System.out.println(
                    "No se puede despachar un pedido cancelado."
            );

            return;
        }

        despachado = true;
        estado = EstadoPedido.EN_REPARTO;

        registrarEvento(
                "Pedido despachado por " + repartidorAsignado
        );

        registrarEvento(
                "Estado actualizado a EN_REPARTO."
        );

        System.out.println(
                "Pedido #" + id +
                        " despachado correctamente."
        );
    }

    // Cancela el pedido siempre que todavía no haya sido despachado
    @Override
    public void cancelar() {

        if (despachado) {

            System.out.println(
                    "No se puede cancelar un pedido ya despachado."
            );

            return;
        }

        cancelado = true;

        registrarEvento("Pedido cancelado.");

        System.out.println(
                "Pedido #" + id +
                        " cancelado exitosamente."
        );
    }

    @Override
    public List<String> verHistorial() {
        return new ArrayList<>(historial);
    }

    // Implementación base para mantener compatibilidad
    // con las clases de pedidos creadas anteriormente.
    public int calcularTiempoEntrega() {
        return (int) Math.ceil(distanciaKm * 2);
    }

    // =========================
    // TOSTRING
    // =========================

    @Override
    public String toString() {

        return "Pedido{" +
                "id=" + id +
                ", direccionEntrega='" + direccionEntrega + '\'' +
                ", estado=" + estado +
                '}';
    }
}