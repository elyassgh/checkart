package irisi.digitalaube.checkart.profile;

import irisi.digitalaube.checkart.R;
import maes.tech.intentanim.CustomIntent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ProfileActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Return to About Menu Activity
        TextView title = findViewById(R.id.title);
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Redirect to Name Changer Activity :
        TextView namePicker = findViewById(R.id.namePicker);
        namePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, namePickerActivity.class);
                startActivity(intent);
                // (Back == Return to ProfileMenuActivity )
                CustomIntent.customType(ProfileActivity.this, "left-to-right");
            }
        });

        // Redirect to Birthday Chooser Activity :
        TextView birthdayPicker = findViewById(R.id.birthdayPicker);
        birthdayPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, BirthdayPickerActivity.class);
                startActivity(intent);
                // (Back == Return to ProfileMenuActivity )
                CustomIntent.customType(ProfileActivity.this, "left-to-right");
            }
        });

        // Redirect to Email Changer Activity :
        TextView emailPicker = findViewById(R.id.emailPicker);
        emailPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, EmailPickerActivity.class);
                startActivity(intent);
                // (Back == Return to ProfileMenuActivity )
                CustomIntent.customType(ProfileActivity.this, "left-to-right");
            }
        });

        // Redirect to Email Changer Activity :
        TextView phonePicker = findViewById(R.id.phonePicker);
        phonePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, PhonePickerActivity.class);
                startActivity(intent);
                // (Back == Return to ProfileMenuActivity )
                CustomIntent.customType(ProfileActivity.this, "left-to-right");
            }
        });

        // Redirect to Password Changer Activity :
        TextView passwordPicker = findViewById(R.id.passwordPicker);
        passwordPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, PasswordPickerActivity.class);
                startActivity(intent);
                // (Back == Return to ProfileMenuActivity )
                CustomIntent.customType(ProfileActivity.this, "left-to-right");
            }
        });


    }

    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(this, "right-to-left");
    }

}