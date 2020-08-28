import java.util.Random;
import java.util.Scanner;

public class gameStart {
	//Globala variabler för namn på valmöjligheterna i spelet, spelarnas poäng, och vilken för vilken runda i spelet 
	private static String rock = "Rock";
	private static String paper = "Paper";
	private static String scissor = "Scissor";
	private static int scoreOne = 0;
	private static int scoreTwo = 0;
	private static int roundCount = 1;

	public static void main(String[] args) {
		
		//Skapar enbart en scanner här i main, skickas som argument till metoder som använder scanner
		/*Jag valde att göra så här med scanner för när jag använder flera scanners och kör scanner.close() på dem så stänger det
		 * hela "System.in" streamen så andra Scanners slutar fungera*/
		Scanner input = new Scanner(System.in);
		//Objekt ur random-klassen som används för att ta fram slumpad int
		Random generator = new Random();
		//För val i meny
		int choiceMenu = 0; 
		//För loopar som håller spelets olika delar igång tills korrekt genomförda
		boolean waiting = true; 
		boolean programRunning = true; 
		boolean gameOn = true; 

		//Inväntar att programmet ska avslutas/köras färdigt
		while (programRunning) {
			waiting = true;
			System.out.println("Welcome to " + gameStart.rock + ", " + gameStart.paper + ", " + gameStart.scissor
					+ " Battle Royale\n" + "Input 1 to start game with 2 Human player\n"
					+ "Input 2 to play against AI\n" + "Input 3 to change names");
			
			choiceMenu = playerInput(input);

//			//Loop som inväntar svar i första menyn
//			while (waiting) {
//				
//				//Fångar upp felaktigt inmatad input. Om formateringsfel upptäcks ombes användaren göra nytt försök
//				try { 
//					//Försöker omvandla input till int
//					choiceMenu = Integer.parseInt(input.nextLine()); 
//					
//					//Om input korrekt och matchar ett av valbara alternativ bryts loopen
//					if (choiceMenu >= 1 && choiceMenu <= 3) {
//						waiting = false; 
//					} else {
//						System.out.println("Your input must be a number between 1-3");
//					}
//				
//				} catch (NumberFormatException e) {
//					System.out.println("Your input must be a number between 1-3");
//				}
//			}

			//Val 1 i menyn -- Två mänskliga spelare
			if (choiceMenu == 1) { 
				
				//Inväntar att spelrunda ska avslutas. En loop en omgång i spelet
				while (gameOn) {
					System.out.println("\nRound " + gameStart.roundCount);
					//Anropar metod som kör spelrunda och skickar scanner som argument
					gamePlayer(input); 
					//Global variabel för vilken spelrunda inkrementeras med 1 för varje loop
					gameStart.roundCount += 1; 
					
					//Om 3 omgångar har spelats testar programmet om rundan ska avslutas
					if (gameStart.roundCount >= 4) { 
						//Om spelarnas poäng ej är samma avslutas omgången och programmet
						if (gameStart.scoreOne != gameStart.scoreTwo) { 
							programRunning = false;
							gameOn = false;
							//Metod som skriver ut spelarnas slutgiltiga poäng
							scorePrinter();
						//Om spelarnas poäng lika körs en ny omgång och meddelande skrivs ut
						} else if (gameStart.scoreOne == gameStart.scoreTwo) {
							System.out.println("Tie Score! Sudden death round");
						}
					}
				}
				
			//Val 2 i menyn -- Spela mot AI
			} else if (choiceMenu == 2) { 
				
				while (gameOn) {
					System.out.println("\nRound " + gameStart.roundCount);
					
					//Metod för spelrunda mot AI. Tar Scanner och Random som argument
					gamePlayerAi(input, generator); 
					
					//Stycket nedanför är samma kod som ovan
					gameStart.roundCount += 1;
					if (gameStart.roundCount >= 4) {
						if (gameStart.scoreOne != gameStart.scoreTwo) {
							programRunning = false;
							gameOn = false;
							scorePrinter();
						} else if (gameStart.scoreOne == gameStart.scoreTwo) {
							System.out.println("Tie Score! Sudden death round");
						}
					}
				}
			
			//Val 3 i menyn - Byt namn på spelalternativen
			} else if (choiceMenu == 3) { 
				//Metod för byta namn. Tar Scanner som argument
				ruleSettings(input);
			}
		}
	}
	
	//Skriver ut spelarnas poäng efter avsluta runda
	public static void scorePrinter() {
		System.out.println(
				"\nGame over!\nPlayer 1 score: " + gameStart.scoreOne + "\nPlayer 2 score: " + gameStart.scoreTwo);
		
		//Om spelare ett har mer poäng än spelare två
		if (gameStart.scoreOne > gameStart.scoreTwo) { 
			System.out.println("Player 1 is the Winner!");
		} else {
			System.out.println("Player 2 is the Winner!");
		}

	}
	
