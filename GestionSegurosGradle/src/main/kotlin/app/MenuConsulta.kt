package app

import service.IServSeguros

class MenuConsulta(private val servSeguros: IServSeguros) {
    val menuAdmin = MenuAdmin(servSeguros)

    fun mostrarMenu() {
        var seguir = true

        while (seguir) {
            menuAdmin.ui.mostrar("""
                📌 Menú de consulta
                
                1.- Consultar seguros
                
                2.- Salir
            """.trimIndent())
            when (menuAdmin.ui.pedirInfo()) {
                "1" -> menuAdmin.consultarSeguro()
                "2" -> seguir = false
                else -> menuAdmin.ui.mostrar("Opción inválida")
            }
        }
    }
}