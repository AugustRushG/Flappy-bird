import bagel.*;
import bagel.util.Rectangle;


/**
 * SWEN20003 Project 2, Semester 2, 2021
 *
 * @author Hao Xu
 */
public class ShadowFlap extends AbstractGame {
    private final Image BACKGROUND_IMAGE = new Image("res/level-0/background.png");
    private final Image BACKGROUND_IMAGE_LEVEL1 = new Image("res/level-1/background.png");
    private final String INSTRUCTION_MSG = "PRESS SPACE TO START";
    private final String GAME_OVER_MSG = "GAME OVER!";
    private final String CONGRATS_MSG = "CONGRATULATIONS!";
    private final String SCORE_MSG = "SCORE: ";
    private final String FINAL_SCORE_MSG = "FINAL SCORE: ";
    private final String PRESS_TO_SHOOT="PRESS 'S' TO SHOOT";
    private final String LEVEL_UP="LEVEL-UP!";
    private final int FONT_SIZE = 48;
    private final Font FONT = new Font("res/font/slkscr.ttf", FONT_SIZE);
    private final int SCORE_MSG_OFFSET = 75;
    private Bird bird;
    private Bird1 bird1Level1;
    private PipeSet pipeSet;
    private int pipeIndicator=0;
    private LifeBar lifeBar;
    private LifeBar emptyLife;
    private int score;
    private int scoreLevel0=0;
    private boolean gameOn;
    private boolean collision;
    private boolean outOfHearts;
    private boolean win;
    private boolean isDone;
    private boolean levelUp;
    private boolean levelUp1;
    private int lifeBarIndicatorLevel0;
    private int lifeBarIndicatorLevel1;
    private int pipeIndicator1;
    private final int constant=1;
    private int frameCount=0;
    private int frameCount1=0;
    private double speed=0;
    private int timeScale=1;
    private int pipeRealIndicator=0;
    private int pipeSpawnRateLevel0=100;



    //level 1 variables
    private int pipeIndicatorLevel1=0;
    private RealPipeSet realPipeSet;
    private int realPipeIndicatorLevel1=0;
    private boolean collisionLevel1;
    private int pipeGoneLevel1=0;
    private int frameCountWeapon=0;
    private LifeBar emptyLifeLevel1;
    private int weaponIndicator=0;
    private Weapon weapon;
    private boolean weaponCollision;
    private int realWeaponIndicator=0;
    private int weaponGotCarried=0;
    private boolean weaponCarrying;
    private boolean gotShoot;
    private int shootRange=0;
    private boolean weaponPipeCollision;
    private int scoreIndicatorLevel1=0;
    private int flameFrameCounter=0;
    private int flameLastingCounter=0;
    private boolean flameCollision;
    private int timeScaleLevel1=1;
    private double realPipeSpeed=5;
    private double weaponSpeed=5;
    private int pipeSpawnRteLevel1=100;
    private int frameCountLevel1=0;





    public ShadowFlap() {
        super(1024, 768, "ShadowFlap");
        bird = new Bird();
        bird1Level1=new Bird1();
        lifeBar = new LifeBar(100);
        emptyLife=new LifeBar(100);
        pipeSet=new PipeSet(0,0);
        pipeSet.setRandomPipeSet();
        score = 0;
        lifeBarIndicatorLevel0=3;
        lifeBarIndicatorLevel1=6;
        gameOn = false;
        collision = false;
        win = false;
        levelUp = false;
        levelUp1= false;
        outOfHearts=false;
        isDone=false;
        pipeIndicator1=0;

        //level 1 variables
        realPipeSet=new RealPipeSet(0,0,0);
        collisionLevel1=false;
        emptyLifeLevel1=new LifeBar(100);
        weapon=new Weapon(0,0);
        weaponCollision=false;
        weaponCarrying=false;
        weaponPipeCollision=false;
        gotShoot=false;
        flameCollision=false;
    }

    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {

        ShadowFlap game = new ShadowFlap();
        game.run();
    }

    /**
     * Performs a state update.
     * allows the game to exit when the escape key is pressed.
     */
    @Override
    public void update(Input input) {

        //levelup1 is for the 20 frames before level up

        if (!levelUp &&!levelUp1){
            BACKGROUND_IMAGE.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);
        }

