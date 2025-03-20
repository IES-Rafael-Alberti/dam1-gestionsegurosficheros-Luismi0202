package Domain
import org.mindrot.jbcrypt.BCrypt
import EnumClasificatorias.TipoPerfil

class Usuario(
    val nombre: String,
    private val contrasenia: String,
    val perfil: TipoPerfil
) {
    val contraseniaEncriptada = BCrypt.hashpw(contrasenia, BCrypt.gensalt())
}