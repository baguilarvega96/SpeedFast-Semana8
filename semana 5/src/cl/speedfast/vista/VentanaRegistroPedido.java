package cl.speedfast.vista;

import cl.speedfast.dao.PedidoDAO;
import cl.speedfast.modelo.Pedido;
import cl.speedfast.modelo.PedidoComida;
import cl.speedfast.modelo.PedidoEncomienda;
import cl.speedfast.modelo.PedidoExpress;

import javax.swing.*;
import java.awt.*;

public class VentanaRegistroPedido extends JFrame {

    public VentanaRegistroPedido() {

        setTitle("Registrar Pedido");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 10, 10));

        JLabel lblId = new JLabel("ID:");
        JTextField txtId = new JTextField();

        JLabel lblDireccion = new JLabel("Dirección:");
        JTextField txtDireccion = new JTextField();

        JLabel lblDistancia = new JLabel("Distancia KM:");
        JTextField txtDistancia = new JTextField();

        JLabel lblTipo = new JLabel("Tipo:");
        JComboBox<String> cmbTipo = new JComboBox<>(
                new String[]{"COMIDA", "ENCOMIENDA", "EXPRESS"}
        );

        JButton btnGuardar = new JButton("Guardar");

        btnGuardar.addActionListener(e -> {

            String id = txtId.getText().trim();
            String direccion = txtDireccion.getText().trim();
            String distanciaTexto = txtDistancia.getText().trim();
            String tipo = cmbTipo.getSelectedItem().toString();

            if (id.isEmpty() || direccion.isEmpty() || distanciaTexto.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Debe completar todos los campos"
                );

                return;
            }

            try {

                double distancia = Double.parseDouble(distanciaTexto);

                Pedido pedido;

                if (tipo.equals("COMIDA")) {

                    pedido = new PedidoComida(
                            id,
                            direccion,
                            distancia
                    );

                } else if (tipo.equals("ENCOMIENDA")) {

                    pedido = new PedidoEncomienda(
                            id,
                            direccion,
                            distancia
                    );

                } else {

                    pedido = new PedidoExpress(
                            id,
                            direccion,
                            distancia
                    );
                }

                DatosCompartidos.pedidos.add(pedido);

                PedidoDAO pedidoDAO = new PedidoDAO();
                pedidoDAO.guardar(pedido);

                JOptionPane.showMessageDialog(
                        this,
                        "Pedido registrado correctamente"
                );

                txtId.setText("");
                txtDireccion.setText("");
                txtDistancia.setText("");

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "La distancia debe ser un número"
                );
            }
        });

        panel.add(lblId);
        panel.add(txtId);

        panel.add(lblDireccion);
        panel.add(txtDireccion);

        panel.add(lblDistancia);
        panel.add(txtDistancia);

        panel.add(lblTipo);
        panel.add(cmbTipo);

        panel.add(new JLabel(""));
        panel.add(btnGuardar);

        add(panel);

        setVisible(true);
    }
}