package irisi.digitalaube.checkart.about;

import irisi.digitalaube.checkart.R;
import maes.tech.intentanim.CustomIntent;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DonateActivity extends Activity {
    TextView title;
    TextView creditcard;
    TextView paypal;
    TextView bank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);
        title = findViewById(R.id.donate1);
        creditcard = findViewById(R.id.creditcard);
        paypal = findViewById(R.id.paypal);
        bank = findViewById(R.id.bank);

        creditcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setAction(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://pay.google.com/gp/w/u/0/home/signup"));
                startActivity(Intent.createChooser(i, "Title"));

            }
        });
        paypal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setAction(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://www.paypal.com/ma/home"));
                startActivity(Intent.createChooser(i, "Title"));
            }
        });
        
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (DonateActivity.this, MenuActivity.class);
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