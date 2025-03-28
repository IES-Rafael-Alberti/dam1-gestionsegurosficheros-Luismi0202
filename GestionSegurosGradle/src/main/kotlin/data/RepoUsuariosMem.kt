package data

import model.Usuario
import model.TipoPerfil

open class RepoUsuariosMem : IRepoUsuarios {
    protected val usuarios = mutableListOf<Usuario>()

    override fun agregarUsuario(usuario: Usuario): Boolean {
        var agregado = false

        if(buscarUsuario(usuario.nombre) != null){
            println("El usuario introducido ya existe.")
        }
        else{
            usuarios.add(usuario)
            agregado = true
        }
        return agregado
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

    override fun cambiarClave(usuario: Usuario, nuevaClave: String): Boolean {
        var cambiada = false
        try {
            usuario.cambiarClave(nuevaClave)
            cambiada = true
        }catch(e:Exception){
            cambiada = false
            println("¡ERROR! No se ha podido cambiar la contraseña Detalle: $e")
        }
        return cambiada
    }
}