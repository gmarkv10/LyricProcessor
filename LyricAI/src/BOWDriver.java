import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
public class BOWDriver {
	
	static HashMap map = new HashMap<String, Integer>();

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
//
//		String lyric = "you used to call me on my you used to you used to";
//		String[] words = lyric.split(" ");
//		for(String 	w : words){
//			Integer freq =  (Integer) map.get(w);
//			map.put(w,freq == null? 1 : ++freq);
//		}
//		
//		
//		Set<String> set =  new TreeSet<String>();
//		set.add("you");
//		set.add("used");
//		set.add("to");
//		set.add("call");
//		set.add("me");
//		set.add("on");
//		set.add("my");
//		Iterator<String> ite = set.iterator();
//		while(ite.hasNext()){
//			String s = ite.next();
//			System.out.println(s + " - " + map.get(s));
//		}
//		
		BagOfWords b =  new BagOfWords("test");
		b.playSong(1);
//		Iterator it = b.sortedKeys.iterator();
//		it.next();
//		System.out.println(map.get(it.next()));
//		
//		b.map.put("0", 2);
//		it = b.sortedKeys.iterator();
//		it.next();
//		System.out.println(b.map.get(it.next()));
		

	}
	
	

}
