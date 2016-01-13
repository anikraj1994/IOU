package me.anikraj.iou;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class Show extends AppCompatActivity {
    DialogInterface.OnClickListener dialogClickListener;
    OObject obj;
    FloatingActionButton fabbut;
    int type;
    boolean canreduce=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        final DatabaseHandler db = new DatabaseHandler(this);
        final Intent i=getIntent();
        type=i.getIntExtra("type",0);
        TextView tv=(TextView)findViewById(R.id.textView);
        TextView tv1=(TextView)findViewById(R.id.textView1);
        TextView tv3=(TextView)findViewById(R.id.textView3);
        fabbut=(FloatingActionButton)findViewById(R.id.fab);


        if(i.getIntExtra("type",0)==1){
            obj=db.getContact(i.getIntExtra("id",0));
           tv.setText("You owe "+obj.getWho());
            tv1.setText("\u20B9 "+obj.getAmount());
            tv3.setText(new TimeAgo(this).timeagostring(obj.getDate())+"");
        }
        else {
            obj=db.getContact2(i.getIntExtra("id",0));
            tv.setText(obj.getWho()+" owes you");
            tv1.setText("\u20B9 "+obj.getAmount());
           tv3.setText(new TimeAgo(this).timeagostring(obj.getDate())+"");
        }
        if(obj.getPayed()==3){
            fabbut.setImageDrawable(this.getResources().getDrawable(R.drawable.ic_clear_white_24dp,this.getTheme()));
            canreduce=false;
        }
        else {
            canreduce=true;
        }

        final OObject obj2=obj;

        dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        if(i.getIntExtra("type",0)==1){
                            db.deleteContact(obj2);
                            Toast.makeText(Show.this, "Deleted!", Toast.LENGTH_SHORT).show();
                            if(canreduce){
                                SharedPreferences settings = getSharedPreferences("butt", 0);
                                SharedPreferences.Editor editor = settings.edit();
                                float owee=settings.getFloat("owe",0);
                                owee-=obj2.getAmount();
                                editor.putFloat("owe",owee);
                                // Commit the edits!
                                editor.commit();
                            }

                            finish();
                        }
                        else{
                            db.deleteContact2(obj2);
                            Toast.makeText(Show.this, "Deleted!", Toast.LENGTH_SHORT).show();
                            if(canreduce){
                                SharedPreferences settings = getSharedPreferences("butt", 0);
                                SharedPreferences.Editor editor = settings.edit();
                                float owedd=settings.getFloat("owed",0);
                                owedd-=obj2.getAmount();
                                editor.putFloat("owed",owedd);
                                // Commit the edits!
                                editor.commit();
                            }

                            finish();
                        }

                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

    }


    public void delete(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    public void fab(View v){
        DatabaseHandler db = new DatabaseHandler(this);
        if(obj.getPayed()!=3){
            obj.setPayed(3);
            if(type==1) {
                db.updateContact(obj);
                SharedPreferences settings = getSharedPreferences("butt", 0);
                SharedPreferences.Editor editor = settings.edit();
                float owee = settings.getFloat("owe", 0);
                owee -= obj.getAmount();
                editor.putFloat("owe", owee);
                // Commit the edits!
                editor.commit();
            }
            else {
                db.updateContact2(obj);
                SharedPreferences settings = getSharedPreferences("butt", 0);
                SharedPreferences.Editor editor = settings.edit();
                float owedd=settings.getFloat("owed",0);
                owedd-=obj.getAmount();
                editor.putFloat("owed",owedd);
                // Commit the edits!
                editor.commit();
            }
            Toast.makeText(this,"Marked payed.",Toast.LENGTH_SHORT).show();
            canreduce=false;
            fabbut.setImageDrawable(v.getResources().getDrawable(R.drawable.ic_clear_white_24dp,this.getTheme()));
        }
        else{
            if(type==1)obj.setPayed(1);
            else if(type==2)obj.setPayed(2);
            if(type==1){
                db.updateContact(obj);
                SharedPreferences settings = getSharedPreferences("butt", 0);
                SharedPreferences.Editor editor = settings.edit();
                float owee = settings.getFloat("owe", 0);
                owee += obj.getAmount();
                editor.putFloat("owe", owee);
                // Commit the edits!
                editor.commit();
            }
            else {
                db.updateContact2(obj);
                SharedPreferences settings = getSharedPreferences("butt", 0);
                SharedPreferences.Editor editor = settings.edit();
                float owedd=settings.getFloat("owed",0);
                owedd +=obj.getAmount();
                editor.putFloat("owed",owedd);
                // Commit the edits!
                editor.commit();
            }
            Toast.makeText(this,"Marked unpayed.",Toast.LENGTH_SHORT).show();
            canreduce=true;
            fabbut.setImageDrawable(v.getResources().getDrawable(R.drawable.ic_done_white_24dp,this.getTheme()));
        }
    }


    public void Watsapp(View view) {

        PackageManager pm=getPackageManager();
        try {

            Intent waIntent = new Intent(Intent.ACTION_SEND);
            waIntent.setType("text/plain");
            String text = "YOUR TEXT HERE";

            PackageInfo info=pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
            waIntent.setPackage("com.whatsapp");

            waIntent.putExtra(Intent.EXTRA_TEXT, text);
            startActivity(Intent.createChooser(waIntent, "Share with"));

        } catch (Exception e) {
            Toast.makeText(this, "WhatsApp not Installed", Toast.LENGTH_SHORT)
                    .show();
        }

    }

}
