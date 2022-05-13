package com.example.pokharahomes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pokharahomes.models.User;
import com.example.pokharahomes.utils.ErrorUtils;
import com.example.pokharahomes.utils.SharedPrefManager;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class EditProfileActivity extends AppCompatActivity {
    private Toolbar _toolbar_edituserprofile;
    private User user;
    private EditText etFullname_editprofile, etGender_editprofile, etDOB_editprofile, Address_editprofile, etPhone_editprofile, etEmail_editprofile, etPassword_editprofile;
    private Button btnSaveUserDetails;
    private int oldUserId;
    private String oldEmail;

    int day, month, year;

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{6,}" +               //at least 6 characters
                    "$");

    private static final Pattern PHONE_PATTERN = Pattern.compile("^[+]?[0-9]{10,13}$");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);

        _toolbar_edituserprofile = (Toolbar) findViewById(R.id.toolbar_edituserprofile);
        setSupportActionBar(_toolbar_edituserprofile);

        user = SharedPrefManager.getInstance(this).getUser();
        oldUserId = user.getUserId();
        oldEmail = user.getEmail();

        etFullname_editprofile = findViewById(R.id.etFullname_editprofile);
        etGender_editprofile = findViewById(R.id.etGender_editprofile);
        etDOB_editprofile = findViewById(R.id.etDOB_editprofile);
        Address_editprofile = findViewById(R.id.Address_editprofile);
        etPhone_editprofile = findViewById(R.id.etPhone_editprofile);
        etEmail_editprofile = findViewById(R.id.etEmail_editprofile);
        etPassword_editprofile = findViewById(R.id.etPassword_editprofile);

        btnSaveUserDetails = findViewById(R.id.btnSaveUserDetails);

        etFullname_editprofile.addTextChangedListener(nameTextWatcher);
        etPhone_editprofile.addTextChangedListener(phoneTextWatcher);
        etEmail_editprofile.addTextChangedListener(emailTextWatcher);
        etPassword_editprofile.addTextChangedListener(passwordTextWatcher);

        setData();

        etDOB_editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditProfileActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month+1;
                        etDOB_editprofile.setText(dayOfMonth + "-" + month + "-" + year);

                    }
                },year, month - 1, day);
                datePickerDialog.show();
            }
        });

        btnSaveUserDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProfile();
            }
        });

    }

    private TextWatcher nameTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String full_name = etFullname_editprofile.getText().toString().trim();
            if (!full_name.matches("[a-zA-Z\\s]+")) {
                etFullname_editprofile.setError("Please enter your valid name");
                btnSaveUserDetails.setEnabled(false);
            } else {
                etFullname_editprofile.setError(null);
                btnSaveUserDetails.setEnabled(true);
            }

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    private TextWatcher emailTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String email = etEmail_editprofile.getText().toString().trim();
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                etEmail_editprofile.setError("Please enter a valid email address");
                btnSaveUserDetails.setEnabled(false);
            }
            else {
                etEmail_editprofile.setError(null);
                btnSaveUserDetails.setEnabled(true);
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private TextWatcher passwordTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String pass = etPassword_editprofile.getText().toString().trim();
            if (!PASSWORD_PATTERN.matcher(pass).matches()){
                etPassword_editprofile.setError("Password must be 6 character long and include one lowercase, digit, special character and uppercase");
                btnSaveUserDetails.setEnabled(false);

            }
            else {
                etPassword_editprofile.setError(null);
                btnSaveUserDetails.setEnabled(true);
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private TextWatcher phoneTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String phone = etPhone_editprofile.getText().toString().trim();
            if (!PHONE_PATTERN.matcher(phone).matches()){
                etPhone_editprofile.setError("Please enter valid phone number");
                btnSaveUserDetails.setEnabled(false);
            }
            else {
                etPhone_editprofile.setError(null);
                btnSaveUserDetails.setEnabled(true);
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private void setData() {
        etFullname_editprofile.setText(user.getFullName());
        etGender_editprofile.setText(user.getGender());
        etDOB_editprofile.setText(user.getDob());
        Address_editprofile.setText(user.getAddress());
        etPhone_editprofile.setText(user.getPhone());
        etEmail_editprofile.setText(user.getEmail());
        etPassword_editprofile.setText(user.getPassword());
    }

    private void updateProfile() {

        final String fullName, newEmail, phone, address, newPassword, dob, gender;
        boolean error = false;
        String url = "https://sabingurung.com.np/pokharahomes/edit_profile.php";
        fullName = etFullname_editprofile.getText().toString().trim();
        newEmail = etEmail_editprofile.getText().toString().trim();
        phone = etPhone_editprofile.getText().toString().trim();
        address = Address_editprofile.getText().toString().trim();
        newPassword = etPassword_editprofile.getText().toString().trim();
        dob = etDOB_editprofile.getText().toString().trim();
        gender = etGender_editprofile.getText().toString().trim();

        if (fullName.isEmpty()){
            etFullname_editprofile.setError("Please fill this field");
            error = true;
        }
        if (!fullName.matches("[a-zA-Z\\s]+")){
            etFullname_editprofile.setError("Please enter your name properly");
            error = true;
        }
        if (newEmail.isEmpty()){
            etEmail_editprofile.setError("Please fill this field");
            error = true;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(newEmail).matches()) {
            etEmail_editprofile.setError("Please enter a valid email address");
            error = true;
        }
        if (phone.isEmpty()){
            etPhone_editprofile.setError("Please fill this field");
            error = true;
        }
        if (!PHONE_PATTERN.matcher(phone).matches()) {
            etPhone_editprofile.setError("Please enter valid phone number");
            error = true;
        }

        if (newPassword.isEmpty()){
            etPassword_editprofile.setError("Please fill this field");
            error = true;
        }
        if (!PASSWORD_PATTERN.matcher(newPassword).matches()) {
            etPassword_editprofile.setError("Password too weak. Must be 6 character long");
            error = true;
        }
        if (address.isEmpty()) {
            Address_editprofile.setError("Please fill this field");
            error = true;
        }
        if (dob.isEmpty()) {
            etDOB_editprofile.setError("Please fill this field");
            error = true;
        }
        if (gender.isEmpty()) {
            etGender_editprofile.setError("Please fill this field");
            error = true;
        }

        if (!error) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            final RequestQueue requestQueue = Volley.newRequestQueue(EditProfileActivity.this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    if (response.trim().equals("success")) {
                        Toast.makeText(EditProfileActivity.this, "Success! Profile Updated", Toast.LENGTH_SHORT).show();
                        if (!newPassword.equals(user.getPassword()) || !newEmail.equals(user.getEmail())) {
                            Intent intent = new Intent(EditProfileActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                        else {
                            User user = new User(oldUserId, fullName, gender, address, phone, newEmail, newPassword, dob);
                            SharedPrefManager.getInstance(EditProfileActivity.this).userLogin(user);
                            Intent intent = new Intent(EditProfileActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                        finish();

                    } else if (response.trim().equals("dbError")) {
                        Toast.makeText(EditProfileActivity.this, "Error while inserting", Toast.LENGTH_SHORT).show();
                    } else if (response.trim().equals("dbError2")) {
                        Toast.makeText(EditProfileActivity.this, "Error while fetching", Toast.LENGTH_SHORT).show();
                    } else if (response.trim().equals("email_taken")) {
                        Toast.makeText(EditProfileActivity.this, "Sorry! This email is already registered", Toast.LENGTH_SHORT).show();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    Toast.makeText(EditProfileActivity.this, ErrorUtils.getVolleyError(error), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("fullName", fullName);
                    params.put("newEmail", newEmail);
                    params.put("oldEmail", oldEmail);
                    params.put("userId", String.valueOf(user.getUserId()));
                    params.put("phone", phone);
                    params.put("gender", gender);
                    params.put("dob", dob);
                    params.put("address", address);
                    params.put("newPassword", newPassword);
                    return params;
                }
            };
            requestQueue.add(stringRequest);
        }

    }
}