package EnumClasificatorias

enum class TipoPerfil(val descripcion:String) {
    ADMIN("Admin"),
    GESTION("Gestion"),
    CONSULTA("Consulta");

    companion object{
        fun getPerfil(valor: String): TipoPerfil?{
            for(perfil in TipoPerfil.entries){
                if(perfil.descripcion.lowercase() == valor.lowercase()){
                    return perfil
                }
            }
            return null
        }
    }
}