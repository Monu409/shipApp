package com.app.shipapp.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
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
import com.app.shipapp.app_utils.FinalVariable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.app.shipapp.app_utils.AppAPis.SIGN_UP_API;
import static com.app.shipapp.app_utils.FinalVariable.EMAIL_PATTERN;

public class SignUpActivity extends BaseActivity {
    private EditText nameEdt,emailEdt,passEdt,cnfrmPassEdt;
    private Button signUpBtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConstantMethod.setBackAndTitle(this,"Sign up");
        nameEdt = findViewById(R.id.name_edt);
        emailEdt = findViewById(R.id.email_edt);
        passEdt = findViewById(R.id.pass_edt);
        cnfrmPassEdt = findViewById(R.id.cnfrn_pass_edt);
        signUpBtn = findViewById(R.id.signup_btn);
        signUpBtn.setOnClickListener(v->signUpUser());
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_signup;
    }

    private void signUpUser(){
        String nameStr = nameEdt.getText().toString().trim();
        String emailStr = emailEdt.getText().toString().trim();
        String passStr = passEdt.getText().toString().trim();
        String cnfrmPassStr = cnfrmPassEdt.getText().toString().trim();
        if(nameStr.isEmpty()||emailStr.isEmpty()||passStr.isEmpty()||cnfrmPassStr.isEmpty()){
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
        }
        else if(!emailStr.matches(EMAIL_PATTERN)){
            Toast.makeText(this, "Enter a valid email address", Toast.LENGTH_SHORT).show();
        }
        else if(!passStr.equals(cnfrmPassStr)){
            Toast.makeText(this, "Password and Confirm Password is not match", Toast.LENGTH_SHORT).show();
        }
        else {
            ConstantMethod.showProgressBar(this);
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
                                    showDialog(SignUpActivity.this,"Thank You", message);
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

    private void showDialog(Context context, String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Ok",(dialog,which)->{
            String emailStr = emailEdt.getText().toString().trim();
            String passStr = passEdt.getText().toString().trim();
            Intent intent = new Intent(this,LoginActivity.class);
            intent.putExtra("email_intent",emailStr);
            intent.putExtra("pass_intent",passStr);
            startActivity(intent);
        });
        builder.show();
    }
}
