package com.agl.metromadrid;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParserHTMLEstacion {
	
	public static void parse(FileOutputStream file){
		try {
			PrintStream pw = new PrintStream(file,true,"UTF-8");
			String code = "";
			String name = "";
			URL parsedURL = new URL("http://www.metromadrid.es/es/index.html");
			HttpURLConnection urlConn = (HttpURLConnection) parsedURL.openConnection();
			BufferedReader reader = new BufferedReader(new InputStreamReader(urlConn.getInputStream(),"UTF-8"));
			String linea = "";
			while ((linea = reader.readLine()) != null){
				if (linea.contains("<option value=")){
					// Extraemos codigo y nombre de opción
					Pattern p = Pattern.compile("\\d+");
					Pattern pat = Pattern.compile("<option .+>(.+)</option>");
					
					Matcher m = p.matcher(linea);
					Matcher mat = pat.matcher(linea);
					
					if (m.find()){
						code = m.group();
					}
					
					if (mat.find()){
						name = mat.group(1);
						name = name.substring(1,name.length()-1);		
					}
					// Escribimos en fichero de salida
					pw.println(name+"%"+code);
					pw.flush();
				}
				else if (linea.contains("</select>")){
					// Terminamos la lectura, ganando tiempo
					break;
				}
			}
			
			reader.close();
			urlConn.disconnect();
			pw.close();
			file.close();
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
