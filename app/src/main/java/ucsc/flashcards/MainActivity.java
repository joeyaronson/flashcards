package ucsc.flashcards;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.id;
import static ucsc.flashcards.R.array.classes;

public class MainActivity extends AppCompatActivity {
    SQLDataBase db;
    ListView listView;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new SQLDataBase(this);


        /*PULL FROM SQL HERE*/
        listView = (ListView) findViewById(R.id.classList);
        final List<String> classList= new ArrayList<String>();
        Cursor classCurs = db.getClasses();
        while(classCurs.moveToNext())
        {
            classList.add(classCurs.getString(1));
        }

        /* ARRAY ADAPTER */
        ArrayAdapter aa = new ArrayAdapter<String>(getApplicationContext(),R.layout.whitetext,classList);
        listView.setAdapter(aa);

        /*ARRAY ONCLICK*/
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                public void onItemClick(AdapterView<?> arg0,View arg1, int position, long arg3)
                {

                    Intent n = new Intent(getApplicationContext(), sets.class);
                    n.putExtra("classPosition", position);
                    String atPosition = classList.get(position);
                    startActivity(n);
                }
            });

        /*NEW CLASS BUTTON*/
        ImageButton newClassButton = (ImageButton) findViewById(R.id.newClass);
        newClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, newClass.class);
                //intent.putExtra("database", db);
                startActivity(intent);

            }
        });


    }
    @Override
    public void onResume(){
        super.onResume();


        /*PULL FROM SQL HERE*/
        listView = (ListView) findViewById(R.id.classList);
        final List<String> classList= new ArrayList<String>();
        Cursor classCurs = db.getClasses();
        while(classCurs.moveToNext())
        {
            classList.add(classCurs.getString(1));
        }

        /* ARRAY ADAPTER */
        ArrayAdapter aa = new ArrayAdapter<String>(getApplicationContext(),R.layout.whitetext,classList);
        listView.setAdapter(aa);

        /*ARRAY ONCLICK*/
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> arg0,View arg1, int position, long arg3)
            {

                Intent n = new Intent(getApplicationContext(), sets.class);
                n.putExtra("classPosition", position);
                String atPosition = classList.get(position);
                startActivity(n);
            }
        });

        /*NEW CLASS BUTTON*/
        ImageButton newClassButton = (ImageButton) findViewById(R.id.newClass);
        newClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, newClass.class);
                //intent.putExtra("database", db);
                startActivity(intent);

            }
        });
    }

}