	//Kör spelrunda med två mänskliga spelare
	public static void gamePlayer(Scanner input) { 
		//Håller spelarnas inmatade val
		int playerOne;
		int playerTwo;

		//Skriver ut alternativen i spelet med default- eller ändrade namn
		System.out.println("Game Start\n 1 = " + gameStart.rock + "\n 2 = " + gameStart.paper + "\n 3 = "
				+ gameStart.scissor + "\nEnter number of your choice ");
		
		//Kör metoder som tar emot int-värde från spelarna och lagrar i respektive variabel
		playerOne = playerInput(input);
		playerTwo = playerInput(input);
		
		//Metod som testar vem som vann omgången. Skickar spelarnas valda alternativ som argument
		answerCheck(playerOne, playerTwo);
	}

	//Kör spelrunda med människa mot AI spelare
	public static void gamePlayerAi(Scanner input, Random generator) {
		int playerOne;

		System.out.println("Game Start\n 1 = " + gameStart.rock + "\n 2 = " + gameStart.paper + "\n 3 = "
				+ gameStart.scissor + "\nEnter number of your choice ");
		
		playerOne = playerInput(input);
		
		//Testar vem som vann omgång men här är ett av argumenten det returnerade värdet från metod som tar fram slumpad int 1-3
		answerCheck(playerOne, randomNumber(generator));
	}

	//Sköter input från spelarna
	public static int playerInput(Scanner input) {

		int playerInput = 0; 
		boolean chosing = true;

		//Körs tills val inmatat korrekt
		while (chosing) { 
			try {
				playerInput = Integer.parseInt(input.nextLine());
				if (playerInput >= 1 && playerInput <= 3) {
					chosing = false;
				} else {
					System.out.println("Your input must be a number between 1-3");
				}

			} catch (NumberFormatException e) {
				System.out.println("Your input must be a number between 1-3");
			}
		}
		return playerInput;
	}

	public static int randomNumber(Random generator) {
		int aiChoice;
		aiChoice = generator.nextInt(3) + 1;
		return aiChoice;
	}

	public static void ruleSettings(Scanner input) {

		System.out.println("Enter new name for ROCK: ");
		gameStart.rock = input.nextLine();

		System.out.println("Enter new name for PAPER: ");
		gameStart.paper = input.nextLine();

		System.out.println("Enter new name for SCISSOR: ");
		gameStart.scissor = input.nextLine();
	}

	public static void answerCheck(int playerOneAns, int playerTwoAns) {

		System.out.println(playerOneAns + " " + playerTwoAns);

		if (playerOneAns == 1 && playerTwoAns == 1) { // Rock vs Rock
			System.out.println(
					"Player 1 chose: " + gameStart.rock + "\nPlayer 2 chose: " + gameStart.rock + "\nIt's a draw!");

		} else if (playerOneAns == 1 && playerTwoAns == 2) { // Rock vs Paper
			System.out.println(
					"Player 1 chose: " + gameStart.rock + "\nPlayer 2 chose: " + gameStart.paper + "\nPlayer 2 wins!");
			gameStart.scoreTwo += 1;

		} else if (playerOneAns == 1 && playerTwoAns == 3) { // Rock vs Scissor
			System.out.println("Player 1 chose: " + gameStart.rock + "\nPlayer 2 chose: " + gameStart.scissor
					+ "\nPlayer 1 wins!");
			gameStart.scoreOne += 1;
		} else if (playerOneAns == 2 && playerTwoAns == 1) { // Paper vs Rock
			System.out.println(
					"Player 1 chose: " + gameStart.paper + "\nPlayer 2 chose: " + gameStart.rock + "\nPlayer 1 wins!");
			gameStart.scoreOne += 1;
		} else if (playerOneAns == 2 && playerTwoAns == 2) { // Paper vs Paper
			System.out.println(
					"Player 1 chose: " + gameStart.paper + "\nPlayer 2 chose: " + gameStart.paper + "\nIt's a draw!");

		} else if (playerOneAns == 2 && playerTwoAns == 3) { // Paper vs Scissor
			System.out.println("Player 1 chose: " + gameStart.paper + "\nPlayer 2 chose: " + gameStart.scissor
					+ "\nPlayer 2 wins!");
			gameStart.scoreTwo += 1;
		} else if (playerOneAns == 3 && playerTwoAns == 1) { // Scissor vs Rock
			System.out.println("Player 1 chose: " + gameStart.scissor + "\nPlayer 2 chose: " + gameStart.rock
					+ "\nPlayer 2 wins!");
			gameStart.scoreTwo += 1;
		} else if (playerOneAns == 3 && playerTwoAns == 2) { // Scissor vs Paper
			System.out.println("Player 1 chose: " + gameStart.scissor + "\nPlayer 2 chose: " + gameStart.paper
					+ "\nPlayer 1 wins!");
			gameStart.scoreOne += 1;
		} else if (playerOneAns == 3 && playerTwoAns == 3) { // Scissor vs Scissor
			System.out.println("Player 1 chose: " + gameStart.scissor + "\nPlayer 2 chose: " + gameStart.scissor
					+ "\nIt's a Draw!");
		}

	}
}
