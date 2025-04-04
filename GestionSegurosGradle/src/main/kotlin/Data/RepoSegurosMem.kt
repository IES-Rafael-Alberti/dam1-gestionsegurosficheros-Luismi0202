package data

import model.Seguro

open class RepoSegurosMem : IRepoSeguros {
    protected val seguros = mutableListOf<Seguro>()

    override fun agregar(seguro: Seguro): Boolean {
        return seguros.add(seguro)
    }

    override fun buscar(numPoliza: Int): Seguro? {
        return seguros.find { it.getNumPoliza() == numPoliza }
    }

    override fun eliminar(seguro: Seguro): Boolean {
        return seguros.remove(seguro)
    }

    override fun eliminar(numPoliza: Int): Boolean {
        val seguro = buscar(numPoliza)
        return if (seguro != null) {
            seguros.remove(seguro)
        } else {
            false
        }
    }

    override fun obtenerTodos(): List<Seguro> {
        return seguros.toList()
    }

    override fun obtener(tipoSeguro: String): List<Seguro> {
        return seguros.filter { it.tipoSeguro() == tipoSeguro }
    }
}