import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class BaseFrame extends JPanel implements KeyListener {
    private GameManager manager;

    @Override
    public void paintComponent(Graphics g){
        if(manager == null)
            return;

        for(Vector2 position : manager.GetGrid()){
            g.setColor(position.getTileColor());
            g.fillRect(position.getX(), position.getY(), position.getWidth(), position.getHeight());
            g.setColor(manager.defaultColor);
        }

        for(Vector2 position : manager.GetFood()){
            g.setColor(position.getTileColor());
            g.fillRect(position.getX(), position.getY(), position.getWidth(), position.getHeight());
            g.setColor(manager.defaultColor);
        }

        for(Vector2 position : manager.GetSnake()){
            g.setColor(position.getTileColor());
            g.fillRect(position.getX(), position.getY(), position.getWidth(), position.getHeight());
            g.setColor(manager.defaultColor);
        }

        g.setColor(Color.black);
        g.drawString("Score: " + manager.score, 7, 15);
        g.drawString("H", manager.GetSnake().get(0).getX(), manager.GetSnake().get(0).getY());
        g.drawString("T", manager.GetSnake().get(manager.GetSnake().size() - 1).getX(), manager.GetSnake().get(manager.GetSnake().size() - 1).getY());
        g.setColor(manager.defaultColor);
    }

    public void keyTyped(KeyEvent event){
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_W && manager.MoveDirection != 2)
            manager.MoveDirection = 1;
        else if(e.getKeyCode() == KeyEvent.VK_S && manager.MoveDirection != 1)
            manager.MoveDirection = 2;
        else if(e.getKeyCode() == KeyEvent.VK_D && manager.MoveDirection !=  0)
            manager.MoveDirection = -1;
        else if(e.getKeyCode() == KeyEvent.VK_A && manager.MoveDirection != -1)
            manager.MoveDirection = 0;
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    private void Start(JFrame frame){
        manager = new GameManager(frame);
        Thread thread = new Thread(() -> {
            do {
                frame.paint(frame.getGraphics());
                manager.Update();
                try {
                    Thread.sleep(150L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            } while (manager.score >= -1);
        });
        thread.start();
    }

    static void Create(){
        BaseFrame base = new BaseFrame();
        JFrame frame = new JFrame("Snake by Dylan Zemlin");
        frame.getContentPane().add(base);
        frame.setSize(500,500);
        frame.setVisible(true);
        frame.setFocusable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.addKeyListener(base);
        base.Start(frame);
    }
}
