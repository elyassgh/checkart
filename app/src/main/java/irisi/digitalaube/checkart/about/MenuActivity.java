package irisi.digitalaube.checkart.about;

import irisi.digitalaube.checkart.R;
import irisi.digitalaube.checkart.home.HomeActivity;
import maes.tech.intentanim.CustomIntent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MenuActivity extends Activity {

    private BottomNavigationView navbar;

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

        // Navigation
        navbar = findViewById(R.id.navbar);
        navbar.setSelectedItemId(R.id.nav_about);

        navbar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        navigate(HomeActivity.class);
                        break;
                    case R.id.nav_explore:
                        Log.d("NavBar :", "goto about");
                        break;
                    case R.id.nav_favorite:
                        Log.d("NavBar :", "goto favorite");
                        break;
                    case R.id.nav_profile:
                        Log.d("NavBar :", "goto profile");
                        break;
                    case R.id.nav_about:
                        // do nothing :)
                        break;
                }
                return true;
            }
        });

    }

    public void navigate(Class<?> activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(this, "fadein-to-fadeout");
    }
}