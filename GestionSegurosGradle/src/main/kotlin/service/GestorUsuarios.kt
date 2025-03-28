package service

import data.IRepoUsuarios
import model.TipoPerfil
import model.Usuario

class GestorUsuarios(
    private val repoUsuarios: IRepoUsuarios,
) : IServUsuarios {

    override fun iniciarSesion(nombre: String, clave: String): TipoPerfil? {
        val usuario = repoUsuarios.buscarUsuario(nombre) ?: return null
        return if (Utils.verificarClave(clave, usuario.contrasenia)) {
            usuario.perfil
        } else {
            null
        }
    }

    override fun agregarUsuario(nombre: String, clave: String, perfil: TipoPerfil): Boolean {
        val claveEncriptada = Utils.encriptarClave(clave)
        val nuevoUsuario = Usuario.crearUsuario(listOf(nombre,claveEncriptada,perfil.descripcion))

        if(nuevoUsuario == null){
            println("¡Error,usuario nulo!")
            return false
        }

        return repoUsuarios.agregarUsuario(nuevoUsuario)
    }

    override fun eliminarUsuario(nombre: String): Boolean {
        return repoUsuarios.eliminarUsuario(nombre)
    }

    override fun cambiarClave(usuario: Usuario, nuevaClave: String): Boolean {
        val nuevaClaveEncriptada = Utils.encriptarClave(nuevaClave)
        return repoUsuarios.cambiarClave(usuario,nuevaClaveEncriptada)
    }

    override fun buscarUsuario(nombre: String): Usuario? {
        return repoUsuarios.buscarUsuario(nombre)
    }

    override fun consultarTodos(): List<Usuario> {
        return repoUsuarios.obtenerTodosUsuarios()
    }

    override fun consultarPorPerfil(perfil: TipoPerfil): List<Usuario> {
        return repoUsuarios.obtenerUsuarioPerfil(perfil)
    }
}