package com.pradum.inovanttest

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pradum.inovanttest.models.Attribute
import de.hdodenhof.circleimageview.CircleImageView

class ImgBannerAdapter(val context: Context) : RecyclerView.Adapter<ImgBannerAdapter.viewHolder>() {
    private var selectedPosition = RecyclerView.NO_POSITION
    private var clickListener: onClickColorListener? = null
    var images:List<Attribute> = emptyList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val v=LayoutInflater.from(parent.context).inflate(R.layout.color_select_iteam,parent,false)
        return viewHolder(v)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val isSelected = position == selectedPosition
        var images= images?.get(position)
        Glide.with(context)
            .load(images?.swatch_url)
            .into(holder.image)
        if (isSelected) holder.circleimage.visibility=View.VISIBLE else holder.circleimage.visibility=View.INVISIBLE

    }

    override fun getItemCount(): Int {
    return images.size
    }

    fun setList(image: List<Attribute>) {
        images=image
        notifyDataSetChanged()
    }
    fun setOnClickColorListener(listener: onClickColorListener) {
        this.clickListener = listener
    }

    inner class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image:CircleImageView
        var circleimage:ImageView
        init {
            image=itemView.findViewById(R.id.img)
            circleimage=itemView.findViewById(R.id.circleimage)
            itemView.setOnClickListener {
                val previousPosition = selectedPosition
                selectedPosition = adapterPosition
                notifyItemChanged(previousPosition)
                notifyItemChanged(selectedPosition)
                clickListener?.onClickColor(images.get(adapterPosition).value,images.get(adapterPosition).images)
            }
        }

    }
    interface onClickColorListener {
        fun onClickColor(value: String, images: List<String>,)
    }
}

