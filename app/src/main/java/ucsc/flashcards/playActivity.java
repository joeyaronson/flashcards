package ucsc.flashcards;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class playActivity extends AppCompatActivity {

    public boolean isFront = true;
    public boolean setDone;
    public int i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        final ArrayList<String> frontList =  getIntent().getStringArrayListExtra("frontArray");
        final ArrayList<String> backList =  getIntent().getStringArrayListExtra("backArray");
        final TextView card = (TextView) findViewById(R.id.card);

        setDone = false;
        card.setText(frontList.get(i));

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
                    if(i < frontList.size()-1)
                    {
                        frontList.remove(i);
                        backList.remove(i);
                        if(i+1 <  frontList.size()-1)
                        {
                            i++;
                        }
                        else
                        {
                            i = 0;
                        }

                        if(frontList.size() > 0)
                        {
                            card.setText(frontList.get(i));
                        }
                        else
                        {
                            card.setText("Contrats! You have completed this set!");
                            setDone = true;
                        }

                    }
                    else
                    {
                        frontList.remove(i);
                        backList.remove(i);
                        i = 0;
                        if(frontList.size() > 0)
                        {
                            card.setText(frontList.get(i));
                        }
                        else
                        {
                            card.setText("Congrats! You have completed this set!");
                            setDone = true;
                        }
                    }
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
                    if(i < frontList.size()-1)
                    {
                        i++;
                        card.setText(frontList.get(i));
                    }
                    else
                    {
                        i = 0;
                        card.setText(frontList.get(i));
                    }
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
