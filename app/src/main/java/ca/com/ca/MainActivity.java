package ca.com.ca;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    public static Context c;

    Switch s;

    boolean is;

    SharedPreferences sp;

    SharedPreferences.Editor spe;

    Button b;

    boolean isEnable;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        s = (Switch)findViewById(R.id.switch1);

        sp = getSharedPreferences("KEY",MODE_PRIVATE);

        spe = sp.edit();

        boolean t = sp.getBoolean("k1",false);

        if(t)
        {
            s.setChecked(t);
        }

        t = sp.getBoolean("k2",false);

        b = (Button)findViewById(R.id.b1);

        b.setEnabled(t);

        c = this.getApplicationContext();

        int p1=checkSelfPermission(Manifest.permission.READ_PHONE_STATE);

        int p2=checkSelfPermission(Manifest.permission.READ_CONTACTS);

        List<String> perm=new ArrayList<String>();

        if(p1!= PackageManager.PERMISSION_GRANTED)
        {
            perm.add(Manifest.permission.READ_PHONE_STATE);
        }

        if(p2!=PackageManager.PERMISSION_GRANTED)
        {
            perm.add(Manifest.permission.READ_CONTACTS);
        }

        if(!perm.isEmpty())
        {
            ActivityCompat.requestPermissions(this,perm.toArray(new String[perm.size()]),1234);
        }

        s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                is = isChecked;

                isEnable = true;

                b.setEnabled(isEnable);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        boolean t = false;

        for(int i=0;i<grantResults.length;++i)
        {
            if(grantResults[i]==PackageManager.PERMISSION_GRANTED)
            {
                t = true;
            }
            else
            {
                t = false;

                break;
            }
        }

        if(t)
        {
            Toast.makeText(this, "Permissions Granted", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "Permissions Not Granted", Toast.LENGTH_SHORT).show();

            finish();
        }

        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void saveChanges(View v)
    {
        isEnable = false;

        b.setEnabled(isEnable);

        spe.putBoolean("k1",is);

        spe.putBoolean("k2",isEnable);

        spe.commit();

        Toast.makeText(c, "Checked : "+is, Toast.LENGTH_LONG).show();
    }
}