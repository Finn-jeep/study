package snakeGame;

import javax.swing.*;
import java.awt.*;

//创建窗体
public class MainFrame extends JFrame {
    public MainFrame() throws HeadlessException {
        //初始化窗体
        initFrame();
        //初始化游戏面板
        initGamePanel();
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

    //初始化游戏面板
    private void initGamePanel() {
        JPanel panel = new JPanel() {
            //绘制棋盘中的内容
            @Override
            public void paint(Graphics g) {
                //Graphics g可以看作是画笔，提供方法
                //绘制竖线
                for (int i = 0; i <= 32; i++) {
                    g.drawLine(i * 20, 0, i * 20, 440);//通过两个坐标来连接直线
                }
                //绘制横线
                for (int i = 0; i <= 22; i++) {
                    g.drawLine(0, i * 20, 640, i * 20);
                }
                //绘制蛇
                //绘制蛇头
                //绘制食物
            }
        };
        //把面板添加到窗体中
        add(panel);
    }

    public static void main(String[] args) {
        //创建窗体对象
        new MainFrame();

    }
}
