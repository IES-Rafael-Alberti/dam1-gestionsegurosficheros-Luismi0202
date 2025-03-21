package Domain
import org.mindrot.jbcrypt.BCrypt
import EnumClasificatorias.TipoPerfil
import Interfaces.IExportable

class Usuario(
    val nombre: String,
    private var contrasenia: String,
    val perfil: TipoPerfil
): IExportable {


    var contraseniaEncriptada = BCrypt.hashpw(contrasenia, BCrypt.gensalt())

    fun verificarClave(claveEncriptada: String): Boolean{
        val coincide = BCrypt.checkpw(contrasenia, claveEncriptada)
        return coincide
    }

    fun cambiarClave(nuevaClaveEncriptada:String){
        contrasenia = nuevaClaveEncriptada
        contraseniaEncriptada = BCrypt.hashpw(contrasenia, BCrypt.gensalt())
    }

    override fun serializar(): String {
        return "$nombre;$contraseniaEncriptada;$perfil"
    }

    companion object{
        val usuariosCreados: MutableList<Usuario> = mutableListOf()

        private fun existeNombre(nombreNuevo:String):Boolean{
            for(usuario in usuariosCreados){
                if(usuario.nombre == nombreNuevo){
                    return true
                }
            }
            return false
        }

        fun crearUsuario(datos: List<String>): Usuario {
            val perfil = TipoPerfil.getPerfil(datos[2])
                ?: throw Exception("El perfil no puede ser nulo")

            if (existeNombre(datos[0])) {
                throw Exception("El nombre introducido ya está en uso por otro usuario")
            }

            val usuario = Usuario(datos[0], datos[1], perfil)
            usuariosCreados.add(usuario)
            return usuario
        }
    }
}