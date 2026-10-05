package cl.speedfast.vista;

import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {

    public VentanaPrincipal() {

        setTitle("SpeedFast - Gestión");
        setSize(500, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel =
                new JPanel();

        panel.setLayout(
                new GridLayout(
                        5,
                        1,
                        10,
                        10
                )
        );

        JButton btnRegistrarPedido =
                new JButton(
                        "Registrar pedido"
                );

        JButton btnGestionarPedidos =
                new JButton(
                        "Gestionar pedidos"
                );

        JButton btnGestionarRepartidores =
                new JButton(
                        "Gestionar repartidores"
                );

        JButton btnGestionarEntregas =
                new JButton(
                        "Gestionar entregas"
                );

        JButton btnAsignarRepartidor =
                new JButton(
                        "Asignar repartidor / Iniciar entrega"
                );

        btnRegistrarPedido.addActionListener(e -> {
            new VentanaRegistroPedido();
        });

        btnGestionarPedidos.addActionListener(e -> {
            new VentanaListaPedidos();
        });

        btnGestionarRepartidores.addActionListener(e -> {
            new VentanaGestionRepartidores();
        });

        btnGestionarEntregas.addActionListener(e -> {
            new VentanaGestionEntregas();
        });

        btnAsignarRepartidor.addActionListener(e -> {
            new VentanaAsignarRepartidor();
        });

        panel.add(
                btnRegistrarPedido
        );

        panel.add(
                btnGestionarPedidos
        );

        panel.add(
                btnGestionarRepartidores
        );

        panel.add(
                btnGestionarEntregas
        );

        panel.add(
                btnAsignarRepartidor
        );

        add(panel);

        setVisible(true);
    }
}