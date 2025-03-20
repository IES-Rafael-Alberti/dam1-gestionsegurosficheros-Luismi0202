package Domain


abstract class Seguro(
    val numPoliza: Int,
    val dniTitular: String,
    protected val importe:Double
) {
    init{
        require(comprobarDni(dniTitular)){"¡El DNI del titular debe tener un formato correcto!"}
    }

    abstract fun calcularImporteAnioSiguiente(interes: Double):Double
    abstract fun tipoSeguro(): String
    abstract fun serializar(): String

    companion object{
        fun comprobarDni(dni:String): Boolean{
            val regex = Regex("^\\d{8}[A-Z]\$")
            return regex.matches(dni)
        }
    }
}