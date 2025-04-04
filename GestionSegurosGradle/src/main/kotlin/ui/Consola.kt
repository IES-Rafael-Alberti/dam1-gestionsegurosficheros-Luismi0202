package ui

import org.jline.reader.EndOfFileException
import org.jline.reader.LineReaderBuilder
import org.jline.reader.UserInterruptException
import org.jline.terminal.TerminalBuilder

class Consola: IEntradaSalida {

    override fun mostrar(msj: String, salto: Boolean, pausa: Boolean) {
        if (salto) {
            println(msj)
        } else {
            print(msj)
        }
        if (pausa) {
            pausar("Presiona Enter para continuar...")
        }
    }

    override fun mostrarError(msj: String, pausa: Boolean) {
        val mensaje = if (msj.startsWith("ERROR - ")) msj else "ERROR - $msj"
        mostrar(mensaje, true, pausa)
    }

    override fun pedirInfo(msj: String): String {
        if (msj.isNotEmpty()) {
            mostrar(msj, false)
        }
        return readLine()?.trim() ?: ""
    }

    override fun pedirInfo(
        msj: String,
        error: String,
        debeCumplir: (String) -> Boolean
    ): String {
        var seguir = true
        var input = ""

        while (seguir) {
            input = pedirInfo(msj)
            if (debeCumplir(input)) {
                seguir = false
            } else {
                mostrarError(error)
            }
        }
        return input
    }

    override fun pedirDouble(
        prompt: String,
        error: String,
        errorConv: String,
        debeCumplir: (Double) -> Boolean
    ): Double {
        var seguir = true
        var number: Double? = null

        while (seguir) {
            val input = pedirInfo(prompt)
            number = input.replace(',', '.').toDoubleOrNull()
            if (number == null) {
                mostrarError(errorConv)
            } else if (!debeCumplir(number)) {
                mostrarError(error)
            } else {
                seguir = false
            }
        }
        return number!!
    }

    override fun pedirEntero(
        prompt: String,
        error: String,
        errorConv: String,
        debeCumplir: (Int) -> Boolean
    ): Int {
        var seguir = true
        var number: Int? = null

        while (seguir) {
            val input = pedirInfo(prompt)
            number = input.toIntOrNull()
            if (number == null) {
                mostrarError(errorConv)
            } else if (!debeCumplir(number)) {
                mostrarError(error)
            } else {
                seguir = false
            }
        }
        return number!!
    }

    override fun pedirInfoOculta(prompt: String): String {
        return try {
            val terminal = TerminalBuilder.builder()
                .dumb(true) // Para entornos no interactivos como IDEs
                .build()

            val reader = LineReaderBuilder.builder()
                .terminal(terminal)
                .build()

            reader.readLine(prompt, '*') // Oculta la contraseña con '*'
        } catch (e: UserInterruptException) {
            mostrarError("Entrada cancelada por el usuario (Ctrl + C).", pausa = false)
            ""
        } catch (e: EndOfFileException) {
            mostrarError("Se alcanzó el final del archivo (EOF ó Ctrl+D).", pausa = false)
            ""
        } catch (e: Exception) {
            mostrarError("Problema al leer la contraseña: ${e.message}", pausa = false)
            ""
        }
    }

    override fun pausar(msj: String) {
        pedirInfo(msj)
    }

    override fun limpiarPantalla(numSaltos: Int) {
        if (System.console() != null) {
            mostrar("\u001b[H\u001b[2J", false)
            System.out.flush()
        } else {
            repeat(numSaltos) {
                mostrar("")
            }
        }
    }

    override fun preguntar(mensaje: String): Boolean {
        var seguir = true
        var respuesta = ""

        while (seguir) {
            respuesta = pedirInfo("$mensaje (s/n): ").lowercase()
            when (respuesta) {
                "s" -> seguir = false
                "n" -> seguir = false
                else -> mostrarError("Respuesta no válida. Por favor, ingresa 's' o 'n'.")
            }
        }
        return respuesta == "s"
    }
}