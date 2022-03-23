import bagel.Image;
import bagel.Window;
import bagel.util.Rectangle;

public class Rock extends Weapon{
    private Image rockImage=new Image("res/level-1/rock.png");
    private double rockX= Window.getWidth();
    private double rockY;
    private final double ROCK_SPEED=5;


    public Rock(int weaponKindIndicator,double rockY){
        super(weaponKindIndicator,rockY);
    }

    @Override

    public void renderRock(){
        rockImage.draw(rockX,rockY);
    }

    public void update(){
        renderRock();
        rockX-=ROCK_SPEED;
    }
}
