package uet.oop.bomberman.algorithm;

class BfsQueue {
    int front, rear, size;
    int capacity;
    BfsNode array[];

    public int getFront() {
        return front;
    }

    public int getRear() {
        return rear;
    }

    public int getSize() {
        return size;
    }

    public int getCapacity() {
        return capacity;
    }

    public BfsQueue(int capacity) {
        this.capacity = capacity;
        front = this.size = 0;
        rear = -1;
        array = new BfsNode[this.capacity];
    }

    boolean isEmpty() {
        return (this.size == 0);
    }

    void enqueue(BfsNode item) {
        this.rear = (this.rear + 1) ;
        this.array[this.rear] = item;
        this.size = this.size + 1;
    }

    BfsNode dequeue() {
//        if (isEmpty(this)) return Integer.MIN_VALUE;
        BfsNode item = this.array[this.front];
        //System.out.println(item.getX());
        this.front = (this.front + 1);
        this.size = this.size - 1;
        return item;
    }
}
