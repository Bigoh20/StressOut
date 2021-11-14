package com.bigoblog.stressout

import android.app.Activity
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bigoblog.stressout.databinding.FragmentCatBinding
import com.bigoblog.stressout.extras.CatEntity
import com.bigoblog.stressout.extras.OnClickListener
import com.bigoblog.stressout.recyclersviews.CatAdapter

class CatFragment : Fragment(R.layout.fragment_cat), OnClickListener {
    private lateinit var binding: FragmentCatBinding
//Crear los sonidos.
    private var soundCat1 : MediaPlayer? = null
    private var soundCat2 : MediaPlayer? = null
    private var soundCat3 : MediaPlayer? = null
    private var soundCat4 : MediaPlayer? = null
    private var soundCat5 : MediaPlayer? = null
    private var soundSelected1 : MediaPlayer? = null
    private var catsArray = mutableListOf<CatEntity>()
    private var soundsArray = mutableListOf<MediaPlayer>()
    //Crear la variable para saber si es loop y multiple
    private var mIsLoopMode = false
    private var mIsStackableMode = false
    var mAdapter : CatAdapter? = null



    //Crear el activity
    private var mActivity : MainActivity? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         binding = FragmentCatBinding.inflate(inflater, container, false)

        setupTopMenu()
       soundsArray = setupSounds()
        setupList()
        setupRecyclerView()
        return binding.root
    }

    private fun setupTopMenu() {
        mActivity = activity as? MainActivity
        setHasOptionsMenu(true)

    }

    private fun setupList() {
        catsArray = mutableListOf(
            CatEntity("Canela", "Gato comelón", R.drawable.img_catbrown, catSound = soundCat1),
            CatEntity("Nevado", "Gato triste :(", R.drawable.img_nevadito, catSound = soundCat2),
            CatEntity("Bigotitos", "Gato tímido", R.drawable.img_hemosho, catSound = soundCat3),
            CatEntity("Dorado", "Gato bebé", R.drawable.img_catrabos, catSound = soundCat4),
            CatEntity("Naranja", "Gato travieso", R.drawable.img_catnaranja, catSound = soundCat5)
        )
    }

    private fun setupRecyclerView() {
        mAdapter = CatAdapter(catsArray, this)
        binding.rvCats.adapter = mAdapter
        binding.rvCats.layoutManager = LinearLayoutManager(activity)

    }

    //Cada vez que se presione el cardview del gato.
    override fun setOnClickListener(cat: CatEntity) {

        cat.isSelected = !cat.isSelected

        if(cat.isSelected) {



            if(mIsStackableMode){
                cat.catSound?.start()


                setOnCompletionListenerEvent(cat)
            }else{
                //Si no es modo en pila desactivar todos.
                setdownSounds()
                //Ahora iniciar el sonido actual.
                cat.catSound?.start()
                //Ponerlo que está seleccionado:
                cat.isSelected = true
                //Y ubicar el elemento en caso de que haya un loop
                setOnCompletionListenerEvent(cat)
            }



        } else{
            cat.catSound?.pause()
            cat.catSound?.seekTo(0)
            Log.i("SystemBigo", "Sondio Pausado y reiniciado")
        }


    }

    private fun setOnCompletionListenerEvent(cat: CatEntity) {

        //Si ya terminó verificar si es un loop
        cat.catSound?.setOnCompletionListener {
            cat.isSelected = !cat.isSelected //Al terminar la pieza poner que ya no está seleccionado,

            //En caso de ser un loop hacer recursividad y arriba se pondrá de nuevo como seleccionado:
            if(mIsLoopMode){

                setOnClickListener(cat)
                Log.i("SystemBigo", "Loop again")
            }



        }
        Log.i("SystemBigo", "Sondio reproducido")
    }

    override fun setupSounds() : MutableList<MediaPlayer>{


        soundCat1 = MediaPlayer.create(activity, R.raw.gato_ronroneo1)

        soundCat1?.setVolume(100F, 100F)
        soundCat2 = MediaPlayer.create(activity, R.raw.gato_ronroneo2)
        soundCat3 = MediaPlayer.create(activity, R.raw.gato_roroneo3)
        soundCat4 = MediaPlayer.create(activity, R.raw.gato_ronroneo4)
        soundCat5 = MediaPlayer.create(activity, R.raw.garo_ronroneo5)
         soundSelected1 = MediaPlayer.create(activity, R.raw.button_selected)

        return mutableListOf<MediaPlayer>(soundCat1!!, soundCat2!!, soundCat3!!,
                                          soundCat4!!, soundCat5!!)
    }

    override fun setdownSounds() {
        //Recorrer a todos los elementos.
        for(currentSound in soundsArray.indices){

            if(soundsArray[currentSound].isPlaying){
                soundsArray[currentSound].pause()
                soundsArray[currentSound].seekTo(0)
                catsArray[currentSound].isSelected = false
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.top_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.loopItem -> {
                mIsLoopMode = !mIsLoopMode

                if(mIsLoopMode) {
                    Toast.makeText(activity, "Modo en bucle activado", Toast.LENGTH_SHORT).show()
                    item.setIcon(R.drawable.loop_activated)
                    soundSelected1?.start()
                }
                else {
                    Toast.makeText(activity, "Modo en bucle desactivado", Toast.LENGTH_SHORT).show()
                    item.setIcon(R.drawable.ic_loop)
                    soundSelected1?.start()
                }

                return true
            }
            R.id.stackableItem -> {
                mIsStackableMode = !mIsStackableMode

                if(mIsStackableMode) {
                    Toast.makeText(activity, "Modo múltiple activado", Toast.LENGTH_SHORT).show()
                    item.setIcon(R.drawable.img_multiple_on)
                    soundSelected1?.start()
                }
                else {
                    Toast.makeText(activity, "Modo múltiple desactivado", Toast.LENGTH_SHORT).show()
                    item.setIcon(R.drawable.ic_multiple)
                    soundSelected1?.start()
                }
                return true

            }

            R.id.stopableItem -> {
                //Parar todos los sonidos.
                setdownSounds()
                //Reproudcir sonido de selección.
                soundSelected1?.start()
                Toast.makeText(activity, "Se han detenido todos los sonidos.", Toast.LENGTH_SHORT).show()
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)

            }
        }
    }

    override fun onDestroy() {
        setHasOptionsMenu(false)
        setdownSounds()
        //Reiniciar los valores m.
        mIsLoopMode = false
        mIsStackableMode = false
        super.onDestroy()
    }

}