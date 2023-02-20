package ca.com.ca;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.AudioManager;
import android.net.Uri;
import android.provider.BaseColumns;
import android.provider.ContactsContract;
import android.speech.tts.TextToSpeech;
import android.telephony.PhoneStateListener;
import android.widget.Toast;

import java.io.Serializable;

/**
 * Created by LOKESHWAR REDDY on 18-Oct-17.
 */

public class MyPhoneStateChangeListener extends PhoneStateListener
{
    Context context;

    //Intent si;

    boolean shouldSpeak;

    int i,j;

    public String getContact(String ic)
    {
        Uri uri=Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI,Uri.encode(ic));

        String name="";

        ContentResolver cr = context.getContentResolver();

        Cursor c=cr.query(uri,new String[]{BaseColumns._ID,ContactsContract.PhoneLookup.DISPLAY_NAME},null,null,null);

        try
        {
            if(c!=null && c.getCount()>0)
            {
                c.moveToNext();

                name=c.getString(c.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
            }
            else if(c==null || c.getCount()==0)
            {
                name=ic;
            }
        }
        finally
        {
            if(c!=null)
            {
                c.close();
            }
        }

        return name;
    }

    void setContext(Context c, boolean b)
    {
        context = c;

        shouldSpeak = b;
    }

    @Override
    public void onCallStateChanged(int state, String incomingNumber)
    {
        String msg;

        if(state==1)
        {
            String name=getContact(incomingNumber);

            if(name.equals(incomingNumber))
            {
                name = name.replace(""," ").trim();

                msg = "Incoming Call From "+name;
            }
            else
            {
                msg="Incoming Call From "+name;
            }

            AudioManager am = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);

            int r = am.getRingerMode();

            if((!shouldSpeak) && (r == AudioManager.RINGER_MODE_SILENT))
            {
                ++i;

                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

                Toast.makeText(context, "Mode Silent : "+i, Toast.LENGTH_SHORT).show();
            }
            else
            {
                //++j;

                //Toast.makeText(context, "Mode Ringing : "+j, Toast.LENGTH_SHORT).show();

                Intent si = new Intent(context,MyIntentService.class);
                si.putExtra("MSG",msg);
                context.startService(si);
            }
        }
        else if(state==0)
        {
            Toast.makeText(context, "You just had a call", Toast.LENGTH_SHORT).show();
        }
    }
}
