package ca.com.ca;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class MyBR extends BroadcastReceiver
{
    public MyBR()
    {

    }

    //public static Context con;

    SharedPreferences sp;


    @Override
    public void onReceive(Context context, Intent intent)
    {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        //Toast.makeText(context, "Walp Changed!!", Toast.LENGTH_LONG).show();


        //con=context;

        try
        {
            TelephonyManager tm=(TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);

            MyPhoneStateChangeListener mp=new MyPhoneStateChangeListener();

            sp = context.getSharedPreferences("KEY",Context.MODE_PRIVATE);

            boolean t = sp.getBoolean("k1",false);

            mp.setContext(context, t);

            tm.listen(mp, PhoneStateListener.LISTEN_CALL_STATE);

            tm.listen(mp,PhoneStateListener.LISTEN_NONE);
        }
        catch (Exception e)
        {
            Toast.makeText(context, "Exception", Toast.LENGTH_SHORT).show();
        }

        //Toast.makeText(context, "Hello", Toast.LENGTH_SHORT).show();

        //throw new UnsupportedOperationException("Not yet implemented");


    }
}
