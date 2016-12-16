package com.jordimasip.brainnbatrainer;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView pointsTextView;
    Button startButton;
    TextView resultTextView;
    Button button0;
    Button button1;
    Button button2;
    Button button3;
    TextView questionTextView;
    TextView timerTextView;
    Button playAgainButton;
    RelativeLayout gameRelativeLayout;
    ArrayList<Question> questions = new ArrayList<>();
    int locationOfCorrectAnswer;
    int score = 0;
    int numberOfQuestions = 0;

    public void generateQuestion() {
        Random rand = new Random();

        int positionAnswer = rand.nextInt(11);

        questionTextView.setText(questions.get(positionAnswer).description);
        Log.i("StatsLovers",questions.get(positionAnswer).answers[0].description);
        for(int i=0; i < 4; i++) {
            if (questions.get(positionAnswer).answers[i].isCorrect) {
                locationOfCorrectAnswer = i;
            }
        }
        button0.setText(questions.get(positionAnswer).answers[0].description);
        button1.setText(questions.get(positionAnswer).answers[1].description);
        button2.setText(questions.get(positionAnswer).answers[2].description);
        button3.setText(questions.get(positionAnswer).answers[3].description);

    }

    public void chooseAnswer(View view) {
        Log.i("correct", "posicio guanyadora ---> "+Integer.toString(locationOfCorrectAnswer));
        Log.i("correct", "seleccio ---> "+view.getTag().toString());

        if (view.getTag().toString().equals(Integer.toString(locationOfCorrectAnswer))) {
            Log.i("correct", "correct");
            score++;
            resultTextView.setText("Correct!");
        } else {
            resultTextView.setText("Wrong!");
        }

        numberOfQuestions++;
        pointsTextView.setText(Integer.toString(score) + "/" + Integer.toString(numberOfQuestions));
        generateQuestion();
    }

    public void playAgain(View view) {
        score = 0;
        numberOfQuestions = 0;

        timerTextView.setText("30s");
        pointsTextView.setText("0/0");
        resultTextView.setText("");
        playAgainButton.setVisibility(View.INVISIBLE);
        button0.setVisibility(View.VISIBLE);
        button1.setVisibility(View.VISIBLE);
        button2.setVisibility(View.VISIBLE);
        button3.setVisibility(View.VISIBLE);
        questionTextView.setVisibility(View.VISIBLE);

        generateQuestion();

        new CountDownTimer(30100, 1000) {

            @Override
            public void onTick(long l) {
                timerTextView.setText(String.valueOf(l / 1000) + "s");
            }

            @Override
            public void onFinish() {
                playAgainButton.setVisibility(View.VISIBLE);
                timerTextView.setText("0s");
                resultTextView.setText("Your score: " + Integer.toString(score) + "/" + Integer.toString(numberOfQuestions));
                button0.setVisibility(View.INVISIBLE);
                button1.setVisibility(View.INVISIBLE);
                button2.setVisibility(View.INVISIBLE);
                button3.setVisibility(View.INVISIBLE);
                questionTextView.setVisibility(View.INVISIBLE);
            }
        }.start();

    }

    public void start(View view) {
        startButton.setVisibility(View.INVISIBLE);
        gameRelativeLayout.setVisibility(RelativeLayout.VISIBLE);

        playAgain(findViewById(R.id.playAgainButton));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        insertQuestions();

        startButton = (Button) findViewById(R.id.startButton);
        questionTextView = (TextView) findViewById(R.id.questionTextView);
        button0 = (Button) findViewById(R.id.button0);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        resultTextView = (TextView) findViewById(R.id.resultTextView);
        pointsTextView = (TextView) findViewById(R.id.pointsTextView);
        timerTextView = (TextView) findViewById(R.id.timerTextView);
        playAgainButton = (Button) findViewById(R.id.playAgainButton);
        gameRelativeLayout = (RelativeLayout) findViewById(R.id.gameRelativeLayout);
    }

    class Question {
        String description;
        Answer[] answers;

        public Question(String description, Answer[] answers) {
            this.description = description;
            this.answers = answers;
        }
    }

    class Answer {
        String description;
        boolean isCorrect;

        public Answer(String description, boolean isCorrect) {
            this.description = description;
            this.isCorrect = isCorrect;
        }
    }

    public void insertQuestions() {
        // 10
        questions.add(new Question("Who was the 2014-2015 MVP?", new Answer[]{new Answer("Stephen Curry", true), new Answer("James Harden", false), new Answer("LeBron James", false), new Answer("Kawhi Leonard", false)}));
        questions.add(new Question("Who was the 2014-2015 Defensive Player of the Year?", new Answer[]{new Answer("Draymond Green", false), new Answer("Kawhi Leonard", true), new Answer("LeBron James", false), new Answer("DeAndre Jordan", false)}));
        questions.add(new Question("Who was the 2014-2015 Coach of the Year?", new Answer[]{new Answer("Mike Budenholzer", true), new Answer("Jason Kidd", false), new Answer("Steve Kerr", false), new Answer("Gregg Popovich", false)}));
        questions.add(new Question("How many championship rings did Kobe Bryant win as a player?", new Answer[]{new Answer("3", false), new Answer("2", false), new Answer("4", false), new Answer("5", true)}));
        questions.add(new Question("How many seasons did Michael Jordan play in the NBA?", new Answer[]{new Answer("10", false), new Answer("15", true), new Answer("17", false), new Answer("12", false)}));
        questions.add(new Question("How many championship rings did Kevin Garnett win as a player?", new Answer[]{new Answer("0", false), new Answer("1", true), new Answer("2", false), new Answer("3", false)}));
        questions.add(new Question("Which NBA team selected Kobe Bryant from the 1996 draft?", new Answer[]{new Answer("Los Angeles Lakers", false), new Answer("Sacramento Kings", false), new Answer("Boston Celtics", false), new Answer("Charlotte Hornets", true)}));
        questions.add(new Question("How many All-Star appearances did Shaquille O'Neal have", new Answer[]{new Answer("15", true), new Answer("18", false), new Answer("17", false), new Answer("16", false)}));
        questions.add(new Question("What is Karl Malone's jersey number", new Answer[]{new Answer("21", false), new Answer("32", true), new Answer("00", false), new Answer("45", false)}));
        questions.add(new Question("What was the most points Kevin Garnett ever scored in a game?", new Answer[]{new Answer("50", false), new Answer("49", false), new Answer("47", true), new Answer("44", false)}));

        // 20
        questions.add(new Question("What is Wilt Chamberlain's record for most points in a game?", new Answer[]{new Answer("98", false), new Answer("85", false), new Answer("91", false), new Answer("100", true)}));
        questions.add(new Question("Who was the first center to lead the NBA in assists for a season?", new Answer[]{new Answer("Wilt Chamberlain", true), new Answer("George Mikan", false), new Answer("Bill Russell", false), new Answer("Bill Walton", false)}));
        questions.add(new Question("Who was the first player to win back to back MVPs?", new Answer[]{new Answer("Wilt Chamberlain", false), new Answer("Bill Russell", true), new Answer("Kareem Abdul-Jabbar", false), new Answer("Isaiah Thomas", false)}));
        questions.add(new Question("What NBA star retired for the third time on May 14, 1996?", new Answer[]{new Answer("Michael Jordan", false), new Answer("Magic Johnson", true), new Answer("Larry Bird", false), new Answer("Karl Malone", false)}));
        questions.add(new Question("Which team was Kareem Abdul-Jabbar drafted by?", new Answer[]{new Answer("Detroit Pistons", false), new Answer("Seattle SuperSonics", false), new Answer("Phoenix Suns", false), new Answer("Milwaukee Bucks", true)}));
        questions.add(new Question("Which team won the 08-09 NBA Championship?", new Answer[]{new Answer("Los Angeles Lakers", true), new Answer("Orlando Magic", false), new Answer("Cleveland Cavaliers", false), new Answer("Dallas Mavericks", false)}));
        questions.add(new Question("Which player won the 06-07 Defensive Player of the Year award?", new Answer[]{new Answer("Ben Wallace", false), new Answer("Marcus Camby", true), new Answer("Ron Artest", false), new Answer("Kevin Garnett", false)}));
        questions.add(new Question("How many jersey numbers has Michael Jordan worn in his NBA career?", new Answer[]{new Answer("1", false), new Answer("2", false), new Answer("3", true), new Answer("4", false)}));
        questions.add(new Question("How many times has Ray Allen been named an All-Star?", new Answer[]{new Answer("10", true), new Answer("8", false), new Answer("11", false), new Answer("7", false)}));
        questions.add(new Question("Which European was the 2002 Rookie of the Year?", new Answer[]{new Answer("Tony Parker", false), new Answer("Manu Ginóbili", false), new Answer("Dirk Nowitzki", false), new Answer("Pau Gasol", true)}));

    }
}
