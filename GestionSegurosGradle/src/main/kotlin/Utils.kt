import Domain.Seguro
import Domain.SeguroAuto
import Domain.SeguroHogar
import Domain.SeguroVida
import Domain.Usuario
import java.io.File
import at.favre.lib.crypto.bcrypt.BCrypt

object Utils {
    fun Double.redondearDosDecimales(): Double {
        return "%.${2}f".format(this).replace(",", ".").toDouble()
    }

    fun deserializarSeguro(serializado: String): Seguro {
        val partes = serializado.split(";")

        return when (partes.last()) {
            "SeguroHogar" -> SeguroHogar.crearSeguro(partes)
            "SeguroVida" -> SeguroVida.crearSeguro(partes)
            "SeguroAuto" -> SeguroAuto.crearSeguro(partes)
            else -> throw IllegalArgumentException("Tipo de seguro desconocido")
        }
    }

    /**
     * Genera un hash seguro de la clave utilizando el algoritmo BCrypt.
     *
     * BCrypt es un algoritmo de hashing adaptativo que permite configurar un nivel de seguridad (coste computacional),
     * lo que lo hace ideal para almacenar contraseñas de forma segura.
     *
     * @param clave La contraseña en texto plano que se va a encriptar.
     * @param nivelSeguridad El factor de coste utilizado en el algoritmo BCrypt. Valores más altos aumentan la seguridad
     * pero también el tiempo de procesamiento. El valor predeterminado es `12`, que se considera seguro para la mayoría
     * de los casos.
     * @return El hash de la clave en formato String, que puede ser almacenado de forma segura.
     */
    fun encriptarClave(clave: String, nivelSeguridad: Int = 12): String {
        return BCrypt.withDefaults().hashToString(nivelSeguridad, clave.toCharArray())
    }

    fun deserializarUsuario(serializado: String): Usuario? {
        val partes = serializado.split(";")
        return if (partes.size >= 3) {

            Usuario.crearUsuario(partes,false)
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