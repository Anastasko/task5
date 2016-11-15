import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GeneticGuess {
	
	public static final int POPULATION = 10;
	public static final int INF = (int) 1e+6;

	private static Random rand = new Random();
	
	private String text;
	
	public GeneticGuess(String text){
		this.text = text;
		init();
	}

	int code(char ch) {
		String str = "";
		str += ch;
		return Character.codePointAt(str, 0);
	}

	List<Character> alphabet = new ArrayList<>();

	void init() {
		alphabet.add(' ');
		alphabet.add('і');		
		alphabet.add('ї');
		alphabet.add('є');
		for (int c = code('а'); c <= code('я'); ++c) {
			char value = (char) c;
			alphabet.add(new Character(value));
		}
		for (int c = code('А'); c <= code('Я'); ++c) {
			char value = (char) c;
			alphabet.add(new Character(value));
		}
//		System.out.print("letters =");
//		for(Character c : alphabet){
//			System.out.print(c);
//		}
//		System.out.println();
	}
	
	public int distance(String s){
		int sol = 0;
		for(int i=0; i<s.length(); ++i){
			if (s.charAt(i) != this.text.charAt(i)){
				++sol;
			}
		}
		return sol;
	}
	
	public Instance instance(String s){
		Instance i = new Instance();
		i.string = s;
		i.diff = distance(s);
		return i;
	}
	
	public Instance getInstance(){
		String s = "";
		for(int i=0; i<this.text.length(); ++i) {
			s+= hromosome();
		}
		return instance(s);
	}
	
	private Character hromosome() {
		int index = rand.nextInt(alphabet.size());
		return alphabet.get(index);
	}

	public Instance cross(Instance instance, Instance instance2){
		String res = new String();
		for(int i=0; i<instance.string.length(); ++i){
			int r = rand.nextInt();
			if (r%2 == 1){
				res += instance.string.charAt(i);
			} else {
				res += instance2.string.charAt(i);
			}
		}
		return instance(res);
	}
	
	public Instance mutate(Instance inst){
		String s = "";
		for(int i=0; i<inst.string.length(); ++i){
			int r = rand.nextInt();
			if (r%4 == 1){ // 25% мутацій
				s += hromosome();
			} else {
				s += inst.string.charAt(i);
			}
		}		
		return instance(s);
	}
	
	public void start(){
		
		System.out.println("SZ=" + alphabet.size());
		List<Instance> strings = new ArrayList<>();
		for(int i=0; i<POPULATION; ++i){
			strings.add(getInstance());
		}
		int steps = 0;
		int best = INF;
		while (true){
			++steps;

			Collections.sort(strings, new InstanceComparator());
			
			// remove bad
			while (strings.size() > POPULATION){
				strings.remove(strings.size()-1);
			}

			// нові
			for(int i=POPULATION-2; i<POPULATION; ++i){
				strings.remove(i);
				strings.add(i, getInstance());
			}
			
			// кросовер
			for(int i = 0; i<POPULATION; ++i)
				for(int j = i+1; j<POPULATION; ++j){
					Instance res = cross(strings.get(i), strings.get(j));
					if (res.diff < strings.get(i).diff ||
							res.diff < strings.get(j).diff){
						strings.add(res);
					}
				}
			
			// мутації
			for(int i = 0; i<POPULATION; ++i){
				Instance mutated = mutate(strings.get(i));
				if (mutated.diff < strings.get(i).diff){
					strings.add(mutated);
				}
			}
						
			if (strings.get(0).diff < best){
				best = strings.get(0).diff;
				System.out.println("досягнуто - " + strings.get(0).string + " (" + best + ")");
			}
			if (strings.get(0).diff == 0){
				break;
			}
		}
		System.err.println("знайдено '" + strings.get(0).string + "' за " + steps + " кроків");
	}
}
