package app

import model.*
import service.IServSeguros
import service.IServUsuarios
import ui.Consola

class MenuAdmin(private val servUsuarios: IServUsuarios?, private val servSeguros: IServSeguros) {

    constructor(servSeguros: IServSeguros) : this(null, servSeguros)

    open val ui = Consola()

    fun mostrarMenu() {
        var seguir = true
        while (seguir) {
            ui.mostrar("""
                📌 Menú de admin
                
                1. Usuarios
                    1. Nuevo
                    2. Eliminar
                    3. Cambiar contraseña
                2. Seguros
                    1. Contratar
                        1. Hogar
                        2. Auto
                        3. Vida
                    2. Eliminar
                    3. Consultar
                        1. Todos
                        2. Hogar
                        3. Auto
                        4. Vida
                3. Salir
            """.trimIndent())
            when (ui.pedirInfo()) {
                "1" -> menuUsuarios()
                "2" -> menuSeguros()
                "3" -> seguir = false
                else -> ui.mostrar("Opción inválida")
            }
        }
    }

    private fun menuUsuarios() {
        if (servUsuarios == null) {
            ui.mostrar("Gestión de usuarios no disponible")
            return
        }
        ui.mostrar("""
            1. Nuevo
            2. Eliminar
            3. Cambiar contraseña
        """.trimIndent())
        when (ui.pedirInfo()) {
            "1" -> nuevoUsuario()
            "2" -> eliminarUsuario()
            "3" -> cambiarContrasena()
            else -> ui.mostrar("Opción inválida")
        }
    }

    private fun nuevoUsuario() {
        if (servUsuarios == null) {
            ui.mostrar("Gestión de usuarios no disponible")
            return
        }

        val nombre = ui.pedirInfo("Nombre:")

        val contrasena = ui.pedirInfoOculta("Contraseña:")

        val perfil = ui.pedirInfo(
            "Perfil (ADMIN, GESTION, CONSULTA):",
            "Perfil no válido. Debe ser ADMIN, GESTION o CONSULTA.",
            { input -> TipoPerfil.entries.any { it.descripcion.equals(input, ignoreCase = true) } }
        )


        if (servUsuarios.agregarUsuario(nombre, contrasena, TipoPerfil.getPerfil(perfil))) {
            ui.mostrar("¡Usuario creado correctamente!")
        } else {
            ui.mostrar("¡Error al crear usuario!")
        }
    }

    private fun eliminarUsuario() {
        if (servUsuarios == null) {
            ui.mostrar("Gestión de usuarios no disponible")
            return
        }

        val nombre = ui.pedirInfo("Nombre del usuario a eliminar:")

        if (servUsuarios.eliminarUsuario(nombre)) {
            ui.mostrar("Usuario eliminado exitosamente")
        } else {
            ui.mostrar("Error al eliminar el usuario")
        }
    }

    private fun cambiarContrasena() {
        if (servUsuarios == null) {
            ui.mostrar("Gestión de usuarios no disponible")
            return
        }

        val nombre = ui.pedirInfo("Nombre del usuario:")

        val usuario = servUsuarios.buscarUsuario(nombre)

        if(usuario != null){
            val nuevaContrasena = ui.pedirInfoOculta("Nueva contraseña:")
            if (servUsuarios.cambiarClave(usuario, nuevaContrasena)) {
                ui.mostrar("Contraseña cambiada exitosamente")
            } else {
                ui.mostrar("Error al cambiar la contraseña")
            }
        }
    }

    fun menuSeguros() {
        ui.mostrar("""
            1. Contratar
                1. Hogar
                2. Auto
                3. Vida
            2. Eliminar
            3. Consultar
                1. Todos
                2. Hogar
                3. Auto
                4. Vida
        """.trimIndent())
        when (ui.pedirInfo()) {
            "1" -> contratarSeguro()
            "2" -> eliminarSeguro()
            "3" -> consultarSeguro()
            else -> ui.mostrar("Opción inválida")
        }
    }

    private fun contratarSeguro() {
        ui.mostrar("""
            1. Hogar
            2. Auto
            3. Vida
        """.trimIndent())
        when (ui.pedirInfo()) {
            "1" -> contratarSeguroHogar()
            "2" -> contratarSeguroAuto()
            "3" -> contratarSeguroVida()
            else -> ui.mostrar("Opción inválida")
        }
    }

