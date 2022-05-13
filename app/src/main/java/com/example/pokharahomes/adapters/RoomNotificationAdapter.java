package com.example.pokharahomes.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.pokharahomes.R;
import com.example.pokharahomes.models.RoomNotification;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RoomNotificationAdapter extends RecyclerView.Adapter<RoomNotificationAdapter.ViewHolder> {
    ArrayList<RoomNotification> roomNotificationArrayList;
    Context context;

    public RoomNotificationAdapter(ArrayList<RoomNotification> roomNotificationArrayList, Context context) {
        this.roomNotificationArrayList = roomNotificationArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public RoomNotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.rooms_custom_design_notification, parent, false);
        RoomNotificationAdapter.ViewHolder viewHolder = new RoomNotificationAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RoomNotificationAdapter.ViewHolder holder, int position) {
        RoomNotification roomNotification = roomNotificationArrayList.get(position);

        Glide.with(context).load(roomNotification.getImage()).into(holder.ivRoom_notification);
        holder.tvBookedRoomName_notification.setText("You have booked " + roomNotification.getRoomName() + ". Your room number is " + roomNotification.getRoomNumber() + ".");
        holder.tvRoomStatus_notification.setText(roomNotification.getStatus());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date past = sdf.parse(roomNotification.getBookedDate());
            Date now = new Date();
            long seconds= TimeUnit.MILLISECONDS.toSeconds(now.getTime() - past.getTime());
            long minutes=TimeUnit.MILLISECONDS.toMinutes(now.getTime() - past.getTime());
            long hours=TimeUnit.MILLISECONDS.toHours(now.getTime() - past.getTime());
            long days=TimeUnit.MILLISECONDS.toDays(now.getTime() - past.getTime());

            if(seconds<60)
            {
                holder.tvRoomTime_notification.setText(seconds + " " + "seconds ago");
            }
            else if(minutes<60)
            {
                holder.tvRoomTime_notification.setText(minutes + " " + "minutes ago");
            }
            else if(hours<24)
            {
                holder.tvRoomTime_notification.setText(hours + " " + "hours ago");
            }
            else
            {
                holder.tvRoomTime_notification.setText(days + " " + "days ago");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return roomNotificationArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvBookedRoomName_notification, tvRoomStatus_notification, tvRoomTime_notification;
        public ImageView ivRoom_notification;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBookedRoomName_notification = itemView.findViewById(R.id.tvBookedRoomName_notification);
            tvRoomStatus_notification = itemView.findViewById(R.id.tvRoomStatus_notification);
            tvRoomTime_notification = itemView.findViewById(R.id.tvRoomTime_notification);
            ivRoom_notification = itemView.findViewById(R.id.ivRoom_notification);

        }
    }
}
