package coding.code;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JPanel;
import java.util.Date;
import java.awt.Font;
import java.util.Random;

public class Board extends JPanel {

    private final int OFFSET = 30;
    private final int SPACE = 40;// actor side length
    private final int LEFT_COLLISION = 1;
    private final int RIGHT_COLLISION = 2;
    private final int TOP_COLLISION = 3;
    private final int BOTTOM_COLLISION = 4;
    public static int forbutton = 0;
    private ArrayList<Wall> walls;
    private ArrayList<Baggage> baggs;
    private ArrayList<Area> areas;
    private ArrayList<HardWall> hardWalls;
    private Police c;
    private Player soko;
    private Portal portal;
    private int w = 0;// board width
    private int h = 0;// board height
    private long collisionIgnoreTime;

    private boolean isCompleted = false;
    private boolean collisionIgnore = false;// penetrate skill
    private boolean penetrateNotUsed = true;// penetrate skill
    Random ran = new Random();

    public Board() {
        initBoard();
    }

    private void initBoard() {
        addKeyListener(new TAdapter());
        setFocusable(true);
        initWorld();
    }

    public int getBoardWidth() {
        return this.w;
    }

    public int getBoardHeight() {
        return this.h;
    }

    private void initWorld() {

        walls = new ArrayList<>();
        baggs = new ArrayList<>();
        areas = new ArrayList<>();
        hardWalls = new ArrayList<>();
        String level;
        int x = OFFSET;
        int y = OFFSET;

        Wall wall;
        Baggage b;
        Area a;
        HardWall hardWall;
        Map maptest;
        maptest=new Map();
        level=(String)(maptest.getMap());
        portal = new Portal(0, 0);

        penetrateNotUsed = true;// penetrate init
        collisionIgnore = false;// penetrate init

        for (int i = 0; i < level.length(); i++) {// set w,h, actors specified by the string

            char item = level.charAt(i);

            switch (item) {
                case '\n':
                    y += SPACE;
                    if (this.w < x) {
                        this.w = x;
                    }
                    x = OFFSET;
                    break;
                case '~':
                    c = new Police(x, y);
                    // System.out.printf("x=%d,y=%d",x,y);
                    x += SPACE;
                    ;
                    break;
                case '#':
                    wall = new Wall(x, y); // create wall at (x,y)
                    walls.add(wall);
                    x += SPACE;
                    break;

                case 'H':
                    hardWall = new HardWall(x, y); // hard wall cannot be penetrated
                    hardWalls.add(hardWall);
                    x += SPACE;
                    break;

                case '$':
                    b = new Baggage(x, y);
                    baggs.add(b);
                    x += SPACE;
                    break;

                case '.':
                    a = new Area(x, y);// target area
                    areas.add(a);
                    x += SPACE;
                    break;

                case '@':
                    soko = new Player(x, y);
                    x += SPACE;
                    break;

                case ' ':
                    x += SPACE;
                    break;

                default:
                    break;
            }

            h = y;
        }
    }

