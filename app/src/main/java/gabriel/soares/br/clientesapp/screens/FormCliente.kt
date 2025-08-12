package gabriel.soares.br.clientesapp.screens

import android.util.Patterns
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
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

    // variaveis de estado para utilizar no outlined
    var nomeCliente by remember {
        mutableStateOf("")
    }
    var emailCliente by remember {
        mutableStateOf("")
    }

    // variaveis de estado para validar a entrada do usuario
    var isNomeError by remember {
        mutableStateOf(false)
    }
    var isEmailError by remember {
        mutableStateOf(false)
    }

    fun validar(): Boolean {
        isNomeError = nomeCliente.length < 1
        // .Patterns - confere se tudo esta no padrao correto
        // ! - para deixar o contrario
        isEmailError = !Patterns.EMAIL_ADDRESS.matcher(emailCliente).matches()
        return !isNomeError && !isEmailError
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
            supportingText = {
                // parece só no caso de erro
                if (isNomeError)
                Text(text = "O Nome do Cliente é Obrigatório!")
            },
            trailingIcon = {
                if (isNomeError){
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Erro")
                }
            },
            isError = isNomeError,
            modifier = Modifier
                .fillMaxWidth()
        )
        OutlinedTextField(
            value = emailCliente,
            onValueChange = { email -> emailCliente = email },
            label = {
                Text(text = "E-mail do Cliente")
            },
            supportingText = {
                // parece só no caso de erro
                if (isEmailError)
                Text(text = "O Email do Cliente é Obrigatório!")
            },
            trailingIcon = {
                if (isEmailError){
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Erro")
                }
            },
            isError = isEmailError,
            modifier = Modifier
                .fillMaxWidth()
        )
        Button(
            onClick = {
                //Criar um cliente com os dados informados
                if (validar()) {
                    val cliente = Cliente(
                        nome = nomeCliente,
                        email = emailCliente
                    )
                    //Requisicao POST para a API
                    // assim que apertar o batao vai ser  executado, ja ira chamar
                    GlobalScope.launch(
                        Dispatchers.IO
                    ) {
                        val novoCliente = clienteApi.criar(cliente).await()
//                    println(novoCliente)
                    }
                } else {
//                    println("Os dados estao incorretos!")
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