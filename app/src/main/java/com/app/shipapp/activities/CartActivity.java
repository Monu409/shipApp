package com.app.shipapp.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.app.shipapp.R;
import com.app.shipapp.adapters.SearchShipAdapter;
import com.app.shipapp.app_utils.ConstantMethod;
import com.app.shipapp.modals.SearchModal;

import java.util.List;

public class CartActivity extends AppCompatActivity {
    private RecyclerView cartList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConstantMethod.setBackAndTitle(this,"Cart");
        setContentView(R.layout.activity_cart);
        cartList = findViewById(R.id.cart_list);
        cartList.setLayoutManager(new LinearLayoutManager(this));
        List<SearchModal> searchModalList = ConstantMethod.getUsers(this);
        SearchShipAdapter searchShipAdapter = new SearchShipAdapter(this,searchModalList,"cart");
        cartList.setAdapter(searchShipAdapter);
    }
}
