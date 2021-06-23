package TADs.hash.MyHash;

public class ClosedHashNode<K,V> {

    private K key;
    private V value;
    private boolean isdeleted;

    public ClosedHashNode(K key, V value) {
        this.key = key;
        this.value = value;
        this.isdeleted = false;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public boolean isdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(boolean isdeleted) {
        this.isdeleted = isdeleted;
    }
}
