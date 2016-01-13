package me.anikraj.iou;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Backendless.initApp(this, "3D5C89C7-FEF2-6DC6-FF50-1DB39A696400", "DEA24989-FD53-7D98-FFE5-4BD8CA0D7C00", "v1" ); // where to get the argument values for this call
        final EditText username=(EditText)findViewById(R.id.username);
        final EditText repassword=(EditText)findViewById(R.id.repassword);
        final EditText password=(EditText)findViewById(R.id.password);




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(repassword.getText().toString().compareTo(password.getText().toString())!=0){
                    Snackbar.make(view, "Password mismatch.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else {
                    final View dup=view;
                    BackendlessUser user = new BackendlessUser();
                    user.setProperty( "email", username.getText().toString() );
                    user.setPassword( password.getText().toString() );

                    Backendless.UserService.register( user, new AsyncCallback<BackendlessUser>()
                    {
                        public void handleResponse( BackendlessUser registeredUser )
                        {
                            Toast.makeText(getApplicationContext(),"Sucess. Login to continue.",Toast.LENGTH_SHORT).show();
                            //startActivity(new Intent(Register.this,Login.class));
                            finish();
                        }

                        public void handleFault( BackendlessFault fault )
                        {
                            Snackbar.make(dup, "Error. "+fault.getMessage(), Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    } );
                    Snackbar.make(dup, "Registering. ", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
    }

}
