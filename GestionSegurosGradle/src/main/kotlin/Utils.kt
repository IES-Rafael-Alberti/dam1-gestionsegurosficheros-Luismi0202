
import at.favre.lib.crypto.bcrypt.BCrypt
import model.*
import java.io.File

object Utils {
    fun Double.redondearDosDecimales(): Double {
        return "%.${2}f".format(this).replace(",", ".").toDouble()
    }

    fun encriptarClave(clave: String, nivelSeguridad: Int = 12): String {
        return BCrypt.withDefaults().hashToString(nivelSeguridad, clave.toCharArray())
    }

    fun verificarClave(claveIngresada: String, hashAlmacenado: String): Boolean {
        return BCrypt.verifyer().verify(claveIngresada.toCharArray(), hashAlmacenado).verified
    }

    fun deserializarSeguro(serializado: String): Seguro? {
        val partes = serializado.split(";")

        return when (partes.last()) {
            "SeguroHogar" -> SeguroHogar.crearSeguro(partes)
            "SeguroVida" -> SeguroVida.crearSeguro(partes)
            "SeguroAuto" -> SeguroAuto.crearSeguro(partes)
            else -> null
        }
    }


    fun deserializarUsuario(serializado: String): Usuario? {
        val partes = serializado.split(";")
        return if (partes.size >= 3) {

            Usuario.crearUsuario(partes)
        } else {
            println("¡Error! Datos insuficientes para deserializar el usuario")
            null
        }
    }

    fun leerArchivo(ruta: String): List<String> {
        return try {
            File(ruta).readLines()
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun leerSeguros(ruta: String, mapaSeguros: Map<String, (List<String>) -> Seguro>): List<Seguro> {
        val lineas = leerArchivo(ruta)
        return lineas.mapNotNull { linea ->
            val datos = linea.split(";")
            val tipo = datos.last()
            val creadorSeguro = mapaSeguros[tipo]
            creadorSeguro?.invoke(datos)
        }
    }

}