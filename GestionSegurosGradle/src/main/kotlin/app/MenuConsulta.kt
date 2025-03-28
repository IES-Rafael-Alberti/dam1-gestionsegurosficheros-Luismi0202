package app

import service.IServSeguros

class MenuConsulta(private val servSeguros: IServSeguros) {
    val menuAdmin = MenuAdmin(servSeguros)

    fun mostrarMenu() {
        var seguir = true

        while (seguir) {
            menuAdmin.ui.mostrar("""
                📌 Menú de consulta
                
                Seguros
                    1. Consultar
                        1. Todos
                        2. Hogar
                        3. Auto
                        4. Vida
                2. Salir
            """.trimIndent())
            when (menuAdmin.ui.pedirInfo()) {
                "1" -> menuAdmin.consultarSeguro()
                "2" -> seguir = false
                else -> menuAdmin.ui.mostrar("Opción inválida")
            }
        }
    }
}