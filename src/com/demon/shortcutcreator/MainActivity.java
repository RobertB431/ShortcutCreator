package com.demon.shortcutcreator;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.view.Menu;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends ListActivity {
	
	private List<String> item = null; 
	private List<String> fav = null;
	private String favorites;
	private TextView myPath;
	
	static final int PICK_FILE = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		myPath = (TextView) findViewById(R.id.path);
		//listView = (ListView) findViewById(R.id.list);
	   // registerForContextMenu(listView);
		
		//item = fav = null;
		favorites = "something";
		
		if(Environment.getExternalStorageState() != null) {
			//root = Environment.getExternalStorageDirectory().getPath();
		}
		else if(Environment.getRootDirectory() != null) {
			//root = "/";
		}
		else {
			noStorage();
		}
		
		
		
		File file = new File(this.getFilesDir(), "saveData.txt");
		
		try {
			file.createNewFile();
			//file.setReadable(true);
			//file.setWritable(true);
			
			/*BufferedWriter buf = new BufferedWriter(new FileWriter(file)); 
			buf.write("+ Create new shortcut");
			buf.newLine();
			buf.write("nothing to see here");
			buf.newLine();
			buf.close();*/
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			new AlertDialog.Builder(this)
			.setIcon(R.drawable.ic_launcher)
			.setTitle("[" + "saveDataCreated" + "]")
			.setPositiveButton("OK", null).show();
			e.printStackTrace();
		}
		
		
		
			getFavs();
		
		
			this.getListView().setLongClickable(true);
			   this.getListView().setOnItemLongClickListener(new OnItemLongClickListener() {
			        public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {
			        //Do some
			        	if(position != 0) {
			        	item.remove(position);
			        	fav.remove(position);
			        	onContentChanged();
			        	}
			            return true;
			        }
			    });
			
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void getFavs() {
		myPath.setText("Shortcuts List 1" );
		item = new ArrayList<String>();
		fav = new ArrayList<String>();
		
		//File[] files = f.listFiles();
		
		
		//item.add("+ add new shortcut");
		//fav.add("nothing to see here");
		//item.add("number 2");
		
		//String filename = "saveData";
		   
	    //FileOutputStream outputStream;

	   // File fileIn = File("saveData");
	    //InputStream in = null;
		String line = null;
		//int count = 1;
		
	   
		//File file = new File("saveData.txt");
		
		//if(file.exists() && file.canRead()) {
			/*new AlertDialog.Builder(this)
			.setIcon(R.drawable.ic_launcher)
			.setTitle("[" + "begin" + "]")
			.setPositiveButton("OK", null).show();*/
					  // InputStream in = null;
			
					   try {
						  //InputStream is;
						  //is = this.getAssets().open("saveData");
						  
						  //Reader reader = new InputStreamReader(is);
						  File file = new File(this.getFilesDir(), "saveData.txt");
						   BufferedReader buf = new BufferedReader(new FileReader(file));
						   /*new AlertDialog.Builder(this)
							.setIcon(R.drawable.ic_launcher)
							.setTitle("[" + "opened" + "]")
							.setPositiveButton("OK", null).show();*/
					    line = buf.readLine();
					   if(line.equals("+ Create new shortcut")) {
					    	 item.add("+ Create new shortcut");
					    	 fav.add("nothing to see here");
								
								
					    }
					   else {
						   new AlertDialog.Builder(this)
							.setIcon(R.drawable.ic_launcher)
							.setTitle("[" + "opened for the first time" + "]")
							.setPositiveButton("OK", null).show();
					   }
					   
					     
					    	line = buf.readLine();
					    	line = buf.readLine();
					    	 
					    	while(line != null) {
					    		/* new AlertDialog.Builder(this)
									.setIcon(R.drawable.ic_launcher)
									.setTitle("[" + line + "]")
									.setPositiveButton("OK", null).show();*/
					    		item.add(line);
					    		line = buf.readLine();
					    		 
					    		 fav.add(line);
					    		line = buf.readLine();
					    	}
					    // }
					   
					   buf.close();
					   }
					   catch (Exception e) {
						   new AlertDialog.Builder(this)
							.setIcon(R.drawable.ic_launcher)
							.setTitle("[" + "setup failed" + "]")
							.setPositiveButton("OK", null).show();
	    				 e.printStackTrace();
	    				
					   }
					
		/*else {
			//item.add("+ add new shortcut");
			//fav.add("nothing to see here");
			new AlertDialog.Builder(this)
			.setIcon(R.drawable.ic_launcher)
			.setTitle("[" + "couldnt open" + "]")
			.setPositiveButton("OK", null).show();
		}*/
		
		

		
		
		ArrayAdapter<String> favList = new ArrayAdapter<String>(this, R.layout.row,item);
		setListAdapter(favList);
		
	}
	
	@Override
	protected void onStop() {
	    super.onStop();  // Always call the superclass method first
	    
	    File file = new File(this.getFilesDir(), "saveData.txt");
	    
	   
		
		
	   
	    //FileOutputStream outputStream;

	    try {
	      //outputStream = openFileOutput(file, Context.MODE_PRIVATE);
	    	 BufferedWriter buf = new BufferedWriter(new FileWriter(file)); 
	    
	      for(int i = 0; i < item.size(); i++){
	    	 /* new AlertDialog.Builder(this)
				.setIcon(R.drawable.ic_launcher)
				.setTitle("[" + item.get(i) + "]")
				.setPositiveButton("OK", null).show();*/
	    	buf.write(item.get(i));
	  		buf.newLine();
	  		buf.write(fav.get(i));
	  		buf.newLine();
	      }
	    
	      buf.close();
	      //outputStream.close();
	    } catch (Exception e) {
	    	new AlertDialog.Builder(this)
			.setIcon(R.drawable.ic_launcher)
			.setTitle("[" + "couldnt write" + "]")
			.setPositiveButton("OK", null).show();
	      e.printStackTrace();
	    }
	}
	
	private void setFav(String value){
		
		File file = new File(value);
		if(!file.isHidden() && file.canRead()){
			fav.add(file.getPath());
    		item.add(file.getName());
    		onContentChanged();
		}
		
		//ArrayAdapter<String> favList = new ArrayAdapter<String>(this, R.layout.row,item);
		//setListAdapter(favList);
		
		
		
	}
	
	private void noStorage() {
		new AlertDialog.Builder(this)
		.setIcon(R.drawable.ic_launcher)
		.setTitle("No storage detected")
		.setPositiveButton("OK", null).show();
	//TODO: exit app;
		finish();
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id){
		if(position == 0 ){
			Intent intent = new Intent(this, FileExActivity.class);
	    	//EditText editText = (EditText) findViewById(R.id.edit_message);
	    	//String message = editText.getText().toString();
	    	//intent.putExtra(EXTRA_MESSAGE, message);
	    	startActivityForResult(intent, PICK_FILE);
		}
		else {
			
			File file = new File(fav.get(position));
			/*new AlertDialog.Builder(this)
			.setIcon(R.drawable.ic_launcher)
			.setTitle("[" + fav.get(position) + "]")
			.setPositiveButton("OK", null).show();*/
			
			/*new AlertDialog.Builder(this)
			.setIcon(R.drawable.ic_launcher)
			.setTitle("[" + file.getName() + "]")
			.setPositiveButton("OK", null).show();*/
			Uri file_n = Uri.fromFile(file);
			
			
			String mime = getMimeType2(fav.get(position), this);
			/*new AlertDialog.Builder(this)
			.setIcon(R.drawable.ic_launcher)
			.setTitle("[" + mime + "]")
			.setPositiveButton("OK", null).show();*/
			Intent fileIntent = new Intent(Intent.ACTION_VIEW);
			fileIntent.setDataAndType(file_n, mime);
			
			
			
			PackageManager packageManager = getPackageManager();
			List<ResolveInfo> activities = packageManager.queryIntentActivities(fileIntent, 0);
			if(activities.size() > 0) {
				startActivity(fileIntent);
				
			}
			else {
				new AlertDialog.Builder(this)
				.setIcon(R.drawable.ic_launcher)
				.setTitle("[" + "No app found for this file type" + "]")
				.setPositiveButton("OK", null).show();
			}
			
			 
		}
	}
	
	
	public static String getMimeType2(String path, Context context) {	 
		String extention = path.substring(path.lastIndexOf(".") ); 
		String mimeTypeMap = MimeTypeMap.getFileExtensionFromUrl(extention); 
		String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(mimeTypeMap); 
		return mimeType;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == Activity.RESULT_OK){
			//String file_name1 = data.getStringExtra("file_name");
			String file_path1 = data.getStringExtra("file_path");
			/*new AlertDialog.Builder(this)
			.setIcon(R.drawable.ic_launcher)
			.setTitle("[" + file_name1 + "]")
			.setPositiveButton("OK", null).show();*/
			setFav(file_path1);
			//item.add(file_name1);
		}
		else {
			/*new AlertDialog.Builder(this)
			.setIcon(R.drawable.ic_launcher)
			.setTitle("[" + "file not found" + "]")
			.setPositiveButton("OK", null).show();*/
		}
	}

}
