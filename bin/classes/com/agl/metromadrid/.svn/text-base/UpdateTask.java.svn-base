package com.agl.metromadrid;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.provider.Settings;

public class UpdateTask extends AsyncTask<Void,Void,Integer>{
	
	ProgressDialog dialog;
	Context context;
	
	public UpdateTask (Context c){
		this.context = c;
	}
	
	protected void onPreExecute(){
		dialog = ProgressDialog.show(context,"Espere...", 
                "Actualizando los datos de estaciones...", true);
		dialog.show();
    }
	
	@Override
	protected Integer doInBackground(Void... params){
		FileOutputStream a;
		try {			
			if (ConnectionManager.hayConexion(context)){
				a = context.openFileOutput("datos.dat",Context.MODE_PRIVATE);
				ParserHTMLEstacion.parse(a);
			}
			else {
				return 0;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return 1;
	}
	
	protected void onPostExecute(Integer n){
		dialog.dismiss();
		if (n==0){
			AlertDialog.Builder builder = new AlertDialog.Builder(context);		
			builder.setMessage(context.getString(R.string.no_internet_db_message))
					.setTitle(context.getString(R.string.no_internet_title))
					.setCancelable(false)
					.setNegativeButton("Cerrar", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				        	   dialog.dismiss();
				           }
				       });
			AlertDialog d = builder.create();
			d.show();
		}
	}
}
