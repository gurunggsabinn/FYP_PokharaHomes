package com.example.pokharahomes;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pokharahomes.adapters.FoodNotificationAdapter;
import com.example.pokharahomes.adapters.OrderHistoryAdapter;
import com.example.pokharahomes.adapters.RoomNotificationAdapter;
import com.example.pokharahomes.models.BookingHistory;
import com.example.pokharahomes.models.FoodNotification;
import com.example.pokharahomes.models.OrderHistory;
import com.example.pokharahomes.models.RoomNotification;
import com.example.pokharahomes.models.User;
import com.example.pokharahomes.utils.ErrorUtils;
import com.example.pokharahomes.utils.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NotificationActivity extends AppCompatActivity {

    Toolbar toolbar_notification;

    private RecyclerView recyclerViewRoomNotification;
    private ArrayList<RoomNotification> roomNotificationArrayList;
    private ArrayList<FoodNotification> foodNotificationArrayList;
    private RoomNotificationAdapter roomNotificationAdapter;
    private FoodNotificationAdapter foodNotificationAdapter;
    private ConcatAdapter concatAdapter;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        toolbar_notification = (Toolbar)findViewById(R.id.toolbar_notification);
        setSupportActionBar(toolbar_notification);

        user = SharedPrefManager.getInstance(this).getUser();

        recyclerViewRoomNotification = findViewById(R.id.recyclerViewNotification);
        roomNotificationArrayList = new ArrayList<>();
        roomNotificationAdapter = new RoomNotificationAdapter(roomNotificationArrayList, this);
        foodNotificationArrayList = new ArrayList<>();
        foodNotificationAdapter = new FoodNotificationAdapter(foodNotificationArrayList, this);

        concatAdapter = new ConcatAdapter();

        getRoomNotifications();
        getFoodNotifications();
    }

    private void getRoomNotifications() {
        String url = "https://sabingurung.com.np/pokharahomes/get_room_notifications.php";

        final RequestQueue requestQueue = Volley.newRequestQueue(NotificationActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("not_found")) {
                    Toast.makeText(NotificationActivity.this, "Room notifications not found.", Toast.LENGTH_SHORT).show();
                }
                else{
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        JSONObject jsonResponse;

                        for (int i = 0; i < jsonArray.length(); i++){
                            jsonResponse = jsonArray.getJSONObject(i);
                            String roomName = jsonResponse.getString("room_name");
                            String bookedDateTime = jsonResponse.getString("booked_date");
                            String image = jsonResponse.getString("image_1");
                            String roomStatus = jsonResponse.getString("room_status");
                            String roomNumber = jsonResponse.getString("room_number");

                            RoomNotification roomNotification = new RoomNotification(roomName, roomNumber, image, roomStatus, bookedDateTime);
                            roomNotificationArrayList.add(roomNotification);
                        }

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(NotificationActivity.this, LinearLayoutManager.VERTICAL, false);
                        recyclerViewRoomNotification.setLayoutManager(linearLayoutManager);
                        concatAdapter.addAdapter(roomNotificationAdapter);
                        recyclerViewRoomNotification.setAdapter(concatAdapter);
                        concatAdapter.notifyDataSetChanged();

                    }

                    catch (JSONException e) {
                        Toast.makeText(NotificationActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(NotificationActivity.this, ErrorUtils.getVolleyError(error), Toast.LENGTH_SHORT).show();
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


    private void getFoodNotifications() {
        String url = "https://sabingurung.com.np/pokharahomes/get_order_notifications.php";

        final RequestQueue requestQueue = Volley.newRequestQueue(NotificationActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("not_found")) {
                    Toast.makeText(NotificationActivity.this, "Food notifications not found.", Toast.LENGTH_SHORT).show();
                }
                else{
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        JSONObject jsonResponse;

                        for (int i = 0; i < jsonArray.length(); i++){
                            jsonResponse = jsonArray.getJSONObject(i);
                            String foodName = jsonResponse.getString("food_name");
                            int estimatedTime = jsonResponse.getInt("estimated_time");
                            String quantity = String.valueOf(jsonResponse.getInt("quantity"));
                            String orderedDateTime = jsonResponse.getString("ordered_date_time");
                            String image = jsonResponse.getString("image_1");
                            String roomNumber = jsonResponse.getString("room_number");

                            FoodNotification foodNotification = new FoodNotification(foodName, image, orderedDateTime, quantity, roomNumber, estimatedTime);
                            foodNotificationArrayList.add(foodNotification);
                        }

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(NotificationActivity.this, LinearLayoutManager.VERTICAL, false);
                        recyclerViewRoomNotification.setLayoutManager(linearLayoutManager);
                        concatAdapter.addAdapter(foodNotificationAdapter);
                        recyclerViewRoomNotification.setAdapter(concatAdapter);
                        concatAdapter.notifyDataSetChanged();

                    }

                    catch (JSONException e) {
                        Toast.makeText(NotificationActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(NotificationActivity.this, ErrorUtils.getVolleyError(error), Toast.LENGTH_SHORT).show();
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
