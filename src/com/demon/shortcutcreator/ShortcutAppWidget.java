package com.demon.shortcutcreator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.webkit.MimeTypeMap;
import android.widget.RemoteViews;
import android.widget.Toast;

public class ShortcutAppWidget extends AppWidgetProvider {

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int N = appWidgetIds.length;

        // Perform this loop procedure for each App Widget that belongs to this provider
        for (int i=0; i<N; i++) {
            int appWidgetId = appWidgetIds[i];

            // Create an Intent to launch ExampleActivity
           

           
            //String pref = "preference_file_key";
    		
           SharedPreferences sharedPref = context.getSharedPreferences(
        		   "demon_file_key", 0);
            String test = "";
            String file_path = sharedPref.getString("path", test);
            
            File file = new File(file_path);
    		
            Uri file_n = Uri.fromFile(file);
			
			
			String mime = getMimeType2(file_path);
			/*new AlertDialog.Builder(this)
			.setIcon(R.drawable.ic_launcher)
			.setTitle("[" + mime + "]")
			.setPositiveButton("OK", null).show();*/
			Intent fileIntent = new Intent(Intent.ACTION_VIEW);
			fileIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			fileIntent.setDataAndType(file_n, mime);
			
			/*int duration = Toast.LENGTH_SHORT;
			Toast toast = Toast.makeText(context, file_path, duration);
			toast.show();*/
            
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, fileIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    		
            
            // Get the layout for the App Widget and attach an on-click listener
            // to the button
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget);
            views.setOnClickPendingIntent(R.id.button_send, pendingIntent);

            // Tell the AppWidgetManager to perform an update on the current app widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }
    
    public static String getMimeType2(String path) {	 
		String extention = path.substring(path.lastIndexOf(".") ); 
		String mimeTypeMap = MimeTypeMap.getFileExtensionFromUrl(extention); 
		String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(mimeTypeMap); 
		return mimeType;
	}

}