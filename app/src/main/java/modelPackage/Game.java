package modelPackage;

import com.example.assignment2recode.R;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Game {
    private int numberOfPlayers;
    private ArrayList<playerScore> player;
    private String date;
    private int ImageID;

    public int getImageID() {
        return ImageID;
    }

    public void setImageID() {
        if(player.get(0).totalScore()>player.get(1).totalScore())
            ImageID = R.drawable.player1;
        else if(player.get(0).totalScore()<player.get(1).totalScore())
            ImageID= R.drawable.player2;
        else
            ImageID= R.drawable.tiegame;

    }

    public ArrayList<playerScore> getPlayer() {
        return player;
    }

    public int getImageNumber() {
        return imageNumber;
    }
    private int imageNumber;
    public void setImageNumber(int imageNumber) {
        this.imageNumber=imageNumber;
    }

    public playerScore returnplayerscore(int index)
    {
        return player.get(index);
    }
    public void assign(int index,playerScore p1)
    {
        player.set(index,p1);
    }
    public Game()
    {
        imageNumber= 0;
        numberOfPlayers=2;
        player= new ArrayList<playerScore>();
        playerScore p1= new playerScore();
        playerScore p2= new playerScore();
        player.add(p1);
        player.add(p2);
        LocalDateTime temp_date= LocalDateTime.now();
        DateTimeFormatter myformat= DateTimeFormatter.ofPattern("MMM d @ HH:mm a");
        String formatted_date = temp_date.format(myformat);
        date= formatted_date;
    }
    public Game(playerScore p1,playerScore p2, String Date)
    {
        player = new ArrayList();
        player.add(p1);
        player.add(p2);
        date = Date;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public String converter() {
        String str;
        str = this.date + "-";
        if (player.get(0).totalScore() > player.get(1).totalScore()) {
            str += "Player 1 won: " + player.get(0).totalScore() + " vs " + player.get(1).totalScore();
        }
        else if(player.get(0).totalScore() < player.get(1).totalScore())
        {
            str += "Player 2 won: " + player.get(0).totalScore() + " vs " + player.get(1).totalScore();
        }
        else
        {
            str += "Tie game: " + player.get(0).totalScore() + " vs " + player.get(1).totalScore();
        }
        return str;
    }

}
