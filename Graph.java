import java.util.LinkedList;

public class Graph {

    int numV;
    LinkedList<Neighbor>[] neighbors;

    public Graph(int numV) {
        this.numV = numV;

        this.neighbors = new LinkedList[numV];
        for(int i=0; i<numV; i++)
            neighbors[i] = new LinkedList<Neighbor>();
    }
}