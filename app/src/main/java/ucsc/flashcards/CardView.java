package ucsc.flashcards;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class CardView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view);
        final String back = getIntent().getExtras().getString("cardPosition");
        TextView backText = (TextView)findViewById(R.id.backText);
        backText.setText(back);
        backText.setMovementMethod(new ScrollingMovementMethod());


    }
}
