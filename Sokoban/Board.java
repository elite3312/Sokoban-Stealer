package coding.code;

/*�D���B�a�����O�B�j�B�ǰe��*/
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JPanel;
import java.util.Date;
import java.awt.Font;
public class Board extends JPanel {

    private final int OFFSET = 30;//�a�����������Z��
    private final int SPACE = 40;//actor side length
    private final int LEFT_COLLISION = 1;
    private final int RIGHT_COLLISION = 2;
    private final int TOP_COLLISION = 3;
    private final int BOTTOM_COLLISION = 4;

    private ArrayList<Wall> walls;
    private ArrayList<Baggage> baggs;
    private ArrayList<Area> areas;
    private ArrayList<Wall> initialWalls;
    
    private Player soko;
    private Portal portal;
    private int w = 0;//board width
    private int h = 0;//board height
    private long collisionIgnoreTime;
    
    private boolean isCompleted = false;
    private boolean collisionIgnore = false;//tj0 f;u6ru4s/6
    private boolean penetrateNotUsed = true;

    private String level
            = "    ######\n"
            + "    ##   #\n"
            + "    ##$  #\n"
            + "  ####  $##\n"
            + "  ##  $ $ #\n"
            + "#### # ## #   ######\n"
            + "##   # ## #####  ..#\n"
            + "## $  $          ..#\n"
            + "###### ### #@##  ..#\n"
            + "    ##     #########\n"
            + "    ########\n";

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
        initialWalls = new ArrayList<>();

        int x = OFFSET;
        int y = OFFSET;

        Wall wall;
        Baggage b;
        Area a;
        portal=new Portal(0,0);

        penetrateNotUsed = true;
        collisionIgnore = false;

