package GUI.Blocks;

import Controllers.CallListener;
import GUI.Components.GUIConnector;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GUIProcedureBlock extends GUICavityBlock {

    private static List<Boolean> takenProcedureNumbers = new ArrayList<>();
    private static int currentPriority = Integer.MAX_VALUE;
    private final List<CallListener> listeners = new ArrayList<>();
    private int procedureNr;

    /**
     * TODO
     */
    public GUIProcedureBlock(int x, int y, int procedureNr) {
        super("Def " + procedureNr, x, y);
        this.procedureNr = procedureNr;
        priority = currentPriority--;
    }

    public GUIProcedureBlock(String name, int x, int y) {
        super(name, x, y);
        priority = currentPriority--;
    }

    @Override
    protected void setConnectors() {
        cavityConnector = new GUIConnector(this, (width + DEFAULT_CAVITY_WIDTH) / 2, cavityUpHeight, Color.red);
        subConnectors.add(cavityConnector);
    }

    @Override
    public GUIBlock clone() {
        return new GUIProcedureBlock(x, y, getNewProcedureNumber());
    }

    @Override
    public void terminate() {
        super.terminate();
        takenProcedureNumbers.set(procedureNr - 1, false);

        if (procedureNr == takenProcedureNumbers.size()) {
            takenProcedureNumbers.remove(takenProcedureNumbers.size() - 1);
        }
    }

    public void unsubscribe(CallListener listener) {
        listeners.remove(listener);
    }

    public void subscribe(CallListener listener) {
        listeners.add(listener);
    }

    private int getNewProcedureNumber() {
        int currentIndex = -1;

        for (int i = 0; i < takenProcedureNumbers.size(); i++) {
            if (!takenProcedureNumbers.get(i)) {
                takenProcedureNumbers.set(i, true);
                currentIndex = i;
                break;
            }
        }

        if (currentIndex == -1) {
            takenProcedureNumbers.add(true);
            return takenProcedureNumbers.size();
        }

        return currentIndex + 1;
    }
}
