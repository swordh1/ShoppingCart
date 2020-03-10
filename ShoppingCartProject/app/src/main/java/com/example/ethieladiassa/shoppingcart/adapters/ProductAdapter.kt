package com.example.ethieladiassa.shoppingcart.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.support.design.widget.Snackbar
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.ethieladiassa.shoppingcart.classes.CartItem
import com.example.ethieladiassa.shoppingcart.classes.Product
import com.example.ethieladiassa.shoppingcart.R
import com.example.ethieladiassa.shoppingcart.classes.ShoppingCart
import com.example.ethieladiassa.shoppingcart.activities.MainActivity
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.product_row_item.view.*


class ProductAdapter(private var context: Context, private var products: List<Product> = arrayListOf()) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.product_row_item, parent, false)
        return ViewHolder(view)

    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.bindProduct(products[position])
        (context as MainActivity).coordinator

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        @SuppressLint("CheckResult", "SetTextI18n")
        fun bindProduct(product: Product) {

            itemView.product_name.text = product.name
            itemView.product_price.text = "AED ${product.price.toString()}"
            val imageIndex = product.photos

            // Changed as requested, kept for reference
/*          val imageBytes = Base64.decode(product.photos[0].filename, Base64.DEFAULT)
            val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
            itemView.product_image.setImageBitmap(decodedImage)*/

            // Selects drawable from array list based on product index
            val imgs = arrayOf(R.drawable.potato, R.drawable.carrots, R.drawable.onions)
            itemView.product_image.setImageResource(imgs[imageIndex])

            Observable.create(ObservableOnSubscribe<MutableList<CartItem>> {

                itemView.addToCart.setOnClickListener { _ ->

                    val item = CartItem(product)
                    ShoppingCart.addItem(item)
                    Snackbar.make(
                        (itemView.context as MainActivity).coordinator,
                        "${product.name} added to your cart",
                        Snackbar.LENGTH_LONG
                    ).show()
                    it.onNext(ShoppingCart.getCart())

                }

                itemView.removeItem.setOnClickListener { _ ->

                    val item = CartItem(product)
                    ShoppingCart.removeItem(item, itemView.context)
                    Snackbar.make(
                        (itemView.context as MainActivity).coordinator,
                        "${product.name} removed from your cart",
                        Snackbar.LENGTH_LONG
                    ).show()
                    it.onNext(ShoppingCart.getCart())

                }


            }).subscribe { cart ->

                var quantity = 0
                cart.forEach { cartItem ->
                    quantity += cartItem.quantity
                }
                (itemView.context as MainActivity).cart_size.text = quantity.toString()
//                Toast.makeText(itemView.context, "Cart size $quantity", Toast.LENGTH_SHORT).show()
            }
        }
    }
}