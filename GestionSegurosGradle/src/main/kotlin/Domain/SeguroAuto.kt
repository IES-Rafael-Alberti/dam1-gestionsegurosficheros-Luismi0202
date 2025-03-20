package Domain

import EnumClasificatorias.TipoAutomovil

class SeguroAuto(
    numPoliza:Int,
    dniTitular:String,
    importe:Double,
    val descripcion: String,
    val combustible: String,
    val tipoAuto: TipoAutomovil,
    val tipoCobertura: String,
    val asistenciaCarretera: Boolean,
    val numPartes: Int
):Seguro(numPoliza,dniTitular,importe) {

    init{
        aumentarId()
    }

    override fun calcularImporteAnioSiguiente(interes: Double): Double {
        TODO("Not yet implemented")
    }

    override fun tipoSeguro(): String {
        return "SeguroAuto"
    }

    override fun serializar(): String {
        return "$id;$dniTitular;$numPoliza;$importe;$descripcion;$combustible;$tipoAuto;$tipoCobertura;$asistenciaCarretera;$numPartes;${tipoSeguro()}"
    }

    companion object{
        protected var id = 400000

        protected fun aumentarId(){
            id++
        }
    }

}