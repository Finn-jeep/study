package snakeGame;


import java.util.LinkedList;

//一条蛇由多个节点组成，使用集合储存节点
public class Snake {
    //蛇的身体
    private LinkedList<Node> body;
    //默认方向向右
    private Dierction direction = Dierction.RIGHT;
    private boolean moving = true;

    public Snake() {
        //初始化蛇身
        initSnake();
    }

    public void initSnake() {
        //创建集合
        body = new LinkedList<>();
        //创建节点
        body.add(new Node(16, 10));
        body.add(new Node(15, 10));
        body.add(new Node(14, 10));
    }

    //移动方法
    //在蛇头添加一个节点，实现移动，蛇尾删除一个节点
    public void move() {
        if (moving) {
            //获取蛇头
            Node head = body.getFirst();
            switch (direction) {
                case UP:
                    body.addFirst(new Node(head.getX(), head.getY() - 1));
                    break;
                case DOWN:
                    body.addFirst(new Node(head.getX(), head.getY() + 1));
                    break;
                case LEFT:
                    body.addFirst(new Node(head.getX() - 1, head.getY()));
                    break;
                case RIGHT:
                    body.addFirst(new Node(head.getX() + 1, head.getY()));
                    break;
            }
            //删除蛇尾
            body.removeLast();
            //判断蛇头是否撞到了边界
            head = body.getFirst();
            if (head.getX() < 1 || head.getX() > 31 || head.getY() < 1 || head.getY() > 21) {
                moving = false;
            }
            //判断蛇头是否撞到了自己的身体
            for (int i = 1; i < body.size(); i++) {
                if (head.equals(body.get(i))) {
                    moving = false;
                    break;
                }
            }
        }
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

    public Dierction getDirection() {
        return direction;
    }

    public void setDirection(Dierction direction) {
        this.direction = direction;
    }
    
    public boolean isMoving() {
        return moving;
    }
    
    public void addNode(Node node) {
        Node head = body.getFirst();
        switch (direction) {
            case UP:
                body.addFirst(new Node(head.getX(), head.getY() - 1));
                break;
            case DOWN:
                body.addFirst(new Node(head.getX(), head.getY() + 1));
                break;
            case LEFT:
                body.addFirst(new Node(head.getX() - 1, head.getY()));
                break;
            case RIGHT:
                body.addFirst(new Node(head.getX() + 1, head.getY()));
                break;
        }
    }
}