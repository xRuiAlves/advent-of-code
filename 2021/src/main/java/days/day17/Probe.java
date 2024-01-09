package days.day17;

public class Probe {
    private int x;
    private int y;
    private int xSpeed;
    private int ySpeed;
    private int highestY;

    public Probe(int xSpeed, int ySpeed) {
        this.x = 0;
        this.y = 0;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.highestY = 0;
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    public int highestY() {
        return highestY;
    }

    public void update() {
        x += xSpeed;
        y += ySpeed;
        xSpeed -= sign(xSpeed);
        ySpeed -= 1;
        highestY = Math.max(highestY, y);
    }

    public boolean pastSquare(Square square) {
        return y < square.y2();
    }

    public boolean inSquare(Square square) {
        return x >= square.x1()
            && x <= square.x2()
            && y >= square.y2()
            && y <= square.y1();
    }

    private static int sign(int num) {
        if (num == 0) {
            return 0;
        }
        return num > 0 ? 1 : -1;
    }

    public boolean simulate(Square target) {
        if (xSpeed == 0 && ySpeed == 0) {
            return false;
        }

        while (true) {
            if (inSquare(target)) {
                return true;
            }
            if (pastSquare(target)) {
                return false;
            }
            update();
        }
    }
}
