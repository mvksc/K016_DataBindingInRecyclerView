package mdev.k016_databindinginrecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import mdev.k016_databindinginrecyclerview.databinding.ActivityMainBinding
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var customAdapter: CustomAdapter
    lateinit var dataSet: ArrayList<ModelItem>
    private var arrChar = 'A'..'Z'

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState!!.putParcelableArrayList("dataSet",dataSet)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        dataSet = savedInstanceState!!.getParcelableArrayList("dataSet")!!
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        initView()
        setOnClickItem()
        setDataToList()
    }

    private fun setOnClickItem() {
        customAdapter.setOnClickItem(object : CustomAdapter.clickItem{
            override fun onClickItem(position: Int, name: String?, price: String?) {
                dataSet.set(position,
                    ModelItem(dataSet.get(position).viewType,
                    dataSet.get(position).name + " [เลือกแล้ว]",
                    dataSet.get(position).price))
                customAdapter.notifyDataSetChanged()
            }
        })
    }

    private fun setDataToList() {
        val dec = DecimalFormat("#,###.00")
        for ((index, txtChar) in arrChar.withIndex()){
            dataSet.add(ModelItem(1, "สินค้า $txtChar ราคา ", "$" + dec.format((1000*(index + 1)))))
            customAdapter.notifyDataSetChanged()
        }
    }

    private fun initView() {
        dataSet = ArrayList()
        binding.rcyList.layoutManager = GridLayoutManager(this,1)
        binding.rcyList.setHasFixedSize(true)
        customAdapter = CustomAdapter(dataSet)
        binding.rcyList.adapter = customAdapter
        customAdapter.notifyDataSetChanged()
    }
}
