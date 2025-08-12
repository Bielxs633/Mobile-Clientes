package gabriel.soares.br.clientesapp.service

import gabriel.soares.br.clientesapp.model.Cliente
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Path

import retrofit2.http.POST
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.DELETE


interface ClienteService {

    @POST("clientes")
    fun criar(@Body cliente: Cliente): Call<Cliente>

    @GET("clientes")
    fun listarTodos(): Call<List<Cliente>>

    @GET("clientes/{id}")
    fun listarPorId(@Path("id") id: Long): Call<Cliente>

    @PUT("clientes")
    fun atualizar(@Body cliente: Cliente): Call<Cliente>

    @DELETE("clientes")
    fun excluir(@Body cliente: Cliente): Call<Unit>

}