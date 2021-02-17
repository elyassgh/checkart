package irisi.digitalaube.checkart.about;

import irisi.digitalaube.checkart.R;
import maes.tech.intentanim.CustomIntent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class CurrentVersionActivity extends Activity {

    // static data for demo purposes
    String[][] versions = {
            {"Version 1.7.8 (Current)", "Culpa1 cillum consectetur \nlabore nulla nulla magna irure.\nId veniam culpa officia aute dolor amet deserunt ex proident commodo"},
            {"Version 1.7.7", "Culpa2 cillum consectetur \nlabore nulla nulla magna irure.  Id veniam culpa officia aute\ndolor amet deserunt ex proident commodo"},
            {"Version 1.7.6", "Culpa3 cillum consectetur \nlabore nulla nulla magna irure. Id veniam culpa officia\naute dolor amet deserunt ex proident commodo"},
            {"Version 1.7.5", "Culpa4 cillum consectetur \nlabore nulla nulla magna irure.\nId veniam culpa officia aute\n dolor amet deserunt ex proident commodo"},
            {"Version 1.7.4", "Culpa5 cillum consectetur \nlabore nulla nulla magna irure. Id veniam culpa officia aute dolor\namet deserunt ex proident commodo"},
            {"Version 1.7.3", "Culpa6 cillum consectetur \nlabore nulla nulla magna irure. Id veniam culpa officia aute dolor\namet deserunt ex proident commodo"},
            {"Version 1.7.1", "Culpa7 cillum consectetur \nlabore nulla nulla magna irure. Id veniam culpa officia\naute dolor amet deserunt\nex proident commodo"},
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_version);

        // Load versions from model ? database ? static ?
        ListView lv = findViewById(R.id.lv);

        VersionAdapter myAdapter = new VersionAdapter(this, versions);
        lv.setAdapter(myAdapter);

        // Return to About Menu Activity
        TextView title = findViewById(R.id.title);
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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