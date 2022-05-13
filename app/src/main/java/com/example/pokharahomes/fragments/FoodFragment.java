package com.example.pokharahomes.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pokharahomes.R;
import com.example.pokharahomes.adapters.FoodAdapter;
import com.example.pokharahomes.bookings.FoodOrderActivity;
import com.example.pokharahomes.bookings.RoomBookingActivity;
import com.example.pokharahomes.models.Food;
import com.example.pokharahomes.models.Room;
import com.example.pokharahomes.utils.ErrorUtils;
import com.example.pokharahomes.utils.ItemClickSupport;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FoodFragment extends Fragment {

    private RecyclerView recyclerViewFood;
    private ArrayList<Food> foodArrayList;
    private FoodAdapter foodAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_foods, container, false);

        recyclerViewFood = v.findViewById(R.id.recyclerViewFoods);

        foodArrayList = new ArrayList<>();

        foodAdapter = new FoodAdapter(foodArrayList, getContext());

        getFoods();
        return v;

    }

    private void getFoods() {
        String url = "https://sabingurung.com.np/pokharahomes/get_foods.php";

        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("not_found")) {
                    Toast.makeText(getContext(), "No any foods found!", Toast.LENGTH_SHORT).show();
                }
                else{
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        JSONObject jsonResponse;

                        for (int i = 0; i < jsonArray.length(); i++){
                            jsonResponse = jsonArray.getJSONObject(i);
                            int foodId = jsonResponse.getInt("id");
                            int price = jsonResponse.getInt("price");
                            int estimatedTime = jsonResponse.getInt("estimated_time");
                            String foodName = jsonResponse.getString("food_name");
                            String ingredients = jsonResponse.getString("ingredients");
                            String image1 = jsonResponse.getString("image_1");
                            String image2 = jsonResponse.getString("image_2");

                            Food food = new Food(foodId, price, estimatedTime, foodName, ingredients, image1, image2);
                            foodArrayList.add(food);
                        }

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        recyclerViewFood.setLayoutManager(linearLayoutManager);
                        recyclerViewFood.setAdapter(foodAdapter);
                        foodAdapter.notifyDataSetChanged();

                        ItemClickSupport.addTo(recyclerViewFood).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                            @Override
                            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                                Food food = foodArrayList.get(position);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("food_details", food);
                                Intent intent = new Intent(getContext(), FoodOrderActivity.class);
                                intent.putExtras(bundle);
                                startActivity(intent);

                            }
                        });


                    }

                    catch (JSONException e) {
                        Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
                    }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), ErrorUtils.getVolleyError(error), Toast.LENGTH_SHORT).show();
            }
        }) {

        };
        requestQueue.add(stringRequest);
    }
}
