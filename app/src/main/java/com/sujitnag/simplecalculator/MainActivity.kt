package com.sujitnag.simplecalculator

import MyMath
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.text.method.ScrollingMovementMethod
import android.view.*
import android.widget.Button
import android.widget.ScrollView
import android.widget.Switch
import android.widget.Toast
import androidx.core.graphics.green
import androidx.preference.PreferenceManager
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
var MySrdPfile="MysharedPfile"
var soundflag:Boolean=false
var smartflag:Boolean=true
//data class Theams(val fColor:Color,val sColor:Color,val tColor:Color)
// tColorList= listOf<Theams>(Theams())

class MainActivity : AppCompatActivity() {
   lateinit var  textToSpeech:TextToSpeech
    val smartL= mutableListOf<Double>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       // setSupportActionBar(toolbar)
      //  toolbar.
        setSupportActionBar (toolbar)
       // toolbar.setTitle()
        textToSpeech=TextToSpeech(this,TextToSpeech.OnInitListener {
            if(it!=TextToSpeech.ERROR){
                textToSpeech.setLanguage(Locale.ENGLISH)
            }
        })
       val sharedprefarance=this.getSharedPreferences( MySrdPfile, MODE_PRIVATE)//PreferenceManager.getDefaultSharedPreferences(this)
       val str=sharedprefarance.getString("TheamColor","#FF5722ff")
       soundflag= sharedprefarance.getBoolean("sound",false)
       smartflag=sharedprefarance.getBoolean("smart",true)
        ////if(str!=null){
       CalTable.setBackgroundColor(Color.parseColor(str))
        val cc=Color.parseColor(str).green
        topLayer.setBackgroundColor(Color.parseColor(str)+2*cc)
        SmartOparetor.visibility=View.GONE


        onlyshow.movementMethod= ScrollingMovementMethod()
        DisplayT.movementMethod= ScrollingMovementMethod()

