package ucsc.flashcards;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

import static android.R.attr.id;
import static android.view.Gravity.BOTTOM;
import static ucsc.flashcards.R.array.classes;

public class MainActivity extends AppCompatActivity {
    SQLDataBase db;
    ListView listView;
    boolean isEmpty = true;
    boolean deleteMode = false;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new SQLDataBase(this);
        deleteMode = false;


        //DISPLAYS MESSAGE IF NO CLASSES
        final TextView noClass = (TextView)findViewById(R.id.noClasses);
        if(!isEmpty)
        {
            noClass.setText("");
        }

        //CLEARS DATABASE
        db.ClearDatabase();

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
        final ArrayAdapter aa = new ArrayAdapter<String>(getApplicationContext(),R.layout.whitetext,classList);
        listView.setAdapter(aa);

        /*ARRAY ONCLICK*/
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                public void onItemClick(AdapterView<?> arg0,View arg1, int position, long arg3)
                {

                if(!deleteMode)
                {
                    Intent n = new Intent(getApplicationContext(), sets.class);
                    int idPos = classIdList.get(position);
                    n.putExtra("classPosition", idPos);
                    startActivity(n);
                }
                else
                {
                    db.deleteClass(classIdList.get(position));
                    classList.remove(position);
                    aa.notifyDataSetChanged();
                    if(classList.isEmpty())
                    {
                        noClass.setText("No classes found, press the + button to create a class.");
                    }
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
        deleteMode = false;


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
        final ArrayAdapter aa = new ArrayAdapter<String>(getApplicationContext(),R.layout.whitetext,classList);
        listView.setAdapter(aa);

        /*ARRAY ONCLICK*/
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> arg0,View arg1, int position, long arg3)
            {

                if(!deleteMode)
                {
                    Intent n = new Intent(getApplicationContext(), sets.class);
                    int idPos = classIdList.get(position);
                    n.putExtra("classPosition", idPos);
                    startActivity(n);
                }
                else
                {
                    db.deleteClass(classIdList.get(position));
                    classList.remove(position);
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
