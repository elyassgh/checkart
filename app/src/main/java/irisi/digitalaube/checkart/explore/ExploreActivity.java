package irisi.digitalaube.checkart.explore;

import irisi.digitalaube.checkart.R;
import irisi.digitalaube.checkart.about.MenuActivity;
import irisi.digitalaube.checkart.favoris.FavoriteActivity;
import irisi.digitalaube.checkart.home.HomeActivity;
import irisi.digitalaube.checkart.profile.ProfileMenuActivity;
import maes.tech.intentanim.CustomIntent;

import androidx.annotation.NonNull;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ExploreActivity extends Activity {

    String COLOR_PREFIX = "#";

    // Fun fact : it takes 35 min to make this array ;)
    String[] color_palette = {"FFEBEE", "FFCDD2", "EF9A9A", "E57373", "F44336", "EF5350", "E53935", "D32F2F", "C62828", "B71C1C",
            "FF8A80", "FF5252", "FF1744", "D50000", "FCE4EC", "F8BBD0", "F48FB1", "F06292", "EC407A", "E91E63", "D81B60", "C2185B",
            "AD1457", "880E4F", "FF80AB", "FF4081", "F50057", "C51162", "F3E5F5", "E1BEE7", "CE93D8", "BA68C8", "AB47BC", "9C27B0",
            "8E24AA", "7B1FA2", "6A1B9A", "4A148C", "EA80FC", "E040FB", "D500F9", "AA00FF", "EDE7F6", "D1C4E9", "B39DDB", "9575CD",
            "7E57C2", "673AB7", "5E35B1", "512DA8", "4527A0", "311B92", "B388FF", "7C4DFF", "651FFF", "6200EA", "E8EAF6", "C5CAE9",
            "9FA8DA", "7986CB", "5C6BC0", "3F51B5", "3949AB", "303F9F", "283593", "1A237E", "8C9EFF", "536DFE", "3D5AFE", "304FFE",
            "E3F2FD", "BBDEFB", "90CAF9", "64B5F6", "42A5F5", "2196F3", "1E88E5", "1976D2", "1565C0", "0D47A1", "82B1FF", "448AFF",
            "2979FF", "2962FF", "E1F5FE", "B3E5FC", "81D4FA", "4FC3F7", "29B6F6", "03A9F4", "039BE5", "0288D1", "0277BD", "01579B",
            "80D8FF", "40C4FF", "00B0FF", "0091EA", "E0F7FA", "B2EBF2", "80DEEA", "4DD0E1", "26C6DA", "00BCD4", "00ACC1", "0097A7",
            "00838F", "006064", "84FFFF", "18FFFF", "00E5FF", "00B8D4", "E0F2F1", "B2DFDB", "80CBC4", "4DB6AC", "26A69A", "009688",
            "00897B", "00796B", "00695C", "004D40", "A7FFEB", "64FFDA", "1DE9B6", "00BFA5", "E8F5E9", "C8E6C9", "A5D6A7", "81C784",
            "66BB6A", "4CAF50", "43A047", "388E3C", "2E7D32", "1B5E20", "B9F6CA", "69F0AE", "00E676", "00C853", "F1F8E9", "DCEDC8",
            "C5E1A5", "AED581", "9CCC65", "8BC34A", "7CB342", "689F38", "558B2F", "33691E", "CCFF90", "B2FF59", "76FF03", "64DD17",
            "F9FBE7", "F0F4C3", "E6EE9C", "DCE775", "D4E157", "CDDC39", "C0CA33", "AFB42B", "9E9D24", "827717", "F4FF81", "EEFF41",
            "C6FF00", "AEEA00", "FFFDE7", "FFF9C4", "FFF59D", "FFF176", "FFEE58", "FFEB3B", "FDD835", "FBC02D", "F9A825", "F57F17",
            "FFFF8D", "FFFF00", "FFEA00", "FFD600", "FFF8E1", "FFECB3", "FFE082", "FFD54F", "FFCA28", "FFC107", "FFB300", "FFA000",
            "FF8F00", "FF6F00", "FFE57F", "FFD740", "FFC400", "FFAB00", "FFF3E0", "FFE0B2", "FFCC80", "FFB74D", "FFA726", "FF9800",
            "FB8C00", "F57C00", "EF6C00", "E65100", "FFD180", "FFAB40", "FF9100", "FF6D00", "FBE9E7", "FFCCBC", "FFAB91", "FF8A65",
            "FF7043", "FF5722", "F4511E", "E64A19", "D84315", "BF360C", "FF9E80", "FF6E40", "FF3D00", "DD2C00", "EFEBE9", "D7CCC8",
            "BCAAA4", "A1887F", "8D6E63", "795548", "6D4C41", "5D4037", "4E342E", "3E2723", "FAFAFA", "F5F5F5", "EEEEEE", "E0E0E0",
            "BDBDBD", "9E9E9E", "757575", "616161", "424242", "212121", "90A4AE", "78909C", "607D8B", "546E7A", "455A64", "37474F",
            "ECEFF1", "CFD8DC", "B0BEC5", "263238", "000000", };

    private AlertDialog.Builder builder;
    private TextView count;
    private LinearLayout emptySpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);

        // get Loaded User In memory : ------------------
        Bundle extras = getIntent().getExtras();
        assert extras != null;
        // Object[] user_in_memory = (Object[]) extras.get("LOGGED_IN_USER");
        // ---------------------------------------------------

        final LinearLayout search_colors = findViewById(R.id.colorSearch);
        final LinearLayout rightSet = findViewById(R.id.resultSet_even);
        final LinearLayout leftSet = findViewById(R.id.resultSet_odd);
        final LayoutInflater inflater = LayoutInflater.from(this);

        builder = new AlertDialog.Builder(this);

        count = findViewById(R.id.result_counter);
        emptySpan = findViewById(R.id.emptyspan);


        // static data for demo purposes -----------------------------------------------------------
        Bitmap dummyImg = BitmapFactory.decodeResource(this.getResources(), R.drawable.tapis);
        final Object [][] exploreResult = {
                {dummyImg , "Lorem1 Ipsum dolor sit ammet sacred bit qest", "#1"},
                {dummyImg , "Lorem2 Ipsum dolor sit ammet sacred bit qest", "#2"},
                {dummyImg , "Lorem3 Ipsum dolor sit ammet sacred bit qest", "#3"},
                {dummyImg , "Lorem4 Ipsum dolor sit ammet sacred bit qest", "#4"},
                {dummyImg , "Lorem5 Ipsum dolor sit ammet sacred bit qest", "#5"},
                {dummyImg , "Lorem6 Ipsum dolor sit ammet sacred bit qest", "#6"},
                {dummyImg , "Lorem7 Ipsum dolor sit ammet sacred bit qest", "#7"},
        };
        final Object [][] exploreResult2 = {
                {dummyImg , "Lorem1 Ipsum dolor sit ammet sacred bit qest", "#1"},
                {dummyImg , "Lorem2 Ipsum dolor sit ammet sacred bit qest", "#2"},
        };
        final Object [][] exploreResult3 = {
                {dummyImg , "Lorem1 Ipsum dolor sit ammet sacred bit qest", "#1"},
                {dummyImg , "Lorem2 Ipsum dolor sit ammet sacred bit qest", "#2"},
                {dummyImg , "Lorem3 Ipsum dolor sit ammet sacred bit qest", "#3"},
        };
        final Object [][] exploreResultEmpty = {};
        // -----------------------------------------------------------------------------------------



        // Render color palette search UI
        for (final String searchColor : color_palette) {
            View view  = inflater.inflate(R.layout.color_btn, search_colors, false);
            ImageButton imageButton = view.findViewById(R.id.color);
            imageButton.setBackgroundColor(Color.parseColor(COLOR_PREFIX + searchColor));

            // TO-DO Later -->
            // Ajax call to the server and render the result
            // loader gif ( beforeSend function )
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // TO-DO Explore Query (With COLOR Hex Value)

                    // demo : ----------------------------------------
                        // get explore result --> delete later
                        Object [][] result;
                        switch (searchColor) {
                            case "FFEBEE":
                                result = exploreResult;
                                break;
                            case "FFCDD2":
                                result = exploreResult2;
                                break;
                            case "EF9A9A":
                                result = exploreResult3;
                                break;
                            default:
                                result = exploreResultEmpty;
                                break;
                        }
                    // demo .  ----------------------------------------

                    // render explore result in UI
                    renderExploreItems(result, inflater , rightSet , leftSet);

                    Toast.makeText(ExploreActivity.this, "Explore pictures matches : " + searchColor , Toast.LENGTH_SHORT).show();
                }
            });

            search_colors.addView(view);
        }


        // Navigation
        BottomNavigationView navbar = findViewById(R.id.navbar);
        navbar.setSelectedItemId(R.id.nav_explore);

        navbar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        navigate(HomeActivity.class);
                        break;
                    case R.id.nav_explore:
                        // do nothing ;)
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
        // pass logged in user : -------------------------------
        // intent.putExtra("LOGGED_IN_USER" , USER_OBJECT_HERE );
        startActivity(intent);
        finish();
    }

    // Explore Items Renderer
    public void renderExploreItems (final Object[][] exploreResult, LayoutInflater inflater, LinearLayout rightSet, LinearLayout leftSet) {

        // Clear views
        rightSet.removeAllViews();
        leftSet.removeAllViews();
        emptySpan.removeAllViews();

        // Handle empty result :
        if (exploreResult.length != 0) {

            // Clear empty span
            emptySpan.setVisibility(View.GONE);

            for (int i=0 ; i < exploreResult.length ; i++) {
                LinearLayout layout = (i % 2 == 0) ? rightSet : leftSet;
                View view  = inflater.inflate(R.layout.explore_item, layout, false);

                LinearLayout explore_item = view.findViewById(R.id.exp_item);
                final int finalI = i;
                explore_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Do some thing with Explore Item Click
                        //Setting dialog message and performing action on button click (YES/NO)
                        builder.setMessage("Do you want to add this item from your favorite list ?")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //  Action for 'YES' Button

                                        // TO-DO Add Favorite Item to favorite list Query !!

                                        dialog.cancel();
                                        Toast.makeText(ExploreActivity.this, "Item : " + (String) exploreResult[finalI][2] + " Added.", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //  Action for 'NO' Button
                                        dialog.cancel();
                                        Toast.makeText(ExploreActivity.this, "Nothing : " +(String) exploreResult[finalI][2], Toast.LENGTH_SHORT).show();
                                    }
                                });
                        //Creating dialog box
                        AlertDialog alert = builder.create();
                        //Setting the title
                        alert.setTitle("Add to Favorite");
                        alert.show();

                    }
                });

                ImageView image = view.findViewById(R.id.exp_item_img);
                image.setImageBitmap( (Bitmap) exploreResult[i][0] );

                TextView title = view.findViewById(R.id.exp_item_title);
                title.setText( (String) exploreResult[i][1] );

                layout.addView(view);
            }
        } else {
            // Render nothing found view
            View nothing_found_view  = inflater.inflate(R.layout.nothing_found, emptySpan, false);

            Button home_action = nothing_found_view.findViewById(R.id.back);
            home_action.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navigate(HomeActivity.class);
                }
            });

            emptySpan.addView(nothing_found_view);
            emptySpan.setVisibility(View.VISIBLE);
            Toast.makeText(ExploreActivity.this, "Nothing Found !" , Toast.LENGTH_SHORT).show();
        }

        count.setText(String.valueOf(exploreResult.length).concat(" Results"));

    }

    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(this, "fadein-to-fadeout");
    }
}