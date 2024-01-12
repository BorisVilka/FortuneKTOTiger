package com.ktotiger.acrashjet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ktotiger.acrashjet.databinding.ListItemBinding
import java.util.Random

class SpinAdapter(val size: Int): RecyclerView.Adapter<SpinAdapter.Companion.MYHolder>() {

    val arr = arrayOf(R.drawable.el1,R.drawable.el2,R.drawable.el3,R.drawable.el4,
        R.drawable.el5,
        )
    val random = Random()
    var data = mutableListOf<Int>()

    init {
        for(i in 0..size) data.add(arr[random.nextInt(arr.size)])
    }

    companion object {
        class MYHolder(val binding: ListItemBinding): ViewHolder(binding.root)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MYHolder {
        return MYHolder(ListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return  data.size
    }

    override fun onBindViewHolder(holder: MYHolder, position: Int) {
        holder.binding.imageView4.setImageResource(data[position])
    }
}