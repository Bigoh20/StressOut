package com.bigoblog.stressout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.fragment.app.Fragment
import com.bigoblog.stressout.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
   private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        //Crear la instancia de cada fragment.
        val catFragment = CatFragment()
        val forestFragment = ForestFragment()
        val helpFragment = HelpFragment()

        //Inicializar por defecto este fragment fragment fragment.
        setCurrentFragment(helpFragment)


        binding.bottomNavigationView.setOnItemSelectedListener{
            when(it.itemId){
                R.id.item_cat -> setCurrentFragment(catFragment)
                R.id.item_forest -> setCurrentFragment(forestFragment)
                R.id.item_help -> setCurrentFragment(helpFragment)
            }
            true
        }



    }





    private fun setCurrentFragment(fragment : Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }
        //Ubicar el titulo correspondiente.
        when (fragment) {
            is CatFragment -> supportActionBar?.title = getString(R.string.title_cat)
            is ForestFragment -> supportActionBar?.title = getString(R.string.title_forest)
            else -> supportActionBar?.title = getString(R.string.title_forest)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }









}