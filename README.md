# Franklin-s-Algorithm
Franklin's Algorithm

Franklin's algorithm aims at improving the message complexity of
Chang-Roberts algorithm.
Each active process p repeatedly compares its own id with the id¡¯s of its
nearest active neighbors on both sides.
If such a neighbor has a larger id, then p becomes passive.
This will avoid messages to travel unnecessarily.

Lets take look at Chang-Roberts Algorithm
{
      Consider a directed ring. The idea of the algorithm is that only the message
      with the highest id completes one round in the ring.
        Each initiator sends a message, tagged with its id.
        When p receives q with q < p, it purges the message.
        When p receives q with q > p, it becomes passive, and passes on the
        message.
        When p receives p, it becomes the leader.
      Passive processes (including all non-initiators) pass on messages.
      Worst-case message complexity: O(N2)
      Average-case message complexity: O(N logN)
}

Franklin's Algorithm
{
      Consider an undirected ring.
        Initially, initiators are active, and non-initiators are passive. Each active
      process sends its id to its neighbors on either side.
        Let active process p receive q and r
        if max(q,r) < p, then p sends its id again
        if max(q,r) > p, then p becomes passive
        if max(q,r) = p, then p becomes the leader
      Passive processes pass on incoming messages.
      Worst-case message complexity: O(N logN).
      In each round, at least half of the active processes become passive. So there
      are at most log2 N rounds.
      Each round takes 2N messages.
}
