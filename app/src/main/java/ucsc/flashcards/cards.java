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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class cards extends AppCompatActivity {
    SQLDataBase db;
    boolean isEmpty = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cards);
        db = new SQLDataBase(this);

        //pulling position of list from previous activity
        final int setID = getIntent().getExtras().getInt("setPosition");

        //DISPLAYS MESSAGE IF NO CLASSES
        TextView noSet = (TextView)findViewById(R.id.noCards);
        if(!isEmpty)
        {
            noSet.setText("");
        }


        ListView listView = (ListView) findViewById(R.id.cardList);
        final List<String> cardList= new ArrayList<String>();
        final List<Integer> cardIdList= new ArrayList<Integer>();
        final List<String> cardBack = new ArrayList<String>();

        Cursor classCurs = db.getCards(setID);
        while(classCurs.moveToNext())
        {
            isEmpty = false;
            cardList.add(classCurs.getString(0));
            cardBack.add(classCurs.getString(1));
            cardIdList.add(classCurs.getInt(0));

        }



        /* ARRAY ADAPTER */
        ArrayAdapter aa = new ArrayAdapter<String>(getApplicationContext(),R.layout.whitetext,cardList);
        listView.setAdapter(aa);

        /*IF ARRAY IS CLICKED*/
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
            {

                Intent n = new Intent(getApplicationContext(), CardView.class);
                String back = cardBack.get(position);
                n.putExtra("cardPosition", back);
                startActivity(n);
            }
        });

        /*NEW CARD BUTTON*/
        ImageButton newCardButton = (ImageButton) findViewById(R.id.newCard);
        newCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(cards.this, newCard.class);
                intent.putExtra("cardPosition", setID);
                isEmpty = false;
                startActivity(intent);
            }
        });
    }

    public void onResume() {
        super.onResume();
        db = new SQLDataBase(this);

        //pulling position of list from previous activity
        final int setID = getIntent().getExtras().getInt("setPosition");


        ListView listView = (ListView) findViewById(R.id.cardList);
        final List<String> cardList= new ArrayList<String>();
        final List<Integer> cardIdList= new ArrayList<Integer>();
        final List<String> cardBack = new ArrayList<String>();

        //DISPLAYS MESSAGE IF NO CLASSES
        TextView noSet = (TextView)findViewById(R.id.noCards);
        if(!isEmpty)
        {
            noSet.setText("");
        }

        Cursor classCurs = db.getCards(setID);
        while(classCurs.moveToNext())
        {
            isEmpty = false;
            cardList.add(classCurs.getString(0));
            cardBack.add(classCurs.getString(1));
            cardIdList.add(classCurs.getInt(0));

        }

         /*IF ARRAY IS CLICKED*/
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
            {

                Intent n = new Intent(getApplicationContext(), CardView.class);
                String back = cardBack.get(position);
                n.putExtra("cardPosition", back);
                startActivity(n);
            }
        });


        /* ARRAY ADAPTER */
        ArrayAdapter aa = new ArrayAdapter<String>(getApplicationContext(),R.layout.whitetext,cardList);
        listView.setAdapter(aa);

        /*NEW CARD BUTTON*/
        ImageButton newCardButton = (ImageButton) findViewById(R.id.newCard);
        newCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isEmpty = false;
                Intent intent = new Intent(cards.this, newCard.class);
                intent.putExtra("cardPosition", setID);
                startActivity(intent);
            }
        });


    }
}
