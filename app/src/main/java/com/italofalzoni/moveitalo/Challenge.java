package com.italofalzoni.moveitalo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class Challenge {
    public String challengeList, challengeId, description;
    public int amount;
    int randomChallengeIndex = new Random().nextInt(12);

    public Challenge(String challengeList) {
        try {
            JSONArray jsonArray = new JSONArray(challengeList);
            JSONObject challenge = jsonArray.getJSONObject(randomChallengeIndex);

            this.challengeId = challenge.getString("challengeId");
            this.description = challenge.getString("description");
            this.amount = challenge.getInt("amount");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getDescription() {
        return description;
    }

    public int getAmount() {
        return amount;
    }

    public int completedChallenge() {
        return amount;
    }

    public int declinedChallenge() {
        return amount / 2;
    }
}
