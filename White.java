import java.util.ArrayList;
import java.util.LinkedHashMap;

public class White {

    // Crazy-House Function
    public static void moveFromCrazyHouseForWhite(Move move, LinkedHashMap<Character, Integer> whiteCrazyHouse, char[][] chess) {
        Piece chosen = move.getReplacement().get();
        char chosenPiece = '-';

        if (chosen == Piece.PAWN) {
            chosenPiece = 'p';
            Bot.wantToChoose(chosenPiece, whiteCrazyHouse);
        }

        if (chosen == Piece.QUEEN) {
            chosenPiece = 'q';
            Bot.wantToChoose(chosenPiece, whiteCrazyHouse);
        }

        if (chosen == Piece.BISHOP) {
            chosenPiece = 'b';
            Bot.wantToChoose(chosenPiece, whiteCrazyHouse);
        }

        if (chosen == Piece.KNIGHT) {
            chosenPiece = 'n';
            Bot.wantToChoose(chosenPiece, whiteCrazyHouse);
        }

        if (chosen == Piece.ROOK) {
            chosenPiece = 'r';
            Bot.wantToChoose(chosenPiece, whiteCrazyHouse);
        }

        String dest = move.getDestination().get();
        int destinationX = 7 - dest.charAt(1) + 49;
        int destinationY = dest.charAt(0) - 97;
        chess[destinationX][destinationY] = chosenPiece;

        // Bot.printOutput(move);
    }

    // Function to update the table in our program

    public static void moveWhite(Move move, char[][] chess, LinkedHashMap<Character, Integer> whiteCrazyHouse, LinkedHashMap<Character, Integer> blackCrazyHouse) {
            String src = move.getSource().get();
            String dest = move.getDestination().get();

            int sourceX = 7 - src.charAt(1) + 49;
            int sourceY = src.charAt(0) - 97;
            int destinationX = 7 - dest.charAt(1) + 49;
            int destinationY = dest.charAt(0) - 97;

            if(sourceX == 7 && sourceY == 0)
                Bot.whiteCastlingLong = 1;
            if(sourceX == 7 && sourceY == 7)
                Bot.whiteCastlingShort = 1;
            if(sourceX == 7 && sourceY == 4)
                Bot.whiteKing = 1;

            char piece = chess[sourceX][sourceY];
            chess[sourceX][sourceY] = '-';

            char putForWhite = (char)(chess[destinationX][destinationY] + 32);

            if(putForWhite - 32 != '-' && whiteCrazyHouse.containsKey(putForWhite)) {
                whiteCrazyHouse.put(putForWhite, whiteCrazyHouse.get(putForWhite) + 1);
            } else {
                whiteCrazyHouse.put(putForWhite, 1);
            }

            chess[destinationX][destinationY] = piece;

            if (7 - dest.charAt(1) + 49 == 0 && chess[7 - dest.charAt(1) + 49][dest.charAt(0) - 97] == 'p') {
                chess[destinationX][destinationY] = 'l';
            }

            if (sourceY - destinationY == 2 && piece == 'k') {
                chess[destinationX][destinationY + 1] = 'r';
                chess[destinationX][destinationY - 2] = '-';
                Bot.doneCastlingWhite = 1;
            }

            if (sourceY - destinationY == -2 && piece == 'k') {
                chess[destinationX][destinationY - 1] = 'r';
                chess[destinationX][destinationY + 1] = '-';
                Bot.doneCastlingWhite = 1;
            }

    }

    // Function for Pawn

    public static void find_pawn_moves(int src_x, int src_y, ArrayList<Position> white, ArrayList<Position> black, char[][] chess) {

        int up_white = src_x - 1;
        int up_white_two = src_x - 2;
        int left_white = src_y - 1;
        int right_white = src_y + 1;

        if (src_x == 6) {
            if (chess[up_white][src_y] == '-') {
                white.add(new Position(src_x, src_y, up_white, src_y));
                if (chess[up_white_two][src_y] == '-') {
                    white.add(new Position(src_x, src_y, up_white_two, src_y));
                }
            }
            if (left_white >= 0 && up_white >= 0 && chess[up_white][left_white] != '-' && chess[up_white][left_white] > 'A' && chess[up_white][left_white] < 'Z') {
                white.add(new Position(src_x, src_y, up_white, left_white));
            }
            if (right_white < 8 && up_white >= 0 && chess[up_white][right_white] != '-' && chess[up_white][right_white] > 'A' && chess[up_white][right_white] < 'Z') {
                white.add(new Position(src_x, src_y, up_white, right_white));
            }
        } else {
            if (up_white >= 0 && chess[up_white][src_y] == '-') {
                white.add(new Position(src_x, src_y, up_white, src_y));
            }
            if (left_white >= 0 && up_white >= 0 && chess[up_white][left_white] != '-' && chess[up_white][left_white] > 'A' && chess[up_white][left_white] < 'Z') {
                white.add(new Position(src_x, src_y, up_white, left_white));
            }
            if (right_white < 8 && up_white >= 0 && chess[up_white][right_white] != '-' && chess[up_white][right_white] > 'A' && chess[up_white][right_white] < 'Z') {
                white.add(new Position(src_x, src_y, up_white, right_white));
            }
        }
    }

