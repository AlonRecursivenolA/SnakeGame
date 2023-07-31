import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {

    private final int B_WIDTH = 300;
    private final int B_HEIGHT = 300;
    private final int DOT_SIZE = 10;
    private final int ALL_DOTS = 900;
    private final int RAND_POS = 29;
    private final int DELAY = 140;

    private final int x[] = new int[ALL_DOTS];
    private final int y[] = new int[ALL_DOTS];

    private int dots;
    private int apple_x;
    private int apple_y;

    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;
    private boolean inGame = true;

    private Timer timer;
    private Image ball;
    private Image apple;


    /**
     * Invoked when an action occurs.
     *
     */

    public Board(){
        initBoard();
    }
    public void initBoard(){
        addKeyListener(new TAdapter());
        setBackground(Color.BLACK);
        setFocusable(true);

        setPreferredSize(new Dimension(B_WIDTH,B_HEIGHT));
        loadImages();
        initGame();
    }
    private void loadImages() {
        ImageIcon snake = new ImageIcon("C:\\Users\\Alon\\IdeaProjects\\Snake\\src\\ball.png");
        ball = snake.getImage();
        ImageIcon apples = new ImageIcon("C:\\Users\\Alon\\IdeaProjects\\Snake\\src\\greenapple.png");
        apple = apples.getImage();
    }
    private void initGame() {
        dots = 3;

        for(int i = 0; i < dots; i++){
            x[i] = 50 - i*10;
            y[i] = 50;
        }
        timer = new Timer(DELAY,this);
        timer.start();

    }

    /**
     * Calls the UI delegate's paint method, if the UI delegate
     * is non-<code>null</code>.  We pass the delegate a copy of the
     * <code>Graphics</code> object to protect the rest of the
     * paint code from irrevocable changes
     * (for example, <code>Graphics.translate</code>).
     * <p>
     * If you override this in a subclass you should not make permanent
     * changes to the passed in <code>Graphics</code>. For example, you
     * should not alter the clip <code>Rectangle</code> or modify the
     * transform. If you need to do these operations you may find it
     * easier to create a new <code>Graphics</code> from the passed in
     * <code>Graphics</code> and manipulate it. Further, if you do not
     * invoke super's implementation you must honor the opaque property, that is
     * if this component is opaque, you must completely fill in the background
     * in an opaque color. If you do not honor the opaque property you
     * will likely see visual artifacts.
     * <p>
     * The passed in <code>Graphics</code> object might
     * have a transform other than the identify transform
     * installed on it.  In this case, you might get
     * unexpected results if you cumulatively apply
     * another transform.
     *
     * @param g the <code>Graphics</code> object to protect
     * @see #paint
     * @see
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);
    }

    private void doDrawing(Graphics g) {

        if(inGame){
            g.drawImage(apple,apple_x,apple_y,this);
            for(int i = 0; i < dots; i++){
                g.drawImage(ball,x[i],y[i],this);
            }

        }
        else{
            gameOver(g);
        }
    }

    private void gameOver(Graphics g) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        checkCollusion();
        move2();

        repaint();
    }

    private void move2() {

        for(int i = dots; i>0; i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        if(upDirection){
            y[0] -= DOT_SIZE;
        }
        if(downDirection){
            y[0] += DOT_SIZE;
        }
        if(rightDirection){
            x[0] += DOT_SIZE;
        }
        if(leftDirection){
            x[0] -= DOT_SIZE;
        }
    }

    private void checkCollusion() {
        for(int i = 1; i<dots; i++){
            if(x[0] == x[i] && y[0] == y[i]){
                    inGame = false;
            }
        }
        if(x[0] > B_WIDTH || x[0] > B_HEIGHT || x[0] < 0){
            inGame = false;
        }
        if(x[0] == apple_x && y[0] == apple_y){
            dots++;
            locationApple();
        }

    }

    private void locationApple() {
        int r = (int) (Math.random() * RAND_POS);
        apple_x = ((r * DOT_SIZE));

        r = (int) (Math.random() * RAND_POS);
        apple_y = ((r * DOT_SIZE));
    }




    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if ((key == KeyEvent.VK_LEFT) && (!rightDirection)) {
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if ((key == KeyEvent.VK_RIGHT) && (!leftDirection)) {
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if ((key == KeyEvent.VK_UP) && (!downDirection)) {
                upDirection = true;
                rightDirection = false;
                leftDirection = false;
            }

            if ((key == KeyEvent.VK_DOWN) && (!upDirection)) {
                downDirection = true;
                rightDirection = false;
                leftDirection = false;
            }
        }
    }
}