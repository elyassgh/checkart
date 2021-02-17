package irisi.digitalaube.checkart.favoris;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.HashMap;

import irisi.digitalaube.checkart.R;

public class MatchesAdapter extends BaseAdapter {

    private Context context;
    private HashMap<String, Double> data;
    private String[] dataKeys;

    public MatchesAdapter(Context context, HashMap<String, Double> data) {
        super();
        this.context = context;
        this.data = data;
        this.dataKeys = data.keySet().toArray(new String[data.size()]);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(dataKeys[position]);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(R.layout.matches_item,parent,false);
        }

        String tapis = "Tapis " + dataKeys[position] + " :";
        String percentage = getItem(position).toString();

        TextView step_number = (TextView) listItemView.findViewById(R.id.tapis);
        step_number.setText(tapis);

        TextView step_content = (TextView) listItemView.findViewById(R.id.percentage);
        step_content.setText(percentage);

        return listItemView;
    }

}