object Utils {
    fun Double.redondearDosDecimales(): Double {
        return "%.${2}f".format(this).replace(",", ".").toDouble()
    }
}