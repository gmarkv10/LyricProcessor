package Final;

import java.util.HashMap;

public class CVDriver {

	private static final int FOLDS = 10;
	
	public static void main(String[] args) throws Exception{
		FinalCrossValidator fcv = FinalCrossValidator.getInstance(FOLDS);
		LyricManager lm = new LyricManager(FOLDS);
		lm.makeFoldFile(0);
		

		
		
	}
	
}
