package com.example.assignment2recode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import modelPackage.Game;
import modelPackage.GameManager;

public class MainActivity extends AppCompatActivity {
    GameManager gamemanager;
    ArrayList<String> items;
    ArrayList<Game> gameManagerShared= new ArrayList<Game>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gamemanager= GameManager.getInstance();
        loadData();
        if(gamemanager.getGames().size()!=0)
        {
            TextView txt= findViewById(R.id.textDescription);
            ImageView img= findViewById(R.id.DiceImage);
            txt.setVisibility(View.GONE);
            img.setVisibility(View.GONE);
        }
    }
    @Override
    protected void onResume()
    {
        super.onResume();
        creatinglistview();
        saveData();
        setUpPlusButton();
        registerClickCallBack();
        loadData();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        creatinglistview();
        loadData();
        setUpPlusButton();
        registerClickCallBack();
        saveData();

    }
    private void registerClickCallBack() {
        ListView list = findViewById(R.id.listview);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                TextView textview= (TextView) viewClicked;
                String message= "You clicked # "+ position + ", which is string: "+ textview.getText().toString();
                Toast.makeText(MainActivity.this,message,Toast.LENGTH_LONG).show();
                Intent intent = MainActivity2.makeIntent(MainActivity.this);
                intent.putExtra(MainActivity2.ACTION, MainActivity2.EDIT);
                intent.putExtra(MainActivity2.INDEX,position);
                startActivity(intent);

            }
        });
    }
    private void loadData() {
        SharedPreferences sharedPreferences= getApplicationContext().getSharedPreferences("DATA", MODE_PRIVATE);
        Gson gson = new Gson();
        String json =sharedPreferences.getString("Game_data", null);
        Type type= new TypeToken<ArrayList<Game>>(){
        }.getType();
        gameManagerShared= gson.fromJson(json,type);
        if(gameManagerShared==null)
        {
            gameManagerShared= new ArrayList<>();
        }
        else if(gameManagerShared.size()!=0 && gamemanager.getGames().size()==0)
        {
            for(Game g: gameManagerShared)
            {
                gamemanager.insert(g);
            }
        }
    }

    private void setUpPlusButton() {
        FloatingActionButton btn = findViewById(R.id.floatingBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this,MainActivity2.class);
                intent.putExtra(MainActivity2.ACTION,MainActivity2.ADD);
                startActivity(intent);
            }
        });

    }

    private void creatinglistview() {
        items= gamemanager.gamesStr();
        saveData();
        ArrayAdapter<String> adapter =new MyListAdapter();
        ListView list=findViewById(R.id.listview);
        list.setAdapter(adapter);
    }
    private class MyListAdapter extends ArrayAdapter<String>{
        public MyListAdapter() {
            super(MainActivity.this, R.layout.item_design,items );
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            View itemView = convertView;
            if(itemView == null)
            {
                itemView= getLayoutInflater().inflate(R.layout.item_design,parent,false);
            }

            Game g= gamemanager.getGames().get(position);
            String str= gamemanager.gamesStr().get(position);
            ImageView imageView= (ImageView)itemView.findViewById(R.id.item_icon);
            g.setImageID();
            imageView.setImageResource(g.getImageID());

            TextView makeText= itemView.findViewById(R.id.textView);
            makeText.setText(str);
            return itemView;
        }
    }

    private void saveData() {
        SharedPreferences sharedPreferences= getApplicationContext().getSharedPreferences("DATA",MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        Gson gson = new Gson();
        if(gamemanager.getGames().size()>0 && gamemanager.getGames().size()!=gameManagerShared.size())
            gameManagerShared.add(gamemanager.getGames().get(gamemanager.getGames().size()-1));
        if(gamemanager.getGames().size()<gameManagerShared.size() || gameManagerShared.size() == gamemanager.getGames().size())
        {
            gameManagerShared= new ArrayList<>();
            for(Game g: gamemanager.getGames())
            {
                gameManagerShared.add(g);
            }
        }
        String json = gson.toJson(gameManagerShared);
        editor.putString("Game_data",json);
        editor.apply();
        //loadData();
    }
}