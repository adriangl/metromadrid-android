package com.agl.metromadrid;

import java.io.Serializable;

public class Estacion implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4352549561262012644L;
	private String nombre;
	private String codigo;
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getNombre() {
		return nombre;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getCodigo() {
		return codigo;
	}
	
	public boolean equals(Estacion e){
		return this.nombre.equals(e.getNombre()) 
		&& this.codigo.equals(e.getCodigo());
	}
	
	public String toString(){
		return nombre;
	}
	
}
