package snakeGame;

import java.util.LinkedList;

//一条蛇由多个节点组成，使用集合储存节点
public class Snake {
    //蛇的身体
    private LinkedList<Node> body;

    public Snake() {
        //初始化蛇身
        initSnake();
    }
    public void initSnake() {
        //创建集合
        body = new LinkedList<>();
        //创建节点
        body.add(new Node(10,16));
        body.add(new Node(10,15));
        body.add(new Node(10,14));
    }

    public Snake(LinkedList<Node> body) {
        this.body = body;
    }

    public LinkedList<Node> getBody() {
        return body;
    }

    public void setBody(LinkedList<Node> body) {
        this.body = body;
    }
}
