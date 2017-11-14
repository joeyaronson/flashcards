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

public class sets extends AppCompatActivity {
    SQLDataBase db;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sets);
        db = new SQLDataBase(this);

        //pulling position of list from previous activity
        final int classID = getIntent().getExtras().getInt("classPosition");
        System.out.println("ClassID:"+classID);


        ListView listView = (ListView) findViewById(R.id.setList);
        final List<String> setList= new ArrayList<String>();
        final List<Integer> setIdList= new ArrayList<Integer>();

        Cursor classCurs = db.getChapters(classID);
        while(classCurs.moveToNext())
        {
            setList.add(classCurs.getString(1));
            setIdList.add(classCurs.getInt(0));
            System.out.println("\n\n\n\n\ncol0: " + classCurs.getInt(0) + " col1: " + classCurs.getString(1) + "\n\n\n\n\n");

        }

        /* ARRAY ADAPTER */
        ArrayAdapter aa = new ArrayAdapter<String>(getApplicationContext(),R.layout.whitetext,setList);
        listView.setAdapter(aa);

        /*ARRAY ONCLICK*/
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
            {

                Intent n = new Intent(getApplicationContext(), cards.class);
                int idPos = setIdList.get(position);
                n.putExtra("setPosition", idPos);
                startActivity(n);
            }
        });

        /*NEW SET BUTTON*/
        ImageButton newSetButton = (ImageButton) findViewById(R.id.newSet);
        newSetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(sets.this, newSet.class);

                intent.putExtra("classPosition", classID);
                startActivity(intent);
            }
        });

    }
    public void onResume() {
        super.onResume();

        final int classID = getIntent().getExtras().getInt("classPosition");
        System.out.println("ClassID:"+classID);
        ListView listView = (ListView) findViewById(R.id.setList);
        final List<String> setList = new ArrayList<String>();
        final List<Integer> setIdList = new ArrayList<Integer>();

        Cursor classCurs = db.getChapters(classID);
        while (classCurs.moveToNext()) {
            setList.add(classCurs.getString(1));
            setIdList.add(classCurs.getInt(0));

        }

        /* ARRAY ADAPTER */
        ArrayAdapter aa = new ArrayAdapter<String>(getApplicationContext(), R.layout.whitetext, setList);
        listView.setAdapter(aa);

        /*ARRAY ONCLICK*/
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                Intent n = new Intent(getApplicationContext(), cards.class);
                int idPos = setIdList.get(position);
                n.putExtra("setPosition", idPos);
                startActivity(n);
            }
        });

        /*NEW SET BUTTON*/
        ImageButton newSetButton = (ImageButton) findViewById(R.id.newSet);
        newSetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(sets.this, newSet.class);
                intent.putExtra("classPosition", classID);
                startActivity(intent);
            }
        });

    }
}
