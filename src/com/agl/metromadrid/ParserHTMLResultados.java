package com.agl.metromadrid;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParserHTMLResultados {
	
	private Ruta listaRuta;
	
	public ParserHTMLResultados(String request){
		listaRuta = new Ruta();
		try {
			URL request_url = new URL(request);
			HttpURLConnection request_conn = (HttpURLConnection) request_url.openConnection();
			// Tomamos InputStream para la lectura del HTML recibido
			BufferedReader br = new BufferedReader(new InputStreamReader
					(request_conn.getInputStream(),"UTF-8"));
			
			String linea = "";
			boolean boolOrigen = false;
			boolean boolMetro = false;
			boolean boolDestino = false;
			
			
			
			Pattern texto_od_regex = Pattern.compile(
					"<li><strong>(.+?)</strong></li>");
			Pattern duracion_od_regex = Pattern.compile(
					"<td class='duracion'>(.+?)</td>");
			Pattern texto_metro_regex = Pattern.compile(
					"<li><strong>(.+?)</strong></li>");
			Pattern duracion_metro_regex = Pattern.compile(
					"<br /><br /></td><td class=\"long\">&nbsp;(.+?)</td><td class=\"long\"><br /><br /></td></tr>");
			
			Pattern tiempo_estimado_regex;
			Pattern numero_estaciones_regex;
			
			if (Locale.getDefault().toString().contains("es")){			
				tiempo_estimado_regex = Pattern.compile(
						"<dt><strong><b>Tiempo Estimado:</b> (.+?) </strong></dt>");
				numero_estaciones_regex = Pattern.compile(
						"<dt><strong>Trayecto</strong></dt><dd>(.+?)</dd>");
			}
			else{
				tiempo_estimado_regex = Pattern.compile(
				"<dt><strong><b>Estimated Time:</b>  (.+?) </strong></dt>");
				numero_estaciones_regex = Pattern.compile(
				"<dt><strong>Route</strong></dt><dd>(.+?)</dd>");
			}
			int contador = 0;
			
			while ((linea = br.readLine())!= null){
				if (linea.contains("No se encontraron rutas")){
					break;
				}
				else if (linea.contains("Tiempo Estimado")||linea.contains("Estimated Time")){
					Matcher m = tiempo_estimado_regex.matcher(linea);
					if (m.find()){
						String s = m.group(1);
						listaRuta.anadirDuracionTotal(s);
					}
				}
				else if (linea.contains("Trayecto")||linea.contains("Route")){
					Matcher m = numero_estaciones_regex.matcher(linea);
					if (m.find()){
						String s = m.group(1);
						int i = s.lastIndexOf(" ");
						listaRuta.anadirNumeroEstaciones(Integer.parseInt(s.substring(0,i)));
					}
				}
				else if (linea.contains("TRAMO EN ORIGEN")||linea.contains("Section at origin")){
					boolOrigen = true;
				}
				else if (linea.contains("TRAMO EN METRO")||linea.contains("Section by Underground")){
					contador++;
					if (contador == 2){
						boolMetro = true;
					}					
				}
				else if (linea.contains("TRAMO EN DESTINO")||linea.contains("Section at destination")){
					boolDestino = true;
				}
				// En este if est� el contenido de cada apartado
				else if (linea.contains("<li><strong>")){
					if (boolOrigen){
						// En el caso de estar en la secci�n de origen del texto...
						// Buscamos Strings con texto descriptivo
						Matcher m = texto_od_regex.matcher(linea);
						// Buscamos todas las coincidencias posibles, y guardamos
						while (m.find()){
							listaRuta.anadirTextoOrigen(m.group(1));
						}
						// Ahora, buscamos con las duraciones
						m = duracion_od_regex.matcher(linea);
						while (m.find()){
							listaRuta.anadirDuracionOrigen(m.group(1));
						}
						boolOrigen = false;
					}
					else if (boolMetro){
						// En el caso de estar en la secci�n de origen del texto...
						// Buscamos Strings con texto descriptivo
						Matcher m = texto_metro_regex.matcher(linea);
						// Buscamos todas las coincidencias posibles, y guardamos
						while (m.find()){
							String text = m.group(1);
							if (text.startsWith(" ")){
								text = text.substring(1);
							}
							if (text.endsWith("<br /><br />")){
								text = text.substring(0,text.indexOf("<br /><br />"));
							}
							listaRuta.anadirTextoMetro(text);
						}
						// Ahora, buscamos con las duraciones
						m = duracion_metro_regex.matcher(linea);
						if (m.find()){
							listaRuta.anadirDuracionMetro(m.group(1));
						}
						boolMetro = false;
					}
					else if (boolDestino){
						// En el caso de estar en la secci�n de origen del texto...
						// Buscamos Strings con texto descriptivo
						Matcher m = texto_od_regex.matcher(linea);
						// Buscamos todas las coincidencias posibles, y guardamos
						while (m.find()){
							listaRuta.anadirTextoDestino(m.group(1));
						}
						// Ahora, buscamos con las duraciones
						m = duracion_od_regex.matcher(linea);
						while (m.find()){
							listaRuta.anadirDuracionDestino(m.group(1));
						}
						boolDestino = false;
						break;
					}
				}
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Ruta getRuta() {
		return listaRuta;
	}

}
