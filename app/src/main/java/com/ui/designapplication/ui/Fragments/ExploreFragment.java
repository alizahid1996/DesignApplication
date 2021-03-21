package com.ui.designapplication.ui.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ui.designapplication.DetailActivity;
import com.ui.designapplication.FilterActivity;
import com.ui.designapplication.R;
import com.ui.designapplication.databinding.FragmentExploreBinding;

import java.util.concurrent.Executor;

public class ExploreFragment extends Fragment {

    FragmentExploreBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       binding = FragmentExploreBinding.inflate(getLayoutInflater(),container,false);


       binding.ShenandoahNationalPark.setOnClickListener(v -> {
           Intent intent = new Intent(getActivity(), DetailActivity.class);
           startActivity(intent);
       });
       return binding.getRoot();
    }
}