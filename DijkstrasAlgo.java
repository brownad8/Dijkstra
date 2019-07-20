import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

public class DijkstrasAlgo {

    public static int getVertexPos(Vertex v, HashMap vertexOrder) {
        String lab = v.label;
        Object vPos = vertexOrder.get(lab);
        int pos = Integer.parseInt(vPos.toString());
        return pos;
    }

    public static void dijkstra(Graph g, String[] vertices,  HashMap vertexOrder) {

        int n = g.numV;
        int[] distance = new int[n];
        String[] parent = new String[n];

        //Step one
        for(int i=0; i<n; i++) {
            distance[i] = Integer.MAX_VALUE;
        }

        //Step two
        distance[0] = 0;

        //Step three
        MinHeapV mhv = new MinHeapV(n, vertexOrder);

        //Step four
        Vertex V = new Vertex(vertices[0], 0);
        mhv.insert(V);

        for(int i=1; i<vertices.length; i++) {
            Vertex v = new Vertex(vertices[i], Integer.MAX_VALUE);
            mhv.insert(v);
        }

        //Step five
        while(mhv.isEmpty() == false) {

            Vertex u = mhv.extractMin();
            int vPos = getVertexPos(u, vertexOrder);
            LinkedList LL = g.neighbors[vPos];

            for(int i=0; i<LL.size(); i++) {
                Object nb = LL.get(i);
                String lab = ((Neighbor) nb).head;
                Object nPos = vertexOrder.get(lab);
                int pos = Integer.parseInt(nPos.toString());
                int d = distance[vPos] + ((Neighbor) nb).distance;

                if(distance[pos] > d) {
                    distance[pos] = d;
                    parent[pos] = u.label;
                    mhv.decreaseKey(mhv.heapOrder[pos], distance[pos]);
                }
            }
        }

        int weight = distance[n-1];
        System.out.println(weight);

        Stack<String> path = new Stack<String>();
        String current = parent[n-1];

        while(current != null) {
            path.push(current);
            String label = current;
            Object parentPos = vertexOrder.get(label);
            int pPos = Integer.parseInt(parentPos.toString());
            current = parent[pPos];
        }

        while(path.empty() == false) {
            System.out.print(path.pop() + " ");
        }

        System.out.println("B");
    }
}
