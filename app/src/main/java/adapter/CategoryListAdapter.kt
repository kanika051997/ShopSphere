package adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.example.shopsphere.activity.MainActivity
import com.example.shopsphere.databinding.CategoryListBinding
import model.ProductList


class CategoryListAdapter(
    val context: MainActivity,
    var categories: List<String>,


    ) : RecyclerView.Adapter<CategoryListAdapter.ViewPagerHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerHolder {
        val binding =
            CategoryListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewPagerHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewPagerHolder, position: Int) {

        val label = categories[position]

        holder.item(label, context)

    }

    override fun getItemCount(): Int {
        return categories.size
    }

    class ViewPagerHolder(private var itemHolderBinding: CategoryListBinding) :
        RecyclerView.ViewHolder(itemHolderBinding.root) {

            fun item(label: String, mcontext: MainActivity) {

                itemHolderBinding.categoryName.text=label




                itemHolderBinding.categoryName.setOnClickListener {


                }

            }




    }





}
