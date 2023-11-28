package com.farrell.muslimpedia

import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.widget.SearchView
import com.farrell.muslimpedia.adapter.SectionPagerAdapter
import com.farrell.muslimpedia.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        setupViewPager()
    }

    private fun setupViewPager() {
        binding.vpNews.adapter = SectionPagerAdapter(this)

        val tabsList = arrayOf(
            "Common,",
            "About Quran",
            "Al Jazeera",
            "Warning For Muslim"
        )
        TabLayoutMediator(binding.tabs,binding.vpNews){tab, page ->
            tab.text = tabsList[page]
        }.attach()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
            (menu?.findItem(R.id.menu_search)?.actionView as androidx.appcompat.widget.SearchView).apply {
                setSearchableInfo(searchManager.getSearchableInfo(componentName))
                return true
            }
    }

}