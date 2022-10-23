package uet.oop.bomberman.algorithm;

public class DisjointSet {
    private int parent[];
    private int nodeCount;

    DisjointSet() {
        parent = new int[11 * 29];
        nodeCount = 11 * 29;
        for (int i = 0; i < nodeCount; i++) {
            parent[i] = i;
        }
    }

    public int anc(int p) {
        if (parent[p] == p) return p;
        else return parent[p] = anc(parent[p]);
    }

    void join(int p, int q) {
        if (anc(p) != anc(q)) {
            parent[anc(p)] = anc(q);
            nodeCount--;
        }
    }

    public int getNodeCount() {
        return nodeCount;
    }
}
