import java.util.List;
import java.util.Scanner;

/**
 * Proyecto elaborado por el equipo: 
 * Raul Vivas, Darrel Sarmiento, Adrian Pineda, Juan Canquis
 * Jeremy Ramirez, Daniel Marrufo, Richard Teran y Andy Rosales.
 */
public class SistemaPrincipal {
    public static void main(String[] args) {
        GestorInventario controlador = new GestorInventario();
        Scanner lector = new Scanner(System.in);
        int seleccion = 0;

        do {
            System.out.println("\n=================================");
            System.out.println("      CONTROL DE INVENTARIO      ");
            System.out.println("=================================");
            System.out.println("[1] Añadir un nuevo artículo");
            System.out.println("[2] Mostrar catálogo completo");
            System.out.println("[3] Editar un artículo existente");
            System.out.println("[4] Remover un artículo");
            System.out.println("[5] Cerrar la aplicación");
            System.out.print("Elige una acción (1-5): ");

            try {
                seleccion = Integer.parseInt(lector.nextLine());

                switch (seleccion) {
                    case 1:
                        System.out.print("Ingrese la descripción del artículo: ");
                        String desc = lector.nextLine();
                        System.out.print("Ingrese el costo: ");
                        double costo = Double.parseDouble(lector.nextLine());

                        Articulo nuevoArticulo = new Articulo(desc, costo);
                        controlador.agregarArticulo(nuevoArticulo);
                        break;

                    case 2:
                        System.out.println("\n--- CATÁLOGO ACTUAL ---");
                        List<Articulo> catalogo = controlador.obtenerTodosLosArticulos();
                        if (catalogo.isEmpty()) {
                            System.out.println("El inventario está vacío en este momento.");
                        } else {
                            for (Articulo art : catalogo) {
                                System.out.println(String.format("Código: %d | Descripción: %s | Costo: $%.2f", 
                                    art.getCodigo(), art.getDescripcion(), art.getCosto()));
                            }
                        }
                        break;

                    case 3:
                        System.out.print("Introduzca el Código del artículo a editar: ");
                        int codEditar = Integer.parseInt(lector.nextLine());
                        System.out.print("Nueva descripción: ");
                        String nuevaDesc = lector.nextLine();
                        System.out.print("Nuevo costo: ");
                        double nuevoCosto = Double.parseDouble(lector.nextLine());

                        Articulo artModificado = new Articulo(codEditar, nuevaDesc, nuevoCosto);
                        if (controlador.modificarArticulo(artModificado)) {
                            System.out.println(">> ¡Actualización aplicada correctamente!");
                        } else {
                            System.out.println(">> Error: No existe un artículo con ese código.");
                        }
                        break;

                    case 4:
                        System.out.print("Introduzca el Código del artículo a remover: ");
                        int codRemover = Integer.parseInt(lector.nextLine());
                        if (controlador.borrarArticulo(codRemover)) {
                            System.out.println(">> ¡Artículo borrado del sistema!");
                        } else {
                            System.out.println(">> Error: No se halló el artículo especificado.");
                        }
                        break;

                    case 5:
                        System.out.println("Cerrando el gestor de inventario... ¡Hasta luego!");
                        break;

                    default:
                        System.out.println(">> Por favor, ingresa un número válido entre 1 y 5.");
                }
            } catch (Exception e) {
                System.out.println(">> Entrada no válida. Asegúrate de ingresar el tipo de dato correcto.");
            }
        } while (seleccion != 5);

        lector.close();
    }
}