import Domain.Seguro
import Domain.SeguroAuto
import Domain.SeguroHogar
import Domain.SeguroVida
import Domain.Usuario
import java.io.File

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