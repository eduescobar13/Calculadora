/*
 * Eduardo Escobar Alberto
 * PROYECTO: Desarrollo de una calculadora con vista científica (HORIZONTAL).
 * CLASE: Clase para la implementación de la memoria de la calculadora.
 */
package upm.android.calculadora;

public class Memoria {

    // DECLARACIÓN DE CONSTANTES.
    final static double ELEMENTO_POR_DEFECTO = 0;

    // DECLARACIÓN DE ATRIBUTOS.
    private double elementoAlmacenado;
    private boolean existeElemento;

    public Memoria() {
        setElementoAlmacenado(ELEMENTO_POR_DEFECTO);
        setExisteElemento(false);
    }

    public Memoria(double elementoAlmacenado) {
        setElementoAlmacenado(elementoAlmacenado);
        setExisteElemento(true);
    }

    /**
     * Método para insertar un elemento en la memoria.
     * @param elementoInsertar Elemento de tipo double a insertar.
     */
    public void insertar(double elementoInsertar) {
        setElementoAlmacenado(elementoInsertar);
        setExisteElemento(true);
    }

    /**
     * Método para insertar un elemento en la memoria.
     * @param elementoInsertar Elemento de tipo String a insertar.
     */
    public void insertar(String elementoInsertar) {
        setElementoAlmacenado(Double.valueOf(elementoInsertar));
        setExisteElemento(true);
    }

    /**
     * Método para limpiar la memoria y borrar los elementos almacenados.
     */
    public void limpiar() {
        setElementoAlmacenado(ELEMENTO_POR_DEFECTO);
        setExisteElemento(false);
    }

    /**
     * Función que devuelve el elemento almacenado en la memoria.
     * @return Elemento de tipo double almacenado.
     */
    public double recuperar() {
        return getElementoAlmacenado();
    }

    public Double getElementoAlmacenado() {
        return elementoAlmacenado;
    }

    public void setElementoAlmacenado(double elementoAlmacenado) {
        this.elementoAlmacenado = elementoAlmacenado;
    }

    public boolean isExisteElemento() {
        return existeElemento;
    }

    public void setExisteElemento(boolean existeElemento) {
        this.existeElemento = existeElemento;
    }
}
