package com.example.pokharahomes.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pokharahomes.R;
import com.example.pokharahomes.models.BookingHistory;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BookingHistoryAdapter extends RecyclerView.Adapter<BookingHistoryAdapter.ViewHolder> {
    ArrayList<BookingHistory> bookingHistoryArrayList;
    Context context;

    public BookingHistoryAdapter(ArrayList<BookingHistory> bookingHistoryArrayList, Context context) {
        this.bookingHistoryArrayList = bookingHistoryArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public BookingHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.booking_history_custom_design_for_recyclerview, parent, false);
        BookingHistoryAdapter.ViewHolder viewHolder = new BookingHistoryAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BookingHistoryAdapter.ViewHolder holder, int position) {
        BookingHistory bookingHistory = bookingHistoryArrayList.get(position);

        holder.tvBookDate_bookingHistory.setText(bookingHistory.getBookedDateTime());
        holder.tvRoomNo_bookingHistory.setText(bookingHistory.getRoomNumber());
        holder.tvCheckinDate_bookingHistory.setText(bookingHistory.getCheckedInDate());
        holder.tvCheckoutdate_bookingHistory.setText(bookingHistory.getCheckedOutDate());
        holder.tvpackagenaem_bookingHistory.setText(bookingHistory.getPackageName());
        holder.tvpaymentMethod_bookingHistory.setText(bookingHistory.getPaymentMethod());
        holder.tvtotalamount_bookingHistory.setText(bookingHistory.getTotalAmount());

    }

    @Override
    public int getItemCount() {
        return bookingHistoryArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvBookDate_bookingHistory, tvRoomNo_bookingHistory, tvCheckinDate_bookingHistory, tvCheckoutdate_bookingHistory, tvpackagenaem_bookingHistory, tvpaymentMethod_bookingHistory, tvtotalamount_bookingHistory;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBookDate_bookingHistory = itemView.findViewById(R.id.tvBookDate_bookingHistory);
            tvRoomNo_bookingHistory = itemView.findViewById(R.id.tvRoomNo_bookingHistory);
            tvCheckinDate_bookingHistory = itemView.findViewById(R.id.tvCheckinDate_bookingHistory);
            tvCheckoutdate_bookingHistory = itemView.findViewById(R.id.tvCheckoutdate_bookingHistory);
            tvpackagenaem_bookingHistory = itemView.findViewById(R.id.tvpackagenaem_bookingHistory);
            tvpaymentMethod_bookingHistory = itemView.findViewById(R.id.tvpaymentMethod_bookingHistory);
            tvtotalamount_bookingHistory = itemView.findViewById(R.id.tvtotalamount_bookingHistory);
        }
    }
}
