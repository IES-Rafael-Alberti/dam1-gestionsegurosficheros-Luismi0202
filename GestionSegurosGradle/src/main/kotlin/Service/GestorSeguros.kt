package service

import data.IRepoSeguros
import model.Seguro

class GestorSeguros(private val repoSeguros: IRepoSeguros) : IServSeguros {
    override fun contratarSeguro(seguro: Seguro): Boolean {
        return repoSeguros.agregar(seguro)
    }

    override fun listarSeguros(): List<Seguro> {
        return repoSeguros.obtenerTodos()
    }

    override fun eliminarSeguro(numPoliza: Int): Boolean {
        return repoSeguros.eliminar(numPoliza)
    }

    override fun consultarSeguros(tipo: String): List<Seguro> {
        return repoSeguros.obtener(tipo)
    }
}