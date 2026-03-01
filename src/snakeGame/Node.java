package snakeGame;
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
    //构造方法用于随机生成一个节点来代表食物
    public void randomNode(){
    }
}
