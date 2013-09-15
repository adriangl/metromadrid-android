package com.agl.metromadrid;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

public class MetroForm extends Activity implements OnClickListener{
    
	private Spinner origen;
	private Spinner destino;
	private Button submit_button;
    
	private Spinner tipo_dia;
	private TextView tipo_hora;
	private Spinner tipo_viaje;	
	
	private ImageButton search_origin;
	private ImageButton search_dest;
	
	private int mHour;
	private int mMinute;
    
	private ArrayList<Estacion> estaciones = null;
    
	private NumberFormat f = new DecimalFormat("00");
        
    private final static int TIME_DIALOG_ID = 0;
    private final static int NO_CONNECTION_ID = 1;
    private final static int SAME_STATIONS_ID = 2;
    private final static int NO_DATA_ID = 3;
    
    private final static int SEARCH_ORIGIN_REQUEST_CODE = 1;
    private final static int SEARCH_DESTINATION_REQUEST_CODE = 2;
	private final static int SUCCESS_RETURN_CODE = 1;
    	
	/** Called when the activity is first created. */
	@Override
    public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
        setContentView(R.layout.metro_form);
        
        origen = (Spinner) findViewById(R.id.origin_spinner);
    	destino = (Spinner) findViewById(R.id.destination_spinner);
        submit_button = (Button) findViewById(R.id.submit_button);
        
        tipo_dia = (Spinner) findViewById(R.id.day_spinner);
        tipo_hora = (TextView) findViewById(R.id.hour_view);
    	tipo_viaje = (Spinner) findViewById(R.id.route_spinner);
    	
    	search_origin = (ImageButton) findViewById(R.id.search_origin);
    	search_dest = (ImageButton) findViewById(R.id.search_destination);
    	
    	ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(
                this, R.array.day_array, android.R.layout.simple_spinner_item);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tipo_dia.setAdapter(adapter4);   
        
        
        ArrayAdapter<CharSequence> adapter5 = ArrayAdapter.createFromResource(
                this, R.array.route_array, android.R.layout.simple_spinner_item);
        adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tipo_viaje.setAdapter(adapter5);
        
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        actualizarHora();
        
        switch (c.get(Calendar.DAY_OF_WEEK)){
        	case Calendar.SUNDAY: tipo_dia.setSelection(2); break;
        	case Calendar.SATURDAY: tipo_dia.setSelection(1); break;
        	default: tipo_dia.setSelection(0); break;
        }        
        
