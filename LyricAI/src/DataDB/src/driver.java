package DataDB.src;

import java.io.IOException;

public class driver{  
    public static void main( String[] args ) throws Exception{  
             parser p = new parser();
             databaseMethods dbm = new databaseMethods();
             
             //dbm.create383ProjectDB();
             //dbm.populateWeeklyurls();
             //dbm.populateRankData();
             //System.out.println("{/}");
            // p.getLyrics123("kim carnes", "cry like a baby");
            // p.getLyrics123("drake", "hotline bling");
             //p.getLyricsGenius("drake", "hotline bling");
           //  String test = "Wiz Khalifa featuring Charlie puth";
           //  System.out.println(p.trimArtist(test));
             
             String test2 = "[verse1] hey dasdas dasd asd sd [hook] asdasdasd asdas a sdas [outro] asdasd..d asd... as";
             System.out.println(p.trimGeniusBrackets(test2));
           // dbm.pullLyrics();
             //System.out.println(p.removeApos("don't cost a' thanggg'"));
             
            //  dbm.findUnusualChars();
              
            // String test = "hey & big sean & poop";
          //   System.out.println(p.trimArtist(test));
             
            // String test = "(50000) * (600000) + 3 + ....(tony).... + (lollol)  ";
             // System.out.println(p.trimParens(test));
    }  
}  
