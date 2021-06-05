package com.italofalzoni.moveitalo.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.italofalzoni.moveitalo.R;
import com.italofalzoni.moveitalo.User;
import com.italofalzoni.moveitalo.databinding.FragmentHomeBinding;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private User user;
    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        user = new User(root.getContext());

        final TextView currentDate = binding.currentDate;
        final TextView username = binding.username;
        final TextView level = binding.currentUserLevel;
        final TextView completedChallenges = binding.currentUserCompletedChallenges;
        final TextView currentExperience = binding.currentExperience;
        final TextView nextLevel = binding.userNextLevel;

        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                currentDate.setText(setCurrentDate());
                username.setText(user.getName());
                level.setText(String.valueOf(user.getLevel()));
                completedChallenges.setText(String.valueOf(user.getChallengesCompleted()));
                currentExperience.setText(String.valueOf((user.getExperience())));
                nextLevel.setText(String.valueOf(user.getNextLevel()));
            }
        });

        return root;
    }

    private String setCurrentDate() {
        Date today = Calendar.getInstance().getTime();
        Locale currentLocale = new Locale("pt", "BR");

        SimpleDateFormat date = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy", currentLocale);
        String formattedDate = date.format(today);

        return formattedDate;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}