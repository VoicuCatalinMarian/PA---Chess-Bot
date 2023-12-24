import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Black {

    // Crazy-House Function
    public static void moveFromCrazyHouseForBlack(Move move, LinkedHashMap<Character, Integer> blackCrazyHouse, char[][] chess ) {
        Piece chosen = move.getReplacement().get();
        char chosenPiece = '-';

        if (chosen == Piece.PAWN) {
            chosenPiece = 'P';
            Bot.wantToChoose(chosenPiece, blackCrazyHouse);
        }

        if (chosen == Piece.QUEEN) {
            chosenPiece = 'Q';
            Bot.wantToChoose(chosenPiece, blackCrazyHouse);
        }

        if (chosen == Piece.BISHOP) {
            chosenPiece = 'B';
            Bot.wantToChoose(chosenPiece, blackCrazyHouse);
        }

        if (chosen == Piece.KNIGHT) {
            chosenPiece = 'N';
            Bot.wantToChoose(chosenPiece, blackCrazyHouse);
        }

        if (chosen == Piece.ROOK) {
            chosenPiece = 'R';
            Bot.wantToChoose(chosenPiece, blackCrazyHouse);
        }

        String dest = move.getDestination().get();
        int destinationX = 7 - dest.charAt(1) + 49;
        int destinationY = dest.charAt(0) - 97;
        chess[destinationX][destinationY] = chosenPiece;

        // Bot.printOutput(move);
    }

    // Function to update the table in our program

    public static void moveBlack(Move move, char[][] chess, LinkedHashMap<Character, Integer> whiteCrazyHouse, LinkedHashMap<Character, Integer> blackCrazyHouse) {
        // The source of the piece (the start position of the move)
        String src = move.getSource().get();
        // The destination of the piece (the end position of the move)
        String dest = move.getDestination().get();

        // The X and Y coordinates of the source
        int sourceX = 7 - src.charAt(1) + 49;
        int sourceY = src.charAt(0) - 97;
        // The X and Y coordinates of the destination
        int destinationX = 7 - dest.charAt(1) + 49;
        int destinationY = dest.charAt(0) - 97;

        if(sourceX == 0 && sourceY == 0)
            Bot.blackCastlingLong = 1;
        if(sourceX == 0 && sourceY == 7)
            Bot.blackCastlingShort = 1;
        if(sourceX == 0 && sourceY == 4)
            Bot.blackKing = 1;



        // The piece we want to move from the source to the destination
        char piece = chess[sourceX][sourceY];
        // At the beggining of the move, the destination space is empty
        chess[sourceX][sourceY] = '-';

        char putForBlack = (char)(chess[destinationX][destinationY] - 32);
        // "Crazy House" mode for black pieces (we save the piece in a dictionary after it's taken)
        if(putForBlack + 32 != '-' && blackCrazyHouse.containsKey(putForBlack)) {
            blackCrazyHouse.put(putForBlack, blackCrazyHouse.get(putForBlack) + 1);
        } else {
            blackCrazyHouse.put(putForBlack, 1);
        }

        // The destination will be the piece we moved
        chess[destinationX][destinationY] = piece;

        // The pown reached the adversary's house (the last line in the matrix leaving from black's side)
        if (7 - destinationX == 0 && chess[destinationX][destinationY] == 'P') {
            chess[destinationX][destinationY] = 'L';
        }

        // Big Castling
        if (sourceY - destinationY == 2 && piece == 'K') {
            chess[destinationX][destinationY + 1] = 'R';
            chess[destinationX][destinationY - 2] = '-';
            Bot.doneCastlingBlack = 1;
        }

        // Small Castling
        if (sourceY - destinationY == -2 && piece == 'K') {
            chess[destinationX][destinationY - 1] = 'R';
            chess[destinationX][destinationY + 1] = '-';
            Bot.doneCastlingBlack = 1;
        }
    }

    // Function for Pawn

    public static void find_pawn_moves(int src_x, int src_y, ArrayList<Position> white, ArrayList<Position> black, char[][] chess) {

        int up_black = src_x + 1;
        int up_black_two = src_x + 2;
        int left_black = src_y + 1;
        int right_black = src_y - 1;

        if (src_x == 1) {
            if (chess[up_black][src_y] == '-') {
                black.add(new Position(src_x, src_y, up_black, src_y));
                if (chess[up_black_two][src_y] == '-') {
                    black.add(new Position(src_x, src_y, up_black_two, src_y));
                }
            }

            if (left_black < 8 && up_black < 8 && chess[up_black][left_black] != '-' && chess[up_black][left_black] > 'a' && chess[up_black][left_black] < 'z') {
                black.add(new Position(src_x, src_y, up_black, left_black));
            }
            if (right_black >= 0 && up_black < 8 && chess[up_black][right_black] != '-' && chess[up_black][right_black] > 'a' && chess[up_black][right_black] < 'z') {
                black.add(new Position(src_x, src_y, up_black, right_black));
            }
        } else {
            if (up_black < 8 && chess[up_black][src_y] == '-') {
                black.add(new Position(src_x, src_y, up_black, src_y));
            }
            if (left_black < 8 && up_black < 8 && chess[up_black][left_black] != '-' && chess[up_black][left_black] > 'a' && chess[up_black][left_black] < 'z') {
                black.add(new Position(src_x, src_y, up_black, left_black));
            }
            if (right_black >= 0 && up_black < 8 && chess[up_black][right_black] != '-' && chess[up_black][right_black] > 'a' && chess[up_black][right_black] < 'z') {
                black.add(new Position(src_x, src_y, up_black, right_black));
            }
        }
    }

    public static void find_king_moves(int src_x, int src_y, ArrayList<Position> white, ArrayList<Position> black, char[][] chess) {
        int up = src_x + 1;
        int down = src_x - 1;
        int left = src_y + 1;
        int right = src_y - 1;

        if (up < 8 && (chess[up][src_y] < 'A' || chess[up][src_y] > 'Z'))
            black.add(new Position(src_x, src_y, up, src_y));
        if (down >= 0 && (chess[down][src_y] < 'A' || chess[down][src_y] > 'Z'))
            black.add(new Position(src_x, src_y, down, src_y));
        if (left < 8 && (chess[src_x][left] < 'A' || chess[src_x][left] > 'Z'))
            black.add(new Position(src_x, src_y, src_x, left));
        if (right >= 0 && (chess[src_x][right] < 'A' || chess[src_x][right] > 'Z'))
            black.add(new Position(src_x, src_y, src_x, right));
        if (up < 8 && left < 8 && (chess[up][left] < 'A' || chess[up][left] > 'Z'))
            black.add(new Position(src_x, src_y, up, left));
        if (up < 8 && right >= 0 && (chess[up][right] < 'A' || chess[up][right] > 'Z'))
            black.add(new Position(src_x, src_y, up, right));
        if (down >= 0 && left < 8 && (chess[down][left] < 'A' || chess[down][left] > 'Z'))
            black.add(new Position(src_x, src_y, down, left));
        if (down >= 0 && right >= 0 && (chess[down][right] < 'A' || chess[down][right] > 'Z'))
            black.add(new Position(src_x, src_y, down, right));
    }

    public static void find_bishop_moves(int src_x, int src_y, ArrayList<Position> white, ArrayList<Position> black, char[][] chess){
        // Primary diagonal up
        for (int i = 1; i < 8; i++) {
            if (src_x - i >= 0 && src_y - i >= 0 && chess[src_x - i][src_y - i] == '-')///liber
                black.add(new Position(src_x, src_y, src_x - i, src_y - i));
            else if (src_x - i >= 0 && src_y - i >= 0 && chess[src_x - i][src_y - i] > 'a' && chess[src_x - i][src_y - i] < 'z') { /// piesa adversa
                black.add(new Position(src_x, src_y, src_x - i, src_y - i));
                break;
            } else
                break;

        }
        // Primary diagonal down
        for (int i = 1; i < 8; i++) {
            if (src_x + i < 8 && src_y + i < 8 && chess[src_x + i][src_y + i] == '-')///liber
                black.add(new Position(src_x, src_y, src_x + i, src_y + i));
            else if (src_x + i < 8 && src_y + i < 8 && chess[src_x + i][src_y + i] > 'a' && chess[src_x + i][src_y + i] < 'z') { /// piesa adversa
                black.add(new Position(src_x, src_y, src_x + i, src_y + i));
                break;
            } else
                break;

        }
        // Secondary diagonal up
        for (int i = 1; i < 8; i++) {
            if (src_x - i >= 0 && src_y + i < 8 && chess[src_x - i][src_y + i] == '-')///liber
                black.add(new Position(src_x, src_y, src_x - i, src_y + i));
            else if (src_x - i >= 0 && src_y + i < 8 && chess[src_x - i][src_y + i] > 'a' && chess[src_x - i][src_y + i] < 'z') { /// piesa adversa
                black.add(new Position(src_x, src_y, src_x - i, src_y + i));
                break;
            } else
                break;

        }

        // Seconedary diagonal down
        for (int i = 1; i < 8; i++) {
            if (src_x + i < 8 && src_y - i >= 0 && chess[src_x + i][src_y - i] == '-')///liber
                black.add(new Position(src_x, src_y, src_x + i, src_y - i));
            else if (src_x + i < 8 && src_y - i >= 0 && chess[src_x + i][src_y - i] > 'a' && chess[src_x + i][src_y - i] < 'z') { /// piesa adversa
                black.add(new Position(src_x, src_y, src_x + i, src_y - i));
                break;
            } else
                break;

        }
    }

    public static void find_rook_moves(int src_x, int src_y, ArrayList<Position> white, ArrayList<Position> black, char[][] chess){
        // We run a "for" to add all the forward moves until we find an accupied place or the piece walks off the table and we stop the "for"
        for (int i = src_x - 1; i >= 0; i--) {
            if (chess[i][src_y] == '-') {
                black.add(new Position(src_x, src_y, i, src_y));
            } else if (chess[i][src_y] >= 'a' && chess[i][src_y] <= 'z') {
                black.add(new Position(src_x, src_y, i, src_y));
                break;
            } else {
                break;
            }
        }
        // We run a "for" to add all the backward moves until we find an accupied place or the piece walks off the table and we stop the "for"
        for (int i = src_x + 1; i <= 7; i++) {
            if (chess[i][src_y] == '-') {
                black.add(new Position(src_x, src_y, i, src_y));
            } else if (chess[i][src_y] >= 'a' && chess[i][src_y] <= 'z') {
                black.add(new Position(src_x, src_y, i, src_y));
                break;
            } else {
                break;
            }
        }
        // We run a "for" to add all the moves to the left until we find an accupied place or the piece walks off the table and we stop the "for"
        for (int i = src_y - 1; i >= 0; i--) {
            if (chess[src_x][i] == '-') {
                black.add(new Position(src_x, src_y, src_x, i));
            } else if (chess[src_x][i] >= 'a' && chess[src_x][i] <= 'z') {
                black.add(new Position(src_x, src_y, src_x, i));
                break;
            } else {
                break;
            }
        }
        // We run a "for" to add all the moves to the right until we find an accupied place or the piece walks off the table and we stop the "for"
        for (int i = src_y + 1; i <= 7; i++) {
            if (chess[src_x][i] == '-') {
                black.add(new Position(src_x, src_y, src_x, i));
            } else if (chess[src_x][i] >= 'a' && chess[src_x][i] <= 'z') {
                black.add(new Position(src_x, src_y, src_x, i));
                break;
            } else {
                break;
            }
        }
    }
}