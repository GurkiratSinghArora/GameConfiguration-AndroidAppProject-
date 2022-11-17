package com.example.assignment2recode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

import modelPackage.Game;
import modelPackage.GameManager;
import modelPackage.playerScore;

public class MainActivity2 extends AppCompatActivity {
    GameManager gamemanager;
    Game game;
    playerScore p1;
    playerScore p2;
    private TextView score1;
    private TextView score2;
    private EditText txtPlayer1cards;
    private EditText txtPlayer2cards;
    private EditText sumPoints1;
    private EditText sumPoints2;
    private EditText numberWager1;
    private EditText numberWager2;
    private TextView editTextDate;
    private TextView result;
    public static final String ADD="ADD";
    public static final String EDIT="EDIT";
    public static final String ACTION="ACTION";
    public static final String INDEX ="INDEX";
    String action;
    int index;
    public void setImage1(View view)
    {
        ImageView img = (ImageView) view;
        int tappedImage = Integer.parseInt(img.getTag().toString());
        game.setImageNumber(tappedImage);
    }
    public void setImage2(View view)
    {
        ImageView img = (ImageView) view;
        int tappedImage = Integer.parseInt(img.getTag().toString());
        game.setImageNumber(tappedImage);
    }
    public void setImage3(View view)
    {
        ImageView img = (ImageView) view;
        int tappedImage = Integer.parseInt(img.getTag().toString());
        game.setImageNumber(tappedImage);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        gamemanager = GameManager.getInstance();
        extractDataFromIntent();
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        txtPlayer1cards = findViewById(R.id.player1cards);
        txtPlayer2cards = findViewById(R.id.player2cards);
        sumPoints1 = findViewById(R.id.sumPoints1);
        sumPoints2 = findViewById(R.id.sumPoints2);
        numberWager1 = findViewById(R.id.numberWager1);
        numberWager2 = findViewById(R.id.numberWager2);
        editTextDate = findViewById(R.id.editDate);
        score1 = findViewById(R.id.scores1);
        score2 = findViewById(R.id.scores2);
        editTextDate.setText(game.getDate());
        result = findViewById(R.id.Results);
//        ((ImageView)findViewById(R.id.imageView1).setImageResource(R.drawable.p3);
//        ((ImageView)findViewById(R.id.imageView2).setImageResource(R.drawable.p1));
//        ImageView image3=findViewById(R.id.imageView3);
//        ((ImageView)findViewById(R.id.imageView0)).setImageResource(0);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) // Result appears in real time as soon as all the values are entered
            {
                if (!txtPlayer1cards.getText().toString().equals("") && !sumPoints1.getText().toString().equals("") && !numberWager1.getText().toString().equals("")) {
                    int temp1 = Integer.parseInt(txtPlayer1cards.getText().toString());
                    int temp2 = Integer.parseInt(sumPoints1.getText().toString());
                    int temp3 = Integer.parseInt(numberWager1.getText().toString());
                    p1 = new playerScore(temp1, temp2, temp3);
                    score1.setText(String.valueOf(p1.totalScore()));
                }
                if (!txtPlayer2cards.getText().toString().equals("") && !sumPoints2.getText().toString().equals("") && !numberWager2.getText().toString().equals("")) {
                    int temp4 = Integer.parseInt(txtPlayer2cards.getText().toString());
                    int temp5 = Integer.parseInt(sumPoints2.getText().toString());
                    int temp6 = Integer.parseInt(numberWager2.getText().toString());
                    p2 = new playerScore(temp4, temp5, temp6);
                    score2.setText(String.valueOf(p2.totalScore()));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!txtPlayer1cards.getText().toString().equals("") && !sumPoints1.getText().toString().equals("") && !numberWager1.getText().toString().equals("") && !txtPlayer2cards.getText().toString().equals("") && !sumPoints2.getText().toString().equals("") && !numberWager2.getText().toString().equals("")) {
                    if (p1.totalScore() > p2.totalScore()) {
                        result.setText("Player1 is winner");
                    } else if (p1.totalScore() < p2.totalScore()) {
                        result.setText("Player2 is winner");
                    } else {
                        result.setText("Tie Game");
                    }
                }

            }
        };
        txtPlayer1cards.addTextChangedListener(textWatcher);
        txtPlayer2cards.addTextChangedListener(textWatcher);
        sumPoints1.addTextChangedListener(textWatcher);
        sumPoints2.addTextChangedListener(textWatcher);
        numberWager1.addTextChangedListener(textWatcher);
        numberWager2.addTextChangedListener(textWatcher);
        setupEndActivityButton();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main2, menu);
        if(action.equals(EDIT)) {
            getMenuInflater().inflate(R.menu.deletemain2, menu);
        }
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                game.assign(0, p1);
                game.assign(1, p2);
                if(action.equals(ADD))
                    gamemanager.insert(game);
                else
                    gamemanager.insertAtIndex(index,game);
                Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(intent);
                return true;
            case android.R.id.home:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                builder1.setMessage("Do you want to discard your changes?").setTitle("Discard Changes").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //TODO - Click "No" does not function right now. Should come back to the activity along with the changes.
                                Intent intent = new Intent(MainActivity2.this, MainActivity2.class);
                                startActivity(intent);
                            }
                        });
                AlertDialog dialog = builder1.create();
                dialog.show();
                return true;
            case R.id.action_delete:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Do you want to delete?").setTitle("Confirm Delete").setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog1, int which) {
                                gamemanager.remove(index);
                                if(index==0)
                                {
                                    deleteSharedPrefData();
                                }
                                Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                                startActivity(intent);
                            }
                        });
                AlertDialog dialog1 = builder.create();
                dialog1.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static Intent makeIntent(Context context) {
        Intent intent = new Intent(context, MainActivity2.class);
        return intent;
    }
    private void setupEndActivityButton()
    {
        if(action.equals(EDIT))
        {
            txtPlayer1cards.setText(""+p1.getNumberOfCards());
            sumPoints1.setText(""+ p1.getSumPoints());
            numberWager1.setText(""+p1.getNumberOfWagercards());
            txtPlayer2cards.setText(""+p2.getNumberOfCards());
            sumPoints2.setText(""+ p2.getSumPoints());
            numberWager2.setText(""+p2.getNumberOfWagercards());

        }
    }
    private void extractDataFromIntent()
    {
        Intent intent= getIntent();
        action = intent.getSerializableExtra(ACTION).toString();
        if(action.equals(ADD))
        {
            game = new Game();
            getSupportActionBar().setTitle("New Game Score");
        }
        else
        {
            index = (int) (intent.getSerializableExtra(INDEX));
            game = gamemanager.retrieve(index);
            getSupportActionBar().setTitle("Edit Game Score");
        }
        p1= new playerScore(game.returnplayerscore(0));
        p2= new playerScore(game.returnplayerscore(1));
    }
    private void deleteSharedPrefData()
    {
        SharedPreferences sharedPreferences= getApplicationContext().getSharedPreferences("DATA",MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        Gson gson = new Gson();
        ArrayList<String> gameManagerShared= new ArrayList<>();
        String json = gson.toJson(gameManagerShared);
        editor.putString("Game_data",json);
        editor.apply();
    }
}