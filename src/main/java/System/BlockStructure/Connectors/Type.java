package System.BlockStructure.Connectors;

public enum Type {
    PLUG {
        @Override
        public boolean canConnectWith(Type otherType) {
            return otherType == SOCKET;
        }
    },
    SOCKET {
        @Override
        public boolean canConnectWith(Type otherType) {
            return otherType == PLUG;
        }
    };

    public abstract boolean canConnectWith(Type otherType);
}
