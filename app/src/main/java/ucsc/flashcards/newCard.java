package ucsc.flashcards;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import static android.view.Gravity.BOTTOM;

public class newCard extends AppCompatActivity {
    SQLDataBase db;
    int setID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_card);
        db = new SQLDataBase(this);
        setID = getIntent().getExtras().getInt("setPostion");
        //makes text color white when typing
        EditText et = (EditText) findViewById(R.id.enterFront);
        et.setTextColor(Color.parseColor("#FFFFFF"));
        EditText et2 = (EditText) findViewById(R.id.enterBack);
        et2.setTextColor(Color.parseColor("#FFFFFF"));

        /*EXITS ACTIVITY WHEN CLICKED*/
        ImageButton doneButton = (ImageButton) findViewById(R.id.doneButton);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                done();
            }
        });
    }
    public void done()
    {
        EditText frontInput = (EditText)findViewById(R.id.enterFront);
        EditText backInput = (EditText)findViewById(R.id.enterBack);
        //checks if nothing is inputted
        if(frontInput.getText().toString().equals("")&& backInput.getText().toString().equals(""))
        {
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.error_toast, (ViewGroup) findViewById(R.id.error_toast));

            TextView text = (TextView) layout.findViewById(R.id.text);
            text.setText("Please enter front and back of card.");

            Toast toast = new Toast(getApplicationContext());
            toast.setGravity(Gravity.CENTER|BOTTOM, 0, 150);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(layout);
            toast.show();
            //Toast.makeText(this,"Please enter front and back of card.",Toast.LENGTH_LONG).show();
        }
        else if(frontInput.getText().toString().equals(""))
        {
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.error_toast, (ViewGroup) findViewById(R.id.error_toast));

            TextView text = (TextView) layout.findViewById(R.id.text);
            text.setText("Please enter front of card.");

            Toast toast = new Toast(getApplicationContext());
            toast.setGravity(Gravity.CENTER|BOTTOM, 0, 150);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(layout);
            toast.show();
            //Toast.makeText(this,"Please enter front of card.",Toast.LENGTH_LONG).show();
        }
        else if(backInput.getText().toString().equals(""))
        {
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.error_toast, (ViewGroup) findViewById(R.id.error_toast));

            TextView text = (TextView) layout.findViewById(R.id.text);
            text.setText("Please enter back of card.");

            Toast toast = new Toast(getApplicationContext());
            toast.setGravity(Gravity.CENTER|BOTTOM, 0, 150);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(layout);
            toast.show();
            //Toast.makeText(this,"Please enter back of card.",Toast.LENGTH_LONG).show();
        }

        /*RUNS IF SOMETHING IS INPUTTED*/
        else
        {
            //TODO PUT LOGIC TO SAVE TEXT HERE

            EditText getFront = (EditText)findViewById(R.id.enterFront);
            String frontName = getFront.getText().toString();

            EditText getBack = (EditText)findViewById(R.id.enterBack);
            String backName = getBack.getText().toString();

            boolean isAdded = db.insertCard(frontName,backName,0);//setID);

            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.save_toast, (ViewGroup) findViewById(R.id.custom_toast_container));

            TextView text = (TextView) layout.findViewById(R.id.text);
            text.setText("Card Saved.");

            Toast toast = new Toast(getApplicationContext());
            toast.setGravity(Gravity.CENTER|BOTTOM, 0, 150);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(layout);
            toast.show();
            //Toast.makeText(this,"Card Saved.",Toast.LENGTH_LONG).show();
            finish();
        }
    }
}
