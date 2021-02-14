package irisi.digitalaube.checkart.userhandler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import irisi.digitalaube.checkart.R;
import irisi.digitalaube.checkart.home.HomeActivity;
import maes.tech.intentanim.CustomIntent;

public class LoginActivity extends Activity {

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

        // TO-DO : Login - load user from database ( If Success redirect to home page )
        // Don't forget Error handling ;)

    }

    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(this, "fadein-to-fadeout");
    }
}