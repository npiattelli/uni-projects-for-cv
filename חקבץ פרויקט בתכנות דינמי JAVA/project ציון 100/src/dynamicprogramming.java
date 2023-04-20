
public class dynamicprogramming {
	public static final int W = 74;
	public static final int N = 18;
	public static final double q= 0.2;
	public static final String taz1 = "316320779";
	public static final String taz2 = "316164177";

	// create an arr of W[i] variables according to the id
	public static int [] create_wi (String taz1,String taz2) {
		String totTaz = taz1+taz2;
		int[] allWi = new int [18];
		for (int i=0 ;i< totTaz.length(); i++) {
			allWi[i]= totTaz.charAt(i)-48;
			if (allWi[i]==0) {
				allWi[i] = 10;
			}
		}
		return allWi;
	}
	// create an arr of P[i] variables according to the id
	public static int [] create_pi (String taz1,String taz2) {
		String totTaz = taz2+taz1;
		int[] allpi = new int [18];
		for (int i=0 ;i< totTaz.length(); i++) {
			allpi[i]= totTaz.charAt(i)-48;
			if (allpi[i]==0) {
				allpi[i] = 10;
			}
		}
		return allpi;
	}
	//create a basic board 
	public static double [][] createBoard (){
		double [][] board = new double [76][78];
		for ( int i = 0 ; i < board.length  ; i++) {
			board[0][i+1] = i ;
		}
		for (int k = 0 ; k<  board.length-1; k++) {
			board[k+1][0] = k ;
		}
		return board;
	}
	//create the main table 
	public static double [][] createMainTable (){
		double [][] board = new double [2][78];
		for ( int i = 1 ; i < board[0].length  ; i++) {
			board[0][i] = i-1 ;
		}
		return board;

	}
	// printing the s the fi and xi of a table 
	public static void printFiandXi (double [][] board,int n) {
		System.out.println("table number: "+(n+1));
		System.out.print("S"+(n+1)+"/"+"X"+(n+1)+ "	");
		System.out.print("F"+(n+1)+ "	");
		System.out.print("X"+(n+1)+ "	");
		System.out.println();
		if (n!= 0) {
			for (int k = 1 ; k < board.length ; k++) {
				System.out.println((k-1) +"	"+ board[k][76]+"	"+board[k][77]);
			}
			System.out.println();
		}
	}

	// print the whole table with fi xi and cutting all the non relevant columns
	public static void printBoard (double [][] board,int n) {
		System.out.println("table number: "+(n+1));
		for (int k = 0 ; k < board.length ; k++) {
			for (int j = 0 ; j < board[0].length ; j++) {
				if(k==0) {
					if (j ==0) {
						System.out.print("S"+(n+1)+"/X"+(n+1) + "	");
					}
					if(j ==76) {
						System.out.print("F"+(n+1)+ "	");
						System.out.print("X"+(n+1)+ "	");
					}
					// erasing all the non relevant columns
					if(j!=1 && j !=76 &&  j!=77) {
						if (board[75][j]>0)
							System.out.print(board[k][j] + "	");
					}
				}
				else {
					// erasing all the non relevant columns
					if(j==1 || j ==76 ||  j==77) {
						System.out.print(board[k][j] + "	");
					}
					else {
						if(board[75][j]>0) {
							System.out.print(board[k][j] + "	");
						}
					}
				}
			}
			System.out.println();
		}
		System.out.println();
	}
	//  print the whole main table with fi xi and erasing all the non relevant columns
	public static void printMainBoard (double [][] board,int n) {
		System.out.println("table number: "+(n+1));
		for (int k = 0 ; k < board.length ; k++) {
			for (int j = 0 ; j < board[0].length ; j++) {
				if(k==0) {
					if (j ==0) {
						System.out.print("S"+(n+1)+"/X"+(n+1) + "	");
					}
					if(j ==76) {
						System.out.print("F"+(n+1)+ "	");
						System.out.print("X"+(n+1)+ "	");
					}
					// erasing all the non relevant columns
					if(j!=1 && j !=76 &&  j!=77) {
						if (board[1][j]>0)
							System.out.print(board[k][j] + "	");
					}
				}
				else {
					// erasing all the non relevant columns
					if(j==1 || j ==76 ||  j==77) {
						System.out.print(board[k][j] + "	");
					}
					else {
						if(board[1][j]>0) {
							System.out.print(board[k][j] + "	");
						}
					}
				}
			}
			System.out.println();
		}
		System.out.println();
	}
	//	update the main table according to the other tables
	public static double [][] lastTable (double[] maxfi,int [] allWi,int [] allpi,int n) {
		double [][] board = createMainTable ();
		int spaceIfSucceeds;
		int spaceIfFail;
		double max = 0;
		double bestXi = 0;
		int Wi = allWi[n];
		int Pi = allpi[n];
		board[1][0] = 74 ;
		for (int k = 1 ; k < board[0].length ; k++) {
			if (k == 1) {
				// calculate the value of each option
				board[1][k] = maxfi[75];
				if (board[1][k]> max) {
					max =board[1][k];
					bestXi = board[0][k];
				}
				continue;
			}
			// if the is no space for this amount of containers 
			if (board[1][0]< Wi*board[0][k]) {
				board[1][k] = 0;
				continue;
			}
			//calculate the empty space left
			spaceIfSucceeds = 76 - Wi*k;
			spaceIfFail= 76 - Wi*(k-1);
			// calculate the value of each option
			if (spaceIfSucceeds <= 0) {
				board[1][k]= 0.8*(Pi*board[0][k])+0.2*(Pi*board[0][k-1]+maxfi[spaceIfFail]);
				board[1][k]=Math.round(board[1][k] * 100.0) / 100.0;
			}
			// calculate the value of each option
			if (spaceIfSucceeds > 0) {
				board[1][k]= 0.8*(Pi*board[0][k]+maxfi[spaceIfSucceeds])+0.2*(Pi*board[0][k-1]+maxfi[spaceIfFail]);
				board[1][k]=Math.round(board[1][k] * 100.0) / 100.0;
			}
			// counter for fi 
			if (board[1][k]> max) {
				max =board[1][k];
				bestXi = board[0][k];
			}
		}
		board[1][76] = max;
		board[1][77] = bestXi;
		updateMaxFi(board,maxfi);
		return board;
	}

