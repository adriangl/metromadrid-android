package com.agl.metromadrid;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class SearchDialogActivity extends Activity implements TextWatcher, OnItemClickListener{
	
	private ArrayList<Estacion> list;
	private EditText busqueda;
	private ListView lista_resultados;	
	
	private ArrayAdapter<Estacion> adapter;

	private final static int  SUCCESS_RETURN_CODE = 1;
	
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.search_layout);
		
		busqueda = (EditText) findViewById(R.id.search_box);
		lista_resultados = (ListView) findViewById(R.id.search_results);
		
		list = (ArrayList<Estacion>) this.getIntent().getExtras().get("ESTACIONES");
		
		int position = this.getIntent().getExtras().getInt("POSICION");
		
		adapter = new ArrayAdapter<Estacion>(
				this,android.R.layout.simple_spinner_dropdown_item,list);
		lista_resultados.setAdapter(adapter);
		
		busqueda.addTextChangedListener(this);
		lista_resultados.setOnItemClickListener(this);
		
		lista_resultados.setSelection(position);
		
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// We can filter the list according to the string sequence, s
		adapter.getFilter().filter(s);
	}

	@Override
	public void afterTextChanged(Editable s) {
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		Intent i = new Intent();
		// We have to extract the position on the original stations list; we 
		// already know the value on the search list.
		Estacion e = (Estacion) arg0.getItemAtPosition(position);
		for (int index = 0; index<list.size();index++){
			if (list.get(index).equals(e)){
				i.putExtra("ESTACION",index);
				break;
			}
		}
		SearchDialogActivity.this.setResult(SUCCESS_RETURN_CODE,i);
		this.finish();		
	}
	
	protected void onDestroy() {
	    super.onDestroy();
	    busqueda.removeTextChangedListener(this);
	}

}
