package TADs.BST;

import TADs.listaEnlazada.ListaEnlazada;

public interface MyBinarySearchTree <K extends Comparable<K>,T>{
    T find(K key);
    void insert(K key, T Data);
    void delete(K key);
    int size();
    int countLeaf();
    int sizeRecursivo();
    int countCompleteElements();
    ListaEnlazada<T> inOrder();
    ListaEnlazada<T> preOrder();
    ListaEnlazada<T> postOrder();
    ListaEnlazada<T> porNivel() throws Exception;

}
