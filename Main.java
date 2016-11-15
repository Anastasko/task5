public class Main {
	
	public static void main(String [] args){

//		String text = "У Львові багато снігу";
//		String text = "У Львові багато снігу але він вже розтає";
		String text = "Сів метелик на травичку";
		
		System.out.println(text.length());
		System.out.println("text = " + text);
		
		GeneticGuess guess = new GeneticGuess(text);
		
		guess.start();
	
	}
	
}
