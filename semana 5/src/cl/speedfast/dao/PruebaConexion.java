package cl.speedfast.dao;

import java.sql.Connection;

public class PruebaConexion {

    public static void main(String[] args) {

        try {

            Connection conexion = ConexionDB.conectar();

            if (conexion != null) {
                System.out.println("Conexion a MySQL realizada correctamente");
                conexion.close();
            }

        } catch (Exception e) {

            System.out.println("Error de conexion: " + e.getMessage());
        }
    }
}