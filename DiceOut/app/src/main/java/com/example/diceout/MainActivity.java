/*
This is a game application that outputs roll diced results and awards points

 */

package com.example.diceout;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    //Field to hold the roll result text
    TextView rollResult;

    //Field to hold the score
    int score;
    //Field to hold our random number generator
    Random rand;

    //Fields to hold our dice values
    int die1;
    int die2;
    int die3;

    //Field to hold the score text
    TextView scoreText;

    //ArrayList to hold our 3 dice values
    ArrayList<Integer> dice;
    //ArrayList to hold all our images
    ArrayList<ImageView> diceImageViews;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rollDice(view);
            }
        });
        //Set initial score
        score =0;
        //To access the result on click
        rollResult = (TextView) findViewById(R.id.rollResult);
        //Create Greetings for when app launches
        Toast.makeText(getApplicationContext(),"Welcome to DiceOut!",Toast.LENGTH_SHORT).show();

        scoreText = (TextView) findViewById(R.id.scoreText);
        //Initialize the random number generator
        rand = new Random();
        //Create ArrayList container for the dice value
        dice = new ArrayList<Integer>();

        ImageView dice1Image = (ImageView) findViewById(R.id.dielImage);
        ImageView dice2Image = (ImageView) findViewById(R.id.dei2Image);
        ImageView dice3Image = (ImageView) findViewById(R.id.dei3Image);

        diceImageViews = new ArrayList<ImageView>();
        diceImageViews.add(dice1Image);
        diceImageViews.add(dice2Image);
        diceImageViews.add(dice3Image);
    }

    //This method displays to the roll Result when clicked
    public void rollDice(View v){
        rollResult.setText("Clicked!");

        //Create a dice random number from 1-6
        die1 = rand.nextInt(6)+1;
        die2 = rand.nextInt(6)+1;
        die3 = rand.nextInt(6)+1;

        //Set dice values to int arrayList
        dice.clear();
        dice.add(die1);
        dice.add(die2);
        dice.add(die3);

        for (int dieOfSet = 0; dieOfSet < 3; dieOfSet++){
            String imageName = "die_"+ dice.get(dieOfSet) + ".png";

            try {
                InputStream stream = getAssets().open(imageName);
                Drawable d = Drawable.createFromStream(stream,null);
                diceImageViews.get(dieOfSet).setImageDrawable(d);
            } catch (IOException e){
                e.printStackTrace();
            }
        }

        //Build message with the result
        String msg;

        if (die1 == die2 && die1 == die3){
            //Triples
            int scoreDelta = die1 *100;
            msg = "You rolled a triple "+ die1 + "! You score "+ scoreDelta + " points!";
            score +=scoreDelta;
        } else if (die1 == die2 || die1 == die3 || die2 == die3) {
            //Doubles
            msg = "You rolled doubles for 50 points!";
            score +=50;
            } else {
            msg = "You didn't score this roll. Try again!";
        }

        //Update the app to display the result msg
        rollResult.setText(msg);
        scoreText.setText("Score: "+ score);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
