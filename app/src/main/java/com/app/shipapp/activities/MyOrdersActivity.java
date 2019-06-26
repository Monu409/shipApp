package com.app.shipapp.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.app.shipapp.R;
import com.app.shipapp.adapters.MyOrdersAdapter;
import com.app.shipapp.app_utils.ConstantMethod;
import com.app.shipapp.modals.MyOrders;
import com.google.gson.Gson;

import org.json.JSONObject;

import static com.app.shipapp.app_utils.AppAPis.MY_ORDERS;

public class MyOrdersActivity extends BaseActivity {
    private RecyclerView orderList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConstantMethod.setBackAndTitle(this,"My Orders");
        orderList = findViewById(R.id.my_order_list);
        ConstantMethod.setBackAndTitle(this,"My Orders");
        orderList.setLayoutManager(new LinearLayoutManager(this));
        callMyOrders();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_my_orders;
    }

    private void callMyOrders(){
        String token = ConstantMethod.getStringPreference("user_token",this);
        String url = MY_ORDERS+token;
        AndroidNetworking
                .get(url)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("res",""+response);
                        String strResponse = String.valueOf(response);
                        Gson gson = new Gson();
                        MyOrders myOrders= gson.fromJson(strResponse,MyOrders.class);
                        MyOrdersAdapter myOrdersAdapter = new MyOrdersAdapter(myOrders,MyOrdersActivity.this);
                        orderList.setAdapter(myOrdersAdapter);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("res",""+anError);
                    }
                });
    }
// 0 1 1 2 3 5  8 13
    private void fibnoci(){
        int x1=0, x2=1;
        for(int i=0;i<10;i++){
            System.out.print(x1);
            int sum = x1+x2;
            x1 = x2;
            x2 = sum;
        }
    }
}
