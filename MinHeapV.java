import java.util.HashMap;

public class MinHeapV {

    int numV;
    Vertex[] heap;
    int[] heapOrder;
    HashMap vertexOrder;
    int firstEmpty = 1;

    public MinHeapV(int numV, HashMap vertexOrder) {
        this.numV = numV;
        this.vertexOrder = vertexOrder;
        this.heap = new Vertex[numV+1];
        this.heapOrder = new int[numV];
    }

    public boolean isEmpty() {
        if(firstEmpty == 1)
            return true;
        else
            return false;
    }

    public void insert(Vertex x) throws ArrayIndexOutOfBoundsException {
        int xPos = firstEmpty;
        firstEmpty++;
        String vLabel = x.label;
        Object vPos = vertexOrder.get(vLabel);
        int pos = Integer.parseInt(vPos.toString());

        try {
            heap[xPos] = x;
            heapOrder[pos] = xPos;

            if(xPos > 1) {
                while(xPos > 1) {
                    if(x.distance < heap[xPos/2].distance) {
                        Vertex temp = x;
                        heap[xPos] = heap[xPos/2];

                        String lab = heap[xPos/2].label;
                        Object vertexPos = vertexOrder.get(lab);
                        int Pos = Integer.parseInt(vertexPos.toString());
                        heapOrder[Pos] = xPos;

                        heap[xPos/2] = temp;
                        heapOrder[pos] = xPos/2;
                        xPos = xPos/2;
                    } else {
                        xPos = 1;
                    }
                }
            }

        } catch(ArrayIndexOutOfBoundsException e) {
            System.out.println("Max elements already reached");
            firstEmpty = numV+1;
        }
    }

    public Vertex extractMin() {
        Vertex extracted = null;

        if(firstEmpty <= 1) 
            System.out.println("Nothing to extract");
        else if(firstEmpty == 2) { 
            extracted = heap[firstEmpty-1];

            String lab = heap[firstEmpty-1].label;
            Object vPos = vertexOrder.get(lab);
            int pos = Integer.parseInt(vPos.toString());
            heapOrder[pos] = Integer.MAX_VALUE;

            heap[firstEmpty-1] = null;
            firstEmpty--;
        } else if(firstEmpty == 3) { 
            extracted = heap[1];

            String labD = heap[1].label;
            Object posD = vertexOrder.get(labD);
            int dPos = Integer.parseInt(posD.toString());
            heapOrder[dPos] = Integer.MAX_VALUE;

            heap[1] = heap[firstEmpty-1];
            heap[firstEmpty-1] = null;

            String lab = heap[1].label;
            Object vPos = vertexOrder.get(lab);
            int pos = Integer.parseInt(vPos.toString());
            heapOrder[pos] = 1;

            firstEmpty--;
        } else {
            extracted = heap[1];

            String labD = heap[1].label;
            Object posD = vertexOrder.get(labD);
            int dPos = Integer.parseInt(posD.toString());
            heapOrder[dPos] = Integer.MAX_VALUE;

            heap[1] = heap[firstEmpty-1];
            heap[firstEmpty-1] = null;
            firstEmpty--;
            int xPos = 1;
            boolean done = false;

            while(done == false) {
                if(xPos*2 >= firstEmpty)
                    done = true;
                else if((xPos*2)+1 >= firstEmpty && xPos*2 == firstEmpty-1) {
                    if(heap[xPos].distance > heap[xPos*2].distance) {
                        Vertex temp = heap[xPos];
                        heap[xPos] = heap[xPos*2];
                        heap[xPos*2] = temp;
                        xPos = xPos*2;
                    }
                    done = true;
                } else if((xPos*2)+1 == firstEmpty-1) {
                    int parent = heap[xPos].distance;
                    int leftChild = heap[xPos*2].distance;
                    int rightChild = heap[(xPos*2)+1].distance;
                    if(parent > leftChild && parent > rightChild) {
                        if(leftChild <= rightChild) {
                            Vertex temp = heap[xPos];
                            heap[xPos] = heap[xPos*2];
                            heap[xPos*2] = temp;
                            xPos = xPos*2;
                        } else {
                            Vertex temp = heap[xPos];
                            heap[xPos] = heap[(xPos*2)+1];
                            heap[(xPos*2)+1] = temp;
                            xPos = (xPos*2)+1;
                        }
                    } else if(parent > leftChild && parent <= rightChild) {
                        Vertex temp = heap[xPos];
                        heap[xPos] = heap[xPos*2];
                        heap[xPos*2] = temp;
                        xPos = xPos*2;
                    } else if(parent > rightChild && parent <= leftChild) {
                        Vertex temp = heap[xPos];
                        heap[xPos] = heap[(xPos*2)+1];
                        heap[(xPos*2)+1] = temp;
                        xPos = (xPos*2)+1;
                    }
                    done = true;
                } else {
                    int parent = heap[xPos].distance;
                    int leftChild = heap[xPos*2].distance;
                    int rightChild = heap[(xPos*2)+1].distance;
                    if(parent > leftChild && parent > rightChild) {
                        if(leftChild <= rightChild) {
                            Vertex temp = heap[xPos];
                            heap[xPos] = heap[xPos*2];
                            heap[xPos*2] = temp;
                            xPos = xPos*2;
                        } else {
                            Vertex temp = heap[xPos];
                            heap[xPos] = heap[(xPos*2)+1];
                            heap[(xPos*2)+1] = temp;
                            xPos = (xPos*2)+1;
                        }
                    } else if(parent > leftChild && parent <= rightChild) {
                        Vertex temp = heap[xPos];
                        heap[xPos] = heap[xPos*2];
                        heap[xPos*2] = temp;
                        xPos = xPos*2;
                    } else if(parent > rightChild && parent <= leftChild) {
                        Vertex temp = heap[xPos];
                        heap[xPos] = heap[(xPos*2)+1];
                        heap[(xPos*2)+1] = temp;
                        xPos = (xPos*2)+1;
                    } else if(parent == leftChild && parent == rightChild) {
                        done = true;
                    } else if(parent < leftChild && parent < rightChild) {
                        done = true;
                    }
                }
            }

            for(int i=1; i<firstEmpty; i++) {
                String lab = heap[i].label;
                Object vPos = vertexOrder.get(lab);
                int pos = Integer.parseInt(vPos.toString());
                heapOrder[pos] = i;
            }
        }

        return extracted;
    }

    public void decreaseKey(int heapPos, int decreaseTo) {
        heap[heapPos].distance = decreaseTo;
        int xPos = heapPos;

        if(xPos > 1) {
            boolean done = false;

            while(done == false) {
                int parent = heap[xPos/2].distance;
                if(heap[xPos].distance < parent) {
                    Vertex temp = heap[xPos];
                    heap[xPos] = heap[xPos/2];
                    heap[xPos/2] = temp;

                    String lab = heap[xPos].label;
                    Object vPos = vertexOrder.get(lab);
                    int pos = Integer.parseInt(vPos.toString());
                    heapOrder[pos] = xPos;

                    lab = heap[xPos/2].label;
                    vPos = vertexOrder.get(lab);
                    pos = Integer.parseInt(vPos.toString());
                    heapOrder[pos] = xPos/2;

                    xPos = xPos/2;

                    if(xPos == 1)
                        done = true;
                } else {
                    done = true;
                }
            }
        }
    }

}