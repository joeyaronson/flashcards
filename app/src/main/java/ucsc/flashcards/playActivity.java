package ucsc.flashcards;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class playActivity extends AppCompatActivity {

    public boolean isFront = true;
    public boolean setDone;
    public int i = 0;
    public int nextIndex = 0;
    public int initialCards;
    Random rand = new Random();
    SQLDataBase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        db = new SQLDataBase(this);

        final ArrayList<String> frontList =  getIntent().getStringArrayListExtra("frontArray");
        final ArrayList<String> backList =  getIntent().getStringArrayListExtra("backArray");
        final boolean sortMode =  getIntent().getExtras().getBoolean("sortMode");
        final ArrayList<Integer> diffList =  getIntent().getIntegerArrayListExtra("diffArray");
        final ArrayList<Integer> cardIDList =  getIntent().getIntegerArrayListExtra("cardIDArray");
        final TextView card = (TextView) findViewById(R.id.card);

        final ProgressBar progressBar=(ProgressBar)findViewById(R.id.pileProgress); // initiate the progress bar
        progressBar.setMax(100); // 100 maximum value for the progress value
        progressBar.setScaleY(3f);

        initialCards = frontList.size();
        if(sortMode){ //set the starting progress
            progressBar.setProgress((100*diffList.size())/(initialCards*5));
        } else {
            progressBar.setProgress(100);
        }


        setDone = false;

        i = rand.nextInt(frontList.size());

        card.setText(frontList.get(i));
        card.setMovementMethod(new ScrollingMovementMethod());

        //final TextView count = (TextView) findViewById(R.id.countText);

        //count.setText((i+1)+"/"+frontList.size());

        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!setDone)
                {
                    if(isFront)
                    {
                        card.setText(backList.get(i));
                        isFront = false;
                    }
                    else
                    {
                        card.setText(frontList.get(i));
                        isFront = true;
                    }
                }
            }
        });

        /*I KNOW THIS BUTTON*/
        ImageButton yesButton = (ImageButton) findViewById(R.id.yesButton);
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!setDone)
                {
                    //if(i < frontList.size()-1)
                    //{
                        /* if(i+1 <  frontList.size()-1)
                        {
                            i++;
                        }
                        else
                        {
                            i = 0;
                        } */


                        if(sortMode){
                            diffList.remove((Integer)i);
                        } else {
                            frontList.remove(i);
                            backList.remove(i);
                        }
                        if(frontList.size() > 0)
                        {
                            if(frontList.size() > 1)
                            {
                                if( sortMode ){
                                    nextIndex = (int)diffList.get(rand.nextInt(diffList.size()));
                                    while( i == nextIndex ){
                                        nextIndex = (int)diffList.get(rand.nextInt(diffList.size()));
                                    }
                                    i = nextIndex;
                                    progressBar.setProgress((100*diffList.size())/(initialCards*5));
                                } else {
                                    i = rand.nextInt(frontList.size());
                                    progressBar.setProgress((100*frontList.size())/initialCards);
                                    System.out.println("i = "+i);
                                }
                            } else {
                                i = 0;
                            }
                            card.setText(frontList.get(i));
                            //count.setText((i+1) +"/"+frontList.size());
                            db.changeDiff(true, cardIDList.get(i));
                        }
                        else
                        {
                            //count.setText("0/0");
                            progressBar.setProgress(0);
                            card.setText("Contrats! You have completed this set!");
                            setDone = true;
                        }

                }
                else
                {
                    finish();
                }


            }
        });





        /*I DO NOT KNOW THIS BUTTON*/
        ImageButton noButton = (ImageButton) findViewById(R.id.noButton);
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!setDone)
                {
                    if(frontList.size() > 1){
                        if( sortMode ){
                            diffList.add(i);
                            nextIndex = (int)diffList.get(rand.nextInt(diffList.size()));
                            while( i == nextIndex ){
                                nextIndex = (int)diffList.get(rand.nextInt(diffList.size()));
                            }
                            i = nextIndex;
                            progressBar.setProgress((100*diffList.size())/(initialCards*5));
                        } else {
                            nextIndex = rand.nextInt(frontList.size());
                            while(i == nextIndex){
                                nextIndex = rand.nextInt(frontList.size());
                            }
                            i = nextIndex;
                            //progressBar.setProgress((100*frontList.size())/initialCards);
                        }
                    }
                    card.setText(frontList.get(i));
                    db.changeDiff(false, cardIDList.get(i));
                    /* if(i < frontList.size()-1)
                    {
                        i++;
                        card.setText(frontList.get(i));
                    }
                    else
                    {
                        i = 0;
                        card.setText(frontList.get(i));
                    } */

                    //count.setText((i+1) +"/"+frontList.size());
                }
                else {
                    finish();
                }
            }
        });

        /*DONE BUTTON */
        ImageButton done = (ImageButton) findViewById(R.id.doneButton);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();
            }
        });

    }


}
