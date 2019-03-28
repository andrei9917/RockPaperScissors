package com.example.andrei.rpsjava;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ImageButton rockBtn;
    private ImageButton paperBtn;
    private ImageButton scissorsBtn;
    private ImageButton resetBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rockBtn = findViewById(R.id.rockButton);
        paperBtn = findViewById(R.id.paperButton);
        scissorsBtn = findViewById(R.id.scissorsButton);
        resetBtn = findViewById(R.id.resetButton);

        rockBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newGame("rock");
            }
        });

        paperBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newGame("paper");
            }
        });

        scissorsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newGame("scissors");
            }
        });

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetUI();
            }
        });

        prepareUI();
    }

    protected void prepareUI(){
        TextView statusText = findViewById(R.id.myStatusTextView);
        statusText.animate().alpha(1).setDuration(1000);
    }

    protected void resetUI() {
        TextView statusText = findViewById(R.id.myStatusTextView);
        ImageView userChoice = findViewById(R.id.myUserChoiceGraphicImageView);
        TextView versusTV = findViewById(R.id.versusTextView);
        ImageView cpuChoice = findViewById(R.id.myCPUChoiceGraphicImageView);
        TextView userScore = findViewById(R.id.myUserScoreTextView);
        TextView cpuScore = findViewById(R.id.myCPUScoreTextView);

        Animation anim = new TranslateAnimation(Animation.ABSOLUTE, Animation.ABSOLUTE, Animation.ABSOLUTE, Animation.ABSOLUTE);
        anim.setDuration(100);
        anim.setFillAfter(true);
        userChoice.setAlpha(0f);
        versusTV.setAlpha(0f);
        cpuChoice.setAlpha(0f);
        statusText.startAnimation(anim);
        statusText.setText("Choose rock/paper/scissors to start playing.");

        userScore.setText("0");
        cpuScore.setText("0");
    }

    protected void newGame(String user) {
        setButtonsUnclickable();
        String cpuChoice = getCPUChoice();
        final int result = evaluateGame(user, cpuChoice);
        processResult(result);
        setGraphicGame(user, cpuChoice);
        animateUserChoice();
        animateVersus();
        animateCPUChoice();
        animateStatusText();
        new CountDownTimer(2000, 1000){
            @Override
            public void onTick(long millisUntilFinished) { }

            @Override
            public void onFinish() {
                modifyScore(result);
            }
        }.start();
    }

    protected void animateStatusText(){
        TextView statusText = findViewById(R.id.myStatusTextView);
        Animation anim = new TranslateAnimation(Animation.ABSOLUTE, Animation.ABSOLUTE, Animation.ABSOLUTE, 100);
        anim.setDuration(1000);
        anim.setFillAfter(true);
        anim.setStartOffset(2000);
        statusText.startAnimation(anim);
        statusText.animate().alpha(1).setDuration(500).setStartDelay(2000);
    }

    protected void animateUserChoice()
    {
        ImageView userChoice = findViewById(R.id.myUserChoiceGraphicImageView);
        Animation anim = new TranslateAnimation(Animation.ABSOLUTE, Animation.ABSOLUTE, Animation.ABSOLUTE, 100);
        anim.setDuration(1000);
        anim.setFillAfter(true);
        userChoice.startAnimation(anim);
        userChoice.animate().alpha(1).setDuration(500);
    }

    protected void animateVersus(){
        TextView vsText = findViewById(R.id.versusTextView);
        Animation anim = new TranslateAnimation(Animation.ABSOLUTE, Animation.ABSOLUTE, Animation.ABSOLUTE, 100);
        anim.setDuration(1000);
        anim.setStartOffset(500);
        anim.setFillAfter(true);
        vsText.startAnimation(anim);
        vsText.animate().alpha(1).setDuration(500).setStartDelay(500);

    }

    protected void animateCPUChoice(){
        ImageView cpuChoice = findViewById(R.id.myCPUChoiceGraphicImageView);
        Animation anim = new TranslateAnimation(Animation.ABSOLUTE, Animation.ABSOLUTE, Animation.ABSOLUTE, 100);
        anim.setDuration(1000);
        anim.setFillAfter(true);
        anim.setStartOffset(1000);
        cpuChoice.startAnimation(anim);
        cpuChoice.animate().alpha(1).setDuration(500).setStartDelay(1000);
    }

    protected void setGraphicGame(String user, String cpu){
        ImageView userGraphic = findViewById(R.id.myUserChoiceGraphicImageView);
        ImageView cpuGraphic = findViewById(R.id.myCPUChoiceGraphicImageView);
        Integer[] images = {R.drawable.rock, R.drawable.paper, R.drawable.scissors};

        if (user.equals("rock"))
            userGraphic.setImageResource(images[0]);
        if (user.equals("paper"))
            userGraphic.setImageResource(images[1]);
        if (user.equals("scissors"))
            userGraphic.setImageResource(images[2]);

        if (cpu.equals("rock"))
            cpuGraphic.setImageResource(images[0]);
        if (cpu.equals("paper"))
            cpuGraphic.setImageResource(images[1]);
        if (cpu.equals("scissors"))
            cpuGraphic.setImageResource(images[2]);

        findViewById(R.id.versusTextView).setAlpha(0f);
        userGraphic.setAlpha(0f);
        cpuGraphic.setAlpha(0f);
    }

    protected void processResult(Integer result){
        if(result == 1){
            TextView status = findViewById(R.id.myStatusTextView);
            status.setText("You WIN!");
            status.setAlpha(0);
        }
        if(result == 0){
            TextView status = findViewById(R.id.myStatusTextView);
            status.setText("You DRAW!");
            status.setAlpha(0);
        }
        if(result == -1){
            TextView status = findViewById(R.id.myStatusTextView);
            status.setText("You LOST!");
            status.setAlpha(0);
        }
    }

    protected Integer evaluateGame(String user, String cpu){
    // returns 1 on player win
    // returns 0 on draw
    // returns -1 on player loss/cpu win
        if (user.equals("rock")) {
            if (cpu.equals("rock"))
                return 0;
            if (cpu.equals("paper"))
                return -1;
            if(cpu.equals("scissors"))
                return 1;
        }
        if(user.equals("paper")){
            if(cpu.equals("rock"))
                return 1;
            if(cpu.equals("paper"))
                return 0;
            if(cpu.equals("scissors"))
                return -1;
        }
        if(user.equals("scissors")) {
            if(cpu.equals("rock"))
                return -1;
            if(cpu.equals("paper"))
                return 1;
            if(cpu.equals("scissors"))
                return 0;
        }
        return 0;
    }

    protected String getCPUChoice(){
        ArrayList<String> choices = new ArrayList<>();
        choices.add("rock");
        choices.add("paper");
        choices.add("scissors");
        Integer random = new Random().nextInt(3);
        return choices.get(random);
    }

    protected void resetScoreColor(){
        TextView playerScoreTV = findViewById(R.id.myUserScoreTextView);
        TextView cpuScoreTV = findViewById(R.id.myCPUScoreTextView);
        playerScoreTV.setTextColor(Color.GRAY);
        cpuScoreTV.setTextColor(Color.GRAY);
    }

    protected void modifyScore(Integer result)
    {
        if(result == 1) {
            TextView playerScoreTV = findViewById(R.id.myUserScoreTextView);
            Integer playerScore = Integer.parseInt(playerScoreTV.getText().toString());
            playerScore++;
            playerScoreTV.setText(playerScore.toString());
            playerScoreTV.setTextColor(Color.GREEN);
        }

        if(result == 0){
            TextView playerScoreTV = findViewById(R.id.myUserScoreTextView);
            TextView cpuScoreTV = findViewById(R.id.myCPUScoreTextView);
            playerScoreTV.setTextColor(Color.GRAY);
            cpuScoreTV.setTextColor(Color.GRAY);
        }

        if (result == -1) {
            TextView cpuScoreTV = findViewById(R.id.myCPUScoreTextView);
            Integer cpuScore = Integer.parseInt(cpuScoreTV.getText().toString());
            cpuScore++;
            cpuScoreTV.setText(cpuScore.toString());
            cpuScoreTV.setTextColor(Color.RED);
        }
        new CountDownTimer(1000, 1000){
            @Override
            public void onTick(long millisUntilFinished) { }

            @Override
            public void onFinish() {
                resetScoreColor();
                setButtonsClickable();
            }
        }.start();
    }

    protected void setButtonsClickable(){
        rockBtn.setClickable(true);
        paperBtn.setClickable(true);
        scissorsBtn.setClickable(true);
        resetBtn.setClickable(true);
    }

    protected void setButtonsUnclickable(){
        rockBtn.setClickable(false);
        paperBtn.setClickable(false);
        scissorsBtn.setClickable(false);
        resetBtn.setClickable(false);
    }
}
