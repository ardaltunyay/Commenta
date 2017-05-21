package tr.edu.dogus.commenta.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import tr.edu.dogus.commenta.model.Venue;
import tr.edu.dogus.commenta.R;

/**
 * Created by ardaltunyay on 21.05.2017.
 */

public class VenueAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private List<Venue> venues;

    public VenueAdapter(Context context, List<Venue> venues) {
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.venues = venues;
    }

    @Override
    public int getCount() {
        return venues.size();
    }

    @Override
    public Object getItem(int position) {
        return venues.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if(row == null)
            row = layoutInflater.inflate(R.layout.row_place, null);

        TextView tvVenueName = (TextView) row.findViewById(R.id.tvVenueName);
        tvVenueName.setText(venues.get(position).getName());

        return row;
    }
}


