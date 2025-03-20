package Domain

import EnumClasificatorias.TipoRiesgo

class SeguroVida(
    numPoliza: Int,
    dniTitular: String,
    importe: Double,
    val fechaNac:String,
    val nivelRiesgo:TipoRiesgo,
    val indemnizacion:Double
):Seguro(numPoliza,dniTitular,importe) {

    init {
        aumentarId()
    }

    override fun calcularImporteAnioSiguiente(interes: Double): Double {
        TODO("Not yet implemented")
    }

    override fun tipoSeguro(): String {
        return "SeguroVida"
    }

    override fun serializar(): String {
        return "$id;$dniTitular;$numPoliza;$importe;$fechaNac;$nivelRiesgo;$indemnizacion;${tipoSeguro()}"
    }

    companion object{
        protected var id = 800000

        protected fun aumentarId(){
            id++
        }
    }
}