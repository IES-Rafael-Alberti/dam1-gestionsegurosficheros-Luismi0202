package Presentacion

import Domain.Seguro
import Domain.SeguroAuto
import Domain.SeguroHogar
import Domain.SeguroVida
import EnumClasificatorias.TipoAutomovil
import EnumClasificatorias.TipoRiesgo
import Service.IServSeguros
import Service.IServUsuarios

class MenuAdmin(private val ui: IUserInterface, private val servUsuarios: IServUsuarios?, private val servSeguros: IServSeguros) {

    constructor(ui: IUserInterface, servSeguros: IServSeguros) : this(ui, null, servSeguros)

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
            when (ui.recibirEntrada()) {
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
        when (ui.recibirEntrada()) {
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

        ui.mostrar("Nombre:")
        val nombre = ui.recibirEntrada()

        ui.mostrar("Contraseña:")
        val contrasena = ui.recibirEntrada()

        ui.mostrar("Perfil (ADMIN, GESTION, CONSULTA):")
        val perfil = ui.recibirEntrada()

        if (servUsuarios.crearUsuario(nombre, contrasena, perfil)) {
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

        ui.mostrar("Nombre del usuario a eliminar:")
        val nombre = ui.recibirEntrada()

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
        ui.mostrar("Nombre del usuario:")
        val nombre = ui.recibirEntrada()

        ui.mostrar("Nueva contraseña:")
        val nuevaContrasena = ui.recibirEntrada()

        if (servUsuarios.cambiarContrasena(nombre, nuevaContrasena)) {
            ui.mostrar("Contraseña cambiada exitosamente")
        } else {
            ui.mostrar("Error al cambiar la contraseña")
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
        when (ui.recibirEntrada()) {
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
        when (ui.recibirEntrada()) {
            "1" -> contratarSeguroHogar()
            "2" -> contratarSeguroAuto()
            "3" -> contratarSeguroVida()
            else -> ui.mostrar("Opción inválida")
        }
    }

    private fun contratarSeguroHogar() {
        ui.mostrar("Dame el dni del titular")
        val dni = ui.recibirEntrada()

        ui.mostrar("Introduzca el importe")
        val importe = ui.recibirEntrada().toDouble()

        ui.mostrar("Introduzca los metros cuadrados")
        val metrosCuadrados = ui.recibirEntrada().toInt()

        ui.mostrar("Introduzca el valor del contenido")
        val valorContenido = ui.recibirEntrada().toDouble()

        ui.mostrar("Introduzca la dirección")
        val direccion = ui.recibirEntrada()

        ui.mostrar("Introduzca el año de construcción")
        val anioConstruccion = ui.recibirEntrada().toInt()

        val seguro = SeguroHogar(0, dni, importe, metrosCuadrados, valorContenido, direccion, anioConstruccion)
        if (servSeguros.contratarSeguro(seguro)) {
            ui.mostrar("Seguro de hogar contratado exitosamente")
        } else {
            ui.mostrar("Error al contratar el seguro de hogar")
        }
    }

    private fun contratarSeguroAuto() {
        ui.mostrar("Dame el dni del titular")
        val dni = ui.recibirEntrada()

        ui.mostrar("Introduzca el importe")
        val importe = ui.recibirEntrada().toDouble()

        ui.mostrar("Introduzca la descripción")
        val descripcion = ui.recibirEntrada()

        ui.mostrar("Introduzca el tipo de combustible")
        val combustible = ui.recibirEntrada()

        ui.mostrar("Introduzca el tipo de automóvil (COCHE,MOTO,CAMIÓN)")
        val tipoAuto = TipoAutomovil.valueOf(ui.recibirEntrada().uppercase())

        ui.mostrar("Introduzca el tipo de cobertura (TERCEROS,TERCEROS AMPLIADO,FRANQUICIA 200, FRANQUICIA 300, FRANQUICIA 400, FRANQUICIA 500, TODO RIESGO)")
        val tipoCobertura = ui.recibirEntrada()

        ui.mostrar("¿Necesita asistencia en carretera? (true/false)")
        val asistenciaCarretera = ui.recibirEntrada().toBoolean()

        ui.mostrar("Introduzca el número de partes")
        val numPartes = ui.recibirEntrada().toInt()

        val seguro = SeguroAuto(0, dni, importe, descripcion, combustible, tipoAuto, tipoCobertura, asistenciaCarretera, numPartes)
        if (servSeguros.contratarSeguro(seguro)) {
            ui.mostrar("Seguro de auto contratado exitosamente")
        } else {
            ui.mostrar("Error al contratar el seguro de auto")
        }
    }

    private fun contratarSeguroVida() {
        ui.mostrar("Dame el dni del titular")
        val dni = ui.recibirEntrada()

        ui.mostrar("Introduzca el importe")
        val importe = ui.recibirEntrada().toDouble()

        ui.mostrar("Introduzca la fecha de nacimiento (YYYY-MM-DD)")
        val fechaNac = ui.recibirEntrada()

        ui.mostrar("Introduzca el nivel de riesgo (BAJO, MEDIO, ALTO)")
        val nivelRiesgo = TipoRiesgo.valueOf(ui.recibirEntrada().uppercase())

        ui.mostrar("Introduzca la indemnización")
        val indemnizacion = ui.recibirEntrada().toDouble()

        val seguro = SeguroVida(0, dni, importe, fechaNac, nivelRiesgo, indemnizacion)

        if (servSeguros.contratarSeguro(seguro)) {
            ui.mostrar("Seguro de vida contratado exitosamente")
        } else {
            ui.mostrar("Error al contratar el seguro de vida")
        }
    }

    private fun eliminarSeguro() {
        ui.mostrar("Número de póliza del seguro a eliminar:")
        val numPoliza = ui.recibirEntrada().toIntOrNull()

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
        when (ui.recibirEntrada()) {
            "1" -> mostrarSeguro(servSeguros.listarSeguros())
            "2" -> mostrarSeguro(servSeguros.consultarSeguros(SeguroHogar::class.simpleName ?: "SeguroHogar"))
            "3" -> mostrarSeguro(servSeguros.consultarSeguros(SeguroAuto::class.simpleName ?: "SeguroAuto"))
            "4" -> mostrarSeguro(servSeguros.consultarSeguros(SeguroVida::class.simpleName ?: "SeguroVida"))
            else -> ui.mostrar("Opción inválida")
        }
    }
}