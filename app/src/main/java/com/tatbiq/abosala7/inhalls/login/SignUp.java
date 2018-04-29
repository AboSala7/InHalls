package com.tatbiq.abosala7.inhalls.login;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tatbiq.abosala7.inhalls.R;
import com.tatbiq.abosala7.inhalls.registerationDb.SqliteHelper;
import com.tatbiq.abosala7.inhalls.registerationDb.User;

public class SignUp extends AppCompatActivity {

    EditText editTextName,editTextPhone,editTextEmail;
    TextView textViewMember;
    Button buttonSubmit;
    Intent intent;
    SqliteHelper sqliteHelper;
    Context context;
     User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initViews();
        sqliteHelper = new SqliteHelper(this);


    }

    public void initViews(){
        editTextName =(EditText)findViewById(R.id.name_signup);
        editTextEmail =(EditText)findViewById(R.id.email_signup);
        editTextPhone =(EditText)findViewById(R.id.phone_signup);
        textViewMember =(TextView)findViewById(R.id.member_signup);
        buttonSubmit = (Button)findViewById(R.id.submit_signup);
    }

    public void click(View view){
        switch (view.getId()){

            //submit button click
            case R.id.submit_signup:
               //  register();
                 break;

            //already member text click
            case R.id.member_signup:
                intent = new Intent(SignUp.this,SignIn.class);
                startActivity(intent);
                break;
         }
    }


    public boolean validate() {
        boolean valid = false;

        //Get values from EditText fields
        String UserName = editTextName.getText().toString();
        String Email = editTextEmail.getText().toString();
        String Phone = editTextPhone.getText().toString();

        //Handling validation for UserName field
        if (UserName.isEmpty()) {
            valid = false;
           Toast.makeText(getApplicationContext(),"please enter your userName",Toast.LENGTH_LONG).show();
        }

        //Handling validation for Email field
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            valid = false;
            Toast.makeText(getApplicationContext(),"please enter a valid email",Toast.LENGTH_LONG).show();
        } else {
            valid = true;
        }

        //Handling validation for Phone field
        if (Phone.isEmpty()) {
            valid = false;
            Toast.makeText(getApplicationContext(),"please enter your phone",Toast.LENGTH_LONG).show();
        } else {
            if (!Patterns.PHONE.matcher(Phone).matches()) {
                valid = false;
                Toast.makeText(getApplicationContext(),"please enter a valid phone",Toast.LENGTH_LONG).show();
            }
        }


        return valid;
    }
}
