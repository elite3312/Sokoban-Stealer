package coding.code;

import java.util.Random;
public class Map {
    public String level_1 = 
        "HHHHHHHHHHHHHHHHHHHHH\n" + 
        "H###################H\n" + 
        "H#####   ###########H\n" + 
        "H#####$  ###########H\n" + 
        "H#####  $###########H\n" + 
        "H###  $ $ ##########H\n" + 
        "H### # ## ##########H\n" + 
        "H#   # ## #####  ..#H\n" + 
        "H# $  $      ~   ..#H\n" + 
        "H##### ### #@##  ..#H\n" + 
        "H#####     #########H\n" + 
        "H###################H\n" + 
        "HHHHHHHHHHHHHHHHHHHHH\n" ;
    
    public String level_2 =
      "HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH\n"
    + "HHHH##############################HHHHH\n"
    + "HHHH##          ~        .......  #HHHH\n"
    + "HHHH##$              $           ##HHHH\n"
    + "HH####  $         ##########     ##HHHH\n"
    + "HH##  $ $ #                     ##HHHHH\n"
    + "H#### # ## #                     #####H\n"
    + "H##   # ## #####                    #HH\n"
    + "H##                                 #HH\n"
    + "H##### ### #@# $#          $       #HHH\n"
    + "HHHH##                      ########HHH\n"
    + "HHHH##########################HHHHHHHHH\n"
    + "HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH";
    public String level_3 =
      "HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH\n"
    + "HHHH#################################H\n"
    + "HHHH##          ~     .......     #HHH\n"
    + "HHHH##$    #     ###  ###$        ##HH\n"
    + "HH####  $ ###  ####  ###  # #     ##HH\n"
    + "HH##  $ $ #        ###     # #    ##HH\n"
    + "H### # ## #  #####     #          ##HH\n"
    + "H#   # ## ######      #    ###### #HHH\n"
    + "H#      ##    ##      #           ##HH\n"
    + "####  ## #@# $#         $  #####  ##HH\n"
    + "HHHH##             ##             ###H\n"
    + "HHHH#######################   ####HHHH\n"
    + "HH#####       #######    ####    ###HH\n"
    + "HHHH#################################H\n"
    + "HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH";
    Random ran =new Random();
    public String getMap(){
        int temp=ran.nextInt(10)%3;
        if(temp==0){
            return this.level_3;
        }
        else if(temp==1){
            return this.level_2;
        }
        else if(temp==2){
            return this.level_1;
        }
        else {
            System.out.printf("Mapfault");
            return null;
        }

    }
}