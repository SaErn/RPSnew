import java.util.Random;
import java.util.Scanner;

public class gameStart {
	//Globala variabler f�r namn p� valm�jligheterna i spelet, spelarnas po�ng, och vilken f�r vilken runda i spelet 
	private static String rock = "Rock";
	private static String paper = "Paper";
	private static String scissor = "Scissor";
	private static int scoreOne = 0;
	private static int scoreTwo = 0;
	private static int roundCount = 1;

	public static void main(String[] args) {
		
		//Skapar enbart en scanner h�r i main, skickas som argument till metoder som anv�nder scanner
		/*Jag valde att g�ra s� h�r med scanner f�r n�r jag anv�nder flera scanners och k�r scanner.close() p� dem s� st�nger det
		 * hela "System.in" streamen s� andra Scanners slutar fungera*/
		Scanner input = new Scanner(System.in);
		//Objekt ur random-klassen som anv�nds f�r att ta fram slumpad int
		Random generator = new Random();
		//F�r val i meny
		int choiceMenu = 0; 
		//F�r loopar som h�ller spelets olika delar ig�ng tills korrekt genomf�rda
		boolean waiting = true; 
		boolean programRunning = true; 
		boolean gameOn = true; 

		//Inv�ntar att programmet ska avslutas/k�ras f�rdigt
		while (programRunning) {
			waiting = true;
			System.out.print("Welcome to " + gameStart.rock + ", " + gameStart.paper + ", " + gameStart.scissor
					+ " Battle Royale\n" + "\nInput 1 to start game with 2 Human player\n"
					+ "Input 2 to play against AI\n" + "Input 3 to change names\nEnter number of your choice: ");
			
			choiceMenu = playerInput(input);



			//Val 1 i menyn -- Tv� m�nskliga spelare
			if (choiceMenu == 1) { 
				
				//Inv�ntar att spelrunda ska avslutas. En loop en omg�ng i spelet
				while (gameOn) {
					System.out.println("\nRound " + gameStart.roundCount);
					//Anropar metod som k�r spelrunda och skickar scanner som argument
					gamePlayer(input); 
					//Global variabel f�r vilken spelrunda inkrementeras med 1 f�r varje loop
					gameStart.roundCount += 1; 
					
					//Om 3 omg�ngar har spelats testar programmet om rundan ska avslutas
					if (gameStart.roundCount >= 4) { 
						//Om spelarnas po�ng ej �r samma avslutas omg�ngen och programmet
						if (gameStart.scoreOne != gameStart.scoreTwo) { 
							programRunning = false;
							gameOn = false;
							//Metod som skriver ut spelarnas slutgiltiga po�ng
							scorePrinter();
						//Om spelarnas po�ng lika k�rs en ny omg�ng och meddelande skrivs ut
						} else if (gameStart.scoreOne == gameStart.scoreTwo) {
							System.out.println("\nTie Score! SUDDEN DEATH ROUND");
						}
					}
				}
				
			//Val 2 i menyn -- Spela mot AI
			} else if (choiceMenu == 2) { 
				
				while (gameOn) {
					System.out.println("\nRound " + gameStart.roundCount);
					
					//Metod f�r spelrunda mot AI. Tar Scanner och Random som argument
					gamePlayerAi(input, generator); 
					
					//Stycket nedanf�r �r samma kod som ovan
					gameStart.roundCount += 1;
					if (gameStart.roundCount >= 4) {
						if (gameStart.scoreOne != gameStart.scoreTwo) {
							programRunning = false;
							gameOn = false;
							scorePrinter();
						} else if (gameStart.scoreOne == gameStart.scoreTwo) {
							System.out.println("\nTie Score! SUDDEN DEATH ROUND");
						}
					}
				}
			
			//Val 3 i menyn - Byt namn p� spelalternativen
			} else if (choiceMenu == 3) { 
				//Metod f�r byta namn. Tar Scanner som argument
				ruleSettings(input);
			}
		}
	}
	
	//Skriver ut spelarnas po�ng efter avsluta runda
	public static void scorePrinter() {
		System.out.println(
				"\nGame over!\nPlayer 1 score: " + gameStart.scoreOne + "\nPlayer 2 score: " + gameStart.scoreTwo);
		
		//Om spelare ett har mer po�ng �n spelare tv�
		if (gameStart.scoreOne > gameStart.scoreTwo) { 
			System.out.println("\nPlayer 1 is the Winner!");
		} else {
			System.out.println("\nPlayer 2 is the Winner!");
		}

	}
	
	//K�r spelrunda med tv� m�nskliga spelare
	public static void gamePlayer(Scanner input) { 
		//H�ller spelarnas inmatade val
		int playerOne;
		int playerTwo;

		//Skriver ut alternativen i spelet med default- eller �ndrade namn
		System.out.println("\n 1 = " + gameStart.rock + "\n 2 = " + gameStart.paper + "\n 3 = "
				+ gameStart.scissor);
		
		System.out.print("\nEnter choice for player 1: ");
		//K�r metoder som tar emot int-v�rde fr�n spelarna och lagrar i respektive variabel
		playerOne = playerInput(input);
		System.out.print("\nEnter choice for player 2: ");
		playerTwo = playerInput(input);
		
		//Metod som testar vem som vann omg�ngen. Skickar spelarnas valda alternativ som argument
		answerCheck(playerOne, playerTwo);
	}

