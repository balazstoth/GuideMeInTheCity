package hu.uniobuda.nik.guideme;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Tamás on 2017. 04. 22..
 */

public class Display extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display);
        String username = getIntent().getStringExtra("UserName");

        TextView tv =(TextView) findViewById(R.id.userName);
        tv.setText(username);
    }
}
