package BinarySearchGraph.src;

import java.util.Arrays;


public class Minheap {

    private int[] heap;
    private int size;

    private long comparisons;
    private long swaps;

    public Minheap(int capacity) {
        this.heap = new int[Math.max(capacity, 1)];
        this.size = 0;
    }

  

    
    public static Minheap fromArrayBottomUp(int[] arr) {
        Minheap h = new Minheap(arr.length);
        System.arraycopy(arr, 0, h.heap, 0, arr.length);
        h.size = arr.length;
        for (int i = h.size / 2 - 1; i >= 0; i--) {
            h.siftDown(i);
        }
        return h;
    }

    
    public static Minheap fromArrayTopDown(int[] arr) {
        Minheap h = new Minheap(arr.length);
        for (int value : arr) {
            h.insert(value);
        }
        return h;
    }

   
    public void insert(int value) {
        ensureCapacity(size + 1);
        heap[size] = value;
        size++;
        siftUp(size - 1);
    }

    public int findMin() {
        if (size == 0) throw new IllegalStateException("Heap is empty");
        return heap[0];
    }

    public int extractMin() {
        if (size == 0) throw new IllegalStateException("Heap is empty");
        int min = heap[0];
        size--;
        heap[0] = heap[size];
        if (size > 0) siftDown(0);
        return min;
    }

    
    public boolean delete(int value) {
        int idx = search(value);
        if (idx == -1) return false;
        deleteAt(idx);
        return true;
    }

    private void deleteAt(int idx) {
        size--;
        heap[idx] = heap[size];
        if (idx < size) {
            int parent = parent(idx);
            if (idx > 0) comparisons++;
            if (idx > 0 && heap[idx] < heap[parent]) {
                siftUp(idx);
            } else {
                siftDown(idx);
            }
        }
    }

    
    public boolean decreaseKey(int oldValue, int newValue) {
        if (newValue >= oldValue) {
            throw new IllegalArgumentException("newValue must be smaller than oldValue");
        }
        int idx = search(oldValue);
        if (idx == -1) return false;
        heap[idx] = newValue;
        siftUp(idx);
        return true;
    }

    
    public int search(int value) {
        for (int i = 0; i < size; i++) {
            comparisons++;
            if (heap[i] == value) return i;
        }
        return -1;
    }

   
    public static int kthSmallest(int[] arr, int k) {
        if (k < 1 || k > arr.length) {
            throw new IllegalArgumentException("k out of range");
        }
        Minheap h = fromArrayBottomUp(arr);
        int result = 0;
        for (int i = 0; i < k; i++) {
            result = h.extractMin();
        }
        return result;
    }

    

    private void siftUp(int i) {
        while (i > 0) {
            int p = parent(i);
            comparisons++;
            if (heap[i] < heap[p]) {
                swap(i, p);
                i = p;
            } else {
                break;
            }
        }
    }

    private void siftDown(int i) {
        while (true) {
            int l = left(i);
            int r = right(i);
            int smallest = i;
            if (l < size) {
                comparisons++;
                if (heap[l] < heap[smallest]) smallest = l;
            }
            if (r < size) {
                comparisons++;
                if (heap[r] < heap[smallest]) smallest = r;
            }
            if (smallest != i) {
                swap(i, smallest);
                i = smallest;
            } else {
                break;
            }
        }
    }

    private void swap(int i, int j) {
        swaps++;
        int tmp = heap[i];
        heap[i] = heap[j];
        heap[j] = tmp;
    }

    private void ensureCapacity(int min) {
        if (min <= heap.length) return;
        int newCap = Math.max(heap.length * 2, min);
        heap = Arrays.copyOf(heap, newCap);
    }

    private static int parent(int i) { return (i - 1) / 2; }
    private static int left(int i) { return 2 * i + 1; }
    private static int right(int i) { return 2 * i + 2; }

   

    public int size() { return size; }
    public boolean isEmpty() { return size == 0; }

    public int[] toArray() { return Arrays.copyOf(heap, size); }

    public long getComparisons() { return comparisons; }
    public long getSwaps() { return swaps; }
    public void resetCounters() { comparisons = 0; swaps = 0; }

    public void display() {
        System.out.println(Arrays.toString(toArray()));
    }
}
 
