package java2020.finalProject;

public class Map {

    /************************************************************
     # : wall (penetrable) 
     H : hard wall (impenetrable) (for the boundary of the map)
     $ : treasure
     @ : player
     . : goal
     ! : police
     % : goal with treasure
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
	/*
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
	*/

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
		"HHHHHHHHHHHHHHHHHHHHH \n" +
		"H          !#!      H \n" +
		"H $   ##########    H \n" +
		"H# ## #       ####  H \n" +
		"H# ##   $#$#@  #    H \n" +
		"H  ##  !   $ #   $ #H \n" +
		"H  ##%#  ######## ##H \n" +
		"H   ##! .....   # ##H \n" +
		"H## ##  .....   # ##H \n" +
		"H  $#########  ##$  H \n" +
		"H   #  !#   #   #   H \n" +
		"H   $   #  !#   $   H \n" +
		"H!  #       $   #   H \n" +
		"H   #   #   #   #  !H \n" +
		"HHHHHHHHHHHHHHHHHHHHH \n";

	
	private String level_5 =
		"HHHHHHHHHHHHHHHHHHHHHHHH\n" +
		"H###...HHHHHHHH  ######H\n" + 
		"H###.       $ H $    .HH\n" + 
		"HHHH$ $HHHHHH!H.......HH\n" + 
		"HH$H$  H.HH#H H $ $$$ HH\n" + 
		"H$  $$$   H#H H $  $  HH\n" +
		"H $$$  $$.H#H H HHHHH HH\n" + 
		"HH  $  $ HH#H H      $HH\n" +
		"HH@  HHHHH!  !HHHHHHH HH\n" + 
		"HH$$$!          !    !HH\n" + 
		"H    HH !   !     HHH HH\n" + 
		"HH $ .HHHH!   ! ##HHH$HH\n" +
		"HH H$$  .HHHHHHHHHH   HH\n" + 
		"HH. $    HH     $     HH\n" + 
		"HHHHH $.HHH  $$$    ...H\n" +
		"H####   .HH $       ...H\n" +
		"HHHHHHHHHHHHHHHHHHHHHHHH\n";


	private String level_6 =  // author : YC SHEN
	"HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH\n" +
	"H@     ##   #      #    # # # #     H\n" +
	"H# $# #  !     ##    ##         ### H\n" +
	"H#    #    ###     #     # ####     H\n" +
	"H  ##    ##      #  #  # #  !    # #H\n" +
	"H    #      # ##    #    #     #    H\n" +
	"H   #  ### ##  ## #   # #    #  #   H\n" +
	"H##        ###       #    #   #    #H\n" +
	"H     #  #  #  # # #    ##  #    # #H\n" +
	"H ##  ##  #     # # #   ### ##   # #H\n" +
	"H ###    #  #    !    ## #     ###  H\n" +
	"H      #      #     #      #   #.   H\n" +
	"HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH\n";

	private String tempLevel = 
	"HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH\n" +
	"H      ##   #      #    # # # #     H\n" +
	"H#  # #  !     ##    ##         ### H\n" +
	"H#    #    ###     #     # ####     H\n" +
	"H  ##    ##      #  #  # #  !    # #H\n" +
	"H    #      # ##    #    #     #    H\n" +
	"H   #  ### ##  ## #   # #    #  #   H\n" +
	"H##        ###       #    #   #    #H\n" +
	"H     #  #  #  # # #    ##  #    # #H\n" +
	"H ##  ##  #     # # #   ### ##   # #H\n" +
	"H ###    #  #    !    ## #     ###  H\n" +
	"H      #      #     #      #   #.$@ H\n" +
	"HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH\n";

	/* surpass the screen size
	private String level_7 = 
	"             HHHHHH      \n" +
	"             H   HHHHH   \n" +
	"         HHHHH # #   H   \n" +
	"    HHHHHH    $  # $ H   \n" +
	"    H  !    ## ###  $HH  \n" +
	"HHHHH   HH  H $ $ $$  HHH\n" +
	"H   ## ######   $    !  H\n" +
	"H #  # #   @# ########  H\n" +
	"H  # #$# ####      ### HH\n" +
	"H# $   # ..## #### ### H \n" +
	"H#  ##$# ..##$#  $  $  H \n" +
	"H $ ## ##..     # # #  H \n" +
	"H  $  ! #..# ## #   HHHH \n" +
	"HH  # # #..#    HHHHH    \n" +
	" H# #   #..HHHHHH        \n" +
	" H  #####..H             \n" +
	" H  !    ..H             \n" +
	" HH  ###  HH             \n" +
	"  HHHHHHHHH              \n";
	*/

	
	private String level_8 = 
	"HHHHHHHHHHHHHHHHHHHHHHH\n" +
	"H.... #@###    # !   %H\n" +
	"H....        #$$  #   H\n" +
	"H.... ##   ### #  #  !H\n" +
	"H....   #  ### ## ##  H\n" +
	"H.   ## #     $ $ #   H\n" +
	"H  !    # !##$ $  #!  H\n" +
	"H ##$##!####  # ##### H\n" +
	"H!      ##    # #    !H\n" +
	"H$   ! ### #  $    ###H\n" +
	"H  ##   #!   $ $ #    H\n" +
	"H%!#%   # ## ## $   ! H\n" +
	"H###%    $$  !  $$    H\n" +
	"H%%##    $ ### ## ### H\n" +
	"H!    !    #!   ! #!  H\n" +
	"HHHHHHHHHHHHHHHHHHHHHHH\n";

	private String level_9 = 
	"";

   
    public String getMap(int selection){
        switch (selection){
        	case 1 : return this.level_1;
        	case 2 : return this.level_4;
			case 3 : return this.level_3; // may change here
			case 4 : return this.level_8;
			case 5 : return this.level_5;
			case 6 : return this.tempLevel; // this.level_6
        	default : return this.level_1;
        }
    }
}