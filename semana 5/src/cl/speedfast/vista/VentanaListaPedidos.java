package cl.speedfast.vista;

import cl.speedfast.dao.PedidoDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VentanaListaPedidos extends JFrame {

    private JTable tablaPedidos;
    private DefaultTableModel modeloTabla;

    public VentanaListaPedidos() {

        setTitle("Gestión de Pedidos");
        setSize(800, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        modeloTabla = new DefaultTableModel(
                new Object[]{
                        "ID",
                        "Dirección",
                        "Tipo",
                        "Estado"
                },
                0
        );

        tablaPedidos = new JTable(modeloTabla);

        JScrollPane scrollPane =
                new JScrollPane(tablaPedidos);

        JButton btnRefrescar =
                new JButton("Refrescar");

        JButton btnEditar =
                new JButton("Editar");

        JButton btnEliminar =
                new JButton("Eliminar");

        btnRefrescar.addActionListener(e -> {
            cargarPedidos();
        });

        btnEditar.addActionListener(e -> {

            int fila =
                    tablaPedidos.getSelectedRow();

            if (fila == -1) {

                JOptionPane.showMessageDialog(
                        this,
                        "Debe seleccionar un pedido"
                );

                return;
            }

            int id =
                    Integer.parseInt(
                            modeloTabla.getValueAt(fila, 0).toString()
                    );

            String direccionActual =
                    modeloTabla.getValueAt(fila, 1).toString();

            String tipoActual =
                    modeloTabla.getValueAt(fila, 2).toString();

            String estadoActual =
                    modeloTabla.getValueAt(fila, 3).toString();

            JTextField txtDireccion =
                    new JTextField(direccionActual);

            JComboBox<String> cmbTipo =
                    new JComboBox<>(
                            new String[]{
                                    "COMIDA",
                                    "ENCOMIENDA",
                                    "EXPRESS"
                            }
                    );

            cmbTipo.setSelectedItem(
                    tipoActual.toUpperCase()
            );

            JComboBox<String> cmbEstado =
                    new JComboBox<>(
                            new String[]{
                                    "PENDIENTE",
                                    "EN_REPARTO",
                                    "ENTREGADO"
                            }
                    );

            cmbEstado.setSelectedItem(
                    estadoActual.toUpperCase()
            );

            JPanel panel =
                    new JPanel(
                            new GridLayout(3, 2, 10, 10)
                    );

            panel.add(
                    new JLabel("Dirección:")
            );

            panel.add(txtDireccion);

            panel.add(
                    new JLabel("Tipo:")
            );

            panel.add(cmbTipo);

            panel.add(
                    new JLabel("Estado:")
            );

            panel.add(cmbEstado);

            int opcion =
                    JOptionPane.showConfirmDialog(
                            this,
                            panel,
                            "Editar Pedido",
                            JOptionPane.OK_CANCEL_OPTION
                    );

            if (opcion ==
                    JOptionPane.OK_OPTION) {

                String nuevaDireccion =
                        txtDireccion.getText().trim();

                if (nuevaDireccion.isEmpty()) {

                    JOptionPane.showMessageDialog(
                            this,
                            "La dirección no puede estar vacía"
                    );

                    return;
                }

                String nuevoTipo =
                        cmbTipo.getSelectedItem().toString();

                String nuevoEstado =
                        cmbEstado.getSelectedItem().toString();

                PedidoDAO pedidoDAO =
                        new PedidoDAO();

                pedidoDAO.update(
                        id,
                        nuevaDireccion,
                        nuevoTipo,
                        nuevoEstado
                );

                JOptionPane.showMessageDialog(
                        this,
                        "Pedido actualizado correctamente"
                );

                cargarPedidos();
            }
        });

        btnEliminar.addActionListener(e -> {

            int fila =
                    tablaPedidos.getSelectedRow();

            if (fila == -1) {

                JOptionPane.showMessageDialog(
                        this,
                        "Debe seleccionar un pedido"
                );

                return;
            }

            int id =
                    Integer.parseInt(
                            modeloTabla.getValueAt(fila, 0).toString()
                    );

            int confirmacion =
                    JOptionPane.showConfirmDialog(
                            this,
                            "¿Desea eliminar el pedido seleccionado?",
                            "Confirmar eliminación",
                            JOptionPane.YES_NO_OPTION
                    );

            if (confirmacion ==
                    JOptionPane.YES_OPTION) {

                PedidoDAO pedidoDAO =
                        new PedidoDAO();

                pedidoDAO.delete(id);

                cargarPedidos();
            }
        });

        JPanel panelBotones =
                new JPanel();

        panelBotones.add(btnRefrescar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);

        setLayout(new BorderLayout());

        add(
                scrollPane,
                BorderLayout.CENTER
        );

        add(
                panelBotones,
                BorderLayout.SOUTH
        );

        cargarPedidos();

        setVisible(true);
    }

    private void cargarPedidos() {

        modeloTabla.setRowCount(0);

        PedidoDAO pedidoDAO =
                new PedidoDAO();

        for (Object[] pedido :
                pedidoDAO.readAll()) {

            modeloTabla.addRow(pedido);
        }
    }
}