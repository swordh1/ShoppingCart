package com.example.ethieladiassa.shoppingcart

import android.content.Context
import android.graphics.BitmapFactory
import android.support.design.widget.Snackbar
import android.support.v7.widget.RecyclerView
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.cart_list_item.view.*
import kotlinx.android.synthetic.main.cart_list_item.view.product_image
import kotlinx.android.synthetic.main.cart_list_item.view.product_name
import kotlinx.android.synthetic.main.cart_list_item.view.product_price
import kotlinx.android.synthetic.main.product_row_item.view.*

class ShoppingCartAdapter(var context: Context, var cartItems: List<CartItem>) :
    RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ShoppingCartAdapter.ViewHolder {

        val layout = LayoutInflater.from(context).inflate(R.layout.cart_list_item, parent, false)

        return ViewHolder(layout)
    }

    override fun getItemCount(): Int = cartItems.size

    override fun onBindViewHolder(viewHolder: ShoppingCartAdapter.ViewHolder, position: Int) {

        viewHolder.bindItem(cartItems[position])
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindItem(cartItem: CartItem) {
            val imageBytes = Base64.decode(cartItem.product.photos[0].filename, Base64.DEFAULT)
            val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
            itemView.product_image.setImageBitmap(decodedImage)
            itemView.addToCart2.setOnClickListener { view ->
                ShoppingCart.addItem(CartItem(cartItem.product))
                var value = cartItem.quantity
                itemView.product_quantity.text = value.toString()
                Toast.makeText(itemView.context, "quantity added", Toast.LENGTH_SHORT).show()
            }
            itemView.removeItem2.setOnClickListener { view ->
                ShoppingCart.removeItem(CartItem(cartItem.product), itemView.context)
                itemView.product_quantity.text = cartItem.quantity.toString()
                Toast.makeText(itemView.context, "quantity removed", Toast.LENGTH_SHORT).show()
            }

            itemView.product_name.text = cartItem.product.name
            itemView.product_price.text = "AED ${cartItem.product.price}"
            itemView.product_quantity.text = cartItem.quantity.toString()

        }



    }

}