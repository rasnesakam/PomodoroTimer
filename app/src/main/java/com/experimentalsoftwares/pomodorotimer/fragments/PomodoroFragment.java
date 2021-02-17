package com.experimentalsoftwares.pomodorotimer.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.experimentalsoftwares.pomodorotimer.R;
import com.experimentalsoftwares.pomodorotimer.activities.PomodoroActivity;


public class PomodoroFragment extends Fragment {

    Button button;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        button = view.findViewById(R.id.start_pomodoro);
        button.setOnClickListener((v)->{
           startActivity(new Intent(getActivity(), PomodoroActivity.class));
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pomodoro, container, false);
    }
}