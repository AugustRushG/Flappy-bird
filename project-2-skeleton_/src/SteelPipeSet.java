import bagel.DrawOptions;
import bagel.Image;
import bagel.Window;
import org.lwjgl.system.CallbackI;

import java.util.ArrayList;
import java.util.Random;

public class SteelPipeSet extends RealPipeSet{
    private Image pipeImage=new Image("res/level-1/steelPipe.png");
    private final int PIPE_GAP = 168;
    private double pipeSpeed=5;
    private ArrayList<SteelPipeSet> steelPipeSets;
    private double randomHeight;
    private final DrawOptions ROTATOR = new DrawOptions().setRotation(Math.PI);
    private double pipeX = Window.getWidth();
    private double topPipeY;
    private double bottomPipeY;
    private final int PIPE_KIND_INDICATOR=1;

    public SteelPipeSet(double topPipeY, double bottomPipeY) {
        super(topPipeY,bottomPipeY,1);
        steelPipeSets=new ArrayList<>();
    }


    public void setSteelPipeSets(){
        randomHeight=getRandomY();
        topPipeY=randomHeight-300;
        bottomPipeY=topPipeY+968;
        SteelPipeSet steelPipeSet=new SteelPipeSet(topPipeY,bottomPipeY);
        System.out.println(steelPipeSets.size());
        steelPipeSets.add(steelPipeSet);
    }

    public void renderSteelPipeSet(){
        pipeImage.draw(pipeX, topPipeY);
        pipeImage.draw(pipeX, bottomPipeY, ROTATOR);
    }

    public void updateSteelPipe(){
        renderSteelPipeSet();
        pipeX-=pipeSpeed;
    }

    public SteelPipeSet getSteelPipeSet(int i){
        return steelPipeSets.get(i);
    }

    public int getPIPE_KIND_INDICATOR(){
        return PIPE_KIND_INDICATOR;
    }



}
