package modelPackage;

import java.util.ArrayList;

public class GameManager {
    public ArrayList<Game> getGames() {
        return games;
    }

    public void setGames(ArrayList<Game> games) {
        this.games = games;
    }

    private ArrayList<Game> games;

    //Singleton Design Implementation
    private static GameManager instance;
    private GameManager(){
        this.games= new ArrayList<>();
    }
    public static GameManager getInstance(){
        if(instance== null)
        {
            instance= new GameManager();
        }
        return instance;
    }
    public int NumberOfGames()
    {
        return games.size();
    }
    public void insertAtIndex(int index, Game game)
    {
         games.set(index,game);
    }
    public void insert(Game g)
    {
        games.add(g);
    }
    public Game retrieve(int index)
    {
        return games.get(index);
    }
    public void remove(int index)
    {
        games.remove(index);
    }
    public ArrayList<String> gamesStr()
    {
        ArrayList<String> a =new ArrayList<>();
        for(Game gg: games)
        {
            a.add(gg.converter());
        }
        return a;
    }

}
