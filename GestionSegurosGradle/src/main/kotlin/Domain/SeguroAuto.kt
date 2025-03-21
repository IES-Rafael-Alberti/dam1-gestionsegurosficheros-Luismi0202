package Domain

import EnumClasificatorias.TipoAutomovil

open class SeguroAuto : Seguro {
    private val descripcion: String
    private val combustible: String
    private val tipoAuto: TipoAutomovil
    private val tipoCobertura: String
    private val asistenciaCarretera: Boolean
    private val numPartes: Int

    constructor(
        dniTitular: String,
        importe: Double,
        descripcion: String,
        combustible: String,
        tipoAuto: TipoAutomovil,
        tipoCobertura: String,
        asistenciaCarretera: Boolean,
        numPartes: Int
    ) : this(id, dniTitular, importe, descripcion, combustible, tipoAuto, tipoCobertura, asistenciaCarretera, numPartes) {
        aumentarId()
    }


    internal constructor(
        numPoliza: Int,
        dniTitular: String,
        importe: Double,
        descripcion: String,
        combustible: String,
        tipoAuto: TipoAutomovil,
        tipoCobertura: String,
        asistenciaCarretera: Boolean,
        numPartes: Int
    ) : super(numPoliza, dniTitular, importe) {
        this.descripcion = descripcion
        this.combustible = combustible
        this.tipoAuto = tipoAuto
        this.tipoCobertura = tipoCobertura
        this.asistenciaCarretera = asistenciaCarretera
        this.numPartes = numPartes
    }

    override fun calcularImporteAnioSiguiente(interes: Double): Double {
        val interesResidual = numPartes * 0.02
        val interesTotal = interes + interesResidual
        return importe * (1 + interesTotal)
    }

    override fun tipoSeguro(): String {
        return this::class.simpleName ?: "Desconocido"
    }

    override fun serializar(): String {
        return super.serializar() + ";$descripcion;$combustible;$tipoAuto;$tipoCobertura;$asistenciaCarretera;$numPartes;${tipoSeguro()}"
    }

    override fun toString(): String {
        return "SeguroAuto(${super.toString()}, descripcion=$descripcion, combustible=$combustible, tipoAuto=$tipoAuto, tipoCobertura=$tipoCobertura, asistenciaCarretera=$asistenciaCarretera, numPartes=$numPartes)"
    }

    companion object {
        private var id = 400000

        private fun aumentarId() {
            id++
        }

        fun crearSeguro(datos: List<String>): SeguroAuto {
            try {
                val numPoliza = datos[0].toInt()
                val dniTitular = datos[1]
                val importe = datos[2].toDouble()
                val descripcion = datos[3]
                val combustible = datos[4]
                val tipoAuto = TipoAutomovil.valueOf(datos[5])
                val tipoCobertura = datos[6]
                val asistenciaCarretera = datos[7].toBoolean()
                val numPartes = datos[8].toInt()

                return SeguroAuto(numPoliza, dniTitular, importe, descripcion, combustible, tipoAuto, tipoCobertura, asistenciaCarretera, numPartes)
            } catch (e: Exception) {
                throw IllegalArgumentException("Error en la conversión de datos: ${e.message}")
            }
        }
    }
}