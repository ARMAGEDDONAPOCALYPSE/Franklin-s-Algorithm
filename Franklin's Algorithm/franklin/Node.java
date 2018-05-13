package franklin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JTextArea;
import javax.swing.JToggleButton;

import type.Message_Direction;
import type.Node_Bit;
import type.Node_State;

public class Node {

	// Node INDENTITY not index
	private int id;
	// Active, Passive, Leader
	private Node_State state;
	// Iteration steps
	private Node_Bit bit;
	private Node left_neighbor;
	private Node right_neighbor;
	// This is the node index, not INDENTITY
	private final int node_index;
	private final int number_of_process;
	// INDENTITY range for Node
	private final int identity_range;
	private JTextArea show;
	@SuppressWarnings("unused")
	private JToggleButton tb, tb1;
	private List<Message> messages;
	private int bitMessagesCount = 0;

	//Initially, all nodes are active and their round number bit = T.
	//At the start of a round an active node picks a random identity
	public Node(int nodeIndex, int n_p, int i_range, JTextArea show, JToggleButton tb, JToggleButton tb1) {
		this.identity_range = i_range;
		this.state = Node_State.ACTIVE;
		this.bit = Node_Bit.T;
		this.node_index = nodeIndex;
		this.number_of_process = n_p;
		this.show = show;
		this.tb = tb;
		this.tb1 = tb1;
		generateNewIdentity();
		messages = new ArrayList<Message>();
	}

	// Sending message to neighbors
	public void send_message() {
		show.append("\n\n[Node " + node_index + " - " + " STATE: " + state + "]");
		for (Message message : messages) {
			if (Node_State.PASSIVE.equals(state)) {
				message.set_hop(message.getHop() + 1);
				// Passive Node passes message
				forward_message(message);
			}
		}

		if (Node_State.ACTIVE.equals(state)) {
			// sends the message hid, 1, biti in both directions
			Message message = new Message(id, 1, bit, Message_Direction.LEFT, tb);
			show.append("\nMessages for [Node " + get_node_index() + "] : " + messages + "");

			show.append("\n\n[Node " + node_index + "] sends message \n	" + ">>>" + message + "\n		>>>to [Node "
					+ left_neighbor.get_node_index() + "]");
			left_neighbor.receive_message(message);

			message.set_msg_dir(Message_Direction.RIGHT);

			show.append("\n\n[Node " + node_index + "] sends message \n	" + ">>>" + message + "\n		>>>to [Node "
					+ right_neighbor.get_node_index() + "]");
			right_neighbor.receive_message(message);
		}
	}

	// Compare for coming message
	private void comparator() {
		boolean state_changed = false;
		Iterator<Message> iterator = messages.iterator();
		while (iterator.hasNext()) {
			Message message = iterator.next();

			if (message.getBit().equals(bit)) {
				if (message.get_id() > id) {
					state = Node_State.PASSIVE;
					bit = bit.getOppositeBit();
					state_changed = true;
				}

				iterator.remove();
				bitMessagesCount--;
			} else if (state_changed) {
				if (message.getBit().equals(bit.getOppositeBit())) {
					iterator.remove();
				}
			}
		}
	}
	/*
	Upon receipt of a message <id, hop < n, bit>, an active node
		stores it, and
		waits for a message from the other direction*/

	// Change Node state depending on received message
	public void receive_message(Message message) {
		// if the node is passive forward the message to it's neighbor in the
		// corresponding direction
		
		/*Upon receipt of messages from both sides, an active node
			becames passive, if any of the ids is larger than its own
			otherwise, it starts a new election round with an inverted
			round number and a new identity*/
		
		if (Node_State.PASSIVE.equals(state)) {
			/*Upon receipt of a message hid, hop, biti
				a passive node passes on a message <id, hop + 1, bit>*/
			message.set_hop(message.getHop() + 1);
			show.append("\n	->>>Forward message from [Node " + node_index + "]");
			forward_message(message);
		} else {
			messages.add(message);
			if(tb.isSelected())
				show.append("\n		Status >>> [Node " + node_index + " - (BIT : " + bit + ") (Messages size: "
					+ messages.size() + ")");
			else
				show.append("\n		Status >>> [Node " + node_index + " -  (Message Size : "
					+ messages.size() + ")");
			if (bit.equals(message.getBit())) {
				if (message.getHop() == number_of_process) {
					/*Upon receipt of a message <id, hop = n, bit>
							an active node becomes the leader*/
					state = Node_State.LEADER;
				}

				bitMessagesCount++;
			}
			if(tb.isSelected())
				show.append(" (BIT Messages Count : " + bitMessagesCount + ")]");

			if (bitMessagesCount == 2) {
				comparator();
			}
		}
	}

	// Forward msg to active nodes
	private void forward_message(Message msg) {
		if (Message_Direction.LEFT.equals(msg.getMessageDirection())) {
			left_neighbor.receive_message(msg);
		} else if (Message_Direction.RIGHT.equals(msg.getMessageDirection())) {
			right_neighbor.receive_message(msg);
		}
	}

	// Asyn
	public void generateNewIdentity() {
		id = (int) (Math.random() * identity_range);
		if (tb1.isSelected())
			show.append("\nNew node id: " + id);
	}

	public int get_node_index() {
		return node_index;
	}

	public boolean isActive() {
		return Node_State.ACTIVE.equals(state);
	}

	public void setState(Node_State state) {
		this.state = state;
	}

	public void setNeighbors(Node left_neighbor, Node right_neighbor) {
		this.left_neighbor = left_neighbor;
		this.right_neighbor = right_neighbor;
	}

	public void removeMessages() {
		messages.clear();
	}

	public boolean isLeader() {
		return Node_State.LEADER.equals(state);
	}

	public String get_neighbor_node_index() {
		return "(" + left_neighbor.get_node_index() + ", " + right_neighbor.get_node_index() + ")\n";
	}

	public void changeBit() {
		bit = bit.getOppositeBit();
	}

	@Override
	public String toString() {
		return "[Node " + node_index + "] - [" + "IDENTITY : " + id + ", STATE : " + state + ", BIT : " + bit + ']';
	}
}
