package com.example.pokharahomes.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.pokharahomes.R;
import com.example.pokharahomes.models.Room;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewHolder> {
    ArrayList<Room> roomModelArrayList;
    Context context;

    public RoomAdapter(ArrayList<Room> roomModelArrayList, Context context) {
        this.roomModelArrayList = roomModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public RoomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.rooms_custom_design_for_fragment_rooms, parent, false);
        RoomAdapter.ViewHolder viewHolder = new RoomAdapter.ViewHolder(view);
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Room room = roomModelArrayList.get(position);

        Glide.with(context).load(room.getImage1()).into(holder.ivroomsType);
        holder.tvroomsname.setText(room.getRoomName());
        holder.tvrooms_shortdetail.setText(room.getFeatures());
        holder.tvrooms_price.setText(String.valueOf(room.getPrice()));
        holder.tvrooms_offer.setText(room.getIncluded());
    }

    @Override
    public int getItemCount() {
       return roomModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView ivroomsType;
        public TextView tvroomsname, tvrooms_shortdetail, tvrooms_price, tvrooms_offer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivroomsType = itemView.findViewById(R.id.ivroomsType);
            tvroomsname = itemView.findViewById(R.id.tvroomsname);
            tvrooms_shortdetail = itemView.findViewById(R.id.tvrooms_shortdetail);
            tvrooms_price = itemView.findViewById(R.id.tvrooms_price);
            tvrooms_offer = itemView.findViewById(R.id.tvrooms_offer);
        }
    }
}
