package System.BlockStructure.Connectors;

/**
 * An enum for the different connector types.
 *
 * @author Alpha-team
 */
public enum Type {
    PLUG {
        /**
         * Checks whether a plug can connect with the given type.
         *
         * @param otherType The type to check.
         *
         * @return True if and only if the given type is a socket.
         */
        @Override
        public boolean canConnectWith(Type otherType) {
            return otherType == SOCKET;
        }
    },
    SOCKET {
        /**
         * Checks whether a socket can connect with the given type.
         *
         * @param otherType The type to check.
         *
         * @return True if and only if the given type is a plug.
         */
        @Override
        public boolean canConnectWith(Type otherType) {
            return otherType == PLUG;
        }
    };

    /**
     * Checks whether this type can connect with the given
     * connector type.
     *
     * @param otherType The type to check.
     *
     * @return True if and only if the given type can connect with
     *         this type.
     */
    public abstract boolean canConnectWith(Type otherType);
}
