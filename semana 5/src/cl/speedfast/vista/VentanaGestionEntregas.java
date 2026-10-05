package cl.speedfast.vista;

import cl.speedfast.dao.EntregaDAO;
import cl.speedfast.dao.PedidoDAO;
import cl.speedfast.dao.RepartidorDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class VentanaGestionEntregas extends JFrame {

    private JTable tablaEntregas;
    private DefaultTableModel modeloTabla;

    private JComboBox<String> cmbPedido;
    private JComboBox<String> cmbRepartidor;

    public VentanaGestionEntregas() {

        setTitle("Gestión de Entregas");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout());

        JPanel panelSuperior = new JPanel(
                new GridLayout(3, 2, 10, 10)
        );

        JLabel lblPedido = new JLabel("Pedido:");
        cmbPedido = new JComboBox<>();

        JLabel lblRepartidor = new JLabel("Repartidor:");
        cmbRepartidor = new JComboBox<>();

        JButton btnRegistrar =
                new JButton("Registrar entrega");

        panelSuperior.add(lblPedido);
        panelSuperior.add(cmbPedido);

        panelSuperior.add(lblRepartidor);
        panelSuperior.add(cmbRepartidor);

        panelSuperior.add(new JLabel(""));
        panelSuperior.add(btnRegistrar);

        modeloTabla = new DefaultTableModel(
                new Object[]{
                        "ID",
                        "ID Pedido",
                        "Dirección",
                        "ID Repartidor",
                        "Repartidor",
                        "Fecha",
                        "Hora"
                },
                0
        );

        tablaEntregas = new JTable(modeloTabla);

        JScrollPane scrollPane =
                new JScrollPane(tablaEntregas);

        JButton btnEditar =
                new JButton("Editar");

        JButton btnEliminar =
                new JButton("Eliminar");

        JButton btnRefrescar =
                new JButton("Refrescar");

        JPanel panelBotones =
                new JPanel();

        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnRefrescar);

        btnRegistrar.addActionListener(e -> {

            if (cmbPedido.getSelectedItem() == null ||
                    cmbRepartidor.getSelectedItem() == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Debe seleccionar pedido y repartidor"
                );

                return;
            }

            int idPedido =
                    obtenerId(
                            cmbPedido.getSelectedItem().toString()
                    );

            int idRepartidor =
                    obtenerId(
                            cmbRepartidor.getSelectedItem().toString()
                    );

            EntregaDAO entregaDAO =
                    new EntregaDAO();

            entregaDAO.create(
                    idPedido,
                    idRepartidor,
                    LocalDate.now(),
                    LocalTime.now()
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Entrega registrada correctamente"
            );

            cargarEntregas();
        });

        btnEditar.addActionListener(e -> {

            int fila =
                    tablaEntregas.getSelectedRow();

            if (fila == -1) {

                JOptionPane.showMessageDialog(
                        this,
                        "Debe seleccionar una entrega"
                );

                return;
            }

            int idEntrega =
                    Integer.parseInt(
                            modeloTabla.getValueAt(
                                    fila,
                                    0
                            ).toString()
                    );

            JComboBox<String> cmbPedidoEditar =
                    new JComboBox<>();

            JComboBox<String> cmbRepartidorEditar =
                    new JComboBox<>();

            cargarPedidosEnCombo(
                    cmbPedidoEditar
            );

            cargarRepartidoresEnCombo(
                    cmbRepartidorEditar
            );

            JPanel panel =
                    new JPanel(
                            new GridLayout(
                                    2,
                                    2,
                                    10,
                                    10
                            )
                    );

            panel.add(
                    new JLabel("Pedido:")
            );

            panel.add(
                    cmbPedidoEditar
            );

            panel.add(
                    new JLabel("Repartidor:")
            );

            panel.add(
                    cmbRepartidorEditar
            );

            int opcion =
                    JOptionPane.showConfirmDialog(
                            this,
                            panel,
                            "Editar Entrega",
                            JOptionPane.OK_CANCEL_OPTION
                    );

            if (opcion ==
                    JOptionPane.OK_OPTION) {

                int idPedido =
                        obtenerId(
                                cmbPedidoEditar
                                        .getSelectedItem()
                                        .toString()
                        );

                int idRepartidor =
                        obtenerId(
                                cmbRepartidorEditar
                                        .getSelectedItem()
                                        .toString()
                        );

                EntregaDAO entregaDAO =
                        new EntregaDAO();

                entregaDAO.update(
                        idEntrega,
                        idPedido,
                        idRepartidor,
                        LocalDate.now(),
                        LocalTime.now()
                );

                JOptionPane.showMessageDialog(
                        this,
                        "Entrega actualizada correctamente"
                );

                cargarEntregas();
            }
        });

        btnEliminar.addActionListener(e -> {

            int fila =
                    tablaEntregas.getSelectedRow();

            if (fila == -1) {

                JOptionPane.showMessageDialog(
                        this,
                        "Debe seleccionar una entrega"
                );

                return;
            }

            int idEntrega =
                    Integer.parseInt(
                            modeloTabla.getValueAt(
                                    fila,
                                    0
                            ).toString()
                    );

            int confirmacion =
                    JOptionPane.showConfirmDialog(
                            this,
                            "¿Desea eliminar la entrega seleccionada?",
                            "Confirmar eliminación",
                            JOptionPane.YES_NO_OPTION
                    );

            if (confirmacion ==
                    JOptionPane.YES_OPTION) {

                EntregaDAO entregaDAO =
                        new EntregaDAO();

                entregaDAO.delete(
                        idEntrega
                );

                cargarEntregas();
            }
        });

        btnRefrescar.addActionListener(e -> {

            cargarCombos();
            cargarEntregas();

        });

        add(
                panelSuperior,
                BorderLayout.NORTH
        );

        add(
                scrollPane,
                BorderLayout.CENTER
        );

        add(
                panelBotones,
                BorderLayout.SOUTH
        );

        cargarCombos();
        cargarEntregas();

        setVisible(true);
    }

    private void cargarCombos() {

        cmbPedido.removeAllItems();
        cmbRepartidor.removeAllItems();

        cargarPedidosEnCombo(
                cmbPedido
        );

        cargarRepartidoresEnCombo(
                cmbRepartidor
        );
    }

    private void cargarPedidosEnCombo(
            JComboBox<String> combo
    ) {

        PedidoDAO pedidoDAO =
                new PedidoDAO();

        for (Object[] pedido :
                pedidoDAO.readAll()) {

            combo.addItem(
                    pedido[0] +
                            " - " +
                            pedido[1]
            );
        }
    }

    private void cargarRepartidoresEnCombo(
            JComboBox<String> combo
    ) {

        RepartidorDAO repartidorDAO =
                new RepartidorDAO();

        for (Object[] repartidor :
                repartidorDAO.readAll()) {

            combo.addItem(
                    repartidor[0] +
                            " - " +
                            repartidor[1]
            );
        }
    }

    private void cargarEntregas() {

        modeloTabla.setRowCount(0);

        EntregaDAO entregaDAO =
                new EntregaDAO();

        for (Object[] entrega :
                entregaDAO.readAll()) {

            modeloTabla.addRow(
                    entrega
            );
        }
    }

    private int obtenerId(
            String texto
    ) {

        String[] partes =
                texto.split(" - ");

        return Integer.parseInt(
                partes[0]
        );
    }
}