package com.glendito.fruisca.ui.home.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.glendito.fruisca.R
import com.glendito.fruisca.database.entities.FruitEntity
import com.glendito.fruisca.databinding.ItemHistoryBinding

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    private val fruits = arrayListOf<FruitEntity>()
    private lateinit var binding: ItemHistoryBinding

    fun setFruits(fruits: List<FruitEntity>) {
        this.fruits.clear()
        this.fruits.addAll(fruits)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryAdapter.ViewHolder {
        binding = ItemHistoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: HistoryAdapter.ViewHolder, position: Int) {
        holder.bindItem(fruits[position], position)
    }

    override fun getItemCount(): Int = fruits.size

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        fun bindItem(fruit: FruitEntity, position: Int) {
            binding.txtNumber.text = "Hasil Pindai ${position+1}"
            binding.txtName.text = fruit.name
            binding.txtCreatedAt.text = fruit.createdAt
            binding.progressBarFresh.progress = fruit.fresh
            binding.txtFresh.text = "${fruit.fresh}%"
            binding.txtHealth.text = fruit.health
            if (fruit.fresh > 50) {
                binding.txtHealth.setTextColor(itemView.context.getColor(R.color.button_bg))
            } else {
                binding.txtHealth.setTextColor(itemView.context.getColor(android.R.color.holo_red_dark))
            }
            Glide.with(itemView.context)
                .load(fruit.image)
                .into(binding.imgFruit)
        }
    }
}