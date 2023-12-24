Instructions of compilation:
	-> to compile we use the command:
	   "make"
	-> to run, the command from the project's regulament is used:
	   "xboard -fcp "make run" -debug"
	-> to delete the files with extension ".class" we use:
	   "make clean"

The bot does't work if we change from White to Black, but 1v1 or with custom moves,
it works until the moment in which we reach a tie or checkmate.

Details about the project's strucure:
	-> for the implementation of the project we used data structures that already
	   existed in Java, like "ArrayList", "LinkedHashMap", as well as primitive
	   data structures, like arrays and matrix
	-> for the chessboard we chose a char[][] matrix of 8x8
	-> we used an "ArrayList" to store the random moves at every step
	-> we used a "LinkedHashMap" to remember the "Crazy House" pieces
	-> we used an "ArrayList" for the specific moves for "Crazy House", which
	   we add to the "ArrayList" with the final moves
	-> in "recordMove" is registered every move and the chessboard is updated
	   with the new piece

The algorithmic aproach:
	-> we didn't use any specific algorithm, the moves that the bot chooses to do
	   are random (they are extracted from the "ArrayList" with the moves for the
	   colour, using the class "Random" from Java)

Sources of insipartion:
	-> we had no insipartion for the bot's moves (everything was made by hand, 
	from scratch), and for the "Crazy House" we used Chess.com