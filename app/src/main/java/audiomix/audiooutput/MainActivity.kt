package audiomix.audiooutput

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.media.AudioManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner



class MainActivity : AppCompatActivity(){


  lateinit var audioManager : AudioManager
  var intDisplay=true


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
      permissionAsker();
    audioManager= getSystemService(Context.AUDIO_SERVICE) as AudioManager
    val audioSourceSpinner=findViewById(R.id.spinnerAudioOut) as Spinner //get the spinner
    val audioSourceItems= resources.getStringArray(R.array.audio_output) as Array<String> //get data from xml
    /*create adapater*/
    val audioSourceAdapter= ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,audioSourceItems)
    audioSourceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    audioSourceSpinner.adapter=audioSourceAdapter
    //listen for change on spinner
    audioSourceSpinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
      override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View, position: Int, id: Long) {
        if(!intDisplay) forceAudio(audioSourceAdapter.getItem(position))
        intDisplay=false
      }

      override fun onNothingSelected(parentView: AdapterView<*>) {
        // your code here
      }

    })





  }

  fun forceAudio(audioOutput : String){
    Log.d("audio", audioManager.mode.toString())
    /// audioManager.mode=AudioManager.MODE_CURRENT
    when(audioOutput){
      "Speaker"->{
        audioManager.isSpeakerphoneOn=true
         audioManager.mode=1
        Log.d("audio", audioManager.mode.toString())


      }
      "Headphone"->{
        audioManager.mode=2;
        Log.d("audio", audioManager.mode.toString())
        //Log.d("audio", "spekaer off")
        audioManager.isSpeakerphoneOn=false
      }
    }
  }
  fun permissionAsker(){
      if (ContextCompat.checkSelfPermission(this,  Manifest.permission.MODIFY_AUDIO_SETTINGS) != PackageManager.PERMISSION_GRANTED){
          ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.MODIFY_AUDIO_SETTINGS),1)
      }else{
          Log.d("audio","output ok")
      }
  }



}
