package com.agl.metromadrid;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Main extends Activity implements OnClickListener{
	
	Button form_button;
	Button map_button;
	
	private static final int ABOUT_APP = 1;
	
	/** Called when the activity is first created. */
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        form_button = (Button) findViewById(R.id.form_button);
        map_button = (Button) findViewById(R.id.map_button);
        
        form_button.setOnClickListener(this);
        map_button.setOnClickListener(this);   
            
        if (isFirstRun()){
			new UpdateTask(this).execute();
			Calendar c = Calendar.getInstance();
			SharedPreferences.Editor editor = this.getApplicationContext().
			getSharedPreferences("metroPrefs",0).edit();
			editor.putInt("update_month", c.get(Calendar.MONTH));
			editor.putInt("update_day", c.get(Calendar.DAY_OF_MONTH));
			editor.commit();
			setFirstRun();
		}
        else if (checkForUpdates()){
        	new UpdateTask(this).execute();
        	updateTime();
        }
	}
	
	private boolean checkForUpdates() {
		SharedPreferences defaultPrefs = PreferenceManager.
		getDefaultSharedPreferences(getBaseContext());
		
		SharedPreferences externalPrefs = this.getApplicationContext().
			getSharedPreferences("metroPrefs",0);
		
		if (defaultPrefs.getBoolean("startupUpdate",false)){
		
			Calendar date = Calendar.getInstance();
			
			int stored_month = externalPrefs.getInt("update_month", 0);
			int stored_day = externalPrefs.getInt("update_day", 0);
			
			int difference = Integer.parseInt(defaultPrefs.getString("updateTime", "0"));			
			
			Calendar storedDate = Calendar.getInstance();
			storedDate.set(Calendar.MONTH,stored_month);
			storedDate.set(Calendar.DAY_OF_MONTH,stored_day);
			
			storedDate.roll(Calendar.MONTH, difference);
			
			int update_months = storedDate.get(Calendar.MONTH)+storedDate.get(Calendar.YEAR)*12;
			int actual_months = date.get(Calendar.MONTH)+date.get(Calendar.YEAR)*12;
			
			int dif = actual_months - update_months;
			
			if ((dif > 0)){
				return true;
			}
			else if ((dif==0) && (date.get(Calendar.DAY_OF_MONTH)>=stored_day)){
				return true;
			}
	
			return false;
		}
		return false;
	}

	@Override
	public void onAttachedToWindow() {
		super.onAttachedToWindow();
		Window window = getWindow();
		window.setFormat(PixelFormat.RGBA_8888);
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
        case R.id.about:
            showDialog(ABOUT_APP);
            return true;
        case R.id.quit:
            this.finish();
            return true;
        case R.id.preferences:
        	Intent i = new Intent(getBaseContext(),Preferencias.class);
        	startActivity(i);
        default:
            return super.onOptionsItemSelected(item);
        }
    }

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case(R.id.form_button):
				startActivity(new Intent(this,MetroForm.class));
				break;
			case(R.id.map_button):
				startActivity(new Intent(this,MetroMap.class));
				break;
		}
		
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
	    switch (id) {
	    case ABOUT_APP:
	    	AlertDialog.Builder builder2 = new AlertDialog.Builder(this);		
			builder2.setMessage(getString(R.string.app_name)+" "+getString(R.string.app_version)+"\n"+getString(R.string.about_app_message))
					.setTitle(getString(R.string.about_app_title))
					.setCancelable(true)
					.setPositiveButton(getString(R.string.accept_button), new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				        	  dialog.dismiss();
				           }
				       });
			return builder2.create();
	    }
	    return null;
	}
	
	private boolean isFirstRun(){
		Context c = this.getApplicationContext();	
		SharedPreferences preferences =  c.getSharedPreferences("metroPrefs",0);
		boolean b = preferences.getBoolean("firstRun", true);
		return b;
	}
	
	private void setFirstRun(){
		Context c = this.getApplicationContext();	
		SharedPreferences preferences =  c.getSharedPreferences("metroPrefs",0);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putBoolean("firstRun", false);
		editor.commit();
	}
	
	private void updateTime(){
		Calendar c = Calendar.getInstance();
		SharedPreferences.Editor editor = this.getApplicationContext()
			.getSharedPreferences("metroPrefs", 0).edit();
		editor.putInt("update_month", c.get(Calendar.MONTH));
		editor.putInt("update_day", c.get(Calendar.DAY_OF_MONTH));
		editor.commit();
	}
}
