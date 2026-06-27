package club;

public class Factura {
    private String concepto;
    private double valor;
    private String nombre;

    public Factura(String pNombre, String pConcepto, double pValor) {
        nombre = pNombre;
        concepto = pConcepto;
        valor = pValor;
    }

    public String darConcepto() { return concepto; }
    public double darValor() { return valor; }
    public String darNombre() { return nombre; }

    public String toString() {
        return concepto + "    $" + valor + "    (" + nombre + ")";
    }
}