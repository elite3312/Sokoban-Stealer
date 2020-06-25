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

	
	private String map0 = 
	"HHHHH\n" + 
	"H@$.H\n" +
	"HHHHH\n";


	private String map1 =  // author : YC SHEN
		"HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH\n" +
		"H@     ##   #      #    # # # #     H\n" +
		"H# $# #  !     ##    ##         ### H\n" +
		"H#    #    ###     #     # ####     H\n" +
		"H# ##    ##      #  #  # #  !    # #H\n" +
		"H    #      # ##    #    # #   #   #H\n" +
		"H   #  ### ##  ## #   # #    #  #   H\n" +
		"H##        ###       #    #   #    #H\n" +
		"H     #  #  #  # # #    ##  #    # #H\n" +
		"H ##  ##  #     # # #   ### ##   # #H\n" +
		"H ###    #  #    !    ## #     ###  H\n" +
		"H      #      #     #      #   #.   H\n" +
		"HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH\n";


    private String map2 = 
        "HHHHHHHHHHHHHHHHHHHHHHHHHH\n" + 
        "H########################H\n" + 
        "H#.########  !     $.####H\n" + 
        "H# ########$  ###### ####H\n" + 
        "H#!   #####  $###^##!####H\n" + 
        "H# ######  $ $ ##### ####H\n" + 
        "H#$###### # ## ##### ####H\n" + 
        "H# ####   # ## #####  ..#H\n" + 
        "H# ####    $      $   ..#H\n" + 
        "H# ########!### #@##  ..#H\n" + 
        "H#!             #### ####H\n" + 
        "H########################H\n" + 
		"HHHHHHHHHHHHHHHHHHHHHHHHHH\n" ;

	
	private String map3 = 
		"HHHHHHHHHHHHHHHHHHHHH \n" +
		"H          !#!      H \n" +
		"H $   ##########    H \n" +
		"H# ## #       ####  H \n" +
		"H# ##   $#$#@  #    H \n" +
		"H  ##  !   $ #   $ #H \n" +
		"H   #%#  ######## ##H \n" +
		"H   ##! .....   # ##H \n" +
		"H## ##  .....   # ##H \n" +
		"H  $#########  ##$  H \n" +
		"H   #  !#   #   #   H \n" +
		"H   $   #  !#   $   H \n" +
		"H!  #       $   #   H \n" +
		"H   #   #   #   #  !H \n" +
		"HHHHHHHHHHHHHHHHHHHHH \n";


    private String map4 =
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

	
	private String map5 = 
		"HHHHHHHHHHHHHHHHHHHHHHHHH\n" +
		"H #####! #  ####.$!!!! .H\n" +
		"H !#!   $   # ! #!!!  !$H\n" +
		"H  #  ## ## $$# #### !# H\n" +
		"H ### ##  # #    !  ### H\n" +
		"H    $  $  $#$ #### ### H\n" +
		"H !   #  ## #    ... ## H\n" +
		"H ## ### # @    #... ## H\n" +
		"H    $  $  ###  #... ## H\n" +
		"H  !  #    #          ! H\n" +
		"H  ## #  #    ###     ##H\n" +
		"H!     !   #   !   #  ! H\n" +
		"HHHHHHHHHHHHHHHHHHHHHHHHH\n";


	private String map6 = 
		"HHHHHHHHHHHHHHHHHHHHHHHHH\n" +
		"H! ##  #! ##  !        !H\n" +
		"H      #  #      ###### H\n" +
		"H    ! #  # $#  $     ! H\n" +
		"H .#.# # ## @#   # #### H\n" +
		"H . .    #  $# ##     ! H\n" +
		"H .#.# # # $    #  #### H\n" +
		"H . . $  ##  ##       ! H\n" +
		"H .#.# #  #   ## $ ## # H\n" +
		"H    $  $        # $ $ .H\n" +
		"H   #    ######      .# H\n" +
		"H!  ######    #      ## H\n" +
		"H $    #    #  $    ### H\n" +
		"H    !      ##   !     !H\n" +
		"HHHHHHHHHHHHHHHHHHHHHHHHH\n";

	
	private String map7 = 
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




	private String map8 = 
		"HHHHHHHHHHHHHHHHHHHHHHH\n" +
		"H!     !      !     !#H\n" +
		"H  ###   #    #   ...#H\n" +
		"H  # $ # # ##$   .....H\n" +
		"H  #  $  #  $    .....H\n" +
		"H ## # # # !#!   .....H\n" +
		"H #  # # $  $ #       H\n" +
		"H  !## ## $## #     #!H\n" +
		"H #   $  $ #    $   ##H\n" +
		"H #$ ####  # ### ##   H\n" +
		"H !   $   $      $ $  H\n" +
		"H ######## $##$    $  H\n" +
		"H  $  ! .##      ##   H\n" +
		"H##########  !   #   @H\n" +
		"HHHHHHHHHHHHHHHHHHHHHHH\n";



	private String map9 =
		"HHHHHHHHHHHHHHHHHHHHHHHH\n" +
		"H###...HHHHHHHH  ######H\n" + 
		"H###.       $ H $    .HH\n" + 
		"HHHH$ $HHHHHH!H.......HH\n" + 
		"HH$H$  H.HH#H H $ $$$ HH\n" + 
		"H$  $$$   H#H H $  $  HH\n" +
		"H $$$  $$.H#H H HHHHH HH\n" + 
		"HH  $  $ HH#H H      $HH\n" +
		"HH@  HHHHH!  !HHHHHHH HH\n" + 
		"HH$ $!   !      !$   @HH\n" + 
		"H    HH !   !     HHH HH\n" + 
		"HH $ .HHHH!   ! ##HHH$HH\n" +
		"HH H$$  .HHHHHHHHHH   HH\n" + 
		"HH. $    HH!    $     HH\n" + 
		"HHHHH $.HHH  $$$    ...H\n" +
		"H####   .HH $       ...H\n" +
		"HHHHHHHHHHHHHHHHHHHHHHHH\n";



   
    public String getMap(int selection){
        switch (selection){
        	case 1 : return map1; //1
        	case 2 : return map2; //4
			case 3 : return map3; // may change here
			case 4 : return map4;
			case 5 : return map5;
			case 6 : return map6;
			case 7 : return map7;
			case 8 : return map8;
			case 9 : return map9;
			case 10: return ""; // game end, save the time
        	default : return map0;
        }
    }
}
