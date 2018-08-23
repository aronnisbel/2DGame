package interactiondesign.arni0010.umu.se.a2dgame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import java.util.ArrayList;

/**
 * Creates all the obstacles and handles the way the move down the creen.
 */
public class ObstacleManager {

    //higher index = lower on screen = higher y value
    private ArrayList<Obstacle> obstacles;
    private int playerGap, obstacleGap, obstacleHeight, color;
    private long startTime, initTime;
    private int score = 0;
    private float gameSpeed;

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
            speed){

        this.playerGap = playerGap;
        this.obstacleGap = obstacleGap;
        this.obstacleHeight = obstacleHeight;
        this.color = color;
        this.gameSpeed = speed;

        startTime = initTime = System.currentTimeMillis();

        obstacles = new ArrayList<>();

        populateObstacles();
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

        int currY = -5*Constants.SCREEN_HEIGHT/4;

        while(currY < Constants.MENU_HEIGHT){

            int xStart = (int)(Math.random()*(Constants.SCREEN_WIDTH - playerGap));

            obstacles.add(new Obstacle(obstacleHeight, color, xStart, currY, playerGap));

            currY += obstacleHeight + obstacleGap;
        }
    }

    /**
     * Updates the obstacles current position depending on the elapsed time. The obstacles are
     * removed when getting of screen and the score is increased. If the score ever gets higher
     * than the highscore the new value to the highscore is set.
     */
    public void update(){

        if(startTime < Constants.INIT_TIME)
            startTime = Constants.INIT_TIME;

        int elapsedTime = (int)(System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();

        float speed = (float)(Math.sqrt(1 + (startTime-initTime)/2000.0))*Constants.SCREEN_HEIGHT/
                gameSpeed;

        for(Obstacle ob : obstacles){
            ob.incrementY(speed * elapsedTime);
        }

        if(obstacles.get(obstacles.size() - 1).getRectangle().top >= Constants.SCREEN_HEIGHT){

            int xStart = (int)(Math.random()*(Constants.SCREEN_WIDTH - playerGap));

            obstacles.add(0, new Obstacle(obstacleHeight, color, xStart, obstacles.get(0)
                    .getRectangle().top - obstacleHeight - obstacleGap,
                    playerGap));

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
