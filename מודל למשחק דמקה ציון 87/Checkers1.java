import java.util.*;
import java.util.Random;

public class Checkers1 {

	static Scanner sc = new Scanner(System.in);

//create a board 8X8 that is maid all of *
	public static String [][] createBoard() {
		String [][] board = new String [8][8];
		for (int i = 0 ; i < board.length; i++) {
			for (int j = 0 ; j < board[0].length; j++) {
				board[i][j] = "*";
			}
		}
		return board;
	}
// print the board with tabs between eache collum 
	public static void printBoard (String [][] board) {
		System.out.println("The board:");
		for (int k = 0 ; k < board.length ; k++) {
			for (int j = 0 ; j < board[0].length ; j++) {
				// print board with spaces
				System.out.print(board[k][j] + " 	");
			}
			System.out.println();

		}
		System.out.println();
	}
//add to the board "-" that indicates the not playable spots
	public static String [][]  setNotPlayableSquares(String [][] board){
		for (int i = 0 ; i < board.length; i ++) {
			if ( i%2 == 0) {
				for (int j = 0 ; j < board[0].length; j = j+2) {
					board[i][j] = "-";
				}
			}
			else {
				for (int j = 1 ; j < board[0].length; j =j+2) {
					board[i][j] = "-";
				}
			}
		}
		return board;
	}
//add to the board the white player pieces
	public static String [][] setWhitePlayer(String [][] board){
		for (int i = 0 ; i < 3; i ++) {
			if ( i%2 == 0) {
				for (int j = 1 ; j < board[0].length; j = j+2) {
					board[i][j] = "W";
				}
			}
			else {
				for (int j = 0 ; j < board[0].length; j =j+2) {
					board[i][j] = "W";
				}
			}
		}
		return board;
	}
	
	//add to the board the red player pieces
	public static String [][] setRedPlayer(String [][] board){
		for (int i = 5 ; i < 8; i ++) {
			if ( i%2 == 0) {
				for (int j = 1 ; j < board[0].length; j = j+2) {
					board[i][j] = "R";
				}
			}
			else {
				for (int j = 0 ; j < board[0].length; j =j+2) {
					board[i][j] = "R";
				}
			}
		}
		return board;
	}
// set the board ready to play by using all the functions above 
	public static String [][] setBoard(String [][] board){
		board = setNotPlayableSquares(board);
		board = setWhitePlayer(board);
		board = setRedPlayer( board);
		return board;
	}
//print the main menu
	public static void printMenu() {
		System.out.println("Welcome to Fatma Checkers \n" + "To start the game press 1:\n" + "to exit press 0:");
	}
//checks if the player inputs is a valid input
	public static boolean validInput(String move) {
		if (move.length() != 5) {
			System.out.println("The input is not valid, please enter your move again: ");
			return false;
		}
		String validCharts = "12345678";
		if (validCharts.indexOf(move.charAt(0)) == -1 ||
				validCharts.indexOf(move.charAt(1)) == -1 ||
				validCharts.indexOf(move.charAt(3)) == -1 ||
				validCharts.indexOf(move.charAt(4)) == -1) {
			System.out.println("The input is not valid, please enter your move again: ");
			return false;
		}
		if (move.charAt(2) != '-') {
			System.out.println("The input is not valid, please enter your move again: ");
			return false;
		}
		return true;
	}
//checks if the player decided to surrender
	public static boolean playerSurender(String move) {
		if (move.compareTo("STOP") == 0) {
			return true;
		}
		return false;
	}
//this represent the main player function that includes checking that the input is valid,that is a valid move and if they are all valid it will also play the move
	public static void playerTurn (String [][] board) {
		boolean done = false;
		String move;
		System.out.println("It's your turn, please enter your move:");
		while(!done) {
			move = sc.next();
			System.out.println(move);
			if (playerSurender(move) ) {
				System.out.println("Sorry, computer has won :(");
				System.out.println("would you like to play again? ");
				start();
			}
			if(!validInput(move))
				continue;
			if(!validMove(move,board))
				continue;
			done = true;
		}
	}
// this function convert the collums and the rows to the required formation
	public static int translateColumnIndex(int input_row_index) {
		int table_row_index;

		table_row_index = input_row_index-1;

		return table_row_index;
	}
	public static int translateRowIndex(int input_column_index) {
		int table_column_index;

		table_column_index = input_column_index-1;

		return table_column_index;
	}
	
