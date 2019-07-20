import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {

        String fileName = args[0];
        File graphFile = new File(fileName);
        BufferedReader br = null;

        int numV = 0;
        String edge = null;

        try {
            br = new BufferedReader(new FileReader(graphFile));
            numV = Integer.parseInt(br.readLine());
            edge = br.readLine();
        } catch(IOException e) {
            System.out.println("File not found");
        }

        Graph g = new Graph(numV);
        String[] Vs = new String[numV];
        int vPos = 0;
        String[] E = edge.split(" ");
        Vs[vPos] = E[0];
        g.neighbors[vPos].add(new Neighbor(E[1], Integer.parseInt(E[2])));
        ArrayList<String> heads = new ArrayList<String>();
        heads.add(E[1]);

        try {
            while((edge = br.readLine()) != null) {
                E = edge.split(" ");
                String tail = E[0];
                String head = E[1];
                int distance = Integer.parseInt(E[2]);

                if(!heads.contains(head))
                    heads.add(head);

                if(tail.equals(Vs[vPos]))
                    g.neighbors[vPos].add(new Neighbor(head, distance));
                else {
                    vPos++;
                    Vs[vPos] = tail;
                    g.neighbors[vPos].add(new Neighbor(head, distance));
                }
            }
            Vs[numV-1] = "B";
        } catch(IOException e) {
            System.out.println("File error");
        }

        ArrayList<String> toAdd = new ArrayList<String>();
        
        for(int i=0; i<heads.size(); i++) {
            String h = heads.get(i);
            boolean found = false;
            for(int j=0; j<numV; j++) {
                if(h.equals(Vs[j]))
                    found = true;
            }
            if(found == false)
                toAdd.add(h);
        }

        if(toAdd.contains("B")) {
            int bPos = toAdd.indexOf("B");
            toAdd.remove(bPos);
        }

        int addPos = numV-2;

        for(int i=0; i<toAdd.size(); i++) {
            Vs[addPos] = toAdd.get(i);
            addPos--;
        }

        HashMap vertexOrder = new HashMap();

        for(int i=0; i<Vs.length; i++)
            vertexOrder.put(Vs[i], i);

        DijkstrasAlgo.dijkstra(g, Vs, vertexOrder);

    }
}