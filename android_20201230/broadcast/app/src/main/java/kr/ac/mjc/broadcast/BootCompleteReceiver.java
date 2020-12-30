package kr.ac.mjc.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class BootCompleteReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action=intent.getAction();
        Log.d("booting","boot complted");
        if(action.equals(Intent.ACTION_BOOT_COMPLETED)){
            Toast.makeText(context,"부팅 완료!!",Toast.LENGTH_SHORT).show();
        }
    }
}
