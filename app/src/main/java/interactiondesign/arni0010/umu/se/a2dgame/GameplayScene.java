package interactiondesign.arni0010.umu.se.a2dgame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;

import static interactiondesign.arni0010.umu.se.a2dgame.MainThread.canvas;

/**
 * Draws the game to the Canvas.
 */
public class GameplayScene implements Scene{

    private Rect r = new Rect();

    private Paint paintGameOver = new Paint();

    private RectPlayer player;
    private Point playerPoint;
    private ObstacleManager obstacleManager;

    private boolean movingPlayer = false;

    private boolean gameOver = Constants.GAMEOVER;
    private long gameOverTime;

    private OrientationData orientationData;
    private long frameTime;
    private float speed;

    /**
     * Creates a player as a RectPlayer and a point on which the player always will start. The
     * constructor also initializes the ObstacleManager and the OrientationData.
     */
    public GameplayScene(){

        player = new RectPlayer(new Rect(100, 100, 200, 200));
        playerPoint = new Point(Constants.SCREEN_WIDTH/2, 3*Constants.SCREEN_HEIGHT/4);
        player.update(playerPoint);

        speed = 0;

        initializeObstacleManager();

        orientationData = new OrientationData();
        orientationData.register();
        frameTime = System.currentTimeMillis();
    }

    /**
     * Resets the player starting position to the same spot as usual and then re-initializes the
     * ObstacleManager to re-create and re-draw the game.
     */
    private void reset(){

        playerPoint = new Point(Constants.SCREEN_WIDTH/2, 3*Constants.SCREEN_HEIGHT/4);
        player.update(playerPoint);

        initializeObstacleManager();

        movingPlayer = false;
    }

    /**
     * At its current state this method only sets the current Scene to this Scene again. Its real
     * mission would be to terminate the this Scene and change it back to a starting Scene. But
     * since this is the starting Scene and the only Scene available this doesn't do much at the
     * moment. A method available for future updates of the game.
     */
    @Override
    public void terminate() {
        SceneManager.ACTIVE_SCENE = 0;
    }

    /**
     * This method handles the event of touch on the screen while the game is running. but the only
     * time to method does something of importance is when it's Game Over for the player. The
     * game then resets by the player touching the display. That's when this method resets the
     * OrientationData and resets the whole game to start from the beginning again.
     * @param event The MotionEvent.
     */
    @Override
    public void recieveTouch(MotionEvent event) {

        switch (event.getAction()){

            case MotionEvent.ACTION_DOWN:

                // Add: if(&& System.currentTimeMillis() - gameOverTime >= 200), if game start delay
                // is needed.
                if(gameOver){

                    reset();
                    gameOver = false;
                    Constants.GAMEOVER = false;
                    orientationData.newGame();
                    orientationData.register();
                }
                break;
        }
    }

    /**
     * This method draws the game to the Canvas. It draws the RectPlayer and the ObstacleManager
     * which handles both the the player, obstacles and the movement of the obstacles. If it's
     * Game Over for the player it draws two Paints with texts that tells the highscore and
     * "GameOver".
     * @param canvas The canvas on which everything is drawn.
     */
    @Override
    public void draw(Canvas canvas) {

        canvas.drawColor(Color.DKGRAY);

        player.draw(canvas);
        obstacleManager.draw(canvas);

        if(Constants.GAMEOVER){

            Paint paintHighscore = new Paint();
            paintHighscore.setTextSize(100);
            paintHighscore.setColor(Color.CYAN);
            drawHighscoreText(canvas, paintHighscore, "Highscore: " + Constants.HIGHSCORE);

            Paint paintGameOver = new Paint();
            paintGameOver.setTextSize(100);
            paintGameOver.setColor(Color.MAGENTA);
            drawGameOverText(canvas, paintGameOver, "Game Over");
        }
    }

