package Main

import service.GestorSeguros
import data.IRepoSeguros
import data.IRepoUsuarios
import data.RepoSegurosFich
import data.RepoSegurosMem
import data.RepoUsuariosFich
import data.RepoUsuariosMem
import model.TipoPerfil
import ui.Consola
import app.*
import service.*
import service.GestorUsuarios

fun main() {
    val ui = Consola()

    ui.mostrar("Seleccione el modo de ejecución:\n1. SIMULACIÓN (solo en memoria)\n2. ALMACENAMIENTO (usar ficheros)")
    val modo = ui.pedirInfo()

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
        val nombre = ui.pedirInfo()
        val contrasena = ui.pedirInfoOculta("Contraseña: ")

        if (servUsuarios.agregarUsuario(nombre, contrasena, TipoPerfil.ADMIN)) {
            ui.mostrar("Usuario ADMIN creado exitosamente")
        } else {
            ui.mostrar("Error al crear el usuario ADMIN")
            return
        }
    }

    ui.mostrar("Ingrese su nombre de usuario:")
    val nombre = ui.pedirInfo()
    val contrasena = ui.pedirInfoOculta("Contraseña: ")

    val usuario = servUsuarios.iniciarSesion(nombre, contrasena)

    if (usuario != null) {
        when (usuario) {
            TipoPerfil.ADMIN -> MenuAdmin(servUsuarios, servSeguros).mostrarMenu()
            TipoPerfil.GESTION -> MenuGestion(servSeguros).mostrarMenu()
            TipoPerfil.CONSULTA -> MenuConsulta(servSeguros).mostrarMenu()
            else -> ui.mostrar("Perfil no reconocido") // no va a salir nunca pero lo dejo
        }
    } else {
        ui.mostrar("Autenticación fallida")
    }
}