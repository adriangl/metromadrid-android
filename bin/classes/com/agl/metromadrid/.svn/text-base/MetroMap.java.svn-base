package com.agl.metromadrid;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TabHost;

public class MetroMap extends TabActivity{
	
	Button schematic_button;
	Button cartographic_button;
	
	
	/** Called when the activity is first created. */
	
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.map_tab_main);

	    Resources res = getResources(); // Resource object to get Drawables
	    TabHost tabHost = getTabHost();  // The activity TabHost
	    TabHost.TabSpec spec;  // Resusable TabSpec for each tab
	    Intent intent;  // Reusable Intent for each tab

	    // Create an Intent to launch an Activity for the tab (to be reused)
	    intent = new Intent().setClass(this, MetroSchematicMap.class);

	    // Initialize a TabSpec for each tab and add it to the TabHost
	    spec = tabHost.newTabSpec("schema").setIndicator(getString(R.string.schematic_tab),
	                      res.getDrawable(R.drawable.ic_map_tab_schematic))
	                  .setContent(intent);
	    tabHost.addTab(spec);

	    // Do the same for the other tabs
	    intent = new Intent().setClass(this, MetroCartographicMap.class);
	    spec = tabHost.newTabSpec("carto").setIndicator(getString(R.string.carto_tab),
	                      res.getDrawable(R.drawable.ic_map_tab_carto))
	                  .setContent(intent);
	    tabHost.addTab(spec);

	    tabHost.setCurrentTab(0);
	}

	@Override
	public void onAttachedToWindow() {
		super.onAttachedToWindow();
		Window window = getWindow();
		window.setFormat(PixelFormat.RGBA_8888);
	}
}
