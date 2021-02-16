package irisi.digitalaube.checkart.favoris;

import irisi.digitalaube.checkart.R;
import irisi.digitalaube.checkart.about.MenuActivity;
import irisi.digitalaube.checkart.about.UGAdapter;
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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Arrays;


public class FavoriteActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        // Load steps from model ? database ? static ?
        ListView lv = findViewById(R.id.lv);

        Bitmap dummyImg = BitmapFactory.decodeResource(this.getResources(), R.drawable.tapis);

        // static data for demo purposes
        final Object [][] dummyFavlist = {
                {dummyImg , "Lorem1 Ipsum dolor sit ammet sacred bit qest", "Action 1"},
                {dummyImg , "Lorem2 Ipsum dolor sit ammet sacred bit qest", "Action 2"},
                {dummyImg , "Lorem3 Ipsum dolor sit ammet sacred bit qest", "Action 3"},
                {dummyImg , "Lorem4 Ipsum dolor sit ammet sacred bit qest", "Action 4"},
                {dummyImg , "Lorem5 Ipsum dolor sit ammet sacred bit qest", "Action 5"},
                {dummyImg , "Lorem6 Ipsum dolor sit ammet sacred bit qest", "Action 6"},
                {dummyImg , "Lorem7 Ipsum dolor sit ammet sacred bit qest", "Action 7"},
        };

        FavListAdapter myAdapter = new FavListAdapter(this, dummyFavlist);
        lv.setAdapter(myAdapter);

        // Favorite Item Click Action --> TO-DO : Redirect to Fav Item Detail Activity
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(FavoriteActivity.this, (String) dummyFavlist[position][2], Toast.LENGTH_SHORT).show();
            }
        });

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