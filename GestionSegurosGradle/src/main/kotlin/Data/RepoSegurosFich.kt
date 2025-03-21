package Data

import Domain.Seguro
import Domain.SeguroAuto
import Domain.SeguroHogar
import Domain.SeguroVida
import EnumClasificatorias.TipoAutomovil
import EnumClasificatorias.TipoRiesgo
import java.io.File

class RepoSegurosFich : IRepoSeguros {
    private val filePath = "GestionSegurosGradle/src/main/kotlin/FicherosTexto/Seguros.txt"

    val mapaSeguros: Map<String, (List<String>) -> Seguro> = mapOf(
        "SeguroHogar" to { datos -> SeguroHogar(0, datos[0], datos[1].toDouble(), datos[2].toInt(), datos[3].toDouble(), datos[4], datos[5].toInt()) },
        "SeguroAuto" to { datos -> SeguroAuto(0, datos[0], datos[1].toDouble(), datos[2], datos[3], TipoAutomovil.valueOf(datos[4]), datos[5], datos[6].toBoolean(), datos[7].toInt()) },
        "SeguroVida" to { datos -> SeguroVida(0, datos[0], datos[1].toDouble(), datos[2], TipoRiesgo.valueOf(datos[3]), datos[4].toDouble()) }
    )

    val seguros = Utils.leerSeguros(filePath,mapaSeguros)

    override fun agregar(seguro: Seguro): Boolean {
        return try {
            File(filePath).appendText(seguro.serializar() + "\n")
            true
        } catch (e: Exception) {
            false
        }
    }

    override fun buscar(numPoliza: Int): Seguro? {
        return obtenerTodos().find { it.getNumPoliza() == numPoliza }
    }

    override fun eliminar(seguro: Seguro): Boolean {
        val seguros = obtenerTodos().toMutableList()
        val result = seguros.removeIf { it.getNumPoliza() == seguro.getNumPoliza() }
        if (result) {
            guardarSeguros(seguros)
        }
        return result
    }

    override fun eliminar(numPoliza: Int): Boolean {
        val seguros = obtenerTodos().toMutableList()
        val result = seguros.removeIf { it.getNumPoliza() == numPoliza }
        if (result) {
            guardarSeguros(seguros)
        }
        return result
    }

    override fun obtenerTodos(): List<Seguro> {
        return try {
            File(filePath).readLines().map { Utils.deserializarSeguro(it) }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override fun obtener(tipoSeguro: String): List<Seguro> {
        return obtenerTodos().filter { it.tipoSeguro() == tipoSeguro }
    }

    private fun guardarSeguros(seguros: List<Seguro>) {
        File(filePath).writeText(seguros.joinToString("\n") { it.serializar() })
    }
}