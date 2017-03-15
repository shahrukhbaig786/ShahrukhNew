package com.example.intel.virtual_receptionist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by intel on 12/14/2016.
 */
public class NavigationListAdapter extends BaseAdapter
{
    Context context;
    String[] title;
    int[] images;
    public NavigationListAdapter(Context context, String[] title, int[] images) {

        this.context = context;
        this.title = title;
        this.images = images;

    }

    @Override
    public int getCount() {
        return title.length;
    }

    @Override
    public Object getItem(int position) {
        return title[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View menuView = convertView;
        LayoutInflater inflater = LayoutInflater.from(context);
        menuView = inflater.inflate(R.layout.custom_navigation_title, parent, false);
        TextView menuTitle = (TextView) menuView.findViewById(R.id.menu_title);
        ImageView menuIcon = (ImageView) menuView.findViewById(R.id.menu_icon);
		/*Typeface tf = Typeface.createFromAsset(context.getAssets(),"open_sans_con.ttf");
		menuTitle.setTypeface(tf);*/
        menuTitle.setText(title[position]);
        menuIcon.setImageResource(images[position]);

        return menuView;
    }
}
