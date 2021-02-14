package irisi.digitalaube.checkart.about;

import irisi.digitalaube.checkart.R;
import maes.tech.intentanim.CustomIntent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class CurrentVersionActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_version);

        // Return to About Menu Activity
        TextView title = findViewById(R.id.title);
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (CurrentVersionActivity.this, MenuActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(this, "right-to-left");
    }
}