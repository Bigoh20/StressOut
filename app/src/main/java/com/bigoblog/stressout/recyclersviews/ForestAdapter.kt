package com.bigoblog.stressout.recyclersviews

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.RecyclerView
import com.bigoblog.stressout.R
import com.bigoblog.stressout.databinding.NatureItemRecyclerviewBinding
import com.bigoblog.stressout.extras.ForestEntity
import com.bigoblog.stressout.extras.OnClickListener

class ForestAdapter(private val forest : MutableList<ForestEntity>,
private val listener : OnClickListener) : RecyclerView.Adapter<ForestAdapter.ForestHolder>() {


    private lateinit var context : Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForestHolder {
        context = parent.context
        val view = LayoutInflater.from(context)
                  .inflate(R.layout.nature_item_recyclerview, parent,false)
        return ForestHolder(view)
    }


    override fun onBindViewHolder(holder: ForestHolder, position: Int) {
        val forest = forest[position]
        holder.render(forest)
    }

    override fun getItemCount(): Int = forest.size


    inner class ForestHolder(view : View) : RecyclerView.ViewHolder(view){
        private val binding = NatureItemRecyclerviewBinding.bind(view)
        fun render(forest: ForestEntity) {
           //Ubicar la foto.
            with(binding){
                imageNature.setImageResource(forest.photo)
                textViewTitle.text = forest.title
                //Ubicar el listener.
                root.setOnClickListener {
                    listener.setOnClickListener(forest)

                }
            }

        }

    }
}