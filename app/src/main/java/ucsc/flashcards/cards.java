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
    boolean deleteMode = false;
    boolean sortMode = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cards);
        db = new SQLDataBase(this);
        deleteMode = false;
        int index = 0;

        //pulling position of list from previous activity
        final int setID = getIntent().getExtras().getInt("setPosition");

        //DISPLAYS MESSAGE IF NO CLASSES
        TextView noSet = (TextView)findViewById(R.id.noCards);
        if(!isEmpty)
        {
            noSet.setText("");
        }


        final ListView listView = (ListView) findViewById(R.id.cardList);
        final ArrayList<String> cardList= new ArrayList<String>();
        final ArrayList<Integer> cardIDList= new ArrayList<Integer>();
        final ArrayList<String> cardBack = new ArrayList<String>();
        final ArrayList<Integer> cardDiffList= new ArrayList<Integer>();

        Cursor classCurs = db.getCards(setID);
        if( sortMode == false ){
            while(classCurs.moveToNext())
            {
                isEmpty = false;
                cardList.add(classCurs.getString(0));
                cardBack.add(classCurs.getString(1));
                cardIDList.add(classCurs.getInt(3));
            }
        } else {
            for(int i = 5; i >= 0; i--)
            {
                classCurs = db.getCards(setID);
                while(classCurs.moveToNext())
                {
                    if(classCurs.getInt(2) == i){
                        index++;
                        isEmpty = false;
                        cardList.add(classCurs.getString(0));
                        cardBack.add(classCurs.getString(1));
                        cardIDList.add(classCurs.getInt(3));
                        for(int j = 0; j < ((i/2)+2); j++)
                        {
                            cardDiffList.add(index);
                        }
                    }
                }
            }

        }




        /* ARRAY ADAPTER */
        final ArrayAdapter aa = new ArrayAdapter<String>(getApplicationContext(),R.layout.whitetext,cardList);
        listView.setAdapter(aa);

        /*IF ARRAY IS CLICKED*/
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
            {
                System.out.println("deleteMode: " + deleteMode);
                if(!deleteMode)
                {
                    Intent n = new Intent(getApplicationContext(), CardView.class);
                    String back = cardBack.get(position);
                    n.putExtra("cardPosition", back);
                    startActivity(n);
                }
                else
                {
                    db.deleteCard(cardIDList.get(position));
                    System.out.println("TEST::::::"+cardIDList.get(position));
                    cardList.remove(position);
                    aa.notifyDataSetChanged();
                }
            }
        });


        /*DELETE BUTTON*/
        final ImageButton deleteButton = (ImageButton) findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.delete_toast, (ViewGroup) findViewById(R.id.delete_toast));

                TextView text = (TextView) layout.findViewById(R.id.text);


                Toast toast = new Toast(getApplicationContext());
                toast.setGravity(Gravity.CENTER|BOTTOM, 0, 150);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(layout);

                if(deleteMode)
                {
                    text.setText("Delete Mode Off");
                    deleteMode = false;
                }
                else
                {
                    text.setText("Delete Mode On");
                    deleteMode = true;
                }
                toast.show();


            }
        });

        /*SORT BUTTON*/
        final ImageButton sortButton = (ImageButton) findViewById(R.id.sortButton);
        sortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.sort_toast, (ViewGroup) findViewById(R.id.sort_toast));

                TextView text = (TextView) layout.findViewById(R.id.text);

                Toast toast = new Toast(getApplicationContext());
                toast.setGravity(Gravity.CENTER|BOTTOM, 0, 200);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(layout);

                if(sortMode)
                {
                    text.setText("Sort by Date Created");
                    aa.notifyDataSetChanged();
                    sortMode = false;
                } else {
                    text.setText("Sort by Difficulty");
                    aa.notifyDataSetChanged();
                    sortMode = true;
                }
                toast.show();
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
                    intent.putIntegerArrayListExtra("diffArray", cardDiffList);
                    intent.putIntegerArrayListExtra("cardIDArray", cardIDList);
                    intent.putExtra("sortMode", sortMode);
                    startActivity(intent);
                }

            }
        });
    }





