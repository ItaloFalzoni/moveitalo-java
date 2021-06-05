package com.italofalzoni.moveitalo.ui.countdown;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.italofalzoni.moveitalo.Challenge;
import com.italofalzoni.moveitalo.ChallengeActivity;
import com.italofalzoni.moveitalo.R;
import com.italofalzoni.moveitalo.User;
import com.italofalzoni.moveitalo.databinding.FragmentCountdownBinding;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class CountdownFragment extends Fragment implements View.OnClickListener {

    private User user;
    private CountdownViewModel countdownViewModel;
    private FragmentCountdownBinding binding;
    int initialValue;

    CountDownTimer countdown;
    Button startCountdown;
    Button cancelCountdown;
    TextView countdownText;

    Challenge challenge;

    Context context;

    private AlertDialog alert;

    int currentExperience, finalExperience, experienceToNextLevel, amount, userLevel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        countdownViewModel = new ViewModelProvider(this).get(CountdownViewModel.class);

        binding = FragmentCountdownBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        context = root.getContext();
        user = new User(context);

        final TextView username = binding.username;

        root.findViewById(R.id.start_countdown).setOnClickListener(this);
        root.findViewById(R.id.cancel_countdown).setOnClickListener(this);

        startCountdown = root.findViewById(R.id.start_countdown);
        cancelCountdown = root.findViewById(R.id.cancel_countdown);
        countdownText = root.findViewById(R.id.countdown_text);

        initialValue = user.getInitialCountdownValue();

        countdownViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                username.setText(user.getName());
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.start_countdown:
                startCountdown.setVisibility(View.GONE);
                cancelCountdown.setVisibility(View.VISIBLE);
                startCountdown(countdownText);
                break;

            case R.id.cancel_countdown:
                cancelCountdown.setVisibility(View.GONE);
                startCountdown.setVisibility(View.VISIBLE);
                cancelCountdown(countdownText);
                break;
        }
    }

    private void openChallenge() {
        LayoutInflater li = getLayoutInflater();
        View challengeDialog = li.inflate(R.layout.challenge_dialog, null);
        loadChallenge();

        TextView challengeValue = challengeDialog.findViewById(R.id.challenge_value);
        TextView challengeDescription = challengeDialog.findViewById(R.id.challenge_description);

        Button acceptButton = challengeDialog.findViewById(R.id.accept_button);
        Button declineButton = challengeDialog.findViewById(R.id.decline_button);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentExperience = user.getExperience();
                amount = challenge.getAmount();
                userLevel = user.getLevel();
                experienceToNextLevel = (int) Math.pow((userLevel + 1) * 4, 2);

                finalExperience = currentExperience + amount;

                if (finalExperience >= experienceToNextLevel) {
                    finalExperience -= experienceToNextLevel;
                    user.levelUp();
                    Toast.makeText(context, "Você subiu de nível!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "Desafio aceito!", Toast.LENGTH_LONG).show();
                }

                user.setExperience(finalExperience);
                user.setChallengesCompleted(user.getChallengesCompleted() + 1);
                user.setNextLevel((currentExperience * 100) / experienceToNextLevel);
                alert.dismiss();
            }
        });

        declineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Desafio recusado", Toast.LENGTH_SHORT).show();
                alert.dismiss();
            }
        });

        challengeValue.setText("Valendo " + challenge.getAmount() + " pontos");
        challengeDescription.setText(challenge.getDescription());

        builder.setView(challengeDialog);

        alert = builder.create();
        alert.show();
        alert.setCanceledOnTouchOutside(false);
    }

    private void loadChallenge() {
        String string = "";
        StringBuilder stringBuilder = new StringBuilder();
        InputStream is = this.getResources().openRawResource(R.raw.challenges);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        while (true) {
            try {
                if ((string = reader.readLine()) == null) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            stringBuilder.append(string);
        }
        challenge = new Challenge(stringBuilder.toString());
    }

    public void startCountdown(TextView countdownText) {
        countdown = new CountDownTimer(1000 * initialValue, 1000) {

            public void onTick(long millisUntilFinished) {
                countdownText.setText("00:0"+millisUntilFinished/ 1000);
            }

            public void onFinish() {
                openChallenge();
                cancelCountdown.setVisibility(View.GONE);
                startCountdown.setVisibility(View.VISIBLE);
                countdownText.setText("00:0"+initialValue);
            }
        }.start();
    }

    public void cancelCountdown(TextView countdownText) {
        countdown.cancel();
        countdownText.setText("00:0"+initialValue);
    }
}