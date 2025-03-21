package Presentacion

import Service.IServSeguros

class MenuConsulta(private val ui: IUserInterface, private val servSeguros: IServSeguros) {
    val menuAdmin = MenuAdmin(ui,servSeguros)
    fun mostrarMenu() {
        var seguir = true
        while (seguir) {
            ui.mostrar("""
                📌 Menú de consulta
                
                Seguros
                    1. Consultar
                        1. Todos
                        2. Hogar
                        3. Auto
                        4. Vida
                2. Salir
            """.trimIndent())
            when (ui.recibirEntrada()) {
                "1" -> menuAdmin.consultarSeguro()
                "2" -> seguir = false
                else -> ui.mostrar("Opción inválida")
            }
        }
    }
}