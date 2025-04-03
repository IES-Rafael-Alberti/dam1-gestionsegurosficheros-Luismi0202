package app

import service.IServSeguros

class MenuGestion(private val servSeguros: IServSeguros) {
    val menuAdmin = MenuAdmin(servSeguros)

    fun mostrarMenu() {
        var seguir = true
        while (seguir) {
            menuAdmin.ui.mostrar(
                """
                📌 Menú de gestión
                
                1.-Seguros
                
                2. Salir
            """.trimIndent()
            )
            when (menuAdmin.ui.pedirInfo()) {
                "1" -> menuAdmin.menuSeguros()
                "2" -> seguir = false
                else -> menuAdmin.ui.mostrar("Opción inválida")
            }
        }
    }
}