        /*val frgMr=supportFragmentManager.beginTransaction()
               frgMr.add(R.id.frgContener,SettingsFragment())
               frgMr.commit()*/
      }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
      //  return super.onCreateOptionsMenu(menu)
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menue, menu)
        return true
    }
    //override fun onOp
    override fun onOptionsItemSelected(item: MenuItem): Boolean =when(item.itemId){
        R.id.setting->{if(item.isChecked==true){
            val frgMr=supportFragmentManager
            val frt=frgMr.beginTransaction()
            val frg1=frgMr.findFragmentById(R.id.frgContener)
            if(frg1?.isRemoving()==false) {
                frt.remove(frg1)
                frt.commit()
            }
            Toast.makeText(this,"true",Toast.LENGTH_SHORT).show()

            item.setChecked(false)
        }else{
            val frgMr=supportFragmentManager.beginTransaction()
            frgMr.add(R.id.frgContener,SettingsFragment())
            frgMr.commit()

            Toast.makeText(this,"false",Toast.LENGTH_SHORT).show()
            item.setChecked(true)
        }
        true
        }
        R.id.smartCal->{

            if(item.isChecked==true){

               Toast.makeText(this,"true",Toast.LENGTH_SHORT).show()
                SmartOparetor.visibility=View.GONE
                NormalOparetor.visibility=View.VISIBLE
                button5.visibility=View.VISIBLE
                button20.visibility=View.VISIBLE
                DisplayT.text=""
                item.setChecked(false)
                }else{
                SmartOparetor.visibility=View.VISIBLE
                NormalOparetor.visibility=View.GONE
                button5.visibility=View.GONE
                button20.visibility=View.GONE

                Toast.makeText(this,"false",Toast.LENGTH_SHORT).show()
                DisplayT.text=""
                item.setChecked(true)
            }
            true
        }

else->{true}
   }
    fun Operators(view:View)
    {
       var b:Button=view as Button
DisplayT.append(" "+b.text.toString()+" ")
        if(soundflag){
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.LOLLIPOP ){
               textToSpeech.speak(b.text.toString(),TextToSpeech.QUEUE_FLUSH,null)
        }else{
         textToSpeech.speak(b.text.toString(),TextToSpeech.QUEUE_FLUSH,null,"operator")
        }}
    }

   fun Operatands(view:View)
    {
        var b:Button=view as Button
        DisplayT.append(b.text)
        if(soundflag){
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.LOLLIPOP ){
        textToSpeech.speak(b.text.toString(),TextToSpeech.QUEUE_FLUSH,null)
    }else{
            textToSpeech.speak(b.text.toString(),TextToSpeech.QUEUE_FLUSH,null,"operands") }

    }}

    fun calculate(view:View)
    {
        //var b:Button=view as Button
        //DisplayT.append(b.text)
        val mobj=MyMath(DisplayT.text.toString())
        onlyshow.append(DisplayT.text.toString())
        DisplayT.text=mobj.inFixCal()
        onlyshow.append("= "+DisplayT.text.toString()+"\n")
        if(soundflag){
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.LOLLIPOP ){
            textToSpeech.speak(DisplayT.text.toString(),TextToSpeech.QUEUE_FLUSH,null)
        }else{
            textToSpeech.speak(DisplayT.text.toString(),TextToSpeech.QUEUE_FLUSH,null,"operands")
        }}
        //mobj.inToPre()
     // val r=  mobj.calculate()
      //  DisplayT.setText(r.toString())

    }
    fun refresh(view:View){
        DisplayT.text=""
        if(soundflag){
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.LOLLIPOP ){
            textToSpeech.speak("Now Refresh",TextToSpeech.QUEUE_FLUSH,null)
        }else{
            textToSpeech.speak("Now Refresh",TextToSpeech.QUEUE_FLUSH,null,"operands")
        }}
    }
    fun back(view:View){
        DisplayT.also {
            if(soundflag){
            if(Build.VERSION.SDK_INT<Build.VERSION_CODES.LOLLIPOP ){
                textToSpeech.speak("Now delete"+it.text.takeLast(2),TextToSpeech.QUEUE_FLUSH,null)
            }else{
                textToSpeech.speak("Now delete"+it.text.takeLast(2),TextToSpeech.QUEUE_FLUSH,null,"operands")
            }
            it.text=it.text.subSequence(0, it.text.length-2)
        }}
    }

    fun nextB(view:View){
        var b:Button=view as Button
        DisplayT.append("->")
        if(soundflag){
            if(Build.VERSION.SDK_INT<Build.VERSION_CODES.LOLLIPOP ){
                textToSpeech.speak(b.text.toString(),TextToSpeech.QUEUE_FLUSH,null)
            }else{
                textToSpeech.speak(b.text.toString(),TextToSpeech.QUEUE_FLUSH,null,"operands") }

        }
    }

    fun smartCalculation(view:View){
       val smartL1=DisplayT.text.split("->")
        onlyshow.append(DisplayT.text.toString())
        var r:Double=0.0
        smartL1.forEach {
            smartL.add(it.toDouble())
        }
        when(view.id){
            R.id.addB->{
                r=   smartL.reduce { acc, d ->  acc+d}
                DisplayT.setText(r.toString())            }
            R.id.subB->{
                r=   smartL.reduce { acc, d ->  acc-d}
                DisplayT.setText(r.toString())
            }
            R.id.multB->{
                r=   smartL.reduce { acc, d ->  acc*d}
                DisplayT.setText(r.toString())
            }
            R.id.divB->{
                r=   smartL.reduce { acc, d ->  acc/d}
                DisplayT.setText(r.toString())
            }

        }
        onlyshow.append(" = "+r.toString()+"\n")
        smartL.clear()
    }
fun replay(view:View){
    if(soundflag){
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.LOLLIPOP ){
            textToSpeech.speak(DisplayT.text.toString(),TextToSpeech.QUEUE_FLUSH,null)
        }else{
            textToSpeech.speak(DisplayT.text.toString(),TextToSpeech.QUEUE_FLUSH,null,"operands") }

    }
}
}