	//checks if the move is a a valid move according to the rules of the game  and if it is valid it will play it 
	public static boolean validMove (String move, String [][] board) {
		int res;
		int toRow = translateRowIndex((move.charAt(0))-'0');
		int toCollum = translateColumnIndex((move.charAt(1))-'0');
		int fromRow= translateRowIndex((move.charAt(3))-'0');
		int fromCollum = translateColumnIndex((move.charAt(4))-'0');
		if (board[fromRow][fromCollum].compareTo("R") != 0 && board[fromRow][fromCollum].compareTo("Q-R") != 0) {
			System.out.println("This move is invalid, please enter a new move:");
			return false;
		}
		if(board[toRow][toCollum].compareTo("*") != 0) {
			System.out.println("This move is invalid, please enter a new move:");
			return false;
		}
		if (board[fromRow][fromCollum].compareTo("Q-R") == 0) {
			res = validMoveQueen(move, board, toRow, toCollum, fromRow, fromCollum);
			if(res == 0)
				return false;
			if ( res == 1) {
				playerMove(move,board, toRow, toCollum, fromRow, fromCollum);
			}
			else if (res == 2) {
				board[toRow][toCollum] = board[fromRow][fromCollum];
				if (fromCollum + 2 == toCollum ) {
					board[fromRow - 1][fromCollum+1] = "*";
				}
				if (fromCollum - 2 == toCollum ) {
					board[fromRow - 1][fromCollum - 1] = "*";
				}
				board[fromRow][fromCollum] = "*";
			}
			else if (res == 3) {
				board[toRow][toCollum] = board[fromRow][fromCollum];
				if (fromCollum + 2 == toCollum ) {
					board[fromRow + 1][fromCollum+1] = "*";
				}
				if (fromCollum - 2 == toCollum ) {
					board[fromRow + 1][fromCollum - 1] = "*";
				}
			}
			board[fromRow][fromCollum] = "*";
			return true;
		}
		else {
			res = validMovePion(move, board, toRow, toCollum, fromRow, fromCollum);
			if(res == 0)
				return false;
			if(res == 1)
				playerMove(move,board, toRow, toCollum, fromRow, fromCollum);
			if(validMovePion(move, board, toRow,toCollum, fromRow, fromCollum) == 2) {
				eatPioner (board, toRow, toCollum,  fromRow, fromCollum);
				return true;
			}
			return true;
		}
	}

	//the function make the simple pieces to eat the opponent pieces 
	public static void eatPioner (String [][] board, int toRow, int toCollum, int fromRow, int fromCollum) {
		board[toRow][toCollum] = board[fromRow][fromCollum];
		if (fromCollum + 2 == toCollum ) {
			board[fromRow - 1][fromCollum+1] = "*";
		}
		if (fromCollum - 2 == toCollum ) {
			board[fromRow - 1][fromCollum - 1] = "*";
		}
		if (toRow == 0) {
			board[toRow][toCollum] = "Q-R";
		}
		board[fromRow][fromCollum] = "*";
	}


//will check if the move is valid according to the rules of the game specific on the rules made for the queen piece
	//if the function returns 1 it means its a simple move
	// if the function returns 2 the queen will eat up
	//if the function returns 3 she will eat down 
	//if the function returns 0 the move is invalid 
	public static int validMoveQueen (String move, String [][] board, int toRow, int toCollum, int fromRow, int fromCollum) {
		if (fromRow - 1 == toRow && (fromCollum + 1 == toCollum || fromCollum - 1 == toCollum)) {
			return 1;
		}
		if (fromRow + 1 == toRow && (fromCollum + 1 == toCollum || fromCollum - 1 == toCollum)) {
			return 1;
		}
		if(moveDiagonalLU(board,toRow,toCollum,fromRow,fromCollum) ||moveDiagonalRU(board,toRow,toCollum,fromRow,fromCollum) ){
			return 2;
		}
		if(moveDiagonalLD(board,toRow,toCollum,fromRow,fromCollum) ||moveDiagonalRD(board,toRow,toCollum,fromRow,fromCollum) ){
			return 3;
		}

		if (fromCollum + 1 != toCollum && fromCollum - 1 != toCollum) {
			System.out.println("This move is invalid, please enter a new move:");
			return 0;
		}
		if (fromRow + 1 != toRow && fromRow - 1 != toRow) {
			System.out.println("This move is invalid, please enter a new move:");
			return 0;
		}
		System.out.println("This move is invalid, please enter a new move:");
		return 0 ;
	}

