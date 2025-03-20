package Data

import Domain.Seguro
import Domain.SeguroAuto
import Domain.SeguroHogar
import Domain.SeguroVida
import EnumClasificatorias.TipoAutomovil
import EnumClasificatorias.TipoRiesgo

class mapaSeguros(){

    companion object {
        val mapaSeguros: Map<String, (List<String>) -> Seguro> = mapOf(
            "SeguroHogar" to { datos ->
                SeguroHogar(
                    datos[0].toInt(), datos[1], datos[2].toDouble(), datos[3].toInt(),
                    datos[4].toDouble(), datos[5]
                )
            },
            "SeguroAuto" to { datos ->
                SeguroAuto(
                    datos[0].toInt(), datos[1], datos[2].toDouble(), datos[3],
                    datos[4], TipoAutomovil.valueOf(datos[5].uppercase()),
                    datos[6], datos[7].toBoolean(), datos[8].toInt()
                )
            },
            "SeguroVida" to { datos ->
                SeguroVida(
                    datos[0].toInt(), datos[1], datos[2].toDouble(), datos[3],
                    TipoRiesgo.valueOf(datos[4].uppercase()), datos[5].toDouble()
                )
            }
        )
    }
}
