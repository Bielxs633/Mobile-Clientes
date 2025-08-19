package gabriel.soares.br.clientesapp.screens

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import gabriel.soares.br.clientesapp.R
import gabriel.soares.br.clientesapp.model.Cliente
import gabriel.soares.br.clientesapp.service.ClienteService
import gabriel.soares.br.clientesapp.service.RetrofitFactory
import gabriel.soares.br.clientesapp.ui.theme.ClientesAppTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.await

//--------------------------------------------------------------------------------------------------
//TODO HOME_SCREEN

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {

    var navController = rememberNavController()

    // fornece a estrutura básica para a interface do usuário ( pode criar os componentes separamente ) é um sistema de andaime
    Scaffold(
        topBar = {
            // TESTE: Text(text = "Barra Superior")
            // Chama a barra de titulo feita la em baixo
            BarraDeTitulo()
        },

        bottomBar = {
            //TESTE: Text(text = "Barra Inferior")
            // Chama a barra de navegacao feita la em baixo
            BarraDeNavegacao(navController)
        },

        floatingActionButton = {
            //Chama o botao feito la em baixo
            BotaoFlutuante(navController)
        },
        content = { paddingValues ->
//        paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.background)
            ) {
                NavHost(
                    navController = navController,
                    startDestination = "Home"
                ) {
                    composable(route = "Home") { TelaHome(paddingValues) }
                    composable(route = "Form") { FormCliente(navController) }

                }
            }
        }
    )
}





//--------------------------------------------------------------------------------------------------
//TODO TOP_BAR

