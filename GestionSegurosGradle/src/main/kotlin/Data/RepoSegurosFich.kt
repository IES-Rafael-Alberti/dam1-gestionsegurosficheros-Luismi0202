package Data

import Domain.Seguro
import java.io.File

class RepoSegurosFich : IRepoSeguros {
    private val filePath = "GestionSegurosGradle/src/main/kotlin/FicherosTexto/Seguros.txt"

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