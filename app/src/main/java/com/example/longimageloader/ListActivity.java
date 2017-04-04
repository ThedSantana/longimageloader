package com.example.longimageloader;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.sheiud.redbible.longimageloader.LongImageLoader;

import java.util.ArrayList;

/**
 * Created by bible on 2017. 3. 23..
 */

public class ListActivity extends AppCompatActivity {
    private ArrayList<String> str;
    private BaseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ListView list = (ListView)findViewById(R.id.list);

        String url = "http://www.pravs.co.kr/shop/lib/meditor/../../data/editor/1467710929.jpg";

//        LongImageLoader.getInstance().setListView(this, list, url);
        str = getDummy();
        adapter = new StringAdapter(this, str);
        LongImageLoader.getInstance().setListView(this, list, url, adapter);

        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str.addAll(getDummy());
                Log.d("hong", "add Items");
                LongImageLoader.getInstance().notifyDataSetChanged(adapter);
            }
        });
    }

    private ArrayList<String> getDummy(){
        ArrayList<String> str = new ArrayList<>();
        str.add("asdasd");
        str.add("qqqqq");
        str.add("wwww");
        str.add("eeee");
        str.add("rrrr");
        str.add("tttt");
        str.add("asdasd");
        str.add("qqqqq");
        str.add("wwww");
        str.add("eeee");
        str.add("rrrr");
        str.add("tttt");
        str.add("asdasd");
        str.add("qqqqq");
        str.add("wwww");
        str.add("eeee");
        str.add("rrrr");
        str.add("tttt");
        str.add("asdasd");
        str.add("qqqqq");
        str.add("wwww");
        str.add("eeee");
        str.add("rrrr");
        str.add("tttt");
        str.add("asdasd");
        str.add("qqqqq");
        str.add("wwww");
        str.add("eeee");
        str.add("rrrr");
        str.add("tttt");
        str.add("asdasd");
        str.add("qqqqq");
        str.add("wwww");
        str.add("eeee");
        str.add("rrrr");
        str.add("tttt");

        return str;
    }

    class StringAdapter extends BaseAdapter {
        private final ArrayList<String> items;
        protected final LayoutInflater inflater;

        public StringAdapter(Context context, ArrayList<String> items) {
            this.items = items;
            this.inflater = LayoutInflater.from(context);
        }

        public void addAll(ArrayList<String> strings){
            this.items.addAll(strings);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View vi, ViewGroup parent) {
            TextView txt;
            if(vi == null){
                vi = inflater.inflate(R.layout.view_text, parent, false);
                txt = (TextView) vi.findViewById(R.id.txt_hong);
                vi.setTag(txt);
            } else {
                txt = (TextView) vi.getTag();
            }

            txt.setText(items.get(position));

            return vi;
        }
    }
}
