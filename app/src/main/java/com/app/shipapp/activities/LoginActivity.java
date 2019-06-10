package com.app.shipapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.app.shipapp.R;
import com.app.shipapp.app_utils.AppAPis;
import com.app.shipapp.app_utils.ConstantMethod;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private EditText emailEdt,passEdt;
    private Button loginBtn,signUpBtn;
    private CallbackManager callbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ConstantMethod.setBackAndTitle(this,"Login");
        emailEdt = findViewById(R.id.email_edt);
        passEdt = findViewById(R.id.pass_edt);
        loginBtn = findViewById(R.id.login_btn);
        signUpBtn = findViewById(R.id.signup_btn);
        loginBtn.setOnClickListener(v->{
            ConstantMethod.showProgressBar(this);
            String emailStr = emailEdt.getText().toString().trim();
            String passStr = passEdt.getText().toString().trim();
            loginUser(emailStr,passStr);
        });
        ConstantMethod.printHashKey(this);

        callbackManager = CallbackManager.Factory.create();

        Button fb = findViewById(R.id.fb);
        LoginButton loginButton = findViewById(R.id.login_button);
        List< String > permissionNeeds = Arrays.asList("user_photos", "email",
                "user_birthday", "public_profile", "AccessToken","gender");
        loginButton.setReadPermissions(permissionNeeds);
        loginButton.registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {@Override
                public void onSuccess(LoginResult loginResult) {

                    System.out.println("onSuccess");

                    String accessToken = loginResult.getAccessToken()
                            .getToken();
                    Log.i("accessToken", accessToken);

                    GraphRequest request = GraphRequest.newMeRequest(
                            loginResult.getAccessToken(),
                            new GraphRequest.GraphJSONObjectCallback() {@Override
                            public void onCompleted(JSONObject object, GraphResponse response) {

                                Log.i("LoginActivity",
                                        response.toString());
                                try {
                                    String id = object.getString("id");
                                    try {
                                        URL profile_pic = new URL("http://graph.facebook.com/" + id + "/picture?type=large");
                                        Log.i("profile_pic", profile_pic + "");
                                    } catch (MalformedURLException e) {
                                        e.printStackTrace();
                                    }
                                    String name = object.getString("name");
                                    String email = object.getString("email");
                                    String gender = object.getString("gender");
                                    String birthday = object.getString("birthday");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            });
                    Bundle parameters = new Bundle();
                    parameters.putString("fields","id,name,email,gender, birthday");
                    request.setParameters(parameters);
                    request.executeAsync();
                }

                    @Override
                    public void onCancel() {
                        System.out.println("onCancel");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        System.out.println("onError");
                        Log.v("LoginActivity", exception.getCause().toString());
                    }
                });
        fb.setOnClickListener(v->loginButton.performClick());
        signUpBtn.setOnClickListener(v->startActivity(new Intent(this,SignUpActivity.class)));
    }

    private void loginUser(String email, String pass){
        AndroidNetworking
                .post(AppAPis.LOGIN_API)
                .addBodyParameter("email",email)
                .addBodyParameter("password",pass)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            ConstantMethod.dismissDialog();
                            String status = response.getString("status");
                            if(status.equals("success")){
                                String token = response.getString("token");
                                String name = response.getString("name");
                                String id = response.getString("id");
                                String email = response.getString("email");
                                ConstantMethod.setStringPreference("user_token",token,LoginActivity.this);
                                ConstantMethod.setStringPreference("user_name",name,LoginActivity.this);
                                ConstantMethod.setStringPreference("user_id",id,LoginActivity.this);
                                ConstantMethod.setStringPreference("user_email",email,LoginActivity.this);
                                ConstantMethod.setStringPreference("login_status","login",LoginActivity.this);
                                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                            }
                            else if(status.equals("error")){
                                Toast.makeText(LoginActivity.this, "Email or Password is incorrect", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(LoginActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(LoginActivity.this, anError.getMessage(), Toast.LENGTH_SHORT).show();
                        ConstantMethod.dismissDialog();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode,
                                    Intent data) {
        super.onActivityResult(requestCode, responseCode, data);
        callbackManager.onActivityResult(requestCode, responseCode, data);
    }
}
