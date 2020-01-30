//package com.example.imtiaz.mist;
//import android.content.Context;
//import android.support.v7.app.AppCompatActivity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.TextView;
//
//import java.util.List;
//
//public class CustomAdapter extends BaseAdapter {
//    List <PostInformation> stuinfo;
//    Context context;
//    private LayoutInflater inflater;
//
//    public CustomAdapter(Context c,List l) {
//        this.context=c;
//        this.stuinfo = l;
//    }
//
//    @Override
//    public int getCount() {
//        return stuinfo.size();
//    }
//
//    @Override
//    public Object getItem(int i) {
//        return null;
//    }
//
//    @Override
//    public long getItemId(int i) {
//        return 0;
//    }
//
//    @Override
//    public View getView(int i, View view, ViewGroup viewGroup) {
//        if(view==null){
//            inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            view=inflater.inflate(R.layout.sample2,viewGroup,false);
//
//        }
//
//        TextView textView1=view.findViewById(R.id.fsamplet1);
//        TextView textView3=view.findViewById(R.id.fsamplet3);
//        TextView textView2=view.findViewById(R.id.fsamplet2);
//
//        textView1.setText(stuinfo.get(i).getUsername());
//        textView3.setText(stuinfo.get(i).getTime());
//        textView2.setText(stuinfo.get(i).getPost());
//        return  view;
//    }
//}
