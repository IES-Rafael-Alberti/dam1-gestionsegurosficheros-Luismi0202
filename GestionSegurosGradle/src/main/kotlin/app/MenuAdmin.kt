package app

import model.*
import service.IServSeguros
import service.IServUsuarios
import ui.Consola

class MenuAdmin(private val servUsuarios: IServUsuarios?, private val servSeguros: IServSeguros) {

    constructor(servSeguros: IServSeguros) : this(null, servSeguros)

    val ui = Consola()

    fun mostrarMenu() {
        var seguir = true
        while (seguir) {
            ui.limpiarPantalla(50)
            ui.mostrar("""
                📌 Menú de admin
                
                1. Usuarios
                2. Seguros
                
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
        var seguir = true

        if (servUsuarios == null) {
            ui.mostrar("Gestión de usuarios no disponible")
            return
        } //En caso de borrar el nuestro, ya no se podrá gestionar y se saldrá.

        while(seguir) {
            ui.limpiarPantalla(50)
            ui.mostrar(
                """
            1. Nuevo
            2. Eliminar
            3. Cambiar contraseña
            4.- Consultar todos
            5.- Consultar por perfil
            
            6.- Volver
        """.trimIndent()
            )
            when (ui.pedirInfo()) {
                "1" -> {
                    nuevoUsuario()
                    ui.pausar("PULSA ENTER PARA CONTINUAR")
                }
                "2" ->{
                    eliminarUsuario()
                    ui.pausar("PULSA ENTER PARA CONTINUAR")
                }

                "3" -> {
                    cambiarContrasena()
                    ui.pausar("PULSA ENTER PARA CONTINUAR")
                }

                "4" -> {
                    servUsuarios.consultarTodos()
                    ui.pausar("PULSA ENTER PARA CONTINUAR")
                }
                "5" -> {
                    servUsuarios.consultarPorPerfil(TipoPerfil.getPerfil(ui.pedirInfo("Dame el perfil que quieres consultar (ADMIN, GESTION,CONSULTA)")))
                    ui.pausar("PULSA ENTER PARA CONTINUAR")
                }

                "6" -> seguir = false

                else -> ui.mostrar("Opción inválida")
            }
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
        var seguir = true
        while(seguir) {
            ui.limpiarPantalla(50)
            ui.mostrar(
                """
            1. Contratar
            2. Eliminar
            3. Consultar
            
            4.- Salir
        """.trimIndent()
            )
            when (ui.pedirInfo()) {
                "1" -> {
                    contratarSeguro()
                    ui.pausar("PULSA ENTER PARA CONTINUAR")
                }

                "2" -> {
                    eliminarSeguro()
                    ui.pausar("PULSA ENTER PARA CONTINUAR")
                }

                "3" ->{
                    consultarSeguro()
                    ui.pausar("PULSA ENTER PARA CONTINUAR")
                }

                "4"-> seguir = false
                else -> ui.mostrar("Opción inválida")
            }
        }
    }

    private fun contratarSeguro() {
        ui.limpiarPantalla(50)
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
        try {
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
        }catch(e: Exception){
            println("¡Error al contratar seguro! Detalle: ${e.message}")
        }
    }

    private fun contratarSeguroAuto() {
        try {
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

            if (tipoAuto == null) {
                ui.mostrar("Error al contratar el seguro de auto")
            } else {
                val seguro = SeguroAuto(
                    dni,
                    importe,
                    descripcion,
                    combustible,
                    tipoAuto,
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
        }catch (e:Exception){
            println("¡Error al contratar seguro! Detalle: ${e.message}")
        }
    }

    private fun contratarSeguroVida() {
        try {
            val dni = ui.pedirInfo("Dame el dni del titular")

            val importe = ui.pedirInfo("Introduzca el importe").toDouble()

            val fechaNac = ui.pedirInfo("Introduzca la fecha de nacimiento (YYYY-MM-DD)")

            val nivelRiesgo =
                TipoRiesgo.getRiesgo(ui.pedirInfo("Introduzca el nivel de riesgo (BAJO, MEDIO, ALTO)").uppercase())

            ui.mostrar("Introduzca la indemnización")
            val indemnizacion = ui.pedirInfo().toDouble()

            if (nivelRiesgo == null) {
                ui.mostrar("Error al contratar el seguro de vida")
            } else {
                val seguro = SeguroVida(dni, importe, fechaNac, nivelRiesgo, indemnizacion)

                if (servSeguros.contratarSeguro(seguro)) {
                    ui.mostrar("Seguro de vida contratado exitosamente")
                } else {
                    ui.mostrar("Error al contratar el seguro de vida")
                }
            }
        }catch(e: Exception){
            println("¡Error al crear seguro de vida! Detalle: ${e.message}")
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
        var seguir = true
        while(seguir) {
            ui.limpiarPantalla(50)
            ui.mostrar(
                """
            1. Todos
            2. Hogar
            3. Auto
            4. Vida
            
            5.- Volver
        """.trimIndent()
            )
            when (ui.pedirInfo()) {
                "1" -> {
                    mostrarSeguro(servSeguros.listarSeguros())
                    ui.pausar("PULSA ENTER PARA CONTINUAR")
                }

                "2" -> {
                    mostrarSeguro(servSeguros.consultarSeguros(SeguroHogar::class.simpleName ?: "SeguroHogar"))
                    ui.pausar("PULSA ENTER PARA CONTINUAR")
                }

                "3" -> {
                    mostrarSeguro(servSeguros.consultarSeguros(SeguroAuto::class.simpleName ?: "SeguroAuto"))
                    ui.pausar("PULSA ENTER PARA CONTINUAR")
                }

                "4" -> {
                    mostrarSeguro(servSeguros.consultarSeguros(SeguroVida::class.simpleName ?: "SeguroVida"))
                    ui.pausar("PULSA ENTER PARA CONTINUAR")
                }

                "5" -> seguir = false

                else -> ui.mostrar("Opción inválida")
            }
        }
    }
}