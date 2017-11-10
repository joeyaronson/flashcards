package ucsc.flashcards;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class newClass extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_class);
        ImageButton doneButton = (ImageButton) findViewById(R.id.doneButton);

        //makes text color white when typing
        EditText et = (EditText) findViewById(R.id.enterClass);
        et.setTextColor(Color.parseColor("#FFFFFF"));

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                done();
            }
        });
    }
    public void done()
    {
        EditText classInput = (EditText)findViewById(R.id.enterClass);
        //checks if nothing is inputted
        if(classInput.getText().toString().equals(""))
        {
            Toast.makeText(this,"Please enter a class name.",Toast.LENGTH_LONG).show();
        }
        else
        {
            //PUT LOGIC TO SAVE TEXT HERE
            Toast.makeText(this,"Class Saved.",Toast.LENGTH_LONG).show();
            finish();
        }
    }
}
