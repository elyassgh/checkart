package irisi.digitalaube.checkart.userhandler;

import irisi.digitalaube.checkart.R;
import maes.tech.intentanim.CustomIntent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class RestorePassActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restore_pass);

        //Redirect to login activity
        TextView signIn = findViewById(R.id.signin);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RestorePassActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //Redirect to Register activity
        TextView signUp = findViewById(R.id.signup);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RestorePassActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // TO-DO : Restore Password ( if email was correct sending an email with a temporary password ?! )

    }

    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(this, "right-to-left");
    }

}