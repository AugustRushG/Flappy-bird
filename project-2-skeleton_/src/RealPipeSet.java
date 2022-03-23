import bagel.DrawOptions;
import bagel.Image;
import bagel.Window;
import bagel.util.Point;
import bagel.util.Rectangle;

import java.util.ArrayList;


/**
 * contains all functions and images of pipes of both plastic pipes and steel pipes
 *
 * */

public class RealPipeSet {


    private final Image pipeImage=new Image("res/level/plasticPipe.png");
    private final Image steelPipeImage=new Image("res/level-1/steelPipe.png");

    private double pipeSpeed=5;
    private double topPipeY;
    private double bottomPipeY;
    private double randomHeight;
    private final DrawOptions ROTATOR = new DrawOptions().setRotation(Math.PI);
    private double pipeX = Window.getWidth();
    private ArrayList<RealPipeSet> realPipeSetArrayList;
    private final double Y_UPPER_BOUND=100;
    private final double Y_LOWER_BOUND=500;
    private int pipeKindIndicator;


    private double flameX;
    private double topFlameY;
    private double bottomFlameY;
    private final Image flameImage=new Image("res/level-1/flame.png");

    public RealPipeSet(double topPipeY,double bottomPipeY,int pipeKindIndicator){
        this.topPipeY=topPipeY;
        this.bottomPipeY=bottomPipeY;
        this.pipeKindIndicator=pipeKindIndicator;
        flameX=pipeX;
        topFlameY=topPipeY+400;
        bottomFlameY=topPipeY+568;
        realPipeSetArrayList=new ArrayList<>();

    }


    /**
     * add plastic with random y
     * add steel pipe with random y */
    public void addPlasticPie(){
        randomHeight=getRandomY();
        topPipeY=randomHeight-300;
        bottomPipeY=topPipeY+968;
        RealPipeSet realPipeSet=new RealPipeSet(topPipeY,bottomPipeY,0);
        realPipeSetArrayList.add(realPipeSet);
    }


    /**
     *add steel pipe into the array list
     */
    public void addSteelPipe(){
        randomHeight=getRandomY();
        topPipeY=randomHeight-300;
        bottomPipeY=topPipeY+968;
        RealPipeSet realPipeSet=new RealPipeSet(topPipeY,bottomPipeY,1);
        realPipeSetArrayList.add(realPipeSet);
    }


    //get random y within the range
    public double getRandomY(){
        double doubleRandom=Math.random()*(Y_UPPER_BOUND-Y_LOWER_BOUND+1)+Y_LOWER_BOUND;
        return doubleRandom;
    }


    //decide which kind of pipe to add in to the arraylist
    public void addRealPipeList(){
        double decide =getRandomY();
        if (decide<300){
            addPlasticPie();
        }
        else {
            addSteelPipe();
        }
    }

    /**
     * get this real pipe set
     * @param i
     * @return
     */
    public RealPipeSet getRealPipeSet(int i){
        return realPipeSetArrayList.get(i);
    }

    //draw plastic pipes

    /**
     * draw plastic pipe
     */
    public void renderPlasticPipe(){
        pipeImage.draw(pipeX, topPipeY);
        pipeImage.draw(pipeX, bottomPipeY, ROTATOR);
    }


    //draw steel pipes

    public void renderSteelPipe (){
        steelPipeImage.draw(pipeX,topPipeY);
        steelPipeImage.draw(pipeX,bottomPipeY,ROTATOR);
    }

    //draw flames
    public void renderFlame(){
        flameImage.draw(flameX,topFlameY);
        flameImage.draw(flameX,bottomFlameY,ROTATOR);
    }


    //draw all pipes and flames according to their pipe kind indicator
    public void realPipeUpdate(int i){
        if (pipeKindIndicator==0){
            renderPlasticPipe();
        }
        else {
            if (i>=20){
                renderFlame();
            }
            renderSteelPipe();
        }
        pipeX -= pipeSpeed;
        flameX-= pipeSpeed;
    }

    /**
     * get this pipe kind
     * @return
     */
    public int getPipeKindIndicator(){
        return pipeKindIndicator;
    }


    //get box of each object

    /**
     * get all types of boxes
     * @return
     */
    public Rectangle getTopBox() {
        return pipeImage.getBoundingBoxAt(new Point(pipeX, topPipeY));

    }

    public Rectangle getBottomBox() {
        return pipeImage.getBoundingBoxAt(new Point(pipeX, bottomPipeY));

    }

    public Rectangle getTopFlameBox(){
        return flameImage.getBoundingBoxAt(new Point(flameX,topFlameY));
    }

    public Rectangle getBottomFlameBox(){
        return flameImage.getBoundingBoxAt(new Point(flameX,bottomFlameY));
    }


    //increaseSpeed of pipe

    /**
     * increase speed or decrease speed according to spec
     * @return
     */
    public double increaseSpeed(){
        pipeSpeed=pipeSpeed+pipeSpeed*0.5;
        return pipeSpeed;
    }

    public double decreaseSpeed(){
        pipeSpeed=pipeSpeed-pipeSpeed*0.5;
        return pipeSpeed;
    }

    /**
     * set the new speed to all pipes
     * @param speed
     */
    public void setRealPipeSpeed(double speed){
        this.pipeSpeed=speed;
    }







}
