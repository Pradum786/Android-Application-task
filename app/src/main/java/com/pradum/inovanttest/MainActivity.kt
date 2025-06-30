package com.pradum.inovanttest

import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel
import com.pradum.inovanttest.api.ProductService
import com.pradum.inovanttest.api.RetrofitInstance
import com.pradum.inovanttest.repository.ProductRepository
import com.pradum.inovanttest.viewmodels.MainViewModel
import com.pradum.inovanttest.viewmodels.MainViewModelFactory
import com.skydoves.expandablelayout.ExpandableLayout
import java.util.Objects

class MainActivity : AppCompatActivity() {

    lateinit var mainViewModel: MainViewModel
    val imgBannerAdapter = ImgBannerAdapter(this);
    lateinit var imageSlider: ImageSlider
    var Color_Name:TextView?=null
    val slideModels = mutableListOf<SlideModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val productService = RetrofitInstance.getInstance().create(ProductService::class.java)
        val productRepository = ProductRepository(productService);

        val imagbanner: RecyclerView = findViewById(R.id.img_banner)
        val title=findViewById<TextView>(R.id.title);
        val name=findViewById<TextView>(R.id.name);
        val prices=findViewById<TextView>(R.id.prices);
        val title_name=findViewById<TextView>(R.id.title_name);
        val sku_name=findViewById<TextView>(R.id.sku_name);
        val description=findViewById<TextView>(R.id.description);
        val expanded=findViewById<ConstraintLayout>(R.id.expanded);
        val expandableLayout=findViewById<ExpandableLayout>(R.id.expandableLayout);
         Color_Name=findViewById<TextView>(R.id.Color_Name);

        imagbanner.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        imagbanner.adapter = imgBannerAdapter
        imageSlider = findViewById(R.id.imagbanner)
        mainViewModel = ViewModelProvider(this, MainViewModelFactory(productRepository)).get(MainViewModel::class.java)

        expanded.setOnClickListener {
            if (expandableLayout.isExpanded) {
                expandableLayout.collapse()
                description.visibility=View.INVISIBLE
            }else{
                expandableLayout.expand()
                description.visibility=View.VISIBLE
            }
        }

        mainViewModel.liveData.observe(this, Observer {
            imgBannerAdapter.setList(it.data.configurable_option.get(0).attributes);
            for (image in it.data.images) {
                slideModels.add(SlideModel(image))
            }
            imageSlider.setImageList(slideModels)
            title.text=it.data.name
            name.text=it.data.brand_name
            prices.text=it.data.price
            title_name.text=it.data.name
            sku_name.text=it.data.sku
            description.text=Html.fromHtml(it.data.description,Html.FROM_HTML_MODE_LEGACY)

        })

        imgBannerAdapter.setOnClickColorListener( object : ImgBannerAdapter.onClickColorListener {
            override fun onClickColor(value: String, images: List<String>) {
                Color_Name?.text=value
                slideModels.clear()
                imageSlider.setImageList(emptyList())
                for (image in images){
                    slideModels.add(SlideModel(image))}
                imageSlider.setImageList(slideModels)
            }

        })

    }


}