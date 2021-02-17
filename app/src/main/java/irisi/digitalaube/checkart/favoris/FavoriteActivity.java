package irisi.digitalaube.checkart.favoris;

import irisi.digitalaube.checkart.R;
import irisi.digitalaube.checkart.about.MenuActivity;
import irisi.digitalaube.checkart.explore.ExploreActivity;
import irisi.digitalaube.checkart.home.HomeActivity;
import irisi.digitalaube.checkart.profile.ProfileMenuActivity;
import maes.tech.intentanim.CustomIntent;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;
import java.util.Map;


public class FavoriteActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        // Load steps from model ? database ? static ?
        ListView lv = findViewById(R.id.lv);


        // Demo data -------------------------------------------------------------------------------

        Map<String, Double> matchings = new HashMap<>();
        matchings.put("azilal", (double) 87);
        matchings.put("beni ouaraine", (double) 12);
        matchings.put("boucherouite", 8.9);


        String [] colors = {"144E5A" , "CACFE5", "00612C", "621D27", "52CDE0", "FB7181",
                "144E5A" , "CACFE5", "00612C", "621D27", "52CDE0", "FB7181"};

        Bitmap dummyImg = BitmapFactory.decodeResource(this.getResources(), R.drawable.tapis);

        // static data for demo purposes
        final Object [][] dummyFavlist = {
                {dummyImg , "Lorem1 Ipsum dolor sit ammet sacred bit qest", "#1", matchings , colors},
                {dummyImg , "Lorem2 Ipsum dolor sit ammet sacred bit qest", "#2", matchings , colors},
                {dummyImg , "Lorem3 Ipsum dolor sit ammet sacred bit qest", "#3", matchings , colors},
                {dummyImg , "Lorem4 Ipsum dolor sit ammet sacred bit qest", "#4", matchings , colors},
                {dummyImg , "Lorem5 Ipsum dolor sit ammet sacred bit qest", "#5", matchings , colors},
                {dummyImg , "Lorem6 Ipsum dolor sit ammet sacred bit qest", "#6", matchings , colors},
                {dummyImg , "Lorem7 Ipsum dolor sit ammet sacred bit qest", "#7", matchings , colors},
        };

        // Demo data -------------------------------------------------------------------------------

        FavListAdapter myAdapter = new FavListAdapter(this, dummyFavlist);
        lv.setAdapter(myAdapter);

        // Navigation
        BottomNavigationView navbar = findViewById(R.id.navbar);
        navbar.setSelectedItemId(R.id.nav_favorite);

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