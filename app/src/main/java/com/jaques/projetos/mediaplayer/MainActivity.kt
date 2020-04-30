package com.jaques.projetos.mediaplayer

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import com.jaques.projetos.mediaplayer.R.id.seekBar_volume
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var seekbarVolume: SeekBar
    private lateinit var audioManager: AudioManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mediaPlayer = MediaPlayer.create(applicationContext, R.raw.teste)
        inicializarSeekbar()

    }

     fun inicializarSeekbar() {
            seekbarVolume = seekBar_volume

        //Configura audio manager
            audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager

        //Recuperar valores de volume máximo e o volume Atual
        val volumeMaximo: Int = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        val volumeAtual: Int = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)

        //configura os valores máximo para seekbar
        seekbarVolume.max = volumeMaximo
        //configura os progresso atual do seekbar
        seekbarVolume.progress = volumeAtual

        seekbarVolume.setOnSeekBarChangeListener( object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
             audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0)
               Toast.makeText(applicationContext, "seekbar progress: $progress", Toast.LENGTH_SHORT).show()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                Toast.makeText(applicationContext,"start tracking",Toast.LENGTH_SHORT).show()
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                Toast.makeText(applicationContext,"stop tracking",Toast.LENGTH_SHORT).show()
            }

        })

    } 

    fun executarSom(view: View) {
        if (mediaPlayer != null) {
            mediaPlayer.start()
        }
    }

    fun pausarMusica(view: View) {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
        }
    }

    fun pararMusica(view: View){
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()

            mediaPlayer = MediaPlayer.create(applicationContext, R.raw.teste)
        }
    }

    override fun onStop() {
        super.onStop()
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
        }
    }

    override fun onDestroy(){
        super.onDestroy()
        if(mediaPlayer.isPlaying){
            mediaPlayer.run {
                stop()
                release()
            }
        }
    }

}
