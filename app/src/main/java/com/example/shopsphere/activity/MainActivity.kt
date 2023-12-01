package com.example.shopsphere.activity


import InternetConnection.NetworkUtils
import adapter.CategoryListAdapter
import adapter.ProductListAdapter
import adapter.ProductPagerAdapter
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.shopsphere.R
import com.example.shopsphere.databinding.ActivityMainBinding
import model.ListCategories
import model.ProductList
import rest.ApiClient
import rest.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    var categoryListAdapter: CategoryListAdapter? = null
    var productListAdapter: ProductListAdapter? = null
    var internetConnection:RelativeLayout?=null
    private lateinit var productPagerAdapter: ProductPagerAdapter
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable
    private var dotsCount: Int = 0
    private var dots: Array<ImageView?> = emptyArray()
    private val autoScrollDelayMillis: Long = 3000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)


        /* End Filter List UI Design*/
        if (NetworkUtils.isNetworkAvailable(this)) {
            internetConnection!!.visibility = View.GONE
        } else {
            internetConnection!!.visibility = View.VISIBLE
            binding.loadingShimmer.setVisibility(View.GONE)
            binding.shimmerViewContainer.setVisibility(View.GONE)
            binding.shimmerViewContainer.stopShimmerAnimation()
        }
    }



    fun categoryList() {



        val apiService = ApiClient.getResponse().create(
            ApiInterface::class.java
        )
        val call: Call<ListCategories> = apiService.filterList

        call.enqueue(object : Callback<ListCategories> {
            override fun onResponse(call: Call<ListCategories>, response: Response<ListCategories>) {

                val categoryList = response.body()!!.getCategories()
                categoryListAdapter = CategoryListAdapter(this@MainActivity,categoryList)

                binding. categorylist.layoutManager = LinearLayoutManager(
                    this@MainActivity,
                    LinearLayoutManager.VERTICAL,
                    false
                )

                binding. categorylist.setHasFixedSize(true)
                binding. categorylist.adapter = categoryListAdapter


                getProductList(categoryList[0])
            }

            override fun onFailure(call: Call<ListCategories>, t: Throwable) {

            }
        })
    }

    fun getProductList(category:String) {

        val apiService = ApiClient.getResponse().create(
            ApiInterface::class.java
        )
        val call: Call<ProductList> = apiService.getFetchFilterList(category)

        call.enqueue(object : Callback<ProductList> {
            override fun onResponse(call: Call<ProductList>, response: Response<ProductList>) {

                val productList = response.body()!!.getProducts()
                productListAdapter = ProductListAdapter(this@MainActivity,productList)

                binding. categorylist.layoutManager = LinearLayoutManager(
                    this@MainActivity,
                    LinearLayoutManager.VERTICAL,
                    false
                )

                binding. categorylist.setHasFixedSize(true)
                binding. categorylist.adapter = productListAdapter

            }

            override fun onFailure(call: Call<ProductList>, t: Throwable) {

            }
        })
    }

    fun productionDescriptionPopup(
        name: String,
        rating: String,
        desrip: String,
        images: MutableList<String>,
        price: Double
    ){

        val productionDescription = Dialog(this@MainActivity)
        productionDescription.requestWindowFeature(Window.FEATURE_NO_TITLE)
        productionDescription.setContentView(R.layout.product_description)
        var productname: TextView = productionDescription.findViewById<TextView>(R.id.product_name)
        var rating: TextView = productionDescription.findViewById<TextView>(R.id.rating)
        var descrip: TextView = productionDescription.findViewById<TextView>(R.id.descrip)
        var price: TextView = productionDescription.findViewById<TextView>(R.id.price)
        var viewPager: ViewPager2 = productionDescription.findViewById<ViewPager2>(R.id.slidingViewPager)
        var slider_dots: LinearLayout = productionDescription.findViewById<LinearLayout>(R.id.slider_dots)



        productname.text=name
        descrip.text= descrip.toString()
        rating.text=rating.toString()
        price.text=rating.toString()

        // Create your ViewPager2 adapter
        productPagerAdapter = ProductPagerAdapter(images,this@MainActivity)
        viewPager.adapter = productPagerAdapter

        // Set an initial page
        viewPager.setCurrentItem(0, false)

        // Get the number of pages in your adapter
        dotsCount = productPagerAdapter.itemCount

        // Create dots
        createDots(viewPager,slider_dots)

        // Auto-scroll with a delay
        handler = Handler(Looper.getMainLooper())
        startAutoScroll(autoScrollDelayMillis,viewPager)

        productionDescription.show()
        productionDescription.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        productionDescription.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        productionDescription.window?.setGravity(Gravity.CENTER)
        productionDescription.setCancelable(true)
        productionDescription.setCanceledOnTouchOutside(true)
    }

    private fun createDots(viewPager: ViewPager2, sliderDots: LinearLayout) {
        dots = Array(dotsCount) { null }

        for (i in 0 until dotsCount) {
            dots[i] = ImageView(this)
            dots[i]?.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.non_active_dot
                )
            )

            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(8, 0, 8, 0)
            sliderDots.addView(dots[i], params)
        }

        // Set the initial active dot
        dots[0]?.setImageDrawable(
            ContextCompat.getDrawable(
                this,
                R.drawable.active_dot
            )
        )

        // Set ViewPager2 callback to update dots on page change
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                updateDots(position)
            }
        })
    }

    private fun updateDots(currentPosition: Int) {
        for (i in 0 until dotsCount) {
            dots[i]?.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    if (i == currentPosition) R.drawable.active_dot else R.drawable.non_active_dot
                )
            )
        }
    }

    private fun startAutoScroll(delayMillis: Long, viewPager: ViewPager2) {
        runnable = Runnable {
            val currentItem = viewPager.currentItem
            viewPager.setCurrentItem(
                if (currentItem < dotsCount - 1) currentItem + 1 else 0,
                true
            )
            handler.postDelayed(runnable, delayMillis)
        }

        handler.postDelayed(runnable, delayMillis)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable)
    }
}