package com.example.pokharahomes.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.pokharahomes.models.User;

public class SharedPrefManager {

    //the constants
    private static final String SHARED_PREF_NAME = "pokhara_homes_user_shared_pref";
    private static final String KEY_FULLNAME = "key_fullname";
    private static final String KEY_EMAIL = "key_email";
    private static final String KEY_PASSWORD = "key_password";
    private static final String KEY_PHONE = "key_phone";
    private static final String KEY_ID = "key_id";
    private static final String KEY_GENDER = "key_gender";
    private static final String KEY_ADDRESS = "key_address";
    private static final String KEY_DOB = "key_dob";

    private static com.example.pokharahomes.utils.SharedPrefManager mInstance;
    private static Context mCtx;

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized com.example.pokharahomes.utils.SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new com.example.pokharahomes.utils.SharedPrefManager(context);
        }
        return mInstance;
    }

    //method to let the user login
    //this method will store the user data in shared preferences
    public void userLogin(User user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID, user.getUserId());
        editor.putString(KEY_FULLNAME, user.getFullName());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_PHONE, user.getPhone());
        editor.putString(KEY_PASSWORD, user.getPassword());
        editor.putString(KEY_GENDER, user.getGender());
        editor.putString(KEY_ADDRESS, user.getAddress());
        editor.putString(KEY_DOB, user.getDob());
        editor.apply();
    }

    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_FULLNAME, null) != null;
    }

    //this method will give the logged in user
    public User getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getInt(KEY_ID, -1),
                sharedPreferences.getString(KEY_FULLNAME, null),
                sharedPreferences.getString(KEY_GENDER, null),
                sharedPreferences.getString(KEY_ADDRESS, null),
                sharedPreferences.getString(KEY_PHONE, null),
                sharedPreferences.getString(KEY_EMAIL, null),
                sharedPreferences.getString(KEY_PASSWORD, null),
                sharedPreferences.getString(KEY_DOB, null)

        );
    }

    //this method will logout the user
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
//        mCtx.startActivity(new Intent(mCtx, LoginActivity.class));
    }
}
