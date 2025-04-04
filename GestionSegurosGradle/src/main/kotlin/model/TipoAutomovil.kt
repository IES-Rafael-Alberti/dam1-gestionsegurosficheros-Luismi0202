package model

enum class TipoAutomovil(val descripcion:String) {
    COCHE("Coche"),
    MOTO("Moto"),
    CAMION("Camion");

    companion object{
        fun getAuto(valor: String): TipoAutomovil?{
            for(auto in TipoAutomovil.entries){
                if(auto.descripcion.lowercase() == valor.lowercase()){
                    return auto
                }
            }
            return COCHE
        }
    }
}