    private fun contratarSeguroHogar() {
        val dni = ui.pedirInfo("Dame el dni del titular")

        val importe = ui.pedirInfo("Introduzca el importe").toDouble()

        val metrosCuadrados = ui.pedirInfo("Introduzca los metros cuadrados").toInt()

        val valorContenido = ui.pedirInfo("Introduzca el valor del contenido").toDouble()

        val direccion = ui.pedirInfo("Introduzca la dirección")

        val anioConstruccion = ui.pedirInfo("Introduzca el año de construcción").toInt()

        val seguro = SeguroHogar(dni, importe, metrosCuadrados, valorContenido, direccion, anioConstruccion)
        if (servSeguros.contratarSeguro(seguro)) {
            ui.mostrar("Seguro de hogar contratado exitosamente")
        } else {
            ui.mostrar("Error al contratar el seguro de hogar")
        }
    }

    private fun contratarSeguroAuto() {
        val dni = ui.pedirInfo("Dame el dni del titular")

        val importe = ui.pedirInfo("Introduzca el importe").toDouble()

        val descripcion = ui.pedirInfo("Introduzca la descripción")

        val combustible = ui.pedirInfo("Introduzca el tipo de combustible")

        ui.mostrar("Introduzca el tipo de automóvil (COCHE,MOTO,CAMIÓN)")
        val tipoAuto = TipoAutomovil.getAuto(ui.pedirInfo().uppercase())

        ui.mostrar("Introduzca el tipo de cobertura (TERCEROS,TERCEROS AMPLIADO,FRANQUICIA 200, FRANQUICIA 300, FRANQUICIA 400, FRANQUICIA 500, TODO RIESGO)")
        val tipoCobertura = Cobertura.getCobertura(ui.pedirInfo().uppercase())

        val asistenciaCarretera = ui.pedirInfo("¿Necesita asistencia en carretera? (true/false)").toBoolean()

        val numPartes = ui.pedirInfo("Introduzca el número de partes").toInt()

        if(tipoAuto == null && tipoCobertura == null){
            ui.mostrar("Error al contratar el seguro de auto")
        }
        else {
            val seguro = SeguroAuto(
                dni,
                importe,
                descripcion,
                combustible,
                tipoAuto!!,
                tipoCobertura.toString(),
                asistenciaCarretera,
                numPartes
            )

            if (servSeguros.contratarSeguro(seguro)) {
                ui.mostrar("Seguro de auto contratado exitosamente")
            } else {
                ui.mostrar("Error al contratar el seguro de auto")
            }
        }
    }

    private fun contratarSeguroVida() {
        val dni = ui.pedirInfo("Dame el dni del titular")

        val importe = ui.pedirInfo("Introduzca el importe").toDouble()

        val fechaNac = ui.pedirInfo("Introduzca la fecha de nacimiento (YYYY-MM-DD)")

        val nivelRiesgo = TipoRiesgo.getRiesgo(ui.pedirInfo("Introduzca el nivel de riesgo (BAJO, MEDIO, ALTO)").uppercase())

        ui.mostrar("Introduzca la indemnización")
        val indemnizacion = ui.pedirInfo().toDouble()

        if(nivelRiesgo == null){
            ui.mostrar("Error al contratar el seguro de vida")
        }
        else {
            val seguro = SeguroVida(dni, importe, fechaNac, nivelRiesgo, indemnizacion)

            if (servSeguros.contratarSeguro(seguro)) {
                ui.mostrar("Seguro de vida contratado exitosamente")
            } else {
                ui.mostrar("Error al contratar el seguro de vida")
            }
        }
    }

    private fun eliminarSeguro() {
        val numPoliza = ui.pedirInfo("Número de póliza del seguro a eliminar:").toIntOrNull()

        if (numPoliza != null && servSeguros.eliminarSeguro(numPoliza)) {
            ui.mostrar("Seguro eliminado exitosamente")
        } else {
            ui.mostrar("Error al eliminar el seguro")
        }
    }

    private fun mostrarSeguro(seguros: List<Seguro>){
        for(seguro in seguros){
            ui.mostrar(seguro.toString())
        }
    }

    fun consultarSeguro() {
        ui.mostrar("""
            1. Todos
            2. Hogar
            3. Auto
            4. Vida
        """.trimIndent())
        when (ui.pedirInfo()) {
            "1" -> mostrarSeguro(servSeguros.listarSeguros())
            "2" -> mostrarSeguro(servSeguros.consultarSeguros(SeguroHogar::class.simpleName ?: "SeguroHogar"))
            "3" -> mostrarSeguro(servSeguros.consultarSeguros(SeguroAuto::class.simpleName ?: "SeguroAuto"))
            "4" -> mostrarSeguro(servSeguros.consultarSeguros(SeguroVida::class.simpleName ?: "SeguroVida"))
            else -> ui.mostrar("Opción inválida")
        }
    }
}