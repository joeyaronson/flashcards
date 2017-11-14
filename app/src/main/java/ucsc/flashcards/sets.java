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

public class sets extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sets);

        //pulling position of list from previous activity
        int classPos = getIntent().getExtras().getInt("classPosition");

        /*PULL FROM SQL HERE*/
        ListView listView = (ListView) findViewById(R.id.setList);
        List<String> setList= new ArrayList<String>();
        setList.add("ch1");
        setList.add("ch2");
        setList.add("ch3");

        /* ARRAY ADAPTER */
        ArrayAdapter aa = new ArrayAdapter<String>(getApplicationContext(),R.layout.whitetext,setList);
        listView.setAdapter(aa);

        /*ARRAY ONCLICK*/
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
            {

                Intent n = new Intent(getApplicationContext(), cards.class);
                n.putExtra("setPosition", position);
                startActivity(n);
            }
        });

        /*NEW SET BUTTON*/
        ImageButton newSetButton = (ImageButton) findViewById(R.id.newSet);
        newSetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(sets.this, newSet.class);
                startActivity(intent);
            }
        });

    }
}
