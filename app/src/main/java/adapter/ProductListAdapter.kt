package adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.example.shopsphere.activity.MainActivity
import com.example.shopsphere.databinding.ProductListBinding
import model.ProductList


class ProductListAdapter(
    val context: MainActivity,
    var productList: List<ProductList.Product>,


    ) : RecyclerView.Adapter<ProductListAdapter.ViewPagerHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerHolder {
        val binding =
            ProductListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewPagerHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewPagerHolder, position: Int) {

        val label = productList[position]

        holder.item(label, context)

    }

    override fun getItemCount(): Int {
        return productList.size
    }

    class ViewPagerHolder(private var itemHolderBinding: ProductListBinding) :
        RecyclerView.ViewHolder(itemHolderBinding.root) {

            fun item(label: ProductList.Product, mcontext: MainActivity) {

                itemHolderBinding.rating.text=label.brand
                itemHolderBinding.rating.text= label.rating.toString()
                itemHolderBinding.descrip.text= label.description.toString()

              itemHolderBinding.product.setOnClickListener {

                  mcontext.productionDescriptionPopup(label.brand,label.rating.toString(),label.description.toString(),label.images,label.price)
              }




            }




    }





}
