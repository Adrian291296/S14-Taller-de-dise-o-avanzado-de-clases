package club;

import java.util.ArrayList;
import club.Socio.Tipo;

public class Club {

    public final static int MAXIMO_VIP = 3;

    private ArrayList<Socio> socios;

    public Club() {
        socios = new ArrayList<Socio>();
    }

    public ArrayList<Socio> darSocios() {
        return socios;
    }

    public void afiliarSocio(String pCedula, String pNombre, Tipo pTipo) throws Exception {
        Socio s = buscarSocio(pCedula);
        if (pTipo == Tipo.VIP && contarSociosVIP() == MAXIMO_VIP) {
            throw new Exception("El club en el momento no acepta más socios VIP.");
        }
        if (s == null) {
            Socio nuevoSocio = new Socio(pCedula, pNombre, pTipo);
            socios.add(nuevoSocio);
        } else {
            throw new Exception("El socio ya existe.");
        }
    }

    public Socio buscarSocio(String pCedulaSocio) {
        Socio elSocio = null;
        boolean encontre = false;
        int numSocios = socios.size();
        for (int i = 0; i < numSocios && !encontre; i++) {
            Socio s = socios.get(i);
            if (s.darCedula().equals(pCedulaSocio)) {
                elSocio = s;
                encontre = true;
            }
        }
        return elSocio;
    }

    public int contarSociosVIP() {
        int conteo = 0;
        for (Socio socio : socios) {
            if (socio.darTipo() == Tipo.VIP) {
                conteo++;
            }
        }
        return conteo;
    }

    public ArrayList<String> darAutorizadosSocio(String pCedulaSocio) throws Exception {
        Socio s = buscarSocio(pCedulaSocio);
        if (s == null) {
            throw new Exception("No existe socio con la cédula especificada.");
        }
        ArrayList<String> autorizados = new ArrayList<String>();
        autorizados.add(s.darNombre());
        autorizados.addAll(s.darAutorizados());
        return autorizados;
    }

    public void agregarAutorizadoSocio(String pCedulaSocio, String pNombreAutorizado) throws Exception {
        Socio s = buscarSocio(pCedulaSocio);
        if (s == null) {
            throw new Exception("No existe socio con la cédula especificada.");
        }
        s.agregarAutorizado(pNombreAutorizado);
    }

    public void eliminarAutorizadoSocio(String pCedulaSocio, String pNombreAutorizado) throws Exception {
        Socio s = buscarSocio(pCedulaSocio);
        if (s == null) {
            throw new Exception("No existe socio con la cédula especificada.");
        }
        s.eliminarAutorizado(pNombreAutorizado);
    }

    public void registrarConsumo(String pCedulaSocio, String pNombreCliente, String pConcepto, double pValor) throws Exception {
        Socio s = buscarSocio(pCedulaSocio);
        if (s == null) {
            throw new Exception("No existe socio con la cédula especificada.");
        }
        s.registrarConsumo(pNombreCliente, pConcepto, pValor);
    }

    public ArrayList<Factura> darFacturasSocio(String pCedulaSocio) {
        Socio s = buscarSocio(pCedulaSocio);
        return s != null ? s.darFacturas() : new ArrayList<Factura>();
    }

    public void pagarFacturaSocio(String pCedulaSocio, int pFacturaIndice) throws Exception {
        Socio s = buscarSocio(pCedulaSocio);
        if (s == null) {
            throw new Exception("No existe socio con la cédula especificada.");
        }
        s.pagarFactura(pFacturaIndice);
    }

    public void aumentarFondosSocio(String pCedulaSocio, double pValor) throws Exception {
        Socio s = buscarSocio(pCedulaSocio);
        if (s == null) {
            throw new Exception("No existe socio con la cédula especificada.");
        }
        s.aumentarFondos(pValor);
    }

    public String metodo1() {
        return "respuesta1";
    }

    public String metodo2() {
        return "respuesta2";
    }

    public double obtenerTotalConsumosSocio(String pCedula) throws Exception {
        Socio s = buscarSocio(pCedula);
        if (s == null) {
            throw new Exception("No existe socio con la cédula especificada.");
        }

        double total = 0;
        ArrayList<Factura> facturasSocio = s.darFacturas();
        for (Factura f : facturasSocio) {
            total += f.darValor();
        }
        return total;
    }

    public boolean sePuedeEliminarSocio(String pCedula) throws Exception {
        Socio s = buscarSocio(pCedula);

        if (s == null) {
            throw new Exception("Error de Eliminación: No existe un socio con la cédula indicada.");
        }
        if (s.darTipo() == Tipo.VIP) {
            throw new Exception("Error de Eliminación: El socio es de tipo VIP y no puede ser eliminado.");
        }
        if (!s.darFacturas().isEmpty()) {
            throw new Exception("Error de Eliminación: El socio posee facturas pendientes de pago.");
        }
        if (s.darAutorizados().size() > 1) {
            throw new Exception("Error de Eliminación: El socio tiene más de un autorizado registrado.");
        }

        return true;
    }

    public void eliminarSocio(String pCedula) throws Exception {
        Socio s = buscarSocio(pCedula);
        if (s == null) {
            throw new Exception("No existe socio con la cédula especificada.");
        }
        socios.remove(s);
    }
}