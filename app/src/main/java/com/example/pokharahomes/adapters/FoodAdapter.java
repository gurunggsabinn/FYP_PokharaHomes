package com.example.pokharahomes.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.pokharahomes.R;
import com.example.pokharahomes.models.Food;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class  FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {
    ArrayList<Food> foodModelArrayList;
    Context context;

    public FoodAdapter(ArrayList<Food> foodModelArrayList, Context context) {
        this.foodModelArrayList = foodModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public FoodAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.foods_custom_design_for_fragment_foods, parent, false);
        FoodAdapter.ViewHolder viewHolder = new FoodAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FoodAdapter.ViewHolder holder, int position) {
        Food food = foodModelArrayList.get(position);

        Glide.with(context).load(food.getImage1()).into(holder.ivfoodsType);
        holder.tvfoodsname.setText(food.getFoodName());
        holder.tvfoodsPrice.setText(String.valueOf(food.getPrice()));
        holder.tvfoods_estimatetime.setText(food.getEstimatedTime() + " minutes");

    }

    @Override
    public int getItemCount() {
        return foodModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView ivfoodsType;
        public TextView tvfoodsname, tvfoodsPrice, tvfoods_estimatetime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivfoodsType = itemView.findViewById(R.id.ivfoodsType);
            tvfoodsname = itemView.findViewById(R.id.tvfoodsname);
            tvfoodsPrice = itemView.findViewById(R.id.tvfoodsPrice);
            tvfoods_estimatetime = itemView.findViewById(R.id.tvfoods_estimatetime);
        }
    }
}