    public static void find_king_moves(int src_x, int src_y, ArrayList<Position> white, ArrayList<Position> black, char[][] chess) {
        int up = src_x - 1;
        int down = src_x + 1;
        int left = src_y - 1;
        int right = src_y + 1;

        if (up >= 0 && (chess[up][src_y] < 'a' || chess[up][src_y] > 'z'))
            white.add(new Position(src_x, src_y, up, src_y));
        if (down < 8 && (chess[down][src_y] < 'a' || chess[down][src_y] > 'z'))
            white.add(new Position(src_x, src_y, down, src_y));
        if (left >= 0 && (chess[src_x][left] < 'a' || chess[src_x][left] > 'z'))
            white.add(new Position(src_x, src_y, src_x, left));
        if (right < 8 && (chess[src_x][right] < 'a' || chess[src_x][right] > 'z'))
            white.add(new Position(src_x, src_y, src_x, right));
        if (up >= 0 && left >= 0 && (chess[up][left] < 'a' || chess[up][left] > 'z'))
            white.add(new Position(src_x, src_y, up, left));
        if (up >= 0 && right < 8 && (chess[up][right] < 'a' || chess[up][right] > 'z'))
            white.add(new Position(src_x, src_y, up, right));
        if (down < 8 && left >= 0 && (chess[down][left] < 'a' || chess[down][left] > 'z'))
            white.add(new Position(src_x, src_y, down, left));
        if (down < 8 && right < 8 && (chess[down][right] < 'a' || chess[down][right] > 'z'))
            white.add(new Position(src_x, src_y, down, right));
    }

    public static void find_bishop_moves(int src_x, int src_y, ArrayList<Position> white, ArrayList<Position> black, char[][] chess){
        // Primary diagonal up
        for (int i = 1; i < 8; i++) {
            if (src_x - i >= 0 && src_y - i >= 0 && chess[src_x - i][src_y - i] == '-')///liber
                white.add(new Position(src_x, src_y, src_x - i, src_y - i));
            else if (src_x - i >= 0 && src_y - i >= 0 && chess[src_x - i][src_y - i] > 'A' && chess[src_x - i][src_y - i] < 'Z') { /// piesa adversa
                white.add(new Position(src_x, src_y, src_x - i, src_y - i));
                break;
            } else
                break;

        }
        // Primary diagonal down
        for (int i = 1; i < 8; i++) {
            if (src_x + i < 8 && src_y + i < 8 && chess[src_x + i][src_y + i] == '-')///liber
                white.add(new Position(src_x, src_y, src_x + i, src_y + i));
            else if (src_x + i < 8 && src_y + i < 8 && chess[src_x + i][src_y + i] > 'A' && chess[src_x + i][src_y + i] < 'Z') { /// piesa adversa
                white.add(new Position(src_x, src_y, src_x + i, src_y + i));
                break;
            } else
                break;

        }
        // Secondary diagonal up
        for (int i = 1; i < 8; i++) {
            if (src_x - i >= 0 && src_y + i < 8 && chess[src_x - i][src_y + i] == '-')///liber
                white.add(new Position(src_x, src_y, src_x - i, src_y + i));
            else if (src_x - i >= 0 && src_y + i < 8 && chess[src_x - i][src_y + i] > 'A' && chess[src_x - i][src_y + i] < 'Z') { /// piesa adversa
                white.add(new Position(src_x, src_y, src_x - i, src_y + i));
                break;
            } else
                break;

        }

        // Secondary diagonal down
        for (int i = 1; i < 8; i++) {
            if (src_x + i < 8 && src_y - i >= 0 && chess[src_x + i][src_y - i] == '-')///liber
                white.add(new Position(src_x, src_y, src_x + i, src_y - i));
            else if (src_x + i < 8 && src_y - i >= 0 && chess[src_x + i][src_y - i] > 'A' && chess[src_x + i][src_y - i] < 'Z') { /// piesa adversa
                white.add(new Position(src_x, src_y, src_x + i, src_y - i));
                break;
            } else
                break;

        }
    }

    public static void find_rook_moves(int src_x, int src_y, ArrayList<Position> white, ArrayList<Position> black, char[][] chess){
        // We run a "for" to add all the forward moves until we find an accupied place or the piece walks off the table and we stop the "for"
        for (int i = src_x - 1; i >= 0; i--) {
            if (chess[i][src_y] == '-') {
                white.add(new Position(src_x, src_y, i, src_y));
            } else if (chess[i][src_y] >= 'A' && chess[i][src_y] <= 'Z') {
                white.add(new Position(src_x, src_y, i, src_y));
                break;
            } else {
                break;
            }
        }
        // We run a "for" to add all the backward moves until we find an accupied place or the piece walks off the table and we stop the "for"
        for (int i = src_x + 1; i <= 7; i++) {
            if (chess[i][src_y] == '-') {
                white.add(new Position(src_x, src_y, i, src_y));
            } else if (chess[i][src_y] >= 'A' && chess[i][src_y] <= 'Z') {
                white.add(new Position(src_x, src_y, i, src_y));
                break;
            } else {
                break;
            }
        }
        // We run a "for" to add all the moves to the left until we find an accupied place or the piece walks off the table and we stop the "for"
        for (int i = src_y - 1; i >= 0; i--) {
            if (chess[src_x][i] == '-') {
                white.add(new Position(src_x, src_y, src_x, i));
            } else if (chess[src_x][i] >= 'A' && chess[src_x][i] <= 'Z') {
                white.add(new Position(src_x, src_y, src_x, i));
                break;
            } else {
                break;
            }
        }
        // We run a "for" to add all the moves to the right until we find an accupied place or the piece walks off the table and we stop the "for"
        for (int i = src_y + 1; i <= 7; i++) {
            if (chess[src_x][i] == '-') {
                white.add(new Position(src_x, src_y, src_x, i));

            } else if (chess[src_x][i] >= 'A' && chess[src_x][i] <= 'Z') {
                white.add(new Position(src_x, src_y, src_x, i));
                break;
            } else {
                break;
            }
        }
    }
}