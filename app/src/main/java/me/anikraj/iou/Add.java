package me.anikraj.iou;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Add extends AppCompatActivity {
    private static final int CONTACT_PICKER_RESULT = 1001;
    boolean owed=true;
    Add thisifier;
    DialogInterface.OnClickListener dialogClickListener;
    String name=null,number=null;
    EditText et;
    Button o,ob,pb;
    DatabaseHandler db;
    TextView prev;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        getSupportActionBar().hide();
        o=(Button)findViewById(R.id.button2);
        ob=(Button)findViewById(R.id.button3);
        pb=(Button)findViewById(R.id.button);
        et=(EditText)findViewById(R.id.editText);
        thisifier=this;
       db = new DatabaseHandler(getApplicationContext());
        prev=(TextView)findViewById(R.id.prev);




        dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences settings = getSharedPreferences("butt", 0);
                SharedPreferences.Editor editor = settings.edit();
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked

                        editor.putInt("addpreferance",0);
                        editor.commit();
                        add();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        editor.putInt("addpreferance",1);
                        editor.commit();
                        seperate();
                        break;
                }
            }
        };

    }

    public void o(View v){
        o.setBackground(v.getResources().getDrawable(R.drawable.addbuttonafter,v.getContext().getTheme()));
        ob.setBackground(v.getResources().getDrawable(R.drawable.addbuttonbefore,v.getContext().getTheme()));
        owed=false;
    }

    public void ob(View v){
        ob.setBackground(v.getResources().getDrawable(R.drawable.addbuttonafter,v.getContext().getTheme()));
        o.setBackground(v.getResources().getDrawable(R.drawable.addbuttonbefore,v.getContext().getTheme()));
        owed=true;
    }

    public void fab(View v){

        if(name==null) Snackbar.make(getCurrentFocus(), "Choose person.", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        else if(et.getText().toString().length()==0)Snackbar.make(getCurrentFocus(), "Enter Amount.", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        else {
            SharedPreferences settings = getSharedPreferences("butt", 0);
            if (settings.getInt("addpreferance", -1) == -1) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                LayoutInflater inflater = this.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.alertadd, null);

                AlertDialog.Builder builder2 =
                        new AlertDialog.Builder(this);
                builder2.setMessage("Choose insert type")
                        .setView(dialogView)
                        .setPositiveButton("Add", dialogClickListener)
                        .setNegativeButton("Seperate", dialogClickListener)
                        .show();
                ;

            } else if (settings.getInt("addpreferance", -1) == 0) add();
            else if (settings.getInt("addpreferance", -1) == 1) seperate();
        }
    }

  public void add(){
      OObject oobject =new OObject();
      oobject.setAmount(Double.parseDouble(et.getText().toString()));
      oobject.setDate(new Date().getTime());
      oobject.setEmail("null");
      oobject.setNumber(Long.parseLong(number));
      oobject.setWho(name);

      if(owed)oobject.setPayed(2);
      else oobject.setPayed(1);

      SharedPreferences settings = getSharedPreferences("butt", 0);
      SharedPreferences.Editor editor = settings.edit();
      float owes=settings.getFloat("owe",0);
      float oweds=settings.getFloat("owed",0);
      float owet=0,owedt=0;




      List<OObject> owe = db.getAllContactsByNum(Long.parseLong(number));
      List<OObject> owed = db.getAllContactsByNum2(Long.parseLong(number));
      if(owe.size()==0&&owed.size()==0)
          seperate();
      else{
          double totaletemp=0;
          for(int i=0;i<owe.size();i++){
              if(owe.get(i).getPayed()==3)continue;
              totaletemp-=owe.get(i).getAmount();
              db.deleteContact(owe.get(i));
              owet+=owe.get(i).getAmount();
          }
          for(int i=0;i<owed.size();i++){
              if(owed.get(i).getPayed()==3)continue;
              totaletemp+=owed.get(i).getAmount();
              db.deleteContact2(owed.get(i));
              owedt+=owed.get(i).getAmount();
          }

          owes-=owet;oweds-=owedt;

          if(totaletemp>0){
              if(oobject.getPayed()==2){
                oobject.setAmount(oobject.getAmount()+totaletemp);
                db.addContact2(oobject);Toast.makeText(this,name+" owes you \u20B9"+oobject.getAmount(),Toast.LENGTH_SHORT).show();
                oweds+=oobject.getAmount();
              }
              else{
                  totaletemp-=oobject.getAmount();
                  if(totaletemp>0){
                      oobject.setPayed(2);
                      oobject.setAmount(totaletemp);
                      db.addContact2(oobject);Toast.makeText(this,name+" owes you \u20B9"+oobject.getAmount(),Toast.LENGTH_SHORT).show();
                      oweds+=oobject.getAmount();
                  }
                  else if(totaletemp<0){
                      oobject.setPayed(1);
                      oobject.setAmount(-totaletemp);
                      db.addContact(oobject);Toast.makeText(this,"You owe "+name+" \u20B9"+oobject.getAmount(),Toast.LENGTH_SHORT).show();
                      owes+=oobject.getAmount();
                  }
                  else {
                      Toast.makeText(this,"Amount balanced with "+oobject.getWho(),Toast.LENGTH_SHORT).show();
                  }
              }
          }
          else{
              if(oobject.getPayed()==1){
                  oobject.setAmount(oobject.getAmount()-totaletemp);
                  db.addContact(oobject);Toast.makeText(this,"You owe "+name+" \u20B9"+oobject.getAmount(),Toast.LENGTH_SHORT).show();
                  owes+=oobject.getAmount();
              }
              else{
                  totaletemp+=oobject.getAmount();
                  if(totaletemp>0){
                      oobject.setAmount(totaletemp);
                      db.addContact2(oobject);Toast.makeText(this,name+" owes you \u20B9"+oobject.getAmount(),Toast.LENGTH_SHORT).show();
                      oweds+=oobject.getAmount();

                  }
                  else if(totaletemp<0){
                      oobject.setAmount(-totaletemp);
                      db.addContact(oobject);Toast.makeText(this,"You owe "+name+" \u20B9"+oobject.getAmount(),Toast.LENGTH_SHORT).show();
                      owes+=oobject.getAmount();
                  }
                  else {
                      Toast.makeText(this,"Amount balanced with "+oobject.getWho(),Toast.LENGTH_SHORT).show();
                  }
              }

          }
          editor.putFloat("owe",owes);
          editor.putFloat("owed",oweds);
          // Commit the edits!
          editor.commit();
            finish();
      }
  }

    public void seperate(){

            OObject oobject =new OObject();
            oobject.setAmount(Double.parseDouble(et.getText().toString()));
            oobject.setDate(new Date().getTime());
            oobject.setEmail("null");
            oobject.setNumber(Long.parseLong(number));
            oobject.setWho(name);

            if(owed)oobject.setPayed(2);
            else oobject.setPayed(1);




            if(owed) {db.addContact2(oobject);Toast.makeText(this,name+" owes you \u20B9"+et.getText().toString(),Toast.LENGTH_SHORT).show();}
            else{ db.addContact(oobject);Toast.makeText(this,"You owe "+name+" \u20B9"+et.getText().toString(),Toast.LENGTH_SHORT).show();}

            SharedPreferences settings = getSharedPreferences("butt", 0);
            SharedPreferences.Editor editor = settings.edit();
            float owee=settings.getFloat("owe",0);
            float owedd=settings.getFloat("owed",0);

            if(owed)owedd+=Double.parseDouble(et.getText().toString());
            else owee+=Double.parseDouble(et.getText().toString());

            editor.putFloat("owe",owee);
            editor.putFloat("owed",owedd);
            // Commit the edits!
            editor.commit();



            finish();




    }

    public void doLaunchContactPicker(View view) {
        Intent i = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
        startActivityForResult(i, CONTACT_PICKER_RESULT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CONTACT_PICKER_RESULT && resultCode == RESULT_OK) {
            Uri contactUri = data.getData();
            String[] projection    = new String[] {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                    ContactsContract.CommonDataKinds.Phone.NUMBER};

            Cursor cursor = getContentResolver().query(contactUri, projection, null, null, null);
            int indexName = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            int indexNumber = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

            cursor.moveToFirst();
            name   = cursor.getString(indexName);
            number = cursor.getString(indexNumber);
            pb.setBackground(getResources().getDrawable(R.drawable.addbuttonafter,this.getTheme()));
            pb.setText(name);
            number = number.replaceAll("[^0-9]", "");
            new GetPrev().execute(Long.parseLong(number));
        }

    }


    private class GetPrev extends AsyncTask<Long, Void, String> {

        @Override
        protected String doInBackground(Long... params) {
            List<OObject> owe = db.getAllContactsByNum(params[0]);
            List<OObject> owed = db.getAllContactsByNum2(params[0]);
            if(owe.size()==0&&owed.size()==0)
            return null;
            else{
                String x="";
                for(int i=0;i<owe.size();i++){
                    if(owe.get(i).getPayed()==3)continue;
                    x+="I owe "+owe.get(i).getWho()+" \u20B9 "+owe.get(i).getAmount()+"\n";
                }
                for(int i=0;i<owed.size();i++){
                    if(owed.get(i).getPayed()==3)continue;
                    x+=owed.get(i).getWho()+" owes me \u20B9 "+owed.get(i).getAmount()+"\n";
                }
                return x;
            }
        }

        @Override
        protected void onPostExecute(String a) {
            if(a!=null){
                prev.setVisibility(View.VISIBLE);
                prev.setText(a);
            }
            else{
                prev.setVisibility(View.GONE);
            }
        }
    }
}
