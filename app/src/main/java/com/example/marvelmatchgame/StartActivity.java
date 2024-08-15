package com.example.marvelmatchgame;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Button startButton = findViewById(R.id.startButton);
        Button instructionsButton = findViewById(R.id.instructionsButton);
        Button exitButton = findViewById(R.id.exitButton);
        startButton.setOnClickListener(v -> {
            // Start the main activity when the button is pressed
            Intent intent = new Intent(StartActivity.this, MainActivity.class);
            startActivity(intent);

        });
        instructionsButton.setOnClickListener(view -> showInstructions());
        exitButton.setOnClickListener(view -> finish());

    }
    private void showInstructions() {
        LayoutInflater inflater = getLayoutInflater();
        android.view.View dialogView = inflater.inflate(R.layout.dialog_instructions, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView)
                .setCancelable(true);

        AlertDialog dialog = builder.create();
        dialog.show();

        Button closeButton = dialogView.findViewById(R.id.closeButton);
        closeButton.setOnClickListener(v -> dialog.dismiss());
    }
}