    /**
     * Keeps track of the elapsed time of each game session. By doing so it can update the
     * players position and the ObstacleManager. It also checks constantly if the player has
     * collided with an Obstacle. If the player has collided the Sensor Listener in
     * OrientationData is paused to maximize performance.
     */
    @Override
    public void update() {

        if(!gameOver) {

            if(frameTime < Constants.INIT_TIME)
                frameTime = Constants.INIT_TIME;

            int elapsedTime = (int) (System.currentTimeMillis() - frameTime);
            frameTime = System.currentTimeMillis();

            if(orientationData.getOrientation() != null && orientationData.getStartOrientation()
                    != null) {

                float pitch = orientationData.getOrientation()[1] - orientationData
                        .getStartOrientation()[1];
                float roll = orientationData.getOrientation()[2] - orientationData
                        .getStartOrientation()[2];

                float xSpeed = 2 * roll * Constants.SCREEN_WIDTH/speed;
                float ySpeed = pitch * Constants.SCREEN_HEIGHT/speed;

                playerPoint.x += Math.abs(xSpeed * elapsedTime) > 5 ? xSpeed * elapsedTime : 0;
                playerPoint.y -= Math.abs(ySpeed * elapsedTime) > 5 ? ySpeed * elapsedTime : 0;
            }

            if(playerPoint.x < 0)
                playerPoint.x = 0;
            else if(playerPoint.x > Constants.SCREEN_WIDTH)
                playerPoint.x = Constants.SCREEN_WIDTH;
            if(playerPoint.y < 0)
                playerPoint.y = 0;
            else if(playerPoint.y > Constants.SCREEN_HEIGHT)
                playerPoint.y = Constants.SCREEN_HEIGHT;


            player.update(playerPoint);
            obstacleManager.update();

            if(obstacleManager.playerCollide(player)){

                gameOver = true;
                gameOverTime = System.currentTimeMillis();
                Constants.GAMEOVER = true;
                orientationData.pause();
            }
        }
    }

    /**
     * Aligns the text to the center of the layout and then changes float y to set the text a
     * bit higher up the screen.
     * @param canvas The canvas on which everything is drawn.
     * @param paint the Paint.
     * @param text The text which is about to be drawn.
     */
    private void drawHighscoreText(Canvas canvas, Paint paint, String text){

        paint.setTextAlign(Paint.Align.LEFT);
        canvas.getClipBounds(r);
        float cHeight = r.height();
        int cWidth = r.width();
        paint.getTextBounds(text, 0, text.length(), r);
        float x = cWidth / 2f - r.width() / 2f - r.left;
        float y = cHeight / 2f + r.height() / 2f - r.bottom;

        //This makes the text go higher up the screen.
        y -= 300;
        canvas.drawText(text, x, y, paint);
    }

    /**
     * Aligns the text to the center of the layout.
     * @param canvas The canvas on which everything is drawn.
     * @param paint the Paint.
     * @param text The text which is about to be drawn.
     */
    private void drawGameOverText(Canvas canvas, Paint paint, String text) {

        paint.setTextAlign(Paint.Align.LEFT);
        canvas.getClipBounds(r);
        int cHeight = r.height();
        int cWidth = r.width();
        paint.getTextBounds(text, 0, text.length(), r);
        float x = cWidth / 2f - r.width() / 2f - r.left;
        float y = cHeight / 2f + r.height() / 2f - r.bottom;
        canvas.drawText(text, x, y, paint);
    }

    /**
     * Initializes the OnstacleManager differently depending on chosen difficulty. It changes the
     * player gap (the gap in which the player is moving through the obstacles), the obstacle
     * gap (the gap between each row of obstacles), the color of the obstacles and the
     * acceleration of the speed both for the obstacles and the RectPlayer.
     */
    private void initializeObstacleManager(){

        switch(Constants.DIFFICULTY){
            case 0:
                obstacleManager = new ObstacleManager(400, 600, 75, Color.BLACK, 10000.0f);
                speed = 1000f;
                break;
            case 1:
                obstacleManager = new ObstacleManager(300, 400, 75, Color.GREEN, 8000.0f);
                speed = 1000f;
                break;
            case 2:
                obstacleManager = new ObstacleManager(250, 400, 75, Color.YELLOW, 6000.0f);
                speed = 1000f;
                break;
            case 3:
                obstacleManager = new ObstacleManager(200, 400, 75, Color.RED, 5000.0f);
                speed = 800f;
                break;
            case 4:
                obstacleManager = new ObstacleManager(200, 400, 75, Color.WHITE, 3000.0f);
                speed = 600f;
                break;
        }
    }
}
