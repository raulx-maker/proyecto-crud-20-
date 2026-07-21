public class Articulo {
    private int codigo;
    private String descripcion;
    private double costo;

    // Constructor para insertar un nuevo artículo (la BD asigna el código automáticamente)
    public Articulo(String descripcion, double costo) {
        this.descripcion = descripcion;
        this.costo = costo;
    }

    // Constructor para cuando leemos de la base de datos o actualizamos
    public Articulo(int codigo, String descripcion, double costo) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.costo = costo;
    }

    // Métodos de acceso (Getters y Setters)
    public int getCodigo() { return codigo; }
    public void setCodigo(int codigo) { this.codigo = codigo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public double getCosto() { return costo; }
    public void setCosto(double costo) { this.costo = costo; }
}