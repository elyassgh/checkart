package irisi.digitalaube.checkart.about;

import irisi.digitalaube.checkart.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import maes.tech.intentanim.CustomIntent;


public class UserGuideActivity extends Activity {

    // static data for demo purposes
    String[][] steps = {
            {"Step 1", "Culpa1 cillum consectetur labore nulla nulla magna irure. Id veniam culpa officia aute dolor amet deserunt ex proident commodo"},
            {"Step 2", "Culpa2 cillum consectetur labore nulla nulla magna irure. Id veniam culpa officia aute dolor amet deserunt ex proident commodo"},
            {"Step 3", "Culpa3 cillum consectetur labore nulla nulla magna irure. Id veniam culpa officia aute dolor amet deserunt ex proident commodo"},
            {"Step 4", "Culpa4 cillum consectetur labore nulla nulla magna irure. Id veniam culpa officia aute dolor amet deserunt ex proident commodo"},
            {"Step 5", "Culpa5 cillum consectetur labore nulla nulla magna irure. Id veniam culpa officia aute dolor amet deserunt ex proident commodo"},
            {"Step 6", "Culpa6 cillum consectetur labore nulla nulla magna irure. Id veniam culpa officia aute dolor amet deserunt ex proident commodo"},
            {"Step 7", "Culpa7 cillum consectetur labore nulla nulla magna irure. Id veniam culpa officia aute dolor amet deserunt ex proident commodo"},

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_guide);

        // Load steps from model ? database ? static ?
        ListView lv = findViewById(R.id.lv);

        UGAdapter myAdapter = new UGAdapter(this, steps);
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