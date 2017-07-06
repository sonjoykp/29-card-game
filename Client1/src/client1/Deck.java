package client1;

import java.util.ArrayList;
import java.util.Random;

class Deck {
    private ArrayList<Card> cards;
    Deck()
    {
        cards = new ArrayList<Card>();
        int index_1, index_2;
        Random generator = new Random();
        Card temp;

        for (short suit=0; suit<=3; suit++)
        {
             for (short rank=0; rank<=7; rank++)
             {
                  cards.add(new Card(suit,rank));
             }
        }      
                   
        for (int i=0; i<1000; i++)
        {
            index_1 = generator.nextInt( cards.size() );
            index_2 = generator.nextInt( cards.size() );
            temp = cards.get( index_2 );                    
            cards.set( index_2 , cards.get( index_1 ) );
            cards.set( index_1, temp );
        }
    }
    public Card drawFromDeck()
    {       
        return cards.remove( 0 );
    }
    public int getTotalCards()
    {
        return cards.size();   
    }
 }