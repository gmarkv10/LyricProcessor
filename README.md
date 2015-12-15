# LyricProcessor
Lyric Processor for AI - CS383

Gabriel Markarian & Anthony Depace

Predicting Release Dates of Popular Works Using Bag Of Words


You will need a github account to access this!

    When you are configured in your terminal or desktop app do a 
    git clone https://github.com/gmarkv10/LyricProcessor.git
      
    Open Eclipse and do File->New->Java Project
    Name that project LyricProcessor
    The wizard will build the project for you if you've done it right
    !IMPORTANT
    go to the queries.java file 
    switch the comments off for tony's path if you have a mac 
    and gabe's path if you have windows! Paths are funny that way.
    


***All useful work is in the Final package***
The CVDriver class has 2 functions:
  
LyricManager lm = new LyricManager(FOLDS);
lm.makeFoldFiles();
  //You will need to run this first, it will generate 10 fold files and take quite some time
	//After this is done once, you wont have to change them unless you change the statistics to comment it out	
FinalCrossValidator fcv = FinalCrossValidator.getInstance(FOLDS);
fcv.crossValidate(1990);
  //outputs to the results.txt file. Do a Save As if you wanna save a make modifications to FinalCrossValidator
      
