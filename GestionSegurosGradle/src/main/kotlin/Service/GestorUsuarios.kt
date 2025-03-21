package Service

import Data.IRepoUsuarios
import Domain.Usuario
import EnumClasificatorias.TipoPerfil

class GestorUsuarios(private val repoUsuarios: IRepoUsuarios) : IServUsuarios {
    override fun autenticarUsuario(nombre: String, contrasena: String): Usuario? {
        val usuario = repoUsuarios.buscarUsuario(nombre)
        return if (usuario != null && usuario.verificarClave(contrasena)) usuario else null
    }

    override fun crearUsuario(nombre: String, contrasena: String, perfil: String): Boolean {
        val perfilEnum = TipoPerfil.getPerfil(perfil)
        if (perfilEnum == null) {
            return false
        }
        val usuario = Usuario(nombre, contrasena, perfilEnum)
        return if (repoUsuarios.agregarUsuario(usuario)) {
            Usuario.usuariosCreados.add(usuario)
            true
        } else {
            false
        }
    }

    override fun eliminarUsuario(nombre: String): Boolean {
        val usuario = repoUsuarios.buscarUsuario(nombre)
        return if (usuario != null && repoUsuarios.eliminarUsuario(usuario)) {
            Usuario.usuariosCreados.remove(usuario)
            true
        } else {
            false
        }
    }

    override fun cambiarContrasena(nombre: String, nuevaContrasena: String): Boolean {
        val usuario = repoUsuarios.buscarUsuario(nombre)
        return if (usuario != null) {
            usuario.cambiarClave(nuevaContrasena)
            repoUsuarios.eliminarUsuario(usuario)
            repoUsuarios.agregarUsuario(usuario)
            true
        } else {
            false
        }
    }

    override fun listarUsuarios(): List<Usuario> {
        return repoUsuarios.obtenerTodosUsuarios()
    }
}