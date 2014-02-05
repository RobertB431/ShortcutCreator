package com.demon.shortcutcreator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.R.bool;
import android.R.string;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;

public class FileExActivity extends ListActivity {
	
	private List<String> file_list; 
	private List<String> file_path;
	private String root;
	private TextView myfile_pathF;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_file_ex);
		// Show the Up button in the action bar.
		setupActionBar();
		String state = Environment.getExternalStorageState();
		myfile_pathF = (TextView)findViewById(R.id.file_path);
		if(Environment.MEDIA_MOUNTED_READ_ONLY.equals(state) || Environment.MEDIA_MOUNTED.equals(state)) {
	        	root = Environment.getExternalStorageDirectory().getPath();
	        }
	    else {
	        	root = "/";
	        }
	        
	        getDir(root);
		
		
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds file_lists to the action bar if it is present.
		getMenuInflater().inflate(R.menu.file_ex, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void getDir(String dirfile_path)
    {
    	myfile_pathF.setText("Pick a file... Current Location: " + dirfile_path);
    	file_list = new ArrayList<String>();
    	file_path = new ArrayList<String>();
    	File f = new File(dirfile_path);
    	File[] files = f.listFiles();
    	
    	file_list.add("/");
		file_path.add("/");
    	
    	if(!dirfile_path.equals(root))
    	{
    		
    		file_list.add(root);
    		file_path.add(root);
    		file_list.add("../");
    		file_path.add(f.getParent());	
    	}
    	
    	for(int i = 0; i < files.length; i++)
    	{
    		File file = files[i];
    		
    		if(!file.isHidden() && file.canRead()){
    			file_path.add(file.getPath());
        		if(file.isDirectory()){
        			file_list.add(file.getName() + "/");
        		}else{
        			file_list.add(file.getName());
        		}
    		}	
    	}

    	ArrayAdapter<String> fileList =
    			new ArrayAdapter<String>(this, R.layout.row, file_list);
    	setListAdapter(fileList);	
    }
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		File file = new File(file_path.get(position));
		
		if (file.isDirectory())
		{
			if(file.canRead()){
				getDir(file_path.get(position));
			}else{
				new AlertDialog.Builder(this)
					.setIcon(R.drawable.ic_launcher)
					.setTitle("[" + file.getName() + "] folder can't be read!")
					.setPositiveButton("OK", null).show();	
			}	
		}else {
			/*new AlertDialog.Builder(this)
					.setIcon(R.drawable.ic_launcher)
					.setTitle("[" + file.getName() + "]")
					.setPositiveButton("OK", null).show();*/
			Intent data = new Intent();
			//static bool identifier = 1;
			//get data here
			String identifier = "file_path";
			//String identifier2 = "file_name";
			String value = file.getPath();//.getPath();
			//String name = file.getName();
			data.putExtra(identifier, value);
			//data.putExtra(identifier2, name);
			setResult(Activity.RESULT_OK, data);
			finish();
			//return(1);

		  }
	}

}
