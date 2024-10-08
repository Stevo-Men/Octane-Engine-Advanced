package BouncingBall;

import Octane.Canvas;

import java.awt.*;
import java.util.Random;

public class Ball {
    public static final int DEFAULT_SPEED = 5;
    private int radius = 25;
    private int speed;
    private int x, y;
    private int dx, dy;
    private Color color;

    public Ball(int radius, int speed, Color color) {
        this.radius = radius;
        this.speed = speed;
        this.color = color;
        initializePosition();
    }

    public Ball(int radius) {
        this(radius, DEFAULT_SPEED, Color.WHITE);
    }

    public void update() {
        x += dx;
        y += dy;
        if (hasTouchVertical()) {
            dy *= -1;
        }
        if (hasTouchHorizontal()) {
            dx *= -1;
        }
    }

    public void draw(Canvas canvas) {

        canvas.drawCircle(x, y, radius * 2,  Color.RED);
    }

    public boolean hasTouchVertical() {
        return y <= radius || y >= 600 - radius;
    }

    public boolean hasTouchHorizontal() {
        return x <= radius || x >= 800 - radius;
    }

    public boolean hasTouched() {
        return hasTouchVertical() || hasTouchHorizontal();
    }

    private void initializePosition() {
        x = randomNumber(radius * 2, 800 - radius * 2);
        y = randomNumber(radius * 2, 600 - radius * 2);
        dx = randomNumber(0, 1) == 0 ? speed : -speed;
        dy = randomNumber(0, 1) == 0 ? speed : -speed;
    }

    private int randomNumber(int min, int max) {
        return (new Random()).nextInt((max - min) + 1) + min;
    }
}
