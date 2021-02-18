package irisi.digitalaube.checkart.favoris;

import irisi.digitalaube.checkart.R;
import maes.tech.intentanim.CustomIntent;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;


public class FavoriteItemActivity extends Activity {

    Object[] fav_item;
    String COLOR_PREFIX = "#";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_item);

        // Return to Favorite Activity
        TextView title = findViewById(R.id.title);
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Get favorite item and render view
        Bundle extras = getIntent().getExtras();
        assert extras != null;
        fav_item = (Object[]) extras.get("fav_item");

        // UI Elements
        ImageView fav_item_img = findViewById(R.id.fav_item_img);
        ListView fav_item_lv_matches = findViewById(R.id.fav_item_lv_matches);
        LinearLayout fav_item_lv_colors = findViewById(R.id.fav_item_lv_colors);

        // Set UI Content ;)
        if (fav_item!=null) {

            // Set image
            fav_item_img.setImageBitmap( (Bitmap) fav_item[0]);

            // Set carpet name & percentages (tapis & pourcentange)
            MatchesAdapter myAdapter = new MatchesAdapter(this, (HashMap<String, Double>) fav_item[3]);
            fav_item_lv_matches.setAdapter(myAdapter);

            // Set colors
            LayoutInflater inflater = LayoutInflater.from(this);
            for (final String colorInPicture : (String[]) fav_item[4]) {
                View view  = inflater.inflate(R.layout.color_btn, fav_item_lv_colors, false);
                ImageButton imageButton = view.findViewById(R.id.color);
                imageButton.setBackgroundColor(Color.parseColor(COLOR_PREFIX + colorInPicture));

                // do something with selected color in search query (Ex. Navigate to Explore) :
                imageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TO-DO Later --> Go to Explore Activity
                        Toast.makeText(FavoriteItemActivity.this, "do something with : " + colorInPicture , Toast.LENGTH_SHORT).show();
                    }
                });

                fav_item_lv_colors.addView(view);
            }

        } else {
            Log.d("fav_item", " : Hell No");
        }

    }

    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(this, "right-to-left");
    }
}