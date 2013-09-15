package com.agl.metromadrid;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class EntradaAdapter extends BaseAdapter{
	
	private ArrayList<Entrada> items;
	private Context c;
	
	public EntradaAdapter(Context context, int textViewResourceId, ArrayList<Entrada> items) {
        this.c = context;
        this.items = items;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            v = LayoutInflater.from(c).inflate(R.layout.row, parent, false);
        }
        Entrada e = items.get(position);
        if (e != null) {
                TextView texto = (TextView) v.findViewById(R.id.texto_fila);
                TextView duracion = (TextView) v.findViewById(R.id.duracion_fila);
                if (texto != null) {
                      texto.setText(e.getTexto());                            
                }
                if(duracion != null){
                      duracion.setText(e.getDuracion());
                }
        }
        return v;
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
}
