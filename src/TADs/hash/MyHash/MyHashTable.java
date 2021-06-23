package TADs.hash.MyHash;

public interface MyHashTable <K,V>{

    void put(K key, V value);

    V get(K key);

    boolean contains(K key);

    void remove(K key);

    float getSize();

}
