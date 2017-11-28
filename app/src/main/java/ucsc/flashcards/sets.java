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

public class sets extends AppCompatActivity {
    SQLDataBase db;
    boolean isEmpty = true;
    boolean deleteMode = false;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sets);
        db = new SQLDataBase(this);

        //pulling position of list from previous activity
        final int classID = getIntent().getExtras().getInt("classPosition");

        //DISPLAYS MESSAGE IF NO CLASSES
        TextView noSet = (TextView)findViewById(R.id.noSets);
        if(!isEmpty)
        {
            noSet.setText("");
        }


        ListView listView = (ListView) findViewById(R.id.setList);
        final List<String> setList= new ArrayList<String>();
        final List<Integer> setIdList= new ArrayList<Integer>();

        Cursor classCurs = db.getChapters(classID);
        while(classCurs.moveToNext())
        {
            isEmpty = false;
            setList.add(classCurs.getString(1));
            setIdList.add(classCurs.getInt(0));

        }

        /* ARRAY ADAPTER */
        final ArrayAdapter aa = new ArrayAdapter<String>(getApplicationContext(),R.layout.whitetext,setList);
        listView.setAdapter(aa);

        /*ARRAY ONCLICK*/
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
            {

                if(!deleteMode)
                {
                    Intent n = new Intent(getApplicationContext(), cards.class);
                    int idPos = setIdList.get(position);
                    n.putExtra("setPosition", idPos);
                    startActivity(n);
                }

                else
                {
                    db.deleteChapter(setIdList.get(position));
                    setList.remove(position);
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

        /*NEW SET BUTTON*/
        ImageButton newSetButton = (ImageButton) findViewById(R.id.newSet);
        newSetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(sets.this, newSet.class);
                isEmpty = false;
                intent.putExtra("classPosition", classID);
                startActivity(intent);
            }
        });

    }
    public void onResume() {
        super.onResume();
        deleteMode = false;

        final int classID = getIntent().getExtras().getInt("classPosition");
        System.out.println("ClassID:"+classID);
        ListView listView = (ListView) findViewById(R.id.setList);
        final List<String> setList = new ArrayList<String>();
        final List<Integer> setIdList = new ArrayList<Integer>();

        //DISPLAYS MESSAGE IF NO CLASSES
        TextView noSet = (TextView)findViewById(R.id.noSets);
        if(!isEmpty)
        {
            noSet.setText("");
        }

        Cursor classCurs = db.getChapters(classID);
        while (classCurs.moveToNext()) {
            isEmpty = false;
            setList.add(classCurs.getString(1));
            setIdList.add(classCurs.getInt(0));

        }

        /* ARRAY ADAPTER */
        final ArrayAdapter aa = new ArrayAdapter<String>(getApplicationContext(), R.layout.whitetext, setList);
        listView.setAdapter(aa);

        /*ARRAY ONCLICK*/
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                if(!deleteMode)
                {
                    Intent n = new Intent(getApplicationContext(), cards.class);
                    int idPos = setIdList.get(position);
                    n.putExtra("setPosition", idPos);
                    startActivity(n);
                }

                else
                {
                    db.deleteChapter(setIdList.get(position));
                    setList.remove(position);
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

        /*NEW SET BUTTON*/
        ImageButton newSetButton = (ImageButton) findViewById(R.id.newSet);
        newSetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(sets.this, newSet.class);
                isEmpty = false;
                intent.putExtra("classPosition", classID);
                startActivity(intent);
            }
        });

    }
}
