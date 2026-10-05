package cl.speedfast.vista;

import cl.speedfast.dao.RepartidorDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VentanaGestionRepartidores extends JFrame {

    private JTable tablaRepartidores;
    private DefaultTableModel modeloTabla;
    private JTextField txtNombre;

    public VentanaGestionRepartidores() {

        setTitle("Gestión de Repartidores");
        setSize(650, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout());

        JPanel panelSuperior = new JPanel(
                new GridLayout(2, 2, 10, 10)
        );

        JLabel lblNombre = new JLabel("Nombre:");
        txtNombre = new JTextField();

        JButton btnRegistrar =
                new JButton("Registrar");

        panelSuperior.add(lblNombre);
        panelSuperior.add(txtNombre);

        panelSuperior.add(new JLabel(""));
        panelSuperior.add(btnRegistrar);

        modeloTabla = new DefaultTableModel(
                new Object[]{
                        "ID",
                        "Nombre"
                },
                0
        );

        tablaRepartidores =
                new JTable(modeloTabla);

        JScrollPane scrollPane =
                new JScrollPane(tablaRepartidores);

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

            String nombre =
                    txtNombre.getText().trim();

            if (nombre.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Debe ingresar el nombre del repartidor"
                );

                return;
            }

            RepartidorDAO repartidorDAO =
                    new RepartidorDAO();

            repartidorDAO.create(nombre);

            JOptionPane.showMessageDialog(
                    this,
                    "Repartidor registrado correctamente"
            );

            txtNombre.setText("");

            cargarRepartidores();
        });

        btnEditar.addActionListener(e -> {

            int fila =
                    tablaRepartidores.getSelectedRow();

            if (fila == -1) {

                JOptionPane.showMessageDialog(
                        this,
                        "Debe seleccionar un repartidor"
                );

                return;
            }

            int id =
                    Integer.parseInt(
                            modeloTabla.getValueAt(
                                    fila,
                                    0
                            ).toString()
                    );

            String nombreActual =
                    modeloTabla.getValueAt(
                            fila,
                            1
                    ).toString();

            String nuevoNombre =
                    JOptionPane.showInputDialog(
                            this,
                            "Nuevo nombre:",
                            nombreActual
                    );

            if (nuevoNombre == null) {
                return;
            }

            nuevoNombre =
                    nuevoNombre.trim();

            if (nuevoNombre.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "El nombre no puede estar vacío"
                );

                return;
            }

            RepartidorDAO repartidorDAO =
                    new RepartidorDAO();

            repartidorDAO.update(
                    id,
                    nuevoNombre
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Repartidor actualizado correctamente"
            );

            cargarRepartidores();
        });

        btnEliminar.addActionListener(e -> {

            int fila =
                    tablaRepartidores.getSelectedRow();

            if (fila == -1) {

                JOptionPane.showMessageDialog(
                        this,
                        "Debe seleccionar un repartidor"
                );

                return;
            }

            int id =
                    Integer.parseInt(
                            modeloTabla.getValueAt(
                                    fila,
                                    0
                            ).toString()
                    );

            int confirmacion =
                    JOptionPane.showConfirmDialog(
                            this,
                            "¿Desea eliminar el repartidor seleccionado?",
                            "Confirmar eliminación",
                            JOptionPane.YES_NO_OPTION
                    );

            if (confirmacion ==
                    JOptionPane.YES_OPTION) {

                RepartidorDAO repartidorDAO =
                        new RepartidorDAO();

                repartidorDAO.delete(id);

                cargarRepartidores();
            }
        });

        btnRefrescar.addActionListener(e -> {
            cargarRepartidores();
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

        cargarRepartidores();

        setVisible(true);
    }

    private void cargarRepartidores() {

        modeloTabla.setRowCount(0);

        RepartidorDAO repartidorDAO =
                new RepartidorDAO();

        for (Object[] repartidor :
                repartidorDAO.readAll()) {

            modeloTabla.addRow(
                    repartidor
            );
        }
    }
}