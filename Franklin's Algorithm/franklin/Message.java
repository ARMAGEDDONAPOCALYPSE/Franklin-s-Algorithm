package franklin;

import javax.swing.JToggleButton;

import type.Message_Direction;
import type.Node_Bit;

/*All messages are
	of the form <id, hop, bit>
	id is the originator identity
	bit is the election round of the owner (modulo 2)
	hop ¡Ü n is hop counter, used to detect identity clashes
	travelling in both directions*/


public class Message {
	// Node INDENTITY
    private int id;
    // Number of steps
    private int hop;
    // Iteration step
    private Node_Bit bit;
    private Message_Direction msg_dir;
    private JToggleButton tb;

    public Message(int id, int hop, Node_Bit bit, Message_Direction msg_dir, JToggleButton tb) {
        this.id = id;
        this.hop = hop;
        this.bit = bit;
        this.msg_dir = msg_dir;
        this.tb = tb;
    }

    public int get_id() {
        return id;
    }

    public void set_id(int id) {
        this.id = id;
    }

    public int getHop() {
        return hop;
    }

    public void set_hop(int hop) {
        this.hop = hop;
    }

    public Node_Bit getBit() {
        return bit;
    }

    public void set_bit(Node_Bit bit) {
        this.bit = bit;
    }

    public Message_Direction getMessageDirection() {
        return msg_dir;
    }

    public void set_msg_dir(Message_Direction messageDirection) {
        this.msg_dir = messageDirection;
    }

    // Probabilistic algorithms can be used
    // Node can pick random identity
    // Message with a hop counter to detect its source.
    
    @Override
    public String toString() {
    	if(tb.isSelected())
    		return "[Message - " +
                "(IDENTITY : " + id +
                ") (HOP : " + hop +
                ") (BIT : " + bit +
                ")	->" + msg_dir + "<-]";
    	else
    		return "[Message - " +
            "(IDENTITY : " + id +
            ")	->" + msg_dir + "<-]";
    }
}
