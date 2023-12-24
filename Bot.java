import java.security.SecureRandom;
import java.util.*;

public class Bot {
    /* Edit this, escaped characters (e.g newlines, quotes) are prohibited */
    private static final String BOT_NAME = "MyBot";

    /* Declare custom fields below */

    private static char[][] chess;
    private static LinkedHashMap<Character, Integer> whiteCrazyHouse;
    private static LinkedHashMap<Character, Integer> blackCrazyHouse;


    private Random random;

    static ArrayList<Position> positionsToAttackWhite;

    ArrayList<Position> nextPositionToAttackWhite;
    static ArrayList<Position> positionsToAttackBlack;
    ArrayList<Position> nextPositionToAttackBlack;

    private int capacityWhite = 0;
    private int capacityBlack = 0;

    public static int whiteCastlingShort = -1;
    public static int whiteCastlingLong = -1;
    public static int whiteKing = -1;
    public static int doneCastlingWhite = -1;
    public static int blackCastlingShort = -1;
    public static int blackCastlingLong = -1;
    public static int blackKing = -1;
    public static int doneCastlingBlack = -1;


    public static Position lastPositionWhite = new Position(0, 0, 0, 0);
    public static Position lastPositionBlack = new Position(0, 0, 0, 0);

    /* Declare custom fields above */

    public Bot() {
        chess = new char[8][8];
        whiteCrazyHouse = new LinkedHashMap<>();
        blackCrazyHouse = new LinkedHashMap<>();
        positionsToAttackWhite = new ArrayList<Position>();
        nextPositionToAttackWhite = new ArrayList<Position>();
        nextPositionToAttackBlack = new ArrayList<Position>();
        positionsToAttackBlack = new ArrayList<Position>();
        random = new SecureRandom();
        init();
    }

    public void init() {
        for (int i = 0; i < 8; i++) {
            chess[1][i] = 'P';
        }

        chess[0][0] = 'R';
        chess[0][1] = 'N';
        chess[0][2] = 'B';
        chess[0][3] = 'Q';
        chess[0][4] = 'K';
        chess[0][5] = 'B';
        chess[0][6] = 'N';
        chess[0][7] = 'R';

        for (int i = 2; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                chess[i][j] = '-';
            }
        }

        for (int i = 0; i < 8; i++) {
            chess[6][i] = 'p';
        }

