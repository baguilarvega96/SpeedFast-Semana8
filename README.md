# SpeedFast - Semana 8

Proyecto desarrollado para la asignatura Desarrollo Orientado a Objetos II.

## Descripción

SpeedFast es una aplicación de escritorio desarrollada en Java que permite gestionar pedidos, repartidores y entregas utilizando una interfaz gráfica Swing y una base de datos MySQL conectada mediante JDBC.

## Funcionalidades

### Gestión de Pedidos

- Registrar pedidos.
- Listar pedidos.
- Editar pedidos.
- Eliminar pedidos.
- Manejar estados:
    - PENDIENTE
    - EN_REPARTO
    - ENTREGADO

### Gestión de Repartidores

- Registrar repartidores.
- Listar repartidores.
- Editar repartidores.
- Eliminar repartidores.

### Gestión de Entregas

- Registrar entregas.
- Asociar un pedido con un repartidor.
- Listar entregas.
- Editar entregas.
- Eliminar entregas.

## Tecnologías utilizadas

- Java
- Java Swing
- MySQL
- JDBC
- IntelliJ IDEA
- Git
- GitHub

## Estructura del proyecto

```text
cl.speedfast
├── dao
│   ├── ConexionDB
│   ├── PedidoDAO
│   ├── RepartidorDAO
│   └── EntregaDAO
├── interfaces
├── main
│   └── Main
├── modelo
│   ├── EstadoPedido
│   ├── Pedido
│   ├── PedidoComida
│   ├── PedidoEncomienda
│   ├── PedidoExpress
│   ├── Repartidor
│   └── ZonaDeCarga
└── vista
    ├── DatosCompartidos
    ├── VentanaPrincipal
    ├── VentanaRegistroPedido
    ├── VentanaListaPedidos
    ├── VentanaGestionRepartidores
    ├── VentanaGestionEntregas
    └── VentanaAsignarRepartidor