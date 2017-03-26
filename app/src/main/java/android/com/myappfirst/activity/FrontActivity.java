package android.com.myappfirst.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.com.myappfirst.R;
import android.widget.TextView;

public class FrontActivity extends AppCompatActivity {

    private TextView TxtWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front);

        String fName= getIntent().getStringExtra("firstName");
        String lName= getIntent().getStringExtra("lastName");

        TxtWelcome = (TextView) findViewById(R.id.txt_welcome);
        TxtWelcome.setText("Welcome "+ fName + " " + lName+ "!!");

    }
}
