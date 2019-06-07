package com.app.shipapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.app.shipapp.R;
import com.app.shipapp.app_utils.ConstantMethod;
import com.app.shipapp.modals.SearchModal;

import java.util.List;

public class SingleShipActivity extends AppCompatActivity {
    private TextInputEditText shipName,imoNum,callSign,mmsi,cost;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_ship);
        List<SearchModal> searchModals = ConstantMethod.getUsers(this);
        Log.e("cart is",""+searchModals);
        shipName = findViewById(R.id.ship_name);
        imoNum = findViewById(R.id.imo_num);
        callSign = findViewById(R.id.call_sign);
        mmsi = findViewById(R.id.mmsi);
        cost = findViewById(R.id.cost);
        Intent i = getIntent();
        SearchModal searchModal = (SearchModal)i.getSerializableExtra("single_ship");

        shipName.setText(searchModal.getShipName());
        imoNum.setText(searchModal.getImoNumber());
        callSign.setText(searchModal.getCallSign());
        mmsi.setText(searchModal.getMmsi());
        cost.setText(searchModal.getCost());
    }
}
