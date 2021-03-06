package irisi.digitalaube.checkart.profile;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import irisi.digitalaube.checkart.R;
import maes.tech.intentanim.CustomIntent;

public class PhonePickerActivity extends Activity {

    // demo user : ---------------------------------------------------------------------------------

    Object [] in_memory_user = {"Elghazi Ilyass" , "elghazi.elyass@gmail.com", "14/08/1999" , "212665941986" , "password"};

    // demo user . ---------------------------------------------------------------------------------

    private EditText phone_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_picker);

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

        phone_number = findViewById(R.id.phone_number);

        // demo : ----------------------------------
        phone_number.setText( (String) in_memory_user[3] );
        // demo . ----------------------------------

        // In implementation : ----------------------
        // if null replace with empty string !
        // birthday.setText( (String) user_in_memory.getPhoneNumber() );
        // ------------------------------------------

        // Submit Button
        Button save = findViewById(R.id.savebtn);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String new_email = phone_number.getText().toString();

                // TO-DO Update Email Query

            }
        });

    }

    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(this, "right-to-left");
    }
}