package com.sujitnag.simplecalculator

import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.graphics.green
import androidx.preference.EditTextPreference
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import kotlinx.android.synthetic.main.activity_main.*


class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        val sharedprefarance= activity?.getSharedPreferences( MySrdPfile, AppCompatActivity.MODE_PRIVATE)

        val theamSetting:ListPreference?=findPreference("TheamColor")
      //  theamSetting?.summaryProvider=
        val smartOp=activity?.findViewById(R.id.SmartOparetor) as TableRow?
        val nornalOp=activity?.findViewById(R.id.NormalOparetor) as TableRow?
        val sound:SwitchPreference?=findPreference("sound")
        val smart:SwitchPreference?=findPreference("smart")


        sound?.setOnPreferenceChangeListener { preference, newValue ->
            sharedprefarance?.edit {
                if(preference is SwitchPreference){
                    soundflag=newValue.toString().toBoolean()
                this.putBoolean("sound",soundflag)

                this.commit()}

            }


            true
        }

        theamSetting?.setOnPreferenceChangeListener { preference, newValue ->
         if(preference is ListPreference){
             val colorInd=preference.findIndexOfValue(newValue.toString())
          //  preferenceManager.sharedPreferences.co
            val tv=activity?.findViewById(R.id.CalTable) as TableLayout?
             val topl=activity?.findViewById(R.id.topLayer) as LinearLayout?
            val cvalu=preference.entryValues.get(colorInd)     //resources.getColor(R.array.colorsN,)
            var c=Color.parseColor(cvalu.toString())

             // val tv=activity?.findViewById(R.id.DisplayT) as TextView?
            tv?.setBackgroundColor(c)
             topl?.setBackgroundColor(c+2*c.green)

             sharedprefarance?.edit {
                 this.putString("TheamColor",cvalu.toString())
                 this.commit()
             }
         }
         true}


    }
}