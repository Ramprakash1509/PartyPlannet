package com.app.partyplanet.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.app.partyplanet.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity implements View.OnClickListener {
    private TextView signup, tv_Google,email;
    public GoogleSignInClient mGoogleSignInClient;
    public GoogleSignInOptions gso;
    private static int SIGN_IN = 100;
    public static String Email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        GetId();
    }

    private void GetId() {
        signup = findViewById(R.id.signup);
        tv_Google = findViewById(R.id.tv_Google);
        email=findViewById(R.id.email);
        email.setOnClickListener(this);
        tv_Google.setOnClickListener(this);
        signup.setOnClickListener(this);

      /*  gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
*/

    }

    @Override
    public void onClick(View v)
    {
        if (v == tv_Google)
        {
            signIn();
        }
        if (v == signup) {
            startActivity(new Intent(Login.this, SignUp.class).putExtra("type",1));//type 1 for Register user
        }
        if (v==email)
        {
        startActivity(new Intent(Login.this,SignInEmail.class));
        }

    }

    private void signIn() {
        Intent SigninIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(SigninIntent, SIGN_IN);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                task.getResult(ApiException.class);
              startActivity(new Intent(Login.this,SignUp.class));
            } catch (ApiException e) {
                Toast.makeText(this, "sdmnjv" + e, Toast.LENGTH_SHORT).show();
            }
        }
    }

  /*  public void getData()
    {

    GoogleSignInAccount acctount = GoogleSignIn.getLastSignedInAccount(this);
            if(acctount!=null)

    {
        String personName = acctount.getDisplayName();
        String personGivenName = acctount.getGivenName();
        String personFamilyName = acctount.getFamilyName();
        String personEmail = acctount.getEmail();
        String personId = acctount.getId();
        Uri personPhoto = acctount.getPhotoUrl();
        Toast.makeText(this, "User Email" + personEmail, Toast.LENGTH_SHORT).show();
}
}*/
}