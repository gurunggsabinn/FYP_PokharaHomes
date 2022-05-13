package com.example.pokharahomes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pokharahomes.models.Room;
import com.example.pokharahomes.models.User;
import com.example.pokharahomes.utils.ErrorUtils;
import com.example.pokharahomes.utils.SharedPrefManager;
import com.khalti.checkout.helper.Config;
import com.khalti.checkout.helper.KhaltiCheckOut;
import com.khalti.checkout.helper.OnCheckOutListener;

import java.util.HashMap;
import java.util.Map;

public class PaymentActivity extends AppCompatActivity {

    private Bundle bundle;
    private Room room;
    private User user;

    private LinearLayout khaltiButton, cashOnDeskButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_payment);

        khaltiButton = findViewById(R.id.khaltiButton);
        cashOnDeskButton = findViewById(R.id.cashOnDeskButton);

        bundle = getIntent().getExtras();
        room = (Room) bundle.getSerializable("room_details");
        user = SharedPrefManager.getInstance(PaymentActivity.this).getUser();

        khaltiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proceed("Khalti");
            }
        });

        cashOnDeskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proceed("Cash On Desk");
            }
        });
    }

    private void proceed(String paymentMethod){
        AlertDialog.Builder builder = new AlertDialog.Builder(PaymentActivity.this);
        builder.setTitle("Note");
        builder.setMessage("Once the payment is done, it will not be refunded. Are you sure you want to proceed?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (paymentMethod.equals("Khalti")) {
                    openKhalti();
                }
                else {
                    addBookings("Cash On Desk");
                }
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void openKhalti() {
        long amount = Long.parseLong(bundle.getString("total_amount"));
        openKhaltiApp(amount);
    }

    private void openKhaltiApp(long amount) {
        amount *= 100;
        Config.Builder builder = new Config.Builder("test_public_key_d116bb904072477d9bcad17d18afbf6e", String.valueOf(room.getRoomId()), room.getRoomName(),
                amount, new OnCheckOutListener() {

            @Override
            public void onSuccess(@NonNull Map<String, Object> data) {
                Log.i("success", data.toString());
                addBookings("Khalti");

            }

            @Override
            public void onError(@NonNull String action, @NonNull Map<String, String> errorMap) {
                Log.e("hello", errorMap.toString());
                Toast.makeText(PaymentActivity.this, "Khalti Error", Toast.LENGTH_SHORT).show();

            }

        });

        Config config = builder.build();

        KhaltiCheckOut khaltiCheckOut = new KhaltiCheckOut(PaymentActivity.this, config);
        khaltiCheckOut.show();
    }

    private void addBookings(String paymentMethod) {
        String url = "https://sabingurung.com.np/pokharahomes/book_room.php";
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        final RequestQueue requestQueue = Volley.newRequestQueue(PaymentActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                if (response.trim().equals("success")) {
                    Toast.makeText(PaymentActivity.this, "Successfully Booked! Thank you for choosing us.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(PaymentActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else if (response.trim().equals("error")) {
                    Toast.makeText(PaymentActivity.this, "Error while inserting", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                Toast.makeText(PaymentActivity.this, ErrorUtils.getVolleyError(error), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("roomId", String.valueOf(room.getRoomId()));
                params.put("userId", String.valueOf(user.getUserId()));
                params.put("checkedInDate", bundle.getString("checked_in_date"));
                params.put("checkedOutDate", bundle.getString("checked_out_date"));
                params.put("paymentMethod", paymentMethod);
                params.put("totalAmount", bundle.getString("total_amount"));
                params.put("roomNumber", bundle.getString("room_number"));
                return params;
            }
        };
        requestQueue.add(stringRequest);

    }

}