package snakeGame;

import java.util.Random;

//节点类，每一个节点就是一个小方块
public class Node {
    private int x;//横坐标
    private int y;//纵坐标

    public Node() {
    }

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Node node = (Node) obj;
        return x == node.x && y == node.y;
    }
    
    @Override
    public int hashCode() {
        return 31 * x + y;
    }
    
    //构造方法用于随机生成一个节点来代表食物
    public void randomNode(){
        Random r =  new Random();
        //随机生成横纵坐标（确保在有效范围内）
        this.x = r.nextInt(30) + 1;  // 范围：1-30
        this.y = r.nextInt(20) + 1;  // 范围：1-20
    }
}