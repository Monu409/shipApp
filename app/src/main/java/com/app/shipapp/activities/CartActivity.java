package com.app.shipapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.app.shipapp.R;
import com.app.shipapp.adapters.SearchShipAdapter;
import com.app.shipapp.app_utils.ConstantMethod;
import com.app.shipapp.modals.SearchModal;

import java.util.List;

public class CartActivity extends BaseActivity implements SearchShipAdapter.CrossClick {
    private RecyclerView cartList;
    private TextView totalTxt;
    private Button proceedBtn;
    List<SearchModal> searchModalList;
    SearchShipAdapter searchShipAdapter;
    String totalCostStr;
    TextView textView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConstantMethod.setBackAndTitle(this,"Cart");
        cartList = findViewById(R.id.cart_list);
        totalTxt = findViewById(R.id.total_txt);
        proceedBtn = findViewById(R.id.proceed_btn);
        textView = findViewById(R.id.cart_status);
        cartList.setLayoutManager(new LinearLayoutManager(this));
        searchModalList = ConstantMethod.getArrayList(this);
        searchShipAdapter = new SearchShipAdapter(this,searchModalList,"cart",this);
        cartList.setAdapter(searchShipAdapter);
        totalCostStr = "$"+totalCount(searchModalList);
        totalTxt.setText(totalCostStr);
        proceedBtn.setOnClickListener(v->{
            if(totalCostStr.equals("$0.0")){
                onBackPressed();
            }
            else {
                Intent intent = new Intent(this, CheckoutActivity.class);
                intent.putExtra("total_txt", totalCostStr);
                startActivity(intent);
            }
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_cart;
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

    @Override
    public void onClick(int position) {
        List<SearchModal> searchModalsPref = ConstantMethod.getArrayList(this);
        for(int i1 = 0 ; i1 < searchModalsPref.size() ; i1++){
            if(searchModalsPref.get(position).getShipName().equalsIgnoreCase(searchModalsPref.get(i1).getShipName())){
                searchModalsPref.remove(i1);
                ConstantMethod.saveArrayList(this,searchModalsPref);
            }
        }
        searchShipAdapter = new SearchShipAdapter(this,searchModalsPref,"cart",this);
        cartList.setAdapter(searchShipAdapter);
        if(searchModalsPref!=null) {
            double nowTotal = totalCount(searchModalsPref);
            if(nowTotal==0.0){
                textView.setVisibility(View.VISIBLE);
                cartList.setVisibility(View.GONE);
                proceedBtn.setText("Continue Shoping");
            }
            else{
                textView.setVisibility(View.GONE);
                cartList.setVisibility(View.VISIBLE);
                proceedBtn.setText("Proceed");
            }
            totalCostStr = "$" + nowTotal;
            totalTxt.setText(totalCostStr);
        }
    }
}
