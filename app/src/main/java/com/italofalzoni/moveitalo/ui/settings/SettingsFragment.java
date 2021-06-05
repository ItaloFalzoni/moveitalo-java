package com.italofalzoni.moveitalo.ui.settings;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.italofalzoni.moveitalo.LoginActivity;
import com.italofalzoni.moveitalo.MainActivity;
import com.italofalzoni.moveitalo.R;
import com.italofalzoni.moveitalo.User;
import com.italofalzoni.moveitalo.databinding.FragmentSettingsBinding;

public class SettingsFragment extends Fragment implements View.OnClickListener {

    private SettingsViewModel settingsViewModel;
    private FragmentSettingsBinding binding;
    private User user;
    EditText editUsername, editCountdown;
    Context context;
    View root;

    private AlertDialog alert;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        settingsViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);

        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        context = root.getContext();
        user = new User(root.getContext());

        final TextView username = binding.username;
        final TextView experience = binding.experience;
        final TextView level = binding.level;

        editUsername = root.findViewById(R.id.edit_username);
        editCountdown = root.findViewById(R.id.edit_countdown);

        editUsername.setText(user.getName());
        editCountdown.setText(String.valueOf(user.getInitialCountdownValue()));

        root.findViewById(R.id.save_button).setOnClickListener(this);
        root.findViewById(R.id.delete_account).setOnClickListener(this);

        settingsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                username.setText(user.getName());
                level.setText(String.valueOf(user.getLevel()));
                experience.setText(user.getExperience()+" pontos");
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
            case R.id.save_button:
                String newUsername = editUsername.getText().toString();
                int newCountdownValue = Integer.parseInt(editCountdown.getText().toString());
                user.setInitialCountdownValue(newCountdownValue);
                user.setName(newUsername);

                Toast.makeText(context, "Dados salvos!", Toast.LENGTH_SHORT).show();

                getActivity().recreate();
                break;

            case R.id.delete_account:
                deleteAccount();
                break;
        }
    }

    private void deleteAccount() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle("Atenção");
        builder.setMessage("Tem certeza que deseja deletar sua conta? Esta ação é irreversível!");

        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                user.deleteAllUserData();
                startActivity(new Intent(context, LoginActivity.class));
            }
        });

        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alert.dismiss();
            }
        });

        alert = builder.create();
        alert.show();
        alert.setCanceledOnTouchOutside(false);
    }
}