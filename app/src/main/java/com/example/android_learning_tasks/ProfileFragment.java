package com.example.android_learning_tasks;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ProfileFragment extends Fragment {

    private String name;
    private String email;
    private int age;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            name = getArguments().getString("name");
            email = getArguments().getString("email");
            age = getArguments().getInt("age");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView tvName, tvEmail, tvAge;
        tvName = view.findViewById(R.id.tvName);
        tvEmail = view.findViewById(R.id.tvEmail);
        tvAge = view.findViewById(R.id.tvAge);
        if(getArguments() != null){
            tvName.setText(name);
            tvEmail.setText(email);
            tvAge.setText(String.valueOf(age));
        }else{
            tvName.setText("User not login!");
            tvEmail.setVisibility(View.GONE);
            tvAge.setVisibility(View.GONE);
        }
    }
}