import bagel.Image;
import bagel.Window;

public class Bomb extends Weapon{
    private Image bombImage=new Image("res/level-1/bomb.png");
    private double bombX= Window.getWidth();
    private double bombY=getRandomY();
    private final double BOMB_SPEED=5;
    private int weaponKindIndicator;

    public Bomb(int weaponKindIndicator,double bombY) {
        super(weaponKindIndicator,bombY);
    }

    public void renderBomb(){
        bombImage.draw(bombX,bombY);
    }

    public void update(){
        renderBomb();
        bombX-=BOMB_SPEED;
    }

}
