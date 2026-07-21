import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GestorInventario {
    private final String rutaBD = "jdbc:sqlite:inventario_local.db";

    public GestorInventario() {
        iniciarEstructura();
    }

    private Connection abrirConexion() throws Exception {
        return DriverManager.getConnection(rutaBD);
    }

    private void iniciarEstructura() {
        String query = "CREATE TABLE IF NOT EXISTS items_tienda ("
                   + " codigo INTEGER PRIMARY KEY AUTOINCREMENT,"
                   + " descripcion TEXT NOT NULL,"
                   + " costo REAL NOT NULL"
                   + ");";
        try (Connection conexion = abrirConexion(); Statement comando = conexion.createStatement()) {
            comando.execute(query);
        } catch (Exception ex) {
            System.out.println("Falla al inicializar la base de datos: " + ex.getMessage());
        }
    }

    // Crear (Create)
    public void agregarArticulo(Articulo articulo) {
        String query = "INSERT INTO items_tienda(descripcion, costo) VALUES(?, ?)";
        try (Connection conexion = abrirConexion(); PreparedStatement peticion = conexion.prepareStatement(query)) {
            peticion.setString(1, articulo.getDescripcion());
            peticion.setDouble(2, articulo.getCosto());
            peticion.executeUpdate();
            System.out.println(">> ¡Artículo guardado exitosamente!");
        } catch (Exception ex) {
            System.out.println("Falla al guardar: " + ex.getMessage());
        }
    }

    // Leer (Read)
    public List<Articulo> obtenerTodosLosArticulos() {
        List<Articulo> inventario = new ArrayList<>();
        String query = "SELECT codigo, descripcion, costo FROM items_tienda";
        try (Connection conexion = abrirConexion(); 
             Statement comando = conexion.createStatement(); 
             ResultSet resultados = comando.executeQuery(query)) {
            
            while (resultados.next()) {
                Articulo art = new Articulo(
                    resultados.getInt("codigo"),
                    resultados.getString("descripcion"),
                    resultados.getDouble("costo")
                );
                inventario.add(art);
            }
        } catch (Exception ex) {
            System.out.println("Falla al cargar inventario: " + ex.getMessage());
        }
        return inventario;
    }

    // Actualizar (Update)
    public boolean modificarArticulo(Articulo articulo) {
        String query = "UPDATE items_tienda SET descripcion = ?, costo = ? WHERE codigo = ?";
        try (Connection conexion = abrirConexion(); PreparedStatement peticion = conexion.prepareStatement(query)) {
            peticion.setString(1, articulo.getDescripcion());
            peticion.setDouble(2, articulo.getCosto());
            peticion.setInt(3, articulo.getCodigo());
            return peticion.executeUpdate() > 0;
        } catch (Exception ex) {
            System.out.println("Falla al modificar: " + ex.getMessage());
            return false;
        }
    }

    // Borrar (Delete)
    public boolean borrarArticulo(int codigo) {
        String query = "DELETE FROM items_tienda WHERE codigo = ?";
        try (Connection conexion = abrirConexion(); PreparedStatement peticion = conexion.prepareStatement(query)) {
            peticion.setInt(1, codigo);
            return peticion.executeUpdate() > 0;
        } catch (Exception ex) {
            System.out.println("Falla al borrar: " + ex.getMessage());
            return false;
        }
    }
}