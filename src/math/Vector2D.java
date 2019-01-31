package math;

public class Vector2D {

    public int x = 0, y = 0;

    public Vector2D(){

    }

    public Vector2D(int x){
        this.x = x;
    }

    public Vector2D(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Vector2D add(){
        return add(0, 0);
    }

    public Vector2D add(int x){
        return add(x, 0);
    }

    public Vector2D add(int x, int y){
        return new Vector2D(this.x += x, this.y += y);
    }

    public Vector2D multiply(){
        return multiply(0);
    }

    public Vector2D multiply(int x){
        return multiply(x, 0, false);
    }

    public Vector2D multiply(int x, int y, boolean notzero){
        if(notzero)
            if(x == 0)
                x = 1;
            else if (y == 0)
                y = 1;
        return new Vector2D(this.x * x, this.y * y);
    }

    public String toString(){
        return x + ":" + y;
    }
}
