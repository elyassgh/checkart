package irisi.digitalaube.checkart.about;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import irisi.digitalaube.checkart.R;

public class VersionAdapter extends BaseAdapter {

    private Context context;
    private String[][] data;

    public VersionAdapter(Context context, String[][] data) {
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
            listItemView = LayoutInflater.from(context).inflate(R.layout.version_item,parent,false);
        }

        String[] listItem = data[position];

        TextView version_nbr = (TextView) listItemView.findViewById(R.id.version_nbr);
        version_nbr.setText(listItem[0]);

        TextView version_content = (TextView) listItemView.findViewById(R.id.version_ctn);
        version_content.setText(listItem[1]);

        return listItemView;
    }

}
