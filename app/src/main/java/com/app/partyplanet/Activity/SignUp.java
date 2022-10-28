package com.app.partyplanet.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.partyplanet.R;
import com.app.partyplanet.Utils.Loader;
import com.app.partyplanet.Utils.UtilsMethod;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

public class SignUp extends AppCompatActivity implements View.OnClickListener {
    private EditText fname,lname,email,password,cpassword;
    private  TextView btnsignin;
    private ImageView backprecc;
    public GoogleSignInClient mGoogleSignInClient;
    public GoogleSignInOptions gso;
    private static int SIGN_IN = 100;
    Loader loader;
  /*  String  type=getIntent().getStringExtra("type");*/


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        GetId();
      /*  if (type.equalsIgnoreCase("1"))
        {

        }
        else if (type.equalsIgnoreCase("2"))
        {

        }*/

        /*gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount account=GoogleSignIn.getLastSignedInAccount(this);

        if(account!=null)
        {
            String name=account.getDisplayName();
            String email=account.getEmail();
            String url= String.valueOf(account.getPhotoUrl());
            Log.d("","dfbjhbf"+name);
            Log.d("","email"+email);
            Log.d("","url"+url);
        }
*/

    }

    private void GetId()
    {   backprecc=findViewById(R.id.backprecc);
        btnsignin=findViewById(R.id.btnsignin);
        fname=findViewById(R.id.fname);
        lname=findViewById(R.id.lname);
        email=findViewById(R.id.user_id);
        password=findViewById(R.id.password);
        cpassword=findViewById(R.id.cpassword);
        loader = new Loader(this, android.R.style.Theme_Translucent_NoTitleBar);
        btnsignin.setOnClickListener(this);
        backprecc.setOnClickListener(this);


    }

    @Override
    public void onClick(View v)
    {
        if (v==btnsignin)
        {
          signIn();
        }
        if (v==backprecc)
        {
            finish();
        }
    }



    private void signIn()
    {
        if (fname.getText().toString().isEmpty()) {
            fname.setError("Enter first Name");
            fname.requestFocus();
        }
        if (lname.getText().toString().isEmpty()) {
            lname.setError("Enter last Name ");
            lname.requestFocus();
        }
        if (email.getText().toString().isEmpty()) {
            email.setError(" Enter Email");
            email.requestFocus();
        }
        if (password.getText().toString().isEmpty())
        {
            password.setError("Enter password");
            password.requestFocus();
        }
        if (cpassword.getText().toString().isEmpty())
        {
            cpassword.setError("Enter confirms password");
            cpassword.requestFocus();
        }
        else if (!password.getText().toString().equals(cpassword.getText().toString())) {
            cpassword.setError("Confirm password does not match");
            cpassword.requestFocus();
        }else if (UtilsMethod.INSTANCE.isValidPassword(password.getText().toString())==false)
            {
               password.setError("Enter at least one special character , lower case , upper case and digit ");
               password.requestFocus();
            }
        else if (UtilsMethod.INSTANCE.isValidEmail(email.getText().toString()) == false)
        {
            //Toast.makeText(this, "Please Enter valid E-mail Id", Toast.LENGTH_SHORT).show();
                 email.setError("Please Enter valid E-mail Id");
                 email.requestFocus();
        }
        else if (UtilsMethod.INSTANCE.isNetworkAvialable(this) == false)
        {
            Toast.makeText(this, "Please Check Your Network Connectivity", Toast.LENGTH_SHORT).show();
        }
        else
        {
          loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);
            UtilsMethod.INSTANCE.createAccount(SignUp.this,loader, fname.getText().toString(),lname.getText().toString(),email.getText().toString(), password.getText().toString(), cpassword.getText().toString());


        }

    }
}