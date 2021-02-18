package irisi.digitalaube.checkart.userhandler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

import irisi.digitalaube.checkart.R;
import irisi.digitalaube.checkart.home.HomeActivity;
import maes.tech.intentanim.CustomIntent;

public class LoginActivity extends Activity {

    private String user_email;
    private String user_password;
    private TextView wrong_span;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        // Login as a guest
        Button loginGuest = findViewById(R.id.geuestlogin);
        loginGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
                // Success ( BACK == EXIT )
                finish();
            }
        });

        // Redirect to Register Activity
        TextView register = findViewById(R.id.registrebtn);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                // (Back == Return to LoginActivity )
                CustomIntent.customType(LoginActivity.this, "left-to-right");
            }
        });


        // Redirect to Restore Password Activity
        TextView restore = findViewById(R.id.restorepass);
        restore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RestorePassActivity.class);
                startActivity(intent);
                // (Back == Return to LoginActivity )
                CustomIntent.customType(LoginActivity.this, "left-to-right");
            }
        });


        wrong_span = findViewById(R.id.wrong);
        wrong_span.setVisibility(View.INVISIBLE);

        // User credentials
        final EditText email = findViewById(R.id.email);
        final EditText password = findViewById(R.id.pass);

        // User Login
        Button signin = findViewById(R.id.signinbtn);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                user_email = email.getText().toString();
                user_password = password.getText().toString();


                // TO-DO Login Query


                // Return Integers for example ( -1 == email not found , 0 == success, 1 == password error , 2== ... etc)
                // --> handling errors:

                // ---- demo result  : -----
                int[] results = {-1,0,1,2};
                int status = (int)(Math.random()*results.length);
                // ---- demo result  . -----

                switch (status) {
                    case -1 :
                        wrong_span.setText(getString(R.string.email_not_found));
                        wrong_span.setVisibility(View.VISIBLE);
                        break;
                    case 0 :
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        // in success Load user in memory : ------------------
                        // intent.putExtra("LOGGED_IN_USER" , USER_OBJECT_HERE );
                        // ---------------------------------------------------
                        startActivity(intent);
                        finish();
                        break;
                    case 1 :
                        wrong_span.setText(getString(R.string.incorrect_password));
                        wrong_span.setVisibility(View.VISIBLE);
                        break;
                    case 2 :
                        // do something
                        Toast.makeText(LoginActivity.this,"do something",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        // User login via Google Account :
        Button googleSignin = findViewById(R.id.googleSignin);
        googleSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // TO-DO Google Login Query

                // Don't forget : Load user in memory + error handling

                Toast.makeText(LoginActivity.this,"do something with google",Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(this, "fadein-to-fadeout");
    }
}