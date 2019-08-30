package mdev.k016_databindinginrecyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mdev.k016_databindinginrecyclerview.databinding.ItemListBinding

class CustomAdapter(var dataSet:ArrayList<ModelItem>): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private var mClickItem: clickItem? = null
    override fun getItemViewType(position: Int): Int {
        return dataSet[position].viewType
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when(viewType){
            ViewType.TYPE_ONE ->{
                val layoutInflater = LayoutInflater.from(parent!!.context)
                val binding = ItemListBinding.inflate(layoutInflater, parent, false)
                return ViewItemOne(binding)
            }
            else -> {
                throw NullPointerException("View holder for type $viewType not found")
            }
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = dataSet[position]
        if (holder is ViewItemOne){
            holder.binding.car = data
            holder.binding.lnMainItem.setOnClickListener{
                mClickItem?.onClickItem(position,data.name,data.price)
            }
        }
    }
    class ViewItemOne(var binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root)

    fun setOnClickItem(rClickItem: clickItem){
        mClickItem = rClickItem
    }
    interface clickItem{
        fun onClickItem(position: Int, name: String?, price: String?)
    }
}