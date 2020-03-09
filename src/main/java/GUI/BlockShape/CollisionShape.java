package GUI.BlockShape;

public abstract class CollisionShape implements CollisionFunctionality, PaintFunctionality {

    public int x;
    public int y;
    protected int collisionDelta;

    public CollisionShape(int x, int y, int collisionDelta) {
        this.x = x;
        this.y = y;
        this.collisionDelta = collisionDelta;
    }
}
