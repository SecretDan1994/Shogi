****************************
What is Shogi?
****************************
@author Daniel Llerena

@version 0.993

The following is a project for the board game Shogi, commonly known as Japanese chess.

It's turn-based and uses the pieces: Pawn, Rook, Bishop, Lance, Knight, SilverGeneral, GoldGeneral, and King.

All pieces have an alliance to either the Black Team or the White Team   

Traditionally the Black Team faces upwards and the White Team faces downwards. The Black Team also goes first.

The goal of the game is to capture opposing pieces with an end-goal of capturing the enemy king.

An interesting mechanic of the game is that any pieces you capture come to your capture-zone and on any of your turns you can
drop a piece you have captured onto the board as a piece of your own.

You can also march your pieces to your opponent's side of the board which is considered the "promotion-zone" where you have the choice
of wheter or not you want to promote your piece.

******************
Promotion Rules
******************
1. Pawn promotes to gold general (called a 'tokin' in this case only).
2. Lance promotes to gold general.
3. Knight promotes to gold general.
4. Silver general promotes to gold general.
5. Gold general does not promote.
6. Bishop promotes to "dragon horse" or just "horse" for short. The horse can move as a bishop or can move one square in any orthogonal direction.
7. Rook promotes to "dragon king" or just "dragon" for short. The dragon can move as a rook or can move one square in any diagonal direction.
8. King does not promote.

# Here is a screenshot example of a rook approaching the promotion zone and the promotion occuring after choosing yes:

****************************
What has been created so far
****************************
- Turn-based two player functionality
- All pieces are properly placed in their starting positions.
- Pieces only move where they are allowed to move.
- Player can drop a piece from their captured pieces panel.
- All piece-promotions and promotions choice correctly work.

***********************************
Immediate Goals that have yet to be fulfilled
***********************************
- Sorting out bugs with the dropping piece system.
- Sorting out general bugs with invalid moves; Could possibly throw an invalid move error when this happens.
- Adding the win conditions for check and checkmate.
- Adding a UI,Menuing kind of system.
- Adding some kind of tutorial.
- Adding visual move paths to let players know where they can move the pieces.
- Visual showing whose turn it is.
- Adding a ranking system similar to traditional Shogi

***************
Here is a Screenshot of the App
***************
![Image](https://github.com/SecretDan1994/Shogi/blob/master/src/images/screenshot.png)

***************
Acknowledgement
***************

I would like to take the time to thank you for viewing the GitHub for this project. Feel free to download the project and try it out.
