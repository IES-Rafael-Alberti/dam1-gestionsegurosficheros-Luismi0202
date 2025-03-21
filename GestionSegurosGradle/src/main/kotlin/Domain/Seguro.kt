package Domain

import Interfaces.IExportable
import Utils.redondearDosDecimales


abstract class Seguro(
    private val numPoliza: Int,
    private val dniTitular: String,
    protected val importe:Double
): IExportable {

    init{
        require(comprobarDni(dniTitular)){"¡El DNI del titular debe tener un formato correcto!"}
    }

    abstract fun calcularImporteAnioSiguiente(interes: Double):Double

    abstract fun tipoSeguro(): String

    override fun toString(): String {
        return "(numPoliza=$numPoliza,dniTitular=$dniTitular,importe=${importe.redondearDosDecimales()}"
    }

    override fun hashCode(): Int {
        return numPoliza
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Seguro) return false
        return numPoliza == other.numPoliza
    }

    override fun serializar(): String{
        return "$numPoliza;$dniTitular;$importe"
    }

    companion object{
        fun comprobarDni(dni:String): Boolean{
            val regex = Regex("^\\d{8}[A-Z]\$")
            return regex.matches(dni)
        }
    }
}