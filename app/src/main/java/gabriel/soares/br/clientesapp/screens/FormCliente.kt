package gabriel.soares.br.clientesapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import gabriel.soares.br.clientesapp.model.Cliente
import gabriel.soares.br.clientesapp.service.RetrofitFactory
import gabriel.soares.br.clientesapp.ui.theme.ClientesAppTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.await

@Composable
fun FormCliente(modifier: Modifier = Modifier) {

    // variavel de estado para utilizar no outlined
    var nomeCliente by remember {
        mutableStateOf("")
    }
    // variavel de estado para utilizar no outlined
    var emailCliente by remember {
        mutableStateOf("")
    }

    // Criar uma instancia do RetrofitFactory
    val clienteApi = RetrofitFactory().getClienteService()

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        OutlinedTextField(
            value = nomeCliente,
            onValueChange = { nome -> nomeCliente = nome },
            label = {
                Text(text = "Nome do Cliente")
            },
            modifier = Modifier
                .fillMaxWidth()
        )
        OutlinedTextField(
            value = emailCliente,
            onValueChange = { email -> emailCliente = email },
            label = {
                Text(text = "E-mail do Cliente")
            },
            modifier = Modifier
                .fillMaxWidth()
        )
        Button(
            onClick = {
                //Criar um cliente com os dados informados
                val cliente = Cliente(
                    nome = nomeCliente,
                    email = emailCliente
                )
                //Requisicao POST para a API
                GlobalScope.launch(
                    Dispatchers.IO
                ) {
                    val novoCliente = clienteApi.criar(cliente).await()
                    println(novoCliente)
                }
            },
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
        ) {
            Text(text = "Gerar Cliente")
        }
    }

}

@Preview
@Composable
private fun FormClientePreview() {
    ClientesAppTheme {
        FormCliente()
    }
}