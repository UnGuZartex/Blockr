package System.BlockStructure.Connectors;

public enum Orientation {
    FACING_UP() {
        /**
         * Check if the given Orientation is opposite to FACING_UP.
         *
         * @param other
         *        The other Orientation to compare with.
         *
         * @return True if and only if the given Orientation is FACING_DOWN.
         */
        @Override
        public boolean isOpposite(Orientation other) {
            return other.equals(FACING_DOWN);
        }
    },
    FACING_LEFT() {
        /**
         * Check if the given Orientation is opposite to FACING_LEFT.
         *
         * @param other
         *        The other Orientation to compare with.
         *
         * @return True if and only if the given Orientation is FACING_RIGHT.
         */
        @Override
        public boolean isOpposite(Orientation other) {
            return other.equals(FACING_RIGHT);
        }
    },
    FACING_DOWN() {
        /**
         * Check if the given Orientation is opposite to FACING_DOWN.
         *
         * @param other
         *        The other Orientation to compare with.
         *
         * @return True if and only if the given Orientation is FACING_UP.
         */
        @Override
        public boolean isOpposite(Orientation other) {
            return other.equals(FACING_UP);
        }
    },
    FACING_RIGHT() {
        /**
         * Check if the given Orientation is opposite to FACING_RIGHT.
         *
         * @param other
         *        The other Orientation to compare with.
         *
         * @return True if and only if the given Orientation is FACING_LEFT.
         */
        @Override
        public boolean isOpposite(Orientation other) {
            return other.equals(FACING_LEFT);
        }
    };

    /**
     * Checks whether or not the given Orientations is opposite to
     * this Orientation.
     *
     * @param other
     *        The other Orientation to compare with.
     *
     * @return True if and only if the given Orientation is the
     *         opposite Orientation to this Orientation.
     */
    public abstract boolean isOpposite(Orientation other);
}
