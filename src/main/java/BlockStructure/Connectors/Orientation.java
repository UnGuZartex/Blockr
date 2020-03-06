package BlockStructure.Connectors;

public enum Orientation {
    FACING_UP() {
        @Override
        public boolean isOpposite(Orientation other) {
            return other.equals(FACING_DOWN);
        }
    },
    FACING_LEFT() {
        @Override
        public boolean isOpposite(Orientation other) {
            return other.equals(FACING_RIGHT);
        }
    },
    FACING_DOWN() {
        @Override
        public boolean isOpposite(Orientation other) {
            return other.equals(FACING_UP);
        }
    },
    FACING_RIGHT() {
        @Override
        public boolean isOpposite(Orientation other) {
            return other.equals(FACING_LEFT);
        }
    };

    public abstract boolean isOpposite(Orientation other);
}
