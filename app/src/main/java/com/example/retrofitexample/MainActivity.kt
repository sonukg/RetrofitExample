package com.example.retrofitexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofitexample.adapter.ItemAdapter
import com.example.retrofitexample.adapter.TodoAdapter
import com.example.retrofitexample.databinding.ActivityMainBinding
import com.example.retrofitexample.network.RetrofitInstance
import retrofit2.HttpException
import java.io.IOException
const val TAG="MainActivity"
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    //lateinit var itemAdapter: ItemAdapter
    lateinit var todoAdapter: TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpRecycler()


        lifecycleScope.launchWhenCreated {
            binding.progressBar.isVisible=true
            val response=try {
                RetrofitInstance.api.getTodos()
            } catch (e:IOException){
                Log.e(TAG,"You have not internet connection")
                binding.progressBar.isVisible=false
                return@launchWhenCreated
            } catch (e:HttpException){
                Log.e(TAG,"Unexpected response")
                binding.progressBar.isVisible=false
                return@launchWhenCreated
            }
            if (response.isSuccessful && response.body() != null){
                todoAdapter.todos=response.body()!!
            }
            else{
                Log.e(TAG,"Response not successfull")
            }
            binding.progressBar.isVisible=false
        }
    }

    private fun setUpRecycler()= binding.recyclerView.apply {
        todoAdapter= TodoAdapter()
        adapter=todoAdapter
        layoutManager =LinearLayoutManager(this@MainActivity)
    }

}