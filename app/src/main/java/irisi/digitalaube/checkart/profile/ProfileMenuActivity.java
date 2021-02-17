package irisi.digitalaube.checkart.profile;

import irisi.digitalaube.checkart.R;
import irisi.digitalaube.checkart.about.MenuActivity;
import irisi.digitalaube.checkart.explore.ExploreActivity;
import irisi.digitalaube.checkart.favoris.FavoriteActivity;
import irisi.digitalaube.checkart.home.HomeActivity;
import maes.tech.intentanim.CustomIntent;

import androidx.annotation.NonNull;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileMenuActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_menu);

        // Redirect to User Profile Activity
        TextView guide = findViewById(R.id.profile);
        guide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileMenuActivity.this, ProfileActivity.class);
                startActivity(intent);
                // (Back == Return to ProfileMenuActivity )
                CustomIntent.customType(ProfileMenuActivity.this, "left-to-right");
            }
        });


        // Navigation
        BottomNavigationView navbar = findViewById(R.id.navbar);
        navbar.setSelectedItemId(R.id.nav_profile);

        navbar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        navigate(HomeActivity.class);
                        break;
                    case R.id.nav_explore:
                        navigate(ExploreActivity.class);
                        break;
                    case R.id.nav_favorite:
                        navigate(FavoriteActivity.class);
                        break;
                    case R.id.nav_profile:
                        // do nothing :)
                        break;
                    case R.id.nav_about:
                        navigate(MenuActivity.class);
                        break;
                }
                return true;
            }
        });
    }

    // Navigator
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