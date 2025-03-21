package Presentacion

import Presentacion.IUserInterface
import Presentacion.MenuAdmin
import Service.IServSeguros

class MenuGestion(private val ui: IUserInterface, private val servSeguros: IServSeguros) {
    val menuAdmin = MenuAdmin(ui,servSeguros)

    fun mostrarMenu() {
        var seguir = true
        while (seguir) {
            ui.mostrar(
                """
                📌 Menú de gestión
                
                1. Seguros
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
                2. Salir
            """.trimIndent()
            )
            when (ui.recibirEntrada()) {
                "1" -> menuAdmin.menuSeguros()
                "2" -> seguir = false
                else -> ui.mostrar("Opción inválida")
            }
        }
    }
}
