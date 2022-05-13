package com.example.pokharahomes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pokharahomes.adapters.BookingHistoryAdapter;
import com.example.pokharahomes.models.BookingHistory;
import com.example.pokharahomes.models.User;
import com.example.pokharahomes.utils.ErrorUtils;
import com.example.pokharahomes.utils.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BookingHistoryActivity extends AppCompatActivity {
    private RecyclerView recyclerViewBookingHistory;
    private ArrayList<BookingHistory> bookingHistoryArrayList;
    private BookingHistoryAdapter bookingHistoryAdapter;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_history);

        user = SharedPrefManager.getInstance(this).getUser();

        recyclerViewBookingHistory = findViewById(R.id.recyclerViewBookingHistory);
        bookingHistoryArrayList = new ArrayList<>();
        bookingHistoryAdapter = new BookingHistoryAdapter(bookingHistoryArrayList, this);

        getBookingHistory();

    }


    private void getBookingHistory() {
        String url = "https://sabingurung.com.np/pokharahomes/get_room_history.php";

        final RequestQueue requestQueue = Volley.newRequestQueue(BookingHistoryActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("not_found")) {
                    Toast.makeText(BookingHistoryActivity.this, "You have not booked any room yet.", Toast.LENGTH_SHORT).show();
                }
                else{
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        JSONObject jsonResponse;

                        for (int i = 0; i < jsonArray.length(); i++){
                            jsonResponse = jsonArray.getJSONObject(i);
                            String roomName = jsonResponse.getString("room_name");
                            String bookedDateTime = jsonResponse.getString("booked_date");
                            String checkedInDate = jsonResponse.getString("checked_in_date");
                            String checkedOutDate = jsonResponse.getString("checked_out_date");
                            String paymentMethod = jsonResponse.getString("payment_method");
                            String totalAmount = String.valueOf(jsonResponse.getInt("total_amount"));
                            String roomNumber = jsonResponse.getString("room_number");

                            BookingHistory bookingHistory = new BookingHistory(bookedDateTime, roomNumber, checkedInDate, checkedOutDate, roomName, paymentMethod, totalAmount);
                            bookingHistoryArrayList.add(bookingHistory);
                        }

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(BookingHistoryActivity.this, LinearLayoutManager.VERTICAL, false);
                        recyclerViewBookingHistory.setLayoutManager(linearLayoutManager);
                        recyclerViewBookingHistory.setAdapter(bookingHistoryAdapter);
                        bookingHistoryAdapter.notifyDataSetChanged();

                    }

                    catch (JSONException e) {
                        Toast.makeText(BookingHistoryActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(BookingHistoryActivity.this, ErrorUtils.getVolleyError(error), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userId", String.valueOf(user.getUserId()));
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

}