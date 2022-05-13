package com.example.pokharahomes;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pokharahomes.utils.ErrorUtils;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    EditText etSignupFullname, etSignupDOB, etSignupAddress, etSignupPhone, etSignupEmail, etSignupPassword;
    TextView tvLogin;
    RadioGroup rgGenre;
    Button btnSignup;

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

    Calendar mCurrentDate;
    int day, month, year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_screen);

        etSignupFullname = (EditText) findViewById(R.id.etSignupFullname);
        etSignupDOB = (EditText) findViewById(R.id.etSignupDOB);
        etSignupAddress = (EditText) findViewById(R.id.etSignupAddress);
        etSignupPhone = (EditText) findViewById(R.id.etSignupPhone);
        etSignupEmail = (EditText) findViewById(R.id.etSignupEmail);
        etSignupPassword = (EditText) findViewById(R.id.etSignupPassword);

        etSignupFullname.addTextChangedListener(nameTextWatcher);
        etSignupEmail.addTextChangedListener(emailTextWatcher);
        etSignupPhone.addTextChangedListener(phoneTextWatcher);
        etSignupPassword.addTextChangedListener(passwordTextWatcher);

        tvLogin = (TextView) findViewById(R.id.tvLogin);

        btnSignup = (Button) findViewById(R.id.btnSignup);

        rgGenre = (RadioGroup) findViewById(R.id.rgGenre);

        mCurrentDate = Calendar.getInstance();
        day = mCurrentDate.get(Calendar.DAY_OF_MONTH);
        month = mCurrentDate.get(Calendar.MONTH);
        year = mCurrentDate.get(Calendar.YEAR);
        month = month + 1;

        etSignupDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(SignUpActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month+1;
                        etSignupDOB.setText(dayOfMonth + "-" + month + "-" + year);

                    }
                },year, month - 1, day);
                datePickerDialog.show();
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount();
            }
        });

    }

    private TextWatcher nameTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String full_name = etSignupFullname.getText().toString().trim();
            if (!full_name.matches("[a-zA-Z\\s]+")) {
                etSignupFullname.setError("Please enter your valid name");
                btnSignup.setEnabled(false);
            } else {
                etSignupFullname.setError(null);
                btnSignup.setEnabled(true);
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
            String email = etSignupEmail.getText().toString().trim();
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                etSignupEmail.setError("Please enter a valid email address");
                btnSignup.setEnabled(false);
            }
            else {
                etSignupEmail.setError(null);
                btnSignup.setEnabled(true);
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
            String pass = etSignupPassword.getText().toString().trim();
            if (!PASSWORD_PATTERN.matcher(pass).matches()){
                etSignupPassword.setError("Password must be 6 character long and include one lowercase, digit, special character and uppercase");
                btnSignup.setEnabled(false);

            }
            else {
                etSignupPassword.setError(null);
                btnSignup.setEnabled(true);
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
            String phone = etSignupPhone.getText().toString().trim();
            if (!PHONE_PATTERN.matcher(phone).matches()){
                etSignupPhone.setError("Please enter valid phone number");
                btnSignup.setEnabled(false);
            }
            else {
                etSignupPhone.setError(null);
                btnSignup.setEnabled(true);
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private void createAccount() {
        final String fullName, email, password, phone, dob, gender, address;
        boolean error = false;
        String url = "https://sabingurung.com.np/pokharahomes/create_user.php";
        fullName = etSignupFullname.getText().toString().trim();
        email = etSignupEmail.getText().toString().trim();
        password = etSignupPassword.getText().toString().trim();
        phone = etSignupPhone.getText().toString().trim();
        address = etSignupAddress.getText().toString().trim();
        dob = etSignupDOB.getText().toString().trim();
        gender = ((RadioButton)findViewById(rgGenre.getCheckedRadioButtonId())).getText().toString();

        if (fullName.isEmpty()){
            etSignupFullname.setError("Please fill this field");
            error = true;
        }
        if (!fullName.matches("[a-zA-Z\\s]+")){
            etSignupFullname.setError("Please enter your valid name");
            error = true;
        }
        if (email.isEmpty()){
            etSignupEmail.setError("Please fill this field");
            error = true;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etSignupEmail.setError("Please enter a valid email address");
            error = true;
        }
        if (password.isEmpty()){
            etSignupPassword.setError("Please fill this field");
            error = true;
        }
        if (!PASSWORD_PATTERN.matcher(password).matches()) {
            etSignupPassword.setError("Password too weak. Must be 6 character long");
            error = true;
        }
        if (phone.isEmpty()){
            etSignupPhone.setError("Please fill this field");
            error = true;
        }
        if (!PHONE_PATTERN.matcher(phone).matches()) {
            etSignupPhone.setError("Please enter valid phone number");
            error = true;
        }

        if (address.isEmpty()) {
            etSignupAddress.setError("Please fill this field");
            error = true;
        }

        if (dob.isEmpty()) {
            etSignupDOB.setError("Please fill this field");
            error = true;
        }

        if (!error) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            final RequestQueue requestQueue = Volley.newRequestQueue(SignUpActivity.this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    if (response.trim().equals("success")) {
                        Toast.makeText(SignUpActivity.this, "Account successfully created", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else if (response.trim().equals("dbError")) {
                        Toast.makeText(getApplicationContext(), "Error while inserting", Toast.LENGTH_SHORT).show();
                    }
                    else if (response.trim().equals("dbError2")) {
                        Toast.makeText(getApplicationContext(), "Error while fetching", Toast.LENGTH_SHORT).show();
                    }
                    else if (response.trim().equals("email_taken")) {
                        Toast.makeText(getApplicationContext(), "Email already registered", Toast.LENGTH_SHORT).show();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    Toast.makeText(SignUpActivity.this, ErrorUtils.getVolleyError(error), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("fullName", fullName);
                    params.put("email", email);
                    params.put("password", password);
                    params.put("phone", phone);
                    params.put("address", address);
                    params.put("dob", dob);
                    params.put("gender", gender);
                    return params;
                }
            };
            requestQueue.add(stringRequest);
        }
    }

    public void gotoLogin(View view) {
        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
        builder.setMessage("Are you sure want to exit the app?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
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
}