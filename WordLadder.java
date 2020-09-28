import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class WordLadder {
	private static String start;
	private static String end;
	private static StringMap T;			// This map stores the dictionary of words.
	private static StringMap R;			// This map keeps track of all the words that are visited during breadth-first-search.
																	// The key field is the word that is visited, and its value field can hold the predecessor pointer.
	private static Queue Q;					// A queue to perform the breadth-first-search.
    private static int Level;
    
    private static void bfs() {
        while (!Q.isEmpty()) {
            QNode N = Q.dequeue();
            String t = N.getWord();
            int dist = N.getDist();
            char[] pop = new char[4];
            for (int i = 0; i < 4; i++) {
                pop[i] = t.charAt(i);
            }
            //System.out.println(t);
            for (int place = 0; place < 4; place++) {
                char test = pop[place];
                //System.out.println(place + " " + String.valueOf(test));
                for (char c = 'a'; c <= 'z'; c++) {
                    pop[place] = c;
                    if (String.valueOf(pop).equals(end)) {
                        Level = dist+1;
                        R.insert(String.valueOf(pop), t);
                        return;
                    }
                    String newTest = new String(pop); 
                    if (T.find(newTest) != null && R.find(newTest) == null ) {
                        R.insert(newTest, t);
                        QNode l = new QNode(dist+1, newTest);
                        Q.enqueue(l);
                        //System.out.println(pop);
                    }
                }
                pop[place] = test;
            }
        }
        return;
    }

	public static void main(String [] args) throws IOException {
		// Loading the dictionary of words into the StringMap T.
		T = new StringMap();
		File file = new File("dictionary4");
		Scanner f = new Scanner(file);
		while (f.hasNext()) {
			String word = f.nextLine();
			T.insert(word, "");
		}
		f.close();

		Scanner kb = new Scanner(System.in);
		System.out.print("Enter the start word: ");
		start = kb.nextLine();
		System.out.print("Enter the end word: ");
		end = kb.nextLine();

		// TODO: Solution to find the shortest set of words that transforms the start word to the end word.
        Q = new Queue();
        R = new StringMap();
        QNode st = new QNode(0, start);
        Q.enqueue(st);
        
        bfs();
        
        if (R.find(end) != null) {
            System.out.println("");
            System.out.println("Yay! A word ladder is possible.");
            StringNode back = R.find(end);
            String [] print = new String[Level+1];
            
            print[0] = end; 
            for (int v = 1; v < Level; v++){
                String mark = back.getValue();
                print[v] = mark;
                back = R.find(mark);
            }
            
            System.out.println(start);
            for (int h = print.length-2; h >= 0; h--) {
                System.out.println(print[h]);
            }
        }
        else System.out.println("Duh! Impossible.");
    }   
}
