package kr.co.hhsoft.twitch_example;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class GameItemLayout extends LinearLayout {
    ImageView imageIv;
    TextView titleTv;
    TextView channelTv;
    TextView viewerTv;

    public GameItemLayout(Context context) {
        super(context);
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.item_game,this,true);
        imageIv=findViewById(R.id.image_iv);
        titleTv=findViewById(R.id.title_tv);
        channelTv=findViewById(R.id.channel_tv);
        viewerTv=findViewById(R.id.viewer_tv);
    }

    public void setTop(Top top){
        if(top!=null){
            Game game=top.getGame();
            titleTv.setText(game.getName());
            channelTv.setText(String.format("채널 %s",top.getChannels()));
            viewerTv.setText(String.format("시청자 %s",top.getViewers()));
            Glide.with(imageIv).load(game.getLogo().getLarge()).into(imageIv);
        }
    }
}