	// function of moving for the queen 
	public static boolean moveDiagonalLU (String [][] board, int toRow, int toCollum, int fromRow, int fromCollum) {
		if(fromRow>1 &&fromCollum>1){
			if(fromRow==toRow-2 && fromCollum==toCollum-2 ){
				//Up-Left
				if (board[toRow][toCollum].equals("*")){
					if (board[toRow+1][toCollum+1].equals("W")||board[toRow+1][toCollum+1].equals("Q-W")){
						return true;
					}
				}
			}
		}
		return false;
	}

	public static boolean moveDiagonalRU (String [][] board, int toRow, int toCollum, int fromRow, int fromCollum) {
		if(fromRow>1 &&fromCollum<6){
			if(fromRow==toRow-2 && fromCollum==toCollum+2 ){
				//Up-Right
				if (board[toRow][toCollum].equals("*")){
					if (board[toRow+1][toCollum-1].equals("W")||board[toRow+1][toCollum-1].equals("Q-W")){
						return true;
					}
				}
			}
		}
		return false;
	}
	public static boolean moveDiagonalLD (String [][] board, int toRow, int toCollum, int fromRow, int fromCollum) {
		if(fromRow<6 &&fromCollum>1){
			if(fromRow==toRow-2 && fromCollum==toCollum+2 ){
				//Left-Down
				if (board[toRow][toCollum].equals("*")){
					if (board[toRow+1][toCollum-1].equals("W")||board[toRow+1][toCollum-1].equals("Q-W")){
						return true;
					}
				}
			}
		}
		return false;
	}

	public static boolean moveDiagonalRD (String [][] board, int toRow, int toCollum, int fromRow, int fromCollum) {
		if(fromRow<6 &&fromCollum<6){
			if(fromRow==toRow-2 && fromCollum==toCollum-2 ){
				//Right-Down
				if (board[toRow][toCollum].equals("*")){
					if (board[toRow-1][toCollum-1].equals("W")||board[toRow-1][toCollum-1].equals("Q-W")){
						return true;
					}
				}
			}
		}
		return false;
	}


//checks if the move is valid according to the rules of the game regarding the simple piece 
	//as before if returns 1 its a simple move if returns 2 its a eating move and if returns 0 its an invalid move
	public static int validMovePion (String move, String [][] board, int toRow, int toCollum, int fromRow, int fromCollum) {
		if (fromRow - 1 == toRow && (fromCollum + 1 == toCollum || fromCollum - 1 == toCollum)) {
			return 1;
		}

		else if ((fromRow - 2 == toRow && (fromCollum + 2 == toCollum || fromCollum - 2 == toCollum )) && (board[fromRow - 1][fromCollum + 1].equals("W") || board[fromRow - 1][fromCollum - 1].equals("W")) || (board[fromRow - 1][fromCollum + 1].equals("Q-W") || board[fromRow - 1][fromCollum - 1].equals("Q-W")) ) {
			return 2;
		}
		else if(fromRow - 1 != toRow) {
			System.out.println("This move is invalid, please enter a new move:");
			return 0;
		}
		else if(fromCollum + 1 != toCollum && fromCollum - 1 != toCollum) {
			System.out.println("This move is invalid, please enter a new move:");
			return 0;
		}
		else {
			return 0;
		}
	}
// this is the function that changes the board for simple moves and make a simple soldier in to a queen when it gets to the  other side of the board
	public static void playerMove(String move,String [][] board, int toRow, int toCollum, int fromRow, int fromCollum) {
		board[toRow][toCollum] = board[fromRow][fromCollum];
		if (toRow == 0 ) {
			board[toRow][toCollum] = "Q-R";
		}
		board[fromRow][fromCollum] = "*";
	}

//function that create a random 2 number digit between 11 -88
	public static String NumRandom1To8 (){
		Random random = new Random();
		int first = 1 +random.nextInt(8);
		int second = 1 +random.nextInt(8);
		return (first + "" + second);
	}

// the function checks if the are not any other valid move for a simple piece
	public static boolean isDrawOnPointPion(String[][] board, int i, int j) {
		
		int[] delta_i = {1, 1, 2, 2};
		int[] delta_j = {1, -1, 2, -2};
		int k;
		
		for(k=0; k<4; k++) {
			if(i+delta_i[k] > 7 || j+delta_j[k] > 7)
				continue;
			if(i+delta_i[k] < 0 || j+delta_j[k] < 0)
				continue;
			if(board[i+delta_i[k]][j+delta_j[k]].equals("*"))
				return false;
		}
		
		return true;
	}
	
