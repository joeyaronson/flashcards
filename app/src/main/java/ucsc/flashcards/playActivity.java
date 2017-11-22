package ucsc.flashcards;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class playActivity extends AppCompatActivity {

    public boolean isFront = true;
    public int i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        final ArrayList<String> frontList =  getIntent().getStringArrayListExtra("frontArray");
        final ArrayList<String> backList =  getIntent().getStringArrayListExtra("backArray");
        final TextView card = (TextView) findViewById(R.id.card);
        card.setText(frontList.get(i));

        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
        });
        ImageButton nextButton = (ImageButton) findViewById(R.id.yesButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        });

    }


}