    private void buildWorld(Graphics g) {

        g.setColor(new Color(250, 240, 170));
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        if (collisionIgnore) {
            // formal version
            /*
            Long checkCollisonTime = new Date().getTime() - collisionIgnoreTime;
            if (checkCollisonTime > 3000) {
                collisionIgnore = false;
            }*/

            // do nothing(debug version)
            int do_nothing;
        }

        String info = "\"portals left:" + portal.getAvailability();

        info += "\"    \"rifle availabilty = " + soko.getRifleAvailable();
        info += "\"    \"ammo = " + soko.getAmmo() + "\"";
        g.setColor(new Color(0, 0, 0));
        g.setFont(new Font("default", Font.PLAIN, 25));
        g.drawString(info, 25, 20);

        ArrayList<Actor> world = new ArrayList<>();

        world.addAll(areas);
        if (soko.getBullet() != null)
            world.add(soko.getBullet());
        world.addAll(walls);
        world.addAll(hardWalls);
        world.addAll(baggs);
        world.add(c);
        world.add(soko);
        world.add(portal);

        int tempBulletX = -500, tempBulletY = -500;
        /*
         * record new bullet x,y. If it collides with a wall, delete bullet, initialized
         * to negative numbers to avoid error
         */

        for (int i = 0; i < world.size(); i++) {

            Actor item = world.get(i);
            if (item instanceof Police && forbutton == 0) {
                System.out.printf("fkeytrigger=%d\n",forbutton);
                Police cop = (Police) item;
                int toward;
                while (true) {
                    toward = (ran.nextInt(40) % 4) + 1;
                    System.out.print(toward);
                    if (checkHardWallCollision(cop, toward)) {
                        continue;
                    } else if (checkWallCollision(cop, toward)) {
                        continue;
                    } else if (checkBagCollisionforPolice(cop, toward)) {
                        continue;
                    }

                    else
                        break;
                }
                cop.setsituation_change(toward);
                g.drawImage(item.getImage(), item.x() + 2, item.y() + 2, this);
            }
            
            if (item instanceof Player) {

                g.drawImage(item.getImage(), item.x() + 2, item.y() + 2, this);
            } else if (item instanceof Baggage) {
                g.drawImage(item.getImage(), item.x(), item.y(), this);
                if (item.x() == tempBulletX && item.y() == tempBulletY)// bullet collides with wall
                    soko.setBullet(null);
            } else if (item instanceof Portal) {
                Portal portalRef = (Portal) item;
                if (portalRef.getIsActive() == 1)
                    g.drawImage(item.getImage(), item.x() + 2, item.y() + 2, this);
            } else if (item instanceof Bullet) {
                Bullet bulletRef = (Bullet) item;
                if (bulletRef != null && bulletRef.getMaxRange() > 0) {
                    bulletRef.updateXY();
                    tempBulletX = bulletRef.x();
                    tempBulletY = bulletRef.y();
                    g.drawImage(item.getImage(), item.x() + 2 + SPACE / 2, item.y() + 2 + SPACE / 3, this);
                } else
                    soko.setBullet(null);

            }

            else if (item instanceof Wall)/* wall */ {
                g.drawImage(item.getImage(), item.x(), item.y(), this);
                if (item.x() == tempBulletX && item.y() == tempBulletY)// bullet collides with wall
                    soko.setBullet(null);
            } else {/* area */
                g.drawImage(item.getImage(), item.x(), item.y(), this);
            }

            if (isCompleted) {
                g.setColor(new Color(0, 0, 0));
                g.drawString("Completed", 25, 20);
            }

        }
        if (forbutton == 1)
                forbutton = 0;// prevent repeatly execute when bottom click
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        buildWorld(g);
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            if (isCompleted) {
                return;
            }

            int key = e.getKeyCode();

            switch (key) {
                case KeyEvent.VK_LEFT:
                    if (checkHardWallCollision(soko, LEFT_COLLISION)) {
                        return;
                    }
                    if (checkWallCollision(soko, LEFT_COLLISION)) {
                        return;
                    }
                    if (checkBagCollision(LEFT_COLLISION)) {
                        return;
                    }
                    soko.move(-SPACE, 0);
                    break;

                case KeyEvent.VK_RIGHT:
                    if (checkHardWallCollision(soko, RIGHT_COLLISION)) {
                        return;
                    }
                    if (checkWallCollision(soko, RIGHT_COLLISION)) {
                        return;
                    }
                    if (checkBagCollision(RIGHT_COLLISION)) {
                        return;
                    }
                    soko.move(SPACE, 0);
                    break;

                case KeyEvent.VK_UP:

                    if (checkHardWallCollision(soko, TOP_COLLISION)) {
                        return;
                    }
                    if (checkWallCollision(soko, TOP_COLLISION)) {
                        return;
                    }
                    if (checkBagCollision(TOP_COLLISION)) {
                        return;
                    }
                    soko.move(0, -SPACE);
                    break;

                case KeyEvent.VK_DOWN:

                    if (checkHardWallCollision(soko, BOTTOM_COLLISION)) {
                        return;
                    }
                    if (checkWallCollision(soko, BOTTOM_COLLISION)) {
                        return;
                    }
                    if (checkBagCollision(BOTTOM_COLLISION)) {
                        return;
                    }

                    soko.move(0, SPACE);
                    break;

                case KeyEvent.VK_R:// restart

                    restartLevel();

                    break;
                case KeyEvent.VK_E:// portal
                    if (portal.getIsActive() == 1) {
                        for (int i = 0; i < baggs.size(); i++) {
                            Baggage ref = baggs.get(i);
                            if (ref.x() == portal.x() && ref.y() == portal.y()) /* check if portal is blocked by bag */
                                return;

                        }
                        soko.setX(portal.x());
                        soko.setY(portal.y());
                        portal.setIsActive(0);
                    } else {
                        if (portal.getAvailability() == 0)
                            return;
                        portal.setAvailability(portal.getAvailability() - 1);
                        portal.setX(soko.x());
                        portal.setY(soko.y());
                        portal.setIsActive(1);
                    }

                    break;
                case KeyEvent.VK_W:// bullet
                    if (soko.getRifleAvailable() == 1 && soko.getAmmo() > 0) {
                        soko.setBullet(new Bullet(soko.x(), soko.y(), TOP_COLLISION));
                        soko.setAmmo(soko.getAmmo() - 1);
                    }
                    break;
                case KeyEvent.VK_S:// bullet
                    if (soko.getRifleAvailable() == 1 && soko.getAmmo() > 0) {
                        soko.setBullet(new Bullet(soko.x(), soko.y(), BOTTOM_COLLISION));
                        soko.setAmmo(soko.getAmmo() - 1);
                    }
                    break;
                case KeyEvent.VK_A:// bullet
                    if (soko.getRifleAvailable() == 1 && soko.getAmmo() > 0) {
                        soko.setBullet(new Bullet(soko.x(), soko.y(), LEFT_COLLISION));
                        soko.setAmmo(soko.getAmmo() - 1);
                    }
                    break;
                case KeyEvent.VK_D:// bullet
                    if (soko.getRifleAvailable() == 1 && soko.getAmmo() > 0) {
                        soko.setBullet(new Bullet(soko.x(), soko.y(), RIGHT_COLLISION));
                        soko.setAmmo(soko.getAmmo() - 1);
                    }
                    break;
                case KeyEvent.VK_F:// penetrate
                    if (penetrateNotUsed) {
                        // formal version
                        /*
                        collisionIgnore = true;
                        collisionIgnoreTime = new Date().getTime();
                        penetrateNotUsed = false;
                        */

                        // below this is debug version
                        penetrateNotUsed = true;
                        collisionIgnore = !collisionIgnore;
                        // above is debug version
                    }
                    break;
                default:
                    break;
            }
            forbutton = 1;System.out.printf("skeytrigger=%d",forbutton);
            repaint();
            
        }
    }

    private boolean checkHardWallCollision(Actor actor, int type) {

        switch (type) {

            case LEFT_COLLISION:
                for (int i = 0; i < hardWalls.size(); i++) {
                    HardWall hardWall = hardWalls.get(i);
                    if (actor.isLeftCollision(hardWall)) {
                        return true;
                    }
                }
                return false;

            case RIGHT_COLLISION:
                for (int i = 0; i < hardWalls.size(); i++) {
                    HardWall hardWall = hardWalls.get(i);
                    if (actor.isRightCollision(hardWall)) {
                        return true;
                    }
                }
                return false;

            case TOP_COLLISION:
                for (int i = 0; i < hardWalls.size(); i++) {
                    HardWall hardWall = hardWalls.get(i);
                    if (actor.isTopCollision(hardWall)) {
                        return true;
                    }
                }
                return false;

            case BOTTOM_COLLISION:
                for (int i = 0; i < hardWalls.size(); i++) {
                    HardWall hardWall = hardWalls.get(i);
                    if (actor.isBottomCollision(hardWall)) {
                        return true;
                    }
                }
                return false;

            default:
                break;
        }
        return false;
    }

    private boolean checkWallCollision(Actor actor, int type) {

        if(actor.getActorName() == "player"){
            if (collisionIgnore) {
                return false;
            }
        }

        switch (type) {

            case LEFT_COLLISION:
                for (int i = 0; i < walls.size(); i++) {
                    Wall wall = walls.get(i);
                    if (actor.isLeftCollision(wall)) {
                        return true;
                    }
                }
                return false;

            case RIGHT_COLLISION:
                for (int i = 0; i < walls.size(); i++) {
                    Wall wall = walls.get(i);
                    if (actor.isRightCollision(wall)) {
                        return true;
                    }
                }
                return false;

            case TOP_COLLISION:
                for (int i = 0; i < walls.size(); i++) {
                    Wall wall = walls.get(i);
                    if (actor.isTopCollision(wall)) {
                        return true;
                    }
                }
                return false;

            case BOTTOM_COLLISION:
                for (int i = 0; i < walls.size(); i++) {
                    Wall wall = walls.get(i);
                    if (actor.isBottomCollision(wall)) {
                        return true;
                    }
                }
                return false;

            default:
                break;
        }
        return false;
    }

    private boolean checkBagCollision(int type) {

        switch (type) {

            case LEFT_COLLISION:
                for (int i = 0; i < baggs.size(); i++) {
                    Baggage bag = baggs.get(i);
                    if (soko.isLeftCollision(bag)) {
                        for (int j = 0; j < baggs.size(); j++) {
                            Baggage item = baggs.get(j);
                            if (!bag.equals(item)) {
                                if (bag.isLeftCollision(item)) {
                                    return true;
                                }
                            }
                            if (checkWallCollision(bag, LEFT_COLLISION)) {
                                return true;
                            }
                            if (checkHardWallCollision(bag, LEFT_COLLISION)) {
                                return true;
                            }
                        }
                        if(!(bag.getX()-SPACE==c.x()&&bag.getY()==c.y())){
                            bag.move(-SPACE, 0);System.out.print("fault");
                        }
                        else return true;
                        isCompleted();
                    }
                }

                return false;

            case RIGHT_COLLISION:
                for (int i = 0; i < baggs.size(); i++) {
                    Baggage bag = baggs.get(i);
                    if (soko.isRightCollision(bag)) {
                        for (int j = 0; j < baggs.size(); j++) {
                            Baggage item = baggs.get(j);
                            if (!bag.equals(item)) {
                                if (bag.isRightCollision(item)) {
                                    return true;
                                }
                            }
                            if (checkWallCollision(bag, RIGHT_COLLISION)) {
                                return true;
                            }
                            if (checkHardWallCollision(bag, RIGHT_COLLISION)) {
                                return true;
                            }
                        }
                        if(!(bag.getX()+SPACE==c.getx()&&bag.getY()==c.gety())){
                            bag.move(SPACE, 0);System.out.printf("falut");
                        }
                        else return true;
                        
                        isCompleted();
                    }
                }
                return false;

            case TOP_COLLISION:
                for (int i = 0; i < baggs.size(); i++) {
                    Baggage bag = baggs.get(i);
                    if (soko.isTopCollision(bag)) {
                        for (int j = 0; j < baggs.size(); j++) {
                            Baggage item = baggs.get(j);
                            if (!bag.equals(item)) {
                                if (bag.isTopCollision(item)) {
                                    return true;
                                }
                            }

                            if (checkWallCollision(bag, TOP_COLLISION)) {
                                return true;
                            }
                            if (checkHardWallCollision(bag, TOP_COLLISION)) {
                                return true;
                            }
                        }
                        if(!(bag.getX()==c.x()&&bag.getY()-SPACE==c.y())){
                            bag.move(0, -SPACE);System.out.printf("falut");
                        }
                        else return true;
                        
                        isCompleted();
                    }
                }

                return false;

            case BOTTOM_COLLISION:
                for (int i = 0; i < baggs.size(); i++) {
                    Baggage bag = baggs.get(i);
                    if (soko.isBottomCollision(bag)) {
                        for (int j = 0; j < baggs.size(); j++) {
                            Baggage item = baggs.get(j);
                            if (!bag.equals(item)) {
                                if (bag.isBottomCollision(item)) {
                                    return true;
                                }
                            }

                            if (checkWallCollision(bag, BOTTOM_COLLISION)) {
                                return true;
                            }
                            if (checkHardWallCollision(bag, BOTTOM_COLLISION)) {
                                return true;
                            }
                        }
                        if(!(bag.getX()==c.x()&&bag.getY()+SPACE==c.y())){
                            bag.move(0, SPACE);System.out.printf("falut");
                        }
                            
                        else return true;
                        
                        isCompleted();
                    }
                }

                break;

            default:
                break;
        }

        return false;
    }

    private boolean checkBagCollisionforPolice(Actor actor, int type) {

        switch (type) {

            case LEFT_COLLISION:
                for (int i = 0; i < baggs.size(); i++) {
                    Baggage bag = baggs.get(i);
                    if (actor.isLeftCollision(bag)) {
                        return true;
                    }
                }
                return false;

            case RIGHT_COLLISION:
                for (int i = 0; i < baggs.size(); i++) {
                    Baggage bag = baggs.get(i);
                    if (actor.isRightCollision(bag)) {
                        return true;
                    }
                }
                return false;

            case TOP_COLLISION:
                for (int i = 0; i < baggs.size(); i++) {
                    Baggage bag = baggs.get(i);
                    if (actor.isTopCollision(bag)) {
                        return true;
                    }
                }
                return false;

            case BOTTOM_COLLISION:
                for (int i = 0; i < baggs.size(); i++) {
                    Baggage bag = baggs.get(i);
                    if (actor.isBottomCollision(bag)) {
                        return true;
                    }
                }
                return false;

            default:
                break;
        }
        return false;
    }

    public void isCompleted() {
        int nOfBags = baggs.size();
        int finishedBags = 0;

        for (int i = 0; i < nOfBags; i++) {
            Baggage bag = baggs.get(i);

            for (int j = 0; j < nOfBags; j++) {
                Area area = areas.get(j);
                if (bag.x() == area.x() && bag.y() == area.y()) {
                    finishedBags += 1;
                }
            }
        }

        if (finishedBags == 1) {
            soko.setRifleAvailable(1);
        } else if (finishedBags == nOfBags) {
            isCompleted = true;
            System.out.printf("aboutbagger fauult\n");
            repaint();
        }
    }

    private void restartLevel() {

        areas.clear();
        baggs.clear();
        walls.clear();

        initWorld();

        if (isCompleted) {
            isCompleted = false;
        }
    }
}