	// the function checks if the are not any other valid move for a queen piece
	public static boolean isDrawOnPointQueen(String[][] board, int i, int j) {
		
		int[] delta_i = {1, 1, -1, -1, 2, 2, -2, -2};
		int[] delta_j = {1, -1, 1, -1, 2, -2, 2, -2};
		int k;
		
		for(k=0; k<4; k++) {
			if(i+delta_i[k] > 7 || j+delta_j[k] > 7)
				continue;
			if(i+delta_i[k] < 0 || j+delta_j[k] < 0)
				continue;
			if(board[i+delta_i[k]][j+delta_j[k]].equals("*"))
				return false;
		}
		
		return true;
	}

	// this function will run all over the board and will check if there are any valid move for the pc
	public static boolean isDrawPcTurn(String[][] board){	

		boolean isDraw = true;
		
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++) {

				if(board[i][j].equals("W"))
					if(!isDrawOnPointPion(board, i, j)) {
						isDraw = false;
						break;
					}
				if(board[i][j].equals("Q-W"))
					if(!isDrawOnPointQueen(board, i, j)) {
						isDraw = false;
						break;
					}
			}
		}
		
		return isDraw;
	}
	
	// this function will run all over the board and will check if there are any valid move for the player
	public static boolean isDrawPlayerTurn(String[][] board){	

		boolean isDraw = true;
		
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++) {

				if(board[i][j].equals("R"))
					if(!isDrawOnPointPion(board, i, j)) {
						isDraw = false;
						break;
					}
				if(board[i][j].equals("Q-R"))
					if(!isDrawOnPointQueen(board, i, j)) {
						isDraw = false;
						break;
					}
			}
		}
		
		return isDraw;
	}


	public static boolean canMoveUpLeft(String[][] board,int fromRow, int fromCol){
		// if a unit can move up and left ( only move and eat )
		if(fromRow>1 && fromCol>1){
			if(board[fromRow-1][fromCol-1].equals("*")){
				return true;
			}
			if(board[fromRow-1][fromCol-1].equals("W")||board[fromRow-1][fromCol-1].equals("Q-W")){
				if(fromRow>2 && fromCol>2){
					if(board[fromRow-2][fromCol-2].equals("*")){
						return true;
					}
				}
			}
		}
		return false;
	}

	// checks if there is other pieces of the white player if not the white wins
	public static boolean redWon (String [][] board  ){
		boolean won = true;
		for (int i = 0 ; i< board.length ; i ++) {
			for (int k = 0 ; k< board[0].length ; k ++ ) {
				if (board [i][k].compareTo("W") == 0 || board [i][k].compareTo("Q-W") == 0  ) {
					won = false;
				}
			}
		}
		return won;
	}
	// checks if there is other pieces of the red player if not the red wins
	public static boolean whiteWon (String [][] board  ){
		boolean won = true;
		for (int i = 0 ; i< board.length ; i ++) {
			for (int k = 0 ; k< board[0].length ; k ++ ) {
				if (board [i][k].compareTo("R") == 0 || board [i][k].compareTo("Q-R") == 0  ) {
					won = false;
				}
			}
		}
		return won;
	}

	// checks if the player  have any eating move
	public static boolean playerCanEat (String [][] board) {
		boolean yumyum = false;
		for (int i = 2 ; i< board.length - 2 ; i ++) {
			for (int k = 2 ; k< board[0].length - 2 ; k ++ ) {
				if (board [i][k].compareTo("R") == 0 && ((board [i -1 ][k +1].compareTo("W") == 0 || (board [i -1 ][k +1].compareTo("Q-W") == 0)) && board [i-2][k +2].compareTo("*") == 0) ) {
					yumyum = true;
					return yumyum;
				}
				if (board [i][k].compareTo("R") == 0 && ((board [i -1 ][k -1].compareTo("W") == 0 || (board [i -1 ][k -1].compareTo("Q-W") == 0)) && board [i-2][k -2].compareTo("*") == 0) ) {
					yumyum = true;
					return yumyum;
				}
				if (board [i][k].compareTo("Q-R") == 0 && ((board [i -1 ][k +1].compareTo("W") == 0 || (board [i -1 ][k +1].compareTo("Q-W") == 0)) && board [i-2][k +2].compareTo("*") == 0) ) {
					yumyum = true;
					return yumyum;
				}
				if (board [i][k].compareTo("Q-R") == 0 && ((board [i -1 ][k -1].compareTo("W") == 0 || (board [i -1 ][k -1].compareTo("Q-W") == 0)) && board [i-2][k -2].compareTo("*") == 0) ) {
					yumyum = true;
					return yumyum;
				}
				if (board [i][k].compareTo("Q-R") == 0 && ((board [i +1 ][k +1].compareTo("W") == 0 || (board [i +1 ][k +1].compareTo("Q-W") == 0)) && board [i+2][k +2].compareTo("*") == 0) ) {
					yumyum = true;
					return yumyum;
				}
				if (board [i][k].compareTo("Q-R") == 0 && ((board [i +1 ][k -1].compareTo("W") == 0 || (board [i +1 ][k -1].compareTo("Q-W") == 0)) && board [i+2][k -2].compareTo("*") == 0) ) {
					yumyum = true;
					return yumyum;
				}
			}
		}
		return yumyum;
	}
	// checks if the pc have any eating move
	public static boolean pcCanEat (String [][] board) {

		boolean yumyum = false;
		for (int i = 2 ; i< board.length - 2 ; i ++) {
			for (int k = 2 ; k< board[0].length - 2 ; k ++ ) {
				if (board [i][k].compareTo("W") == 0 && ((board [i -1 ][k +1].compareTo("R") == 0 || (board [i -1 ][k +1].compareTo("Q-R") == 0)) && board [i-2][k +2].compareTo("*") == 0) ) {
					yumyum = true;
					return yumyum;
				}
				if (board [i][k].compareTo("W") == 0 && ((board [i -1 ][k -1].compareTo("R") == 0 || (board [i -1 ][k -1].compareTo("Q-R") == 0)) && board [i-2][k -2].compareTo("*") == 0) ) {
					yumyum = true;
					return yumyum;
				}
				if (board [i][k].compareTo("Q-W") == 0 && ((board [i -1 ][k +1].compareTo("R") == 0 || (board [i -1 ][k +1].compareTo("Q-R") == 0)) && board [i-2][k +2].compareTo("*") == 0) ) {
					yumyum = true;
					return yumyum;
				}
				if (board [i][k].compareTo("Q-W") == 0 && ((board [i -1 ][k -1].compareTo("R") == 0 || (board [i -1 ][k -1].compareTo("Q-R") == 0)) && board [i-2][k -2].compareTo("*") == 0) ) {
					yumyum = true;
					return yumyum;
				}
				if (board [i][k].compareTo("Q-W") == 0 && ((board [i +1 ][k +1].compareTo("R") == 0 || (board [i +1 ][k +1].compareTo("Q-R") == 0)) && board [i+2][k +2].compareTo("*") == 0) ) {
					yumyum = true;
					return yumyum;
				}
				if (board [i][k].compareTo("Q-W") == 0 && ((board [i +1 ][k -1].compareTo("R") == 0 || (board [i +1 ][k -1].compareTo("Q-R") == 0)) && board [i+2][k -2].compareTo("*") == 0) ) {
					yumyum = true;
					return yumyum;
				}
			}
		}
		return yumyum;
	}
