package me.anikraj.iou;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Backendless.initApp(this, "3D5C89C7-FEF2-6DC6-FF50-1DB39A696400", "DEA24989-FD53-7D98-FFE5-4BD8CA0D7C00", "v1" ); // where to get the argument values for this call
        final EditText username=(EditText)findViewById(R.id.username);
        final EditText password=(EditText)findViewById(R.id.password);
        getSupportActionBar().hide();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View dup=view;
                Backendless.UserService.login( username.getText().toString(), password.getText().toString(), new AsyncCallback<BackendlessUser>()
                {
                    public void handleResponse( BackendlessUser user )
                    {
                       Toast.makeText(getApplicationContext(),"asd "+user.getUserId(),Toast.LENGTH_LONG).show();
                    }

                    public void handleFault( BackendlessFault fault )
                    {
                        // login failed, to get the error code call fault.getCode()
                        Snackbar.make(dup, "Error. "+fault.getMessage(), Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                });
                Snackbar.make(view, "Logging in.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }


    public void regclick(View v){
        startActivity(new Intent(Login.this,Register.class));
    }
}
