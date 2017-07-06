package client3;

 class Card
{
    public short rank, suit;
    public String[] suits = { "c", "h", "s", "d"};
    public String[] ranks = { "j","9", "a", "t", "k", "q", "8", "7" };

    Card(short suit, short rank)
    {
        this.rank=rank;
        this.suit=suit;    
    }
   
    public @Override String toString()
    {
        return ranks[rank]+suits[suit];              
    }

    public short getRank() {
         return rank;
    }

    public short getSuit() {
        return suit;
    }
}