package com.ioa.jyh.broadcast;

import static android.provider.Telephony.Sms.Intents.SMS_RECEIVED_ACTION;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class SMSReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action=intent.getAction();
        if(action.equals(Intent.ACTION_NEW_OUTGOING_CALL) ){
            //사용자가 전화를 걸었을때
            Toast.makeText(context,"전화 거는중입니다",Toast.LENGTH_SHORT).show();
        }
        //문자를 받을때
        if(action.equals(SMS_RECEIVED_ACTION)){
            Toast.makeText(context,"문자받음",Toast.LENGTH_SHORT).show();
            Intent startMainIntent=new Intent(context,MainActivity.class);
            startMainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(startMainIntent);
        }
    }
}
