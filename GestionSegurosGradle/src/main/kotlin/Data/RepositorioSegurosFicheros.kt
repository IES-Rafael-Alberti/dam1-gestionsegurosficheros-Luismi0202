package Data

import Domain.Seguro
import java.io.File

class RepositorioSegurosFicheros(private val archivo: String, private val mapaSeguros: Map<String, (List<String>) -> Seguro>) {

    // Guardar un seguro en el fichero
    fun guardarSeguro(seguro: Seguro) {
        val file = File(archivo)
        println("Guardando seguro: ${seguro.serializar()}")
        file.appendText(seguro.serializar() + "\n")
    }

    // Cargar todos los seguros del fichero
    fun cargarSeguros(): List<Seguro> {
        val seguros = mutableListOf<Seguro>()
        val file = File(archivo)

        if (!file.exists()) {
            println("El archivo no existe.")
            return seguros
        }

        file.forEachLine { linea ->
            val datos = linea.split(";")
            val tipoSeguro = datos.last() // El último campo indica el tipo de seguro
            println("Leyendo seguro del tipo: $tipoSeguro")

            val seguro = mapaSeguros[tipoSeguro]?.invoke(datos.dropLast(1)) // Pasamos la lista de datos SIN el tipoSeguro
            if (seguro != null) {
                seguros.add(seguro)
                println("Seguro cargado: ${seguro.serializar()}")
            } else {
                println("No se pudo cargar el seguro del tipo: $tipoSeguro")
            }
        }
        return seguros
    }
}