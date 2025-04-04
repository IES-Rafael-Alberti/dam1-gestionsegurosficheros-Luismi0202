package model

enum class TipoRiesgo(val descripcion:String) {
    BAJO("Bajo"),
    MEDIO("Medio"),
    ALTO("Alto");


    companion object{
        private val intereses = mutableListOf(2.0,5.0,10.0)

        fun getRiesgo(valor: String): TipoRiesgo?{
            for(riesgo in TipoRiesgo.entries){
                if(riesgo.descripcion.lowercase() == valor.lowercase()){
                    return riesgo
                }
            }
            return MEDIO
        }

        fun TipoRiesgo.interes():Double{
            return when(this){
                BAJO-> intereses[0]
                MEDIO-> intereses[1]
                ALTO-> intereses[2]
            }
        }
    }
}