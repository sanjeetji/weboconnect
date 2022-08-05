package com.sanjeet.weboconnect.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

public class SharedPrefManager {

    private static SharedPrefManager mInstance;
    private static Context mCtx;
    public static final String PREF_FILE_KEY = "com.sanjeet.weboconnect.shared_pref";
    private static final String ANDROID_EMPLOYEE = "Android_employee";
    private static final String IOS_EMPLOYEE = "Ios_employee";
    private static final String WEB_EMPLOYEE = "Web_employee";

    private final SharedPreferences sharedPreferences;

    private SharedPrefManager(Context appContext) {
        mCtx = appContext;
        sharedPreferences = mCtx.getSharedPreferences(PREF_FILE_KEY, Context.MODE_PRIVATE);

    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context.getApplicationContext());
        }
        return mInstance;
    }

    public void saveAndroidEmployee(Set<String> employees) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet(ANDROID_EMPLOYEE, employees);
        editor.apply();
    }

    public Set<String> getAndroidEmployee() {
        return sharedPreferences.getStringSet(ANDROID_EMPLOYEE, new HashSet<String>());
    }

    public void saveIosEmployee(Set<String> employees) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet(IOS_EMPLOYEE, employees);
        editor.apply();
    }

    public Set<String> getIosEmployee() {
        return sharedPreferences.getStringSet(IOS_EMPLOYEE, new HashSet<String>());
    }

    public void saveWebEmployee(Set<String> employees) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet(WEB_EMPLOYEE, employees);
        editor.apply();
    }

    public Set<String> getWebEmployee() {
        return sharedPreferences.getStringSet(WEB_EMPLOYEE, new HashSet<String>());
    }
}
