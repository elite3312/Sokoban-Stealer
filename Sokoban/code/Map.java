package java2020.finalProject;

public class Map {

    /************************************************************
     # : wall (penetrable) 
     H : hard wall (impenetrable) (for the boundary of themap)
     $ : baggage
     @ : player
     . : goal
     ~ : police
     % : goal with baggage
     *************************************************************/

    private String level_1 = 
        "HHHHHHHHHHHHHHHHHHHHHHHHHH\n" + 
        "H########################H\n" + 
        "H#.########  !     $.####H\n" + 
        "H#$########$  ###### ####H\n" + 
        "H#   !#####  $######!####H\n" + 
        "H# ######  $ $ ##### ####H\n" + 
        "H# ###### # ## ##### ####H\n" + 
        "H# ####   # ## #####  ..#H\n" + 
        "H# ####    $      $   ..#H\n" + 
        "H# ########!### #@##  ..#H\n" + 
        "H#              #### ####H\n" + 
        "H########################H\n" + 
        "HHHHHHHHHHHHHHHHHHHHHHHHHH\n" ;
    
    private String level_2 =
		"HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH\n" +
		"HHHH############################HHHHH\n" +
		"HHHH##          !      .......  #HHHH\n" +
		"HHHH##$    !         $         ##HHHH\n" +
		"HH####  $         ########     ##HHHH\n" +
		"HH##  $ $ #  !                ##HHHHH\n" +
		"H#### # ## #        !          #####H\n" +
		"H##   # ## #####      ##!         #HH\n" +
		"H##                               #HH\n" +
		"H##### ### #@# $#        $       #HHH\n" +
		"HHHH##                    ########HHH\n" +
		"HHHH########################HHHHHHHHH\n" +
		"HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH";

    private String level_3 =
		"HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH\n" +
		"H##################################H\n" +
		"H#####          !     ........    #H\n" +
		"H#####!    #     ##    #  $       #H\n" +
		"H#####  $ ###  ####  ###  # # ### #H\n" +
		"H###  $   #                 #     #H\n" +
		"H###!#    #  #####    ##        ###H\n" +
		"H#   # ## ######      #   ##### ###H\n" +
		"H#      ##          ! #       ! ###H\n" +
		"H###   # #@# $##       $  ####  ###H\n" +
		"H#####        #     ## #   $    ###H\n" +
		"H#! ###          $    ##  ! ###$###H\n" +
		"H##                 !  #          #H\n" +
		"H###########  #####################H\n" +
		"HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH";

    private String level_4 = 
		"HHHHHHHHHHHHHHHHHHH \n" +
		"H   ######  !     H \n" +
		"H $  ##########   H \n" +
		"H# # #       #### H \n" +
		"H# #   $#$#@  #   H \n" +
		"H  #  !   $ #   $ H \n" +
		"H !###  ######## #H \n" +
		"H  ##! .....   # #H \n" +
		"H# ##  .....   # #H \n" +
		"H $#########  ##$ H \n" +
		"H  $  !   $    $ !H \n" +
		"H  #   #  !#   #  H \n" +
		"HHHHHHHHHHHHHHHHHHH \n";

    private String old_level_4 =
		"HHHHHHHHHHHHHHHHHHH \n" +
		"H   ##############H \n" +
		"H $  #############H \n" +
		"H# # #       #####H \n" +
		"H# #   $#$#@  #   H \n" +
		"H  #      $ #   $ H \n" +
		"H !### ######### #H \n" +
		"H  ## ..%..... # #H \n" +
		"H# ## %.%..%.% # #H \n" +
		"H $########## ##$ H \n" +
		"H  $   $  $    $  H \n" +
		"H  #   #   #   #  H \n" +
		"HHHHHHHHHHHHHHHHHHH \n";

   
    public String getMap(int selection){
        switch (selection){
        	case 1 : return this.level_1;
        	case 2 : return this.level_3;
            case 3 : return this.level_4; // may change here
        	default : return "invalid selection";
        }
    }
}