        for (int i = 0; i < level.length(); i++) {//set w,h, actors specified by the string

            char item = level.charAt(i);

            switch (item) {

                case '\n':
                    y += SPACE;

                    if (this.w < x) {
                        this.w = x;
                    }

                    x = OFFSET;
                    break;

                case '#':
                    wall = new Wall(x, y);//create wall at (x,y)
                    walls.add(wall);
                    x += SPACE;
                    break;

                case '$':
                    b = new Baggage(x, y);
                    baggs.add(b);
                    x += SPACE;
                    break;

                case '.':
                    a = new Area(x, y);//target area
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
        Long temp=new Date().getTime()-portal.getStartTime();
        
        if(collisionIgnore){
            Long checkCollisonTime = new Date().getTime() - collisionIgnoreTime;
            if(checkCollisonTime > 3000){
                collisionIgnore = false;
                penetrateNotUsed = false;
            }
        }

        String info="\"portal timer:";
        if(temp>5000) 
        {
    		portal.setIsActive(0);
        	info+="X";
        }
        else {
        	info+=temp.intValue()/1000;
        }
        info+="\"    \"rifle availabilty = "+soko.getRifleAvailable();
        info+="\"    \"ammo = "+soko.getAmmo()+"\"";
    	g.setColor(new Color(0, 0, 0));
    	g.setFont(new Font("Calibri", Font.PLAIN, 25));
    	g.drawString(info, 25,20);
    	
        ArrayList<Actor> world = new ArrayList<>();
        
        world.addAll(areas);
        if(soko.getBullet()!=null)
        	world.add(soko.getBullet());
        world.addAll(walls);
        world.addAll(baggs);
        world.add(soko);
        world.add(portal);		
        int tempBulletX=-500, tempBulletY=-500;/*
        record new bullet x,y. If it collides with a wall,
         delete bullet, initialized to negative numbers to avoid error
         */
        for (int i = 0; i < world.size(); i++) {

            Actor item = world.get(i);

            if (item instanceof Player ) {
                
                g.drawImage(item.getImage(), item.x() + 2, item.y() + 2, this);//�H�����𤧶����q�Z��2
            }
            else if( item instanceof Baggage) {
            	 g.drawImage(item.getImage(), item.x(), item.y(), this);
           	  if(item.x()==tempBulletX&& item.y()==tempBulletY)//bullet collides with wall
           		  soko.setBullet(null);
            }
            else if(item instanceof Portal){
            	Portal portalRef=(Portal)item;
            	if(portalRef.getIsActive()==1)
            		g.drawImage(item.getImage(), item.x() + 2, item.y() + 2, this);
            } 
            else if(item instanceof Bullet){
            	Bullet bulletRef=(Bullet)item;
            	if(bulletRef!=null&&bulletRef.getMaxRange()>0) {
            		bulletRef.updateXY();
            		tempBulletX=bulletRef.x();
            		tempBulletY=bulletRef.y();
            		g.drawImage(item.getImage(), item.x() + 2+SPACE/2, item.y() + 2+SPACE/3, this);
            	}
            	else
            		soko.setBullet(null);
            		
            }
              
            else if(item instanceof Wall)/*wall*/{
            	  g.drawImage(item.getImage(), item.x(), item.y(), this);
            	  if(item.x()==tempBulletX&& item.y()==tempBulletY)//bullet collides with wall
            		  soko.setBullet(null);
            } 
            else {/*area*/
            	g.drawImage(item.getImage(), item.x(), item.y(), this);
            }
            

            if (isCompleted) {
                
                g.setColor(new Color(0, 0, 0));
                g.drawString("Completed", 25, 20);
            }

        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        buildWorld(g);
    }

    private class TAdapter extends KeyAdapter {//��J�౵��

        @Override
        public void keyPressed(KeyEvent e) {

            if (isCompleted) {
                return;
            }

            int key = e.getKeyCode();

            switch (key) {
                
                case KeyEvent.VK_LEFT:
                    
                    if (checkWallCollision(soko,
                            LEFT_COLLISION)) {
                        return;
                    }
                    
                    if (checkBagCollision(LEFT_COLLISION)) {
                        return;
                    }
                    
                    soko.move(-SPACE, 0);
                    
                    break;
                    
                case KeyEvent.VK_RIGHT:
                    
                    if (checkWallCollision(soko, RIGHT_COLLISION)) {
                        return;
                    }
                    
                    if (checkBagCollision(RIGHT_COLLISION)) {
                        return;
                    }
                    
                    soko.move(SPACE, 0);
                    
                    break;
                    
                case KeyEvent.VK_UP:
                    
                    if (checkWallCollision(soko, TOP_COLLISION)) {
                        return;
                    }
                    
                    if (checkBagCollision(TOP_COLLISION)) {
                        return;
                    }
                    
                    soko.move(0, -SPACE);
                    
                    break;
                    
                case KeyEvent.VK_DOWN:
                    
                    if (checkWallCollision(soko, BOTTOM_COLLISION)) {
                        return;
                    }
                    
                    if (checkBagCollision(BOTTOM_COLLISION)) {
                        return;
                    }
                    
                    soko.move(0, SPACE);
                    
                    break;
                    
                case KeyEvent.VK_R://restart
                    
                    restartLevel();
                    
                    break;
                case KeyEvent.VK_E://portal
                	if(portal.getIsActive()==1) {
                		
                		soko.setX(portal.x());
                		soko.setY(portal.y());
                		portal.setIsActive(0);
                	}
                	else {
                		portal.setStartTime(new Date().getTime());
                		portal.setX(soko.x());
                    	portal.setY(soko.y());
                    	portal.setIsActive(1);
                	}
                	
                	break;
                case KeyEvent.VK_W://bullet
                	if(soko.getRifleAvailable()==1&&soko.getAmmo()>0) {
                		soko.setBullet(new Bullet(soko.x(),soko.y(),TOP_COLLISION));
                		soko.setAmmo(soko.getAmmo()-1);
                	}
                	break;
                case KeyEvent.VK_S://bullet
                	if(soko.getRifleAvailable()==1&&soko.getAmmo()>0) {
                		soko.setBullet(new Bullet(soko.x(),soko.y(),BOTTOM_COLLISION));
                		soko.setAmmo(soko.getAmmo()-1);
                	}
                	break;
                case KeyEvent.VK_A://bullet
                	if(soko.getRifleAvailable()==1&&soko.getAmmo()>0) {
                		soko.setBullet(new Bullet(soko.x(),soko.y(),LEFT_COLLISION));
                		soko.setAmmo(soko.getAmmo()-1);
                	}
                	break;
                case KeyEvent.VK_D://bullet
                	if(soko.getRifleAvailable()==1&&soko.getAmmo()>0) {
                		soko.setBullet(new Bullet(soko.x(),soko.y(),RIGHT_COLLISION));
                		soko.setAmmo(soko.getAmmo()-1);
                	}
                    break;
                case KeyEvent.VK_F://tj0 fu;6
                    if(penetrateNotUsed){
                        collisionIgnore = true;
                        collisionIgnoreTime = new Date().getTime();
                    }
                    break;
                default:
                    break;
            }

            repaint();
        }
    }

    private boolean checkWallCollision(Actor actor, int type) {

        if(penetrateNotUsed){
            if(collisionIgnore){
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
                        }
                        
                        bag.move(-SPACE, 0);
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
                        }
                        
                        bag.move(SPACE, 0);
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
                        }
                        
                        bag.move(0, -SPACE);
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
                            
                            if (checkWallCollision(bag,BOTTOM_COLLISION)) {
                                
                                return true;
                            }
                        }
                        
                        bag.move(0, SPACE);
                        isCompleted();
                    }
                }
                
                break;
                
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
                
                Area area =  areas.get(j);
                
                if (bag.x() == area.x() && bag.y() == area.y()) {
                    
                    finishedBags += 1;
                }
            }
        }
        if(finishedBags==1)
        	soko.setRifleAvailable(1);
        else if (finishedBags == nOfBags) {
            
            isCompleted = true;
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
