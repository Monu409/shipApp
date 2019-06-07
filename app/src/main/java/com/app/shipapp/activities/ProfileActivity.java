package com.app.shipapp.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.app.shipapp.R;
import com.app.shipapp.app_utils.ConstantMethod;

import org.json.JSONException;
import org.json.JSONObject;

import static com.app.shipapp.app_utils.AppAPis.USER_PROFILE;

public class ProfileActivity extends AppCompatActivity {
    private TextInputEditText nameEdt,emailEdt,mobileEdt;
    private Button saveProfile;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ConstantMethod.setBackAndTitle(this,"Profile");
        nameEdt = findViewById(R.id.name_edt);
        emailEdt = findViewById(R.id.email_edt);
        mobileEdt = findViewById(R.id.mobile_edt);
        saveProfile = findViewById(R.id.save_profile);
        nameEdt.setEnabled(false);
        emailEdt.setEnabled(false);
        mobileEdt.setEnabled(false);
        saveProfile.setEnabled(false);
        saveProfile.setAlpha(0.3f);
        getUserProfile();
        saveProfile.setOnClickListener(v->{
            nameEdt.setEnabled(false);
            emailEdt.setEnabled(false);
            mobileEdt.setEnabled(false);
            saveProfile.setAlpha(0.3f);
            saveProfile.setEnabled(false);
        });
    }

    private void getUserProfile(){
        ConstantMethod.showProgressBar(this);
        String userToken = ConstantMethod.getStringPreference("user_token",this);
        String profileUrl = USER_PROFILE+userToken;
        AndroidNetworking
                .get(profileUrl)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                       ConstantMethod.dismissDialog();
                        try {
                            JSONObject jsonObject = response.getJSONObject("message");
                            String name = jsonObject.getString("name");
                            String email = jsonObject.getString("email");
                            String mobile = jsonObject.getString("mobile");
                            nameEdt.setText(name);
                            emailEdt.setText(email);
                            mobileEdt.setText(mobile);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        ConstantMethod.dismissDialog();
                        Toast.makeText(ProfileActivity.this, anError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_profile:
                saveProfile.setVisibility(View.VISIBLE);
                nameEdt.setEnabled(true);
                emailEdt.setEnabled(true);
                mobileEdt.setEnabled(true);
                saveProfile.setAlpha(1.0f);
                saveProfile.setEnabled(true);
                return true;
            case android.R.id.home:
                finish();
                return true;
            default:
                return false;
        }
    }
}
