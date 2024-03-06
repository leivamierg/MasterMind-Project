import java.util.Scanner;
import java.util.Random;
public class BLeivaMierGerssonBzGuessGame {
	public static void main(String[] args) {
		//DECLARATION OF VARIABLES AND CREATION OF A SCANNER OBJECT
		Scanner sc = new Scanner(System.in);
		
		String secretCode, currentInput, guessHistory, currentHint;
		int attempts;
		boolean win;
		
		
		//STARTING MESSAGE:
		System.out.println("************************************************************************************"
				+ "\n/////////////////////////////////////////////"
				+ "\n/Welcome to my MasterMind game Java Project!/"
				+ "\n/////////////////////////////////////////////"
				+ "\n************************************************************************************"	
				+ "\nYou have 20 attempts to guess the secret code"
				+ "\nThe secret code is a 4 characters string that goes from a to f"
				+ "\nIn your turn you can:"
				+ "\n-Enter your guess (a 4 character string that goes from a to f)"
				+ "\nEXAMPLE: 'aabd'\n-Enter one of the following commands:"
				+ "\n\t'new': start over a new game"
				+ "\n\t'help': display a help screen with a little guide about the game"
				+ "\n\t'buy': spend 5 attempts and reveals one of the secret code's characters"
				+ "\n\t'h': show the history of all guesses and evaluations,"
				+ "\n\t'quit': reveal the solution and quit the game"
				+ "\n************************************************************************************"
				+ "\n///////////////////////////////////"
				+ "\n/Programmed by: Leiva Mier Gersson/"
				+ "\n///////////////////////////////////"
				+ "\n************************************************************************************");
		
		
		//INITIALIZATION OF THE VARIABLES
		secretCode=generateCode(); //STORES THE SECRET CODE; CHECK THE STATIC METHOD AT THE BOTTOM
		attempts=20; //20 ATTEMPTS
		win=false; //BOOLEAN THAT CHECKS IF YOU'VE WON
		guessHistory=""; //KEEPS TRACK OF EVERY VALID GUESS AND EVERY BUY COMMAND 
		//currentInput; STORES YOUR INPUT
		//currentHint; IF 'BUY' WAS USED, THE HINT IS STORED HERE
		
		
		//GAME (LOOP):
		while(attempts>0 && !win) { //REPEAT UNTIL: OUT OF ATTEMPTS OR YOU'VE WON (win==true)
			
			//1. DISPLAY THE ATTEMPT NUMBER, WAIT FOR THE PLAYERS INPUT AND STORE IT
			System.out.print(attempts+">");
			currentInput=sc.next();
			
			//2. CHECK THE CURRENT INPUT
			
				//a) IF IT IS A COMMAND: 
/*'new'*/	if(currentInput.equalsIgnoreCase("new")) { 
				attempts=20; //resets attempts to 20
				secretCode=generateCode(); //a new secret code is generated
				System.out.println("************************************************************************************");
				System.out.println("A new game has been started"); //a message is displayed
				System.out.println("************************************************************************************");
/*'help'*/	}else if(currentInput.equalsIgnoreCase("help")) {
				//a help screen is displayed
				System.out.println("************************************************************************************"
						+ "\nHELP:"
						+ "\n************************************************************************************"
						+ "\nYou have 20 attempts to guess the secret code"
						+ "\nThe secret code is a 4 characters string that goes from a to f"
						+ "\nIn your turn you can:"
						+ "\n-Enter your guess (a 4 character string that goes from a to f)"
						+ "\nEXAMPLE: 'aabd'\n-Enter one of the following commands:"
						+ "\n\t'new': start over a new game"
						+ "\n\t'help': display a help screen with a little guide about the game"
						+ "\n\t'buy': spend 5 attempts and reveals one of the secret code's characters"
						+ "\n\t'h': show the history of all guesses and evaluations,"
						+ "\n\t'quit': reveal the solution and quit the game"
						+ "\n************************************************************************************");
/*'buy'*/	}else if(currentInput.equalsIgnoreCase("buy")) {
				if(attempts>=5) { //enough attempts to use 'buy'
					currentHint=showHint(secretCode); //generates a hint (check the static method at the bottom)
					System.out.println("HINT: "+currentHint); //displays the generated hint
					guessHistory=guessHistory+attempts+"> "+currentInput+" "+currentHint+"\n"; //adds the hint to the guess history
					attempts=attempts-5; //reduces the number of attempts by 5
				}else { //not enough attempts to use 'buy'
					System.out.println("*\nINVALID COMMAND!\nYou need at least 5 attempts to buy a hint\n*"); //display a message
				}
/*'h'*/		}else if(currentInput.equalsIgnoreCase("h")) {
				System.out.println("************************************************************************************"
						+ "\nGUESS HISTORY:");
				System.out.println(guessHistory); //displays the guess history
				System.out.println("************************************************************************************");
/*'r'*/		}else if(currentInput.equalsIgnoreCase("r")) { //debug command
				System.out.println(secretCode); //displays the secret code
/*p*/		}else if(currentInput.equalsIgnoreCase("p")){//debug command
				secretCode="fade"; //sets the secret code to 'fade'
/*quit*/	}else if(currentInput.equalsIgnoreCase("quit")) {
				System.out.println("************************************************************************************");
				System.out.println("You've quitted the game..."); //displays quit message
				System.out.println("The solution was: "+secretCode); //displays the solution
				System.exit(0); //quits the program
				System.out.println("************************************************************************************");
				
				//b) IF IT IS A VALID GUESS
			}else if(currentInput.length()==4 && isValidGuess(currentInput)) { 
				System.out.println(guessEvaluation(secretCode, currentInput)); //display the guess evaluation (check methods)
				guessHistory=guessHistory+attempts+"> "+currentInput+" "+guessEvaluation(secretCode, currentInput)+"\n"; //adds the evaluation to the guess history
				attempts--; //decrease the number of attempts by 1
				if(guessEvaluation(secretCode, currentInput).equals("XXXX")) { //if your guess is equal to the secret code
					win=true; //you won, a boolean that breaks the game loop is triggered
				}
				
				//c) IF IT IS NOR A COMMAND NOR A GUESS
			}else {
				//DISPLAY AN ERROR MESSAGE
				System.out.println("*\nINVALID INPUT!\nThe current input is nor a command nor a valid guess"
						+ "\nIf you were trying to use a command:\n-Check that you've written it correctly"
						+ "\nIf you were trying to write a guess:");
				//DISPLAY THE POSSIBLE MISTAKES
					//a)length 
				if(currentInput.length()<4) {
					System.out.println("-Your guess is too short");
				}else if(currentInput.length()>4) {
					System.out.println("-Your guess is too long");
				}
					//b)presence of invalid characters
				if(!isValidGuess(currentInput)) {
					System.out.println("-Your guess contains invalid characters");
				}
				System.out.println("*");
			}
			//4.REPEAT IF THE WHILE CONDITIONS STILL HOLD
		}
		
		//CHECK IF YOU WON OR YOU LOST
		
		if(win) {//a) YOU WON!
			System.out.println("CONGRATULATIONS, YOU'VE WON!");
		}else {//b) YOU LOST! :(
			System.out.println("You've ran out of attempts...");
		}
		
		
		//THE GAME ENDS
		
		sc.close(); //THE SCANNER GETS CLOSED
	}
	
	
	//USEFUL METHODS
	public static String generateCode() {
		char[] list = {'a','b','c','d','e','f'};
		Random generator = new Random();
		String code="";
		
		for(int i=0; i<4;i++) { //4 times for loop, to get 4 letters
			code=code+list[generator.nextInt(list.length)];
		}
		
		return code;
		//THE 'generateCode' METHOD GENERATES THE SECRET CODE AT THE START OF EACH NEW GAME
		//IT USES A FOR LOOP CONTAINING A RANDOM NUMBER GENERATOR
		//THE RANDOM NUMBER CORRESPONDS TO THE INDEX OF A CHARACTER OF THE 'list' ARRAY
		//THE 'list' ARRAY CONTAINS THE CHARACTERS 'a, b, c, d, e, f'
		//IN THAT WAY THE GENERATED NUMBER IS USED TO ADD A VALID CHARACTER TO THE 'code'
	}
	
	
	public static boolean isValidGuess(String s) { //returns true if the 4 letter attempt is valid
		char[] list = {'a','b','c','d','e','f'};
		boolean isPresent=true;
		for(char c:s.toCharArray()) {//goes trough each letter of the attempt
			isPresent=false; //reset the boolean by each character of the attempt
			for(char d:list) {//goes through the letters in the list (a-f)
				if(Character.toLowerCase(c)==d) { 
					isPresent=true; //if present in the list reset the boolean and break the list loop
					break;
				}
			}
			if(!isPresent) { //the if statement might or might not be triggered
				break; //There is a character of the attempt that is not a letter from a to f
			}
		}
		return isPresent;
		
		//THE 'isValidGuess' METHOD RETURNS TRUE IF THE PLAYERS GUESS IS VALID: IT CONTAINS ONLY A-F
		//IT DOESNT CHECK THE LENGTH OF THE GUESS SINCE IT IS DONE ALREADY WITHIN THE LOOP
		//THE VALID CHARACTER ARRAY, 'list', IS USED AGAIN
		//THE FIRST ENHANCED FOR LOOP GOES THROUGH EVERY CHARACTER OF THE GUESS
		//THE NESTED FOR LOOP GOES THROUGH EVERY "VALID" CHARACTER AND COMPARES IT WITH THE CURRENT CHARACTER OF THE GUESS
		//IF THE CURRENT CHARACTER IS INSIDE THE VALID CHARACTERS ARRAY THE NESTED LOOP IS BROKEN AND THE FIRST LOOP
		//GOES THROUGH THE NEXT CHARACTER
		//IF THE CURRENT CHARACTER IS NOT VALID --> THE GUESS IS NOT VALID
		//AN IF STATEMENT IS TRIGGERED WHICH BREAKS THE FIRST ENHANCED FOR LOOP AND RETURNS FALSE
		//IF THE 'IF' STATEMENTE IS NEVER TRIGGERED --> THE GUESS IS VALID
		//IN THAT CASE IT RETURNS TRUE
	}

	
	public static String guessEvaluation(String code, String attempt) { 
		boolean[] found= {false, false, false, false}; //found is linked to the secret code
		
		String evaluation="";
		
		for (int i = 0; i < found.length; i++) {
			if(code.charAt(i)==Character.toLowerCase(attempt.charAt(i))) {
				evaluation=evaluation+"X";
				found[i]=true; //the character in this index has been found (code)
			}
		}
		
		for (int i = 0; i < found.length; i++) { //code(0-3)
			for (int j = 0; j < found.length; j++) { //attempt (0-3)
				if(!found[i] && code.charAt(i)==Character.toLowerCase(attempt.charAt(j))) { //if the character hasn't been found yet 
					evaluation=evaluation+"-";
					found[i]=true; //the character in this index has been found (code)
				}
			}
		}
		
		return evaluation;
		
		//THE 'guessEvaluation' METHOD RETURN THE SPECIFIC X/-/' ' COMBINATION DEPENDING ON THE SIMILARITIES BETWEEN
		//THE VALID GUESS AND THE SECRET CODE.
		//FIRST IT CHECKS IF ANY CHARACTER OF THE GUESS MATCHES PERFECTLY WITH ANY CHARACTER OF THE SECRET CODE
		//IF IT DOES: IT ADDS AN X TO THE 'evaluation' STRING AND THE CORRESPONDING INDEX OF THE CHARACTER 
		//IS USED TO MODIFY THE 'found' ARRAY, SO THE METHOD REMEMBERS WHICH CHARACTERS HAVE BEEN FOUND ALREADY.
		//THEN IT CHECKS IF ANY CHARACTER OF THE GUESS IS PRESENT ON THE SECRET CODE BUT IN THE WRONG POSITION
		//THIS IS DONE THROUGH A NESTED FOR LOOP, THAT COMPARES EVERY CHARACTER OF THE GUESS TO EVERY CHARACTER OF
		//THE SECRET CODE.
		//IF THEY MATCH AND THE CHARACTER HASNT BEEN FOUND ALREADY (X or -): '-' IS ADDED TO 'evaluation' AND
		//THE 'found' ARRAY IS MODIFIED AGAIN SO THE CURRENT CHARACTER DOESNT TRIGGER ANY OTHER '-'.
	}
	
	
	public static String showHint(String code) {
		String hint ="";
		int index;
		Random generator = new Random();
		index=generator.nextInt(4); //generates the index of the character that will be revealed
		for(int i=0; i<code.length();i++) {
			if(index==i) {
				hint=hint+code.charAt(i);
			}else {
				hint=hint+".";
			}
		}
		return hint;
		
		//THE 'showHint' METHOD GIVES A HINT TO THE PLAYER WHEN THE COMMAND 'buy' IS USED
		//A NUMBER BETWEEN 0 AND 3 IS GENERATED, THAT CORRESPOND TO THE INDEX OF ONE CHARACTER OF THE SECRET CODE
		//A FOR LOOP THAT GOES THROUGH EVERY INDEX OF 'code' ADDS A '.' TO 'hint' WHENEVER THE INDEX DOESNT MATCH 
		//THE RANDOM NUMBER, AND WHEN IT DOES MATCH THE CORRESPONDING CHARACTER AT THE CORRESPONDING INDEX IS
		//ADDED TO 'hint'.
	}
}
