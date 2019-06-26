package com.app.shipapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.app.shipapp.R;
import com.app.shipapp.app_utils.ConstantMethod;
import com.app.shipapp.modals.SearchModal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static com.app.shipapp.app_utils.AppAPis.SAVE_ORDER;

public class CheckoutActivity extends BaseActivity {
    private EditText emailEdt,nameEdt,addressEdt,cityEdt,provinceEdt,postalEdt,phoneEdt;
    private Button checkOut;
    private TextView totalTxt;
    String totalTxtStr;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConstantMethod.setBackAndTitle(this,"Checkout");
        emailEdt = findViewById(R.id.email_edt);
        nameEdt = findViewById(R.id.name_edt);
        addressEdt = findViewById(R.id.address_edt);
        cityEdt = findViewById(R.id.city_edt);
        provinceEdt = findViewById(R.id.province_edt);
        postalEdt = findViewById(R.id.pcode_edt);
        phoneEdt = findViewById(R.id.phone_edt);
        totalTxtStr = getIntent().getStringExtra("total_txt");
        totalTxt = findViewById(R.id.total_txt);
        totalTxt.setText("Total: "+totalTxtStr);
        String usersName = ConstantMethod.getStringPreference("user_name",this);
        String usersEmail = ConstantMethod.getStringPreference("user_email",this);
        emailEdt.setText(usersEmail);
        nameEdt.setText(usersName);
        checkOut = findViewById(R.id.checkout);
        checkOut.setOnClickListener(v->saveOrderToServer());
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_checkout;
    }

    private void saveOrderToServer(){
        ConstantMethod.showProgressBar(this);
        String emailStr = emailEdt.getText().toString().trim();
        String nameStr = nameEdt.getText().toString().trim();
        String addressStr = addressEdt.getText().toString().trim();
        String cityStr = cityEdt.getText().toString().trim();
        String provinceStr = provinceEdt.getText().toString().trim();
        String postalStr = postalEdt.getText().toString().trim();
        String phoneStr = phoneEdt.getText().toString().trim();
        String userId = ConstantMethod.getStringPreference("user_id",this);
        String token = ConstantMethod.getStringPreference("user_token",this);
        String url = SAVE_ORDER+token;
        List<SearchModal> searchModalList = ConstantMethod.getArrayList(this);
        String formateOfSendCart = "";
        JSONArray jsonArray = new JSONArray();
        try {
            for(int i=0;i<searchModalList.size();i++) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", String.valueOf(searchModalList.get(i).getId()));
                jsonObject.put("qty", "2");
                jsonArray.put(i, jsonObject);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AndroidNetworking
                .post(url)
                .addBodyParameter("user_id",userId)
                .addBodyParameter("email",emailStr)
                .addBodyParameter("name",nameStr)
                .addBodyParameter("address",addressStr)
                .addBodyParameter("city",cityStr)
                .addBodyParameter("province",provinceStr)
                .addBodyParameter("postalcode",postalStr)
                .addBodyParameter("phone",phoneStr)
                .addBodyParameter("name_on_card","Visa Dabit")
                .addBodyParameter("subtotal",totalTxtStr)
                .addBodyParameter("products",jsonArray.toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            ConstantMethod.dismissDialog();
                            String result = response.getString("result");
                            String message = response.getString("message");
                            Toast.makeText(CheckoutActivity.this, message, Toast.LENGTH_SHORT).show();
                            ConstantMethod.emptyCart(CheckoutActivity.this);
                            Intent intent = new Intent(CheckoutActivity.this,MainActivity.class);
                            startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        ConstantMethod.dismissDialog();
                        Toast.makeText(CheckoutActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
