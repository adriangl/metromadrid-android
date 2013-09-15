package com.agl.metromadrid;

import java.text.DateFormat;
import java.util.Calendar;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.Preference.OnPreferenceClickListener;

public class Preferencias extends PreferenceActivity{
	
	private SharedPreferences list;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
		
		Preference update = (Preference) findPreference("updateNow");
		
		Preference date = (Preference) findPreference("updateTime");
		
		Preference bool = (Preference) findPreference("startupUpdate");
		
		final Preference lastUpdate = (Preference) findPreference("lastUpdate");
		
		// configuramos la entrada lastUpdate para que muestre la fecha
		// de la última actualización...
		
		final SharedPreferences prefs = this.getApplicationContext().getSharedPreferences("metroPrefs", 0);
		
		updateDate(prefs,lastUpdate);
		
		
		list = this.getApplicationContext().getSharedPreferences("metroPrefs",0);
		
		date.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			public boolean onPreferenceClick(Preference p) {
				return false;
			}
			
		});
		
		bool.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			public boolean onPreferenceClick(Preference p) {
				updateTime();
				return false;
			}
		});
		
		update.setOnPreferenceClickListener(new OnPreferenceClickListener() {

			@Override
			public boolean onPreferenceClick(Preference p) {
				new UpdateTask(Preferencias.this).execute();
				updateTime();
				updateDate(prefs,lastUpdate);
				return false;
			}
			
		});
		
	}
	
	private void updateDate(SharedPreferences prefs, Preference lastUpdate) {
		int day = prefs.getInt("update_day", 0);
		int month = prefs.getInt("update_month",0);
		
		Calendar c = Calendar.getInstance();
		
		c.set(Calendar.DAY_OF_MONTH,day);
		c.set(Calendar.MONTH,month);
		
		DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
		
		lastUpdate.setSummary(df.format(c.getTime()));		
	}

	private void updateTime(){
		Calendar c = Calendar.getInstance();
		SharedPreferences.Editor editor = list.edit();
		editor.putInt("update_month", c.get(Calendar.MONTH));
		editor.putInt("update_day", c.get(Calendar.DAY_OF_MONTH));
		editor.commit();
	}
	
}
