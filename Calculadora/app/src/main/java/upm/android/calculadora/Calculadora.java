/*
 * Eduardo Escobar Alberto
 * PROYECTO: Desarrollo de una calculadora con vista científica (HORIZONTAL).
 * CLASE: Clase para la implementación del controlador de la calculadora.
 */
package upm.android.calculadora;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Optional;

public class Calculadora extends AppCompatActivity implements OnClickListener {

    // DECLARACIÓN DE CONSTANTES.
    final static int MAX_VALORES_PORTRAIT = 10;
    final static int MAX_VALORES_LANDSCAPE = 25;
    final static String VALOR_ACTUAL_DISPLAY = "valorActualDisplay";
    final static String VALOR_ACTUAL_MEMORIA = "valorActualMemoria";
    final static boolean ENCENDIDA = true;
    final static boolean APAGADA = false;
    final static int NO_EXCESO_PORTRAIT = 0;
    final static int NO_EXCESO_LANDSCAPE = 1;
    final static int EXCESO_PORTRAIT = 2;
    final static int EXCESO_LANDSCAPE = 2;
    final static String DISPLAY_VACIO = "";
    final static char PUNTO = '.';
    final static String NO_OPERACION = "";
    final static String CERO_INICIAL = "0";
    final static double CIEN_PORCENTAJE = 100;
    final static int UN_OPERANDO = 1;
    final static String NUMERO_0 = "0";
    final static String NUMERO_1 = "1";
    final static String NUMERO_2 = "2";
    final static String NUMERO_3 = "3";
    final static String NUMERO_4 = "4";
    final static String NUMERO_5 = "5";
    final static String NUMERO_6 = "6";
    final static String NUMERO_7 = "7";
    final static String NUMERO_8 = "8";
    final static String NUMERO_9 = "9";
    final static String SUMA = "+";
    final static String RESTA = "-";
    final static String PRODUCTO = "*";
    final static String DIVISION = "÷";
    final static String RESULTADO = "=";
    final static String PORCENTAJE = "%";
    final static String RAIZ_CUADRADA = "√";
    final static String CAMBIO_SIGNO = "+/-";
    final static String POTENCIA_CUADRADO = "x^2";
    final static String POTENCIA_CUBO = "x^3";
    final static String SENO = "sen";
    final static String COSENO = "cos";
    final static String TANGENTE = "tan";
    final static String SECANTE = "sec";
    final static String COSECANTE = "csc";
    final static String COTANGENTE = "cot";
    final static String PUNTO_DECIMAL = ".";
    final static String BORRAR = "C";
    final static String BORRAR_TODO = "AC";
    final static String INSERTAR_MEMORIA = "M+";
    final static String LIMPIAR_MEMORIA = "MC";
    final static String RECUPERAR_MEMORIA = "MR";
    final static String ENCENDER = "ON";
    final static String APAGAR = "OFF";

