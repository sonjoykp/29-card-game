package client3;

class PlayerHands {

    public Card[] playerOneCards;
    public Card[] playerTwoCards;
    public Card[] playerThreeCards;
    public Card[] playerFourCards;

    PlayerHands(Deck d, int round) {
        playerOneCards = new Card[round];
        playerTwoCards = new Card[round];
        playerThreeCards = new Card[round];
        playerFourCards = new Card[round];

        for (int x = 0; x < round; x++) {
            playerOneCards[x] = d.drawFromDeck();
            playerTwoCards[x] = d.drawFromDeck();
            playerThreeCards[x] = d.drawFromDeck();
            playerFourCards[x] = d.drawFromDeck();
        }

        /*System.out.println("player 1:");
         for (int x=0; x<round; x++)
         {
         System.out.println(playerOneCards[x]) ; 
         }
         System.out.println("player 2:");
         for (int x=0; x<round; x++)
         {
         System.out.println(playerTwoCards[x]) ; 
         }*/
    }
}
