package hu.uniobuda.nik.guideme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends Activity {
    Button btn_create, btn_login;
    EditText editName, editPassword;
    DatabaseHelperUser helper;
    public static DatabaseHelperMonument dbh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btn_tourist = (Button)findViewById(R.id.btn_tourist);
        final Intent touristActivity = new Intent(this,MainActivity.class);
        helper = new DatabaseHelperUser(this);

        editName =(EditText) findViewById(R.id.userNameBox);
        editPassword =(EditText) findViewById(R.id.passwordBox);

        btn_create =(Button) findViewById(R.id.btn_newuser);
        btn_login = (Button) findViewById(R.id.btn_login);

        final Intent userActivity = new Intent(this, SiqnUp.class);
        final Intent displayActivity = new Intent(this, Display.class);

        //Create new dataBase object
        dbh = new DatabaseHelperMonument(LoginActivity.this);

        btn_tourist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(touristActivity);
            }
        });

        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(userActivity);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(helper.Search(editName.getText().toString()).equals(editPassword.getText().toString()))
                {
                    displayActivity.putExtra("UserName", editName.getText().toString());
                    startActivity(displayActivity);
                }
                else{
                    Toast.makeText(LoginActivity.this,"Username or password not correct !", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}

