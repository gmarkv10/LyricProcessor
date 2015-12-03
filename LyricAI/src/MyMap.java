import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;

public class MyMap<K,V> implements Map<K,V> {

	MapSetList<K> KList = new MapSetList<K>();
	MapSetList<V> VList = new MapSetList<V>();
	@Override
	public int size() {
		// TODO Auto-generated method stub
		if(KList.size != VList.size){ //FAILSAFE
			System.err.println("WARNING, YOUR SIZES ARE OFF: K("+KList.size+")  V("+VList.size+")" );
		}
		return VList.size; //had to pick one
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		if(KList.size != VList.size){ //FAILSAFE
			System.err.println("WARNING, YOUR SIZES ARE OFF: K("+KList.size+")  V("+VList.size+")" );
		}
		return VList.isEmpty(); //had to pick one
	}
	
	@Override
	public boolean containsKey(Object key) {
		// TODO Auto-generated method stub
		return KList.contains((K) key) >= 0 ? true : false;
	}

	@Override
	public boolean containsValue(Object value) {
		// TODO Auto-generated method stub
		return VList.contains((V) value) >= 0 ? true : false;
	}

	@Override
	public V get(Object key) {
		// TODO Auto-generated method stub
		try{
			return (V) VList.getIdx(KList.contains((K) key)).getValue();
		} catch(NullPointerException e){
			return null;
		}
	}

	@Override
	public V put(K key, V value) {
		// TODO Auto-generated method stub
		if( containsKey(key) ){
			int vActiveIdx = KList.contains(key); //returns index
			VList.removeAt(vActiveIdx);
			int kActiveIdx = VList.insert(value);
			KList.swap(vActiveIdx, kActiveIdx);
		}
		else{
			KList.insertAt(VList.insert(value), key);
		}
		return value;
	}
	
	public static MyMap merge(MyMap[] m){
		MyMap<String, Integer> ret = new MyMap();
		for(int i = 0; i < m.length; i++){
			System.out.println("Merging: " + i);
			MyMap map = m[i];
			if(map == null) continue;
			Node ptr = map.KList.head;
			for(int j = 0;j < map.size(); j++ ){
				String key = ptr.getValueS();
				Integer freq = (Integer) ret.get(key);
				ret.put(key, (Integer) ((freq == null) ? map.get(key) : freq + (Integer) map.get(key)));
				ptr = ptr.getNext();
			}
		}
		return ret;
	}

	@Override
	public V remove(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set<K> keySet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<V> values() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String toJSON(){
		String s = "{\n";
		KList.resetPtr(); VList.resetPtr();
		int count = 0;
		for(int i = 0; i < size() - 1 ;i++){
			s += KList.getPtr().toString() + ": " + VList.getPtr().toString() + ",\n";
			KList.advancePtr(); VList.advancePtr();
		}
		s += KList.getPtr().toString() + ": " + VList.getPtr().toString() + "\n}";
		return s;
	}
	

}