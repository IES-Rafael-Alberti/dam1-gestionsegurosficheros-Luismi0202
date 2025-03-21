import Domain.Seguro
import Domain.SeguroAuto
import Domain.SeguroHogar
import Domain.SeguroVida
import Domain.Usuario
import EnumClasificatorias.TipoAutomovil
import EnumClasificatorias.TipoPerfil
import EnumClasificatorias.TipoRiesgo

object Utils {
    fun Double.redondearDosDecimales(): Double {
        return "%.${2}f".format(this).replace(",", ".").toDouble()
    }

    fun deserializarSeguro(serializado: String): Seguro {
        val partes = serializado.split(";")
        val numPoliza = partes[0].toInt()
        val dniTitular = partes[1]
        val importe = partes[2].toDouble()

        return when (partes[6]) {
            "SeguroHogar" -> SeguroHogar.crearSeguro(
                listOf(
                    partes[0],
                    partes[1],
                    partes[2],
                    partes[3],
                    partes[4],
                    partes[5],
                    partes[6]
                )
            )
            "SeguroVida" -> SeguroVida.crearSeguro(
                listOf(
                    partes[0],
                    partes[1],
                    partes[2],
                    partes[3],
                    partes[4],
                    partes[5]
                )
            )
            "SeguroAuto" -> SeguroAuto.crearSeguro(
                listOf(
                    partes[0],
                    partes[1],
                    partes[2],
                    partes[3],
                    partes[4],
                    partes[5],
                    partes[6],
                    partes[7],
                    partes[8]
                )
            )
            else -> throw IllegalArgumentException("Tipo de seguro desconocido")
        }
    }


    fun deserializarUsuario(serializado: String): Usuario {
        val partes = serializado.split(";")
        val nombre = partes[0]
        val contrasenia = partes[1]
        val perfil = TipoPerfil.valueOf(partes[2])
        return Usuario(nombre,contrasenia,perfil)
    }
}