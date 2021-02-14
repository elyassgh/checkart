package irisi.digitalaube.checkart.about;

import irisi.digitalaube.checkart.R;
import maes.tech.intentanim.CustomIntent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MenuActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_menu);

        // Redirect to User Guide Activity
        TextView guide = findViewById(R.id.guide);
        guide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, UserGuideActivity.class);
                startActivity(intent);
                // (Back == Return to MenuActivity )
                CustomIntent.customType(MenuActivity.this, "left-to-right");
            }
        });

        // Redirect to Privacy Policy Activity
        TextView privacy = findViewById(R.id.privacy);
        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, PrivacyPolicyActivity.class);
                startActivity(intent);
                // (Back == Return to MenuActivity )
                CustomIntent.customType(MenuActivity.this, "left-to-right");
            }
        });

        // Redirect to Donate Activity
        TextView donate = findViewById(R.id.donate);
        donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, DonateActivity.class);
                startActivity(intent);
                // (Back == Return to MenuActivity )
                CustomIntent.customType(MenuActivity.this, "left-to-right");
            }
        });

        // Redirect to Current Version Activity
        TextView version = findViewById(R.id.version);
        version.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, CurrentVersionActivity.class);
                startActivity(intent);
                // (Back == Return to MenuActivity )
                CustomIntent.customType(MenuActivity.this, "left-to-right");
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(this, "fadein-to-fadeout");
    }
}