        chess[7][0] = 'r';
        chess[7][1] = 'n';
        chess[7][2] = 'b';
        chess[7][3] = 'q';
        chess[7][4] = 'k';
        chess[7][5] = 'b';
        chess[7][6] = 'n';
        chess[7][7] = 'r';
    }

    public static void wantToChoose(char piece, LinkedHashMap<Character, Integer> crazyHouse) {
        if (crazyHouse.containsKey(piece)) {
            int countTypeForPiece = crazyHouse.get(piece);
            if (countTypeForPiece > 1) {
                crazyHouse.put(piece, countTypeForPiece - 1);
            } else {
                crazyHouse.remove(piece, countTypeForPiece);
            }
        }
        else if(crazyHouse.containsKey('l')){
            int countTypeForPiece = crazyHouse.get('l');
            if (countTypeForPiece > 1) {
                crazyHouse.put('l', countTypeForPiece - 1);
            } else {
                crazyHouse.remove('l', countTypeForPiece);
            }
        }
        else if(crazyHouse.containsKey('L')){
            int countTypeForPiece = crazyHouse.get('L');
            if (countTypeForPiece > 1) {
                crazyHouse.put('L', countTypeForPiece - 1);
            } else {
                crazyHouse.remove('L', countTypeForPiece);
            }
        }
    }


    /**
     * Record received move (either by enemy in normal play,
     * or by both sides in force mode) in custom structures
     *
     * @param move       received move
     * @param sideToMove side to move (either PlaySide.BLACK or PlaySide.WHITE)
     */

    public void recordMove(Move move, PlaySide sideToMove) {
        // Black
        if (sideToMove == PlaySide.BLACK) {
            // If the piece is placed from "Crazy House" back on the table
            if (move.getSource().orElse("-").compareTo("-") == 0) {
                Black.moveFromCrazyHouseForBlack(move, blackCrazyHouse, chess);
            } else {
                Black.moveBlack(move, chess, whiteCrazyHouse, blackCrazyHouse);
            }
        }

        // White
        if (sideToMove == PlaySide.WHITE) {
            // If the piece is placed from "Crazy House" back on the table

            if (move.getSource().orElse("-").compareTo("-") == 0) {
                White.moveFromCrazyHouseForWhite(move, whiteCrazyHouse, chess);
            } else {
                White.moveWhite(move, chess, whiteCrazyHouse, blackCrazyHouse);
            }
        }

        printOutput(move);
    }

    public static void printOutput(Move move) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(chess[i][j] + " ");
            }

            System.out.println();
        }

        System.out.println();

        whiteCrazyHouse.remove('M', 1);

        if (whiteCrazyHouse.size() != 0) {
            for (Map.Entry<Character, Integer> entry : whiteCrazyHouse.entrySet()) {
                System.out.println("W  -> " + entry.getKey() + " : " + entry.getValue());
            }
        }

        System.out.println();

        blackCrazyHouse.remove('M', 1);

        if (blackCrazyHouse.size() != 0) {
            for (Map.Entry<Character, Integer> entry : blackCrazyHouse.entrySet()) {
                System.out.println("B  -> " + entry.getKey() + " : " + entry.getValue());
            }
        }

        System.out.println();

        // // Without this conditions, an exception appears
        // if (move.getSource() == NULL) {
        //     System.out.println(move.getDestination().get());
        //  } else if (move.getSource().isPresent()) {
        //      System.out.println(move.getSource().get() + " " + move.getDestination().get())
        
        for (Position i : positionsToAttackWhite)
            System.out.println(i);

        System.out.println("-------");

        for (Position i : positionsToAttackBlack)
            System.out.println(i);
    }

    public void find_pawn_moves(int src_x, int src_y, ArrayList<Position> white, ArrayList<Position> black, char c) {

        if (chess[src_x][src_y] == 'p')
            White.find_pawn_moves(src_x, src_y, white, black, chess);
        else
            Black.find_pawn_moves(src_x, src_y, white, black, chess);
    }

    public void find_bishop_moves(int src_x, int src_y, ArrayList<Position> white, ArrayList<Position> black, char c) {
        if (chess[src_x][src_y] == 'b' || chess[src_x][src_y] == 'q' || chess[src_x][src_y] == 'l')
            White.find_bishop_moves(src_x, src_y, white, black, chess);
        else
            Black.find_bishop_moves(src_x, src_y, white, black, chess);

    }

    public void find_rook_moves(int src_x, int src_y, ArrayList<Position> white, ArrayList<Position> black, char c) {
        
        // We check if a Rook is on the present position
        // If the bot is White
        if (chess[src_x][src_y] == 'r' || chess[src_x][src_y] == 'q' || chess[src_x][src_y] == 'l')
            White.find_rook_moves(src_x, src_y, white, black, chess);
            // If the bot is Black
        else
            Black.find_rook_moves(src_x, src_y, white, black, chess);
    }

    public void find_knight_moves(int src_x, int src_y, ArrayList<Position> white, ArrayList<Position> black, char c) {
        // White
        if (chess[src_x][src_y] == 'n') {

            // Up-Left
            if (src_x - 2 >= 0 && src_y - 1 >= 0 && (chess[src_x - 2][src_y - 1] == '-' || (chess[src_x - 2][src_y - 1] > 'A' && chess[src_x - 2][src_y - 1] < 'Z'))) {
                white.add(new Position(src_x, src_y, src_x - 2, src_y - 1));
            }
            // Up-Right
            if (src_x - 2 >= 0 && src_y + 1 < 8 && (chess[src_x - 2][src_y + 1] == '-' || (chess[src_x - 2][src_y + 1] > 'A' && chess[src_x - 2][src_y + 1] < 'Z'))) {
                white.add(new Position(src_x, src_y, src_x - 2, src_y + 1));
            }
            // Down-Left
            if (src_x + 2 < 8 && src_y - 1 >= 0 && (chess[src_x + 2][src_y - 1] == '-' || (chess[src_x + 2][src_y - 1] > 'A' && chess[src_x + 2][src_y - 1] < 'Z'))) {
                white.add(new Position(src_x, src_y, src_x + 2, src_y - 1));
            }
            // Down-Right
            if (src_x + 2 < 8 && src_y + 1 < 8 && (chess[src_x + 2][src_y + 1] == '-' || (chess[src_x + 2][src_y + 1] > 'A' && chess[src_x + 2][src_y + 1] < 'Z'))) {
                white.add(new Position(src_x, src_y, src_x + 2, src_y + 1));
            }
            // Left-Up
            if (src_x - 1 >= 0 && src_y - 2 >= 0 && (chess[src_x - 1][src_y - 2] == '-' || (chess[src_x - 1][src_y - 2] > 'A' && chess[src_x - 1][src_y - 2] < 'Z'))) {
                white.add(new Position(src_x, src_y, src_x - 1, src_y - 2));
            }
            // Left-Down
            if (src_x + 1 < 8 && src_y - 2 >= 0 && (chess[src_x + 1][src_y - 2] == '-' || (chess[src_x + 1][src_y - 2] > 'A' && chess[src_x + 1][src_y - 2] < 'Z'))) {
                white.add(new Position(src_x, src_y, src_x + 1, src_y - 2));
            }
            // Right-Up
            if (src_x - 1 >= 0 && src_y + 2 < 8 && (chess[src_x - 1][src_y + 2] == '-' || (chess[src_x - 1][src_y + 2] > 'A' && chess[src_x - 1][src_y + 2] < 'Z'))) {
                white.add(new Position(src_x, src_y, src_x - 1, src_y + 2));
            }
            // Right-Down
            if (src_x + 1 < 8 && src_y + 2 < 8 && (chess[src_x + 1][src_y + 2] == '-' || (chess[src_x + 1][src_y + 2] > 'A' && chess[src_x + 1][src_y + 2] < 'Z'))) {
                white.add(new Position(src_x, src_y, src_x + 1, src_y + 2));
            }
        //Blac
        } else if (chess[src_x][src_y] == 'N') {
            // Up-Left
            if (src_x - 2 >= 0 && src_y - 1 >= 0 && (chess[src_x - 2][src_y - 1] == '-' || (chess[src_x - 2][src_y - 1] > 'a' && chess[src_x - 2][src_y - 1] < 'z'))) {
                black.add(new Position(src_x, src_y, src_x - 2, src_y - 1));
            }
            // Up-Right
            if (src_x - 2 >= 0 && src_y + 1 < 8 && (chess[src_x - 2][src_y + 1] == '-' || (chess[src_x - 2][src_y + 1] > 'a' && chess[src_x - 2][src_y + 1] < 'z'))) {
                black.add(new Position(src_x, src_y, src_x - 2, src_y + 1));
            }
            // Down-Left
            if (src_x + 2 < 8 && src_y - 1 >= 0 && (chess[src_x + 2][src_y - 1] == '-' || (chess[src_x + 2][src_y - 1] > 'a' && chess[src_x + 2][src_y - 1] < 'z'))) {
                black.add(new Position(src_x, src_y, src_x + 2, src_y - 1));
            }
            // Down-Right
            if (src_x + 2 < 8 && src_y + 1 < 8 && (chess[src_x + 2][src_y + 1] == '-' || (chess[src_x + 2][src_y + 1] > 'a' && chess[src_x + 2][src_y + 1] < 'z'))) {
                black.add(new Position(src_x, src_y, src_x + 2, src_y + 1));
            }
            // Left-Up
            if (src_x - 1 >= 0 && src_y - 2 >= 0 && (chess[src_x - 1][src_y - 2] == '-' || (chess[src_x - 1][src_y - 2] > 'a' && chess[src_x - 1][src_y - 2] < 'z'))) {
                black.add(new Position(src_x, src_y, src_x - 1, src_y - 2));
            }
            // Left-Down
            if (src_x + 1 < 8 && src_y - 2 >= 0 && (chess[src_x + 1][src_y - 2] == '-' || (chess[src_x + 1][src_y - 2] > 'a' && chess[src_x + 1][src_y - 2] < 'z'))) {
                black.add(new Position(src_x, src_y, src_x + 1, src_y - 2));
            }
            // Right-Up
            if (src_x - 1 >= 0 && src_y + 2 < 8 && (chess[src_x - 1][src_y + 2] == '-' || (chess[src_x - 1][src_y + 2] > 'a' && chess[src_x - 1][src_y + 2] < 'z'))) {
                black.add(new Position(src_x, src_y, src_x - 1, src_y + 2));
            }
            // Right-Down
            if (src_x + 1 < 8 && src_y + 2 < 8 && (chess[src_x + 1][src_y + 2] == '-' || (chess[src_x + 1][src_y + 2] > 'a' && chess[src_x + 1][src_y + 2] < 'z'))) {
                black.add(new Position(src_x, src_y, src_x + 1, src_y + 2));
            }
        }
    }

    public void find_queen_moves(int src_x, int src_y, ArrayList<Position> white, ArrayList<Position> black, char c) {
        find_rook_moves(src_x, src_y, white, black, c);
        find_bishop_moves(src_x, src_y, white, black, c);
    }

    public boolean check_is_check(ArrayList<Position> attackedBy, int x, int y) {
        for (Position i : attackedBy) {
            if (i.getX_des() == x && i.getY_des() == y)
                return true;
        }
        return false;
    }

    public int get_out_from_check(Position king, ArrayList<Position> attackedBy, ArrayList<Position> protectedBy, ArrayList<Position> nextAttackedBy, ArrayList<Position> nextProtectedBy, char c) {

        // If the King can move
        for (int i = 0; i < protectedBy.size(); i++) {
            // All available moves for the King
            if (king.getX_src() == protectedBy.get(i).getX_src() && king.getY_src() == protectedBy.get(i).getY_src()) {
                king.setX_des(protectedBy.get(i).getX_des());
                king.setY_des(protectedBy.get(i).getY_des());

                char aux1 = chess[king.getX_des()][king.getY_des()];
                char aux2 = chess[king.getX_src()][king.getY_src()];

                // We make the board for that move
                chess[king.getX_src()][king.getY_src()] = '-';
                chess[king.getX_des()][king.getY_des()] = aux2;

                // We remake the new moves
                if (Main.getEngineSide() == PlaySide.WHITE)
                    get_moves(nextProtectedBy, nextAttackedBy);
                else if (Main.getEngineSide() == PlaySide.BLACK)
                    get_moves(nextAttackedBy, nextProtectedBy);

                // If the King is not in Check, we found the right move
                if (check_is_check(nextAttackedBy, king.getX_des(), king.getY_des()) == false) {
                    chess[king.getX_src()][king.getY_src()] = aux2;
                    chess[king.getX_des()][king.getY_des()] = aux1;
                    nextAttackedBy.clear();
                    nextProtectedBy.clear();
                    return i;
                }
                nextAttackedBy.clear();
                nextProtectedBy.clear();
                chess[king.getX_src()][king.getY_src()] = aux2;
                chess[king.getX_des()][king.getY_des()] = aux1;

            }
        }

        // The case in which we put a piece in front of the King so it's not in Check

        for (int i = 0; i < protectedBy.size(); i++) {
            Position piece = protectedBy.get(i);
            char aux1;
            char aux2 = 0;

            if(piece.getY_src() == -1){
                aux1 = '-';
                if(Main.getEngineSide() == PlaySide.WHITE)
                    // aux2 = choose_piece_white(piece.getX_src());
                chess[piece.getX_des()][piece.getY_des()] = (char) piece.getX_src();
            }
            else{
                aux1 = chess[piece.getX_src()][piece.getY_src()];
                aux2 = chess[piece.getX_des()][piece.getY_des()];
                chess[piece.getX_src()][piece.getY_src()] = '-';
                chess[piece.getX_des()][piece.getY_des()] = aux1;
            }

            if (Main.getEngineSide() == PlaySide.WHITE)
                get_moves(nextProtectedBy, nextAttackedBy);
            else if (Main.getEngineSide() == PlaySide.BLACK)
                get_moves(nextAttackedBy, nextProtectedBy);

            if (aux1 == c) {
                if (check_is_check(nextAttackedBy, piece.getX_des(), piece.getY_des()) == false) {
                    if(piece.getY_src() == -1){
                        chess[piece.getX_des()][piece.getY_des()] = '-';
                    }
                    else{
                        chess[piece.getX_src()][piece.getY_src()] = aux1;
                        chess[piece.getX_des()][piece.getY_des()] = aux2;
                    }
                    return i;
                } else {
                    if(piece.getY_src() == -1){
                        chess[piece.getX_des()][piece.getY_des()] = '-';
                    }
                    else{
                        chess[piece.getX_src()][piece.getY_src()] = aux1;
                        chess[piece.getX_des()][piece.getY_des()] = aux2;
                    }
                }
            } else {
                if (check_is_check(nextAttackedBy, king.getX_src(), king.getY_src()) == false) {
                    if(piece.getY_src() == -1){
                        chess[piece.getX_des()][piece.getY_des()] = '-';
                    }
                    else{
                        chess[piece.getX_src()][piece.getY_src()] = aux1;
                        chess[piece.getX_des()][piece.getY_des()] = aux2;
                    }
                    return i;
                } else {
                    if(piece.getY_src() == -1){
                        chess[piece.getX_des()][piece.getY_des()] = '-';
                    }
                    else{
                        chess[piece.getX_src()][piece.getY_src()] = aux1;
                        chess[piece.getX_des()][piece.getY_des()] = aux2;
                    }
                }
            }
            nextProtectedBy.clear();
            nextAttackedBy.clear();
        }

        return -1;
    }

    public void find_king_moves(int src_x, int src_y, ArrayList<Position> white, ArrayList<Position> black, char c) {
        if (chess[src_x][src_y] == 'k')
            White.find_king_moves(src_x, src_y, white, black, chess);
        else if (chess[src_x][src_y] == 'K')
            Black.find_king_moves(src_x, src_y, white, black, chess);
    }

    public void get_moves(ArrayList<Position> white, ArrayList<Position> black) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                switch (chess[i][j]) {
                    case 'p' -> find_pawn_moves(i, j, white, black, 'p');
                    case 'b' -> find_bishop_moves(i, j, white, black, 'b');
                    case 'n' -> find_knight_moves(i, j, white, black, 'n');
                    case 'r' -> find_rook_moves(i, j, white, black, 'r');
                    case 'q' -> find_queen_moves(i, j, white, black, 'q');
                    case 'k' -> find_king_moves(i, j, white, black, 'k');
                    case 'P' -> find_pawn_moves(i, j, white, black, 'P');
                    case 'B' -> find_bishop_moves(i, j, white, black, 'B');
                    case 'N' -> find_knight_moves(i, j, white, black, 'N');
                    case 'R' -> find_rook_moves(i, j, white, black, 'R');
                    case 'Q' -> find_queen_moves(i, j, white, black, 'Q');
                    case 'K' -> find_king_moves(i, j, white, black, 'K');
                    case 'l' -> find_queen_moves(i,j,white,black,'l');
                    case 'L' -> find_queen_moves(i,j,white,black,'L');
                }
            }
        }
        white.addAll(findPositions(whiteCrazyHouse, chess));
        black.addAll(findPositions(blackCrazyHouse, chess));
    }

    /**
     * Calculate and return the bot's next move
     *
     * @return your move
     */

    public Position get_king_position(char c) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++)
                if (chess[i][j] == c)
                    return new Position(i, j, 0, 0);
        }
        return null;
    }

    public static ArrayList<Position> findPositions(LinkedHashMap<Character, Integer> crazy_house, char[][] chess) {
        ArrayList<Position> freePositions = new ArrayList<>();
        crazy_house.remove('M',1);
        for(Map.Entry<Character, Integer> entry : crazy_house.entrySet()){
            if(entry.getValue() >= 1){
                if(entry.getKey() == 'p' || entry.getKey() == 'P' || entry.getKey() == 'l' || entry.getKey() == 'L'){
                    for(int i = 0 ; i < 8 ; i++){
                        for(int j = 0 ; j < 8 ; j++){
                            if((i != 0 && i != 7) && chess[i][j] == '-'){
                                freePositions.add(new Position(entry.getKey(), -1,i, j));
                            }
                        }
                    }
                }
                else if (((entry.getKey() > 'A' && entry.getKey() < 'Z') || (entry.getKey() > 'a' && entry.getKey() < 'z')) && entry.getKey() != 'p' && entry.getKey() != 'P' && entry.getKey() != 'l' && entry.getKey() != 'L'){
                    for(int i = 0 ; i < 8 ; i++){
                        for(int j = 0 ; j < 8 ; j++){
                            if(chess[i][j] == '-'){
                                freePositions.add(new Position(entry.getKey(), -1,i, j));
                            }
                        }
                    }
                }
            }
        }
        return freePositions;
    }

    public Piece choose_piece(char piece){
        switch (piece){
            case 'p' -> {
                return Piece.PAWN;
            }
            case 'P' -> {
                return Piece.PAWN;
            }
            case 'r' -> {
                return Piece.ROOK;
            }
            case 'R' -> {
                return Piece.ROOK;
            }
            case 'n' -> {
                return Piece.KNIGHT;
            }
            case 'N' -> {
                return Piece.KNIGHT;
            }
            case 'q' -> {
                return Piece.QUEEN;
            }
            case 'Q' -> {
                return Piece.QUEEN;
            }
            case 'b' -> {
                return Piece.BISHOP;
            }
            case 'B' -> {
                return Piece.BISHOP;
            }
            case 'L' -> {
                return Piece.PAWN;
            }
            case 'l' -> {
                return Piece.PAWN;
            }
        }
        return null;
    }

    public void generate_castling_moves(ArrayList<Position> botSide, ArrayList<Position> enemySide, ArrayList<Position> nextBotSide, ArrayList<Position> nextEnemySide) {

        if (Main.getEngineSide() == PlaySide.WHITE && whiteKing == -1 && whiteCastlingLong == -1 && chess[7][1] == '-' && chess[7][2] == '-' && chess[7][3] == '-' && doneCastlingWhite == -1) {
            boolean ok = true;
            for (int i = 1; i <= 3; i++) {
                if (check_is_check(enemySide, 7, i) == true) {
                    ok = false;
                    break;
                }
            }
            if (ok == true)
                botSide.add(new Position(7, 4, 7, 2));
        }
        if (Main.getEngineSide() == PlaySide.WHITE && whiteKing == -1 && whiteCastlingShort == -1 && chess[7][5] == '-' && chess[7][6] == '-' && doneCastlingWhite == -1) {
            boolean ok = true;
            for (int i = 5; i <= 6; i++) {
                if (check_is_check(enemySide, 7, i) == true) {
                    ok = false;
                    break;
                }
            }
            if (ok == true)
                botSide.add(new Position(7, 4, 7, 6));
        }


        if (Main.getEngineSide() == PlaySide.BLACK && blackKing == -1 && blackCastlingShort == -1 && chess[0][5] == '-' && chess[0][6] == '-' && doneCastlingBlack == -1) {

            boolean ok = true;
            for (int i = 5; i <= 6; i++) {
                if (check_is_check(enemySide, 0, i) == true) {
                    ok = false;
                    break;
                }
            }
            if (ok == true)
                botSide.add(new Position(0, 4, 0, 6));
        }

        if (Main.getEngineSide() == PlaySide.BLACK && blackKing == -1 && blackCastlingLong == -1 && chess[0][1] == '-' && chess[0][2] == '-' && chess[0][3] == '-' && doneCastlingBlack == -1) {
            boolean ok = true;
            for (int i = 1; i <= 3; i++) {
                if (check_is_check(enemySide, 0, i) == true) {
                    ok = false;
                    break;
                }
            }
            if (ok == true)
                botSide.add(new Position(0, 4, 0, 2));
        }
    }

    public int getRandomMoves(ArrayList<Position> botSide, ArrayList<Position> enemySide, ArrayList<Position> nextBotSide, ArrayList<Position> nextEnemySide, char c) {

        Position king_position = get_king_position(c);

        int index = -2;

        // If it's in Check we try to solve it
        if (check_is_check(enemySide, king_position.getX_src(), king_position.getY_src()) == true) {
            index = get_out_from_check(king_position, enemySide, botSide, nextEnemySide, nextBotSide, c);
        } else {
            // If we can, we generate the moves for Castling
            generate_castling_moves(botSide, enemySide, nextBotSide, nextEnemySide);
        }

        // If the Index remains -1 (if there are no moves to solve Check, then it's Checkmate)

        if (index == -1)
            return -1;

        index = random.nextInt(botSide.size());
        boolean ok = true;
        // If there are no moves for the bot, then it's a tie (We used the lose function)
        if(botSide.size() == 0)
            return -2;
        Position piece = botSide.get(index);
        while (ok == true) {
            piece = botSide.get(index);
            char aux1;
            char aux2 = 0;

            if(piece.getY_src() == -1){
                aux1 = '-';
                if(Main.getEngineSide() == PlaySide.WHITE)
                //    aux2 = choose_piece_white(piece.getX_src());
                chess[piece.getX_des()][piece.getY_des()] = (char) piece.getX_src();
            }
            else{
                aux1 = chess[piece.getX_src()][piece.getY_src()];
                aux2 = chess[piece.getX_des()][piece.getY_des()];
                chess[piece.getX_src()][piece.getY_src()] = '-';
                chess[piece.getX_des()][piece.getY_des()] = aux1;
            }

            if (Main.getEngineSide() == PlaySide.WHITE)
                get_moves(nextBotSide, nextEnemySide);
            else if (Main.getEngineSide() == PlaySide.BLACK)
                get_moves(nextEnemySide, nextBotSide);

            if (aux1 == c) {
                if (check_is_check(nextEnemySide, piece.getX_des(), piece.getY_des()) == false) {
                    if(piece.getY_src() == -1){
                        chess[piece.getX_des()][piece.getY_des()] = '-';
                    }
                    else{
                        chess[piece.getX_src()][piece.getY_src()] = aux1;
                        chess[piece.getX_des()][piece.getY_des()] = aux2;
                    }
                    ok = false;
                    return index;
                } else {
                    if(piece.getY_src() == -1){
                        chess[piece.getX_des()][piece.getY_des()] = '-';
                    }
                    else{
                        chess[piece.getX_src()][piece.getY_src()] = aux1;
                        chess[piece.getX_des()][piece.getY_des()] = aux2;
                    }
                    botSide.remove(index);
                    ok = true;
                    if(botSide.size() == 0)
                        return -2;
                    index = random.nextInt(botSide.size());
                }
            } else {
                if (check_is_check(nextEnemySide, king_position.getX_src(), king_position.getY_src()) == false) {
                    if(piece.getY_src() == -1){
                        chess[piece.getX_des()][piece.getY_des()] = '-';
                    }
                    else{
                        chess[piece.getX_src()][piece.getY_src()] = aux1;
                        chess[piece.getX_des()][piece.getY_des()] = aux2;
                    }
                    ok = false;
                    return index;
                } else {
                    if(piece.getY_src() == -1){
                        chess[piece.getX_des()][piece.getY_des()] = '-';
                    }
                    else{
                        chess[piece.getX_src()][piece.getY_src()] = aux1;
                        chess[piece.getX_des()][piece.getY_des()] = aux2;
                    }
                    botSide.remove(index);
                    ok = true;
                    index = random.nextInt(botSide.size());
                }
            }
            nextBotSide.clear();
            nextEnemySide.clear();
        }

        return index;
    }

    public void clear_arrays() {
        positionsToAttackWhite.clear();
        positionsToAttackBlack.clear();
        nextPositionToAttackWhite.clear();
        nextPositionToAttackBlack.clear();
    }

    public Move calculateNextMove() {

        /* Calculate next move for the side the engine is playing (Hint: Main.getEngineSide())
         * Make sure to record your move in custom structures before returning.
         *
         * Return move that you are willing to submit
         * Move is to be constructed via one of the factory methods defined in Move.java */

        // Moves fot White and Black
        get_moves(positionsToAttackWhite, positionsToAttackBlack);
        positionsToAttackWhite.addAll(findPositions(whiteCrazyHouse, chess));

        if (Main.getEngineSide() == PlaySide.WHITE) {

            int index = getRandomMoves(positionsToAttackWhite, positionsToAttackBlack, nextPositionToAttackWhite, nextPositionToAttackBlack, 'k');

            if(index == -2)
                return Move.resign();

            if (index == -1)
                return Move.resign();

            if(positionsToAttackWhite.get(index).getY_src() == -1){
                char y2 = (char) (positionsToAttackWhite.get(index).getY_des() + 97);
                String b = y2 + "" + (8 - positionsToAttackWhite.get(index).getX_des());
                // char piesa = choose_piece_white(positionsToAttackWhite.get(index).getX_src());
                Piece piesa_format = choose_piece((char) positionsToAttackWhite.get(index).getX_src());
                recordMove(Move.dropIn(b,piesa_format),Main.getEngineSide());
                clear_arrays();
                printOutput(null);
                return Move.dropIn(b, piesa_format);
            }

            char y1 = (char) (positionsToAttackWhite.get(index).getY_src() + 97);
            char y2 = (char) (positionsToAttackWhite.get(index).getY_des() + 97);
            String a = y1 + "" + (8 - positionsToAttackWhite.get(index).getX_src());
            String b = y2 + "" + (8 - positionsToAttackWhite.get(index).getX_des());

            recordMove(Move.moveTo(a, b), Main.getEngineSide());

            printOutput(null);

            clear_arrays();

            return Move.moveTo(a, b);
        }

        if (Main.getEngineSide() == PlaySide.BLACK) {

            int index = getRandomMoves(positionsToAttackBlack, positionsToAttackWhite, nextPositionToAttackBlack, nextPositionToAttackWhite, 'K');

            if (index == -1)
                return Move.resign();

            if(index == -2)
                return Move.resign();

            if(positionsToAttackBlack.get(index).getY_src() == -1){
                char y2 = (char) (positionsToAttackBlack.get(index).getY_des() + 97);
                String b = y2 + "" + (8 - positionsToAttackBlack.get(index).getX_des());
                // char piesa = choose_piece_black(positionsToAttackWhite.get(index).getX_src());
                Piece piesa_format = choose_piece((char) positionsToAttackBlack.get(index).getX_src());
                recordMove(Move.dropIn(b,piesa_format),Main.getEngineSide());
                clear_arrays();
                printOutput(null);
                return Move.dropIn(b, piesa_format);
            }

            char y1 = (char) (positionsToAttackBlack.get(index).getY_src() + 97);
            char y2 = (char) (positionsToAttackBlack.get(index).getY_des() + 97);
            String a = y1 + "" + (8 - positionsToAttackBlack.get(index).getX_src());
            String b = y2 + "" + (8 - positionsToAttackBlack.get(index).getX_des());

            recordMove(Move.moveTo(a, b), Main.getEngineSide());

            printOutput(null);

            clear_arrays();

            return Move.moveTo(a, b);
        }

        return Move.resign();
    }

    public static String getBotName() {
        return BOT_NAME;
    }
}