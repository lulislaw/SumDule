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
    private lateinit var newsFragment: NewsFragment

    lateinit var fragments: ArrayList<Fragment>
    lateinit var boolFrag: BooleanArray


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        homeFragment = HomeFragment.newInstance()
        settingsFragment = SettingsFragment.newInstance()
        searchFragment = SearchFragment.newInstance()
        newsFragment = NewsFragment.newInstance()
        fragments = arrayListOf(homeFragment,searchFragment,newsFragment,settingsFragment)
        boolFrag =  BooleanArray(fragments.size)
        Paper.init(this)
        Paper.book("main").write("launch", 0)

        setfragment(0)
        binding.bottomNavView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_home -> {
                    setfragment(0)
                }
                R.id.menu_search -> {
                    setfragment(1)
                }
                R.id.menu_news -> {
                    setfragment(2)
                }
                R.id.menu_settings -> {
                    setfragment(3)
                }

            }
            true
        }
    }

    fun setfragment(fragmentId: Int) {
        for(it in fragments)
        {

            supportFragmentManager.beginTransaction()
                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                .hide(it)
                .commit();
        }

        if(boolFrag.get(fragmentId))
        {
            supportFragmentManager.beginTransaction()
                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                .show(fragments.get(fragmentId))
                .commit();
        }else
        {
            supportFragmentManager.beginTransaction()
                .add(R.id.frameContainer,fragments.get(fragmentId))
                .commit()
            boolFrag.set(fragmentId,true)
            supportFragmentManager.beginTransaction()
                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                .show(fragments.get(fragmentId))
                .commit();
        }
    }
}