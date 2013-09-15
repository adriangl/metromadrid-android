package com.agl.metromadrid;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectionManager {
	
	public static boolean hayConexion(Context c) {
    	ConnectivityManager manager = 
    		(ConnectivityManager)c.getSystemService(Context.CONNECTIVITY_SERVICE);
    	NetworkInfo[] network_list = manager.getAllNetworkInfo();
    	
    	boolean conn=false;
    	
    	for (int i=0; i<network_list.length; i++){
    		if (network_list[i].isConnected()){
    			conn=true;
    			break;
    		}
    	}
		return conn;
	}

}
