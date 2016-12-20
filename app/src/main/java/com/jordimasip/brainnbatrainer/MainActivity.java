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
    ArrayList<Integer> locationPastQuestions = new ArrayList<>();

    public static boolean isAlreadyAsk(ArrayList<Integer> arr, int targetValue) {
        for (int i = 0; i < arr.size(); i++) {
            if(arr.get(i).intValue() == targetValue) {
                return true;
            }
        }
        return false;
    }

    public int getPositionQuestion() {
        Random rand = new Random();
        int positionQuestion = rand.nextInt(questions.size());
        while(isAlreadyAsk(locationPastQuestions, positionQuestion)) {
            positionQuestion = rand.nextInt(questions.size());
        }
        locationPastQuestions.add(positionQuestion);
        return positionQuestion;
    }

    public void generateQuestion() {
        int positionQuestion = getPositionQuestion();
        Log.i("StatsLovers", "posicio pregunta -------------> "+positionQuestion);

        questionTextView.setText(questions.get(positionQuestion).description);
        for(int i=0; i < 4; i++) {
            if (questions.get(positionQuestion).answers[i].isCorrect) {
                locationOfCorrectAnswer = i;
            }
        }
        button0.setText(questions.get(positionQuestion).answers[0].description);
        button1.setText(questions.get(positionQuestion).answers[1].description);
        button2.setText(questions.get(positionQuestion).answers[2].description);
        button3.setText(questions.get(positionQuestion).answers[3].description);

    }

    public void chooseAnswer(View view) {

        if (view.getTag().toString().equals(Integer.toString(locationOfCorrectAnswer))) {
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

        timerTextView.setText("60s");
        pointsTextView.setText("0/0");
        resultTextView.setText("");
        playAgainButton.setVisibility(View.INVISIBLE);
        button0.setVisibility(View.VISIBLE);
        button1.setVisibility(View.VISIBLE);
        button2.setVisibility(View.VISIBLE);
        button3.setVisibility(View.VISIBLE);
        questionTextView.setVisibility(View.VISIBLE);

        generateQuestion();

        new CountDownTimer(60100, 1000) {

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
        questions.add(new Question("Which team won the 2008-09 NBA Championship?", new Answer[]{new Answer("Los Angeles Lakers", true), new Answer("Orlando Magic", false), new Answer("Cleveland Cavaliers", false), new Answer("Dallas Mavericks", false)}));
        questions.add(new Question("Which player won the 2006-07 Defensive Player of the Year award?", new Answer[]{new Answer("Ben Wallace", false), new Answer("Marcus Camby", true), new Answer("Ron Artest", false), new Answer("Kevin Garnett", false)}));
        questions.add(new Question("How many jersey numbers has Michael Jordan worn in his NBA career?", new Answer[]{new Answer("1", false), new Answer("2", false), new Answer("3", true), new Answer("4", false)}));
        questions.add(new Question("How many times has Ray Allen been named an All-Star?", new Answer[]{new Answer("10", true), new Answer("8", false), new Answer("11", false), new Answer("7", false)}));
        questions.add(new Question("Which European was the 2002 Rookie of the Year?", new Answer[]{new Answer("Tony Parker", false), new Answer("Manu Ginóbili", false), new Answer("Dirk Nowitzki", false), new Answer("Pau Gasol", true)}));

        // 30
        questions.add(new Question("Which team won the 2015-16 NBA Championship?", new Answer[]{new Answer("San Antonio Spurs", false), new Answer("Golden State Warriors", false), new Answer("Cleveland Cavaliers", true), new Answer("Miami Heat", false)}));
        questions.add(new Question("Which team won the 2014-15 NBA Championship?", new Answer[]{new Answer("Boston Celtics", false), new Answer("Houston Rockets", false), new Answer("Cleveland Cavaliers", false), new Answer("Golden State Warriors", true)}));
        questions.add(new Question("Which team won the 2013-14 NBA Championship?", new Answer[]{new Answer("San Antonio Spurs", true), new Answer("Miami Heat", false), new Answer("Oklahoma City Thunder", false), new Answer("Dallas Mavericks", false)}));
        questions.add(new Question("Which team won the 2012-13 NBA Championship?", new Answer[]{new Answer("Houston Rockets", false), new Answer("Boston Celtics", false), new Answer("Miami Heat", true), new Answer("San Antonio Spurs", false)}));
        questions.add(new Question("Which team won the 2011-12 NBA Championship?", new Answer[]{new Answer("Oklahoma City Thunder", false), new Answer("Miami Heat", true), new Answer("Dallas Mavericks", false), new Answer("Golden State Warriors", false)}));
        questions.add(new Question("Which team won the 2010-11 NBA Championship?", new Answer[]{new Answer("Miami Heat", false), new Answer("Cleveland Cavaliers", false), new Answer("New Jersey Nets", false), new Answer("Dallas Mavericks", true)}));

        /*
        2000 	Los Angeles Lakers (1) 	4–2 	Indiana Pacers (1) 	[78]
        2001 	Los Angeles Lakers (2) 	4–1 	Philadelphia 76ers (1) 	[79]
        2002 	Los Angeles Lakers (3) 	4–0 	New Jersey Nets (1) 	[80]
        2003 	San Antonio Spurs (1) 	4–2 	New Jersey Nets (2) 	[81]
        2004 	Los Angeles Lakers (2) 	1–4 	Detroit Pistons (3) 	[82]
        2005 	San Antonio Spurs (2) 	4–3 	Detroit Pistons (2) 	[83]
        2006 	Dallas Mavericks (4) 	2–4 	Miami Heat (2) 	[84]
        2007 	San Antonio Spurs (3) 	4–0 	Cleveland Cavaliers (2) 	[85]
        2008 	Los Angeles Lakers (1) 	2–4 	Boston Celtics (1)dagger 	[86]
        2010 	Los Angeles Lakers (1) 	4–3 	Boston Celtics (4)*/

    }
}
