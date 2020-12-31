package kr.ac.mjc.rssreader;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.stanfy.gsonxml.GsonXml;
import com.stanfy.gsonxml.GsonXmlBuilder;
import com.stanfy.gsonxml.XmlParserCreator;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NewsFragment extends Fragment {

    public NewsFragment(String url){
        this.mUrl=url;
    }

    String mUrl;
    NewsAdapter mAdapter;
    List<Item> newsList=new ArrayList();

    Handler handler =new Handler();

    XmlParserCreator parserCreator = new XmlParserCreator() {
        @Override
        public XmlPullParser createParser() {
            try {
                return XmlPullParserFactory.newInstance().newPullParser();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_news,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView listRv=view.findViewById(R.id.list_rv);


        mAdapter=new NewsAdapter(getActivity(),newsList );
        listRv.setAdapter(mAdapter);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        listRv.setLayoutManager(linearLayoutManager);


        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder()
                .url(mUrl)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("rssreader",e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                GsonXml gsonXml = new GsonXmlBuilder()
                        .setXmlParserCreator(parserCreator)
                        .setSameNameLists(true)
                        .create();
                String xml=response.body().string();

                Rss rss=gsonXml.fromXml(xml,Rss.class);
//                Log.d("channel",rss.getChannel().toString());
                for(Item item : rss.getChannel().getItem()){
                    Log.d("item title", item.getTitle());
                    newsList.add(item);
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.notifyDataSetChanged();
                    }
                });


//                Log.d("rssreader",response.body().string());
            }
        });
    }
}
