package gabriel.soares.br.clientesapp.teste

import gabriel.soares.br.clientesapp.model.Cliente
import gabriel.soares.br.clientesapp.service.RetrofitFactory

// funcao para ser executada independentemente a qualquer hora ( play ao lado )
fun main() {

    val c1 = Cliente(
        nome = "Gabriel Soares",
        email = "gabriel.soares@gmail.com"
    )

    val retrofit = RetrofitFactory().getClienteService()
    val cliente = retrofit.criar(c1)
}