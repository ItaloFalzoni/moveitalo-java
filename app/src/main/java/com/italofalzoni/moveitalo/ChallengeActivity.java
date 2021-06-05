package com.italofalzoni.moveitalo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ChallengeActivity extends AppCompatActivity implements View.OnClickListener {
    User user;
    Challenge challenge;
    int currentExperience, finalExperience, experienceToNextLevel, amount, userLevel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.challenge_activity);
        getSupportActionBar().hide();

        Button acceptChallenge = findViewById(R.id.accept_button);
        Button declineChallenge = findViewById(R.id.decline_button);

        user = new User(this);

        loadCurrentUserData();
        loadChallenge();
        acceptChallenge.setOnClickListener(this);
        declineChallenge.setOnClickListener(this);
    }

    private void loadChallenge() {
        TextView challengeValue = findViewById(R.id.challenge_value);
        TextView challengeDescription = findViewById(R.id.challenge_description);

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

        challengeValue.setText("Valendo " + challenge.getAmount() + " pontos");
        challengeDescription.setText(challenge.getDescription());
    }

    private void loadCurrentUserData() {
        TextView currentLevel = findViewById(R.id.current_level);
        TextView currentExperience = findViewById(R.id.current_experience);
        TextView nextLevel = findViewById(R.id.next_level);
        TextView username = findViewById(R.id.username);


        currentLevel.setText("Level " + user.getLevel());
        currentExperience.setText("Pontos: "+user.getExperience());
        nextLevel.setText(user.getNextLevel()+"% para o próximo nível");

        username.setText(user.getName());
    }

    @Override
    public void onClick(View v) {

        Intent mainIntent = new Intent(getBaseContext(), MainActivity.class);

        switch (v.getId()) {
            case R.id.accept_button:
                currentExperience = user.getExperience();
                amount = challenge.getAmount();
                userLevel = user.getLevel();
                experienceToNextLevel = (int) Math.pow((userLevel + 1) * 4, 2);

                finalExperience = currentExperience + amount;

                if (finalExperience >= experienceToNextLevel) {
                    finalExperience -= experienceToNextLevel;
                    user.levelUp();
                    Toast.makeText(this, "Você subiu de nível!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Desafio aceito!", Toast.LENGTH_LONG).show();
                }

                user.setExperience(finalExperience);
                user.setChallengesCompleted(user.getChallengesCompleted() + 1);
                user.setNextLevel((currentExperience * 100) / experienceToNextLevel);

                startActivity(mainIntent);
                break;

            case R.id.decline_button:
                Toast.makeText(this, "Desafio recusado!", Toast.LENGTH_LONG).show();
                startActivity(mainIntent);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }
    }
}
