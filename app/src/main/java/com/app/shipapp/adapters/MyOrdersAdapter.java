package com.app.shipapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.app.shipapp.R;
import com.app.shipapp.app_utils.ConstantMethod;
import com.app.shipapp.modals.Message;
import com.app.shipapp.modals.MyOrders;

import java.util.List;

public class MyOrdersAdapter extends RecyclerView.Adapter<MyOrdersAdapter.MyOrderHolder> {
    private MyOrders myOrders;
    private LayoutInflater layoutInflater;

    public MyOrdersAdapter(MyOrders myOrders, Context context){
        this.myOrders = myOrders;
        layoutInflater = LayoutInflater.from(context);

    }
    @NonNull
    @Override
    public MyOrderHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = layoutInflater.inflate(R.layout.adapter_my_orders,null);
        return new MyOrderHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyOrderHolder myOrderHolder, int position) {
        List<Message> messages = myOrders.getMessage();
        for(int i=0;i<messages.size();i++){
            myOrderHolder.orderIdTxt.setText(String.valueOf(messages.get(position).getId()));
            myOrderHolder.text1.setText(messages.get(position).getBillingAddress());
            myOrderHolder.text2.setText(messages.get(position).getBillingCity());
            myOrderHolder.dateTxt.setText(ConstantMethod.currentDate());
        }
    }

    @Override
    public int getItemCount() {
        return myOrders.getMessage().size();
    }

    class MyOrderHolder extends RecyclerView.ViewHolder {
        TextView orderIdTxt,text1,text2,dateTxt;
        public MyOrderHolder(@NonNull View itemView) {
            super(itemView);
            orderIdTxt = itemView.findViewById(R.id.order_id_txt);
            text1 = itemView.findViewById(R.id.name_txt);
            text2 = itemView.findViewById(R.id.title_txt);
            dateTxt = itemView.findViewById(R.id.date_txt);
        }
    }
}
