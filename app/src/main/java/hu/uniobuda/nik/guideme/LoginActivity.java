package hu.uniobuda.nik.guideme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class LoginActivity extends Activity  {
    Button btn_create, btn_login;
    EditText editName, editPassword;
    DatabaseHelperUser helper;
    TextView textUsername, textPassword;

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

        textUsername =(TextView) findViewById(R.id.textView_userName);
        textPassword =(TextView) findViewById(R.id.textView_password);

        final Intent userActivity = new Intent(this, SiqnUpActivity.class);
        final Intent displayActivity = new Intent(this, DisplayActivity.class);

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
                if (!editName.getText().toString().matches("") && !editPassword.getText().toString().matches("")){
                    if(helper.Search(editName.getText().toString()).equals(editPassword.getText().toString()))
                    {
                        displayActivity.putExtra("UserName", editName.getText().toString());


                        editName.setVisibility(View.GONE);
                        textUsername.setVisibility(View.GONE);
                        editPassword.setVisibility(View.GONE);
                        textPassword.setVisibility(View.GONE);
                        btn_login.setVisibility(View.GONE);
                        btn_create.setVisibility(View.GONE);
                        startActivity(displayActivity);
                    }
                    else{
                        Toast.makeText(LoginActivity.this,"Username or password not correct !", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(LoginActivity.this,"Please fill username and password fields!", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}

