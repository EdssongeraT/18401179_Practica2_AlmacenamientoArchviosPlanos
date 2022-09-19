package mx.edu.ittepic.a18401179_practica2_almacenamientoarchviosplanos

import android.content.ClipData
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import java.io.BufferedReader
import java.io.InputStreamReader

class CustomAdapter(private val onItemClick:(posicion:Int)->Unit): RecyclerView.Adapter<CustomAdapter.ViewHolder>() {
    var titles :Array<String> = emptyArray()
    var types :Array<String> = emptyArray()
    var prices :Array<String> = emptyArray()

    inner class ViewHolder(itemView: View,private val onItemClick: (posicion: Int) -> Unit): RecyclerView.ViewHolder(itemView),View.OnClickListener{
        var itemImage: ImageView
        var itemTitle: TextView
        var itemType: TextView
        var itemPrice: TextView

        init {
            itemView.setOnClickListener({_ ->
                onItemClick(adapterPosition)
            })
            itemImage = itemView.findViewById(R.id.item_image)
            itemTitle = itemView.findViewById(R.id.item_title)
            itemType = itemView.findViewById(R.id.item_type)
            itemPrice = itemView.findViewById(R.id.item_price)

        }

        override fun onClick(p0: View?) {
            TODO("Not yet implemented")
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.card_layout,viewGroup,false)
        return ViewHolder(v,onItemClick)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.itemTitle.text = titles[i]
        viewHolder.itemType.text = types[i]
        viewHolder.itemPrice.text = prices[i]

    }


    override fun getItemCount(): Int {
        return titles.size
    }
}