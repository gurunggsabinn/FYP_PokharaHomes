package com.example.pokharahomes.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.os.IResultReceiver;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pokharahomes.LoginActivity;
import com.example.pokharahomes.R;
import com.example.pokharahomes.models.User;
import com.example.pokharahomes.utils.SharedPrefManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    private TextView tvLogout, tvBookingHistory, tvOrderHistory, fragment_profile_username, tvUserprofile_gender, tvUserprofile_dob, tvUserprofile_address, tvUserprofile_phone, tvUserprofile_email, tvNotitications;
    private User user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        user = SharedPrefManager.getInstance(getContext()).getUser();

        fragment_profile_username = v.findViewById(R.id.fragment_profile_username);
        tvUserprofile_gender = v.findViewById(R.id.tvUserprofile_gender);
        tvUserprofile_dob = v.findViewById(R.id.tvUserprofile_dob);
        tvUserprofile_address = v.findViewById(R.id.tvUserprofile_address);
        tvUserprofile_phone = v.findViewById(R.id.tvUserprofile_phone);
        tvUserprofile_email = v.findViewById(R.id.tvUserprofile_email);
        tvNotitications = v.findViewById(R.id.tvNotitications);

        tvOrderHistory = v.findViewById(R.id.tvOrderHistory);
        tvBookingHistory = v.findViewById(R.id.tvBookingHistory);
        tvLogout = v.findViewById(R.id.tvLogout);

        setData();

        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

        return v;
    }

    private void setData() {
        tvUserprofile_gender.setText(user.getGender());
        tvUserprofile_dob.setText(user.getDob());
        tvUserprofile_address.setText(user.getAddress());
        tvUserprofile_phone.setText(user.getPhone());
        tvUserprofile_email.setText(user.getEmail());
        fragment_profile_username.setText(user.getFullName());
    }

    private void logout(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Logout from the app?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                getActivity().finish();
                SharedPrefManager.getInstance(getContext()).logout();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);

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
