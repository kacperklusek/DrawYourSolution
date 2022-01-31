package Game;

import org.dyn4j.geometry.Vector2;

import java.io.Serializable;

public class Vector2Serial implements Serializable {
    public final double x;
    public final double y;
    public Vector2Serial(double x, double y){
        this.x = x;
        this.y = y;
    }
    public Vector2Serial(Vector2 v){
        this.x = v.x;
        this.y = v.y;
    }
    public Vector2 toVector2(){
        return new Vector2(x, y);
    }

}
