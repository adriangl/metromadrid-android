package com.agl.metromadrid;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ParserEstacionMetro {
	
	private ArrayList<Estacion> listaEstaciones;
	
	public ParserEstacionMetro(){
		setListaEstaciones(new ArrayList<Estacion>());
	}

	public void setListaEstaciones(ArrayList<Estacion> listaEstaciones) {
		this.listaEstaciones = listaEstaciones;
	}

	public ArrayList<Estacion> getListaEstaciones() {
		return listaEstaciones;
	}
	
	public void parseEstaciones(FileInputStream fichero){
		// Parseamos un archivo fijo de texto con las entradas correspondientes
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(fichero,"UTF-8"));
			String linea="";
			while ((linea = br.readLine()) != null){
				String[] datosEstacion = linea.split("%");
				Estacion estacion = new Estacion();
				estacion.setNombre(datosEstacion[0]);
				estacion.setCodigo(datosEstacion[1]);
				listaEstaciones.add(estacion);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
