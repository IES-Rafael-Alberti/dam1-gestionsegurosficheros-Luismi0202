package service

import model.Usuario
import model.TipoPerfil
interface IServUsuarios {
    fun iniciarSesion(nombre: String, clave: String): TipoPerfil?
    fun agregarUsuario(nombre: String, clave: String, perfil: TipoPerfil): Boolean
    fun eliminarUsuario(nombre: String): Boolean
    fun cambiarClave(usuario: Usuario, nuevaClave: String): Boolean
    fun buscarUsuario(nombre: String): Usuario?
    fun consultarTodos()
    fun consultarPorPerfil(perfil: TipoPerfil)

}