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
import com.example.pokharahomes.adapters.RoomAdapter;
import com.example.pokharahomes.bookings.RoomBookingActivity;
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

public class RoomFragment extends Fragment {

    private RecyclerView recyclerViewRoom;
    private ArrayList<Room> roomArrayList;
    private RoomAdapter roomAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_rooms, container, false);

        recyclerViewRoom = v.findViewById(R.id.recyclerViewRooms);

        roomArrayList = new ArrayList<>();

        roomAdapter = new RoomAdapter(roomArrayList, getContext());

        getRooms();

        return v;

    }

    private void getRooms() {
        String url = "https://sabingurung.com.np/pokharahomes/get_rooms.php";

        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("not_found")) {
                    Toast.makeText(getContext(), "No any rooms found!", Toast.LENGTH_SHORT).show();
                }
                else{
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        JSONObject jsonResponse;

                        for (int i = 0; i < jsonArray.length(); i++){
                            jsonResponse = jsonArray.getJSONObject(i);
                            String roomName = jsonResponse.getString("room_name");
                            int price = jsonResponse.getInt("price");
                            int roomId = jsonResponse.getInt("id");
                            String features = jsonResponse.getString("features");
                            String included = jsonResponse.getString("included");
                            int maxPersonAllowed = jsonResponse.getInt("max_person_allowed");
                            String image1 = jsonResponse.getString("image_1");
                            String image2 = jsonResponse.getString("image_2");

                            Room room = new Room(roomId, price, maxPersonAllowed, roomName, included, features, image1, image2);
                            roomArrayList.add(room);
                        }

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        recyclerViewRoom.setLayoutManager(linearLayoutManager);
                        recyclerViewRoom.setAdapter(roomAdapter);
                        //recycler_view_futsals.addItemDecoration(new SpacesItemDecoration(20));
                        roomAdapter.notifyDataSetChanged();

                        ItemClickSupport.addTo(recyclerViewRoom).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                            @Override
                            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                                Room room = roomArrayList.get(position);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("room_details", room);
                                Intent intent = new Intent(getContext(), RoomBookingActivity.class);
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
