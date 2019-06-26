package com.app.shipapp.app_utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;

import com.app.shipapp.activities.CheckoutActivity;
import com.app.shipapp.modals.SearchModal;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ConstantMethod {
    private static ProgressDialog progressDialog;
    private static final String PREFS_TAG = "pref_tag";
    private static final String OBJECT_TAG = "object_tag";

    public static void showProgressBar(Context context){
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please wait");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public static void dismissDialog(){
        progressDialog.dismiss();
    }

    public static void showDialog(Context context, String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Ok",null);
        builder.show();
    }

    public static void setBackAndTitle(AppCompatActivity activity,String title){
        if (activity.getSupportActionBar() != null){
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        activity.setTitle(title);
    }


    public static void printHashKey(Context pContext) {
        try {
            PackageInfo info = pContext.getPackageManager().getPackageInfo(pContext.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.e("hashKey", "printHashKey() Hash Key: " + hashKey);
            }
        } catch (NoSuchAlgorithmException e) {
            Log.e("Exception", "printHashKey()", e);
        } catch (Exception e) {
            Log.e("Exception", "printHashKey()", e);
        }
    }

    public static void setStringPreference(String key, String value, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getStringPreference(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, "");
    }

    public static void saveArrayList(Context context, List<SearchModal> list){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString("save_ships", json);
        editor.apply();
    }

    public static List<SearchModal> getArrayList(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = prefs.getString("save_ships", null);
        Type type = new TypeToken<List<SearchModal>>() {}.getType();
        return gson.fromJson(json, type);
    }

    public static void emptyCart(Context context){
        List<SearchModal> searchModals = ConstantMethod.getArrayList(context);
        searchModals.clear();
        ConstantMethod.saveArrayList(context, searchModals);
    }

    public static String currentDate(){
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        return df.format(c);
    }
}
