package com.example.marvelmatchgame;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView resultTextView = findViewById(R.id.resultTextView);
        TextView pairsFoundTextView = findViewById(R.id.pairsFoundTextView);
        TextView movesTextView = findViewById(R.id.movesTextView);
        TextView timeStatusTextView = findViewById(R.id.timeStatusTextView);
        Button retryButton=findViewById(R.id.retryButton);
        Button exitButton=findViewById(R.id.exitButton);

        // Get data from intent
        Intent intent = getIntent();
        int pairsFound = intent.getIntExtra("pairsFound", 0);
        int moves = intent.getIntExtra("moves", 0);
        String timeLeft = intent.getStringExtra("timeLeft");
        boolean isTimeUp = intent.getBooleanExtra("isTimeUp", false);

        // Set results
        resultTextView.setText(pairsFound!=8? "Game Over! \nTime's Up You Lost!\nBetter Luck Next Time!!" : "Game Over! \nCongratulations! You won!");
        pairsFoundTextView.setText("Pairs Found: " + pairsFound);
        movesTextView.setText("Moves: " + moves);
        timeStatusTextView.setText(isTimeUp ? "Time Status: Time Up" : timeLeft);

        retryButton.setOnClickListener(view -> {
            Intent intent1 = new Intent(ResultActivity.this, MainActivity.class);
            startActivity(intent1);
            finish();
        });

        exitButton.setOnClickListener(view -> finish());
    }
}
