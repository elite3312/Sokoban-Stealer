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

	private String maps[] = new String[]{
		// map 0  (for debugging)
		"HHHHH\n" + 
		"H@$.H\n" +
		"HHHHH\n",

		// map 1
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
		"HHHHHHHHHHHHHHHHHHHHHHHHHH\n",

		// map 2
		"HHHHHHHHHHHHHHHHHHHHH \n" +
		"H          !#!      H \n" +
		"H $   ##########    H \n" +
		"H# ## #       ####  H \n" +
		"H# ##   $#$#@  #    H \n" +
		"H  ##  !   $ #   $ #H \n" +
		"H   #%#  ######## ##H \n" +
		"H   ##! .....   # #^H \n" +
		"H## ##  .....   # ##H \n" +
		"H  $#########  ##$  H \n" +
		"H   #  !#   #   #   H \n" +
		"H   $   #  !#   $   H \n" +
		"H!  #       $   #   H \n" +
		"H   #   #   #   #  !H \n" +
		"HHHHHHHHHHHHHHHHHHHHH \n",

		// map 3
		"HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH\n" +
		"H##################################H\n" +
		"H#####           !     .......... #H\n" +
		"H##!       #     ##    #  $  #### #H\n" +
		"H#####    ###  ####  ######   ### #H\n" +
		"H###  $   #                 #     #H\n" +
		"H###!#     $ #####$   ###       ###H\n" +
		"H# $ # ##### ###      #   ##### ###H\n" +
		"H#      ##        #####       ! #^#H\n" +
		"H###   ###@##$##    !  $  ####  ###H\n" +
		"H#####  ###   #     ####   $    ###H\n" +
		"H#! ## $  #           ##  ! ###$###H\n" +
		"H#            ##    !  #          #H\n" +
		"H##################################H\n" +
		"HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH\n",

		// map 4
		"HHHHHHHHHHHHHHHHHHHHHHHHH\n" +
		"H #####! #  ####.$!!!! .H\n" +
		"H !#!   $   # ! #!!!  !$H\n" +
		"H  #  ## ## $$# #### !# H\n" +
		"H ### ##  # #    !  ### H\n" +
		"H    $  $  $#$ #### #^# H\n" +
		"H !   #  ## #    ... ## H\n" +
		"H ## ### # @    #... ## H\n" +
		"H    $  $  ###  #... ## H\n" +
		"H  !  #    #          ! H\n" +
		"H  ## #  #    ###     ##H\n" +
		"H!     !   #   !   #  ! H\n" +
		"HHHHHHHHHHHHHHHHHHHHHHHHH\n",

		// map 5
		"HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH\n" +
		"H@     ##   #      #    # # # #     H\n" +
		"H# $# #  !     ##    ##         ### H\n" +
		"H#    #    ###     #     # ####     H\n" +
		"H# ##    ##      #  #  # #  !    # #H\n" +
		"H    #      # ##    #    # #   #   #H\n" +
		"H   #  ### ##  ## #   # #    #  #   H\n" +
		"H##        ###       #    #   #    #H\n" +
		"H     #  #  #  # # #    ##  #    # #H\n" +
		"H ##  ##  #     # # #   #^# ##   # #H\n" +
		"H ###    #  #    !    ## #     ###  H\n" +
		"H      #      #     #      #   #.   H\n" +
		"HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH\n",

		// map 6
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
		"HHHHHHHHHHHHHHHHHHHHHHHHH\n",

		// map 7
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
		"HHHHHHHHHHHHHHHHHHHHHHH\n",

		// map 8
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
		"H########^#  !   #   @H\n" +
		"HHHHHHHHHHHHHHHHHHHHHHH\n",

		// map 9
		"HHHHHHHHHHHHHHHHHHHHHHHH\n" +
		"H#^#...HHHHHHHH  ######H\n" + 
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
		"H#^##   .HH $       ...H\n" +
		"HHHHHHHHHHHHHHHHHHHHHHHH\n"
	};

	public int getMapCount() {
		// need to decrease 1 beacuse the debugging map
		return this.maps.length - 1;
	}

    public String getMap(int selection) {

		if(selection >= 0 && selection < maps.length)
			return maps[selection];
		
		return "";
    }
}
