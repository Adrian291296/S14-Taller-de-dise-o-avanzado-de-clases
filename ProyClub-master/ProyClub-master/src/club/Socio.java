package club;

import java.util.ArrayList;

public class Socio {

    public enum Tipo {
        VIP, REGULAR
    }

    public final static double FONDOS_INICIALES_REGULARES = 50;
    public final static double FONDOS_INICIALES_VIP = 100;
    public final static double MONTO_MAXIMO_REGULARES = 1000;
    public final static double MONTO_MAXIMO_VIP = 5000;

    private String cedula;
    private String nombre;
    private double fondos;
    private Tipo tipoSubscripcion;
    private ArrayList<Factura> facturas;
    private ArrayList<String> autorizados;

    public Socio(String pCedula, String pNombre, Tipo pTipo) {
        cedula = pCedula;
        nombre = pNombre;
        tipoSubscripcion = pTipo;

        switch (tipoSubscripcion) {
            case VIP:
                fondos = FONDOS_INICIALES_VIP;
                break;
            default:
                fondos = FONDOS_INICIALES_REGULARES;
        }

        facturas = new ArrayList<Factura>();
        autorizados = new ArrayList<String>();
    }

    public String darNombre() { return nombre; }
    public String darCedula() { return cedula; }
    public double darFondos() { return fondos; }
    public Tipo darTipo() { return tipoSubscripcion; }
    public ArrayList<Factura> darFacturas() { return facturas; }
    public ArrayList<String> darAutorizados() { return autorizados; }

    private boolean existeAutorizado(String pNombreAutorizado) {
        boolean encontro = false;
        for (int i = 0; i < autorizados.size() && !encontro; i++) {
            if (autorizados.get(i).equals(pNombreAutorizado)) {
                encontro = true;
            }
        }
        return encontro;
    }

    private boolean tieneFacturaAsociada(String pNombreAutorizado) {
        boolean tiene = false;
        for (int i = 0; i < facturas.size() && !tiene; i++) {
            if (facturas.get(i).darNombre().equals(pNombreAutorizado)) {
                tiene = true;
            }
        }
        return tiene;
    }

    public void aumentarFondos(double pFondos) throws Exception {
        if (tipoSubscripcion == Tipo.VIP && pFondos + fondos > MONTO_MAXIMO_VIP) {
            throw new Exception("Con este monto se excederían los fondos máximos de un socio VIP.");
        } else if (tipoSubscripcion == Tipo.REGULAR && pFondos + fondos > MONTO_MAXIMO_REGULARES) {
            throw new Exception("Con este monto se excederían los fondos máximos de un socio regular.");
        } else {
            fondos = fondos + pFondos;
        }
    }

    public void registrarConsumo(String pNombre, String pConcepto, double pValor) throws Exception {
        if (pValor > fondos) {
            throw new Exception("El socio no posee fondos suficientes para este consumo.");
        } else {
            Factura nuevaFactura = new Factura(pNombre, pConcepto, pValor);
            facturas.add(nuevaFactura);
        }
    }

    public void agregarAutorizado(String pNombreAutorizado) throws Exception {
        if (pNombreAutorizado.equals(darNombre())) {
            throw new Exception("No puede agregar el socio como autorizado.");
        }
        if (fondos == 0) {
            throw new Exception("El socio no tiene fondos para financiar un nuevo autorizado.");
        }
        if (!existeAutorizado(pNombreAutorizado)) {
            autorizados.add(pNombreAutorizado);
        } else {
            throw new Exception("El autorizado ya existe.");
        }
    }

    public void eliminarAutorizado(String pNombreAutorizado) throws Exception {
        if (tieneFacturaAsociada(pNombreAutorizado)) {
            throw new Exception(pNombreAutorizado + " tiene una factura sin pagar.");
        }
        boolean encontro = false;
        for (int i = 0; i < autorizados.size() && !encontro; i++) {
            if (autorizados.get(i).equals(pNombreAutorizado)) {
                encontro = true;
                autorizados.remove(i);
            }
        }
        if (!encontro) {
            throw new Exception("El autorizado no existe.");
        }
    }

    public void pagarFactura(int pIndiceFactura) throws Exception {
        if (pIndiceFactura < 0 || pIndiceFactura >= facturas.size()) {
            throw new Exception("Índice de factura fuera de rango.");
        }
        Factura factura = facturas.get(pIndiceFactura);
        if (factura.darValor() > fondos) {
            throw new Exception("El socio no posee fondos suficientes para pagar esta factura.");
        } else {
            fondos = fondos - factura.darValor();
            facturas.remove(pIndiceFactura);
        }
    }

    public String toString() {
        return cedula + " - " + nombre;
    }
}