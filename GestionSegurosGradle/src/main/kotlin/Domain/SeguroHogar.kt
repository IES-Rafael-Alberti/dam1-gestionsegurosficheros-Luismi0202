package Domain

class SeguroHogar(
    numPoliza:Int,
    dniTitular:String,
    importe:Double,
    val metrosCuadrados:Int,
    val valorContenido: Double,
    val direccion: String
): Seguro(numPoliza,dniTitular,importe) {

    init{
        aumentarId()
    }

    override fun calcularImporteAnioSiguiente(interes: Double): Double {
        TODO("Not yet implemented")
    }

    override fun tipoSeguro(): String {
       return "SeguroHogar"
    }

    override fun serializar(): String {
        return "$id;$dniTitular;$numPoliza;$importe;$valorContenido;$metrosCuadrados;$direccion;${tipoSeguro()}"
    }

    companion object{
        protected var id = 100000

        protected fun aumentarId(){
            id++
        }
    }
}