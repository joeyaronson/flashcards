package ucsc.flashcards;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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

public class newClass extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_class);
        ImageButton doneButton = (ImageButton) findViewById(R.id.doneButton);

        //makes text color white when typing
        EditText et = (EditText) findViewById(R.id.enterClass);
        et.setTextColor(Color.parseColor("#FFFFFF"));

        // Make new database to write to
        SQLDataBase db = new SQLDataBase(this);

        /*EXITS ACTIVITY WHEN CLICKED*/
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
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.error_toast, (ViewGroup) findViewById(R.id.error_toast));

            TextView text = (TextView) layout.findViewById(R.id.text);
            text.setText("Please enter a class name.");

            Toast toast = new Toast(getApplicationContext());
            toast.setGravity(Gravity.CENTER|BOTTOM, 0, 150);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(layout);
            toast.show();
        }

        /*RUNS IF SOMETHING IS INPUTTED*/
        else
        {
            //TODO PUT LOGIC TO SAVE TEXT HERE
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.save_toast, (ViewGroup) findViewById(R.id.custom_toast_container));

            TextView text = (TextView) layout.findViewById(R.id.text);
            text.setText("Class Saved.");
            Toast toast = new Toast(getApplicationContext());
            toast.setGravity(Gravity.CENTER|BOTTOM, 0, 150);
            toast.setDuration(Toast.LENGTH_LONG);

            toast.setView(layout);
            toast.show();
            finish();
        }
    }
}