         // draw level1 background when levelup = true

        else if (levelUp){
            BACKGROUND_IMAGE_LEVEL1.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);
        }


        /*
         * draw the scene when switching from level 0 to level 1
         * and stop when frame count hit 20
         * */
        else{
            BACKGROUND_IMAGE.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);
            renderScore();
            renderLevelUp();
            frameCount1++;
            if (frameCount1==50){
                levelUp1=false;
                levelUp=true;
                gameOn=false;
                score=0;
            }
        }



        if (input.wasPressed(Keys.ESCAPE)) {
            Window.close();
        }

        // game has not started
        if (!gameOn) {
            renderInstructionScreen(input);
        }

        if(!gameOn&&levelUp){
            renderShoot();
        }

        // game over
        if (outOfHearts) {
            renderGameOverScreen();
        }

        // game won
        if (win) {
            renderWinScreen();
        }



        // game is active level 1
        /*
         * contains all logic for level 1*/
        if (gameOn && !outOfHearts && !win &&levelUp) {
            bird1Level1.update(input);
            /*
             * set life bar first, then draw life bar */
            lifeBar.setLifeBarArrayListLevel1(100);

            for (int i=lifeBarIndicatorLevel1-1;i>-1;i--){
                lifeBar.getLifeBarLevel1(i).update();
            }

            for (int i=0;i<6-lifeBarIndicatorLevel1;i++){
                emptyLifeLevel1.getEmptyLifeLevel1(i).updateEmpty();
            }

            frameCountWeapon++;
            frameCountLevel1++;
            flameFrameCounter++;



            //add pipe
            /*
             * for every frame count level 1 hit the pipe spawn rate create new pipe
             * same with weapon spawn rate*/
            if (frameCountLevel1==pipeSpawnRteLevel1){
                realPipeSet.addRealPipeList();
                pipeIndicatorLevel1++;
                frameCountLevel1=0;
            }

            //add weapon

            if (frameCountWeapon==(int) Math.ceil(pipeSpawnRteLevel1)-30){
                weapon.addRandomWeapon();
                weaponIndicator++;
                frameCountWeapon=0;
            }

            if (weaponIndicator>0){
                /*
                 * real weapon indicator ++ if the bird pass the weapon */
                if (bird1Level1.getBox().right()>=weapon.getWeapon(realWeaponIndicator).getWeaponBox().right()){
                    realWeaponIndicator++;
                }
                if (realWeaponIndicator==weaponIndicator){
                    realWeaponIndicator=weaponIndicator--;
                }
                Rectangle weaponBox=weapon.getWeapon(realWeaponIndicator).getWeaponBox();
                weaponCollision=detectCollisionWeapon(bird1Level1.getBox(),weaponBox);
                /*
                 * if bird collide with the weapon, the bird carries weapon*/
                if (weaponCollision && !weaponCarrying){
                    weaponGotCarried=realWeaponIndicator;
                    weaponCarrying=true;
                    weaponCollision=false;
                }

                //shoot function
                /*
                 * if weapon gor carried, weapon follows bird location until got shoot or got switched to another
                 * weapon*/
                if(weaponCarrying){
                    if (input.wasPressed(Keys.S)){
                        gotShoot=true;
                        weaponCarrying=false;
                    }
                    else{
                        weapon.getWeapon(weaponGotCarried).carriedByBird(bird1Level1.getX()+5,bird1Level1.getY());
                    }
                }

                /*
                 * if weapon got shoot change the move speed of weapon and set the shooting range of weapon*/
                if (gotShoot && !weaponPipeCollision){
                    weapon.getWeapon(weaponGotCarried).shoot();
                    shootRange++;
                    if (weapon.getWeapon(weaponGotCarried).getWeaponKindIndicator()==0 && shootRange==25){
                        shootRange=0;
                        weaponGotCarried++;
                        gotShoot=false;

                    }
                    else if (shootRange==50){
                        shootRange=0;
                        weaponGotCarried++;
                        gotShoot=false;
                    }

                }
            }

            //draw weapon and pipes
            for (int i=weaponGotCarried;i<weaponIndicator;i++){
                weapon.getWeapon(i).setWeaponSpeed(weaponSpeed);
                weapon.getWeapon(i).weaponUpdate();
            }

            for (int i=pipeGoneLevel1;i<pipeIndicatorLevel1;i++){
                realPipeSet.getRealPipeSet(i).setRealPipeSpeed(realPipeSpeed);
                realPipeSet.getRealPipeSet(i).realPipeUpdate(flameFrameCounter);

            }




            if (pipeIndicatorLevel1>0){

                Rectangle topPipeBox=realPipeSet.getRealPipeSet(realPipeIndicatorLevel1).getTopBox();
                Rectangle bottomPipeBox=realPipeSet.getRealPipeSet(realPipeIndicatorLevel1).getBottomBox();
                System.out.println(realPipeIndicatorLevel1);
                /*real pipe indicator will only grow if it has passed the pipe */
                if (bird1Level1.getBox().right()>=realPipeSet.getRealPipeSet(realPipeIndicatorLevel1).getTopBox().right()){
                    scoreIndicatorLevel1++;
                    realPipeIndicatorLevel1++;
                }

                /*
                 * if bird hit the pipe or the flame lose one life*/
                collisionLevel1=detectCollision(bird1Level1.getBox(),topPipeBox,bottomPipeBox);
                if (collisionLevel1 || flameCollision){
                    pipeGoneLevel1=realPipeIndicatorLevel1+1;
                    realPipeIndicatorLevel1++;
                    lifeBarIndicatorLevel1--;
                    collisionLevel1=false;
                    flameCollision=false;
                    emptyLifeLevel1.addEmptyLifeBarLevel1(lifeBar.getX(lifeBar.getLifeBarLevel1(lifeBarIndicatorLevel1)));
                    outOfHearts(lifeBarIndicatorLevel1);
                }

                //respawn if out of bound
                if (birdLevel1OutOfBound()){
                    bird1Level1=new Bird1();
                    lifeBarIndicatorLevel1--;
                    emptyLifeLevel1.addEmptyLifeBarLevel1(lifeBar.getX(lifeBar.getLifeBarLevel1(lifeBarIndicatorLevel1)));
                    outOfHearts(lifeBarIndicatorLevel1);
                }

                //for weapon destroy pipes

                if (weaponIndicator>0){
                    Rectangle weaponGotShoot=weapon.getWeapon(weaponGotCarried).getWeaponBox();
                    weaponPipeCollision=detectCollision(weaponGotShoot,topPipeBox,bottomPipeBox);
                    if (weaponPipeCollision &&gotShoot){

                        //if its plastic pipe no matter which weapon, all works


                        if (realPipeSet.getRealPipeSet(realPipeIndicatorLevel1).getPipeKindIndicator()==0){
                            gotShoot=false;
                            weaponPipeCollision=false;
                            pipeGoneLevel1=realPipeIndicatorLevel1+1;
                            realPipeIndicatorLevel1++;
                            weaponGotCarried++;
                            scoreIndicatorLevel1++;
                        }


                        //if its bomb works for all
                        else if (weapon.getWeapon(weaponGotCarried).getWeaponKindIndicator()==1){
                            gotShoot=false;
                            pipeGoneLevel1++;
                            weaponGotCarried++;
                            weaponPipeCollision=false;
                            scoreIndicatorLevel1++;
                        }

                        else{
                            gotShoot=false;
                            weaponPipeCollision=false;
                            weaponGotCarried++;
                        }


                    }
                }



                //logic for flame appear

                if (flameFrameCounter>=20){
                    flameLastingCounter++;
                    Rectangle flameTop= realPipeSet.getRealPipeSet(realPipeIndicatorLevel1).getTopFlameBox();
                    Rectangle bottomFlameBox= realPipeSet.getRealPipeSet(realPipeIndicatorLevel1).getBottomFlameBox();
                    flameCollision=detectCollision(bird1Level1.getBox(),flameTop,bottomFlameBox);

                    if (flameLastingCounter==3){
                        flameFrameCounter=0;
                        flameLastingCounter=0;
                    }
                }


            }



            //time scale change
            /*time scale change, same with level 0.*/
            if (input.wasPressed(Keys.L)&&timeScaleLevel1<5){
                timeScaleLevel1++;
                realPipeSpeed=realPipeSet.increaseSpeed();
                pipeSpawnRteLevel1= (int) Math.ceil ((pipeSpawnRteLevel1/1.5));
                weaponSpeed=realPipeSpeed;
            }

            if (input.wasPressed(Keys.K)&&timeScaleLevel1>1){
                timeScaleLevel1--;
                pipeSpawnRteLevel1= (int) Math.ceil ((pipeSpawnRteLevel1*1.5));
                realPipeSpeed=realPipeSet.decreaseSpeed();
                weaponSpeed=realPipeSpeed;
            }




            updateScore(scoreIndicatorLevel1);

            //finish game if reaches 30
            if (scoreIndicatorLevel1==30){
                win=true;
            }
        }












        if (gameOn && !outOfHearts && !win &&!levelUp1 &&!levelUp) {

            lifeBar.setLifeBar(100);

            bird.update(input);
            Rectangle birdBox = bird.getBox();

            /*
            * draw life bar as array list
            * */
            for (int i=lifeBarIndicatorLevel0-1;i>-1;i--){
                lifeBar.getLifeBar(i).update();
            }

            frameCount++;

            //level up gameOn=false
            //level up when score =10
            if (scoreLevel0==10){
                levelUp1=true;
            }

            /*every 100 frames add a new pipe into the arraylist
            Originally, but pipe Spawn rate will change according to the timescale*/
            if (frameCount==pipeSpawnRateLevel0){
                pipeSet.setRandomPipeSet();
                pipeIndicator++;
                isDone=false;
                frameCount=0;
            }


            //pipe spawn continuously
            /*
            * time scale change whenever L or K got pressed and hasn't reach the limit
            * */
            if (input.wasPressed(Keys.K)&&timeScale>1){
                speed=pipeSet.decreasePipeSpeed();
                timeScale--;
                pipeSpawnRateLevel0= (int) Math.ceil ((pipeSpawnRateLevel0*1.5));

            }

            if (input.wasPressed(Keys.L) &&timeScale<5){
                speed= pipeSet.increasePipeSpeed();
                timeScale++;
                pipeSpawnRateLevel0= (int) Math.ceil ((pipeSpawnRateLevel0/1.5));

            }



            /*
            * draw pipes that has not been collided with the bird
            * */
            for (int i=pipeIndicator1;i<pipeIndicator;i++){
                if (speed!=0){
                    pipeSet.getPipeSets(i).setPipeSpeed(speed);
                }
                pipeSet.getPipeSets(i).update();
            }

            Rectangle topPipeBox;
            Rectangle bottomPipeBox;

            /*
             * only draw when pipe got created
             * */
            if (pipeIndicator>=1 ){
                topPipeBox = pipeSet.getPipeSets(pipeRealIndicator).getTopBox();
                bottomPipeBox = pipeSet.getPipeSets(pipeRealIndicator).getBottomBox();
                //for scoring, everytime pass the pipe add one
                if (birdBox.right()>=pipeSet.getPipeSets(pipeRealIndicator).getTopBox().right()&&!isDone){
                    pipeRealIndicator++;
                    isDone=true;
                    scoreLevel0++;
                }
                collision=detectCollision(birdBox,topPipeBox,bottomPipeBox);
            }


            //collision will cause lose life
            /*
             * collision happens, pipe that has passed ++, empty heart ++, full heart --, check if out of hearts
             * */

             if (collision){
                pipeIndicator1=pipeRealIndicator+1;
                pipeRealIndicator++;
                collision=false;
                emptyLife.addEmptyLifeBarLevel0(lifeBar.getX(lifeBar.getLifeBar(lifeBarIndicatorLevel0-1)));
                lifeBarIndicatorLevel0=lifeBarIndicatorLevel0-constant;
                outOfHearts(lifeBarIndicatorLevel0);
            }



            else if (birdOutOfBound()){
                emptyLife.addEmptyLifeBarLevel0(lifeBar.getX(lifeBar.getLifeBar(lifeBarIndicatorLevel0-1)));
                lifeBarIndicatorLevel0=lifeBarIndicatorLevel0-constant;
                bird=new Bird();
                outOfHearts(lifeBarIndicatorLevel0);

            }


            /*the idea is to draw empty heart from the opposite direction from the full heart
             * */
            for (int i=0;i<3-lifeBarIndicatorLevel0;i++){
                emptyLife.getEmptyLifeLevel0(i).updateEmpty();
            }
            updateScore(scoreLevel0);
        }

    }


    /**
     * methods provided by Betty from solution project A
     * @return
     */
    public boolean birdOutOfBound() {
        return (bird.getY() > Window.getHeight()) || (bird.getY() < 0);
    }

    public boolean birdLevel1OutOfBound(){
        return (bird1Level1.getY() > Window.getHeight()) || (bird1Level1.getY() < 0);
    }

    public void renderInstructionScreen(Input input) {
        // paint the instruction on screen
        FONT.drawString(INSTRUCTION_MSG, (Window.getWidth()/2.0-(FONT.getWidth(INSTRUCTION_MSG)/2.0)), (Window.getHeight()/2.0-(FONT_SIZE/2.0)));
        if (input.wasPressed(Keys.SPACE)) {
            gameOn = true;
        }
    }

    public void renderGameOverScreen() {
        FONT.drawString(GAME_OVER_MSG, (Window.getWidth()/2.0-(FONT.getWidth(GAME_OVER_MSG)/2.0)), (Window.getHeight()/2.0-(FONT_SIZE/2.0)));
        String finalScoreMsg = FINAL_SCORE_MSG + score;
        FONT.drawString(finalScoreMsg, (Window.getWidth()/2.0-(FONT.getWidth(finalScoreMsg)/2.0)), (Window.getHeight()/2.0-(FONT_SIZE/2.0))+SCORE_MSG_OFFSET);
    }

    public void renderScore(){
        String finalScoreMsg = FINAL_SCORE_MSG + score;
        FONT.drawString(finalScoreMsg, (Window.getWidth()/2.0-(FONT.getWidth(finalScoreMsg)/2.0)), (Window.getHeight()/2.0-(FONT_SIZE/2.0)));
    }

    public void renderShoot(){
        FONT.drawString(PRESS_TO_SHOOT,(Window.getWidth()/2.0-(FONT.getWidth(PRESS_TO_SHOOT)/2.0)), (Window.getHeight()/2.0-(FONT_SIZE/2.0))+68);
    }

    public void renderLevelUp(){
        FONT.drawString(LEVEL_UP,(Window.getWidth()/2.0-(FONT.getWidth(LEVEL_UP)/2.0)), (Window.getHeight()/2.0-(FONT_SIZE/2.0))+68);
    }

    public void renderWinScreen() {
        FONT.drawString(CONGRATS_MSG, (Window.getWidth()/2.0-(FONT.getWidth(CONGRATS_MSG)/2.0)), (Window.getHeight()/2.0-(FONT_SIZE/2.0)));
        String finalScoreMsg = FINAL_SCORE_MSG + score;
        FONT.drawString(finalScoreMsg, (Window.getWidth()/2.0-(FONT.getWidth(finalScoreMsg)/2.0)), (Window.getHeight()/2.0-(FONT_SIZE/2.0))+SCORE_MSG_OFFSET);
    }

    /**
     * detect collision between bird and pipes/ or any other object with pipes
     * @param birdBox
     * @param topPipeBox
     * @param bottomPipeBox
     * @return
     */
    public boolean detectCollision(Rectangle birdBox, Rectangle topPipeBox, Rectangle bottomPipeBox) {
        // check for collision
        return birdBox.intersects(topPipeBox) ||
                birdBox.intersects(bottomPipeBox);
    }

    /**
     * detect collision with bird and weapon
     * @param birdLevel1Box
     * @param weaponBox
     * @return
     */

    public boolean detectCollisionWeapon(Rectangle birdLevel1Box, Rectangle weaponBox){
        return birdLevel1Box.intersects(weaponBox);
    }


    /**
     * update score according to how many pipes have passed
     * @param i
     */
    public void updateScore(int i) {
        score=i;
        String scoreMsg = SCORE_MSG + score;
        FONT.drawString(scoreMsg, 100, 100);

        // detect win

    }

    /**
     * check if the bird is running out of life
     * @param lifeBarIndicatorLevel
     */
    public void outOfHearts(int lifeBarIndicatorLevel){
        if (lifeBarIndicatorLevel<=0){
            outOfHearts=true;
        }
    }


}
