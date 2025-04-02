package doing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
// lưu các đường ống
import java.util.Random;
import java.util.ArrayList; 



public class FlappyBird extends JPanel implements ActionListener, KeyListener {
    
    int boardWidth = 360;
    int boardHeight = 640;
    
    //Images
    Image backgroundImage;
    Image birdImage;
    Image toppipImage;
    Image bottompipeImage;


    // Bird
    class Bird {
        int x;
        int y;
        int width = 34;
        int height = 24;

        Image image;

        Bird(Image image) {
            this.image = image;
            resetSetting();
        }

        void resetSetting() {
            x = boardWidth / 8;
            y = boardHeight / 8;
        }
    }

    // pipes

    class Pipe {
        int x = boardWidth;
        int y = 0;
        int width = 64;
        int height = 512;

        Image image;
        boolean isTheBirdPass = false;

        Pipe(Image image) {
            this.image = image;
        }
    }


    //game logic
    Bird bird;
    int velocityX = -4; // move pipe to left
    int velocityY = 0; // vận tốc của bird
    int gravity = 1; // trọng lực

    ArrayList<Pipe> pipes;
    Random random = new Random();

    Timer gameLoop; 
    Timer placePipesTimer;
    boolean isGameOver = false;
    double score = 0;

    FlappyBird() {
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        // setBackground(Color.BLUE);
        // lớp này sẽ xử lý sự kiện phím
        setFocusable(true);
        addKeyListener(this);

        // load image
        backgroundImage = new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage();
        birdImage = new ImageIcon(getClass().getResource("./flappybird.png")).getImage();
        toppipImage = new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
        bottompipeImage = new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();
        
        //bird
        bird = new Bird(birdImage);

        // pipes
        pipes = new ArrayList<Pipe>();

        // placepipes timer
        placePipesTimer = new Timer(1200, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placePipes();
            }
        });
        placePipesTimer.start();
   
        // Game timer cài đặt 60 frame / giây
        gameLoop = new Timer(1000/60, this);
        gameLoop.start();
    }

    // khởi tạo các đường ống
    public void placePipes() {
        // (0 - 1) * pipeHeight/2 -> (0-256)
        //128
        //0 - 128 - (0-256) ---> 1/4 pipeHeight  ---> 3/4 pipeHeight
        Pipe topPipe = new Pipe(toppipImage);

        int randomPipeY = (int)(topPipe.y - topPipe.height/4 - Math.random() * (topPipe.height/2));
        topPipe.y += randomPipeY;
        pipes.add(topPipe);

        int spaceBetweenPipes = boardHeight / 4;

        Pipe bottomPipe = new Pipe(bottompipeImage);
        bottomPipe.y += topPipe.y + topPipe.height + spaceBetweenPipes;
        pipes.add(bottomPipe);     
    }

    // là phương thức của JPanel dùng để vẽ hình ảnh của game
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    // vẽ hình ảnh lên màn hình
    public void draw(Graphics g){
        // System.out.println("draw");
        // vẽ background
        g.drawImage(backgroundImage, 0, 0, boardWidth, boardHeight, null);
        
        // vẽ bird
        g.drawImage(birdImage, bird.x, bird.y, bird.width, bird.height, null);

        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            g.drawImage(pipe.image, pipe.x, pipe.y, pipe.width, pipe.height, null);
        }

        // socre
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        if (isGameOver) {
            String gameOverText = "Game over!!! Your score: " + String.valueOf((int) score);
            String restartText = "Press Enter or Space to restart";
            int restartTextWidth = g.getFontMetrics().stringWidth(restartText);
            int textWidth = g.getFontMetrics().stringWidth(gameOverText);
            int textHeight = g.getFontMetrics().getHeight();
            g.drawString(gameOverText, (boardWidth - textWidth) / 2, (boardHeight - textHeight) / 2);
            g.drawString(restartText, (boardWidth - restartTextWidth) / 2, (boardHeight - textHeight) / 2 + 30);
        } else {
            g.drawString("Score: " + String.valueOf((int) score), 10, 20);
        }

    }

    public void move() {
        // bird
        velocityY += gravity;
        bird.y += velocityY;
        bird.y = Math.max(0, bird.y);

        // pipes
        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            pipe.x += velocityX;

            if (!pipe.isTheBirdPass && pipe.x + pipe.width < bird.x) {  // bird đã qua pipe
                pipe.isTheBirdPass = true;
                score += 0.5;
            }

            if (collision(bird, pipe)) {
                isGameOver = true;
            }
        }

        if (bird.y + bird.height >= boardHeight || bird.y == 0) {
            isGameOver = true;
        }
    }

    public boolean collision(Bird a, Pipe b) {
        return a.x < b.x + b.width && // vị trí bird trái trên nhỏ hơn vị trí pipe phải dưới
                a.x + a.width > b.x && // vị trí bird phải trên lớn hơn vị trí pipe trái dưới
                a.y < b.y + b.height && // vị trí bird trên nhỏ hơn vị trí pipe dưới
                a.y + a.height > b.y;    // vị trí bird dưới lớn hơn vị trí pipe trên
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if (isGameOver) {
            gameLoop.stop();
            placePipesTimer.stop();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_ENTER) {
            velocityY = -9;
            if (isGameOver) {
                // restart the game by restting all the values
                bird.resetSetting();
                pipes.clear();
                isGameOver = false;
                velocityX = -4;
                velocityY = 0; 
                gravity = 1; 
                score = 0;
                gameLoop.start();
                placePipesTimer.start();

            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
