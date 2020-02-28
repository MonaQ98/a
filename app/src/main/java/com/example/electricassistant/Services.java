package com.example.electricassistant;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class Services extends Fragment {

    private ServicesViewModel mViewModel;
    LinearLayout electricity_standard;
    LinearLayout policy;
    public static Services newInstance() {
        return new Services();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.services_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ServicesViewModel.class);
        // TODO: Use the ViewModel
        policy = getActivity().findViewById(R.id.policy);
        electricity_standard = getActivity().findViewById(R.id.electricity_standard);
        electricity_standard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), Web_Electricity_Standard.class);
                startActivity(intent);//打开新的activity
            }
        });
        policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), Web_policy.class);
                startActivity(intent);//打开新的activity
            }
        });

    }

}
