package irisi.digitalaube.checkart.userhandler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import irisi.digitalaube.checkart.R;
import irisi.digitalaube.checkart.database.CheckArtDbHelper;
import irisi.digitalaube.checkart.home.HomeActivity;
import maes.tech.intentanim.CustomIntent;

public class RegisterActivity extends Activity {

    private String user_name;
    private String user_email;
    private String user_password;
    private String user_password_rep;
    private TextView wrong_span;

    private CheckArtDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dbHelper = new CheckArtDbHelper(this);

        //Redirect to login activity
        TextView signIn = findViewById(R.id.signin);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        wrong_span = findViewById(R.id.wrong);
        wrong_span.setVisibility(View.INVISIBLE);

        // User credentials
        final EditText name = findViewById(R.id.user_name);
        final EditText email = findViewById(R.id.email);
        final EditText password = findViewById(R.id.pass);
        final EditText password_rep = findViewById(R.id.pass_rep);


        // User SignUp after verifications
        Button signupbtn = findViewById(R.id.signupbtn);
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                user_name = name.getText().toString();
                user_email = email.getText().toString();
                user_password = password.getText().toString();
                user_password_rep = password_rep.getText().toString();

                // password Verification - Client side:
                if (user_password.isEmpty() || user_password_rep.isEmpty() || user_password.length() < 6) {
                    wrong_span.setText(getString(R.string.invalid_password));
                    wrong_span.setVisibility(View.VISIBLE);
                    return;
                }
                if (!user_password.equals(user_password_rep)) {
                    wrong_span.setText(getString(R.string.diff_pass));
                    wrong_span.setVisibility(View.VISIBLE);
                    return;
                }

                // Inputs are ok
                wrong_span.setVisibility(View.INVISIBLE);


                int status = 2;


                // TO-DO : Register Query ( If Success automatic login )


                // Return Integers for example ( -1 == email already exist , 0 == success, 1 == user name already exist , 2 == ... etc)
                // --> handling errors:

                switch (status) {
                    case -1 :
                        wrong_span.setText(R.string.already_used);
                        wrong_span.setVisibility(View.VISIBLE);
                        break;
                    case 0 :
                        Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                        // in success register user & automatic login : ------------------
                        // load user in memory
                        // intent.putExtra("LOGGED_IN_USER" , USER_OBJECT_HERE );
                        // ---------------------------------------------------------------
                        startActivity(intent);
                        finish();
                        break;
                    case 1 :
                        wrong_span.setText(getString(R.string.invalid_username));
                        wrong_span.setVisibility(View.VISIBLE);
                        break;
                    case 2 :
                        // Other database side verifications
                        // do something
                        Toast.makeText(RegisterActivity.this,"Something Went Wrong - Error !",Toast.LENGTH_SHORT).show();
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