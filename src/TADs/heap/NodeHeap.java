package TADs.heap;

public class NodeHeap <K extends Comparable<K>, T> {


    private K key;
    private T value;

    private NodeHeap<K, T> left;

    private NodeHeap<K, T> right;

    public NodeHeap(K key, T value) {
        this.value = value;
        this.left = null;
        this.right = null;
        this.key = key;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public NodeHeap<K, T> getLeft() {
        return left;
    }

    public void setLeft(NodeHeap<K, T> left) {
        this.left = left;
    }

    public NodeHeap<K, T> getRight() {
        return right;
    }

    public void setRight(NodeHeap<K, T> right) {
        this.right = right;
    }
}

