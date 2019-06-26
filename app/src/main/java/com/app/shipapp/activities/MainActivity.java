package com.app.shipapp.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.app.shipapp.R;
import com.app.shipapp.adapters.SearchShipAdapter;
import com.app.shipapp.app_utils.ConstantMethod;
import com.app.shipapp.modals.SearchModal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.app.shipapp.app_utils.AppAPis.SEARCH_SHIP_LIST;
import static com.app.shipapp.app_utils.AppAPis.SHIP_LIST;

public class MainActivity extends BaseActivity implements SearchShipAdapter.CrossClick {
    private RecyclerView shipLsit;
    private EditText shipSedt,imoSedt,callSedt,mmsiSedt;
    private ImageView searchImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shipLsit = findViewById(R.id.ship_list);
        shipSedt = findViewById(R.id.ship_sedt);
        imoSedt = findViewById(R.id.imo_sedt);
        callSedt = findViewById(R.id.call_sedt);
        mmsiSedt = findViewById(R.id.mmsi_sedt);
        searchImg = findViewById(R.id.search_img);
        shipLsit.setLayoutManager(new LinearLayoutManager(this));
        getShipDetailsList();
        searchImg.setOnClickListener(v->{
            String shipName = shipSedt.getText().toString();
            String imoName = imoSedt.getText().toString();
            String callSname = callSedt.getText().toString();
            String mmsiName = mmsiSedt.getText().toString();
            String []strings = {shipName,imoName,callSname,mmsiName};
            searchShip(strings);
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_main;
    }

    private void getShipDetailsList(){
        ConstantMethod.showProgressBar(this);
        List<SearchModal> searchModals = new ArrayList<>();
        String userToken = ConstantMethod.getStringPreference("user_token",this);
        String shipListUrl = SHIP_LIST+userToken;
        AndroidNetworking
                .get(shipListUrl)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            ConstantMethod.dismissDialog();
                            JSONObject jsonObject = response.getJSONObject("result");
                            JSONArray allShipList = jsonObject.getJSONArray("data");
                            for(int i=0;i<allShipList.length();i++){
                                JSONObject childObj = allShipList.getJSONObject(i);
                                String name = childObj.getString("ship_name");
                                String cost = childObj.getString("cost");
                                String imoNo = childObj.getString("imo_number");
                                String mmsi = childObj.getString("mmsi");
                                String callSign = childObj.getString("call_sign");
                                String shipId = childObj.getString("id");
                                SearchModal searchModal = new SearchModal();
                                searchModal.setShipName(name);
                                searchModal.setCost(cost);
                                searchModal.setImoNumber(imoNo);
                                searchModal.setMmsi(mmsi);
                                searchModal.setCallSign(callSign);
                                searchModal.setId(Integer.parseInt(shipId));
                                searchModals.add(searchModal);
                            }
                            SearchShipAdapter searchShipAdapter = new SearchShipAdapter(MainActivity.this,searchModals,"search",MainActivity.this);
                            shipLsit.setAdapter(searchShipAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        ConstantMethod.dismissDialog();
                        Toast.makeText(MainActivity.this, anError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void searchShip(String []shipName){
        ConstantMethod.showProgressBar(this);
        List<SearchModal> searchModals = new ArrayList<>();
        String userToken = ConstantMethod.getStringPreference("user_token",this);
        String searchShipListUrl = SEARCH_SHIP_LIST+userToken;
        AndroidNetworking
                .post(searchShipListUrl)
                .addBodyParameter("ship_name",shipName[0])
                .addBodyParameter("imo_number",shipName[1])
                .addBodyParameter("call_sign",shipName[2])
                .addBodyParameter("mmsi",shipName[3])
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            ConstantMethod.dismissDialog();
                            JSONObject jsonObject = response.getJSONObject("result");
                            JSONArray allShipList = jsonObject.getJSONArray("data");
                            for(int i=0;i<allShipList.length();i++){
                                JSONObject childObj = allShipList.getJSONObject(i);
                                String name = childObj.getString("ship_name");
                                String cost = childObj.getString("cost");
                                String imoNo = childObj.getString("imo_number");
                                String mmsi = childObj.getString("mmsi");
                                String callSign = childObj.getString("call_sign");
                                SearchModal searchModal = new SearchModal();
                                searchModal.setShipName(name);
                                searchModal.setCost(cost);
                                searchModal.setImoNumber(imoNo);
                                searchModal.setMmsi(mmsi);
                                searchModal.setCallSign(callSign);
                                searchModals.add(searchModal);
                            }
                            SearchShipAdapter searchShipAdapter = new SearchShipAdapter(MainActivity.this,searchModals,"search",MainActivity.this);
                            shipLsit.setAdapter(searchShipAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        ConstantMethod.dismissDialog();
                        Toast.makeText(MainActivity.this, anError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.logout:
                logoutDialog();
                return true;
            case R.id.my_cart:
                List<SearchModal> searchModals = ConstantMethod.getArrayList(this);
                if(searchModals.size()!=0){
                    startActivity(new Intent(this, CartActivity.class));
                }
                else {
                    Toast.makeText(this, "Cart is empty", Toast.LENGTH_SHORT).show();
                }
                return true;    
            case R.id.profile:
                startActivity(new Intent(this,ProfileActivity.class));
                return true;
            case R.id.myorders:
                startActivity(new Intent(this,MyOrdersActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void logoutDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Logout");
        builder.setMessage("Do you want to logout?");
        builder.setPositiveButton("Ok",(dialog,which)->{
            ConstantMethod.setStringPreference("login_status","logout",this);
            ConstantMethod.emptyCart(this);
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
            finish();
        });
        builder.setNegativeButton("Cancel",null);
        builder.show();
    }

    @Override
    public void onClick(int position) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}
