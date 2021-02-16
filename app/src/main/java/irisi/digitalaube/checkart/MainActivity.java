package irisi.digitalaube.checkart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import irisi.digitalaube.checkart.userhandler.LoginActivity;
import maes.tech.intentanim.CustomIntent;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
               // Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                Intent intent = new Intent(MainActivity.this, UserguideActivity.class);
                startActivity(intent);
                CustomIntent.customType(MainActivity.this, "fadein-to-fadeout");
                finish();
            }
        }, 3500);

    }
}