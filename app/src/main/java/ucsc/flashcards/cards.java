package ucsc.flashcards;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.view.Gravity.BOTTOM;

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
        final ArrayList<String> cardList= new ArrayList<String>();
        final ArrayList<Integer> cardIdList= new ArrayList<Integer>();
        final ArrayList<String> cardBack = new ArrayList<String>();

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

        /*PLAY BUTTON*/
        ImageButton playButton = (ImageButton) findViewById(R.id.playButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isEmpty)
                {
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.error_toast, (ViewGroup) findViewById(R.id.error_toast));

                    TextView text = (TextView) layout.findViewById(R.id.text);
                    text.setText("There are no cards in this set.");

                    Toast toast = new Toast(getApplicationContext());
                    toast.setGravity(Gravity.CENTER|BOTTOM, 0, 150);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();

                }
                else
                {
                    Intent intent = new Intent(cards.this, playActivity.class);

                    intent.putStringArrayListExtra("frontArray", cardList);
                    intent.putStringArrayListExtra("backArray", cardBack);
                    startActivity(intent);
                }

            }
        });
    }

    public void onResume() {
        super.onResume();
        db = new SQLDataBase(this);

        //pulling position of list from previous activity
        final int setID = getIntent().getExtras().getInt("setPosition");


        ListView listView = (ListView) findViewById(R.id.cardList);
        final ArrayList<String> cardList= new ArrayList<String>();
        final ArrayList<Integer> cardIdList= new ArrayList<Integer>();
        final ArrayList<String> cardBack = new ArrayList<String>();

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

        /*PLAY BUTTON*/
        ImageButton playButton = (ImageButton) findViewById(R.id.playButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isEmpty)
                {
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.error_toast, (ViewGroup) findViewById(R.id.error_toast));

                    TextView text = (TextView) layout.findViewById(R.id.text);
                    text.setText("There are no cards in this set.");

                    Toast toast = new Toast(getApplicationContext());
                    toast.setGravity(Gravity.CENTER|BOTTOM, 0, 150);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();

                }
                else
                {
                    Intent intent = new Intent(cards.this, playActivity.class);

                    intent.putStringArrayListExtra("frontArray", cardList);
                    intent.putStringArrayListExtra("backArray", cardBack);
                    startActivity(intent);
                }

            }
        });

    }
}
