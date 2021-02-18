package irisi.digitalaube.checkart.userhandler;

import irisi.digitalaube.checkart.R;
import irisi.digitalaube.checkart.home.HomeActivity;
import maes.tech.intentanim.CustomIntent;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RestorePassActivity extends Activity {

    private String user_name;
    private String user_email;
    private TextView wrong_span;

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

        wrong_span = findViewById(R.id.wrong);
        wrong_span.setVisibility(View.INVISIBLE);

        // User credentials
        final EditText name = findViewById(R.id.user_name);
        final EditText email = findViewById(R.id.email);

        // Restore Password ( if email & username was correct sending an email or message with a temporary password ?! )
        Button restore_pass = findViewById(R.id.restore);
        restore_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                user_name = name.getText().toString();
                user_email = email.getText().toString();


                // TO-DO : Restore Password Query


                // Return Integers for example ( -1 == email does not exist , 0 == success, 1 == user name does not exist , 2 == email & pass does not match)
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
                        // Inputs are ok
                        wrong_span.setVisibility(View.INVISIBLE);

                        // Send mail

                        // in success :
                        AlertDialog.Builder builder = new AlertDialog.Builder(RestorePassActivity.this);
                        builder.setMessage("An email will be sent to your email containing a temporary password");
                        builder.setCancelable(true);
                        builder.setPositiveButton(
                                "Ok",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog email_sent = builder.create();
                        email_sent.show();

                        break;
                    case 1 :
                        wrong_span.setText(getString(R.string.username_not_found));
                        wrong_span.setVisibility(View.VISIBLE);
                        break;
                    case 2 :
                        wrong_span.setText(getString(R.string.email_uname_doesnt_match));
                        wrong_span.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });


    }

    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(this, "right-to-left");
    }

}