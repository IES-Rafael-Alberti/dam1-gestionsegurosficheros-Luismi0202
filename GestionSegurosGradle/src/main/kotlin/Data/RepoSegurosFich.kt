package data


import model.*
import java.io.File

class RepoSegurosFich : RepoSegurosMem(),IRepoSeguros,ICargarSegurosIniciales  {
    private val filePath = "GestionSegurosGradle/src/main/kotlin/Ficheros/Seguros.txt"

    init{
        cargarSeguros()
    }

    override fun agregar(seguro: Seguro): Boolean {
        return try {
            seguros.add(seguro)
            File(filePath).appendText(seguro.serializar() + "\n")
            true
        } catch (e: Exception) {
            false
        }
    }

    override fun eliminar(seguro: Seguro): Boolean {
        var result = seguros.removeIf { it.getNumPoliza() == seguro.getNumPoliza() }

        if (result) {
            guardarSeguros(seguros)
        }
        return result
    }

    override fun eliminar(numPoliza: Int): Boolean {
        val result = seguros.removeIf { it.getNumPoliza() == numPoliza }
        if (result) {
            guardarSeguros(seguros)
        }
        return result
    }



    private fun guardarSeguros(seguros: List<Seguro>) {
        File(filePath).writeText(seguros.joinToString("\n") { it.serializar() })
    }

    override fun cargarSeguros(): Boolean {
        val segurosLista = Utils.leerArchivo(filePath)

        for(seguro in segurosLista){
            val serializado = Utils.deserializarSeguro(seguro)

            if(serializado != null){
                seguros.add(serializado)
            }
        }
        return seguros.isEmpty()
    }
}