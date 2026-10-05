package cl.speedfast.vista;

import cl.speedfast.dao.EntregaDAO;
import cl.speedfast.dao.RepartidorDAO;
import cl.speedfast.modelo.EstadoPedido;
import cl.speedfast.modelo.Pedido;
import cl.speedfast.modelo.Repartidor;
import cl.speedfast.modelo.ZonaDeCarga;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class VentanaAsignarRepartidor extends JFrame {

    public VentanaAsignarRepartidor() {

        setTitle("Asignar Repartidor / Iniciar Entrega");
        setSize(600, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 10, 10));

        JLabel lblPedido = new JLabel("Pedido:");
        JComboBox<Pedido> cmbPedidos = new JComboBox<>();

        for (Pedido pedido : DatosCompartidos.pedidos) {
            cmbPedidos.addItem(pedido);
        }

        cmbPedidos.setRenderer(new DefaultListCellRenderer() {

            @Override
            public Component getListCellRendererComponent(
                    JList<?> list,
                    Object value,
                    int index,
                    boolean isSelected,
                    boolean cellHasFocus) {

                super.getListCellRendererComponent(
                        list,
                        value,
                        index,
                        isSelected,
                        cellHasFocus
                );

                if (value instanceof Pedido) {

                    Pedido pedido = (Pedido) value;

                    setText(
                            "Pedido " + pedido.getId()
                                    + " - " + pedido.getDireccionEntrega()
                                    + " - " + pedido.getTipoPedido()
                    );
                }

                return this;
            }
        });

        JLabel lblRepartidor = new JLabel("Repartidor:");
        JTextField txtRepartidor = new JTextField();

        JButton btnAsignar =
                new JButton("Asignar e iniciar entrega");

        btnAsignar.addActionListener(e -> {

            Pedido pedidoSeleccionado =
                    (Pedido) cmbPedidos.getSelectedItem();

            String nombreRepartidor =
                    txtRepartidor.getText().trim();

            if (pedidoSeleccionado == null ||
                    nombreRepartidor.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Debe seleccionar un pedido e ingresar un repartidor"
                );

                return;
            }

            pedidoSeleccionado.asignarRepartidor(
                    nombreRepartidor
            );

            pedidoSeleccionado.setEstado(
                    EstadoPedido.EN_REPARTO
            );

            ZonaDeCarga zonaDeCarga =
                    new ZonaDeCarga();

            zonaDeCarga.agregarPedido(
                    pedidoSeleccionado
            );

            Repartidor repartidor =
                    new Repartidor(
                            nombreRepartidor,
                            zonaDeCarga
                    );

            RepartidorDAO repartidorDAO =
                    new RepartidorDAO();

            repartidorDAO.guardar(repartidor);

            int idPedidoBD =
                    pedidoSeleccionado.getId();

            int idRepartidorBD =
                    repartidorDAO.obtenerUltimoId();

            EntregaDAO entregaDAO =
                    new EntregaDAO();

            entregaDAO.guardar(
                    idPedidoBD,
                    idRepartidorBD,
                    LocalDate.now(),
                    LocalTime.now()
            );

            Thread hiloRepartidor =
                    new Thread(repartidor);

            hiloRepartidor.start();

            JOptionPane.showMessageDialog(
                    this,
                    "Entrega iniciada correctamente"
            );

            txtRepartidor.setText("");
        });

        panel.add(lblPedido);
        panel.add(cmbPedidos);

        panel.add(lblRepartidor);
        panel.add(txtRepartidor);

        panel.add(new JLabel(""));
        panel.add(btnAsignar);

        add(panel);

        setVisible(true);
    }
}