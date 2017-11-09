package ucsc.flashcards;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class newSet extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_set);

        //makes text color white when typing
        EditText et = (EditText) findViewById(R.id.enterSet);
        et.setTextColor(Color.parseColor("#FFFFFF"));

        ImageButton doneButton = (ImageButton) findViewById(R.id.doneButton);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                done();
                finish();
            }
        });
    }
    public void done()
    {
        Toast.makeText(this,"Set Saved.",Toast.LENGTH_LONG).show();
    }
}
