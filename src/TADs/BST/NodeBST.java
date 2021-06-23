package TADs.BST;

public class NodeBST<K extends Comparable<K>, T> {

    private K key;
    private T value;

    private NodeBST<K, T> left;

    private NodeBST<K, T> right;

    public NodeBST(K key, T value) {
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

    public NodeBST<K, T> getLeft() {
        return left;
    }

    public void setLeft(NodeBST<K, T> left) {
        this.left = left;
    }

    public NodeBST<K, T> getRight() {
        return right;
    }

    public void setRight(NodeBST<K, T> right) {
        this.right = right;
    }
}

