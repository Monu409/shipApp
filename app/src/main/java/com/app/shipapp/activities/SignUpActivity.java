package com.app.shipapp.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.app.shipapp.R;
import com.app.shipapp.app_utils.ConstantMethod;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.app.shipapp.app_utils.AppAPis.SIGN_UP_API;

public class SignUpActivity extends AppCompatActivity {
    private EditText nameEdt,emailEdt,passEdt,cnfrmPassEdt;
    private Button signUpBtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        nameEdt = findViewById(R.id.name_edt);
        emailEdt = findViewById(R.id.email_edt);
        passEdt = findViewById(R.id.pass_edt);
        cnfrmPassEdt = findViewById(R.id.cnfrn_pass_edt);
        signUpBtn = findViewById(R.id.signup_btn);
        signUpBtn.setOnClickListener(v->signUpUser());
    }

    private void signUpUser(){
        ConstantMethod.showProgressBar(this);
        String nameStr = nameEdt.getText().toString().trim();
        String emailStr = emailEdt.getText().toString().trim();
        String passStr = passEdt.getText().toString().trim();
        String cnfrmPassStr = cnfrmPassEdt.getText().toString().trim();

        if(!passStr.equals(cnfrmPassStr)){
            Toast.makeText(this, "Password and Confirm Password is not match", Toast.LENGTH_SHORT).show();
        }
        else {
            AndroidNetworking
                    .post(SIGN_UP_API)
                    .addBodyParameter("name",nameStr)
                    .addBodyParameter("email",emailStr)
                    .addBodyParameter("password",passStr)
                    .addBodyParameter("password_confirmation",cnfrmPassStr)
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            ConstantMethod.dismissDialog();
                            try {
                                String success = response.getString("success");
                                if(success.equals("true")){
                                    String message = response.getString("message");
                                    ConstantMethod.showDialog(SignUpActivity.this,"Thank You", message);
                                }
                                if(success.equals("false")){
                                    String message = response.getString("error");
                                    JSONObject jsonObject = new JSONObject(message);
                                    JSONArray jsonArray = jsonObject.getJSONArray("email");
                                    String alrtMsg = jsonArray.getString(0);
                                    ConstantMethod.showDialog(SignUpActivity.this,"Alert", alrtMsg);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                            ConstantMethod.dismissDialog();
                            Toast.makeText(SignUpActivity.this, anError.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}
