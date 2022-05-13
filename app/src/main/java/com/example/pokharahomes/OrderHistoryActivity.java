package com.example.pokharahomes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pokharahomes.adapters.FoodAdapter;
import com.example.pokharahomes.adapters.OrderHistoryAdapter;
import com.example.pokharahomes.bookings.FoodOrderActivity;
import com.example.pokharahomes.models.Food;
import com.example.pokharahomes.models.OrderHistory;
import com.example.pokharahomes.models.User;
import com.example.pokharahomes.utils.ErrorUtils;
import com.example.pokharahomes.utils.ItemClickSupport;
import com.example.pokharahomes.utils.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrderHistoryActivity extends AppCompatActivity {
    Toolbar _toolbar_orderhistory;

    private RecyclerView recyclerViewFoodOrderHistory;
    private ArrayList<OrderHistory> orderHistoryArrayList;
    private OrderHistoryAdapter orderHistoryAdapter;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        _toolbar_orderhistory = (Toolbar)findViewById(R.id.toolbar_orderhistory);
        setSupportActionBar(_toolbar_orderhistory);

        user = SharedPrefManager.getInstance(this).getUser();

        recyclerViewFoodOrderHistory = findViewById(R.id.recyclerViewOrderHistory);
        orderHistoryArrayList = new ArrayList<>();
        orderHistoryAdapter = new OrderHistoryAdapter(orderHistoryArrayList, this);

        getFoods();

    }

    private void getFoods() {
        String url = "https://sabingurung.com.np/pokharahomes/get_order_history.php";

        final RequestQueue requestQueue = Volley.newRequestQueue(OrderHistoryActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("not_found")) {
                    Toast.makeText(OrderHistoryActivity.this, "You have not ordered any food yet.", Toast.LENGTH_SHORT).show();
                }
                else{
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        JSONObject jsonResponse;

                        for (int i = 0; i < jsonArray.length(); i++){
                            jsonResponse = jsonArray.getJSONObject(i);
                            String foodName = jsonResponse.getString("food_name");
                            String orderedDateTime = jsonResponse.getString("ordered_date_time");
                            String qty = String.valueOf(jsonResponse.getInt("quantity"));
                            String totalAmount = String.valueOf(jsonResponse.getInt("total_amount"));
                            String roomNumber = jsonResponse.getString("room_number");

                            OrderHistory orderHistory = new OrderHistory(orderedDateTime, foodName, qty, roomNumber, totalAmount);
                            orderHistoryArrayList.add(orderHistory);
                        }

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(OrderHistoryActivity.this, LinearLayoutManager.VERTICAL, false);
                        recyclerViewFoodOrderHistory.setLayoutManager(linearLayoutManager);
                        recyclerViewFoodOrderHistory.setAdapter(orderHistoryAdapter);
                        orderHistoryAdapter.notifyDataSetChanged();

                    }

                    catch (JSONException e) {
                        Toast.makeText(OrderHistoryActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(OrderHistoryActivity.this, ErrorUtils.getVolleyError(error), Toast.LENGTH_SHORT).show();
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