        submit_button.setOnClickListener(this);
        tipo_hora.setOnClickListener(this);
        search_dest.setOnClickListener(this);
        search_origin.setOnClickListener(this);
	
	
        new ParseTask().execute();
		
    }
	
	@Override
	public void onAttachedToWindow() {
		super.onAttachedToWindow();
		Window window = getWindow();
		window.setFormat(PixelFormat.RGBA_8888);
	}
	
	
	public void onRestart(){
    	// tras volver de otra actividad, refrescamos contenido
    	super.onRestart();
    	if (!ConnectionManager.hayConexion(this)){
        	showDialog(NO_CONNECTION_ID);
        }
        else {
        	if (estaciones==null){
        		new ParseTask().execute();
        	}
        }
    }
	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
			case R.id.submit_button:
				Estacion est_origen = (Estacion) origen.getSelectedItem();
				Estacion est_destino = (Estacion) destino.getSelectedItem();
				
				if (est_origen.getNombre().equals(est_destino.getNombre())){
					showDialog(SAME_STATIONS_ID);
				}
				else{
					Intent i = new Intent(this, Resultados.class);
					
					// Colocamos extras para su procesado
					i.putExtra("ORIGEN", est_origen);
					i.putExtra("DESTINO", est_destino);
					i.putExtra("TIPO_ORIGEN", "ESTACION");
					i.putExtra("TIPO_DESTINO", "ESTACION");
					i.putExtra("TIPO_DIA", tipo_dia.getSelectedItemPosition()+1);
					String tipo="";
					switch (tipo_viaje.getSelectedItemPosition()){
						case 0: tipo = "rapida"; break;
						case 1: tipo = "transbordos"; break;
						case 2: tipo = "corta"; break;
					}
					i.putExtra("TIPO_VIAJE", tipo);
					i.putExtra("HORA", f.format(mHour));
					i.putExtra("MINUTOS", f.format(mMinute));
					
					// Lanzamos actividad de resultados
					startActivity(i);
				}
				break;
			case R.id.hour_view:				
				showDialog(TIME_DIALOG_ID);
				break;
			case R.id.search_origin:
				Intent i = new Intent(this,SearchDialogActivity.class);
				i.putExtra("ESTACIONES", estaciones);
				i.putExtra("POSICION", origen.getSelectedItemPosition());
				startActivityForResult(i, SEARCH_ORIGIN_REQUEST_CODE);
				break;
			case R.id.search_destination:
				Intent in = new Intent(this,SearchDialogActivity.class);
				in.putExtra("ESTACIONES", estaciones);
				in.putExtra("POSICION", destino.getSelectedItemPosition());
				startActivityForResult(in, SEARCH_DESTINATION_REQUEST_CODE);
				break;
		}		
	}
	
	@Override 
	protected void onActivityResult(int requestCode, int resultCode, Intent extras) {
		super.onActivityResult(requestCode, resultCode, extras);
		// Here We identify the subActivity we started
		if (extras !=null){
			Bundle datos = extras.getExtras();
			int position = datos.getInt("ESTACION");
			if(requestCode == SEARCH_ORIGIN_REQUEST_CODE){
				origen.setSelection(position);				
			}
			else if(requestCode == SEARCH_DESTINATION_REQUEST_CODE){
				destino.setSelection(position);
			}
		}
		
	} 
	
	@Override
	protected Dialog onCreateDialog(int id) {
	    switch (id) {
	    case TIME_DIALOG_ID:
	        return new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
	            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
	                mHour = hourOfDay;
	                mMinute = minute;
					actualizarHora();
	            }}, 
	            mHour, mMinute, false);
	    case NO_CONNECTION_ID:
	    	AlertDialog.Builder builder = new AlertDialog.Builder(this);		
			builder.setMessage(getString(R.string.no_internet_message))
					.setTitle(getString(R.string.no_internet_title))
					.setCancelable(false)
					.setNegativeButton(getString(R.string.close_app), new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				        	  MetroForm.this.finish();
				           }
				       })			
					.setPositiveButton(getString(R.string.enable_network), new DialogInterface.OnClickListener() {				
						public void onClick(DialogInterface dialog, int id) {
							dialog.dismiss();
							startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
						}
					});			
			return builder.create();
			
	    case SAME_STATIONS_ID:
	    	AlertDialog.Builder builder2 = new AlertDialog.Builder(this);		
			builder2.setMessage(getString(R.string.same_stations_message))
					.setTitle(getString(R.string.same_stations_title))
					.setCancelable(true)
					.setPositiveButton(getString(R.string.accept_button), new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				        	  dialog.dismiss();
				           }
				       });
			return builder2.create();
			
	    case NO_DATA_ID:
	    	AlertDialog.Builder builder3 = new AlertDialog.Builder(this);		
			builder3.setMessage(getString(R.string.no_data_message))
					.setTitle(getString(R.string.no_data_title))
					.setCancelable(false)
					.setNegativeButton(getString(R.string.close_app), new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				        	  MetroForm.this.finish();
				           }
				       })			
					.setPositiveButton(getString(R.string.update_db), new DialogInterface.OnClickListener() {				
						public void onClick(DialogInterface dialog, int id) {
							dialog.dismiss();
							MetroForm.this.finish();
							new UpdateTask(MetroForm.this).execute();
						}
					});			
			return builder3.create();
	    }
	    return null;
	}
	
	private void actualizarHora (){
		tipo_hora.setText(f.format(mHour)+":"+f.format(mMinute));
	}
	
	
	private class ParseTask extends AsyncTask<Void,Void,Integer>{
		
		ProgressDialog dialog;
		
		protected void onPreExecute(){
			dialog = ProgressDialog.show(MetroForm.this,getString(R.string.parse_title), 
					getString(R.string.parse_message), true);
			dialog.show();
	    }
		
		@Override
		protected Integer doInBackground(Void... params){
			try {
				if (!existe("datos.dat")){
					return 0;
				}
				else{
					FileInputStream b = MetroForm.this.openFileInput("datos.dat");
					ParserEstacionMetro pem = new ParserEstacionMetro();
					pem.parseEstaciones(b);
					estaciones = pem.getListaEstaciones();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			return 1;
		}
		
		protected void onPostExecute(Integer n){
			dialog.dismiss();
			if (n==0){
				showDialog(NO_DATA_ID);
			}
			else{
				ArrayAdapter<Estacion> adapter = new ArrayAdapter<Estacion>(
						MetroForm.this, android.R.layout.simple_spinner_item,estaciones);
				adapter.setDropDownViewResource(
						android.R.layout.simple_spinner_dropdown_item);
				origen.setAdapter(adapter);
				destino.setAdapter(adapter);
				destino.setSelection(1);
			}
		}

		private boolean existe(String string) {
			boolean bool=false;
			for(String i : MetroForm.this.fileList()){
				if (i.equals(string)){
					bool = true;
					break;
				}
			}			
			return bool;
		}	
		
	}
}