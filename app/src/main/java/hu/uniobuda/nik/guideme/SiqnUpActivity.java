package hu.uniobuda.nik.guideme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Tamás on 2017. 04. 22..
 */

public class SiqnUp extends Activity {
    EditText editName, editEmail, editUserName, editPassword;
    Button signupButton;
    DatabaseHelperUser helper = new DatabaseHelperUser(this);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        editName =(EditText) findViewById(R.id.nameText);
        editEmail =(EditText) findViewById(R.id.emailText);
        editUserName =(EditText) findViewById(R.id.usernameText);
        editPassword =(EditText) findViewById(R.id.passwordText);
        signupButton =(Button) findViewById(R.id.signupButton);


        final Intent loginActivity = new Intent(this, LoginActivity.class);



        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                if(!(editName.getText().toString().equals("") ||
                        editEmail.getText().toString().equals("") ||
                        editUserName.getText().toString().equals("") ||
                        editPassword.getText().toString().equals("")))
                {
                    user.setName(editName.getText().toString());
                    user.setEmail(editEmail.getText().toString());
                    user.setUsername(editUserName.getText().toString());
                    user.setPassword(editPassword.getText().toString());

                    if(isValidEmail(editEmail.getText().toString()))
                    {
                        if(helper.CheckUserName(user.getUsername()) == 1){
                            helper.insertUser(user);
                            Toast.makeText(SiqnUp.this,"User Created!", Toast.LENGTH_LONG).show();

                            //displayActivity.putExtra("UserName", user.getUsername());
                            startActivity(loginActivity);
                        }
                        else{
                            Toast.makeText(SiqnUp.this,"This username still exists!", Toast.LENGTH_LONG).show();
                        }
                    }
                    else{
                        Toast.makeText(SiqnUp.this,"Please add valid email!", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(SiqnUp.this,"Please fill all fields!", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}
