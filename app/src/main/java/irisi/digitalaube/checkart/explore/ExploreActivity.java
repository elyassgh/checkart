package irisi.digitalaube.checkart.explore;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import irisi.digitalaube.checkart.R;
import irisi.digitalaube.checkart.about.MenuActivity;
import irisi.digitalaube.checkart.favoris.FavoriteActivity;
import irisi.digitalaube.checkart.home.HomeActivity;
import irisi.digitalaube.checkart.profile.ProfileMenuActivity;
import maes.tech.intentanim.CustomIntent;

public class ExploreActivity extends Activity {


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
        for (final String searchColor : ColorSearch.COLOR_PALETTE) {
            View view  = inflater.inflate(R.layout.color_btn, search_colors, false);
            ImageButton imageButton = view.findViewById(R.id.color);
            imageButton.setBackgroundColor(Color.parseColor(ColorSearch.COLOR_PREFIX + searchColor));

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

                    ColorSearch.findSimilars(searchColor , 0.2);

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