//generate a random eating and valid move 
	public static String randomEatingMoves (String [][] board){
		while (true) {
			Random random = new Random();
			int first = random.nextInt(8);
			int second = random.nextInt(8);
			int diff = random.nextInt(5) - 2 ;
			int diff2 = random.nextInt(5) - 2 ;
			if ((diff == 2 || diff == -2) && (diff2 == 2 || diff2 == -2 )) {
				int third = first+diff;
				int fourth = second + diff2;
				if (third >= 0  &&  third < 8 && fourth >= 0 &&  fourth <8 ) {
					int averagerow = (first +third)/2; 
					int averagecollum = (second + fourth)/2; 
					if (board[first][second].equals ("*") && (board[third][fourth].equals ("W") || board[third][fourth].equals ("Q-W") )) {
						if (board[averagerow][averagecollum].equals ("R") || board[averagerow][averagecollum].equals ("Q-R")){
							return ((first+1) +"" + (second+1) +"-"+ (third+1) + "" + (fourth+1));

						}
					}
				}
			}
		}
	}

	// like functions above just adapted to the pc turn
	public static int validMoveQueenPC (String move, String [][] board, int toRow, int toCollum, int fromRow, int fromCollum) {
		if (fromRow - 1 == toRow && (fromCollum + 1 == toCollum || fromCollum - 1 == toCollum)) {
			return 1;
		}
		if (fromRow + 1 == toRow && (fromCollum + 1 == toCollum || fromCollum - 1 == toCollum)) {
			return 1;
		}

		if(moveDiagonalLUPC(board,toRow,toCollum,fromRow,fromCollum) ||moveDiagonalRUPC(board,toRow,toCollum,fromRow,fromCollum) ){
			return 2;
		}

		if(moveDiagonalLDPC(board,toRow,toCollum,fromRow,fromCollum) ||moveDiagonalRDPC(board,toRow,toCollum,fromRow,fromCollum) ){
			return 3;
		}

		if (fromCollum + 1 != toCollum && fromCollum - 1 != toCollum) {
			//			System.out.println("CC-This move is invalid, please enter a new move:");
			return 0;
		}
		if (fromRow + 1 != toRow && fromRow - 1 != toRow) {
			//			System.out.println("DD-This move is invalid, please enter a new move:");
			return 0;
		}
		//		System.out.println("KK-This move is invalid, please enter a new move:");
		return 0 ;
	}

	public static boolean moveDiagonalLUPC (String [][] board, int toRow, int toCollum, int fromRow, int fromCollum) {
		if(fromRow>1 &&fromCollum>1){
			if(fromRow==toRow-2 && fromCollum==toCollum-2 ){
				//Up-Left
				if (board[toRow][toCollum].equals("*")){
					if (board[toRow+1][toCollum+1].equals("R")||board[toRow+1][toCollum+1].equals("Q-R")){
						return true;
					}
				}
			}
		}
		return false;
	}

	public static boolean moveDiagonalRUPC (String [][] board, int toRow, int toCollum, int fromRow, int fromCollum) {
		if(fromRow>1 &&fromCollum<6){
			if(fromRow==toRow-2 && fromCollum==toCollum+2 ){
				//Up-Right
				if (board[toRow][toCollum].equals("*")){
					if (board[toRow+1][toCollum-1].equals("R")||board[toRow+1][toCollum-1].equals("Q-R")){
						return true;
					}
				}
			}
		}
		return false;
	}
	public static boolean moveDiagonalLDPC (String [][] board, int toRow, int toCollum, int fromRow, int fromCollum) {
		if(fromRow<6 &&fromCollum>1){
			if(fromRow==toRow-2 && fromCollum==toCollum+2 ){
				//Left-Down
				if (board[toRow][toCollum].equals("*")){
					if (board[toRow-1][toCollum-1].equals("R")||board[toRow+1][toCollum-1].equals("Q-R")){
						return true;
					}
				}
			}
		}
		return false;
	}

	public static boolean moveDiagonalRDPC (String [][] board, int toRow, int toCollum, int fromRow, int fromCollum) {
		if(fromRow<6 &&fromCollum<6){
			if(fromRow==toRow-2 && fromCollum==toCollum-2 ){
				//Right-Down
				if (board[toRow][toCollum].equals("*")){
					if (board[toRow-1][toCollum-1].equals("R")||board[toRow-1][toCollum-1].equals("Q-R")){
						return true;
					}
				}
			}
		}
		return false;
	}

	public static int validMovePionPC(String move, String [][] board, int toRow, int toCollum, int fromRow, int fromCollum) {
		int averagerow = (toRow + fromRow )/2 ; 
		int averagecollum = (fromCollum + toCollum)/2; 

		if (fromRow + 1 == toRow && (fromCollum + 1 == toCollum || fromCollum - 1 == toCollum)) {
			return 1;
		}
		if ((fromRow + 2 == toRow && (fromCollum + 2 == toCollum || fromCollum - 2 == toCollum )) && (board[averagerow][averagecollum].equals("R") || board[averagerow][averagecollum].equals("Q-R"))) {
			return 2;
		}
		else if(fromRow + 1 != toRow) {
			return 0;
		}
		else if(fromCollum + 1 != toCollum && fromCollum - 1 != toCollum) {
			return 0;
		}
		else {
			return 0;
		}
	}

	public static void PcMove(String move,String [][] board, int toRow, int toCollum, int fromRow, int fromCollum) {
		board[toRow][toCollum] = board[fromRow][fromCollum];
		if (toRow == 7 ) {
			board[toRow][toCollum] = "Q-W";
		}
		board[fromRow][fromCollum] = "*";
	}
	public static boolean validMovePC (String move, String [][] board) {
		int res;
		int toRow = translateRowIndex((move.charAt(0))-'0');
		int toCollum = translateColumnIndex((move.charAt(1))-'0');
		int fromRow= translateRowIndex((move.charAt(3))-'0');
		int fromCollum = translateColumnIndex((move.charAt(4))-'0');

		if (board[fromRow][fromCollum].compareTo("W") != 0 && board[fromRow][fromCollum].compareTo("Q-W") != 0) {
			return false;
		}
		if(board[toRow][toCollum].compareTo("*") != 0) {
			return false;
		}
		if (board[fromRow][fromCollum].compareTo("Q-W") == 0) {
			res = validMoveQueenPC(move, board, toRow, toCollum, fromRow, fromCollum);
			if(res == 0)
				return false;
			if ( res == 1) {
				PcMove(move,board, toRow, toCollum, fromRow, fromCollum);
			}
			else if (res == 2) {
				board[toRow][toCollum] = board[fromRow][fromCollum];
				if (fromCollum + 2 == toCollum ) {
					board[fromRow - 1][fromCollum+1] = "*";
				}
				if (fromCollum - 2 == toCollum ) {
					board[fromRow - 1][fromCollum - 1] = "*";
				}
				board[fromRow][fromCollum] = "*";
			}
			else if (res == 3) {
				board[toRow][toCollum] = board[fromRow][fromCollum];
				if (fromCollum + 2 == toCollum ) {
					board[fromRow + 1][fromCollum+1] = "*";
				}
				if (fromCollum - 2 == toCollum ) {
					board[fromRow + 1][fromCollum - 1] = "*";
				}
			}
			board[fromRow][fromCollum] = "*";
			return true;
		}
		else {
			res = validMovePionPC(move, board, toRow, toCollum, fromRow, fromCollum);
			if(res == 0)
				return false;
			if(res == 1)
				PcMove(move,board, toRow, toCollum, fromRow, fromCollum);
			if(validMovePionPC(move, board, toRow,toCollum, fromRow, fromCollum) == 2) {
				eatPionerPC (board, toRow, toCollum,  fromRow, fromCollum);
				return true;
			}
			return true;
		}
	}

	public static void eatPionerPC (String [][] board, int toRow, int toCollum, int fromRow, int fromCollum) {
		board[toRow][toCollum] = board[fromRow][fromCollum];
		if (fromCollum + 2 == toCollum ) {
			board[fromRow + 1][fromCollum+1] = "*";
		}
		if (fromCollum - 2 == toCollum ) {
			board[fromRow + 1][fromCollum - 1] = "*";
		}
		if (toRow == 7) {
			board[toRow][toCollum] = "Q-W";
		}
		board[fromRow][fromCollum] = "*";
	}

