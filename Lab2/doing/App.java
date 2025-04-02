package doing;

import javax.swing.*;
public class App {
	public static void main(String[] args) {   
        int boardWidth = 360;  
        int boardHeight = 640;
        JFrame frame = new JFrame("Flappy Bird");
        // set size of the frame
        frame.setSize(boardWidth, boardHeight);
        // set vị trí đứng giữa
        frame.setLocationRelativeTo(null);
        // set không cho thay đổi kích thước
        frame.setResizable(false);
        // set close operation
 
    
        FlappyBird flappyBird = new FlappyBird();
        // add flappyBird vào frame
        frame.add(flappyBird);
        // pack frame để hiển thị đúng kích thước vì nếu chỉ set size thì có thể bị lệch cả phần thanh tiêu đề
        frame.pack();
        flappyBird.requestFocus();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }
}
