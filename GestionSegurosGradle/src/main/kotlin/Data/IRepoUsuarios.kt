package data

import model.Usuario
import model.TipoPerfil

interface IRepoUsuarios {
    fun agregarUsuario(usuario: Usuario): Boolean
    fun buscarUsuario(nombre: String): Usuario?
    fun eliminarUsuario(usuario:Usuario): Boolean
    fun eliminarUsuario(nombre: String): Boolean
    fun obtenerTodosUsuarios(): List<Usuario>
    fun obtenerUsuarioPerfil(perfil: TipoPerfil): List<Usuario>
    fun cambiarClave(usuario: Usuario, nuevaClave: String): Boolean
}