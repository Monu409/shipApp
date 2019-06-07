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

import com.app.shipapp.modals.SearchModal;
import com.google.gson.Gson;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConstantMethod {
    private static ProgressDialog progressDialog;
    private static final String PREFS_TAG = "pref_tag";
    private static final String OBJECT_TAG = "object_tag";

    public static void showProgressBar(Context context){
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please wiat");
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

    public static void saveUsers(Context context, List<SearchModal> users) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(PREFS_TAG, Context.MODE_PRIVATE);
        editor = settings.edit();
        Gson gson = new Gson();
        String jsonUsers = gson.toJson(users);
        editor.putString(OBJECT_TAG, jsonUsers);
        editor.commit();
    }

    public static List<SearchModal> getUsers(Context context) {
        SharedPreferences settings;
        List<SearchModal> users;

        settings = context.getSharedPreferences(PREFS_TAG,
                Context.MODE_PRIVATE);

        if (settings.contains(OBJECT_TAG)) {
            String jsonUsers = settings.getString(OBJECT_TAG, null);
            Gson gson = new Gson();
            SearchModal[] userItems = gson.fromJson(jsonUsers,
                    SearchModal[].class);

            users = Arrays.asList(userItems);
            users= new ArrayList<>(users);
        } else
            return null;

        return users;
    }

}
