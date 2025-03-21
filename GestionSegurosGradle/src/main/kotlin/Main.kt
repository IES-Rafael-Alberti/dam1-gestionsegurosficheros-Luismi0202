package Main

import Data.IRepoSeguros
import Data.IRepoUsuarios
import Data.RepoSegurosFich
import Data.RepoSegurosMem
import Data.RepoUsuariosFich
import Data.RepoUsuariosMem
import EnumClasificatorias.TipoPerfil
import Presentacion.Consola
import Presentacion.MenuAdmin
import Presentacion.MenuConsulta
import Presentacion.MenuGestion
import Service.*

fun main() {
    val ui = Consola()


    ui.mostrar("Seleccione el modo de ejecución:\n1. SIMULACIÓN (solo en memoria)\n2. ALMACENAMIENTO (usar ficheros)")
    val modo = ui.recibirEntrada()

    val repoUsuarios: IRepoUsuarios
    val repoSeguros: IRepoSeguros

    if (modo == "1") {
        repoUsuarios = RepoUsuariosMem()
        repoSeguros = RepoSegurosMem()
    } else {
        repoUsuarios = RepoUsuariosFich()
        repoSeguros = RepoSegurosFich()
    }

    val servUsuarios = GestorUsuarios(repoUsuarios)
    val servSeguros = GestorSeguros(repoSeguros)

    if (repoUsuarios.obtenerTodosUsuarios().isEmpty()) {
        ui.mostrar("No hay usuarios en el sistema. Por favor, cree un usuario ADMIN.")
        ui.mostrar("Nombre:")
        val nombre = ui.recibirEntrada()
        ui.mostrar("Contraseña:")
        val contrasena = ui.recibirEntrada()

        if (servUsuarios.crearUsuario(nombre, contrasena, "ADMIN")) {
            ui.mostrar("Usuario ADMIN creado exitosamente")
        } else {
            ui.mostrar("Error al crear el usuario ADMIN")
            return
        }
    }

    ui.mostrar("Ingrese su nombre de usuario:")
    val nombre = ui.recibirEntrada()
    ui.mostrar("Ingrese su contraseña:")
    val contrasena = ui.recibirEntrada()

    val usuario = servUsuarios.autenticarUsuario(nombre, contrasena)

    if (usuario != null) {
        when (usuario.perfil) {
            TipoPerfil.ADMIN -> MenuAdmin(ui, servUsuarios, servSeguros).mostrarMenu()
            TipoPerfil.GESTION -> MenuGestion(ui, servSeguros).mostrarMenu()
            TipoPerfil.CONSULTA -> MenuConsulta(ui, servSeguros).mostrarMenu()
            else -> ui.mostrar("Perfil no reconocido")
        }
    } else {
        ui.mostrar("Autenticación fallida")
    }
}