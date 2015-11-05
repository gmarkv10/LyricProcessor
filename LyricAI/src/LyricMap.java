import java.util.HashMap;
import java.util.Map;

public abstract class LyricMap<K> {
	
	Map<K, Integer> map;
	
	public LyricMap(){
		map = new MyMap<K, Integer>();
	}

}
