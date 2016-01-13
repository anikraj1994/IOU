package me.anikraj.iou;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class Owe extends Fragment {
    SwipeRefreshLayout swipeLayout;
    Toast toast;
    ListView listview;
    public Owe thisifier =null;

    public Owe() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_owe, container, false);
        final DatabaseHandler db = new DatabaseHandler(getContext());
        listview = (ListView) v.findViewById(R.id.listView);
       // new ReadCardsLoc().execute("k");
       // new ReadCards().execute("k");
        List<OObject> contacts = db.getAllContacts();
        listview.setAdapter(new CustomAdapter2(getActivity().getBaseContext(), R.layout.row, contacts));
        thisifier=this;
        swipeLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //new ReadCards().execute("k");
                List<OObject> contacts = db.getAllContacts();

                Collections.sort(contacts, new Comparator<OObject>(){
                    public int compare(OObject emp1, OObject emp2) {
                        return emp1.getPayed()-(emp2.getPayed());
                    }
                });

                listview.setAdapter(new CustomAdapter2(getActivity().getBaseContext(), R.layout.row, contacts));
                swipeLayout.setRefreshing(false);
            }
        });

       // swipeLayout.setColorSchemeResources(R.color.orange, R.color.green, R.color.blue);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
            {
                List<OObject> contacts = db.getAllContacts();
                //Toast.makeText(getContext(), "" + contacts.get(position).getEmail(), Toast.LENGTH_SHORT).show();
                Collections.sort(contacts, new Comparator<OObject>(){
                    public int compare(OObject emp1, OObject emp2) {
                        return emp1.getPayed()-(emp2.getPayed());
                    }
                });
                Intent i=new Intent(arg1.getContext(),Show.class);
                i.putExtra("type",1);
                i.putExtra("id",Integer.parseInt(contacts.get(position).getEmail()));
                startActivity(i);
            }
        });

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        final DatabaseHandler db = new DatabaseHandler(getContext());
        List<OObject> contacts = db.getAllContacts();
        Collections.sort(contacts, new Comparator<OObject>(){
            public int compare(OObject emp1, OObject emp2) {
                return emp1.getPayed()-(emp2.getPayed());
            }
        });
        listview.setAdapter(new CustomAdapter2(getActivity().getBaseContext(), R.layout.ro, contacts));
    }

}