/* ---------------------------------------------------------------------------------------------- */







    public void onResume() {
        super.onResume();
        db = new SQLDataBase(this);
        deleteMode = false;
        int index = 0;

        //pulling position of list from previous activity
        final int setID = getIntent().getExtras().getInt("setPosition");


        final ListView listView = (ListView) findViewById(R.id.cardList);
        final ArrayList<String> cardList= new ArrayList<String>();
        final ArrayList<Integer> cardIDList= new ArrayList<Integer>();
        final ArrayList<String> cardBack = new ArrayList<String>();
        final ArrayList<Integer> cardDiffList= new ArrayList<Integer>();

        //DISPLAYS MESSAGE IF NO CLASSES
        TextView noSet = (TextView)findViewById(R.id.noCards);
        if(!isEmpty)
        {
            noSet.setText("");
        }

        Cursor classCurs = db.getCards(setID);

        index = 0;
        if( sortMode == false ){
            while(classCurs.moveToNext())
            {
                isEmpty = false;
                cardList.add(classCurs.getString(0));
                cardBack.add(classCurs.getString(1));
                cardIDList.add(classCurs.getInt(3));
            }
        } else {
            for(int i = 5; i >= 0; i--)
            {
                classCurs = db.getCards(setID);
                while(classCurs.moveToNext())
                {
                    if(classCurs.getInt(2) == i){
                        isEmpty = false;
                        cardList.add(classCurs.getString(0));
                        cardBack.add(classCurs.getString(1));
                        cardIDList.add(classCurs.getInt(3));
                        for(int j = 0; j < ((i/2)+2); j++)
                        {
                            cardDiffList.add(index);
                        }
                        index++;
                    }
                }
            }

        }

        /* ARRAY ADAPTER */
        final ArrayAdapter aa = new ArrayAdapter<String>(getApplicationContext(),R.layout.whitetext,cardList);
        listView.setAdapter(aa);

         /*IF ARRAY IS CLICKED*/
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
            {
                System.out.println("deleteMode: " + deleteMode);
                if(!deleteMode)
                {
                    Intent n = new Intent(getApplicationContext(), CardView.class);
                    String back = cardBack.get(position);
                    n.putExtra("cardPosition", back);
                    startActivity(n);
                }
                else
                {
                    db.deleteCard(cardIDList.get(position));
                    System.out.println("TEST::::::"+cardIDList.get(position));
                    cardList.remove(position);
                    aa.notifyDataSetChanged();
                }
            }
        });




        /*DELETE BUTTON*/
        final ImageButton deleteButton = (ImageButton) findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.delete_toast, (ViewGroup) findViewById(R.id.delete_toast));

                TextView text = (TextView) layout.findViewById(R.id.text);


                Toast toast = new Toast(getApplicationContext());
                toast.setGravity(Gravity.CENTER|BOTTOM, 0, 200);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(layout);

                if(deleteMode)
                {
                    text.setText("Delete Mode Off");
                    deleteMode = false;
                }
                else
                {
                    text.setText("Delete Mode On");
                    deleteMode = true;
                }
                toast.show();


            }
        });


        /*SORT BUTTON*/
        final ImageButton sortButton = (ImageButton) findViewById(R.id.sortButton);
        sortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.sort_toast, (ViewGroup) findViewById(R.id.sort_toast));

                TextView text = (TextView) layout.findViewById(R.id.text);

                Toast toast = new Toast(getApplicationContext());
                toast.setGravity(Gravity.CENTER|BOTTOM, 0, 200);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(layout);

                if(sortMode)
                {
                    text.setText("Sort by Date Created");
                    aa.notifyDataSetChanged();
                    sortMode = false;
                } else {
                    text.setText("Sort by Difficulty");
                    aa.notifyDataSetChanged();
                    sortMode = true;
                }
                toast.show();
            }
        });

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
                    toast.setGravity(Gravity.CENTER|BOTTOM, 0, 200);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();

                }
                else
                {
                    Intent intent = new Intent(cards.this, playActivity.class);

                    intent.putStringArrayListExtra("frontArray", cardList);
                    intent.putStringArrayListExtra("backArray", cardBack);
                    intent.putIntegerArrayListExtra("diffArray", cardDiffList);
                    intent.putIntegerArrayListExtra("cardIDArray", cardIDList);
                    intent.putExtra("sortMode", sortMode);
                    startActivity(intent);
                }

            }
        });

    }
}
