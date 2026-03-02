package snakeGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

//创建窗体
public class MainFrame extends JFrame {
    private Snake snake;
    private JPanel panel;
    private Timer timer;//定时器，在规定的时间调用方法
    private Node food;
    private int score = 0; // 得分
    private long startTime; // 游戏开始时间
    private int interval = 200; // 初始间隔时间
    private boolean gameRunning = true; // 游戏运行状态
    private int foodGenerationAttempts = 0; // 食物生成尝试次数

    public MainFrame() throws HeadlessException {
        //初始化窗体
        initFrame();
        //初始化游戏面板
        initGamePanel();
        //初始化蛇
        initSnake();
        //初始化食物
        initFood();
        //记录游戏开始时间
        startTime = System.currentTimeMillis();
        //初始化定时器
        initTimer();
        //键盘录入，控制蛇移动
        setKeyListener();
    }

    private void initFood() {
        food = new Node();
        //随机生成食物
        generateFood();
    }
    
    private void generateFood() {
        foodGenerationAttempts = 0;
        do {
            food.randomNode();
            foodGenerationAttempts++;
            // 如果连续5次都无法生成有效食物，说明地图已满
            if (foodGenerationAttempts > 5) {
                gameRunning = false;
                timer.cancel();
                
                // 显示通关对话框，提供重新开始选项
                Object[] options = {"重新开始", "退出"};
                int choice = JOptionPane.showOptionDialog(this, 
                    "恭喜通关，地图已经占满！\n最终得分：" + score + "\n游戏时长：" + getGameTime() + "秒", 
                    "恭喜通关", 
                    JOptionPane.YES_NO_OPTION, 
                    JOptionPane.INFORMATION_MESSAGE, 
                    null, options, options[0]);
                
                if (choice == JOptionPane.YES_OPTION) {
                    // 重新开始游戏
                    restartGame();
                } else {
                    // 退出游戏
                    System.exit(0);
                }
                return;
            }
        } while (isFoodOnSnake()); // 确保食物不在蛇身上
    }
    
    private boolean isFoodOnSnake() {
        for (Node node : snake.getBody()) {
            if (food.equals(node)) {
                return true;
            }
        }
        return false;
    }

