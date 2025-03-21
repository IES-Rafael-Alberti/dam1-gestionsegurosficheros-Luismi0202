package Data

import Domain.Usuario
import EnumClasificatorias.TipoPerfil

class RepoUsuariosMem : IRepoUsuarios {
    private val usuarios = mutableListOf<Usuario>()

    override fun agregarUsuario(usuario: Usuario): Boolean {
        return usuarios.add(usuario)
    }

    override fun buscarUsuario(nombre: String): Usuario? {
        return usuarios.find { it.nombre == nombre }
    }

    override fun eliminarUsuario(usuario: Usuario): Boolean {
        return usuarios.remove(usuario)
    }

    override fun eliminarUsuario(nombre: String): Boolean {
        val usuario = buscarUsuario(nombre)
        return if (usuario != null) {
            usuarios.remove(usuario)
        } else {
            false
        }
    }

    override fun obtenerTodosUsuarios(): List<Usuario> {
        return usuarios.toList()
    }

    override fun obtenerUsuarioPerfil(perfil: TipoPerfil): List<Usuario> {
        return usuarios.filter { it.perfil == perfil }
    }
}