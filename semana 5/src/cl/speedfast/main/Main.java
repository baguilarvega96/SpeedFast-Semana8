package cl.speedfast.main;

import cl.speedfast.dao.ConexionDB;
import cl.speedfast.vista.VentanaPrincipal;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        JPasswordField campoPassword = new JPasswordField();

        int opcion = JOptionPane.showConfirmDialog(
                null,
                campoPassword,
                "Ingrese contraseña de MySQL",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (opcion != JOptionPane.OK_OPTION) {
            System.exit(0);
        }

        String password = new String(
                campoPassword.getPassword()
        );

        ConexionDB.configurarPassword(password);

        try {

            ConexionDB.conectar().close();

            new VentanaPrincipal();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    null,
                    "No fue posible conectar con MySQL.\n" +
                            "Verifique la contraseña y que MySQL esté iniciado.",
                    "Error de conexión",
                    JOptionPane.ERROR_MESSAGE
            );

            System.exit(0);
        }
    }
}