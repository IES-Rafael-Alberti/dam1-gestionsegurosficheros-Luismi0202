import Data.RepositorioSegurosFicheros
import Data.mapaSeguros
import Domain.SeguroAuto
import Domain.SeguroHogar
import Domain.SeguroVida
import EnumClasificatorias.TipoAutomovil
import EnumClasificatorias.TipoRiesgo

fun main() {
    val archivo = "GestionSegurosGradle/src/main/kotlin/FicherosTexto/Seguros.txt"

    val repo = RepositorioSegurosFicheros(archivo, mapaSeguros.mapaSeguros)

    val seguroHogar = SeguroHogar(1, "12345678A", 101.0, 150000, 80.0, "Calle Mayor, 12")
    val seguroAuto = SeguroAuto(2, "98765432B", 102.0, "Toyota Corolla Azul", "Gasolina", TipoAutomovil.COCHE, "Todo Riesgo", true, 1)
    val seguroVida = SeguroVida(3, "98765432C", 103.0, "John", TipoRiesgo.ALTO, 200000.0)

    repo.guardarSeguro(seguroHogar)
    repo.guardarSeguro(seguroAuto)
    repo.guardarSeguro(seguroVida)


    val segurosCargados = repo.cargarSeguros()
    println("Seguros cargados: ${segurosCargados.size}")
    segurosCargados.forEach { println(it.tipoSeguro() + ": " + it.serializar()) }
}