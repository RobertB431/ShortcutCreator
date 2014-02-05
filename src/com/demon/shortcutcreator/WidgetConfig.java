package com.demon.shortcutcreator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.Toast;

public class WidgetConfig extends Activity {
	
int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
static final int PICK_FILE = 1;
//String file_path2 = "";
Context context = this;
String id = null;
String file_path2 = "";
boolean choosen = false;

@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	
	Intent intent = getIntent();
	Bundle extras = intent.getExtras();
	if (extras != null) {
	    mAppWidgetId = extras.getInt(
	            AppWidgetManager.EXTRA_APPWIDGET_ID, 
	            AppWidgetManager.INVALID_APPWIDGET_ID);
		}
	setResult(RESULT_CANCELED);
	id = AppWidgetManager.EXTRA_APPWIDGET_ID;
	
	
	
	
	
	getFile();
	
	
	
	}


public void getFile() {
	Intent intent_file = new Intent(this, FileExActivity.class);
	//EditText editText = (EditText) findViewById(R.id.edit_message);
	//String message = editText.getText().toString();
	//intent.putExtra(EXTRA_MESSAGE, message);
	
	startActivityForResult(intent_file, PICK_FILE);
	
	//String fileName = "saveDataWidget";
    
	/*new AlertDialog.Builder(this)
	.setIcon(R.drawable.ic_launcher)
	.setTitle("[" + id + "]")
	.setPositiveButton("OK", null).show();*/
}

public void setPath(String temp)
{
	AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
	RemoteViews views = new RemoteViews(context.getPackageName(),
			R.layout.app_widget);
			appWidgetManager.updateAppWidget(mAppWidgetId, views);
			
	Intent resultValue = new Intent();
	resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
	
	file_path2 = temp;
	
	int duration = Toast.LENGTH_LONG;
	Toast toast = Toast.makeText(context, file_path2, duration);
	toast.show();
	
	if(file_path2.isEmpty()) {
		setResult(RESULT_CANCELED, resultValue);
		finish();
	}
	else
	{
		setResult(RESULT_OK, resultValue);
		SharedPreferences sharedPref = context.getSharedPreferences(
				getString(R.string.preference_file_key), 0);
		
		SharedPreferences.Editor editor = sharedPref.edit();
		
		//String test = getString(R.string.widget_file_test);
		
		editor.putString("path", file_path2);
		
		editor.commit(); 
	}
	
	//setResult(RESULT_OK, resultValue);
	

	finish();		
}


@Override
public void onActivityResult(int requestCode, int resultCode, Intent data) {
	if(resultCode == Activity.RESULT_OK){
		//String file_name1 = data.getStringExtra("file_name");
		String temp_path = data.getStringExtra("file_path");
		setPath(temp_path);
		/*new AlertDialog.Builder(this)
		.setIcon(R.drawable.ic_launcher)
		.setTitle("[" + file_name1 + "]")
		.setPositiveButton("OK", null).show();*/
		//setFav(file_path1);
		//item.add(file_name1); 
	}
	else {
		setPath("");
		/*new AlertDialog.Builder(this)
		.setIcon(R.drawable.ic_launcher)
		.setTitle("[" + "file not found" + "]")
		.setPositiveButton("OK", null).show();*/
		//setResult(RESULT_CANCELED);
		//finish();
	}
	//choosen = true;
}

}

