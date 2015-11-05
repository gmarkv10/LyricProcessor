import java.io.IOException;

public class LyricAIDriver {

	public static void main(String[] args) {
		// TODO Get There
		FrequencyProcessor fp = new FrequencyProcessor("hello",3);
		fp.processLines();
		
		//System.out.println(fp.frqMap.toJSON(1));
		
		fp.exportJSON();
	
/*		MapSetList m = new MapSetList<String>();
		
		m.insert("One");
		m.insert("Two");
		m.insert("Three");
		m.insert("Four");
		
		m.insertAt(3, "Five");
		m.print();
	*/

	}

}


