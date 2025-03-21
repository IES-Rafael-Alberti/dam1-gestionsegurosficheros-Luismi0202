package Domain

import java.time.LocalDate

open class SeguroHogar : Seguro {
    val metrosCuadrados: Int
    val valorContenido: Double
    val direccion: String
    val anioConstruccion: Int

    init{
        aumentarId()
    }

    constructor(
        dniTitular: String,
        importe: Double,
        metrosCuadrados: Int,
        valorContenido: Double,
        direccion: String,
        anioConstruccion: Int
    ) : this(id, dniTitular, importe, metrosCuadrados, valorContenido, direccion, anioConstruccion)

    internal constructor(
        numPoliza: Int,
        dniTitular: String,
        importe: Double,
        metrosCuadrados: Int,
        valorContenido: Double,
        direccion: String,
        anioConstruccion: Int
    ) : super(numPoliza, dniTitular, importe) {
        this.metrosCuadrados = metrosCuadrados
        this.valorContenido = valorContenido
        this.direccion = direccion
        this.anioConstruccion = anioConstruccion
    }

    init {
        aumentarId()
    }

    override fun calcularImporteAnioSiguiente(interes: Double): Double {
        val anioActual = LocalDate.now().year
        val antiguedad = anioActual - anioConstruccion
        val interesResidual = (antiguedad / 5) * 0.0002
        val interesTotal = interes + interesResidual
        return importe * (1 + interesTotal)
    }

    override fun tipoSeguro(): String {
        return this::class.simpleName.toString()?:"Desconocido"
    }

    override fun serializar(): String {
        return super.serializar() + ";$metrosCuadrados;$valorContenido;$direccion;$anioConstruccion;${tipoSeguro()}"
    }

    override fun toString(): String {
        return "${tipoSeguro()}${super.toString()},metros cuadrados=$metrosCuadrados, " +
                "valor contenido=$valorContenido,direccion=$direccion,año de construccion=$anioConstruccion)"
    }

    companion object {
        protected var id = 100000

        protected fun aumentarId() {
            id++
        }

        fun crearSeguro(datos: List<String>): SeguroHogar {
            try {
                val numPoliza = datos[0].toInt()
                val dniTitular = datos[1]
                val importe = datos[2].toDouble()
                val metrosCuadrados = datos[3].toInt()
                val valorContenido = datos[4].toDouble()
                val direccion = datos[5]
                val anioConstruccion = datos[6].toInt()

                return SeguroHogar(numPoliza, dniTitular, importe, metrosCuadrados, valorContenido, direccion, anioConstruccion)
            } catch (e: NumberFormatException) {
                throw IllegalArgumentException("Error en la conversión de datos: ${e.message}")
            } catch (e: IndexOutOfBoundsException) {
                throw IllegalArgumentException("Datos insuficientes para crear una instancia de SeguroHogar")
            }
        }
    }
}