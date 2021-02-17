package irisi.digitalaube.checkart.favoris;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import irisi.digitalaube.checkart.R;
import maes.tech.intentanim.CustomIntent;

public class FavListAdapter extends BaseAdapter {

    private Context context;
    private Object [][] data;
    private AlertDialog.Builder builder;

    public FavListAdapter(Context context, Object[][] data) {
        super();
        this.context = context;
        this.data = data;
        this.builder = new AlertDialog.Builder(context);
    }

    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public Object getItem(int position) {
        return data[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(R.layout.fav_list_item,parent,false);
        }

        final Object[] listItem = data[position];

        ImageView item_img = (ImageView) listItemView.findViewById(R.id.item_img);
        item_img.setImageBitmap( (Bitmap) listItem[0]);

        TextView item_title = (TextView) listItemView.findViewById(R.id.item_title);
        item_title.setText( (String) listItem[1]);

        ImageView delete = (ImageView) listItemView.findViewById(R.id.delete);
        // Delete Icon Click --> delete item from favorite :
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show Dialog Box
                //Setting dialog message and performing action on button click (YES/NO)
                builder.setMessage("Do you want to delete this item from your favorite list ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'YES' Button

                                // Do Delete Favorite Item Query !!

                                dialog.cancel();
                                Toast.makeText(context.getApplicationContext(),"Item " + (String) listItem[2] + " deleted.", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();
                                Toast.makeText(context.getApplicationContext(),"Item " + (String) listItem[2] + " remaining", Toast.LENGTH_SHORT).show();
                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title
                alert.setTitle("Delete Favorite Item");
                alert.show();
            }
        });

        return listItemView;
    }

}