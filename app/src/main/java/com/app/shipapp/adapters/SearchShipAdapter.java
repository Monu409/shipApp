package com.app.shipapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import com.app.shipapp.R;
import com.app.shipapp.activities.SingleShipActivity;
import com.app.shipapp.app_utils.ConstantMethod;
import com.app.shipapp.modals.SearchModal;

import java.util.ArrayList;
import java.util.List;

public class SearchShipAdapter extends RecyclerView.Adapter<SearchShipAdapter.SrchShpHolder> {
    private List<SearchModal> searchModals;
    private LayoutInflater layoutInflater;
    private Context context;
    private String activityName;
    private CrossClick crossClick;

    public SearchShipAdapter(Context context, List<SearchModal> searchModals,String activityName,CrossClick crossClick){
        this.searchModals = searchModals;
        this.context = context;
        this.activityName = activityName;
        this.crossClick = crossClick;
        layoutInflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public SrchShpHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.adapter_search_ship,null);
        return new SrchShpHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SrchShpHolder srchShpHolder, int i) {
        if(activityName.equals("cart")){
            srchShpHolder.deleteImg.setVisibility(View.VISIBLE);
            srchShpHolder.selectShip.setVisibility(View.INVISIBLE);
            srchShpHolder.deleteImg.setOnClickListener(v->{
                crossClick.onClick(i);
                notifyDataSetChanged();
            });
        }
        else if(activityName.equals("search")){
            srchShpHolder.deleteImg.setVisibility(View.INVISIBLE);
            srchShpHolder.selectShip.setVisibility(View.VISIBLE);
        }
        srchShpHolder.shipName.setText(searchModals.get(i).getShipName());
        srchShpHolder.cost.setText(searchModals.get(i).getCost());
        srchShpHolder.selectShip.setOnCheckedChangeListener((buttonView,isChecked)->{
            if(isChecked){
                List<SearchModal> searchModalsPref = ConstantMethod.getArrayList(context);
                if(searchModalsPref==null){
                    searchModalsPref = new ArrayList<>();
                    searchModalsPref.add(searchModals.get(i));
                    ConstantMethod.saveArrayList(context, searchModalsPref);
                }else {
                    searchModalsPref.add(searchModals.get(i));
                    ConstantMethod.saveArrayList(context, searchModalsPref);
                }
            }
            else {
                List<SearchModal> searchModalsPref = ConstantMethod.getArrayList(context);
                for(int i1 = 0 ; i1 < searchModalsPref.size() ; i1++){
                    if(searchModals.get(i).getShipName().equalsIgnoreCase(searchModalsPref.get(i1).getShipName())){
                        searchModalsPref.remove(i1);
                        ConstantMethod.saveArrayList(context,searchModalsPref);
                    }
                }
            }
        });
        srchShpHolder.fullView.setOnClickListener(v->{
            Intent intent  = new Intent(context,SingleShipActivity.class);
            intent.putExtra("single_ship",searchModals.get(i));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return searchModals.size();
    }

    public static class SrchShpHolder extends RecyclerView.ViewHolder {
        public TextView shipName, cost;
        public CardView fullView;
        public CheckBox selectShip;
        public ImageView deleteImg;
        public SrchShpHolder(@NonNull View itemView) {
            super(itemView);
            shipName = itemView.findViewById(R.id.name_txt);
            cost = itemView.findViewById(R.id.title_txt);
            fullView = itemView.findViewById(R.id.full_view);
            selectShip = itemView.findViewById(R.id.select_box);
            deleteImg = itemView.findViewById(R.id.delete_ship);

        }
    }

    public interface CrossClick{
        void onClick(int position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
