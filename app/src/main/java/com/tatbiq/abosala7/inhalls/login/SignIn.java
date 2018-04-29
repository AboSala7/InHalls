package com.tatbiq.abosala7.inhalls.login;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.tatbiq.abosala7.inhalls.HallsActivity;
import com.tatbiq.abosala7.inhalls.R;
import com.tatbiq.abosala7.inhalls.registerationDb.SqliteHelper;
import com.tatbiq.abosala7.inhalls.registerationDb.User;

import java.util.Arrays;

public class SignIn extends AppCompatActivity {

    EditText editTextName,editTextPhone;
    Button buttonSignIn,buttonSignUp;
    Intent intent;
    SqliteHelper sqliteHelper;
    Context context;
    CallbackManager callbackManager;
    User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        initViews();

        sqliteHelper = new SqliteHelper(this);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void register(){
        if (!sqliteHelper.checkUser(editTextPhone.getText().toString().trim())) {
            user = new User();
            user.setUserName(editTextName.getText().toString().trim());
            user.setPhone(editTextPhone.getText().toString().trim());
            sqliteHelper.addUser(user);
            Toast.makeText(getApplicationContext(),"تم التسجيل بنجاح",Toast.LENGTH_SHORT).show();
            intent = new Intent(SignIn.this,HallsActivity.class);
            startActivity(intent);
        } else {

            Toast.makeText(getApplicationContext(),"خطأ في التسجيل",Toast.LENGTH_SHORT).show();
        }

    }


    public void addFb(){
        callbackManager = CallbackManager.Factory.create();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
    }
    public void initViews(){
        editTextName = (EditText)findViewById(R.id.name_signin);
        editTextPhone =(EditText)findViewById(R.id.phone_signin);
        buttonSignIn =(Button)findViewById(R.id.signin_signin);
        buttonSignUp =(Button)findViewById(R.id.signup_signin);
    }
    //This method is used to validate input given by user
    public boolean validate() {
        boolean valid = true;

        //Get values from EditText fields
        String phone = editTextPhone.getText().toString();
        String Name = editTextName.getText().toString();

        //Handling validation for Email field
        if (!Patterns.PHONE.matcher(phone).matches()) {
            valid = false;
            Toast.makeText(getApplicationContext(),"الرقم الذي أدخلته غير صحيح", Toast.LENGTH_SHORT).show();
        }

        //Handling validation for Password field
        if (Name.isEmpty()) {
            valid = false;
            Toast.makeText(getApplicationContext(),"الرجاء إدخال الإسم", Toast.LENGTH_SHORT).show();
        }
        return valid;
    }

    public void login (){

        if (sqliteHelper.checkUser(editTextName.getText().toString().trim()
                ,editTextPhone.getText().toString().trim())) {

            Intent intent = new Intent(getApplicationContext(),HallsActivity.class);
            startActivity(intent);

        } else {
            // Snack Bar to show success message that record is wrong
            Toast.makeText(getApplicationContext(),"الحساب الذي أدخلته غير صحيح", Toast.LENGTH_SHORT).show();
        }

    }

    public void fbLogin(){


        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        Toast.makeText(getApplicationContext(),"fb login success",Toast.LENGTH_SHORT).show();

                        intent = new Intent(SignIn.this,HallsActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        Toast.makeText(getApplicationContext(),"fb login fail",Toast.LENGTH_SHORT).show();
                    }
                });

    }

    public void click(View view){
        switch (view.getId()) {
            //signin button click
            case R.id.signin_signin:
                if(validate()) {
                    register();
                }
                else {
                    Toast.makeText(getApplicationContext(), "غير قادر على التسجيل", Toast.LENGTH_SHORT).show();
                }
                break;

            //signUp button click
            case R.id.signup_signin:
                intent = new Intent(SignIn.this, SignUp.class);
                startActivity(intent);
                break;

            case R.id.faceb_signin:
                fbLogin();
                break;
        }
    }
}