//activates the game
	public static void start () {
		printMenu();
		String [][] board = createBoard();
		setBoard(board);


		int num = sc.nextInt();
		while (num > 1 || num < 0 ) {
			System.out.println("wrong input choose oprtion 0 or option 1:");
			num = sc.nextInt();
		}
		if (num == 0 ) {
			option0 ();
		}
		else {
			option1(board);
		}
		printBoard(board);
	}

	public static boolean validInputPC(String move) {
		if (move.length() != 5) {
			System.out.println("The input is not valid, please enter your move again: AA");
			System.out.println(move);
			return false;
		}
		String validCharts = "12345678";
		if (validCharts.indexOf(move.charAt(0)) == -1 ||
				validCharts.indexOf(move.charAt(1)) == -1 ||
				validCharts.indexOf(move.charAt(3)) == -1 ||
				validCharts.indexOf(move.charAt(4)) == -1) {
			System.out.println("The input is not valid, please enter your move again: BB");
			System.out.println(move);
			return false;
		}
		if (move.charAt(2) != '-') {
			System.out.println("The input is not valid, please enter your move again: CC");
			System.out.println(move);
			return false;
		}
		return true;
	}

	public static void option0 () {
		System.out.print("ok goodbye");
	}
// when the player decide to start playing and represent whole player side
	public static void option1 (String [][] board ) {
		printBoard(board);
		playerTurn(board);
		printBoard(board);
		if (isDrawPcTurn(board)) {
			System.out.println("Well, unfortunately it’s a Tie…");
			System.out.println("would you like to play again? ");
			start ();
		}
		if (redWon(board)) {
			System.out.println("Congratulations, user has won :)");
			System.out.println("would you like to play again? ");
			start ();
		}
		pcTurn (board);

	}
//the main function of the pc turn 
	public static void pcTurn (String [][] board) {
		String move = "";
		if (pcCanEat (board)) {
			while (true) {
				move = randomEatingMoves (board);
				if (validMovePC(move,board)){
					if (whiteWon(board)) {
						System.out.println("Sorry, computer has won :(");
						System.out.println("would you like to play again? ");
						start ();
					}
					option1(board);
				}
			}
		}
		else {
			while (true) {
				move = NumRandom1To8 () + "-" + NumRandom1To8 ();
				if(validMovePC ( move,board)) {
					if (whiteWon(board)) {
						System.out.println("Sorry, computer has won :(");
						System.out.println("would you like to play again? ");
						start ();
					}
					if (isDrawPlayerTurn(board)) {
						System.out.println("Well, unfortunately it’s a Tie…");
						System.out.println("would you like to play again? ");
						start ();
					}
					option1(board);
				}
			}
		}
	}


	public static void main(String[] args) {
		
		
		start();
	}
}


