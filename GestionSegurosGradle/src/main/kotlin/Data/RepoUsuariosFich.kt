package Data

import Domain.Usuario
import EnumClasificatorias.TipoPerfil
import java.io.File

class RepoUsuariosFich : IRepoUsuarios {
    private val filePath = "GestionSegurosGradle/src/main/kotlin/FicherosTexto/Usuarios.txt"

    override fun agregarUsuario(usuario: Usuario): Boolean {
        return try {
            File(filePath).appendText(usuario.serializar() + "\n")
            true
        } catch (e: Exception) {
            false
        }
    }

    override fun buscarUsuario(nombre: String): Usuario? {
        return obtenerTodosUsuarios().find { it.nombre == nombre }
    }

    override fun eliminarUsuario(usuario: Usuario): Boolean {
        val usuarios = obtenerTodosUsuarios().toMutableList()
        val result = usuarios.removeIf { it.nombre == usuario.nombre }
        if (result) {
            guardarUsuarios(usuarios)
        }
        return result
    }

    override fun eliminarUsuario(nombre: String): Boolean {
        val usuarios = obtenerTodosUsuarios().toMutableList()
        val result = usuarios.removeIf { it.nombre == nombre }
        if (result) {
            guardarUsuarios(usuarios)
        }
        return result
    }

    override fun obtenerTodosUsuarios(): List<Usuario> {
        return try {
            File(filePath).readLines().map { Utils.deserializarUsuario(it) }
        } catch (e: Exception) {
            println("¡Error! Detalle: $e")
            emptyList()
        }
    }

    override fun obtenerUsuarioPerfil(perfil: TipoPerfil): List<Usuario> {
        return obtenerTodosUsuarios().filter { it.perfil == perfil }
    }

    private fun guardarUsuarios(usuarios: List<Usuario>) {
        File(filePath).writeText(usuarios.joinToString("\n") { it.serializar() })
    }
}