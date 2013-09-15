package com.agl.metromadrid;

import java.util.Locale;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class MetroSchematicMap extends Activity{
	
	/** Called when the activity is first created. */
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.metro_map);
        
        WebView map_pic = (WebView)findViewById(R.id.map_pic);
        
        WebSettings settings = map_pic.getSettings();
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        
        Locale l = Locale.getDefault();
        if (l.toString().equals("es_ES")){
        	map_pic.loadUrl("file:///android_asset/plano_esp.gif");
        }
        else{
        	map_pic.loadUrl("file:///android_asset/plano_eng.gif");
        }
	}
	
	@Override
	public void onAttachedToWindow() {
		super.onAttachedToWindow();
		Window window = getWindow();
		window.setFormat(PixelFormat.RGBA_8888);
	}
}
