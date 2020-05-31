package java2020.finalProject;

import java.util.Random;
public class Map {

    /************************************************************
     # : wall (penetrable) 
     H : hard wall (impenetrable) (for the boundary of themap)
     $ : baggage
     @ : player
     . : goal
     ~ : police
     *************************************************************/

    public String level_1 = 
        "HHHHHHHHHHHHHHHHHHHHH\n" + 
        "H###################H\n" + 
        "H#####   ###########H\n" + 
        "H#####$  ###########H\n" + 
        "H#####  $###########H\n" + 
        "H###  $ $ ##########H\n" + 
        "H### # ## ##########H\n" + 
        "H#  ~# ## #####  ..#H\n" + 
        "H# $  $          ..#H\n" + 
        "H##### ### #@##  ..#H\n" + 
        "H#####     #########H\n" + 
        "H###################H\n" + 
        "HHHHHHHHHHHHHHHHHHHHH\n" ;
    
    public String level_2 =
      "HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH\n"
    + "HHHH############################HHHHH\n"
    + "HHHH##          ~      .......  #HHHH\n"
    + "HHHH##$              $         ##HHHH\n"
    + "HH####  $         ########     ##HHHH\n"
    + "HH##  $ $ #                   ##HHHHH\n"
    + "H#### # ## #                   #####H\n"
    + "H##   # ## #####                  #HH\n"
    + "H##                               #HH\n"
    + "H##### ### #@# $#        $       #HHH\n"
    + "HHHH##                    ########HHH\n"
    + "HHHH########################HHHHHHHHH\n"
    + "HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH";

    public String level_3 =
      "HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH\n"
    + "HHHH###############################H\n"
    + "HHHH##          ~     .......   #HHH\n"
    + "HHHH##$    #     ###  ###$      ##HH\n"
    + "HH####  $ ###  ####  ###  # #   ##HH\n"
    + "HH##  $ $ #        ###     # #  ##HH\n"
    + "H### # ## #  #####     #        ##HH\n"
    + "H#   # ## ######      #  ###### #HHH\n"
    + "H#      ##    ##      #         ##HH\n"
    + "####  ## #@# $#       $  #####  ##HH\n"
    + "HHHH##             ##           ###H\n"
    + "HHHH##############    ###   ####HHHH\n"
    + "HH#####       #####    ##      ###HH\n"
    + "HHHH###############################H\n"
    + "HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH";

   
    public String getMap(int selection){
        switch (selection){
        	case 1:return this.level_1;
        	case 2:return this.level_2;
        	case 3:return this.level_3;
        	default :return "invalid selection";
        }
        
        

    }
}