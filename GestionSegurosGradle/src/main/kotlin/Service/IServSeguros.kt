package service

import model.Seguro

interface IServSeguros {
    fun contratarSeguro(seguro: Seguro): Boolean
    fun listarSeguros(): List<Seguro>
    fun eliminarSeguro(numPoliza: Int): Boolean
    fun consultarSeguros(tipo: String): List<Seguro>
}