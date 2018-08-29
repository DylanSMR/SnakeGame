import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

class GameManager {
    private ArrayList<Vector2> Grid = new ArrayList<>();
    private ArrayList<Vector2> Snake = new ArrayList<>();
    private ArrayList<Vector2> Food = new ArrayList<>();
    private boolean gameOver = false;
    int score = 0;

    int MoveDirection = -1; // -1 >, 0 <, 1 ^, 2 Down
    private JFrame frame;
    Color defaultColor = Color.gray;

    ArrayList<Vector2> GetGrid(){
        return Grid;
    }

    ArrayList<Vector2> GetSnake(){
        return Snake;
    }

    ArrayList<Vector2> GetFood(){
        return Food;
    }

    void Update() {
        if (gameOver) {
            System.exit(0);
            return;
        }

        int headX = Snake.get(0).getX();
        int headY = Snake.get(0).getY();

        switch (MoveDirection) {
            case -1:
                Snake.get(0).setX(headX + 10);
                break;
            case 0:
                Snake.get(0).setX(headX - 10);
                break;
            case 2:
                Snake.get(0).setY(headY + 10);
                break;
            case 1:
                Snake.get(0).setY(headY - 10);
                break;
        }

        for (int tailIndex = 1; tailIndex < Snake.size(); tailIndex++) {
            int storeX = Snake.get(tailIndex).getX();
            int storeY = Snake.get(tailIndex).getY();

            Snake.get(tailIndex).setX(headX);
            Snake.get(tailIndex).setY(headY);

            headX = storeX;
            headY = storeY;
        }

        if (Snake.get(0).getY() > frame.getHeight() || Snake.get(0).getY() < 0 || Snake.get(0).getX() > frame.getWidth() || Snake.get(0).getX() < 0)
        {
            gameOver = true;
            return;
        }

        for(int tailPart = 0; tailPart < Snake.size(); tailPart++)
        {
            if(Snake.get(tailPart).compareTo(Snake.get(0)) && tailPart != 0){
                gameOver = true;
                break;
            }

            ArrayList<Vector2> copyfood = (ArrayList<Vector2>) Food.clone();
            for(Vector2 food : copyfood) {
                if(Snake.get(tailPart).compareTo(food)){
                    Food.remove(food);
                    SpawnFood();
                    score += 5;

                    int x = 0;
                    int y = 0;
                    if(MoveDirection == 1){
                        y = Snake.get(Snake.size() - 1).getY() - 10;
                        x = Snake.get(Snake.size() - 1).getX();
                    }
                    else if(MoveDirection == 2){
                        y = Snake.get(Snake.size() - 1).getY() + 10;
                        x = Snake.get(Snake.size() - 1).getX();
                    }
                    else if(MoveDirection == -1){
                        y = Snake.get(Snake.size() - 1).getY();
                        x = Snake.get(Snake.size() - 1).getX() - 10;
                    }
                    else if(MoveDirection == 0){
                        y = Snake.get(Snake.size() - 1).getY();
                        x = Snake.get(Snake.size() - 1).getX() + 10;
                    }

                    Vector2 newTail = new Vector2(x,y);
                    newTail.setTileColor(Color.red);
                    Snake.add(newTail);
                }
            }
        }
    }

    private void SpawnFood(){
        boolean foodSpawned = false;
        while(!foodSpawned) {
            int x = 10 * ThreadLocalRandom.current().nextInt(0, frame.getWidth() / 10);
            int y = 10 * ThreadLocalRandom.current().nextInt(0, frame.getHeight() / 10);

            boolean isIntertwined = false;
            for (Vector2 part : Snake) {
                if (part.compareTo(x, y))
                    isIntertwined = true;
            }
            if (isIntertwined)
                continue;

            Vector2 food = new Vector2(x, y);
            food.setTileColor(Color.blue);
            Food.add(food);
            foodSpawned = true;
        }
    }

    GameManager(JFrame frame){
        this.frame = frame;

        for(int widthIndex = 0; widthIndex < (frame.getWidth() / 10); widthIndex++){
            for(int heightIndex = 0; heightIndex < (frame.getHeight() / 10); heightIndex++){
                Vector2 position = new Vector2(widthIndex * 10, heightIndex * 10);
                position.setTileColor(Color.gray);
                Grid.add(position);
            }
        }

        for(int snakeIndex = 0; snakeIndex < 5; snakeIndex++){
            Vector2 pos = new Vector2((frame.getWidth() / 2) + (snakeIndex * 10), frame.getHeight() / 2);
            pos.setTileColor(Color.red);
            Snake.add(0, pos);
        }

        for(var i = 0; i < ThreadLocalRandom.current().nextInt(3, 25); i++){
            SpawnFood();
        }
    }
}
