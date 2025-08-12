package gabriel.soares.br.clientesapp.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// val - nao pode mudar
// fal - pode mudar

class RetrofitFactory {

    private val BASE_URL = "https://srv945707.hstgr.cloud/api/"

    private val retrofitFactory =
        Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    // funcao para construir/criar o objeto com os metodos, a classe concreta/real, ja que a interface nao é real/inconcreta
    fun getClienteService(): ClienteService {
        return retrofitFactory.create(ClienteService::class.java)
    }
}