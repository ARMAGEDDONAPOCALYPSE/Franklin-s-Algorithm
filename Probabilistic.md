Assumptions:

    • An anonymous, undirected ring

    • Asynchronous communication

    • Message order is not preserved between any pair of nodes

    • Reliable channels

    • Fair scheduler for message queues

    • Sent message will eventually be processed at its destination.

    • All nodes know the ring size n.
  
  

1. Each node is either active, passive or leader

  An active node maintains two parameters:

    ~ identity, not necessarily unique

    ~ the number of the current election round (modulo 2)

  All messages are

    ~ of the form hid, hop, biti

    ~ id is the originator identity

    ~ bit is the election round of the owner (modulo 2)

    ~ hop . n is hop counter, used to detect identity clashes

    ~ travelling in both directions

  Passive nodes simply pass on messages

    ~ increasing hop counter by one
  
  2. Initially

    ~all nodes are active and their round number bit = T

    ~At the start of a round an active node picks a random identity and sends the message (id, 1, bit) in both directions

  3. Upon receipt of a message (id, hop < n, bit), an active node
  
    ~ stores it, and
    
    ~ waits for a message from the other direction
    
  4. Upon receipt of messages from both sides, an active node

    ~ becames passive, if any of the ids is larger than its own

    ~ otherwise, it starts a new election round with an inverted round number and a new identity

  5. Upon receipt of a message (id, hop, bit)

    ~ a passive node passes on a message hid, hop + 1, biti

  6. Upon receipt of a message (id, hop = n, bit)

    ~ an active node becomes the leader
