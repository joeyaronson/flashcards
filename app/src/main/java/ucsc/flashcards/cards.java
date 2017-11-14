package ucsc.flashcards;

import android.content.Intent;
import android.database.Cursor;
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
    SQLDataBase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cards);
        db = new SQLDataBase(this);

        //pulling position of list from previous activity
        final int setID = getIntent().getExtras().getInt("setPosition");
        System.out.println(setID);


        ListView listView = (ListView) findViewById(R.id.cardList);
        final List<String> cardList= new ArrayList<String>();
        final List<Integer> cardIdList= new ArrayList<Integer>();

        Cursor classCurs = db.getCards(setID);
        while(classCurs.moveToNext())
        {
            cardList.add(classCurs.getString(1));
            cardIdList.add(classCurs.getInt(0));

        }
        /*PULL FROM SQL HERE*/


        /* ARRAY ADAPTER */
        ArrayAdapter aa = new ArrayAdapter<String>(getApplicationContext(),R.layout.whitetext,cardList);
        listView.setAdapter(aa);

        /*NEW CARD BUTTON*/
        ImageButton newCardButton = (ImageButton) findViewById(R.id.newCard);
        newCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(cards.this, newCard.class);
                intent.putExtra("cardPosition", setID);
                startActivity(intent);
            }
        });
    }

    public void onResume() {
        super.onResume();
        db = new SQLDataBase(this);

        //pulling position of list from previous activity
        final int setID = getIntent().getExtras().getInt("setPosition");
        System.out.println(setID);


        ListView listView = (ListView) findViewById(R.id.cardList);
        final List<String> cardList= new ArrayList<String>();
        final List<Integer> cardIdList= new ArrayList<Integer>();

        Cursor classCurs = db.getCards(setID);
        while(classCurs.moveToNext())
        {
            cardList.add(classCurs.getString(2));
            cardIdList.add(classCurs.getInt(0));

        }
        /*PULL FROM SQL HERE*/


        /* ARRAY ADAPTER */
        ArrayAdapter aa = new ArrayAdapter<String>(getApplicationContext(),R.layout.whitetext,cardList);
        listView.setAdapter(aa);

        /*NEW CARD BUTTON*/
        ImageButton newCardButton = (ImageButton) findViewById(R.id.newCard);
        newCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(cards.this, newCard.class);
                intent.putExtra("cardPosition", setID);
                startActivity(intent);
            }
        });


    }
}
