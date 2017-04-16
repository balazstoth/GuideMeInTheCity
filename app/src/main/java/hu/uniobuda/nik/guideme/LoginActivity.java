package hu.uniobuda.nik.guideme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btn_tourist = (Button)findViewById(R.id.btn_tourist);
        final Intent touristActivity = new Intent(this,MainActivity.class);

        btn_tourist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(touristActivity);
            }
        });
    }
}

