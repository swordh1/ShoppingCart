package com.example.ethieladiassa.shoppingcart.activities

// List of imports required to run the main activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.StaggeredGridLayoutManager
import com.example.ethieladiassa.shoppingcart.*
import com.example.ethieladiassa.shoppingcart.adapters.ProductAdapter
import com.example.ethieladiassa.shoppingcart.classes.Product
import com.example.ethieladiassa.shoppingcart.classes.ShoppingCart
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    // File for product description, id, name, prices and quantities
    private lateinit var productAdapter: ProductAdapter

    // Update the cart value in the main activity
    override fun onResume() {
        cart_size.text = ShoppingCart.getShoppingCartSize().toString()
        super.onResume()
    }

    // What happens when you start the app
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initializing Paper database
        Paper.init(this)

        // Sets the first activity when starting the app as activity_main.xml
        setContentView(R.layout.activity_main)

        // Adding Toolbar
        setSupportActionBar(toolbar)

        // sets the scheme color to primary swipe down
        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorPrimary))

        // make sure to refresh if swiped down
        swipeRefreshLayout.isRefreshing = true

        // When swiped down call function getproducts()
        swipeRefreshLayout.setOnRefreshListener {
            getProducts()
        }

        // Sets the main activity recycle item viewer as vertical with 3 itemviews
        products_recyclerview.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        // updates the quantity of items in the cart button
        cart_size.text = ShoppingCart.getShoppingCartSize().toString()

        // calls function
        getProducts()

        // when cart button is clicked, it goes to the shopping cart activity
        showCart.setOnClickListener {
            startActivity(Intent(this, ShoppingCartActivity::class.java))
        }

    }

    private fun getProducts() {
        swipeRefreshLayout.isRefreshing = false

        // Statically adding the product list for the recycle view in the main activity
        val productList = listOf(
            Product("Description", 1, "Potatoes", "5", 0),
            Product("Description", 2, "Carrots", "4", 1),
            Product("Description", 3, "Onions", "2", 2)
        )
        // calls the product adapter
        productAdapter = ProductAdapter(this@MainActivity, productList)
        products_recyclerview.adapter = productAdapter
    }
}