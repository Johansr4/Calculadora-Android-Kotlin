package com.agenciacristal.calculadora

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.text.DecimalFormat
import kotlin.math.pow


class MainActivity : AppCompatActivity() {
    var numero1: Double = 0.0
    var operacionRealizada: Boolean = false
    var nuevoNumero: Boolean = false
    lateinit var txtRes: TextView
    lateinit var txtRes2: TextView
    var operacionActual: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtRes = findViewById(R.id.tvTemp)
        txtRes2 = findViewById(R.id.tvResult)

        val btnBorrar: Button = findViewById(R.id.buttonPrueba4)
        val btnBorradoTotal: Button = findViewById(R.id.buttonPrueba3)
        val btnIgual: Button = findViewById(R.id.button21)

        btnIgual.setOnClickListener {
            realizarOperacion()
        }

        btnBorrar.setOnClickListener {
            resetearCalculadora()
        }
        btnBorradoTotal.setOnClickListener {
            resetearCalculadora2()
        }
    }

    fun clicOperacion(view: View) {
        val operador = when (view.id) {
            R.id.button16 -> "x"
            R.id.button20 -> "-"
            R.id.button25 -> "+"
            R.id.buttonDividir -> "/"
            R.id.buttonPruebaImagen2 -> "^"
            else -> ""
        }

        if (txtRes.text.isNotEmpty() && !nuevoNumero) {
            if (operador == "/" && txtRes.text.toString().endsWith("/")) {
                return
            }
            operacionActual = operador
            txtRes.append(operador)
            txtRes2.append(operador)
            nuevoNumero = true
            operacionRealizada = false
        }
    }


    fun presionarNumero(view: View) {
        val numeroPresionado = when (view.id) {
            R.id.button10 -> "7"
            R.id.button14 -> "8"
            R.id.button15 -> "9"
            R.id.button17 -> "4"
            R.id.button18 -> "5"
            R.id.button19 -> "6"
            R.id.button22 -> "1"
            R.id.button23 -> "2"
            R.id.button24 -> "3"
            R.id.button27 -> "0"
            R.id.button26 -> "."
            else -> ""
        }

        if (numeroPresionado == "." && txtRes.text.contains(".")) {
            return
        }

        if (view.id == R.id.buttonPruebaImagen2) {
            // Botón de potencia
            operacionActual = "^"
            txtRes.append("^")
            txtRes2.append("^")
            nuevoNumero = true
            operacionRealizada = false
        }

        txtRes.append(numeroPresionado)
        txtRes2.append(numeroPresionado)
        operacionRealizada = false
    }

    fun calcularPotencia(base: Double, exponente: Double): Double {
        return base.pow(exponente)
    }


    private fun resetearCalculadora() {
        txtRes2.text = ""
        numero1 = 0.0
        operacionRealizada = false
        nuevoNumero = false
        operacionActual = ""
    }

    private fun resetearCalculadora2() {
        txtRes.text = ""
        txtRes2.text = ""
        numero1 = 0.0
        operacionRealizada = false
        nuevoNumero = false
        operacionActual = ""
    }

    private fun realizarOperacion() {
        val expresion = txtRes.text.toString()

        if (expresion.isNotEmpty()) {
            val expresionSplit = expresion.split(Regex("(?<=[-+x/^])|(?=[-+x/^])"))

            var resultado: Double? = expresionSplit[0].toDoubleOrNull()

            if (expresionSplit.size >= 2) {
                for (i in 1 until expresionSplit.size step 2) {
                    val operador = expresionSplit[i]
                    val num = expresionSplit[i + 1].toDoubleOrNull()

                    if (resultado != null && num != null) {
                        when (operador) {
                            "+" -> resultado += num
                            "-" -> resultado -= num
                            "x" -> resultado *= num
                            "/" -> {
                                if (num != 0.0) {
                                    resultado /= num
                                } else {
                                    txtRes.text = "Error: División por cero"
                                    return
                                }
                            }
                            "^" -> {
                                resultado = Math.pow(resultado, num)
                            }
                            else -> {
                            }
                        }
                    }
                }

                val resultadoFormateado = if (resultado != null && resultado % 1.0 == 0.0) {
                    resultado.toInt().toString()
                } else {
                    resultado.toString()
                }

                txtRes2.text = resultadoFormateado
            }
        }
        operacionRealizada = false
        nuevoNumero = false
    }




    fun calcularRaiz(view: View) {
        val expresion = txtRes.text.toString()

        if (expresion.isNotEmpty()) {
            val num = expresion.toDouble()

            if (num >= 0) {
                val raiz = Math.sqrt(num)
                mostrarResultado(raiz)
                nuevoNumero = true
                operacionRealizada = false
            } else {
                mostrarError("Error: Número negativo")
            }
        }
    }

    private fun mostrarResultado(resultado: Double) {
        val resultadoFormateado = if (resultado % 1.0 == 0.0) {
            resultado.toInt().toString()
        } else {
            resultado.toString()
        }

        txtRes.text = resultadoFormateado
        txtRes2.text = "√$resultadoFormateado"
    }

    private fun mostrarError(mensaje: String) {
        txtRes.text = mensaje
    }

}