    //设置键盘录入监听
    private void setKeyListener() {
        addKeyListener(new KeyAdapter() {
            //键盘按下自动调用此方法
            @Override
            public void keyPressed(KeyEvent e) {
                //键盘中的每一个键都有一个键值
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        //改变方向
                        //做一个判断，防止蛇向反方向移动
                        if (snake.getDirection() != Dierction.DOWN) {
                            snake.setDirection(Dierction.UP);
                        }
                        break;
                    case KeyEvent.VK_DOWN:
                        if (snake.getDirection() != Dierction.UP) {
                            snake.setDirection(Dierction.DOWN);
                        }
                        break;
                    case KeyEvent.VK_LEFT:
                        if (snake.getDirection() != Dierction.RIGHT) {
                            snake.setDirection(Dierction.LEFT);
                        }
                        break;
                    case KeyEvent.VK_RIGHT:
                        if (snake.getDirection() != Dierction.LEFT) {
                            snake.setDirection(Dierction.RIGHT);
                        }
                        break;
                }
            }
        });
    }


    //初始化窗体参数
    private void initFrame() {
        //设置标题
        setTitle("贪吃蛇游戏");
        //设置窗体的大小
        setSize(640, 440);//为边缘的渲染留下位置
        //设置窗体的位置，指的是到屏幕左上角的距离
        setLocation(400, 400);
        //设置窗体的关闭方式
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //设置窗体可见
        setVisible(true);
        //设置窗体大小不可变
        setResizable(false);
    }

    //初始化定时器
    private void initTimer() {
        timer = new Timer();
        //初始化定时器，在规定的时间调用方法
        TimerTask task = new TimerTask() {

            @Override
            public void run() {
                if (!gameRunning) return;
                
                snake.move();
                
                // 检查游戏是否结束（撞墙或撞自己）
                if (!snake.isMoving()) {
                    gameRunning = false;
                    timer.cancel();
                    
                    // 显示游戏结束对话框，提供重新开始选项
                    Object[] options = {"重新开始", "退出"};
                    int choice = JOptionPane.showOptionDialog(MainFrame.this, 
                        "游戏结束！\n最终得分：" + score + "\n游戏时长：" + getGameTime() + "秒", 
                        "游戏结束", 
                        JOptionPane.YES_NO_OPTION, 
                        JOptionPane.INFORMATION_MESSAGE, 
                        null, options, options[0]);
                    
                    if (choice == JOptionPane.YES_OPTION) {
                        // 重新开始游戏
                        restartGame();
                    } else {
                        // 退出游戏
                        System.exit(0);
                    }
                    return;
                }
                
                //判断是否吃到食物
                if (snake.getBody().getFirst().equals(food)) {
                    //吃到食物，增加蛇的长度
                    snake.addNode(food);
                    //增加得分
                    score += 10;
                    //减少间隔时间（加速），最低100ms
                    interval = Math.max(100, interval - 10);
                    //重新设置定时器
                    timer.cancel();
                    initTimer();
                    //重新生成食物
                    generateFood();
                }
                //刷新界面
                panel.repaint();
            }
        };
        timer.scheduleAtFixedRate(task, 0, interval);
    }
    
    private String getGameTime() {
        long elapsed = (System.currentTimeMillis() - startTime) / 1000;
        return String.valueOf(elapsed);
    }

    //初始化游戏面板
    private void initGamePanel() {
        panel = new JPanel() {
            //绘制棋盘中的内容
            @Override
            public void paint(Graphics g) {
                g.clearRect(0, 0, getWidth(), getHeight());


                //Graphics g可以看作是画笔，提供方法
                //绘制竖线
                for (int i = 0; i <= 32; i++) {
                    g.drawLine(i * 20, 0, i * 20, 440);//通过两个坐标来连接直线
                }
                //绘制横线
                for (int i = 0; i <= 22; i++) {
                    g.drawLine(0, i * 20, 640, i * 20);
                }
                //先绘制墙壁（红色），确保在最底层
                g.setColor(Color.RED);
                //绘制上边界墙壁
                g.fillRect(0, 0, 640, 20);
                //绘制下边界墙壁（修复坐标：窗口高度440，下边界从420开始，高度20）
                g.fillRect(0, 420, 640, 20);
                //绘制左边界墙壁
                g.fillRect(0, 0, 20, 440);
                //绘制右边界墙壁
                g.fillRect(620, 0, 20, 440);
                
                //绘制蛇（黑色）
                g.setColor(Color.BLACK);
                LinkedList<Node> body = snake.getBody();
                for (Node node : body) {
                    g.fillRect(node.getX() * 20, node.getY() * 20, 20, 20);
                }
                
                //绘制食物（橙色）
                g.setColor(Color.ORANGE);
                g.fillRect(food.getX() * 20, food.getY() * 20, 20, 20);
                
                //恢复默认颜色
                g.setColor(Color.BLACK);
                
                //显示得分和游戏时间
                g.setColor(Color.BLUE);
                g.setFont(new Font("Arial", Font.BOLD, 16));
                g.drawString("得分: " + score, 10, 20);
                g.drawString("时间: " + getGameTime() + "秒", 100, 20);
                g.setColor(Color.BLACK);
            }
        };
        //把面板添加到窗体中
        add(panel);
    }

    private void initSnake() {
        snake = new Snake();
    }
    
    // 重新开始游戏方法
    private void restartGame() {
        // 重置所有游戏状态
        gameRunning = true;
        score = 0;
        interval = 200;
        
        // 重新初始化蛇
        snake = new Snake();
        
        // 重新生成食物
        generateFood();
        
        // 重新记录开始时间
        startTime = System.currentTimeMillis();
        
        // 重新启动定时器
        if (timer != null) {
            timer.cancel();
        }
        initTimer();
        
        // 刷新界面
        panel.repaint();
        
        // 重新获取焦点，确保键盘监听有效
        requestFocus();
    }

    public static void main(String[] args) {
        //创建窗体对象
        new MainFrame();
    }
}