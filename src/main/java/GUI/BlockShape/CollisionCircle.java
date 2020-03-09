package GUI.BlockShape;

import Utility.Position;

public class CollisionCircle extends CollisionShape {

    public int radius;

    public CollisionCircle(int x, int y, int collisionDelta) {
        super(x, y, collisionDelta);
        this.radius = radius;
    }

    @Override
    public boolean contains(int x, int y) {
        Position circlePos = new Position(this.x, this.y);
        Position checkPos = new Position(x, y);
        return circlePos.getDistance(checkPos) <= radius + collisionDelta;
    }
}
