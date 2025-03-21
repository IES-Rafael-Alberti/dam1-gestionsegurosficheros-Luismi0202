package Service

import Domain.Usuario

interface IServUsuarios {
    fun autenticarUsuario(nombre: String, contrasena: String): Usuario?
    fun crearUsuario(nombre: String, contrasena: String, perfil: String): Boolean
    fun eliminarUsuario(nombre: String): Boolean
    fun cambiarContrasena(nombre: String, nuevaContrasena: String): Boolean
    fun listarUsuarios(): List<Usuario>

}