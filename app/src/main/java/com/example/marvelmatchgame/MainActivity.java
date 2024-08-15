package com.example.marvelmatchgame;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView1, imageView2;
    private TextView movesTextView, timerTextView, pairsFoundTextView;

    private final List<Integer> card1Values = new LinkedList<>();
    private final List<Integer> card2Values = new LinkedList<>();
    private final int[] images = {
            R.drawable.card1, R.drawable.card2, R.drawable.card3, R.drawable.card4,
            R.drawable.card5, R.drawable.card6, R.drawable.card7, R.drawable.card8
    };

    private boolean firstCardClicked = false, secondCardClicked = false;
    private int moves = 0, index = 0;
    private int pairsFound = 0;
    private final Set<Integer> foundPairs = new HashSet<>();

    private CountDownTimer countDownTimer;
    private long timeLeftInMillis = 60000; // 60 seconds
    private int firstCard = 0, secondCard = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView1 = findViewById(R.id.imageView1);
        imageView2 = findViewById(R.id.imageView2);
        movesTextView = findViewById(R.id.movesTextView);
        timerTextView = findViewById(R.id.timerTextView);
        pairsFoundTextView = findViewById(R.id.pairsFoundTextView);
        Button quitButton = findViewById(R.id.quitButton);

        initializeCards();

        imageView1.setOnClickListener(view -> handleCardClick(imageView1));
        imageView2.setOnClickListener(view -> handleCardClick(imageView2));

        startTimer();

        quitButton.setOnClickListener(view -> endGame());
    }

    private void initializeCards() {
        card1Values.clear();
        card2Values.clear();
        for (int i = 0; i < images.length; i++) {
            card1Values.add(i);
            card2Values.add(i);
        }
        Collections.shuffle(card1Values);
        Collections.shuffle(card2Values);
    }

    private void handleCardClick(ImageView clickedImageView) {
        if (index == card1Values.size()) index = 0;

        if (clickedImageView == imageView1) {
            if (secondCardClicked) imageView2.setImageResource(R.drawable.card_back);
            firstCard = images[card1Values.get(index)];
            imageView1.setImageResource(firstCard);
            firstCardClicked = true;
        } else {
            if (firstCardClicked) imageView1.setImageResource(R.drawable.card_back);
            secondCard = images[card2Values.get(index)];
            imageView2.setImageResource(secondCard);
            secondCardClicked = true;
        }

        index++;
        checkMatch();
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {
                endGame();
            }
        }.start();
    }


    private void updateTimer() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;
        timerTextView.setText(String.format("Time Left: %02d:%02d", minutes, seconds));
    }

    private void endGame() {
        countDownTimer.cancel();
        imageView1.setEnabled(false);
        imageView2.setEnabled(false);

        Intent intent = new Intent(MainActivity.this, ResultActivity.class);
        intent.putExtra("pairsFound", pairsFound);
        intent.putExtra("moves", moves);
        intent.putExtra("isTimeUp", false);
        intent.putExtra("timeLeft", timerTextView.getText().toString());
        startActivity(intent);
        finish();
    }

    private void checkMatch() {
        if (firstCard == secondCard && !foundPairs.contains(firstCard)) {
            Toast.makeText(MainActivity.this, "Pair Found!!", Toast.LENGTH_SHORT).show();
            pairsFound++;
            foundPairs.add(firstCard);
            pairsFoundTextView.setText("Pairs Found: " + pairsFound);
        }
        if (firstCardClicked && secondCardClicked) {
            moves++;
            movesTextView.setText("Moves: " + moves);
            firstCardClicked = secondCardClicked = false; // Reset click flags
        }
        if(pairsFound==images.length){
            endGame();
        }
    }
}
