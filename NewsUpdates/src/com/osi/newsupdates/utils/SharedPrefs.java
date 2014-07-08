package com.osi.newsupdates.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

	public class SharedPrefs {


		public static final String PREFERENCES_NAME = "NewsApp_Android";
		
		public static String getTitle(Context context) {
			return getSharedPreference(context).getString("title","");
		}

		public static void setTitle(Context context,String title) {
			Editor edit = getSharedPreference(context).edit();
			edit.putString("title", title);
			edit.commit();
		}

		public static String getDescription(Context context) {
			return getSharedPreference(context).getString("desc","");
		}

		public static void setDescription(Context context,String desc) {
			Editor edit = getSharedPreference(context).edit();
			edit.putString("desc", desc);
			edit.commit();
		}

		//shared preferences
		public static SharedPreferences getSharedPreference(Context context) {
			return context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
		}
		public static void setimageUrl(Context context,String imageurl) {
			Editor edit = getSharedPreference(context).edit();
			edit.putString("imageurl", imageurl);
			edit.commit();
		}

		public static String getimageUrl(Context context) {
			return getSharedPreference(context).getString("imageurl","");
		}
		
		public static Boolean getfirsttime(Context context) {
			return getSharedPreference(context).getBoolean("firsttime", false);
		}

		public static void setfirsttime(Context context,Boolean firsttime) {
			Editor edit = getSharedPreference(context).edit();
			edit.putBoolean("firsttime",firsttime);
			edit.commit();
		}
		
	}


