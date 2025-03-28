package Data

import Domain.Usuario
import EnumClasificatorias.TipoPerfil
import java.io.File

class RepoUsuariosFich : IRepoUsuarios {
    private val filePath = "GestionSegurosGradle/src/main/kotlin/FicherosTexto/Usuarios.txt"
    val usuarios = mutableListOf<Usuario>()

    init{
        val users = Utils.leerArchivo(filePath)

        for(user in users){
            val serializado = Utils.deserializarUsuario(user)
            if(serializado != null){
                usuarios.add(serializado)
            }
        }
    }

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
            File(filePath).readLines()
                .mapNotNull { linea ->
                    try {
                        Utils.deserializarUsuario(linea)
                    } catch (e: Exception) {
                        println("¡Error al deserializar usuario! Detalle: $e")
                        null
                    }
                }
        } catch (e: Exception) {
            println("¡Error al leer el archivo! Detalle: $e")
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