package com.example.retrofitexample.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitexample.data.model.Todo
import com.example.retrofitexample.databinding.ItemListBinding

class ItemAdapter : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(val binding: ItemListBinding):RecyclerView.ViewHolder(binding.root)

    private val diffCallback =object: DiffUtil.ItemCallback<Todo>(){
        override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
           return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem==newItem
        }

    }
    private val differ= AsyncListDiffer(this,diffCallback)
    var todos:List<Todo>
    get() = differ.currentList
    set(value) {differ.submitList(value)}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(ItemListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
    holder.binding.apply {
        val todo =todos[position]
        edtTitle.text=todo.title
        cbDone.isChecked=todo.completed
    }
    }

    override fun getItemCount():Int{
        return todos.size
    }


}