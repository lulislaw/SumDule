package com.lul.dulesum

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.lul.dulesum.databinding.ActivityMainBinding
import io.paperdb.Paper


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var homeFragment: HomeFragment
    private lateinit var settingsFragment: SettingsFragment
    private lateinit var searchFragment: SearchFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        homeFragment = HomeFragment.newInstance()
        settingsFragment = SettingsFragment.newInstance()
        searchFragment = SearchFragment.newInstance()
        Paper.init(this)
        Paper.book("main").write("launch", 0)
        setfragment(homeFragment)
        binding.bottomNavView.setOnNavigationItemSelectedListener {
            when(it.itemId)
            {
                R.id.menu_home ->{
                setfragment(homeFragment)
                      }
                R.id.menu_settings ->{
                    setfragment(settingsFragment)
                }
                R.id.menu_search ->{
                    setfragment(searchFragment)
                }
            }
            true
        }
    }
    fun setfragment(fragment: Fragment)
    {
        supportFragmentManager.beginTransaction().replace(R.id.frameContainer,fragment).commit()
    }
}