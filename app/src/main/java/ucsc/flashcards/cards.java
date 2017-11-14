package ucsc.flashcards;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class cards extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cards);

        //pulling position of list from previous activity
        int pos = getIntent().getExtras().getInt("setPosition");

        /*PULL FROM SQL HERE*/
        ListView listView = (ListView) findViewById(R.id.cardList);
        List<String> cardList= new ArrayList<String>();
        cardList.add("covalent bond");
        cardList.add("ionic bond");
        cardList.add("polar bond");

        /* ARRAY ADAPTER */
        ArrayAdapter aa = new ArrayAdapter<String>(getApplicationContext(),R.layout.whitetext,cardList);
        listView.setAdapter(aa);

        /*NEW CARD BUTTON*/
        ImageButton newCardButton = (ImageButton) findViewById(R.id.newCard);
        newCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(cards.this, newCard.class);
                startActivity(intent);
            }
        });
    }
}
