package model
import model.TipoRiesgo.Companion.interes
import java.time.LocalDate
import java.time.format.DateTimeFormatter

open class SeguroVida : Seguro {
    private val fechaNac: LocalDate
    private val nivelRiesgo: TipoRiesgo
    private val indemnizacion: Double

    init{
        aumentarId()
    }

    constructor(
        dniTitular: String,
        importe: Double,
        fechaNac: String,
        nivelRiesgo: TipoRiesgo,
        indemnizacion: Double
    ) : this(id, dniTitular, importe, fechaNac, nivelRiesgo, indemnizacion)


    internal constructor(
        numPoliza: Int,
        dniTitular: String,
        importe: Double,
        fechaNac: String,
        nivelRiesgo: TipoRiesgo,
        indemnizacion: Double
    ) : super(numPoliza, dniTitular, importe) {
        this.fechaNac = LocalDate.parse(fechaNac, DateTimeFormatter.ISO_LOCAL_DATE)
        this.nivelRiesgo = nivelRiesgo
        this.indemnizacion = indemnizacion
    }

    override fun calcularImporteAnioSiguiente(interes: Double): Double {
        val anioActual = LocalDate.now().year
        val anioNacimiento = fechaNac.year
        val edad = anioActual - anioNacimiento
        val interesResidual = (edad * 0.0005) + nivelRiesgo.interes()
        val interesTotal = interes + interesResidual
        return importe * (1 + interesTotal)
    }

    override fun serializar(): String {
        return super.serializar()+";${fechaNac.format(DateTimeFormatter.ISO_LOCAL_DATE)};$nivelRiesgo;$indemnizacion;${tipoSeguro()}"
    }

    override fun toString(): String {
        return "${super.toString()},fechaNac=${fechaNac.format(DateTimeFormatter.ISO_LOCAL_DATE)}, nivelRiesgo=$nivelRiesgo, indemnizacion=$indemnizacion)"
    }

    companion object {
        private var id = 800000

        private fun aumentarId() {
            id++
        }

        fun crearSeguro(datos: List<String>): SeguroVida {
            try {
                val numPoliza = datos[0].toInt()
                val dniTitular = datos[1]
                val importe = datos[2].toDouble()
                val fechaNac = datos[3]
                val nivelRiesgo = TipoRiesgo.valueOf(datos[4])
                val indemnizacion = datos[5].toDouble()

                return SeguroVida(numPoliza, dniTitular, importe, fechaNac, nivelRiesgo, indemnizacion)
            } catch (e: Exception) {
                throw IllegalArgumentException("Error en la conversión de datos: ${e.message}")
            }
        }
    }
}