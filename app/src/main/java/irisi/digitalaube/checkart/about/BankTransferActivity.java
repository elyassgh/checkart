package irisi.digitalaube.checkart.about;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import irisi.digitalaube.checkart.R;
import maes.tech.intentanim.CustomIntent;

public class BankTransferActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_transfer);

        // Return to Donate Activity
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