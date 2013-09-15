package com.agl.metromadrid;

import java.util.ArrayList;

public class Ruta {
	
	private ArrayList<String> texto_origen = new ArrayList<String>();
	private ArrayList<String> duracion_origen = new ArrayList<String>();
	
	private ArrayList<String> texto_metro = new ArrayList<String>();
	private String duracion_metro = "";
	
	private ArrayList<String> texto_destino = new ArrayList<String>();
	private ArrayList<String> duracion_destino = new ArrayList<String>();
	
	private String duracionTotal;
	private int numeroEstaciones=0;
	
	public void anadirTextoOrigen (String texto){
		texto_origen.add(texto);
	}
	
	public void anadirTextoMetro (String texto){
		texto_metro.add(texto);
	}
	
	public void anadirTextoDestino (String texto){
		texto_destino.add(texto);
	}
	
	public void anadirDuracionOrigen (String texto){
		duracion_origen.add(texto);
	}
	
	public void anadirDuracionMetro (String texto){
		duracion_metro=texto;
	}
	
	public void anadirDuracionDestino (String texto){
		duracion_destino.add(texto);
	}
	
	public ArrayList<Entrada> getEntradasOrigen(){
		ArrayList<Entrada> l = new ArrayList<Entrada>();
		for (int indice =0; indice<texto_origen.size(); indice++) {
			Entrada e =  new Entrada();
			e.setTexto(texto_origen.get(indice));
			//e.setDuracion(duracion_origen.get(indice));
			l.add(e);
		}
		return l;
	}
	
	public ArrayList<Entrada> getEntradasMetro(){
		ArrayList<Entrada> l = new ArrayList<Entrada>();
		for (int indice =0; indice<texto_metro.size()-1; indice++) {
			Entrada e =  new Entrada();
			e.setTexto(texto_metro.get(indice));
			if ((indice==texto_metro.size()-2)){
				e.setDuracion(texto_metro.get(indice+1).substring(6));
			}
			else{
				e.setDuracion("");
			}
			l.add(e);
		}
		return l;
	}
	
	public ArrayList<Entrada> getEntradasDestino(){
		ArrayList<Entrada> l = new ArrayList<Entrada>();
		for (int indice =0; indice<texto_destino.size(); indice++) {
			Entrada e =  new Entrada();
			e.setTexto(texto_destino.get(indice));
			//e.setDuracion(duracion_destino.get(indice));
			l.add(e);
		}
		return l;
	}
	
	public int getNumEntradasOrigen(){
		return texto_origen.size();
	}
	
	public int getNumEntradasMetro(){
		return texto_metro.size();
	}
	
	public int getNumEntradasDestino(){
		return texto_destino.size();
	}

	public void anadirDuracionTotal(String substring) {
		duracionTotal = substring;		
	}

	public String getDuracionTotal() {
		return duracionTotal;
	}

	public void anadirNumeroEstaciones(int numeroEstaciones) {
		this.numeroEstaciones = numeroEstaciones;
	}

	public int getNumeroEstaciones() {
		return numeroEstaciones;
	}
}
