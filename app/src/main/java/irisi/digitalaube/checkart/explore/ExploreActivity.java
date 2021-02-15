package irisi.digitalaube.checkart.explore;

import irisi.digitalaube.checkart.R;
import irisi.digitalaube.checkart.about.MenuActivity;
import irisi.digitalaube.checkart.favoris.FavoriteActivity;
import irisi.digitalaube.checkart.home.HomeActivity;
import irisi.digitalaube.checkart.profile.ProfileMenuActivity;
import maes.tech.intentanim.CustomIntent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ExploreActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);




        // Navigation
        BottomNavigationView navbar = findViewById(R.id.navbar);
        navbar.setSelectedItemId(R.id.nav_explore);

        navbar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        navigate(HomeActivity.class);
                        break;
                    case R.id.nav_explore:
                        // do nothing ;)
                        break;
                    case R.id.nav_favorite:
                        navigate(FavoriteActivity.class);
                        break;
                    case R.id.nav_profile:
                        navigate(ProfileMenuActivity.class);
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