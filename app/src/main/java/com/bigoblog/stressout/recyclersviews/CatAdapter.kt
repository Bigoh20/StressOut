package com.bigoblog.stressout.recyclersviews

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.RecyclerView
import com.bigoblog.stressout.R
import com.bigoblog.stressout.databinding.CatItemRecyclerviewBinding
import com.bigoblog.stressout.databinding.FragmentCatBinding
import com.bigoblog.stressout.extras.CatEntity
import com.bigoblog.stressout.extras.OnClickListener

class CatAdapter(private val catsArray : MutableList<CatEntity>,
private val listener : OnClickListener) : RecyclerView.Adapter<CatAdapter.CatHolder>() {

    private lateinit var context : Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatHolder {
        context = parent.context
        val view = LayoutInflater.from(context).
        inflate(R.layout.cat_item_recyclerview, parent, false)

        return CatHolder(view)
    }

    override fun onBindViewHolder(holder: CatHolder, position: Int) {
        val catItem = catsArray[position]
        holder.render(catItem)
    }

    override fun getItemCount(): Int = catsArray.size





    inner class CatHolder(view : View) : RecyclerView.ViewHolder(view){


        //Recuperar el binding
        private val binding = CatItemRecyclerviewBinding.bind(view)


        fun render(cat : CatEntity) {
            binding.tvCatName.text = cat.catName
            binding.tvCatDescription.text = cat.catDescription
            binding.imageCat.setImageResource(cat.catPhoto)
            binding.cardViewCat.setOnClickListener {
                listener.setOnClickListener(cat)

            }
        }






    }




}