	// create the right table according to W[i],p[i],n
	public static void update_i (int n,int [] allWi,int [] allpi,double [][] board,double[] maxfi){
		int spaceIfSucceeds;
		int spaceIfFail;
		double max = 0;
		double bestXi = 0;
		int Wi = allWi[n];
		int Pi = allpi[n];
		for (int i = 1 ; i < board.length; i++) {
			for (int k = 1 ; k < board.length ; k++) {
				if (k == 1) {
					// if we decide to take 0 containers we will the fi of the previous table
					board[i][k] = maxfi[i];
					if (board[i][k]> max) {
						max =board[i][k];
						bestXi = board[0][k];
					}
					continue;
				}
				// checks if the empty space is enough to contain the containers spaces 
				if (board[i][0]< Wi*board[0][k]) {
					board[i][k] = 0;
					continue;
				}
				spaceIfSucceeds = i+1 - Wi*k;
				spaceIfFail= i - Wi*(k-1);
				if (spaceIfSucceeds <= 0) {
					// calculate the value of each option
					board[i][k]= 0.8*(Pi*board[0][k])+0.2*(Pi*board[0][k-1]+maxfi[spaceIfFail]);
					board[i][k]=Math.round(board[i][k] * 100.0) / 100.0;
				}

				if (spaceIfSucceeds > 0) {
					// calculate the value of each option
					board[i][k]= 0.8*(Pi*board[0][k]+maxfi[spaceIfSucceeds])+0.2*(Pi*board[0][k-1]+maxfi[spaceIfFail]);
					board[i][k]=Math.round(board[i][k] * 100.0) / 100.0;
				}
				if (n==17) {
					// the first table
					board[i][k]= 0.8*(Pi*board[0][k])+0.2*(Pi*board[0][k-1]);
					board[i][k]=Math.round(board[i][k] * 100.0) / 100.0;
				}
				// counter to find the fi 
				if (board[i][k]> max) {
					max =board[i][k];
					bestXi = board[0][k];
				}
			}
			board[i][76] = max;
			board[i][77] = bestXi;
		}
		updateMaxFi(board,maxfi);
		printFiandXi(board,n);
		//		if (13<n && n<=17) {
		//			printBoard (board,n);
		//		}
		if (n !=0) {
			update_i (n-1,allWi,allpi,board,maxfi);
		}
		if (n ==0) {
			double [][] mainTable = lastTable (maxfi,allWi,allpi,n);
			System.out.println(74 +"	"+ mainTable[1][76]+"	"+mainTable[1][77]);
			//			printMainBoard (mainTable,0);
		}
	}
	// update the arr of fi values in each row
	public static void updateMaxFi (double [][] board,double [] maxfi){
		for (int i = 1 ; i < board.length; i++) {
			maxfi[i]= board[i][76];
		}
	}

	// create an arr of the fi values in each row
	public static double[] maxFiLastTable (double [][] board){
		double [] maxValues = new double [77];
		for (int i = 1 ; i < board.length; i++) {
			maxValues[i]= findMaxRow(board,i);
		}
		return maxValues;
	}

	//find the highest value in a row for Fi
	public static double findMaxRow (double [][] board,int row) {
		double max = 0 ;
		for (int k = 1 ; k < board.length; k++) {
			if (board[row][k]>max) {
				max=board[row][k];
			}
		}
		return max;
	}
	// return amount of Xi
	public static double findAmountXi (double [][] board,int i,double maxValue) {
		double amount = 0;
		for (int k = 1 ; k < board.length; k++) {
			if (board[i][k]== maxValue && maxValue != 0) {
				amount=board[0][k];
				return amount;
			}
		}
		return amount;
	}

	public static void main(String[] args){
		int [] allWi = create_wi ( dynamicprogramming.taz1,dynamicprogramming.taz2);
		int [] allpi = create_pi (dynamicprogramming.taz1,dynamicprogramming.taz2);
		double [][] board =  createBoard ();
		double [] maxfi = maxFiLastTable(board);
		int n = 17;
		update_i (n,allWi,allpi,board,maxfi);
	}
}