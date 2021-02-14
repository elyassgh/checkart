package irisi.digitalaube.checkart.about;

import irisi.digitalaube.checkart.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class UGAdapter extends BaseAdapter {

    private Context context;
    private String[][] data;

    public UGAdapter(Context context, String[][] data) {
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
            listItemView = LayoutInflater.from(context).inflate(R.layout.user_guide_item,parent,false);
        }

        String[] listItem = data[position];

        TextView step_number = (TextView) listItemView.findViewById(R.id.stepnbr);
        step_number.setText(listItem[0]);

        TextView step_content = (TextView) listItemView.findViewById(R.id.stepctn);
        step_content.setText(listItem[1]);

        return listItemView;
    }

}