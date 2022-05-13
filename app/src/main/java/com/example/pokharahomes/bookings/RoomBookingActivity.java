package com.example.pokharahomes.bookings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
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
import com.example.pokharahomes.LoginActivity;
import com.example.pokharahomes.PaymentActivity;
import com.example.pokharahomes.R;
import com.example.pokharahomes.models.Room;
import com.example.pokharahomes.models.RoomNumber;
import com.example.pokharahomes.models.User;
import com.example.pokharahomes.utils.ErrorUtils;
import com.example.pokharahomes.utils.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoomBookingActivity extends AppCompatActivity {

    private Bundle bundle;
    private Room room;
    private User user;
    private List<SlideUIModel> sliderImages;
    private ArrayList<String> roomNumberArrayList;
    private ArrayAdapter<String> roomNumberArrayAdapter;

    ImageSlider slider;

    private TextView tvroomsname_booking, tvrooms_offer, tvrooms_maxpersonallowed, tvrooms_price_booking, tvrooms_details_booking, etCheckin, etCheckOut, tvtotalroomprice_booking;
    private Spinner roomsNumberDropdownSpinner;
    private Button btnBookingRoom;

    Calendar mCurrentDate;
    int day, month, year;

    Toolbar _toolbar_bookinghistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_room);

        _toolbar_bookinghistory = (Toolbar)findViewById(R.id.toolbar_bookinghistory);
        setSupportActionBar(_toolbar_bookinghistory);

        bundle = getIntent().getExtras();
        room = (Room) bundle.getSerializable("room_details");

        user = SharedPrefManager.getInstance(RoomBookingActivity.this).getUser();

        slider = findViewById(R.id.imageSlider);
        tvroomsname_booking = findViewById(R.id.tvroomsname_booking);
        tvrooms_offer = findViewById(R.id.tvrooms_offer);
        tvrooms_price_booking = findViewById(R.id.tvrooms_price_booking);
        tvrooms_details_booking = findViewById(R.id.tvrooms_details_booking);
        etCheckin = findViewById(R.id.etCheckin);
        etCheckOut = findViewById(R.id.etCheckOut);
        tvtotalroomprice_booking = findViewById(R.id.tvtotalroomprice_booking);
        tvrooms_maxpersonallowed = findViewById(R.id.tvrooms_maxpersonallowed);

        roomsNumberDropdownSpinner = findViewById(R.id.roomsNumberDropdownSpinner);

        btnBookingRoom = findViewById(R.id.btnBookingRoom);
        btnBookingRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPaymentActivity();
            }
        });

        mCurrentDate = Calendar.getInstance();
        day = mCurrentDate.get(Calendar.DAY_OF_MONTH);
        month = mCurrentDate.get(Calendar.MONTH);
        year = mCurrentDate.get(Calendar.YEAR);

        month = month + 1;

        etCheckin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(RoomBookingActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month+1;
                        etCheckin.setText(dayOfMonth + "-" + month + "-" + year);

                    }
                },year, month - 1, day);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });

        etCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!etCheckin.getText().toString().isEmpty()) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(RoomBookingActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            month = month+1;
                            etCheckOut.setText(dayOfMonth + "-" + month + "-" + year);
                            getDaysFromTwoDates(etCheckin.getText().toString(), etCheckOut.getText().toString());

                        }
                    },year, month - 1, day);

                    long longDate = getTime(etCheckin.getText().toString());
                    datePickerDialog.getDatePicker().setMinDate(longDate + (1000 * 60 * 60 * 24));
                    datePickerDialog.show();
                }
                else {
                    Toast.makeText(RoomBookingActivity.this, "Please select check in date first.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        roomNumberArrayList = new ArrayList<>();

        sliderImages = new ArrayList<SlideUIModel>();
        sliderImages.add(new SlideUIModel(room.getImage1(), ""));
        sliderImages.add(new SlideUIModel(room.getImage2(), ""));

        slider.setImageList(sliderImages);
        slider.startSliding(3000);

        setData();
        getRoomNumbers();

    }

    private void setData() {
        tvroomsname_booking.setText(room.getRoomName());
        tvrooms_offer.setText(room.getIncluded());
        tvrooms_price_booking.setText(String.valueOf(room.getPrice()));
        tvrooms_details_booking.setText(room.getFeatures());
        tvrooms_maxpersonallowed.setText(String.valueOf(room.getMaxPersonAllowed()));
    }

    private void getRoomNumbers() {
        String url = "https://sabingurung.com.np/pokharahomes/get_room_numbers.php";

        final RequestQueue requestQueue = Volley.newRequestQueue(RoomBookingActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("not_found")) {
                    Toast.makeText(RoomBookingActivity.this, "No any room numbers available.", Toast.LENGTH_SHORT).show();

                }
                else{
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        JSONObject jsonResponse;

                        for (int i = 0; i < jsonArray.length(); i++){
                            jsonResponse = jsonArray.getJSONObject(i);
                            String roomNo = jsonResponse.getString("number");

                            roomNumberArrayList.add(roomNo);
                            roomNumberArrayAdapter = new ArrayAdapter<>(RoomBookingActivity.this, android.R.layout.simple_spinner_item, roomNumberArrayList);
                            roomNumberArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            roomsNumberDropdownSpinner.setAdapter(roomNumberArrayAdapter);
                        }
                        roomsNumberDropdownSpinner.setSelection(0);
                        roomNumberArrayAdapter.notifyDataSetChanged();

                    }

                    catch (JSONException e) {
                        Toast.makeText(RoomBookingActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RoomBookingActivity.this, ErrorUtils.getVolleyError(error), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("roomId", String.valueOf(room.getRoomId()));
                return params;
            }

        };
        requestQueue.add(stringRequest);
    }

    private void openPaymentActivity() {
        checkRoom();
    }

    private void checkRoom() {
        final String checkedInDate, checkedOutDate, roomNumber;
        boolean error = false;
        String url = "https://sabingurung.com.np/pokharahomes/check_available_room.php";
        checkedInDate = etCheckin.getText().toString();
        checkedOutDate = etCheckOut.getText().toString();
        roomNumber = roomsNumberDropdownSpinner.getSelectedItem().toString().trim();

        if (checkedInDate.isEmpty() || checkedOutDate.isEmpty()) {
            Toast.makeText(this, "Please select both check-in and check-out date.", Toast.LENGTH_SHORT).show();
            error = true;
        }

        if (!error) {
            final RequestQueue requestQueue = Volley.newRequestQueue(RoomBookingActivity.this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.trim().equals("room_available")) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("room_details", room);
                        bundle.putString("room_number", roomNumber);
                        bundle.putString("checked_in_date", checkedInDate);
                        bundle.putString("checked_out_date", checkedOutDate);
                        bundle.putString("total_amount", tvtotalroomprice_booking.getText().toString());
                        Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(RoomBookingActivity.this, "This room has already been booked. Please choose another room number.", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(RoomBookingActivity.this, ErrorUtils.getVolleyError(error), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("roomId", String.valueOf(room.getRoomId()));
                    params.put("checkedInDate", checkedInDate);
                    params.put("checkedOutDate", checkedOutDate);
                    params.put("roomNumber", roomNumber);
                    return params;
                }

            };
            requestQueue.add(stringRequest);
        }
    }

    private long getTime(String dateToConvert) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date;
        long longDate = 0;
        try {
            date = (Date)formatter.parse(dateToConvert);
            longDate = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return longDate;
    }

    private void getDaysFromTwoDates(String checkInDate, String checkOutDate) {
        long checkedInDate = getTime(checkInDate);
        long checkedOutDate = getTime(checkOutDate);

        long difference = Math.abs(checkedInDate - checkedOutDate);
        long days = difference / (24 * 60 * 60 * 1000);

        long totalPrice = days * room.getPrice();
        tvtotalroomprice_booking.setText(String.valueOf(totalPrice));
    }

}