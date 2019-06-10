package com.app.shipapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.TextView;

import com.app.shipapp.R;
import com.app.shipapp.adapters.SearchShipAdapter;
import com.app.shipapp.app_utils.ConstantMethod;
import com.app.shipapp.modals.SearchModal;

import java.util.List;

public class CartActivity extends AppCompatActivity {
    private RecyclerView cartList;
    private TextView totalTxt;
    private Button proceedBtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        ConstantMethod.setBackAndTitle(this,"Cart");
        cartList = findViewById(R.id.cart_list);
        totalTxt = findViewById(R.id.total_txt);
        proceedBtn = findViewById(R.id.proceed_btn);
        cartList.setLayoutManager(new LinearLayoutManager(this));
        List<SearchModal> searchModalList = ConstantMethod.getUsers(this);
        SearchShipAdapter searchShipAdapter = new SearchShipAdapter(this,searchModalList,"cart");
        cartList.setAdapter(searchShipAdapter);
        String totalCostStr = "$"+String.valueOf(totalCount(searchModalList));
        totalTxt.setText(totalCostStr);
        proceedBtn.setOnClickListener(v->{
            Intent intent = new Intent(this,CheckoutActivity.class);
            intent.putExtra("total_txt",totalCostStr);
            startActivity(intent);
        });
    }

    private double totalCount(List<SearchModal> searchModalList){
        double total = 0.00;
        for(int i=0;i<searchModalList.size();i++){
            String totalCostStr = searchModalList.get(i).getCost();
            double totalCostDbl = Double.parseDouble(totalCostStr);
            total = total+totalCostDbl;
        }
        return total;
    }
}
