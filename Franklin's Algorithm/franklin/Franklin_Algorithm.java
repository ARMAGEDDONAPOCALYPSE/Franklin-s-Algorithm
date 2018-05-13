package franklin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;

public class Franklin_Algorithm {
	/**
	 * Holds the number of nodes/processes
	 */
	private final int n_p;

	/**
	 * Holds the maximum identity number.
	 */
	private final int i_r;

	public Franklin_Algorithm(int n_p, int i_r) {
		this.n_p = n_p;
		this.i_r = i_r;
	}

	public void sys_p(long x, JTextArea show) throws InterruptedException {
		for (int i = 0; i < 10; i++) {
			show.append(". ");
			TimeUnit.SECONDS.sleep(x);
			x -= 0.1;
		}
		show.append("\n");
	}

	/**
	 * Simulates the leader election algorithm for the given nodes with maximum
	 * identity.
	 * 
	 * @throws InterruptedException
	 */
	@SuppressWarnings("unused")
	public void simulate(JTextArea show, JToggleButton tb, JToggleButton tb1) throws InterruptedException {
		long x = 1;
		int elect = 1;
		show.append("	Franklin's Algorithm Simulation Starts");
		// sys_p(x, show);
		show.append("\n\nNode neighbors: \n");
		// create the nodes queue
		List<Node> nodes = initNodes(show, tb, tb1);

		// set the neighbor of the nodes
		generate_neighbors(nodes);
		
		// sys_p(x, show);

		for (Node node : nodes) {
			show.append("	[Node "+node.get_node_index() + "] - " + node.get_neighbor_node_index());
		}
		//show.append("}\n");
		boolean leaderFound = false;
		
		while (!leaderFound) {
			show.append("\n-------------------------------------Election : " + elect + "-------------------------------------\n");
			// sys_p(x, show);
			show.append("\nNodes: ");
			for (Node node : nodes) {
				show.append("\n" + node.toString());
			}

			// start message sending between neighbors
			for (Node node : nodes) {
				node.send_message();
			}

			List<Node> activeNodes = new ArrayList<Node>();
			for (Node node : nodes) {
				// add active nodes in a separate list
				if (node.isActive()) {
					activeNodes.add(node);
				}

				// change the iteration of the node
				node.changeBit();
				if (node.isLeader()) {
					show.append("\n\n--------------------------------------------------------------------------");
					show.append("\n	[>>> Node " + node.get_node_index() + " is leader. <<<]\n");
					show.append("--------------------------------------------------------------------------\n\n");
					leaderFound = true;
				}

				if (tb.isSelected())
					node.generateNewIdentity();
				node.removeMessages();
			}

			if (activeNodes.size() == 0 && !leaderFound) {
				show.append("\n\nInvalid solution!");
				break;
			}
			elect++;
		}

	}

	// Initialize node index and identity according to user input
	private List<Node> initNodes(JTextArea show, JToggleButton tb, JToggleButton tb1) {
		List<Node> nodes = new ArrayList<Node>();

		for (int i = 0; i < n_p; i++) {
			nodes.add(new Node(i, n_p, i_r, show, tb, tb1));
		}
		return nodes;
	}

	// Produce 2 neighbors for each node
	private void generate_neighbors(List<Node> nodes) {
		for (int i = 0; i < n_p; i++) {
			Node node = nodes.get(i);

			// left neighbor index
			int left_node_index = (i - 1) % n_p;
			// right neighbor index
			int right_node_index = (i + 1) % n_p;

			// correct the left neighbor index
			if (left_node_index < 0) {
				left_node_index = n_p + left_node_index;
			}

			// correct the right neighbor index
			if (right_node_index > n_p) {
				right_node_index = right_node_index - n_p;
			}

			// set neighbors
			node.setNeighbors(nodes.get(left_node_index), nodes.get(right_node_index));
		}
	}
}
