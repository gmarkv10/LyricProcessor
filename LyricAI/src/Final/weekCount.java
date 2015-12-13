package Final;

public class weekCount {

	public String week;
	public int count;
	
	
	public weekCount(String week, int count){
		this.week = week;
		this.count = count;
	}
	
}

/*public class yearCount implements Comparable<yearCount> {

	public int year;
	public int count;
	
	
	public yearCount(String week, int count){
		this.year = Helpers.extractYear(week);
		this.count = count;
	}


	@Override
	public int compareTo(yearCount o) {
		if(this.count == o.count) return 0;
		if(this.count > o.count) return 1;
		else return -1;
	}
	
}*/
