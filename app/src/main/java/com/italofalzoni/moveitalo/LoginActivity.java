package com.italofalzoni.moveitalo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private User user;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        Button buttonLogin = findViewById(R.id.login_button);
        EditText loginUsername = findViewById(R.id.login_username);
        TextView invalidUsername = findViewById(R.id.invalid_username);

        buttonLogin.setImeActionLabel("Ir", KeyEvent.KEYCODE_ENTER);

        Toast toast = Toast.makeText(this, "Insira um usuário", Toast.LENGTH_SHORT);

        user = new User(this);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = loginUsername.getText().toString();

                if (username.equals("")) {
                    invalidUsername.setVisibility(View.VISIBLE);
                    toast.show();
                } else {
                    handleSetUsername(username, this);
                }
            }
        });
    }


    public void handleSetUsername(String username, View.OnClickListener context) {
        user.setName(username);

        new MainActivity();
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        Log.d("name of user", user.getName());
        startActivity(intent);
    }
}
