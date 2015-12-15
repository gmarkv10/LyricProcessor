# LyricProcessor
Lyric Processor for AI - CS383

Gabriel Markarian & Anthony Depace

Predicting Release Dates of Popular Works Using Bag Of Words

ABSTRACT
    Studies show popular songs imitate and influence popular culture.[3] By that metric, as culture changes, so too should the songs which are popular.  By extracting the right features from the songs that are the most popular, we sought to predict the time in which the song was popular. The most accessible features for the authors to analyze were the lyrics, so a database of 7801 song lyrics from top 50 lists going back to 1980 was materialized. Several attempts at seeking out features in popular lyrics are discussed which classify on a mix of relevant attributes to popular music. While many of our own lyrical features were brainstormed, acceptable classification performance came from a Bag of Words classifier implemented in Java and evaluated by a 10-fold cross validation. The success of the tf-idf weighting scheme with Bag Of Words and the pitfalls of several underperforming classification strategies are discussed
    
    You will need a github account to access this!
    
    When you are configured in your terminal or desktop app do a 
    
      git clone https://github.com/gmarkv10/LyricProcessor.git
      
    Open Eclipse and do File->New->Java Project
    Name that project LyricProcessor
    
    The wizard will build the project for you if you've done it right
    
    !IMPORTANT
    go to the queries.java file and switch the comments off for tony's path if you have a mac and gabe's path if you have windows! Paths are funny that way.
    
    ***All useful work is in the Final package***
    The CVDriver class has 2 functions:
  
LyricManager lm = new LyricManager(FOLDS);
lm.makeFoldFiles();
  //You will need to run this first, it will generate 10 fold files and take quite some time
	//After this is done once, you wont have to change them unless you change the statistics to comment it out	
FinalCrossValidator fcv = FinalCrossValidator.getInstance(FOLDS);
fcv.crossValidate(1990);
  //outputs to the results.txt file. Do a Save As if you wanna save a make modifications to FinalCrossValidator
      
