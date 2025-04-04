package model

enum class Cobertura(val descripcion:String) {
    TERCEROS("Terceros"),
    TERCEROS_AMPLIADO("Terceros ampliado"),
    FRANQUICIA_200("Franquicia 200"),
    FRANQUICIA_300("Franquicia 300"),
    FRANQUICIA_400("Franquicia 400"),
    FRANQUICIA_500("Franquicia 500"),
    TODO_RIESGO("Todo Riesgo");

    companion object{
        fun getCobertura(valor: String): Cobertura{
            for(cobertura in Cobertura.entries){
                if(cobertura.descripcion.lowercase() == valor.lowercase()){
                    return cobertura
                }
            }
            return TERCEROS
        }
    }
}