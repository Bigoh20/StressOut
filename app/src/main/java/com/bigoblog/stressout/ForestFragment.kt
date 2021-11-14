package com.bigoblog.stressout

import android.app.Activity
import android.media.MediaPlayer
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.*
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bigoblog.stressout.databinding.FragmentForestBinding
import com.bigoblog.stressout.extras.CatEntity
import com.bigoblog.stressout.extras.ForestEntity
import com.bigoblog.stressout.extras.OnClickListener
import com.bigoblog.stressout.extras.toast
import com.bigoblog.stressout.recyclersviews.ForestAdapter

class ForestFragment : Fragment(R.layout.fragment_forest), OnClickListener {

    private lateinit var binding : FragmentForestBinding

    //Crear los sonidos:
     private var soundFogata : MediaPlayer? = null
    private var soundLluvia : MediaPlayer? = null
    private var soundViento : MediaPlayer? = null
    private var soundNieve : MediaPlayer? = null
    private var soundPajaros : MediaPlayer? = null
    private var soundPlaya : MediaPlayer? = null
    private var soundRio : MediaPlayer? = null
    private var soundSelected1 : MediaPlayer? = null
    //Crear la lista para enviar al recyclerview.
    private var forestList = mutableListOf<ForestEntity>()
    private var soundsList = mutableListOf<MediaPlayer>()
    //Crear las variables m.
    var mIsLoopMode = false
    var mIsStackableMode = false
    var mActivity : MainActivity? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentForestBinding.inflate(inflater, container, false)

        setupTopMenu()
        soundsList = setupSounds()
        setupList()
        setupRecyclerView()

        return binding.root
    }

    private fun setupTopMenu() {
        mActivity = activity as? MainActivity
        setHasOptionsMenu(true)
    }

    private fun setupList() {
        forestList = mutableListOf(
            ForestEntity("Fogata", R.drawable.img_fogatarealistic,  forestSound = soundFogata),
            ForestEntity("Lluvia", R.drawable.img_lluvia,  forestSound = soundLluvia),
            ForestEntity("Viento", R.drawable.img_viento,  forestSound = soundViento),
            ForestEntity("Nieve", R.drawable.img_nieve,  forestSound = soundNieve),
            ForestEntity("Pájaros", R.drawable.img_pajaros,  forestSound = soundPajaros),
            ForestEntity("Playa", R.drawable.img_playa,  forestSound = soundPlaya),
            ForestEntity("Rio", R.drawable.img_rio,  forestSound = soundRio)

        )
    }


    private fun setupRecyclerView() {
            binding.rvForest.adapter = ForestAdapter(forestList, this)
            binding.rvForest.layoutManager = LinearLayoutManager(activity)

    }

    override fun setOnClickListener(forest : ForestEntity) {

        forest.isSelected = !forest.isSelected

        if(forest.isSelected) {

            if(mIsStackableMode){
                forest.forestSound?.start()
                Log.i("SystemBigo", "Sondio reproducido")
                setOnCompletionListenerEvent(forest)
            }else{
                //Si no es modo en pila desactivar todos.
                setdownSounds()
                //Ahora iniciar el sonido actual.
                forest.forestSound?.start()
                //Ponerlo que está seleccionado:
                forest.isSelected = true
                //Y ubicar el elemento en caso de que haya un loop
                setOnCompletionListenerEvent(forest)
            }


        }
        else{
              //Si ya estaba seleccionado reiniciarlo.
            forest.forestSound?.pause()
            forest.forestSound?.seekTo(0)
            Log.i("SystemBigo", "Sondio Pausado y reiniciado")

        }


    }



    private fun setOnCompletionListenerEvent(forest: ForestEntity) {

        //Si ya terminó verificar si es un loop
        forest.forestSound?.setOnCompletionListener {
            forest.isSelected = !forest.isSelected //Al terminar la pieza poner que ya no está seleccionado,

            //En caso de ser un loop hacer recursividad y arriba se pondrá de nuevo como seleccionado:
            if(mIsLoopMode){
                setOnClickListener(forest)
                Log.i("SystemBigo", "Loop again")
            }

        }
        Log.i("SystemBigo", "Sondio reproducido")
    }



    override fun setdownSounds() {

        //Colorcar un loop para que desactive todos.
        for(currentSound in soundsList.indices){
            if(soundsList[currentSound].isPlaying) {
                soundsList[currentSound].pause()
                soundsList[currentSound].seekTo(0)
                forestList[currentSound].isSelected = false
            }
        }

    }

    override fun setupSounds() : MutableList<MediaPlayer>{
        soundFogata = MediaPlayer.create(activity, R.raw.fotaga_sound)
        soundLluvia = MediaPlayer.create(activity, R.raw.lluvia_sound)
        soundViento = MediaPlayer.create(activity, R.raw.viento_sound)
        soundNieve = MediaPlayer.create(activity, R.raw.nieve_sound)
        soundPajaros = MediaPlayer.create(activity, R.raw.pajaro_sound)
        soundPlaya = MediaPlayer.create(activity, R.raw.playa_sound)
        soundRio = MediaPlayer.create(activity, R.raw.river_sound)
        soundSelected1 = MediaPlayer.create(activity, R.raw.button_selected)

        return mutableListOf<MediaPlayer>(soundFogata!!, soundLluvia!!, soundViento!!, soundNieve!!, soundPajaros!!,
                                          soundPlaya!!, soundRio!!)
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
                } else {
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
                } else {
                    Toast.makeText(activity, "Modo múltiple desactivado", Toast.LENGTH_SHORT).show()
                    item.setIcon(R.drawable.ic_multiple)
                    soundSelected1?.start()
                }
                return true

            }
            R.id.stopableItem -> {
                setdownSounds()
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
        setdownSounds()
        setHasOptionsMenu(false)

        //Reiniciar los valores m.
        mIsLoopMode = false
        mIsStackableMode = false
        super.onDestroy()
    }
}