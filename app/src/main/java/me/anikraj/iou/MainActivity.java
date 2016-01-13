package me.anikraj.iou;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
   
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TabWidget;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import org.w3c.dom.Text;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    TextView total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        Backendless.initApp(this, "3D5C89C7-FEF2-6DC6-FF50-1DB39A696400", "DEA24989-FD53-7D98-FFE5-4BD8CA0D7C00", "v1" ); // where to get the argument values for this call

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        total=(TextView)findViewById(R.id.total);



      FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
      fab.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
               final View dup = view;
         /*     OObject oobject =new OObject();
              oobject.setAmount(100);
              oobject.setDate(new Date().getTime());
              oobject.setEmail("ASdsad");
              oobject.setNumber(1231232);
              oobject.setWho("anik");
              oobject.setPayed(1);
              DatabaseHandler db = new DatabaseHandler(getApplicationContext());
              db.addContact(oobject);
              oobject.setPayed(2);
              db.addContact2(oobject); */
              startActivity(new Intent(MainActivity.this,Add.class));



           /*   Backendless.Persistence.save( oobject, new AsyncCallback<OObject>() {
                  public void handleResponse( OObject response )
                  {
                      Snackbar.make(dup, "added new object", Snackbar.LENGTH_LONG)
                              .setAction("Action", null).show();
                  }

                  public void handleFault( BackendlessFault fault )
                  {
                      Snackbar.make(dup, "Error. "+fault.getMessage(), Snackbar.LENGTH_LONG)
                              .setAction("Action", null).show();
                  }
              });  */








            //  Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)                      .setAction("Action", null).show();
              //startActivity(new Intent(MainActivity.this,Login.class));
          }
      });


        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)

                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();


        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withName("Home").withSelectable(false);
        PrimaryDrawerItem item2 = new PrimaryDrawerItem().withName("Logs").withSelectable(false);
        SecondaryDrawerItem item3 = new SecondaryDrawerItem().withName("Settings").withSelectable(false);

//create the drawer and remember the `Drawer` result object
        Drawer result = new DrawerBuilder()
                .withActivity(this) .withAccountHeader(headerResult)
                .addDrawerItems(
                        item1,
                        item2,
                        new DividerDrawerItem(),
                        item3
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // do something with the clicked item :D
                        if (position==0){

                        }
                        else if (position==1){
                            Toast.makeText(getApplicationContext(),"Future Feature.",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Future Feature.",Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    }
                })
                .build();
      
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences settings = getSharedPreferences("butt", 0);
        float owe=settings.getFloat("owe",0);
        float owed=settings.getFloat("owed",0);
        float totall=owed-owe;
        total.setText("\u20B9 "+totall);

        TabLayout vTabs = (TabLayout)findViewById(R.id.tabs);
        vTabs.getTabAt(0).setText("I Owe\n\u20B9 "+owe);
        vTabs.getTabAt(1).setText("IM Owed\n\u20B9 "+owed);
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        SharedPreferences settings = getSharedPreferences("butt", 0);
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new Owe();
                case 1:
                    return new Owed();
            }
            return null;

        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            float owe=settings.getFloat("owe",0);
            float owed=settings.getFloat("owed",0);
            switch (position) {
                case 0:
                    return "I Owe\n\u20B9 "+owe;
                case 1:
                    return "Im Owed\n\u20B9 "+owed;
            }
            return null;
        }
    }


}
