package com.internshla.bookaholicapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;



public class MainAdapter extends BaseAdapter {

    SecondScreen SecondScreen;
    String[] title;
    int[] listImages;

    Animation animation;

    public MainAdapter(SecondScreen secondScreen, String[] title, int[] images) {

        this.SecondScreen = secondScreen;
        this.title = title;
        this.listImages=images;
    }

    public static int getRandom(int max) {
        return (int) (Math.random() * max);

    }

    @Override
    public int getCount() {
        return title.length;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = LayoutInflater.from(SecondScreen).inflate(R.layout.new_item_layout, viewGroup, false);
        animation = AnimationUtils.loadAnimation(SecondScreen, R.anim.animation1);

        TextView textView;
        ImageView bookImges=(ImageView)view.findViewById(R.id.imageIcon) ;

        LinearLayout ll_bg;
        ll_bg = view.findViewById(R.id.ll_bg);
        textView = view.findViewById(R.id.textView);


        int number = getRandom(8);
        if (number == 1) {
            ll_bg.setBackground(ContextCompat.getDrawable(SecondScreen, R.drawable.gradient_1));

        } else
        {
            ll_bg.setBackground(ContextCompat.getDrawable(SecondScreen, R.drawable.gradient_2));
        }

        textView.setText(title[i]);
        textView.setAnimation(animation);
        bookImges.setImageResource(listImages[i]);
        return view;

    }
}


