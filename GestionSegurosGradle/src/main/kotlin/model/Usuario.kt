package model


class Usuario private constructor(
    val nombre: String,
    var contrasenia: String,
    val perfil: TipoPerfil
) : IExportable {

    fun cambiarClave(nuevaClave: String) {
        contrasenia = nuevaClave
    }

    override fun serializar(): String {
        return "$nombre;$contrasenia;$perfil"
    }

    companion object {

        fun crearUsuario(datos: List<String>): Usuario? {
            return try {
                if (datos.size < 3) throw IllegalArgumentException("Datos insuficientes para crear el usuario")

                val perfil = TipoPerfil.getPerfil(datos[2])

                val usuario = Usuario(datos[0], datos[1], perfil)
                usuario
            } catch (e: Exception) {
                println("¡Error! $e")
                null
            }
        }
    }
}