	//K�r spelrunda med m�nniska mot AI spelare
	public static void gamePlayerAi(Scanner input, Random generator) {
		int playerOne;

		System.out.println("\n 1 = " + gameStart.rock + "\n 2 = " + gameStart.paper + "\n 3 = "
				+ gameStart.scissor);
		
		System.out.print("\nEnter choice for player 1: ");
		playerOne = playerInput(input);
		
		//Testar vem som vann omg�ng men h�r �r ett av argumenten det returnerade v�rdet fr�n metod som tar fram slumpad int 1-3
		answerCheck(playerOne, randomNumber(generator));
	}

	//Sk�ter input fr�n spelarna
	public static int playerInput(Scanner input) {

		int playerInput = 0; 
		boolean chosing = true;

		//Loop som inv�ntar svar fr�n spelare
		while (chosing) { 
			//F�ngar upp felaktigt inmatad input. Om formateringsfel uppt�cks ombes anv�ndaren g�ra nytt f�rs�k
			try {
				//F�rs�ker omvandla input till int
				playerInput = Integer.parseInt(input.nextLine());
				//Om input korrekt och matchar ett av valbara alternativ bryts loopen
				if (playerInput >= 1 && playerInput <= 3) {
					chosing = false;
				} else {
					System.out.println("\nYour input must be a number between 1-3");
				}

			} catch (NumberFormatException e) {
				System.out.println("\nYour input must be a number between 1-3");
			}
		}
		return playerInput;
	}

	//Tar fram en slumpad int mellan 1-3, med hj�lp av objekt ur random-klassen. Anv�nds f�r AI-spelare val
	public static int randomNumber(Random generator) {
		int aiChoice;
		//Anropar nextInt-metoden och adderar slumpad int med 1 s� v�rdena blir 1,2,3 ist�llet f�r 0,1,2. 
		aiChoice = generator.nextInt(3) + 1;
		return aiChoice;
	}

	//F�r �ndring av namn p� valbara alternativ i spelet
	public static void ruleSettings(Scanner input) {

		//�ndrar v�rdet/namn p� dem globala variabler som representerar dem olika alternativen
		System.out.println("\nEnter new name for ROCK: ");
		gameStart.rock = input.nextLine();

		System.out.println("\nEnter new name for PAPER: ");
		gameStart.paper = input.nextLine();

		System.out.println("\nEnter new name for SCISSOR: ");
		gameStart.scissor = input.nextLine();
	}

	//Testar vilken av spelarna som vann aktuell omg�ng och skriver ut en vinnare eller om det blev lika
	public static void answerCheck(int playerOneAns, int playerTwoAns) {
		//Testar vilken kombination av svar som mottagits fr�n spelarna
		if (playerOneAns == 1 && playerTwoAns == 1) { // Rock vs Rock
			System.out.println(
					"\nPlayer 1 chose: " + gameStart.rock + "\nPlayer 2 chose: " + gameStart.rock + "\n\nIT'S A DRAW!");

		} else if (playerOneAns == 1 && playerTwoAns == 2) { // Rock vs Paper
			System.out.println(
					"\nPlayer 1 chose: " + gameStart.rock + "\nPlayer 2 chose: " + gameStart.paper + "\n\nPLAYER 2 WINS!");
			//Om det fanns en vinnare av en omg�ng s� adderas 1 till v�rdet av global variabel som r�knar vinnande spelarens po�ng
			gameStart.scoreTwo += 1;

		} else if (playerOneAns == 1 && playerTwoAns == 3) { // Rock vs Scissor
			System.out.println("\nPlayer 1 chose: " + gameStart.rock + "\nPlayer 2 chose: " + gameStart.scissor
					+ "\n\nPLAYER 1 WINS!");
			gameStart.scoreOne += 1;
		} else if (playerOneAns == 2 && playerTwoAns == 1) { // Paper vs Rock
			System.out.println(
					"\nPlayer 1 chose: " + gameStart.paper + "\nPlayer 2 chose: " + gameStart.rock + "\n\nPLAYER 1 WINS!");
			gameStart.scoreOne += 1;
		} else if (playerOneAns == 2 && playerTwoAns == 2) { // Paper vs Paper
			System.out.println(
					"\nPlayer 1 chose: " + gameStart.paper + "\nPlayer 2 chose: " + gameStart.paper + "\n\nIT'S A DRAW!");

		} else if (playerOneAns == 2 && playerTwoAns == 3) { // Paper vs Scissor
			System.out.println("\nPlayer 1 chose: " + gameStart.paper + "\nPlayer 2 chose: " + gameStart.scissor
					+ "\n\nPLAYER 2 WINS!");
			gameStart.scoreTwo += 1;
		} else if (playerOneAns == 3 && playerTwoAns == 1) { // Scissor vs Rock
			System.out.println("\nPlayer 1 chose: " + gameStart.scissor + "\nPlayer 2 chose: " + gameStart.rock
					+ "\n\nPLAYER 2 WINS!");
			gameStart.scoreTwo += 1;
		} else if (playerOneAns == 3 && playerTwoAns == 2) { // Scissor vs Paper
			System.out.println("\nPlayer 1 chose: " + gameStart.scissor + "\nPlayer 2 chose: " + gameStart.paper
					+ "\n\nPLAYER 1 WINS!");
			gameStart.scoreOne += 1;
		} else if (playerOneAns == 3 && playerTwoAns == 3) { // Scissor vs Scissor
			System.out.println("\nPlayer 1 chose: " + gameStart.scissor + "\nPlayer 2 chose: " + gameStart.scissor
					+ "\n\nIT'S A DRAW!");
		}

	}
}
