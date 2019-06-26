package com.app.shipapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import static com.app.shipapp.app_utils.AppAPis.SIGN_UP_API;
import static com.app.shipapp.app_utils.AppAPis.SOCIAL_LOGIN;

public class LoginActivity extends AppCompatActivity {
    private EditText emailEdt,passEdt;
    private Button loginBtn,signUpBtn;
    private CallbackManager callbackManager;
    private static final int RC_SIGN_IN = 234;
    private static final String TAG = "TAG_VALUE";
    GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ConstantMethod.setBackAndTitle(this,"Login");
        emailEdt = findViewById(R.id.email_edt);
        passEdt = findViewById(R.id.pass_edt);
        loginBtn = findViewById(R.id.login_btn);
        signUpBtn = findViewById(R.id.signup_btn);
        TextView signUpText = findViewById(R.id.signup_txt);
        SpannableString content = new SpannableString(signUpText.getText().toString());
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        signUpText.setText(content);
        Intent intent = getIntent();
        if(intent!=null){
            String emailStr = intent.getStringExtra("email_intent");
            String passStr = intent.getStringExtra("pass_intent");
            emailEdt.setText(emailStr);
            passEdt.setText(passStr);
        }
        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });


        loginBtn.setOnClickListener(v->{
            String emailStr = emailEdt.getText().toString().trim();
            String passStr = passEdt.getText().toString().trim();
            if(emailStr.isEmpty()||passStr.isEmpty()){
                Toast.makeText(this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
            }
            else {
                ConstantMethod.showProgressBar(this);
                loginUser(emailStr, passStr);
            }
        });
        ConstantMethod.printHashKey(this);

        callbackManager = CallbackManager.Factory.create();

        Button fb = findViewById(R.id.fb);
        LoginButton loginButton = findViewById(R.id.login_button);
        List< String > permissionNeeds = Arrays.asList("user_photos", "email",
                "user_birthday", "public_profile");
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
                                    socialLogin(name,email,id,"facebook");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            });
                    Bundle parameters = new Bundle();
                    parameters.putString("fields","id,name,email");
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
        signUpText.setOnClickListener(v->startActivity(new Intent(this,SignUpActivity.class)));
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
                        String errorMsg = "";
                        try {
                            JSONObject jsonObject = new JSONObject(anError.getErrorBody());
                            errorMsg = jsonObject.getString("message");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(LoginActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
                        ConstantMethod.dismissDialog();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent data) {
        super.onActivityResult(requestCode, responseCode, data);
        callbackManager.onActivityResult(requestCode, responseCode, data);
        if (requestCode == RC_SIGN_IN) {

            //Getting the GoogleSignIn Task
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                //Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                String name = account.getDisplayName();
                String email = account.getEmail();
                String id = account.getId();
                socialLogin(name,email,id,"google");

                //authenticating with firebase
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        //getting the auth credential
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);

        //Now using firebase we are signing in the user here
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "signInWithCredential:success");
                        FirebaseUser user = mAuth.getCurrentUser();

                        Toast.makeText(LoginActivity.this, "User Signed In", Toast.LENGTH_SHORT).show();
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithCredential:failure", task.getException());
                        Toast.makeText(LoginActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();

                    }

                    // ...
                });
    }


    //this method is called on click
    private void signIn() {
        //getting the google signin intent
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();

        //starting the activity for result
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void socialLogin(String nameStr, String emailStr, String fbUserId,String loginWith){
            ConstantMethod.showProgressBar(this);
            AndroidNetworking
                    .post(SOCIAL_LOGIN)
                    .addBodyParameter("name",nameStr)
                    .addBodyParameter("email",emailStr)
                    .addBodyParameter("social_id",fbUserId)
                    .addBodyParameter("login_with",loginWith)
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            ConstantMethod.dismissDialog();
                            try {
                                String name = response.getString("name");
                                String email = response.getString("email");
                                String token = response.getString("token");
                                String id = response.getString("id");
                                Toast.makeText(LoginActivity.this, "Login success", Toast.LENGTH_SHORT).show();
                                ConstantMethod.setStringPreference("user_token",token,LoginActivity.this);
                                ConstantMethod.setStringPreference("user_name",name,LoginActivity.this);
                                ConstantMethod.setStringPreference("user_id",id,LoginActivity.this);
                                ConstantMethod.setStringPreference("user_email",email,LoginActivity.this);
                                ConstantMethod.setStringPreference("login_status","login",LoginActivity.this);
                                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                            ConstantMethod.dismissDialog();
                            Toast.makeText(LoginActivity.this, anError.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
    }

}
