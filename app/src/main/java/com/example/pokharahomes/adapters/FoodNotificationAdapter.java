package com.example.pokharahomes.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.pokharahomes.R;
import com.example.pokharahomes.models.FoodNotification;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FoodNotificationAdapter extends RecyclerView.Adapter<FoodNotificationAdapter.ViewHolder> {
    ArrayList<FoodNotification> foodNotificationArrayList;
    Context context;

    public FoodNotificationAdapter(ArrayList<FoodNotification> foodNotificationArrayList, Context context) {
        this.foodNotificationArrayList = foodNotificationArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public FoodNotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.foods_custom_design_notfication, parent, false);
        FoodNotificationAdapter.ViewHolder viewHolder = new FoodNotificationAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FoodNotificationAdapter.ViewHolder holder, int position) {
        FoodNotification foodNotification = foodNotificationArrayList.get(position);

        Glide.with(context).load(foodNotification.getImage()).into(holder.ivFoods_notification);
        holder.tvOrderfood_notification.setText("You have ordered " + foodNotification.getQuantity() + " " + foodNotification.getFoodName() + " for the room number " + foodNotification.getRoomNumber());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date past = sdf.parse(foodNotification.getOrderedDateTime());
            Date now = new Date();

            if (TimeUnit.MILLISECONDS.toMinutes(now.getTime() - past.getTime()) <= foodNotification.getEstimatedTime()) {
                holder.tvFoodStatus_notification.setText("Preparing");
            }
            else {
                holder.tvFoodStatus_notification.setText("Prepared");
            }

            long seconds= TimeUnit.MILLISECONDS.toSeconds(now.getTime() - past.getTime());
            long minutes=TimeUnit.MILLISECONDS.toMinutes(now.getTime() - past.getTime());
            long hours=TimeUnit.MILLISECONDS.toHours(now.getTime() - past.getTime());
            long days=TimeUnit.MILLISECONDS.toDays(now.getTime() - past.getTime());

            if(seconds<60)
            {
                holder.tvFoodTime_notification.setText(seconds + " " + "seconds ago");
            }
            else if(minutes<60)
            {
                holder.tvFoodTime_notification.setText(minutes + " " + "minutes ago");
            }
            else if(hours<24)
            {
                holder.tvFoodTime_notification.setText(hours + " " + "hours ago");
            }
            else
            {
                holder.tvFoodTime_notification.setText(days + " " + "days ago");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return foodNotificationArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvOrderfood_notification, tvFoodStatus_notification, tvFoodTime_notification;
        public ImageView ivFoods_notification;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderfood_notification = itemView.findViewById(R.id.tvOrderfood_notification);
            tvFoodStatus_notification = itemView.findViewById(R.id.tvFoodStatus_notification);
            tvFoodTime_notification = itemView.findViewById(R.id.tvFoodTime_notification);
            ivFoods_notification = itemView.findViewById(R.id.ivFoods_notification);

        }
    }
}
