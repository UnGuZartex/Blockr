package System.GameWorld;

/**
 * An enum for the different directions a robot can look towards.
 *
 * @author Alpha-team
 */
public enum Direction {
    UP {
        /**
         * Turns this direction to the left.
         *
         * @return The left direction.
         */
        @Override
        public Direction turnLeft() {
            return Direction.LEFT;
        }

        /**
         * Turns this direction to the right.
         *
         * @return The right direction.
         */
        @Override
        public Direction turnRight() {
            return Direction.RIGHT;
        }
    },
    LEFT {
        /**
         * Turns this direction to the left.
         *
         * @return The down direction.
         */
        @Override
        public Direction turnLeft() {
            return Direction.DOWN;
        }

        /**
         * Turns this direction to the right.
         *
         * @return The up direction.
         */
        @Override
        public Direction turnRight() {
            return Direction.UP;
        }
    },
    DOWN {
        /**
         * Turns this direction to the left.
         *
         * @return The right direction.
         */
        @Override
        public Direction turnLeft() {
            return Direction.RIGHT;
        }

        /**
         * Turns this direction to the right.
         *
         * @return The left direction.
         */
        @Override
        public Direction turnRight() {
            return Direction.LEFT;
        }
    },
    RIGHT {
        /**
         * Turns this direction to the left.
         *
         * @return The up direction.
         */
        @Override
        public Direction turnLeft() {
            return Direction.UP;
        }

        /**
         * Turns this direction to the right.
         *
         * @return The down direction.
         */
        @Override
        public Direction turnRight() {
            return Direction.DOWN;
        }
    };

    /**
     * Turns this direction to the left.
     *
     * @return This direction to the left.
     */
    public abstract Direction turnLeft();

    /**
     * Turns this direction to the right.
     *
     * @return This direction to the right.
     */
    public abstract Direction turnRight();
}
