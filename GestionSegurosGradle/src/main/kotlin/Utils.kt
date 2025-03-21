import Domain.Seguro
import Domain.SeguroAuto
import Domain.SeguroHogar
import Domain.SeguroVida
import Domain.Usuario

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


    fun deserializarUsuario(serializado: String): Usuario {
            val partes = serializado.split(";")
            return Usuario.crearUsuario(partes)
    }
}