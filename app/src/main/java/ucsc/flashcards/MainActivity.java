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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.id;
import static ucsc.flashcards.R.array.classes;

public class MainActivity extends AppCompatActivity {
    SQLDataBase db;
    ListView listView;
    boolean isEmpty = true;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new SQLDataBase(this);


        //DISPLAYS MESSAGE IF NO CLASSES
        TextView noClass = (TextView)findViewById(R.id.noClasses);
        if(!isEmpty)
        {
            noClass.setText("");
        }

        //CLEARS DATABASE
        //db.ClearDatabase();

        /*PULL FROM SQL HERE*/
        listView = (ListView) findViewById(R.id.classList);
        final List<String> classList= new ArrayList<String>();
        final List<Integer> classIdList= new ArrayList<Integer>();

        Cursor classCurs = db.getClasses();
        while(classCurs.moveToNext())
        {
            isEmpty = false;
            classList.add(classCurs.getString(1));
            classIdList.add(classCurs.getInt(0));

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
                int idPos = classIdList.get(position);
                n.putExtra("classPosition", idPos);
                startActivity(n);
            }
            });

        /*NEW CLASS BUTTON*/
        ImageButton newClassButton = (ImageButton) findViewById(R.id.newClass);
        newClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, newClass.class);
                isEmpty = false;
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
        final List<Integer> classIdList= new ArrayList<Integer>();

        //Message if no classes
        TextView noClass = (TextView)findViewById(R.id.noClasses);
        if(!isEmpty)
        {
            noClass.setText("");
        }
        Cursor classCurs = db.getClasses();
        while(classCurs.moveToNext())
        {
            isEmpty = false;
            classList.add(classCurs.getString(1));
            classIdList.add(classCurs.getInt(0));
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
                int idPos = classIdList.get(position);
                n.putExtra("classPosition", idPos);
                startActivity(n);
            }
        });

        /*NEW CLASS BUTTON*/
        ImageButton newClassButton = (ImageButton) findViewById(R.id.newClass);
        newClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, newClass.class);
                isEmpty = false;
                startActivity(intent);

            }
        });
    }





}
