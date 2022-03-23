import bagel.Image;
import bagel.Window;
import bagel.util.Point;
import bagel.util.Rectangle;
import java.util.ArrayList;



/**
 * weapon class that contains all functions need for the project
 * */
public class Weapon {
    private final Image rockImage=new Image("res/level-1/rock.png");
    private final Image bombImage=new Image("res/level-1/bomb.png");

    private final double Y_UPPER_BOUND=100;
    private final double Y_LOWER_BOUND=500;
    private double weaponX = Window.getWidth();
    private double weaponY;
    private double weaponSpeed=5;
    private ArrayList<Weapon> weaponArrayList;
    private int weaponKindIndicator;

    public Weapon(int weaponKindIndicator,double weaponY){
        weaponArrayList=new ArrayList<>();
        this.weaponKindIndicator=weaponKindIndicator;
        this.weaponY=weaponY;
    }

    public void shoot() {
        weaponX+=weaponSpeed*2;
    }


    //same function just like in the pipe class, get random y and add them into the list
    public double getRandomY(){
        double doubleRandom=Math.random()*(Y_UPPER_BOUND-Y_LOWER_BOUND+1)+Y_LOWER_BOUND;
        return doubleRandom;
    }

    /**
     * add random weapon into the arrray list
     */
    public void addRandomWeapon(){
        double doubleRandom1=Math.random();
        if (doubleRandom1<=0.5){
            Weapon rock=new Weapon(0,getRandomY());
            weaponArrayList.add(rock);
        }
        else {
            Weapon bomb=new Weapon(1,getRandomY());
            weaponArrayList.add(bomb);
        }

    }


    //get weapon in the array list to implement more functions
    public Weapon getWeapon(int i){
        return weaponArrayList.get(i);
    }

    //draw bomb or rock according to their weapon kind indicator
    public void renderBomb(){
        bombImage.draw(weaponX,weaponY);
    }

    public void renderRock(){
        rockImage.draw(weaponX,weaponY);
    }


    /**
     * update weapon
     */
    public void weaponUpdate(){
        if (weaponKindIndicator==0){
            renderRock();
        }
        else {
            renderBomb();
        }
        weaponX-=weaponSpeed;
    }


    public Rectangle getWeaponBox(){
        if (weaponKindIndicator==0){

            return rockImage.getBoundingBoxAt(new Point(weaponX,weaponY));
        }
        else {

            return bombImage.getBoundingBoxAt(new Point(weaponX,weaponY));
        }
    }


    //this function is created so that weapon will get carried by bird
    public void carriedByBird(double weaponX, double weaponY){
        this.weaponX=weaponX;
        this.weaponY=weaponY;
    }

    //get weapon kind indicator
    public int getWeaponKindIndicator(){
        return weaponKindIndicator;
    }

    public void setWeaponSpeed(double weaponSpeed){
        this.weaponSpeed=weaponSpeed;
    }

    //detect if weapon intersect with pipe, return true or false statement
    public boolean detectCollision(Rectangle weaponBox, Rectangle topPipeBox, Rectangle bottomPipeBox){
        return weaponBox.intersects(topPipeBox)|| weaponBox.intersects(bottomPipeBox);
    }




}
