package interactiondesign.arni0010.umu.se.a2dgame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;

import java.util.ArrayList;

/**
 * Creates all the obstacles and handles the way the move down the creen.
 */
public class ObstacleManager {

    //higher index = lower on screen = higher y value
    private ArrayList<Obstacle> obstacles;
    private int playerGap;
    private int obstacleGap;
    private int obstacleHeight;
    private int width;
    private int height;
    private int color;
    private long startTime;
    private long initTime;
    private int score = 0;
    private float gameSpeed;
    public boolean restore = false;
    public long restoredCurrentTime = System.currentTimeMillis();

    /**
     * Initializes each variable with the corresponding param, sets the start time and
     * initializes and ArrayList with Obstacles. Then it calls the method populateObstacles().
     * @param playerGap The gap in which the player is moving through the obstacles.
     * @param obstacleGap The gap between each row of obstacles.
     * @param obstacleHeight The thickness of each obstacle.
     * @param color The color of the obstacles.
     * @param speed The speed in which the obstacles are accelerating down the screen.
     */
    public ObstacleManager(int playerGap, int obstacleGap, int obstacleHeight, int color, float
            speed, int width, int height){

        this.playerGap = playerGap;
        this.obstacleGap = obstacleGap;
        this.obstacleHeight = obstacleHeight;
        this.width = width;
        this.height = height;
        this.color = color;
        this.gameSpeed = speed;

        startTime = initTime = System.currentTimeMillis();

        obstacles = new ArrayList<>();

        populateObstacles();
    }

    /**
     * Restore the state of this class when the GameActivity has been destroyed and the re-opened.
     * @param savedInstanceState the bundle with saved values.
     */
    public ObstacleManager(Bundle savedInstanceState){

        this.playerGap = savedInstanceState.getInt("playerGap");
        this.obstacleGap = savedInstanceState.getInt("obstacleGap");
        this.obstacleHeight = savedInstanceState.getInt("obstacleHeight");
        this.color = savedInstanceState.getInt("color");
        this.gameSpeed = savedInstanceState.getFloat("gameSpeed");

        startTime = savedInstanceState.getLong("startTime");
        initTime = savedInstanceState.getLong("initTime");
        int size = savedInstanceState.getInt("size");
        obstacles = new ArrayList<>();

        for(int i = 0 ; i < size; i++) {
            obstacles.add((Obstacle) savedInstanceState.getParcelable("ob" + i));
        }

        update();
    }

    /**
     * Gets all the data needed for this class to be restored if and when that happens.
     * @param outState the bundle where all the data will be saved.
     */
    public void getData(Bundle outState){

        outState.putInt("playerGap", playerGap);
        outState.putInt("obstacleGap", obstacleGap);
        outState.putInt("obstacleHeight", obstacleHeight);
        outState.putInt("color", color);
        outState.putFloat("gameSpeed", gameSpeed);
        outState.putLong("startTime", startTime);
        outState.putLong("initTime", initTime);
        outState.putInt("size", obstacles.size());

        int i=0;
        for (Obstacle ob : obstacles){
            outState.putParcelable("ob"+i, ob);
            i++;
        }
    }

    /**
     * @return the start-time.
     */
    public long getStartTime(){
        return startTime;
    }

    /**
     * @return the initialization time.
     */
    public long getInitTime(){
        return initTime;
    }

    /**
     * Checks if the player have collided with any obstacle in the ArrayList.
     * @param player The RectPlayer
     * @return true or false depending on if the player has collided or not.
     */
    public boolean playerCollide(RectPlayer player){

        for(Obstacle ob : obstacles){
            if(ob.playerCollide(player)){
                return true;
            }
        }
        return false;
    }

    /**
     * Adds individual obstacles to the ArrayList and sets the screen height to be slightly above
     * the real screen height to prevent the player to see the obstacles pop into existence
     * making it feel like they are endlessly going upwards and not that the obstacles feels like
     * they are falling on the player.
     */
    private void populateObstacles(){

        int currY = -5* height/4;

        while(currY < Constants.MENU_HEIGHT){

            int xStart = (int)(Math.random()*(width - playerGap));

            obstacles.add(new Obstacle(obstacleHeight, color, xStart, currY, playerGap, width));

            currY += obstacleHeight + obstacleGap;
        }
    }

    /**
     * Updates the obstacles current position depending on the elapsed time. The obstacles are
     * removed when getting of screen and the score is increased. If the score ever gets higher
     * than the highscore the new value to the highscore is set.
     */
    public void update(){

        int elapsedTime = (int)(System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();

        float speed = (float)(Math.sqrt(1 + (startTime-initTime)/2000.0))* height/
                gameSpeed;

        for(Obstacle ob : obstacles){
            ob.incrementY(speed * elapsedTime);
        }

        if(obstacles.get(obstacles.size() - 1).getRectangle().top >= height){

            int xStart = (int)(Math.random()*(width - playerGap));

            obstacles.add(0, new Obstacle(obstacleHeight, color, xStart, obstacles.get(0)
                    .getRectangle().top - obstacleHeight - obstacleGap,
                    playerGap, width));

            obstacles.remove(obstacles.size() - 1);
            score ++;
        }

        if(score > Constants.HIGHSCORE){

            Constants.HIGHSCORE = score;
        }

    }

    /**
     * Draws the obstacles from the ArrayList to the Canvas.
     * @param canvas The canvas on which everything is drawn.
     */
    public void draw(Canvas canvas){

        for(Obstacle ob : obstacles) {
            ob.draw(canvas);
        }

        Paint paint = new Paint();
        paint.setTextSize(100);
        paint.setColor(Color.MAGENTA);
        canvas.drawText("" + score, 50, 50 + paint.descent() - paint.ascent(), paint);
    }
}
