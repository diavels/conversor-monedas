public class formatoConversion {

    private String monedaBase;
    private String monedaCambio;
    private double valorMoneda;
    private String ultimaActualizacion;
    private String clave;



    public formatoConversion(valoresJson formato) {
        this.monedaBase = formato.base_code();
        this.monedaCambio = formato.target_code();
        this.valorMoneda = formato.conversion_rate();
        this.ultimaActualizacion = formato.time_last_update_utc();
    }

    public String getUltimaActualizacion() {
        return ultimaActualizacion;
    }

    public double isValorMoneda() {
        return valorMoneda;
    }

    public String getMonedaCambio() {
        return monedaCambio;
    }

    public String getMonedaBase() {
        return monedaBase;
    }



    @Override
    public String toString() {



        return "(Informacion de la Conversion: " +
               "monedaBase= " + monedaBase +
               ", monedaCambio= " + monedaCambio +
               ", valorMoneda= " + valorMoneda +
               ", ultimaActualizacion= " + ultimaActualizacion + ")";
    }
}