    // DECLARACIÓN DE ATRIBUTOS
    private TextView display;
    private boolean estadoDisplay;
    private ArrayList<String> operandos;
    private String operacionPulsada;
    private String valorDisplayActual;
    private Memoria memoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculadora);
        setDisplay((TextView)findViewById(R.id.display));
        setEstadoDisplay(ENCENDIDA);
        setOperandos(new ArrayList<String>());
        if (savedInstanceState != null) {
            setValorDisplayActual(savedInstanceState.getString(VALOR_ACTUAL_DISPLAY));
            setMemoria(new Memoria(savedInstanceState.getDouble(VALOR_ACTUAL_MEMORIA)));
        }
        else {
            setMemoria(new Memoria());
            setValorDisplayActual(CERO_INICIAL);
        }
        setOperacionPulsada(NO_OPERACION);
        gestionarEventos();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString(VALOR_ACTUAL_DISPLAY, getValorDisplayActual());
        savedInstanceState.putDouble(VALOR_ACTUAL_MEMORIA, getMemoria().getElementoAlmacenado());
        super.onSaveInstanceState(savedInstanceState);
    }

    /**
     * Método para anadir los eventos a los elementos de la actividad.
     */
    public void gestionarEventos() {
        Optional<Button> botonAuxiliar;
        findViewById(R.id.boton_numero_0).setOnClickListener(this);
        findViewById(R.id.boton_numero_1).setOnClickListener(this);
        findViewById(R.id.boton_numero_2).setOnClickListener(this);
        findViewById(R.id.boton_numero_3).setOnClickListener(this);
        findViewById(R.id.boton_numero_4).setOnClickListener(this);
        findViewById(R.id.boton_numero_5).setOnClickListener(this);
        findViewById(R.id.boton_numero_6).setOnClickListener(this);
        findViewById(R.id.boton_numero_7).setOnClickListener(this);
        findViewById(R.id.boton_numero_8).setOnClickListener(this);
        findViewById(R.id.boton_numero_9).setOnClickListener(this);
        findViewById(R.id.boton_suma).setOnClickListener(this);
        findViewById(R.id.boton_resta).setOnClickListener(this);
        findViewById(R.id.boton_producto).setOnClickListener(this);
        findViewById(R.id.boton_division).setOnClickListener(this);
        findViewById(R.id.boton_resultado).setOnClickListener(this);
        findViewById(R.id.boton_porcentaje).setOnClickListener(this);
        findViewById(R.id.boton_raiz_cuadrada).setOnClickListener(this);
        findViewById(R.id.boton_cambio_signo).setOnClickListener(this);
        findViewById(R.id.boton_punto).setOnClickListener(this);
        findViewById(R.id.boton_borrar).setOnClickListener(this);
        findViewById(R.id.boton_borrar_todo).setOnClickListener(this);
        findViewById(R.id.boton_insertar_memoria).setOnClickListener(this);
        findViewById(R.id.boton_limpiar_memoria).setOnClickListener(this);
        findViewById(R.id.boton_recuperar_memoria).setOnClickListener(this);
        findViewById(R.id.boton_ON).setOnClickListener(this);
        findViewById(R.id.boton_OFF).setOnClickListener(this);
        botonAuxiliar = inicializarBotonPorID(R.id.boton_potencia_cuadrado);
        asignarSetOnClickListener(botonAuxiliar);
        botonAuxiliar = inicializarBotonPorID(R.id.boton_potencia_cubo);
        asignarSetOnClickListener(botonAuxiliar);
        botonAuxiliar = inicializarBotonPorID(R.id.boton_seno);
        asignarSetOnClickListener(botonAuxiliar);
        botonAuxiliar = inicializarBotonPorID(R.id.boton_coseno);
        asignarSetOnClickListener(botonAuxiliar);
        botonAuxiliar = inicializarBotonPorID(R.id.boton_tangente);
        asignarSetOnClickListener(botonAuxiliar);
        botonAuxiliar = inicializarBotonPorID(R.id.boton_secante);
        asignarSetOnClickListener(botonAuxiliar);
        botonAuxiliar = inicializarBotonPorID(R.id.boton_cosecante);
        asignarSetOnClickListener(botonAuxiliar);
        botonAuxiliar = inicializarBotonPorID(R.id.boton_cotangente);
        asignarSetOnClickListener(botonAuxiliar);
    }

    /**
     * Función Optional para inicializar los objetos de la vista que puedan ser null.
     * @param botonID ID del boton a inicializar.
     * @return Valor Button Optional con la posibilidad de ser null.
     */
    private Optional<Button> inicializarBotonPorID(int botonID) {
        Button botonAuxiliar;
        if (findViewById(botonID) != null) {
            botonAuxiliar = findViewById(botonID);
            return Optional.of(botonAuxiliar);
        }
        return Optional.empty();
    }

    /**
     * Función para asignar el evento onClickListener a los botones Optional.
     * @param botonOptionalAsignacion Botón Optional al que asignar el evento.
     */
    private void asignarSetOnClickListener(Optional<Button> botonOptionalAsignacion) {
        if (botonOptionalAsignacion.isPresent()) {
            botonOptionalAsignacion.get().setOnClickListener(this);
        }
    }

    /**
     * Sobreescritura del método onClick para controlar las pulsaciones en los elementos de la actividad.
     * @param view Parámetro view con el elemento que realiza el evento.
     */
    @Override
    public void onClick(View view) {
        Button botonPulsado = findViewById(view.getId());
        ejecutarAcciones(botonPulsado);
    }

    /**
     * Método que determina la acción a ejecutar con la pulsación de cada botón de la calculadora.
     * @param botonAccion Botón pulsado que determina la acción a realizar.
     */
    public void ejecutarAcciones(Button botonAccion) {
        String contenidoBoton = botonAccion.getText().toString();
        String operando;
        switch (contenidoBoton) {
            case NUMERO_0:
            case NUMERO_1:
            case NUMERO_2:
            case NUMERO_3:
            case NUMERO_4:
            case NUMERO_5:
            case NUMERO_6:
            case NUMERO_7:
            case NUMERO_8:
            case NUMERO_9:
            case PUNTO_DECIMAL:
                anadirElementoDisplay(contenidoBoton);
                break;
            case PORCENTAJE:
            case RAIZ_CUADRADA:
            case CAMBIO_SIGNO:
            case POTENCIA_CUADRADO:
            case POTENCIA_CUBO:
            case SENO:
            case COSENO:
            case TANGENTE:
            case SECANTE:
            case COSECANTE:
            case COTANGENTE:
                if (!estaDisplayVacio()) {
                    setOperacionPulsada(contenidoBoton);
                    operando = getDisplay().getText().toString();
                    getOperandos().add(operando);
                    calcular();
                    reiniciarOperandos();
                }
                break;
            case BORRAR:
                setValorDisplayActual(CERO_INICIAL);
                break;
            case BORRAR_TODO:
                borrarTodo();
                break;
            case INSERTAR_MEMORIA:
                getMemoria().insertar(getValorDisplayActual());
                break;
            case LIMPIAR_MEMORIA:
                getMemoria().limpiar();
                break;
            case RECUPERAR_MEMORIA:
                if (getMemoria().isExisteElemento()) {
                    setValorDisplayActual(formatearElemento(getMemoria().recuperar() + ""));
                }
                break;
            case RESULTADO:
                if ((!estaDisplayVacio()) && (getOperandos().size() > 0)) {
                    operando = getDisplay().getText().toString();
                    getOperandos().add(operando);
                    calcular();
                    setOperacionPulsada(NO_OPERACION);
                    reiniciarOperandos();
                }
                break;
            case ENCENDER:
            case APAGAR:
                cambiarEstadoDisplay(contenidoBoton);
                break;
            default:
                if (!estaDisplayVacio()) {
                    setOperacionPulsada(contenidoBoton);
                    operando = getDisplay().getText().toString();
                    getOperandos().add(operando);
                }
                break;
        }
    }

    /**
     * Método para encender o apagar el display de la calculadora.
     */
    public void cambiarEstadoDisplay(String estado) {
        if ((estado.equals(ENCENDER)) && (isEstadoDisplay() == APAGADA)) {
            setEstadoDisplay(ENCENDIDA);
            setValorDisplayActual(CERO_INICIAL);
        }
        else if ((estado.equals(APAGAR)) && (isEstadoDisplay() == ENCENDIDA)) {
            setEstadoDisplay(APAGADA);
            setValorDisplayActual(DISPLAY_VACIO);
            setOperandos(new ArrayList<String>());
            setOperacionPulsada(NO_OPERACION);
        }
    }

    /**
     * Método para eliminar el contenido del display.
     */
    public void limpiarDisplay() {
        setValorDisplayActual(DISPLAY_VACIO);
    }

    /**
     * Función para comprobar si existen elementos en el display de la calculadora.
     * @return True si no existen elementos.
     */
    public boolean estaDisplayVacio() {
        return getDisplay().getText().toString().equals(DISPLAY_VACIO);
    }

    /**
     * Método para borrar y reiniciar la calculadora.
     */
    public void borrarTodo() {
        setValorDisplayActual(CERO_INICIAL);
        setOperandos(new ArrayList<String>());
        setOperacionPulsada(NO_OPERACION);
    }

    /**
     * Método que reinicia los operandos almacenados en la calculadora.
     */
    public void reiniciarOperandos() {
        setOperandos(new ArrayList<String>());
    }

    /**
     * Función para redondear número enteros a un dígito indicado por parámetro.
     * @param numero Numero entero a redondear.
     * @param digitoRedondeo Dígito del redondeo.
     * @return Número entero en formato String, para ser usado en el display, redondeado.
     */
    public String redondearNumeroEntero(String numero, int digitoRedondeo) {
        String elementoAuxiliar;
        double elementoAuxiliarNumerico;
        elementoAuxiliar = numero.substring(0, digitoRedondeo - 1);
        elementoAuxiliar = elementoAuxiliar + PUNTO + numero.substring(digitoRedondeo, digitoRedondeo);
        elementoAuxiliarNumerico = Double.parseDouble(elementoAuxiliar);
        elementoAuxiliarNumerico = Math.ceil(elementoAuxiliarNumerico);
        elementoAuxiliar = elementoAuxiliarNumerico + "";
        return elementoAuxiliar;
    }

    /**
     * Función para redondear números decimales a un dígito indicado por parámetro.
     * @param numero Numero decimal a redondear.
     * @param digitoRedondeo Dígito del redondeo.
     * @return Número decimal en formato String, para ser usado en el display, redondeado.
     */
    public String redondearNumeroDecimal(String numero, int digitoRedondeo) {
        double numeroAuxiliar = Double.parseDouble(numero);
        numeroAuxiliar = Math.round(numeroAuxiliar * Math.pow(10, digitoRedondeo)) / Math.pow(10, digitoRedondeo);
        return numeroAuxiliar + "";
    }

    /**
     * Función que parsea un elemento y lo muestra en la unidad y el formato adecuado.
     * @param elemento Resultado actual para ser comprobado y formateado.
     * @return Resultado formateado.
     */
    public String formatearElemento(String elemento) {
        String elementoFormateado;
        int indicePunto = elemento.indexOf(PUNTO);
        String parteDecimalElemento = elemento.substring(indicePunto + 1);
        if (parteDecimalElemento.equals("0")) {
            elementoFormateado = elemento.substring(0, indicePunto);
        }
        else {
            elementoFormateado = elemento;
        }
        return elementoFormateado;
    }

    /**
     * Funcíon que realiza las operaciones más básicas de la calculadora, necesitando un solo operando.
     * @param tipoOperacion Tipo de operación a realizar.
     * @return Resultado de la operación de tipo String para ser mostrado en el display.
     */
    public String realizarOperacionUnOperando(String tipoOperacion) {
        String resultado = null;
        double operandoNumerico = Double.parseDouble(getOperandos().get(0));
        limpiarDisplay();
        switch (tipoOperacion) {
            case PORCENTAJE:
                resultado = (operandoNumerico / CIEN_PORCENTAJE) + "";
                break;
            case RAIZ_CUADRADA:
                resultado = (Math.sqrt(operandoNumerico)) + "";
                break;
            case CAMBIO_SIGNO:
                resultado = (operandoNumerico * -1) + "";
                break;
            case POTENCIA_CUADRADO:
                resultado = Math.pow(operandoNumerico, 2) + "";
                break;
            case POTENCIA_CUBO:
                resultado = Math.pow(operandoNumerico, 3) + "";
                break;
            case SENO:
                resultado = Math.sin(operandoNumerico) + "";
                break;
            case COSENO:
                resultado = Math.cos(operandoNumerico) + "";
                break;
            case TANGENTE:
                resultado = Math.tan(operandoNumerico) + "";
                break;
            case SECANTE:
                resultado = Math.sinh(operandoNumerico) + "";
                break;
            case COSECANTE:
                resultado = Math.cosh(operandoNumerico) + "";
                break;
            case COTANGENTE:
                resultado = Math.tanh(operandoNumerico) + "";
                break;
        }
       return resultado;
    }

    /**
     * Función que realiza las operaciones más complejas de la calculadora, necesitando dos operandos.
     * @param tipoOperacion Tipo de operación a realizar.
     * @return Resultado de la operación de tipo String para ser mostrado en el display.
     */
    public String realizarOperacionMultiplesOperandos(String tipoOperacion) {
        String resultado;
        double operandoAuxiliar;
        double resultadoAuxiliar;
        resultadoAuxiliar = Double.parseDouble(getOperandos().get(0));
        for (int i = 1; i < getOperandos().size(); i++) {
            operandoAuxiliar = Float.parseFloat(getOperandos().get(i));
            switch (tipoOperacion) {
                case SUMA:
                    resultadoAuxiliar += operandoAuxiliar;
                    break;
                case RESTA:
                    resultadoAuxiliar -= operandoAuxiliar;
                    break;
                case PRODUCTO:
                    resultadoAuxiliar *= operandoAuxiliar;
                    break;
                case DIVISION:
                    resultadoAuxiliar /= operandoAuxiliar;
                    break;
            }
        }
        resultado = Double.toString(resultadoAuxiliar);
        return resultado;
    }

    /**
     * Método para realizar las operaciones de la calculadora.
     */
    public void calcular() {
        String resultado = null;
        if (getOperandos().size() == UN_OPERANDO) {
            resultado = realizarOperacionUnOperando(getOperacionPulsada());
        }
        else if (getOperandos().size() > UN_OPERANDO) {
            resultado = realizarOperacionMultiplesOperandos(getOperacionPulsada());
        }
        limpiarDisplay();
        setValorDisplayActual(formatearElemento(resultado));
    }

    /**
     * Función booleana para comprobar si un elemento entra en los límites de la pantalla.
     * @param elementoComprobar Elemento de tipo String a comprobar.
     * @return Valor que distingue el tipo de exceso de los límites del display.
     */
    public int comprobarLimitesDisplay(String elementoComprobar) {
        final int rotacionPantalla = ((WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getRotation();
        int valorRetorno = -1;
        if ((elementoComprobar.length() < MAX_VALORES_PORTRAIT) &&
                ((rotacionPantalla == Surface.ROTATION_0))) {
            valorRetorno = NO_EXCESO_PORTRAIT;
        }
        else if ((elementoComprobar.length() < MAX_VALORES_LANDSCAPE) &&
                ((rotacionPantalla == Surface.ROTATION_90) ||
                 (rotacionPantalla == Surface.ROTATION_180) ||
                 (rotacionPantalla == Surface.ROTATION_270))) {
            valorRetorno = NO_EXCESO_LANDSCAPE;
        }
        if ((elementoComprobar.length() >= MAX_VALORES_PORTRAIT) &&
                ((rotacionPantalla == Surface.ROTATION_0))) {
            valorRetorno =  EXCESO_PORTRAIT;
        }
        else if ((elementoComprobar.length() >= MAX_VALORES_LANDSCAPE) &&
                ((rotacionPantalla == Surface.ROTATION_90) ||
                 (rotacionPantalla == Surface.ROTATION_180) ||
                 (rotacionPantalla == Surface.ROTATION_270))) {
            valorRetorno = EXCESO_LANDSCAPE;
        }
        return valorRetorno;
    }

    /**
     * Método para añadir un elemento en el display de la calculadora.
     * @param elementoAnadir Elemento de tipo String que queremos añadir en el display.
     */
    public void anadirElementoDisplay(String elementoAnadir) {
        if (getOperacionPulsada() != NO_OPERACION) {
            limpiarDisplay();
        }
        String elementosDisplay = getDisplay().getText().toString();
        if ((elementosDisplay.equals(CERO_INICIAL) && (!elementoAnadir.equals(PUNTO_DECIMAL)))) {
            elementosDisplay = DISPLAY_VACIO;
        }
        if (isEstadoDisplay() == ENCENDIDA) {
            if ((comprobarLimitesDisplay(elementosDisplay) == NO_EXCESO_PORTRAIT) ||
                (comprobarLimitesDisplay(elementosDisplay) == NO_EXCESO_LANDSCAPE)) {
                elementosDisplay += elementoAnadir;
                getDisplay().setText(elementosDisplay);
            }
        }
    }

    /**
     * Método para mostrar elementos en el display de la calculadora.
     * @param elementoMostrar Elemento de tipo String a mostrar.
     */
    public void mostrarElementoDisplay(String elementoMostrar) {
        String elementoAuxiliar = null;
        int numeroDecimales = 0;
        int enteroAuxiliar;
        double doubleAuxiliar;
        if ((comprobarLimitesDisplay(elementoMostrar) == NO_EXCESO_PORTRAIT) ||
            (comprobarLimitesDisplay(elementoMostrar) == NO_EXCESO_LANDSCAPE)) {
            getDisplay().setText(elementoMostrar);
        }
        else {
            int indicePunto = elementoMostrar.indexOf(PUNTO);
            if (indicePunto == -1) { // El valor del display no es un valor decimal.
                if (comprobarLimitesDisplay(elementoMostrar) == EXCESO_PORTRAIT) {
                    elementoAuxiliar = redondearNumeroEntero(elementoMostrar, MAX_VALORES_PORTRAIT);
                }
                else if (comprobarLimitesDisplay(elementoMostrar) == EXCESO_LANDSCAPE) {
                    elementoAuxiliar = redondearNumeroEntero(elementoMostrar, MAX_VALORES_LANDSCAPE);
                }
            }
            else {
                if (comprobarLimitesDisplay(elementoMostrar) == EXCESO_PORTRAIT) {
                    numeroDecimales = MAX_VALORES_PORTRAIT - (indicePunto + 1);
                }
                else if (comprobarLimitesDisplay(elementoMostrar) == EXCESO_LANDSCAPE) {
                    numeroDecimales = MAX_VALORES_LANDSCAPE - (indicePunto + 1);
                }
                elementoAuxiliar = "0" + PUNTO + elementoMostrar.substring(indicePunto + 1);
                enteroAuxiliar = (int)(Double.parseDouble(elementoMostrar) - Double.parseDouble(elementoAuxiliar));
                elementoAuxiliar = redondearNumeroDecimal(elementoAuxiliar, numeroDecimales);
                doubleAuxiliar = enteroAuxiliar + Double.parseDouble(elementoAuxiliar);
                elementoAuxiliar = doubleAuxiliar + "";
            }
            getDisplay().setText(elementoAuxiliar);
        }
    }

    public TextView getDisplay() {
        return display;
    }

    public void setDisplay(TextView display) {
        this.display = display;
    }

    public boolean isEstadoDisplay() {
        return estadoDisplay;
    }

    public void setEstadoDisplay(boolean estadoDisplay) {
        this.estadoDisplay = estadoDisplay;
    }

    public ArrayList<String> getOperandos() {
        return operandos;
    }

    public void setOperandos(ArrayList<String> operandos) {
        this.operandos = operandos;
    }

    public String getOperacionPulsada() {
        return operacionPulsada;
    }

    public void setOperacionPulsada(String operacionPulsada) {
        this.operacionPulsada = operacionPulsada;
    }

    public String getValorDisplayActual() {
        return valorDisplayActual;
    }

    public void setValorDisplayActual(String valorDisplayActual) {
        this.valorDisplayActual = valorDisplayActual;
        mostrarElementoDisplay(valorDisplayActual);
    }

    public Memoria getMemoria() {
        return memoria;
    }

    public void setMemoria(Memoria memoria) {
        this.memoria = memoria;
    }
}
