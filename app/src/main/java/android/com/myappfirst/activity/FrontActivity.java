package android.com.myappfirst.activity;

import android.Manifest;
import android.com.myappfirst.activity.utils.Constants;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.com.myappfirst.R;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class FrontActivity extends AppCompatActivity {

    private static final String TAG = FrontActivity.class.getSimpleName();
    private static final int RESULT_CODE = 2;
    private LinearLayout llLayoutPassIntent;
    private TextView txtShowWelcome, txtShowChildPass;
    private EditText edtUserType;
    private Spinner spinnerUserCourse;
    //Button btnMakeCall, btnSendEmail, btnLaunchBrowser, btnShowLocation;
    private String fNameString, lNameString, phoneNumberString, emailString, addressString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front);

        // initialize TextView
        initializeViews();

        //getting intent that started this activity
        Intent intent = getIntent();

        if (intent != null) {
            //if not used bundle you can simply take intent value
            //String fName = getIntent().getStringExtra(Constants.FIRST_NAME);
            //String lName = getIntent().getStringExtra(Constants.LAST_NAME);

            //get the Bundle that stores the data of this Activity
            Bundle b = intent.getExtras();

            // getting data from bundle
            fNameString = b.getString(Constants.FIRST_NAME);
            lNameString = b.getString(Constants.LAST_NAME);
            long phoneNumberLong = b.getLong(Constants.PHONE_NUM);
            phoneNumberString = Long.toString(phoneNumberLong);
            emailString = b.getString(Constants.PHONE_NUM);
            addressString = b.getString(Constants.ADDRESS);

            // show data to layout
            txtShowWelcome.setText("Welcome "+ fNameString + " " + lNameString+ "!!!!");
        }

    }

    private void initializeViews(){
        llLayoutPassIntent = (LinearLayout) findViewById(R.id.ll_layout_pass_value);
        txtShowWelcome   = (TextView) findViewById(R.id.txt_welcome);
        // btnMakeCall      = (Button) findViewById(R.id.btn_make_call);
        // btnSendEmail     = (Button) findViewById(R.id.btn_send_email);
        // btnLaunchBrowser = (Button) findViewById(R.id.btn_launch_browser);
        // btnShowLocation  = (Button) findViewById(R.id.btn_show_location);

        edtUserType = (EditText) findViewById(R.id.edt_user_type);
        spinnerUserCourse = (Spinner) findViewById(R.id.spin_user_course);

        txtShowChildPass = (TextView) findViewById(R.id.txt_show_pass_by_child_actv);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity( new Intent(this, MainActivity.class ));
        finish();
    }

    // make a phone call on button click //must set permission for call
    public void makeCall(View v){
        // For api 23+ you have to check for permissions runtime
        if (FrontActivity.this.checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + phoneNumberString));
            startActivity(callIntent);
        }
    }

    public void sendEmail(View v){
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("plain/Text");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{emailString} );
        i.putExtra(Intent.EXTRA_SUBJECT, "My Subject");
        i.putExtra(Intent.EXTRA_TEXT, "My Mail Body");
        startActivity(Intent.createChooser(i, ""));
    }

    public void launchBrowser(View v){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com/android"));
        startActivity(browserIntent);
    }

    public void showLocation(View v){
        //if used lat and long for map
        //double latitude = 40.714728;
        //double longitude = -73.998672;
        //String label = "MAP Label";
        //String uriBegin = "geo:" + latitude + "," + longitude;
        //String query = latitude + "," + longitude + "(" + label + ")";
        //String encodedQuery = Uri.encode(query);
        //String uriString = uriBegin + "?q=" + encodedQuery + "&z=16";

        // for location text eg. "geo:0,0?q=1600+Amphitheatre+Parkway%2C+CA"
        // take address from form value
        String address = Uri.encode(addressString);
        String uriString = "geo:0,0" + "?q=1600+" + address;

        Uri uri = Uri.parse(uriString);
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    //just starting new activity
    public void calculateUserAge(View v){
        startActivity(new Intent(this, CalculateUserAge.class));
        //if we put this finish then after backpress frm CalculateUserAge activity you will be directly redirect to MainActivity(form)
        finish(); // if we use finish() then app not return back to this activity again
    }

    // example of passing intent to activity
    public void checkUserType(View v){
        llLayoutPassIntent.setVisibility(View.VISIBLE);
    }

    public void getUserTypeResult(View v) {
        String user_type = edtUserType.getText().toString().trim();
        String user_course = String.valueOf(spinnerUserCourse.getSelectedItem());

        if (TextUtils.isEmpty(user_type)) {
            edtUserType.setError("Cannot be empty");
        } else {
            //Toast.makeText(FrontActivity.this, user_type + user_course , Toast.LENGTH_SHORT).show();
            Intent passUserTypeValue = new Intent(this, ShowUserType.class);
            passUserTypeValue.putExtra(Constants.USER_TYPE, user_type);
            passUserTypeValue.putExtra(Constants.COURSE_RELATED_TO, user_course);
            startActivity(passUserTypeValue);
            //finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "onActivityResult: " + requestCode + "::" + resultCode);
        // check if the request code is same as what is passed  here it is 2
        if (resultCode == RESULT_CODE) {
            txtShowChildPass.setVisibility(View.VISIBLE);
            String message = data.getStringExtra(Constants.USER_TYPE);
            txtShowChildPass.setText(message);
        } else {
            txtShowChildPass.setVisibility(View.VISIBLE);
            txtShowChildPass.setText("No value");
        }

    }

    /**
     * Showcase Intent can be used to wait for an result if needed
     * {@link FrontActivity} is started and waits for result from it.
     */

    public void startNewActivityForResult(View v) {
        Intent passIntentValue = new Intent(this, ShowUserType.class);
        passIntentValue.putExtra(Constants.FOR_TYPE_ACTIVITY_RESULT, true);
        startActivityForResult(passIntentValue, RESULT_CODE);

    }


}
