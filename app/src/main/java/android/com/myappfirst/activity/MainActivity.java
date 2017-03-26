package android.com.myappfirst.activity;

import android.app.ProgressDialog;
import android.com.myappfirst.R;
import android.com.myappfirst.activity.utils.ToastUtils;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private EditText edtFirstName, edtLastName, edtPhone, edtEmail, edtAddress;
    private Button btnSubmit, btnCancel;
    private String firstName, lastName, email, address, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

    }

    private void initView() {
        edtFirstName = (EditText) findViewById(R.id.edt_first_name);
        edtLastName = (EditText) findViewById(R.id.edt_last_name);
        edtPhone = (EditText) findViewById(R.id.edt_phone);
        edtEmail = (EditText) findViewById(R.id.edt_email_address);
        edtAddress = (EditText) findViewById(R.id.edt_address);
        btnSubmit = (Button) findViewById(R.id.btn_submit);
        btnCancel = (Button) findViewById(R.id.btn_cancel);

        /*  //if not much buttons on view can use this
         btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                formSubmitOnClick(v);
            }
        }); */

        btnSubmit.setOnClickListener(this);  //to use this need to implement view.Listener and override method onClick
        btnCancel.setOnClickListener(this);
    }

    //function call frm view on btn click
    public void formSubmitOnClick(View v){
        Toast.makeText(MainActivity.this, "Hello world!!!", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btn_submit:
                signUp();
                break;
            case R.id.btn_cancel:
                resetFormFields();
                break;
        }
    }

    private void signUp(){

        if( !validate() ){
            onSignUpFailed();
            return;
        }

        //if validate success show progress dialog for few time
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this, R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        onSignUpSuccess();
        progressDialog.dismiss();

    }

    public boolean validate(){
        boolean valid = true;

        firstName = edtFirstName.getText().toString().trim();
        lastName = edtLastName.getText().toString().trim();
        phone = edtPhone.getText().toString().trim();
        email = edtEmail.getText().toString().trim();
        address = edtAddress.getText().toString().trim();

        if ( TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName) || TextUtils.getTrimmedLength(lastName) < 3 || TextUtils.getTrimmedLength(firstName) < 3 ) {  //TextUtils is already build in class to chk for text validation
            ToastUtils.showToast(MainActivity.this, "Name must have at least 3 characters." , false);
            valid = false;
        }

        else if (TextUtils.isEmpty(email) || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            ToastUtils.showToast(MainActivity.this, "Enter valid Email Address." , false);
            valid = false;
        }

        else if (TextUtils.isEmpty(phone) || TextUtils.getTrimmedLength(phone) != 10) {
            ToastUtils.showToast(MainActivity.this, "Enter valid Phone Number." , false);
            valid = false;
        }

        return valid;
    }

    private void onSignUpSuccess(){
        firstName = edtFirstName.getText().toString().trim();
        lastName = edtLastName.getText().toString().trim();

        ToastUtils.showToast(MainActivity.this, "Successful" , true);
        Intent i = new Intent(getApplicationContext(), FrontActivity.class);
        i.putExtra("firstName", firstName);
        i.putExtra("lastName", lastName);
        startActivity(i);
        setContentView(R.layout.activity_front);
    }

    private void onSignUpFailed(){
        ToastUtils.showToast(MainActivity.this, "Sign Up Failed", true);
    }


    private void resetFormFields(){
        edtFirstName.setText("");
        edtLastName.setText("");
        edtEmail.setText("");
        edtPhone.setText("");
        edtAddress.setText("");
    }
}
