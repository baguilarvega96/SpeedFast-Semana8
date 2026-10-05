package cl.speedfast.dao;

import cl.speedfast.modelo.Repartidor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RepartidorDAO {

    public void create(String nombre) {

        String sql =
                "INSERT INTO repartidor (nombre) VALUES (?)";

        try (
                Connection conexion = ConexionDB.conectar();
                PreparedStatement statement =
                        conexion.prepareStatement(sql)
        ) {

            statement.setString(1, nombre);

            statement.executeUpdate();

            System.out.println(
                    "Repartidor creado correctamente"
            );

        } catch (SQLException e) {

            System.out.println(
                    "Error al crear repartidor: " +
                            e.getMessage()
            );
        }
    }

    public List<Object[]> readAll() {

        List<Object[]> repartidores =
                new ArrayList<>();

        String sql =
                "SELECT id, nombre " +
                        "FROM repartidor " +
                        "ORDER BY id";

        try (
                Connection conexion =
                        ConexionDB.conectar();

                PreparedStatement statement =
                        conexion.prepareStatement(sql);

                ResultSet resultado =
                        statement.executeQuery()
        ) {

            while (resultado.next()) {

                repartidores.add(
                        new Object[]{
                                resultado.getInt("id"),
                                resultado.getString("nombre")
                        }
                );
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al listar repartidores: " +
                            e.getMessage()
            );
        }

        return repartidores;
    }

    public void update(
            int id,
            String nombre
    ) {

        String sql =
                "UPDATE repartidor " +
                        "SET nombre = ? " +
                        "WHERE id = ?";

        try (
                Connection conexion =
                        ConexionDB.conectar();

                PreparedStatement statement =
                        conexion.prepareStatement(sql)
        ) {

            statement.setString(1, nombre);
            statement.setInt(2, id);

            statement.executeUpdate();

            System.out.println(
                    "Repartidor actualizado correctamente"
            );

        } catch (SQLException e) {

            System.out.println(
                    "Error al actualizar repartidor: " +
                            e.getMessage()
            );
        }
    }

    public void delete(int id) {

        String sql =
                "DELETE FROM repartidor " +
                        "WHERE id = ?";

        try (
                Connection conexion =
                        ConexionDB.conectar();

                PreparedStatement statement =
                        conexion.prepareStatement(sql)
        ) {

            statement.setInt(1, id);

            statement.executeUpdate();

            System.out.println(
                    "Repartidor eliminado correctamente"
            );

        } catch (SQLException e) {

            System.out.println(
                    "Error al eliminar repartidor: " +
                            e.getMessage()
            );
        }
    }

    public void guardar(
            Repartidor repartidor
    ) {

        create(
                repartidor.getNombre()
        );
    }

    public int obtenerUltimoId() {

        String sql =
                "SELECT MAX(id) AS ultimo_id " +
                        "FROM repartidor";

        try (
                Connection conexion =
                        ConexionDB.conectar();

                PreparedStatement statement =
                        conexion.prepareStatement(sql);

                ResultSet resultado =
                        statement.executeQuery()
        ) {

            if (resultado.next()) {

                return resultado.getInt(
                        "ultimo_id"
                );
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al obtener ID del repartidor: " +
                            e.getMessage()
            );
        }

        return -1;
    }

    public List<String> listarTodos() {

        List<String> repartidores =
                new ArrayList<>();

        for (Object[] repartidor :
                readAll()) {

            repartidores.add(
                    repartidor[0] +
                            " - " +
                            repartidor[1]
            );
        }

        return repartidores;
    }
}