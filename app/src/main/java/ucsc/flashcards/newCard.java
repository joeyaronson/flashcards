package ucsc.flashcards;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class newCard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_card);

        //makes text color white when typing
        EditText et = (EditText) findViewById(R.id.enterFront);
        et.setTextColor(Color.parseColor("#FFFFFF"));
        EditText et2 = (EditText) findViewById(R.id.enterBack);
        et2.setTextColor(Color.parseColor("#FFFFFF"));

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
            Toast.makeText(this,"Please enter front and back of card.",Toast.LENGTH_LONG).show();
        }
        else if(frontInput.getText().toString().equals(""))
        {
            Toast.makeText(this,"Please enter front of card.",Toast.LENGTH_LONG).show();
        }
        else if(backInput.getText().toString().equals(""))
        {
            Toast.makeText(this,"Please enter back of card.",Toast.LENGTH_LONG).show();
        }
        else
        {
            //PUT LOGIC TO SAVE TEXT HERE
            Toast.makeText(this,"Card Saved.",Toast.LENGTH_LONG).show();
            finish();
        }
    }
}
