package com.example.pokharahomes.bookings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afdhal_fa.imageslider.ImageSlider;
import com.afdhal_fa.imageslider.model.SlideUIModel;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pokharahomes.MainActivity;
import com.example.pokharahomes.PaymentActivity;
import com.example.pokharahomes.R;
import com.example.pokharahomes.models.Food;
import com.example.pokharahomes.models.Room;
import com.example.pokharahomes.models.User;
import com.example.pokharahomes.utils.ErrorUtils;
import com.example.pokharahomes.utils.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FoodOrderActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Bundle bundle;
    private User user;
    private Food food;
    private List<SlideUIModel> sliderImages;
    private ArrayList<String> roomNumberArrayList;
    private ArrayAdapter<String> roomNumberArrayAdapter;
    private TextView _tvfoodquantity_ordering, tvfoodsname_ordering, tvfoodprice_ordering, tvincredients_ordering, tvestimatedtime_ordering, tvtotalamount_ordering;
    private Spinner roomsNumberDropdownSpinner_ordering;
    private Button btnOrderingFood;
    private int count = 0;

    private ImageSlider imageSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_ordering);

        _tvfoodquantity_ordering = (TextView) findViewById(R.id.tvfoodquantity_ordering);

        //to control spinner or dropdown menu for room number
        Spinner spinner1 = findViewById(R.id.roomsNumberDropdownSpinner_ordering);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.roomNumbers, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(this);

        bundle = getIntent().getExtras();
        food = (Food) bundle.getSerializable("food_details");

        user = SharedPrefManager.getInstance(FoodOrderActivity.this).getUser();

        imageSlider = findViewById(R.id.imageSliderFood);
        tvfoodsname_ordering = findViewById(R.id.tvfoodsname_ordering);
        tvfoodprice_ordering = findViewById(R.id.tvfoodprice_ordering);
        tvincredients_ordering = findViewById(R.id.tvincredients_ordering);
        tvestimatedtime_ordering = findViewById(R.id.tvestimatedtime_ordering);
        tvtotalamount_ordering = findViewById(R.id.tvtotalamount_ordering);

        roomsNumberDropdownSpinner_ordering = findViewById(R.id.roomsNumberDropdownSpinner_ordering);

        btnOrderingFood = findViewById(R.id.btnOrderingFood);
        btnOrderingFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orderFood();
            }
        });

        roomNumberArrayList = new ArrayList<>();

        sliderImages = new ArrayList<SlideUIModel>();
        sliderImages.add(new SlideUIModel(food.getImage1(), ""));
        sliderImages.add(new SlideUIModel(food.getImage2(), ""));

        imageSlider.setImageList(sliderImages);
        imageSlider.startSliding(3000);

        setData();
        getRoomNumbers();
    }

    //to control action of dropdown(spinner) selected data
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    //handling onclick increment and decrement icon of food quantity
    public void increment(View v) {
        count++;
        _tvfoodquantity_ordering.setText("" + count);
        int totalPrice = food.getPrice() * count;
        tvtotalamount_ordering.setText(String.valueOf(totalPrice));
    }

    public void decrement(View v) {
        if (count <= 0) count = 0;
        else count--;
        _tvfoodquantity_ordering.setText("" + count);
        int totalPrice = food.getPrice() * count;
        tvtotalamount_ordering.setText(String.valueOf(totalPrice));
    }
    private void setData() {
        tvfoodsname_ordering.setText(food.getFoodName());
        tvfoodprice_ordering.setText(String.valueOf(food.getPrice()));
        tvincredients_ordering.setText("Ingredients: " + food.getIngredients());
        tvestimatedtime_ordering.setText(String.valueOf(food.getEstimatedTime()) + " minutes");
        tvtotalamount_ordering.setText("0");
    }

    private void orderFood() {
        final String totalAmount;
        totalAmount = tvtotalamount_ordering.getText().toString();
        boolean error = false;

        if (Integer.parseInt(totalAmount) == 0) {
            Toast.makeText(this, "Please select the quantity.", Toast.LENGTH_SHORT).show();
            error = true;
        }

        if (!error) {
            String url = "https://sabingurung.com.np/pokharahomes/order_food.php";
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            final RequestQueue requestQueue = Volley.newRequestQueue(FoodOrderActivity.this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    if (response.trim().equals("success")) {
                        Toast.makeText(FoodOrderActivity.this, "Order Success!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(FoodOrderActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else if (response.trim().equals("error")) {
                        Toast.makeText(FoodOrderActivity.this, "Error while inserting", Toast.LENGTH_SHORT).show();
                    } else if (response.trim().equals("get_error")) {
                        Toast.makeText(FoodOrderActivity.this, "Error while fetching", Toast.LENGTH_SHORT).show();
                    } else if (response.trim().equals("check_error")) {
                        Toast.makeText(FoodOrderActivity.this, "You have not booked any room at this moment.", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    Toast.makeText(FoodOrderActivity.this, ErrorUtils.getVolleyError(error), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("userId", String.valueOf(user.getUserId()));
                    params.put("foodId", String.valueOf(food.getFoodId()));
                    params.put("totalAmount", tvtotalamount_ordering.getText().toString());
                    params.put("qty", String.valueOf(count));
                    params.put("roomNumber", roomsNumberDropdownSpinner_ordering.getSelectedItem().toString());
                    return params;
                }
            };
            requestQueue.add(stringRequest);
        }

    }

    private void getRoomNumbers() {
        String url = "https://sabingurung.com.np/pokharahomes/get_all_room_numbers.php";

        final RequestQueue requestQueue = Volley.newRequestQueue(FoodOrderActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("not_found")) {
                    Toast.makeText(FoodOrderActivity.this, "No any room numbers available.", Toast.LENGTH_SHORT).show();

                }
                else{
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        JSONObject jsonResponse;

                        for (int i = 0; i < jsonArray.length(); i++){
                            jsonResponse = jsonArray.getJSONObject(i);
                            String roomNo = jsonResponse.getString("number");

                            roomNumberArrayList.add(roomNo);
                            roomNumberArrayAdapter = new ArrayAdapter<>(FoodOrderActivity.this, android.R.layout.simple_spinner_item, roomNumberArrayList);
                            roomNumberArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            roomsNumberDropdownSpinner_ordering.setAdapter(roomNumberArrayAdapter);
                        }
                        roomsNumberDropdownSpinner_ordering.setSelection(0);
                        roomNumberArrayAdapter.notifyDataSetChanged();

                    }

                    catch (JSONException e) {
                        Toast.makeText(FoodOrderActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(FoodOrderActivity.this, ErrorUtils.getVolleyError(error), Toast.LENGTH_SHORT).show();
            }
        }) {

        };
        requestQueue.add(stringRequest);
    }
}