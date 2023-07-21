package com.internshla.bookaholicapp;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class SecondScreen extends AppCompatActivity {

    ListView listView;
    Animation animation;
    String[] title;

    int[] bookImages= {R.drawable.twilight,R.drawable.new_moon, R.drawable.eclipse, R.drawable.breaking_dawn,R.drawable.notebook, R.drawable.walk,R.drawable.lucky,R.drawable.see_me};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_screen);

        listView=findViewById(R.id.list_view);
        title=getResources().getStringArray(R.array.title);
        MainAdapter adapter=new MainAdapter(SecondScreen.this,title,bookImages);
        animation= AnimationUtils.loadAnimation(this,R.anim.animation1);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                Intent intent=new Intent(SecondScreen.this,third_screen.class);
                startActivity(intent);
            }
        });
    }
}