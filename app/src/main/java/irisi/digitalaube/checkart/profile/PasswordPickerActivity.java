package irisi.digitalaube.checkart.profile;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import irisi.digitalaube.checkart.R;
import maes.tech.intentanim.CustomIntent;

public class PasswordPickerActivity extends Activity {

    // demo user : ---------------------------------------------------------------------------------

    Object [] in_memory_user = {"Elghazi Ilyass" , "elghazi.elyass@gmail.com", "14/08/1999" , "212665941986" , "password"};

    // demo user . ---------------------------------------------------------------------------------

    private EditText new_pass;
    private EditText new_pass_rep;
    private EditText old_pass;
    private TextView wrong_span;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_picker);

        // get Loaded User In memory : ------------------
        Bundle extras = getIntent().getExtras();
        assert extras != null;
        // Object[] user_in_memory = (Object[]) extras.get("LOGGED_IN_USER");
        // ---------------------------------------------------


        // Return to Profile Activity
        TextView title = findViewById(R.id.title);
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        new_pass = findViewById(R.id.password);
        new_pass_rep = findViewById(R.id.password2);
        old_pass = findViewById(R.id.oldpassword);
        wrong_span = findViewById(R.id.wrong);
        wrong_span.setVisibility(View.INVISIBLE);

        // Submit Button
        Button save = findViewById(R.id.savebtn);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Password handling
                String new_password = new_pass.getText().toString();

                String new_password_rep = new_pass_rep.getText().toString();
                String old_password = old_pass.getText().toString();


                if (new_password.isEmpty() || new_password_rep.isEmpty() || new_password.length() < 6) {
                    wrong_span.setText(getString(R.string.invalid_password));
                    wrong_span.setVisibility(View.VISIBLE);
                    return;
                }

                if (!new_password.equals(new_password_rep)) {
                    wrong_span.setText(getString(R.string.diff_pass));
                    wrong_span.setVisibility(View.VISIBLE);
                    return;
                } else {
                    // Verify old password is correct

                    // demo  : -------------------------------------------------------
                    if (!old_password.equals(in_memory_user[4])) {
                        wrong_span.setText(getString(R.string.incorrect_password));
                        wrong_span.setVisibility(View.VISIBLE);
                        return;
                    }
                    // demo  . -------------------------------------------------------


                    // In implementation : -------------------------------------------
                    /* if (!old_password.equals(in_memory_user.getPassword())) {
                        wrong_span.setText(getString(R.string.incorrect_password));
                        wrong_span.setVisibility(View.VISIBLE);
                        return;
                    } *///--------------------------------------------------------------


                    // Inputs are ok
                    wrong_span.setVisibility(View.INVISIBLE);
                }

                // TO-DO Password Update Query

            }
        });

    }

    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(this, "right-to-left");
    }
}