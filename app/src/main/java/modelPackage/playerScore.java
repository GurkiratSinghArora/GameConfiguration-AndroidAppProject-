package modelPackage;

public class playerScore {
    private int numberOfWagercards;
    private int sumPoints;
    private int numberOfCards;

    public playerScore(playerScore player)
    {
        this.numberOfCards= player.numberOfCards;
        this.numberOfWagercards= player.numberOfWagercards;
        this.sumPoints= player.sumPoints;
    }
    public playerScore()
    {}

    public playerScore(int cards, int points, int wagecards)
    {
        if(cards==0)
        {
            numberOfCards=0;
            numberOfWagercards=0;
            sumPoints=0;
        }
        else
        {
            setNumberOfCards(cards);
            setSumPoints(points);
            setNumberOfWagercards(wagecards);
        }
    }
    public int getNumberOfWagercards() {
        return numberOfWagercards;
    }

    public void setNumberOfWagercards(int numberOfWagercards) {
        this.numberOfWagercards = numberOfWagercards;
    }

    public int getSumPoints() {
        return sumPoints;
    }

    public void setSumPoints(int sumPoints) {
        this.sumPoints = sumPoints;
    }

    public int getNumberOfCards() {
        return numberOfCards;
    }

    public void setNumberOfCards(int numberOfCards) {
        this.numberOfCards = numberOfCards;
    }
    public int totalScore()
    {
        if(numberOfCards==0)
            return 0;
        int sum = sumPoints-20;
        sum=sum *(numberOfWagercards+1);
        if(numberOfCards>=8)
            sum=sum+20;
        return sum;
    }
}
