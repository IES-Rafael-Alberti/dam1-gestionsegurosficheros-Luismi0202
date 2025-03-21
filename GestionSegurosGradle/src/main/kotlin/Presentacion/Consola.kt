package Presentacion

class Consola : IUserInterface {

    override fun mostrar(mensaje: String) {
        println(mensaje)
    }

    override fun recibirEntrada(): String {
        return readLine() ?: ""
    }
}