/*
 * File: Utils.java
 * 
 * Copyright (C) 2009 The Humanitarian FOSS Project (http://www.hfoss.org)
 * 
 * This file is part of POSIT, Portable Open Search and Identification Tool.
 *
 * POSIT is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License (LGPL) as published 
 * by the Free Software Foundation; either version 3.0 of the License, or (at
 * your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU LGPL along with this program; 
 * if not visit http://www.gnu.org/licenses/lgpl.html.
 * 
 */
package org.hfoss.posit.utilities;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import android.content.BroadcastReceiver;

import android.R;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A collection of utility functions that may not be worth moving to their own spaces
 */
public class Utils {
	
	private static final String TAG = "Utils";
	public static ConnectivityManager conManage;
/**
 * Creates a Connectivity Manager for the given context and returns true if a network is available
 * and false if otherwise.
 * @param mContext
 * @return Connectivity Boolean
 */
	public static boolean isNetworkAvailable(Context context)
	{
		conManage = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        if (conManage == null) {
           Log.w(TAG, "couldn't get connectivity manager");
        } else {
            NetworkInfo[] info = conManage.getAllNetworkInfo();
            Log.v(TAG,info.toString());
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                           Log.v(TAG, "network is available");
                        return true;
                    }
                }
            }
        }
            Log.v(TAG, "network is not available");

        return false; 
	}
	
	public static boolean debug = true;
	
	
    /**
     * Removes '[Error]' or '[Success'] from the beginning of the response strings
     * that are returned from http Post and Get.
     * @param response
     * @return
     */
    public static String stripHttpResultCode(String str) {
    	return str.substring(str.indexOf(']') + 1);
    }
    
    public static boolean isSuccessfulHttpResultCode(String str) {
    	return str.indexOf("[Success]") != -1;
    }
	
	/**
	 * This is for showing the Toast on screen for notifications
	 * 
	 * @param mContext
	 * @param text
	 */
	public static void showToast(Context mContext, String text) {
		Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
	}

	public static void showToast(Context mContext, int resId) {
		Toast.makeText(mContext, resId, Toast.LENGTH_SHORT).show();
	}
	/**
	 *  Helper method for creating notification 
	 *  You'll still need to get the NotificationManager using
	 *  mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)  
	 *
	 */
	public static void showDefaultNotification(Context cxt,NotificationManager mNotificationManager, int notification_id, String text){
		Notification notification = new Notification(R.drawable.btn_plus, "Posit", 
				System.currentTimeMillis());
		notification.flags = Notification.FLAG_AUTO_CANCEL;
		mNotificationManager.notify(2, notification); //magic number
	}
	/**
	 * This gives the list of strings in a string-array as an ArrayList
	 * 
	 * @param mContext
	 * @param resId
	 * @return
	 */
	public static ArrayList<String> getFieldsFromResource(Context mContext,
			int resId) {
		Resources res = mContext.getResources();
		ArrayList<String> items;
		items = new ArrayList<String>(Arrays.asList(res.getStringArray(resId)));
		return items;
	}

	/**
	 * This is to find the name type of the columns from the table resource Id
	 * Since the tables are generated from the same resource Id, we are assured to have
	 * column_name type(as in integer,long,double,text etc. 
	 * This is very useful in pulling appropriate types from the database
	 * @param mContext
	 * @param resId
	 * @return
	 */
	public static HashMap<String,String> getColumnTypeMapFromResource(Context mContext, int resId){
		Resources res = mContext.getResources();
		HashMap<String,String> columnNameTypeMap = new HashMap<String, String>();

		for (String field : res.getStringArray(resId)) {
			String[] str = field.split("\\s+");
			columnNameTypeMap.put(str[0], str[1]);
		}
		return columnNameTypeMap;
	}

	/**
	 * Maps the items provided into the views
	 * 
	 * @param items
	 * @param views
	 */
	public static void putCursorItemsInViews(Cursor c, String[] items,
			HashMap<String, View> views) {
		for (String item : items) {
			View v = views.get(item);
			if (v instanceof TextView) {
				((TextView) v).setText(c.getString(c
						.getColumnIndexOrThrow(item)));
			} else if (v instanceof EditText) {
				((EditText) v).setText(c.getString(c
						.getColumnIndexOrThrow(item)));
			} else if (v instanceof CheckBox) {
				((CheckBox) v).setChecked(c.getInt(c
						.getColumnIndexOrThrow(item)) == 1 ? true : false);
			} else if (v instanceof RadioGroup) {
				((RadioGroup) v).check(c.getInt(c.getColumnIndexOrThrow(item)));
			}
		}
	}

	/**
	 * Given a context and MediaStore-specific uri, this function will return the filename
	 * associated with the uri. Note: only works on items that can be found in MediaStore.
	 * @param mContext
	 * @param mUri
	 * @return Filename the URI links to
	 */
	public static String getFilenameFromMediaUri(Context mContext, Uri mUri) {
		final String[] mProjection = new String[] {
				android.provider.BaseColumns._ID,
				android.provider.MediaStore.MediaColumns.TITLE,
				android.provider.MediaStore.MediaColumns.DATA };

		Cursor mCursor= mContext.getContentResolver().query(mUri, mProjection, null, null, null);

		String mFilename = null;
		
		if (mCursor != null && mCursor.moveToFirst()) {
			mFilename = mCursor.getString(mCursor.getColumnIndexOrThrow(mProjection[2]));
		}
		
		return mFilename;
	}

	/**
	 * Finds the position of an object in the given resource array or -1 otherwise
	 * @param mContext
	 * @param object
	 * @param resId
	 * @return
	 */
	public static int getPositionInResourceArray(Context mContext, Object object, int resId) {
		Resources res = mContext.getResources();
		ArrayList<String> items;

		items = new ArrayList<String>(Arrays.asList(res.getStringArray(resId)));
		return items.indexOf(object);
	}

	/**
	 * @param mContext
	 * @param s
	 */
	public static void bindArrayListToSpinner(Context mContext, Spinner s, ArrayList<String> spinnerItems) {
		ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(mContext, 
				android.R.layout.simple_spinner_dropdown_item,  (String[])spinnerItems.toArray());
		s.setAdapter(adapter);
	}

	/**
	 * Gets the unique IMEI code for the phone used for identification
	 * The phone should have proper permissions (READ_PHONE_STATE) to be able to get this data.
	 */
	public static String getIMEI(Context mContext) {
		TelephonyManager tm = (TelephonyManager)mContext.getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getDeviceId();
	}


	public static ContentValues getContentValuesFromCursor(Cursor c) {
		ContentValues args = new ContentValues();
		for (int i = 0; i < c.getColumnCount(); i++) {
			args.put(c.getColumnName(i), c.getString(i));
		}
		return args;
	}

	public static List<Integer> getListFromCollection(Collection<Integer> mySet){
		List<Integer> myList = new ArrayList<Integer>();
		Iterator<Integer> iter = mySet.iterator();
		while (iter.hasNext()) {
			myList.add(iter.next());
		}
		return myList;
	}

	public static HashMap<String,String> getMapFromCursor(Cursor c){
		HashMap<String, String> myMap = new HashMap<String, String>();
		for (int i = 0; i < c.getColumnCount(); i++) {
			myMap.put(c.getColumnName(i), c.getString(i));
		}
		return myMap;
	}

	/**
	 * Checks if the phone has any connection at all, wifi or data.  Used at startup to see if the phone should
	 * go into ad hoc mode or not.  
	 * 
	 * @param mContext the Context asking for the connection, in this case only the main activity
	 * @return whether any connection exists
	 */
	public static boolean isConnected(Context mContext, int typeOfConnection) {
		ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();

		if (info == null) {
			Log.i(TAG,"isConnected info is null");
			return false;
		}
		return info.getType() == typeOfConnection;
	}
	
	/**
	 * NOTE:  This method doesn't appear to work properly.
	 * 
	 * Checks if the phone has any connection at all, wifi or data.  Used at startup to see if the phone should
	 * go into ad hoc mode or not.  
	 * 
	 * @param mContext the Context asking for the connection, in this case only the main activity
	 * @return whether any connection exists
	 */
	public static boolean isConnected(Context mContext) {
		ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		if (info ==  null)
			return false;
		else return true;
	}
	
	  public static String getTimestamp() {
		    Calendar cal = Calendar.getInstance();
		    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		    return sdf.format(cal.getTime());
		  }
	  
	  public static void showNetworkErrorDialog(Context context) {
		  new AlertDialog.Builder(context)
	        .setTitle("Network Error")
	        .setMessage("To access or change projects, connect to Wifi or a data network.")
	        .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int whichButton) {
	                //        Log.d(MSG_TAG, "Close pressed");
	                        //this.application.finish();
	                }
	        })
	        .show();
	  }
	  /**
	   * Taken from  
	   * http://stackoverflow.com/questions/326390/how-to-create-a-java-string-from-the-contents-of-a-file
	   * Reads content of a file into a string. This is useful for small-ish files
	   * You shouldn't be reading very large files anyways, like this.
	   * @param path
	   * @return
	   * @throws IOException
	   */
	  public static String readFileToString(String file) throws IOException {
		  BufferedReader reader = new BufferedReader( new FileReader (file));
		    String line  = null;
		    StringBuilder stringBuilder = new StringBuilder();
		    String ls = System.getProperty("line.separator");
		    while( ( line = reader.readLine() ) != null ) {
		        stringBuilder.append( line );
		        stringBuilder.append( ls );
		    }
		    return stringBuilder.toString();
		}
	  
	  public static String readInputStreamToString(InputStream inputStream) {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			byte buf[] = new byte[1024];
			int len;
			try {
				while ((len = inputStream.read(buf)) != -1) {
					outputStream.write(buf, 0, len);
				}
				outputStream.close();
				inputStream.close();
			} catch (IOException e) {
			}
			return outputStream.toString();
		}
	  
	  /**
	   * Converts and IP address to an integer
	   * @param addr
	   * @return
	   */
	  public static int ipToInt(String addr) {
	        String[] addrArray = addr.split("\\.");

	        int num = 0;
	        for (int i=0;i<addrArray.length;i++) {
	            int power = 3-i;

	            num += ((Integer.parseInt(addrArray[i])%256 * Math.pow(256,power)));
	        }
	        return num;
	    }
}
