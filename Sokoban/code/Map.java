package java2020.finalProject;

public class Map {

    /************************************************************
     # : wall (penetrable) 
     H : hard wall (impenetrable) (for the boundary of the map)
     $ : baggage
     @ : player
     . : goal
     ! : police
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
		"HHHH##############################HHH\n" +
		"HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH\n";

    private String level_3 =
		"HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH\n" +
		"H##################################H\n" +
		"H#####           !     .......... #H\n" +
		"H##!       #     ##    #  $  #### #H\n" +
		"H#####    ###  ####  ######   ### #H\n" +
		"H###  $   #                 #     #H\n" +
		"H###!#     $ #####$   ###       ###H\n" +
		"H# $ # ##### ###      #   ##### ###H\n" +
		"H#      ##        #####       ! ###H\n" +
		"H###   ###@##$##    !  $  ####  ###H\n" +
		"H#####  ###   #     ####   $    ###H\n" +
		"H#! ## $  #           ##  ! ###$###H\n" +
		"H#            ##    !  #          #H\n" +
		"H##################################H\n" +
		"HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH\n";

    private String level_4 = 
		"HHHHHHHHHHHHHHHHHHH \n" +
		"H   ##      !     H \n" +
		"H $  ##########   H \n" +
		"H# # #       #### H \n" +
		"H# #   $#$#@  #   H \n" +
		"H  #  !   $ #   $ H \n" +
		"H !###  ######## #H \n" +
		"H  ##! .....   # #H \n" +
		"H# ##  .....   # #H \n" +
		"H $#########  ##$ H \n" +
		"H  $  !   $    $ !H \n" +
		"H !#   #  !#   #  H \n" +
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

	private String level_5 =
		"HHHHHHHHHHH\n" +
		"HHHH...HHHH\n" + 
		"HHHH   HHHH\n" + 
		"HHHH$ $HHHH\n" + 
		"HH$H$  H.HH\n" + 
		"H$  $$$   H\n" +
		"H $$$  $$ H\n" + 
		"HH@ $  $ HH\n" +
		"HHHHHHHHHHH\n";


	private String level_6 =
		"HHHHHHHHHHHHHHHH\n" +
		"HH @ HHHH   !! H\n" + 
		"HH$$$HHHH!     H\n" + 
		"H    HHHHH    !H\n" + 
		"HH $  HHHH  !  H\n" +
		"HH H$$  .HHHHHHH\n" + 
		"HH  $    HHHHHHH\n" + 
		"HHHHH $.HHHHHHHH\n" +
		"HHHHH   .HHHHHHH\n" +
		"HHHHHHHHHHHHHHHH\n";


   
    public String getMap(int selection){
        switch (selection){
        	case 1 : return this.level_1;
        	case 2 : return this.level_3;
			case 3 : return this.level_4; // may change here
			case 4 : return this.level_5;
			case 5 : return this.level_6;
        	default : return "invalid selection";
        }
    }
}