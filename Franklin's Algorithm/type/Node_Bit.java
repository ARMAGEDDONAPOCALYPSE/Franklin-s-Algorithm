package type;
import java.util.HashMap;
import java.util.Map;

public enum Node_Bit {
    T,
    F;

	//bit is the election round of the owner (modulo 2)

    private static Map<Node_Bit, Node_Bit> opposite = new HashMap<Node_Bit, Node_Bit>();

    static {
        opposite.put(F, T);
        opposite.put(T, F);
    }

    public Node_Bit getOppositeBit() {
        return opposite.get(this);
    }
}
