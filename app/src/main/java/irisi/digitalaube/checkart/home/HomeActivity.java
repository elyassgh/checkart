package irisi.digitalaube.checkart.home;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

import irisi.digitalaube.checkart.R;
import irisi.digitalaube.checkart.RealtimeActivity;
import irisi.digitalaube.checkart.about.MenuActivity;
import irisi.digitalaube.checkart.explore.ExploreActivity;
import irisi.digitalaube.checkart.favoris.FavoriteActivity;
import irisi.digitalaube.checkart.profile.ProfileMenuActivity;
import irisi.digitalaube.checkart.userhandler.LoginActivity;
import maes.tech.intentanim.CustomIntent;

public class HomeActivity extends Activity {

    private static final int CAMERA_PIC_REQUEST = 1888;
    private static final int CAMERA_PERMISSION_CODE = 100;
    private ImageView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // get Loaded User In memory : ------------------
        Bundle extras = getIntent().getExtras();
        assert extras != null;
        // Object[] user_in_memory = (Object[]) extras.get("LOGGED_IN_USER");
        // ---------------------------------------------------

        // Open the camera
        ImageButton openCam = findViewById(R.id.cam);
        openCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
                    } else {
                        // Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        // startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
                        Intent intent = new Intent(HomeActivity.this, RealtimeActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });

        // Display container of the captured image, for demo purposes.
        // this.test = (ImageView) findViewById(R.id.test);

        // Navigation
        BottomNavigationView navbar = findViewById(R.id.navbar);
        navbar.setSelectedItemId(R.id.nav_home);

        navbar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        // do nothing :)
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

    // Permission handling
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
            } else {
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    /* Get captured image
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_PIC_REQUEST) {
            Bitmap photo = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
            // do something with photo
            // --> test
            // this.test.setImageBitmap(photo);
        }
    }
    */

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