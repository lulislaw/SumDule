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
    private lateinit var cabinetFragment: CabinetFragment

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
        cabinetFragment = CabinetFragment.newInstance()
        fragments = arrayListOf(homeFragment,searchFragment,newsFragment,cabinetFragment,settingsFragment)
        boolFrag =  BooleanArray(fragments.size)
        Paper.init(this)
        if (Paper.book("main").read<ArrayList<ItemSubject>>("subjects") != null){
            Paper.book("main").write("launch", 0)
            setfragment(0)
        }
        else
        {
            setfragment(3)
        }

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
                R.id.menu_cabinet -> {
                    setfragment(3)
                }
                R.id.menu_settings -> {
                    setfragment(4)
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