package ca.com.ca;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Toast;

import java.util.Locale;
import java.util.logging.Handler;
import java.util.logging.LogRecord;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class MyIntentService extends IntentService implements TextToSpeech.OnInitListener
{
    private String msg;

    TextToSpeech tts;

    public MyIntentService()
    {
        super("Super");
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
    }

    @Override
    //TextToSpeech OnInit
    public void onInit(int status)
    {
        if(status==TextToSpeech.SUCCESS)
        {
            int result=tts.setLanguage(Locale.US);

            if(result==TextToSpeech.LANG_MISSING_DATA||result==TextToSpeech.LANG_NOT_SUPPORTED)
            {
                Toast.makeText(this, "Language Not Supported", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Log.v("CA","onInit");

                for (int i=1; i <= 3; ++i)
                {
                    tts.setSpeechRate(0.9f);

                    tts.speak(msg,TextToSpeech.QUEUE_ADD,null);

                    Toast.makeText(this, msg+i, Toast.LENGTH_LONG).show();
                }
            }
        }
        else
        {
            Toast.makeText(this, "Initialisation Failed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        msg = intent.getStringExtra("MSG");

        Log.v("CA","onStart");

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy()
    {
        stopSelf();
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        Log.v("CA","onHandleIntent1");

        tts = new TextToSpeech(this.getApplicationContext(),this);

        Log.v("CA","onHandleIntent2");
    }

    public void sp()
    {
        Log.v("CA","speak");
    }
}
