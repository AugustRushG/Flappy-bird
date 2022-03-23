import bagel.Image;
import bagel.Input;
import bagel.Keys;
import bagel.util.Point;
import bagel.util.Rectangle;

import java.util.ArrayList;

public class LifeBar {
    private double x;
    private final int Y=15;
    private final Image FULL_HEART_IMAGE=new Image("res/level/fullLife.png");
    private final Image EMPTY_HEART_IMAGE=new Image("res/level/noLife.png");
    private LifeBar lifeBar;

    private ArrayList<LifeBar> lifeBarArrayList;
    private ArrayList<LifeBar> lifeBarArrayListLevel1;
    private ArrayList<LifeBar> emptyHeartArrayListLevel0;
    private ArrayList<LifeBar> emptyHeartArrayListLevel1;


    public LifeBar(double x){
        this.x= x;
        lifeBarArrayList=new ArrayList<>();
        lifeBarArrayListLevel1=new ArrayList<>();
        emptyHeartArrayListLevel0=new ArrayList<>();
        emptyHeartArrayListLevel1=new ArrayList<>();
    }
    public void renderFullLifeBar(){
        FULL_HEART_IMAGE.draw(x,Y);
    }

    public void renderEmptyLifeBar(){
        EMPTY_HEART_IMAGE.draw(x,Y);
    }

    public void update(){
        renderFullLifeBar();
    }

    public void updateEmpty(){
        renderEmptyLifeBar();
    }


    public  LifeBar getLifeBar(int i){
        return lifeBarArrayList.get(i);
    }
    public LifeBar getLifeBarLevel1(int i){return lifeBarArrayListLevel1.get(i);}

    public LifeBar getEmptyLifeLevel0(int i){
        return emptyHeartArrayListLevel0.get(i);
    }
    public LifeBar getEmptyLifeLevel1(int i){
        return emptyHeartArrayListLevel1.get(i);
    }

    public void setLifeBar(int j){
        LifeBar lifeBar=new LifeBar(j);
        lifeBarArrayList.add(lifeBar);
        j=j+50;
        lifeBar=new LifeBar(j);
        lifeBarArrayList.add(lifeBar);
        j=j+50;
        lifeBar=new LifeBar(j);
        lifeBarArrayList.add(lifeBar);

    }
    public void setLifeBarArrayListLevel1(int j){
        for (int i=0;i<6;i++){
            lifeBar=new LifeBar(j);
            lifeBarArrayListLevel1.add(lifeBar);
            j=j+50;
        }
    }

    public void addEmptyLifeBarLevel0(double j){
        lifeBar=new LifeBar(j);
        emptyHeartArrayListLevel0.add(lifeBar);
    }

    public void addEmptyLifeBarLevel1(double j){
        lifeBar=new LifeBar(j);
        emptyHeartArrayListLevel1.add(lifeBar);
    }

    public void setEmptyLifeBarLevel1(double j){
        for (int i=0;i<6;i++){
            lifeBar=new LifeBar(j);
            emptyHeartArrayListLevel1.add(lifeBar);
            j=j-50;
        }
    }

    public double getX(LifeBar lifeBar){
        return  lifeBar.x;
    }



}
