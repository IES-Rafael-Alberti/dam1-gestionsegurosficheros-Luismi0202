package Domain

import org.mindrot.jbcrypt.BCrypt
import EnumClasificatorias.TipoPerfil
import Interfaces.IExportable

class Usuario(
    val nombre: String,
    private var contrasenia: String,
    val perfil: TipoPerfil
) : IExportable {

    var contraseniaEncriptada: String = BCrypt.hashpw(contrasenia, BCrypt.gensalt())

    fun verificarClave(clave: String): Boolean {
        return BCrypt.checkpw(clave, contraseniaEncriptada)
    }

    fun cambiarClave(nuevaClave: String) {
        contrasenia = nuevaClave
        contraseniaEncriptada = BCrypt.hashpw(contrasenia, BCrypt.gensalt())
    }

    override fun serializar(): String {
        return "$nombre;$contraseniaEncriptada;$perfil"
    }

    companion object {
        val usuariosCreados: MutableList<Usuario> = mutableListOf()

        private fun existeNombre(nombreNuevo: String): Boolean {
            return usuariosCreados.any { it.nombre == nombreNuevo }
        }

        fun crearUsuario(datos: List<String>,crear:Boolean=true): Usuario? {
            return try {
                if (datos.size < 3) throw IllegalArgumentException("Datos insuficientes para crear el usuario")

                val perfil = TipoPerfil.getPerfil(datos[2]) ?: throw IllegalArgumentException("El perfil no puede ser nulo")

                if (existeNombre(datos[0]) && crear) {
                    throw IllegalArgumentException("El nombre introducido ya está en uso por otro usuario")
                }

                val usuario = Usuario(datos[0], datos[1], perfil)
                usuariosCreados.add(usuario)
                usuario
            } catch (e: Exception) {
                println("¡Error! $e")
                null
            }
        }
    }
}