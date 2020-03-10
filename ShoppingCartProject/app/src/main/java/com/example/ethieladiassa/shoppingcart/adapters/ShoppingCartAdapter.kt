package com.example.ethieladiassa.shoppingcart.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.ethieladiassa.shoppingcart.classes.CartItem
import com.example.ethieladiassa.shoppingcart.R
import com.example.ethieladiassa.shoppingcart.classes.ShoppingCart
import com.example.ethieladiassa.shoppingcart.activities.ShoppingCartActivity
import kotlinx.android.synthetic.main.activity_shopping_cart.*
import kotlinx.android.synthetic.main.cart_list_item.view.*
import kotlinx.android.synthetic.main.cart_list_item.view.product_image
import kotlinx.android.synthetic.main.cart_list_item.view.product_name
import kotlinx.android.synthetic.main.cart_list_item.view.product_price

class ShoppingCartAdapter(private var context: Context, private var cartItems: List<CartItem>) :
    RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val layout = LayoutInflater.from(context).inflate(R.layout.cart_list_item, parent, false)
        return ViewHolder(layout)
    }

    override fun getItemCount(): Int = cartItems.size
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bindItem(cartItems[position])
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        @SuppressLint("SetTextI18n")
        fun bindItem(cartItem: CartItem) {
            // Displaying the image in the image view by decoding the Base64 to Bitmap

            val imageIndex = cartItem.product.photos
            val imgs = arrayOf(R.drawable.potato, R.drawable.carrots, R.drawable.onions)
            itemView.product_image.setImageResource(imgs[imageIndex])

            // Recycling through adding or removing items from the shopping cart
            itemView.addToCart2.setOnClickListener {
                ShoppingCart.addItem(CartItem(cartItem.product))
                val value1 = ++cartItem.quantity
                itemView.product_quantity.text = value1.toString()
                val totalPrice = ShoppingCart.getCart()
                    .fold(0.toDouble()) { acc, cartItem -> acc + cartItem.quantity.times(cartItem.product.price!!.toDouble()) }
                (itemView.context as ShoppingCartActivity).total_price.text = "AED $totalPrice"
            }
            itemView.removeItem2.setOnClickListener {
                ShoppingCart.removeItem(CartItem(cartItem.product), itemView.context)
                val value = --cartItem.quantity
                if(cartItem.quantity <= 0){
                    itemView.visibility = View.GONE
                }
                else{
                    itemView.product_quantity.text = value.toString()
                }
                val totalPrice = ShoppingCart.getCart()
                    .fold(0.toDouble()) { acc, cartItem -> acc + cartItem.quantity.times(cartItem.product.price!!.toDouble()) }
                (itemView.context as ShoppingCartActivity).total_price.text = "AED $totalPrice"
            }

            itemView.product_name.text = cartItem.product.name
            itemView.product_price.text = "AED ${cartItem.product.price}"
            itemView.product_quantity.text = cartItem.quantity.toString()

        }



    }

}