// Notaçao/Import da TopAppBar ( mouse em cima e clica )
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarraDeTitulo(modifier: Modifier = Modifier) {
    TopAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp),
        colors = TopAppBarDefaults
            .topAppBarColors(
                containerColor = MaterialTheme
                    .colorScheme.primary
            ),
        title = {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ){
                Column {
                    Text(
                        text = "GABRIEL DA SILVA SOARES",
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Text(text = "gabriel.soares@gmail.com",
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
                Card(
                    modifier = Modifier
                        .size(60.dp)
                        .padding(5.dp),
                    shape = CircleShape
                ){
                    Image(
                        painter = painterResource(R.drawable.boy),
                        contentDescription = "Foto de Perfil",
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    )
}

// Preview para Barra de Titulo ( TopBar )
@Preview
@Composable
private fun BarraDeTituloPreview() {
    ClientesAppTheme {
        BarraDeTitulo()
    }
}

//--------------------------------------------------------------------------------------------------
//TODO BOTTOM_BAR

@Composable
fun NavigationBar(navController: NavController?) {
    NavigationBar(
        containerColor = MaterialTheme
            .colorScheme.primary,
        contentColor = MaterialTheme
            .colorScheme.onPrimary
    ) {
        NavigationBarItem(
            selected = false,
            onClick = {
                navController!!.navigate("Home")
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            },
            label = {
                Text(text = "Home", color = MaterialTheme.colorScheme.onPrimary)
            }
        )

        NavigationBarItem(
            selected = false,
            onClick = {navController!!.navigate("Form")},
            icon = {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Favorite",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            },
            label = {
                Text(text = "Favorite", color = MaterialTheme.colorScheme.onPrimary)
            }
        )

        NavigationBarItem(
            selected = false,
            onClick = {},
            icon = {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            },
            label = {
                Text(text = "Menu", color = MaterialTheme.colorScheme.onPrimary)
            }
        )
    }
}

//--------------------------------------------------------------------------------------------------
//TODO NAVIGATION_BAR

@Composable
private fun BarraDeNavegacao(navController: NavHostController) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primary
    ){
        NavigationBarItem(
            selected = false,
            onClick = {},
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            },
            // Para ter escrita de baixo da imagem
            label = {
                Text(
                    text = "Home",
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        )
        NavigationBarItem(
            selected = false,
            onClick = {},
            icon = {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Favorite",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            },
            // Para ter escrita de baixo da imagem
            label = {
                Text(
                    text = "Favorite",
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        )
        NavigationBarItem(
            selected = false,
            onClick = {},
            icon = {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            },
            // Para ter escrita de baixo da imagem
            label = {
                Text(
                    text = "Menu",
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        )
    }
}

//--------------------------------------------------------------------------------------------------
//TODO FAB ( BOTÃO_FLUTUANTE )

@Composable
fun BotaoFlutuante(navController: NavController?) {
    FloatingActionButton(
        onClick = {
            navController!!.navigate("Form")
        },
        containerColor = MaterialTheme.colorScheme.tertiary
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Botao Adicionar",
            tint = MaterialTheme.colorScheme.onTertiary
        )
    }
}

@Preview
@Composable
private fun BotaoFlutuantePreview() {
    ClientesAppTheme {
        BotaoFlutuante(null)
    }
}

//--------------------------------------------------------------------------------------------------
//TODO CLIENTE_CARD

@Composable
fun ClienteCard(cliente: Cliente, clienteApi: ClienteService?) {

    var mostrarConfirmacaoDeExclusao by remember {
        mutableStateOf(false)
    }

    // Mostrar confirmaçao de exclusao
    if (mostrarConfirmacaoDeExclusao)

        AlertDialog(
            onDismissRequest = {
                mostrarConfirmacaoDeExclusao = false
            },
            title = {
                Text(text = "Excluir")
            },
            text = {
                Text(text = "Confirma a exclusão do cliente ${cliente.nome}?")
            },
            confirmButton = {
                //TODO Botão mais estilizado
//                Button(
//                    onClick = { mostrarConfirmacaoDeExclusao = false }
//                ) {
//                    Text("Confirmar"),
//                    Icon(
//                        imageVector = Icons.Default.Check,
//                        contentDescription = "Icone de Conrirmar"
//                    )
//                }
                TextButton(
                    onClick = {
                        GlobalScope.launch(
                            Dispatchers.IO
                        )
                        {
                            clienteApi!!.excluir(cliente)
                        }
                    }
                ) {
                    Text("Confirmar")
                }
            },
            dismissButton = {
                //TODO Botão mais estilizado
//                Button(
//                    onClick = { mostrarConfirmacaoDeExclusao = false }
//                ) {
//                    Text("Confirmar")
//                    Icon(
//                        imageVector = Icons.Default.Close,
//                        contentDescription = "Icone de Cancelar"
//                    )
//                }
                TextButton(
                    onClick = {
                        mostrarConfirmacaoDeExclusao = false
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .padding(
                start = 8.dp,
                end = 8.dp,
                bottom = 4.dp
            ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 12.dp)
            ) {
                Text(
                    text = cliente.nome,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = cliente.email,
                    fontSize = 12.sp
                )
            }
            IconButton(
                onClick = {
                    mostrarConfirmacaoDeExclusao = true
                },
            ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete"
            )
            }
        }
    }
}

@Preview
@Composable
private fun ClienteCardPreview() {
    ClienteCard(Cliente(), null)
}

//--------------------------------------------------------------------------------------------------
//TODO TELA_HOME

@Composable
fun TelaHome(paddingValues: PaddingValues) {

    // cria uma instancia do retroftFactory
    val clienteApi = RetrofitFactory().getClienteService()

    // Cria uma variavel de estado para armazenar a lista de clientes da Api
    var clientes by remember {
        mutableStateOf(listOf<Cliente>())
    }

    LaunchedEffect(Dispatchers.IO) {
        clientes = clienteApi.listarTodos().await()
//        println(clientes)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
        ){
            Icon(
                imageVector = Icons.Default.AccountBox,
                contentDescription = "Icone da lista de Clientes",
                tint = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Lista de Clintes"
            )
        }
        // Replica o objeto
        LazyColumn(
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
            items(clientes) { cliente ->
                ClienteCard(cliente, clienteApi)
            }
        }
    }
}

//--------------------------------------------------------------------------------------------------
//==================================================================================================
//TODO Preview_para_TUDO

// uiMode, altera o tema sem precisar iniciar o aplicativo
@Preview (uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun HomeScreenPreview() {
    ClientesAppTheme {
        HomeScreen()
    }
}

//==================================================================================================
