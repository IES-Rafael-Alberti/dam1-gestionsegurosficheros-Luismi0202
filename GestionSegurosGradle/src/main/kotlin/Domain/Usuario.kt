package Domain

import at.favre.lib.crypto.bcrypt.BCrypt
import EnumClasificatorias.TipoPerfil
import Interfaces.IExportable

class Usuario(
    val nombre: String,
    var contrasenia: String,
    val perfil: TipoPerfil
) : IExportable {




    /**
     * Verifica si una contraseña ingresada coincide con un hash almacenado previamente usando BCrypt.
     *
     * Esta función permite autenticar a un usuario comprobando si la clave ingresada,
     * tras ser procesada con BCrypt, coincide con el hash almacenado en la base de datos.
     *
     * @param claveIngresada La contraseña en texto plano que se desea comprobar.
     * @param hashAlmacenado El hash BCrypt previamente generado contra el que se verificará la clave ingresada.
     * @return `true` si la clave ingresada coincide con el hash almacenado, `false` en caso contrario.
     */
    fun verificarClave(claveIngresada: String, hashAlmacenado: String): Boolean {
        return BCrypt.verifyer().verify(claveIngresada.toCharArray(), hashAlmacenado).verified
    }

    fun cambiarClave(nuevaClave: String) {
        contrasenia = nuevaClave
    }

    override fun serializar(): String {
        return "$nombre;$contrasenia;$perfil"
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