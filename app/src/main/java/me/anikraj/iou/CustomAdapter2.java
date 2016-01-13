package me.anikraj.iou;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

public class CustomAdapter2 extends ArrayAdapter<OObject> {

    public CustomAdapter2(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public CustomAdapter2(Context context, int resource, List<OObject> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        OObject p = getItem(position);

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            if(p.getPayed()==3) v = vi.inflate(R.layout.rowmarked, null);
            else v = vi.inflate(R.layout.row, null);
        }






        if (p != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.name);
            TextView tt2 = (TextView) v.findViewById(R.id.date);
            TextView tt3 = (TextView) v.findViewById(R.id.amount);
            ImageView im=(ImageView) v.findViewById(R.id.list_icon);

            if (tt1 != null) {
                tt1.setText(p.getWho()+"");
            }

            if (tt2 != null) {
                tt2.setText(new TimeAgo(getContext()).timeagostring(p.getDate())+"");
            }

            if (tt3 != null) {
                tt3.setText(p.getAmount()+"");
            }
            if (p.getPayed()==1) {
                    im.setImageDrawable(v.getResources().getDrawable(R.drawable.ic_call_made_black_24dp,getContext().getTheme()));
            }
            else if (p.getPayed()==2) {
                im.setImageDrawable(v.getResources().getDrawable(R.drawable.ic_call_received_black_24dp,getContext().getTheme()));
            }
            else if (p.getPayed()==3) {
                im.setImageDrawable(v.getResources().getDrawable(R.drawable.ic_check_black_24dp,getContext().getTheme()));
            }
        }

        return v;
    }

}