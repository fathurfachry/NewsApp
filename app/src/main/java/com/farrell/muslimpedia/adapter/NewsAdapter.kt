package com.farrell.muslimpedia.adapter

import android.content.Intent
import android.provider.ContactsContract.Data
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.farrell.muslimpedia.R
import com.farrell.muslimpedia.data.model.ArticlesItem
import com.farrell.muslimpedia.data.model.NewsResponse
import com.farrell.muslimpedia.databinding.ItemRowNewsBinding
import com.farrell.muslimpedia.ui.details.DetailNewsActivity
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.Locale

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.MyViewHolder>() {



    private val listNews = ArrayList<ArticlesItem>()

    fun setData(list:List<ArticlesItem>?){
        listNews.clear()
        if (list != null){
            listNews.addAll(list)
        }
        notifyItemRangeChanged(0,listNews.size)
    }

    class MyViewHolder(val binding:ItemRowNewsBinding):
    RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapter.MyViewHolder =
        MyViewHolder(ItemRowNewsBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    override fun onBindViewHolder(holder: NewsAdapter.MyViewHolder, position: Int) {
        val dataNews = listNews[position]

        val dateTimeString = dataNews.publishedAt
        val dateTimeFormat = SimpleDateFormat("yyyy-MM-hh'T'HH:mm:ss'Z'", Locale.getDefault())
        val date = dateTimeFormat.parse(dateTimeString)
        val myDateFormat = SimpleDateFormat("EEE,dd MMMM | HH:mm", Locale.getDefault())

        val myDate = myDateFormat.format(date)


        holder.binding.apply {
            tvSource.text = dataNews.source.name
            tvTitle.text = dataNews.title
            tvDate.text = myDate
            Picasso.get()
                .load(dataNews.urlToImage)
                .fit()
                .centerInside()
                .placeholder(R.drawable.ic_logo)
                .into(imgNews)
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailNewsActivity::class.java)
            intent.putExtra(DetailNewsActivity.DATA_NEWS,dataNews)
            intent.putExtra(DetailNewsActivity.DATE_NEWS,myDate)
            holder.itemView.context.startActivity(intent)
        }
    }


    override fun getItemCount(): Int = listNews.size
}