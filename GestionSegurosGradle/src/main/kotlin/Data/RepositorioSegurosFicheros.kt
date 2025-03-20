package Data

import Domain.Seguro
import java.io.File

class RepositorioSegurosFicheros(private val archivo: String, private val mapaSeguros: Map<String, (List<String>) -> Seguro>) {

    // Guardar un seguro en el fichero
    fun guardarSeguro(seguro: Seguro) {
        File(archivo).appendText(seguro.serializar() + "\n")
    }

    // Cargar todos los seguros del fichero
    fun cargarSeguros(): List<Seguro> {
        val seguros = mutableListOf<Seguro>()
        val file = File(archivo)

        if (!file.exists()) return seguros

        file.forEachLine { linea ->
            val datos = linea.split(";")
            val tipoSeguro = datos.last() // El último campo indica el tipo de seguro

            val seguro = mapaSeguros[tipoSeguro]?.invoke(datos.dropLast(1)) // Pasamos la lista de datos SIN el tipoSeguro
            if (seguro != null) {
                seguros.add(seguro)
            }
        }
        return seguros
    }
}