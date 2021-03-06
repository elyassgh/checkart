package irisi.digitalaube.checkart.profile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import irisi.digitalaube.checkart.R;
import irisi.digitalaube.checkart.about.MenuActivity;
import irisi.digitalaube.checkart.explore.ExploreActivity;
import irisi.digitalaube.checkart.favoris.FavoriteActivity;
import irisi.digitalaube.checkart.home.HomeActivity;
import maes.tech.intentanim.CustomIntent;

public class ProfileMenuActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_menu);

        // get Loaded User In memory : ------------------
        Bundle extras = getIntent().getExtras();
        assert extras != null;
        // Object[] user_in_memory = (Object[]) extras.get("LOGGED_IN_USER");
        // ---------------------------------------------------

        // Redirect to User Profile Activity
        TextView guide = findViewById(R.id.profile);
        guide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileMenuActivity.this, ProfileActivity.class);
                // pass logged in user : -------------------------------
                // intent.putExtra("LOGGED_IN_USER" , USER_OBJECT_HERE );
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
        // pass logged in user : -------------------------------
        // intent.putExtra("LOGGED_IN_USER" , USER_OBJECT_HERE );
        startActivity(intent);
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(this, "fadein-to-fadeout");
    }
}