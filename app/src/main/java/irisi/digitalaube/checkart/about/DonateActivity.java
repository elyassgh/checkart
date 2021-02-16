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

    String paypalMeLink = "https://www.paypal.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);

        // Return to About Menu Activity
        TextView title = findViewById(R.id.title);
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (DonateActivity.this, MenuActivity.class);
                startActivity(intent);
                finish();
            }
        });


        // Go to payment Gateway :
        TextView credit_or_debit = findViewById(R.id.card);
        credit_or_debit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DonateActivity.this, PaymentActivity.class);
                startActivity(intent);
                // (Back == Return to DonateActivity )
                CustomIntent.customType(DonateActivity.this, "left-to-right");
            }
        });

        // Redirect to Paypal donate checkpoint
        TextView paypal_me = findViewById(R.id.paypal);
        paypal_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(paypalMeLink));
                startActivity(Intent.createChooser(intent, "Choose browser"));
            }
        });

        // Redirect to Bank Transfer Details
        TextView banck_transfer = findViewById(R.id.bank);
        banck_transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DonateActivity.this, BankTransferActivity.class);
                startActivity(intent);
                // (Back == Return to DonateActivity )
                CustomIntent.customType(DonateActivity.this, "left-to-right");
            }
        });

    }

    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(this, "right-to-left");
    }
}