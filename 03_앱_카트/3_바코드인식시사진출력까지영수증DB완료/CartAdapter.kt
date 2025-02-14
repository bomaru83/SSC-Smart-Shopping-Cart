package com.example.ssc_cart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.in_cart.view.*
import java.lang.System.load


class CartAdapter(var items: MutableList<CartData>): RecyclerView.Adapter<CartAdapter.MainViewHolder>(){


    inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvCartProduct = itemView.tv_cart_product
        val tvCartPrice = itemView.tv_cart_price
        val tvCartNum = itemView.tv_cart_num
        val tvCartTotal = itemView.tv_cart_total
        val tvCartUrl = itemView.tv_cart_url
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.in_cart, parent, false)
        return MainViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        items[position].let { item ->
            with(holder) {
                tvCartProduct.text = item.product_name
                tvCartPrice.text = item.product_price.toString()
                tvCartNum.text = item.product_num.toString()
                tvCartTotal.text = item.product_total.toString()
                Glide.with(itemView.context).load(item.product_url.toString()).into(tvCartUrl)
            }
        }
    }
}