package android.com.myappfirst.activity;

import android.com.myappfirst.activity.utils.Constants;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.com.myappfirst.R;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ShowUserType extends AppCompatActivity {

    private String user_type = "";
    private String user_course = "";
    private boolean forResult = false;

    TextView txtShowPassValue;
    Button btnPassValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_user_type);

        initializeViews();

        Intent intent = getIntent();
        if( intent != null ){
            user_type = intent.getStringExtra(Constants.USER_TYPE);
            user_course = intent.getStringExtra(Constants.COURSE_RELATED_TO);

            txtShowPassValue.setText("The user is of type " + user_type + "and relates to course "+ user_course);

            forResult = intent.getBooleanExtra(Constants.FOR_TYPE_ACTIVITY_RESULT, false);
            if( forResult){
                btnPassValue.setVisibility(View.VISIBLE);
                txtShowPassValue.setVisibility(View.GONE);
            }

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, FrontActivity.class));
        finish();
    }

    private void initializeViews() {
        txtShowPassValue = (TextView) findViewById(R.id.txt_show_passed_value);
        btnPassValue = (Button) findViewById(R.id.btn_pass_value);
    }

    //Return an value to {@link FrontActivity}
    public void  passUserTypeToParentActivity(View v){
        Intent i = new Intent();
        i.putExtra(Constants.USER_TYPE, "Passed from called UserType - Activity for startActivity for Result- Front");
        setResult(2, i);
        finish();
    }
}
