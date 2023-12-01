package adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shopsphere.R
import com.example.shopsphere.activity.MainActivity

class ProductPagerAdapter (private val images: List<String>, var mainActivity: MainActivity) : RecyclerView.Adapter<ProductPagerAdapter.PagerViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pager, parent, false)
        return PagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        Glide.with(mainActivity)
            .load(images[position])
            .into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    inner class PagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }
}