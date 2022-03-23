import bagel.DrawOptions;
import bagel.Image;
import bagel.Window;
import bagel.util.Point;
import bagel.util.Rectangle;
import java.util.ArrayList;
import java.util.Random;

public class PipeSet extends RealPipeSet {
    private final Image pipeImage=new Image("res/level/plasticPipe.png");
    private double pipeSpeed=5;
    private double topPipeY;
    private double bottomPipeY;
    private ArrayList<Double> doubleList;
    private ArrayList<PipeSet> pipeSets;
    private double y;
    private final DrawOptions ROTATOR = new DrawOptions().setRotation(Math.PI);
    private double pipeX = Window.getWidth();




    public PipeSet(double topPipeY, double bottomPipeY) {
        super(topPipeY,bottomPipeY,0);
        this.topPipeY=topPipeY;
        this.bottomPipeY=bottomPipeY;
        doubleList=new ArrayList<>();
        pipeSets=new ArrayList<>();
        y=-300;
        doubleList.add(y);
        y=-100;
        doubleList.add(y);
        y=100;
        doubleList.add(y);
    }


    /**
     * draw pipes
     */
    public void renderPipeSet() {
        pipeImage.draw(pipeX, topPipeY);
        pipeImage.draw(pipeX, bottomPipeY, ROTATOR);
    }


    public void update() {
        renderPipeSet();
        pipeX -= pipeSpeed;
    }

    /**
     * get all kinds of boxes
     * @return
     */
    public Rectangle getTopBox() {
        return pipeImage.getBoundingBoxAt(new Point(pipeX, topPipeY));

    }

    public Rectangle getBottomBox() {
        return pipeImage.getBoundingBoxAt(new Point(pipeX, bottomPipeY));

    }


    public void randomY(){
        topPipeY=getRandomDouble(doubleList);
        bottomPipeY=topPipeY+968;
    }

    //get a random double from arraylist

    /**
     * get random double from the array list
     * @param doubleList
     * @return
     */
    public double getRandomDouble(ArrayList<Double> doubleList){
        Random rand=new Random();
        return doubleList.get(rand.nextInt(doubleList.size()));
    }

    //get a random y and set a new pipeSet and add it into the arraylist
    public void setRandomPipeSet(){
        randomY();
        PipeSet pipeSet=new PipeSet(topPipeY,bottomPipeY);
        pipeSets.add(pipeSet);
    }


    public PipeSet getPipeSets(int i){
        return pipeSets.get(i);
    }

    /**
     * increase speed or decrease speed
     * @return
     */
    //increase or decrease pipe speed
    public double increasePipeSpeed (){
        pipeSpeed+=pipeSpeed*0.5;
        return pipeSpeed;
    }
    public double decreasePipeSpeed(){
        pipeSpeed-=pipeSpeed*0.5;
        return pipeSpeed;
    }

    /**
     * set new speed
     * @param pipeSpeed
     */
    //set speed of the pipe
    public void setPipeSpeed(double pipeSpeed){
        this.pipeSpeed=pipeSpeed;
    }
}
