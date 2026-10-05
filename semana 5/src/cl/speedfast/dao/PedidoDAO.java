package cl.speedfast.dao;

import cl.speedfast.modelo.Pedido;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {

    public void create(Pedido pedido) {

        String sql =
                "INSERT INTO pedido (id, direccion, tipo, estado) " +
                        "VALUES (?, ?, ?, ?)";

        try (
                Connection conexion = ConexionDB.conectar();
                PreparedStatement statement =
                        conexion.prepareStatement(sql)
        ) {

            statement.setInt(1, pedido.getId());
            statement.setString(2, pedido.getDireccionEntrega());
            statement.setString(3, pedido.getTipoPedido());
            statement.setString(4, pedido.getEstado().toString());

            statement.executeUpdate();

            System.out.println(
                    "Pedido creado correctamente en la base de datos"
            );

        } catch (SQLException e) {

            System.out.println(
                    "Error al crear pedido: " + e.getMessage()
            );
        }
    }

    public List<Object[]> readAll() {

        List<Object[]> pedidos = new ArrayList<>();

        String sql =
                "SELECT id, direccion, tipo, estado FROM pedido ORDER BY id";

        try (
                Connection conexion = ConexionDB.conectar();
                PreparedStatement statement =
                        conexion.prepareStatement(sql);
                ResultSet resultado =
                        statement.executeQuery()
        ) {

            while (resultado.next()) {

                pedidos.add(new Object[]{
                        resultado.getInt("id"),
                        resultado.getString("direccion"),
                        resultado.getString("tipo"),
                        resultado.getString("estado")
                });
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al listar pedidos: " + e.getMessage()
            );
        }

        return pedidos;
    }

    public void update(
            int id,
            String direccion,
            String tipo,
            String estado
    ) {

        String sql =
                "UPDATE pedido " +
                        "SET direccion = ?, tipo = ?, estado = ? " +
                        "WHERE id = ?";

        try (
                Connection conexion = ConexionDB.conectar();
                PreparedStatement statement =
                        conexion.prepareStatement(sql)
        ) {

            statement.setString(1, direccion);
            statement.setString(2, tipo);
            statement.setString(3, estado);
            statement.setInt(4, id);

            statement.executeUpdate();

            System.out.println(
                    "Pedido actualizado correctamente"
            );

        } catch (SQLException e) {

            System.out.println(
                    "Error al actualizar pedido: " + e.getMessage()
            );
        }
    }

    public void delete(int id) {

        String sql =
                "DELETE FROM pedido WHERE id = ?";

        try (
                Connection conexion = ConexionDB.conectar();
                PreparedStatement statement =
                        conexion.prepareStatement(sql)
        ) {

            statement.setInt(1, id);
            statement.executeUpdate();

            System.out.println(
                    "Pedido eliminado correctamente"
            );

        } catch (SQLException e) {

            System.out.println(
                    "Error al eliminar pedido: " + e.getMessage()
            );
        }
    }

    public void guardar(Pedido pedido) {
        create(pedido);
    }

    public List<Object[]> listarTodos() {
        return readAll();
    }
}