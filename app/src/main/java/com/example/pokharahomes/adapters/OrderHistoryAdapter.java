package com.example.pokharahomes.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pokharahomes.R;
import com.example.pokharahomes.models.OrderHistory;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.ViewHolder> {
    ArrayList<OrderHistory> orderHistoryArrayList;
    Context context;

    public OrderHistoryAdapter(ArrayList<OrderHistory> orderHistoryArrayList, Context context) {
        this.orderHistoryArrayList = orderHistoryArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.order_history_custom_design_for_recyclerview, parent, false);
        OrderHistoryAdapter.ViewHolder viewHolder = new OrderHistoryAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHistoryAdapter.ViewHolder holder, int position) {
        OrderHistory orderHistory = orderHistoryArrayList.get(position);

        holder.tvOrderedDatetime_orderHistory.setText(orderHistory.getOrderedDateTime());
        holder.tvFoodName_orderHistory.setText(orderHistory.getFoodName());
        holder.tvQuantity_orderHistory.setText(orderHistory.getQuantity());
        holder.tvRoomNo_orderHistory.setText(orderHistory.getRoomNumber());
        holder.tvTotalAmount_orderHistory.setText(orderHistory.getTotalAmount());

    }

    @Override
    public int getItemCount() {
        return orderHistoryArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvOrderedDatetime_orderHistory, tvFoodName_orderHistory, tvQuantity_orderHistory, tvRoomNo_orderHistory, tvTotalAmount_orderHistory;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderedDatetime_orderHistory = itemView.findViewById(R.id.tvOrderedDatetime_orderHistory);
            tvFoodName_orderHistory = itemView.findViewById(R.id.tvFoodName_orderHistory);
            tvQuantity_orderHistory = itemView.findViewById(R.id.tvQuantity_orderHistory);
            tvRoomNo_orderHistory = itemView.findViewById(R.id.tvRoomNo_orderHistory);
            tvTotalAmount_orderHistory = itemView.findViewById(R.id.tvTotalAmount_orderHistory);
        }
    }
}
