package irisi.digitalaube.checkart.favoris;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import irisi.digitalaube.checkart.R;

public class FavListAdapter extends BaseAdapter {

    private Context context;
    private Object [][] data;

    public FavListAdapter(Context context, Object[][] data) {
        super();
        this.context = context;
        this.data = data;
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

        Object[] listItem = data[position];

        ImageView item_img = (ImageView) listItemView.findViewById(R.id.item_img);
        item_img.setImageBitmap( (Bitmap) listItem[0]);

        TextView item_title = (TextView) listItemView.findViewById(R.id.item_title);
        item_title.setText( (String) listItem[1]);

        return listItemView;
    }

}