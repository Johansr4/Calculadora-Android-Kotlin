package com.johan.calculadorapro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import kotlin.math.pow


class MainActivity : AppCompatActivity() {

    var primerNum: Double = 0.0
    var realizada: Boolean = false
    var otroNumero: Boolean = false
    lateinit var txtTemp: TextView
    lateinit var txtRes: TextView
    var operacionActual: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtTemp = findViewById(R.id.tvTemp)
        txtRes = findViewById(R.id.tvResult)

        val btnBorrar: Button = findViewById(R.id.buttonPrueba4)
        val btnBorradoTotal: Button = findViewById(R.id.buttonPrueba3)
        val btnIgual: Button = findViewById(R.id.button21)

        btnIgual.setOnClickListener {
            calcularOperacion()
        }

        btnBorrar.setOnClickListener {
            resetearCalc()
        }
        btnBorradoTotal.setOnClickListener {
            resetearCalc2()
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

        if (txtTemp.text.isNotEmpty() && !otroNumero) {
            if (operador == "/" && txtTemp.text.toString().endsWith("/")) {
                return
            }
            operacionActual = operador
            txtTemp.append(operador)
            txtRes.append(operador)
            otroNumero = true
            realizada = false
        }
    }


    fun seleccionarNum(view: View) {
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

        if (numeroPresionado == "." && txtTemp.text.contains(".")) {
            return
        }

        if (view.id == R.id.buttonPruebaImagen2) {
            // Botón de potencia
            operacionActual = "^"
            txtTemp.append("^")
            txtRes.append("^")
            otroNumero = true
            realizada = false
        }

        txtTemp.append(numeroPresionado)
        txtRes.append(numeroPresionado)
        realizada = false
    }


    fun calcularPotencia(base: Double, exponente: Double): Double {
        return base.pow(exponente)
    }


    private fun resetearCalc() {
        txtRes.text = ""
        primerNum = 0.0
        realizada = false
        otroNumero = false
        operacionActual = ""
    }

    private fun resetearCalc2() {
        txtTemp.text = ""
        txtRes.text = ""
        primerNum = 0.0
        realizada = false
        otroNumero = false
        operacionActual = ""
    }

    private fun calcularOperacion() {
        val expresion = txtTemp.text.toString()

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
                                    txtTemp.text = "Error de división por cero"
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

                txtRes.text = resultadoFormateado
            }
        }
        realizada = false
        otroNumero = false
    }



    fun calcularRaiz(view: View) {
        val expresion = txtTemp.text.toString()

        if (expresion.isNotEmpty()) {
            val num = expresion.toDouble()

            if (num >= 0) {
                val raiz = Math.sqrt(num)
                mostrarResultado(raiz)
                otroNumero = true
                realizada = false
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

        txtTemp.text = resultadoFormateado
        txtRes.text = "√$resultadoFormateado"
    }

    private fun mostrarError(mensaje: String) {
        txtTemp.text = mensaje
    }

}
