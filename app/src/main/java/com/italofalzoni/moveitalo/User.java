package com.italofalzoni.moveitalo;

import android.content.Context;
import android.content.SharedPreferences;

public class User {
    public String name;
    public int experience, level, challengesCompleted, nextLevel, initialCountdownValue;

    private SharedPreferences mSharedPreferences;

    public User(Context mContext) {
        this.mSharedPreferences = mContext.getSharedPreferences("name", Context.MODE_PRIVATE);
    }

    public int getInitialCountdownValue() {
        return this.mSharedPreferences.getInt("initialCountdownValue", 3);
    }

    public void setInitialCountdownValue(int value) {
        this.mSharedPreferences.edit().putInt("initialCountdownValue", value).apply();
    }

    public String getName() {
        return this.mSharedPreferences.getString("name", "");
    }

    public void setName(String value) {
        this.mSharedPreferences.edit().putString("name", value).apply();
    }

    public int getExperience() {
        return this.mSharedPreferences.getInt("experience", 0);
    }

    public void setExperience(int value) {
        this.mSharedPreferences.edit().putInt("experience", value).apply();
    }

    public int getLevel() {
        return this.mSharedPreferences.getInt("level", 0);
    }

    public void setLevel(int value) {
        this.mSharedPreferences.edit().putInt("level", value).apply();
    }

    public int getChallengesCompleted() {
        return this.mSharedPreferences.getInt("challengesCompleted", 0);
    }

    public void setChallengesCompleted(int value) {
        this.mSharedPreferences.edit().putInt("challengesCompleted", value).apply();
    }

    public int getNextLevel() {
        return this.mSharedPreferences.getInt("nextlevel", 0);
    }

    public void setNextLevel(int value) {
        this.mSharedPreferences.edit().putInt("nextlevel", value).apply();
    }

    public void levelUp() {
        int currentLevel = getLevel();
        setLevel(currentLevel + 1);
    }

    public void deleteAllUserData() {
//        this.mSharedPreferences.edit().putString("name", "").apply();
//        this.mSharedPreferences.edit().putInt("experience", 0).apply();
//        this.mSharedPreferences.edit().putInt("level", 0).apply();
//        this.mSharedPreferences.edit().putInt("challengesCompleted", 0).apply();
//        this.mSharedPreferences.edit().putInt("nextLevel", 0).apply();
//        this.mSharedPreferences.edit().putInt("initialCountdownValue", 0).apply();
        this.mSharedPreferences.edit().clear().apply();
    }
}
