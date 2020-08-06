package com.example.todo.networking

import android.util.Log
import com.example.todo.models.Priority
import com.example.todo.models.ToDo
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object Network {
    private val items = mutableListOf<ToDo>()

    private val client = OkHttpClient()

    private class ToDoCallback(private val onSuccess: (List<ToDo>) -> Unit): Callback<Items> {
        override fun onFailure(call: Call<Items>, t: Throwable) {
            Log.v("Networking", "Error! $t")
        }

        override fun onResponse(call: Call<Items>, response: Response<Items>) {
            val toDos = response.body()?.items?.map {
                it.toToDo()
            } ?: emptyList()

            items.clear()
            items.addAll(toDos)
            onSuccess(items)
        }
    }

    private val toDoApi: ToDoApi
    get() {
        return Retrofit.Builder()
            .baseUrl("http://192.168.1.6:3003")
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(ToDoApi::class.java)
    }

    fun getToDoItem(onSuccess: (List<ToDo>) -> Unit) {
        if(items.isNotEmpty()) {
            onSuccess(items)
            return
        }

        toDoApi.getToDoItems().enqueue(ToDoCallback(onSuccess))
    }

    fun addItem(toDo: ToDo, onSuccess: (List<ToDo>) -> Unit) {
        toDoApi.addToDoItem(toDo.toItem()).enqueue(ToDoCallback(onSuccess))
    }

    private fun Item.toToDo(): ToDo {
        return ToDo(
            name = item,
            priority = Priority.of(priority)
        )
    }

    private fun ToDo.toItem(): Item {
        return Item(
            item = name,
            priority = priority.priorityindex
        )
    }
}