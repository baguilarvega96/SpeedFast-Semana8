package cl.speedfast.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class EntregaDAO {

    public void create(
            int idPedido,
            int idRepartidor,
            LocalDate fecha,
            LocalTime hora
    ) {

        String sql =
                "INSERT INTO entrega " +
                        "(id_pedido, id_repartidor, fecha, hora) " +
                        "VALUES (?, ?, ?, ?)";

        try (
                Connection conexion = ConexionDB.conectar();
                PreparedStatement statement =
                        conexion.prepareStatement(sql)
        ) {

            statement.setInt(1, idPedido);
            statement.setInt(2, idRepartidor);
            statement.setDate(
                    3,
                    java.sql.Date.valueOf(fecha)
            );
            statement.setTime(
                    4,
                    java.sql.Time.valueOf(hora)
            );

            statement.executeUpdate();

            System.out.println(
                    "Entrega creada correctamente"
            );

        } catch (SQLException e) {

            System.out.println(
                    "Error al crear entrega: " +
                            e.getMessage()
            );
        }
    }

    public List<Object[]> readAll() {

        List<Object[]> entregas =
                new ArrayList<>();

        String sql =
                "SELECT e.id, " +
                        "e.id_pedido, " +
                        "p.direccion, " +
                        "e.id_repartidor, " +
                        "r.nombre, " +
                        "e.fecha, " +
                        "e.hora " +
                        "FROM entrega e " +
                        "INNER JOIN pedido p " +
                        "ON e.id_pedido = p.id " +
                        "INNER JOIN repartidor r " +
                        "ON e.id_repartidor = r.id " +
                        "ORDER BY e.id";

        try (
                Connection conexion =
                        ConexionDB.conectar();

                PreparedStatement statement =
                        conexion.prepareStatement(sql);

                ResultSet resultado =
                        statement.executeQuery()
        ) {

            while (resultado.next()) {

                entregas.add(
                        new Object[]{
                                resultado.getInt("id"),
                                resultado.getInt("id_pedido"),
                                resultado.getString("direccion"),
                                resultado.getInt("id_repartidor"),
                                resultado.getString("nombre"),
                                resultado.getDate("fecha"),
                                resultado.getTime("hora")
                        }
                );
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al listar entregas: " +
                            e.getMessage()
            );
        }

        return entregas;
    }

    public void update(
            int id,
            int idPedido,
            int idRepartidor,
            LocalDate fecha,
            LocalTime hora
    ) {

        String sql =
                "UPDATE entrega " +
                        "SET id_pedido = ?, " +
                        "id_repartidor = ?, " +
                        "fecha = ?, " +
                        "hora = ? " +
                        "WHERE id = ?";

        try (
                Connection conexion =
                        ConexionDB.conectar();

                PreparedStatement statement =
                        conexion.prepareStatement(sql)
        ) {

            statement.setInt(1, idPedido);
            statement.setInt(2, idRepartidor);

            statement.setDate(
                    3,
                    java.sql.Date.valueOf(fecha)
            );

            statement.setTime(
                    4,
                    java.sql.Time.valueOf(hora)
            );

            statement.setInt(5, id);

            statement.executeUpdate();

            System.out.println(
                    "Entrega actualizada correctamente"
            );

        } catch (SQLException e) {

            System.out.println(
                    "Error al actualizar entrega: " +
                            e.getMessage()
            );
        }
    }

    public void delete(int id) {

        String sql =
                "DELETE FROM entrega WHERE id = ?";

        try (
                Connection conexion =
                        ConexionDB.conectar();

                PreparedStatement statement =
                        conexion.prepareStatement(sql)
        ) {

            statement.setInt(1, id);

            statement.executeUpdate();

            System.out.println(
                    "Entrega eliminada correctamente"
            );

        } catch (SQLException e) {

            System.out.println(
                    "Error al eliminar entrega: " +
                            e.getMessage()
            );
        }
    }

    public void guardar(
            int idPedido,
            int idRepartidor,
            LocalDate fecha,
            LocalTime hora
    ) {

        create(
                idPedido,
                idRepartidor,
                fecha,
                hora
        );
    }
}