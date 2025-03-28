package data

import data.ICargarUsuariosIniciales
import model.Usuario
import java.io.File

class RepoUsuariosFich : RepoUsuariosMem(), ICargarUsuariosIniciales {
    private val filePath = "GestionSegurosGradle/src/main/kotlin/Ficheros/Usuarios.txt"

    init{
        cargarUsuarios()
    }

    override fun agregarUsuario(usuario: Usuario): Boolean {
        return try {
            File(filePath).appendText(usuario.serializar() + "\n")
            true
        } catch (e: Exception) {
            false
        }
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


    private fun guardarUsuarios(usuarios: List<Usuario>) {
        File(filePath).writeText(usuarios.joinToString("\n") { it.serializar() })
    }

    override fun cargarUsuarios(): Boolean {
        val users = Utils.leerArchivo(filePath)

        for(user in users){
            val serializado = Utils.deserializarUsuario(user)

            if(serializado != null){
                usuarios.add(serializado)
            }
        }
        return usuarios.isEmpty()
    }
}