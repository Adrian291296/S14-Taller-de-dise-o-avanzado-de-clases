package club;

import java.util.ArrayList;
import java.util.Scanner;
import club.Socio.Tipo;

public class Main {

    private static Club club = new Club();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean salir = false;
        System.out.println("=== SISTEMA DE ADMINISTRACIÓN - CLUB SOCIAL ===");

        while (!salir) {
            mostrarMenu();
            int opcion;

            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.err.println("Error: Debe ingresar un valor numérico válido.");
                System.out.println("----------------------------------------------");
                continue;
            }

            switch (opcion) {
                case 1:
                    try {
                        afiliarSocio();
                    } catch (Exception e) {
                        System.err.println("Error en la operación: " + e.getMessage());
                    }
                    break;
                case 2:
                    try {
                        registrarAutorizado();
                    } catch (Exception e) {
                        System.err.println("Error en la operación: " + e.getMessage());
                    }
                    break;
                case 3:
                    try {
                        eliminarAutorizado();
                    } catch (Exception e) {
                        System.err.println("Error en la operación: " + e.getMessage());
                    }
                    break;
                case 4:
                    try {
                        registrarConsumo();
                    } catch (Exception e) {
                        System.err.println("Error en la operación: " + e.getMessage());
                    }
                    break;
                case 5:
                    try {
                        pagarFactura();
                    } catch (Exception e) {
                        System.err.println("Error en la operación: " + e.getMessage());
                    }
                    break;
                case 6:
                    try {
                        aumentarFondos();
                    } catch (Exception e) {
                        System.err.println("Error en la operación: " + e.getMessage());
                    }
                    break;
                case 7:
                    try {
                        consultarTotalConsumos();
                    } catch (Exception e) {
                        System.err.println("Error en la operación: " + e.getMessage());
                    }
                    break;
                case 8:
                    try {
                        eliminarSocioDelClub();
                    } catch (Exception e) {
                        System.err.println("Error en la operación: " + e.getMessage());
                    }
                    break;
                case 9:
                    listarSocios();
                    break;
                case 10:
                    salir = true;
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.err.println("Opción no válida. Intente de nuevo.");
            }
            System.out.println("----------------------------------------------");
        }
    }

    private static void mostrarMenu() {
        System.out.println("\n--- Menú Principal ---");
        System.out.println("1. Afiliar un socio al club");
        System.out.println("2. Registrar una persona autorizada");
        System.out.println("3. Eliminar una persona autorizada");
        System.out.println("4. Registrar un consumo");
        System.out.println("5. Pagar una factura");
        System.out.println("6. Aumentar fondos");
        System.out.println("7. Consultar total de consumos");
        System.out.println("8. Eliminar un socio");
        System.out.println("9. Listar todos los socios");
        System.out.println("10. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private static void afiliarSocio() throws Exception {
        System.out.print("Ingrese cédula: ");
        String cedula = scanner.nextLine();
        System.out.print("Ingrese nombre completo: ");
        String nombre = scanner.nextLine();
        System.out.print("Seleccione Tipo (1. VIP / 2. REGULAR): ");
        int tipoOp = Integer.parseInt(scanner.nextLine());
        Tipo tipo = (tipoOp == 1) ? Tipo.VIP : Tipo.REGULAR;

        club.afiliarSocio(cedula, nombre, tipo);
        System.out.println("Socio afiliado con éxito.");
    }

    private static void registrarAutorizado() throws Exception {
        System.out.print("Ingrese cédula del socio titular: ");
        String cedula = scanner.nextLine();
        asegurarSocio(cedula);

        System.out.print("Ingrese nombre de la persona autorizada: ");
        String autorizado = scanner.nextLine();

        club.agregarAutorizadoSocio(cedula, autorizado);
        System.out.println("Persona autorizada registrada.");
    }

    private static void eliminarAutorizado() throws Exception {
        System.out.print("Ingrese cédula del socio titular: ");
        String cedula = scanner.nextLine();
        Socio s = asegurarSocio(cedula);

        ArrayList<String> autorizados = s.darAutorizados();
        if (autorizados.isEmpty()) {
            throw new Exception("El socio no posee personas autorizadas registradas.");
        }

        System.out.println("Personas autorizadas actuales:");
        for (int i = 0; i < autorizados.size(); i++) {
            System.out.println("[" + i + "] " + autorizados.get(i));
        }

        System.out.print("Ingrese el nombre exacto o seleccione el número del autorizado a eliminar: ");
        String entrada = scanner.nextLine();
        String nombreAEliminar = "";

        try {
            int indice = Integer.parseInt(entrada);
            if (indice >= 0 && indice < autorizados.size()) {
                nombreAEliminar = autorizados.get(indice);
            } else {
                throw new Exception("Índice de autorizado fuera de rango.");
            }
        } catch (NumberFormatException e) {
            nombreAEliminar = entrada;
        }


        club.eliminarAutorizadoSocio(cedula, nombreAEliminar);
        System.out.println("Proceso de eliminación finalizado para: " + nombreAEliminar);
    }

    private static void registrarConsumo() throws Exception {
        System.out.print("Ingrese cédula del socio: ");
        String cedula = scanner.nextLine();
        asegurarSocio(cedula);

        System.out.print("Nombre de la persona que consume (Socio/Autorizado): ");
        String cliente = scanner.nextLine();
        System.out.print("Concepto del consumo: ");
        String concepto = scanner.nextLine();
        System.out.print("Valor del consumo: ");
        double valor = Double.parseDouble(scanner.nextLine());

        club.registrarConsumo(cedula, cliente, concepto, valor);
        System.out.println("Consumo procesado.");
    }

    private static void pagarFactura() throws Exception {
        System.out.print("Ingrese cédula del socio: ");
        String cedula = scanner.nextLine();
        asegurarSocio(cedula);

        ArrayList<Factura> facturas = club.darFacturasSocio(cedula);
        if (facturas.isEmpty()) {
            System.out.println("El socio no presenta facturas pendientes.");
            return;
        }

        for (int i = 0; i < facturas.size(); i++) {
            System.out.println("[" + i + "] " + facturas.get(i));
        }
        System.out.print("Seleccione el índice de la factura a pagar: ");
        int indice = Integer.parseInt(scanner.nextLine());

        if (indice < 0 || indice >= facturas.size()) {
            throw new Exception("Índice de factura fuera de rango.");
        }

        club.pagarFacturaSocio(cedula, indice);
        System.out.println("Factura liquidada.");
    }

    private static void aumentarFondos() throws Exception {
        System.out.print("Ingrese cédula del socio: ");
        String cedula = scanner.nextLine();
        asegurarSocio(cedula);

        System.out.print("Monto a agregar: ");
        double monto = Double.parseDouble(scanner.nextLine());

        club.aumentarFondosSocio(cedula, monto);
        System.out.println("Fondos actualizados.");
    }

    private static void consultarTotalConsumos() throws Exception {
        System.out.print("Ingrese la cédula a consultar: ");
        String cedula = scanner.nextLine();

        double total = club.obtenerTotalConsumosSocio(cedula);
        System.out.println("Monto total de consumos acumulados: $" + total);
    }

    private static void eliminarSocioDelClub() throws Exception {
        System.out.print("Ingrese la cédula del socio a eliminar: ");
        String cedula = scanner.nextLine();

        if (club.sePuedeEliminarSocio(cedula)) {
            club.eliminarSocio(cedula);
            System.out.println("El socio fue desvinculado del club satisfactoriamente.");
        }
    }

    private static void listarSocios() {
        ArrayList<Socio> lista = club.darSocios();
        if (lista.isEmpty()) {
            System.out.println("No hay socios registrados en el club.");
            return;
        }
        System.out.println("=== SOCIOS REGISTRADOS ===");
        for (Socio s : lista) {
            System.out.println(s.toString() + " | Tipo: " + s.darTipo() + " | Fondos: $" + s.darFondos() + " | Facturas: " + s.darFacturas().size() + " | Autorizados: " + s.darAutorizados().size());
        }
    }

    private static Socio asegurarSocio(String cedula) throws Exception {
        Socio s = club.buscarSocio(cedula);
        if (s == null) {
            throw new Exception("Socio con cédula " + cedula + " no está registrado.");
        }
        return s;
    }
}