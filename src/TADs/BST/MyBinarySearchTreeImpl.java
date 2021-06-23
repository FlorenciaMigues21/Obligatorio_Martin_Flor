package TADs.BST;


import TADs.listaEnlazada.ListaEnlazada;
import TADs.queue.MyQueue;
import TADs.queue.QueueConListaEnlazada;

public class MyBinarySearchTreeImpl<K extends Comparable<K>,T> implements MyBinarySearchTree<K,T> {

    private NodeBST<K,T> root;

    private int size = 0;

    public int getSize() {
        return size;
    }

    @Override
    public T find(K key){
    return (this.findRecursivo(key, this.root));
    }

    public T findRecursivo(K key, NodeBST<K,T> subTree){
        T devolucion = null;

        if(subTree != null) { // Cuando llega a nul es que recorrio todos los lugares donde podria estar y no lo encontro
            if (key.compareTo(subTree.getKey()) > 0) { // Significa que mi valor es mayor que el valor de la raiz del nuevo subarbol y quiero que siga buscando a la derecha

                devolucion = findRecursivo(key, subTree.getRight());

            } else if (key.compareTo(subTree.getKey()) < 0) { // Significa que mi valor es menor que el valor de la razi del nuevo subarbol y quiero que siga buscando a la izquierda

                devolucion = findRecursivo(key, subTree.getLeft());

            } else { // Significa que (value.compareTo(subTree.getValue()) == 0) y entonces encontre el valor en mi arbol
                devolucion = subTree.getValue();
            }
        }
        return devolucion;
    }

    @Override
    public void insert(K key, T data) {
        NodeBST<K,T> elementToAdd = new NodeBST<>(key, data);
        root = this.insertRecursivo(elementToAdd, root);
        size++;
    }

    public NodeBST<K,T> insertRecursivo(NodeBST<K,T> elementToAdd, NodeBST<K,T> subTree){

        if (subTree == null) { // Este es el caso donde el arbol no tiene ni raiz
            return elementToAdd;
        } else {
            if (elementToAdd.getKey().compareTo(subTree.getKey()) > 0) { // Este es el caso donde el elemento que quiero agregar es mas grande que la raiz del subarbol, entonces lo quiero agregar a la derecha

                NodeBST<K,T> newRight = insertRecursivo(elementToAdd, subTree.getRight());

                subTree.setRight(newRight);

                return subTree;
            } else if (elementToAdd.getKey().compareTo(subTree.getKey()) < 0) { // Este es el caso en el que el elemento que quiero agregar es mas chic que la raiz del subarbol entonces lo quiero meter en el subarbol izquierdo de la raiz

                NodeBST<K,T> newLeft = insertRecursivo(elementToAdd, subTree.getLeft());

                subTree.setLeft(newLeft);

                return subTree;

            }else{ // En este caso (value.compareTo(subTree.getValue()) == 0) y eso significa que son iguales. La gracia de los arboles es que no haya elementos repetidos
                // No hago nada y retorno null
                // subTree.apperances++;
                size--;
                return subTree;
            }
        }
    }


    @Override
    public void delete(K key) {
        this.deleteRecursivo(key, root);
        size--;
    }

    public NodeBST<K, T>  deleteRecursivo(K key, NodeBST<K,T> subTree){
        NodeBST<K, T> devolucion;
        if (subTree == null){
            devolucion = subTree; // no encontre el elemento devuelvo el arbol igual
            System.out.println("Estoy intenado eliminar un elemento que no esta en el arbol");
            size ++; // Sumo para que cuando reste no me afecte el size
        }
        else if (key.compareTo(subTree.getKey()) > 0) { // key > subtree.getKey()
            NodeBST<K, T> nuevoArbolDerecho = deleteRecursivo(key, subTree.getRight());
            subTree.setRight(nuevoArbolDerecho);
            devolucion = subTree;
        }
        else if (key.compareTo(subTree.getKey()) < 0) { // key < subtree.getKey()
            NodeBST<K, T> nuevoArbolIzq = deleteRecursivo(key, subTree.getLeft());
            subTree.setLeft(nuevoArbolIzq);
            devolucion = subTree;
        }
        else{ // El nodo en el que estoy tiene la key igual que el que me mandaron
            if (subTree.getRight() == null) {
                devolucion = subTree.getLeft();
            }
            else if (subTree.getLeft() == null) {
                devolucion = subTree.getRight();
            }
            else { //Es el que quiero borrar y ademas, tiene los dos hijos
                NodeBST<K, T> mayorSubArbolIzq = encontrarMaximo(subTree.getLeft()); //Candidato
                subTree.setValue(mayorSubArbolIzq.getValue());
                subTree.setKey(mayorSubArbolIzq.getKey());
                subTree.setLeft(deleteRecursivo(mayorSubArbolIzq.getKey(), subTree.getLeft()));
                devolucion = subTree;
            }
        }
        return devolucion;
    }

    public NodeBST<K,T> encontrarMaximo(NodeBST<K,T> nodoIzquierda){
        NodeBST<K,T> devolucion = null;
        if (nodoIzquierda != null){
            if(nodoIzquierda.getRight() != null){
                devolucion = encontrarMaximo(nodoIzquierda.getRight());
            }else{
                devolucion = nodoIzquierda;
            }
        }
        return devolucion;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public int countLeaf() {
        int i = countLeaf(root);
        return i;
    }

    public int countLeaf(NodeBST<K,T> subTree){
        int devolucion = 0;
        if(subTree != null){

            if(subTree.getRight() == null && subTree.getLeft() == null){
                devolucion = 1;

            }else if(subTree.getLeft() != null || subTree.getRight() != null){
                devolucion = countLeaf(subTree.getLeft()) + countLeaf(subTree.getRight());

            }
        }
        return devolucion;
    }

    @Override
    public int sizeRecursivo(){
        int i = sizeRecursivo(root);
        return i;
    }

    public int sizeRecursivo(NodeBST<K,T> subTree){
        int devolucion = 0;
        if(subTree != null){
            if(!(subTree.getRight() == null && subTree.getLeft() == null)){
                devolucion = 1 + sizeRecursivo(subTree.getRight()) + sizeRecursivo(subTree.getLeft());
            }else{
                devolucion = 1;
            }
        }
        return devolucion;
    }

    @Override
    public int countCompleteElements(){
        int devolucion;
         devolucion = countCompleteElementsRecursivo(this.root);
         return devolucion;
    }

    public int countCompleteElementsRecursivo(NodeBST<K,T> subTree){

        int devolucion = 0;
        if(subTree != null){
            if(subTree.getRight() != null && subTree.getLeft() != null){
                devolucion = 1 + countCompleteElementsRecursivo(subTree.getLeft()) + countCompleteElementsRecursivo(subTree.getRight());
            }
            else{
                devolucion = countCompleteElementsRecursivo(subTree.getLeft()) + countCompleteElementsRecursivo(subTree.getRight());
            }
        }
        return devolucion;
    }

    @Override
    public ListaEnlazada<T> inOrder() {
        ListaEnlazada<T> lista = new ListaEnlazada<>();
        inOrderRecursivo(root, lista);
        return lista;
    }

    public void inOrderRecursivo(NodeBST<K,T> subTree, ListaEnlazada<T> lista){
        if(subTree.getLeft() != null){
            inOrderRecursivo(subTree.getLeft(), lista);
        }

        lista.add(subTree.getValue());

        if (subTree.getRight() != null){
            inOrderRecursivo(subTree.getRight(), lista);
        }
    }

    @Override
    public ListaEnlazada<T> preOrder() {
        ListaEnlazada<T> lista = new ListaEnlazada<>();
        preOrderRecursivo(root, lista);
        return lista; // Aca estoy utilizando la facilidad de java de que paso algo que copia la referencia de la memoria entonces estoy manipulando la misma lista
    }

    public void preOrderRecursivo(NodeBST<K,T> subTree, ListaEnlazada<T> lista){


        lista.add(subTree.getValue());

        if(subTree.getLeft() != null){
            preOrderRecursivo(subTree.getLeft(), lista);
        }

        if(subTree.getRight() != null) {
            preOrderRecursivo(subTree.getRight(), lista);
        }

    }

    @Override
    public ListaEnlazada<T> postOrder() {
        ListaEnlazada<T> lista = new ListaEnlazada<>();
        postOrderRecursivo(root, lista);
        return lista;
    }

    public void postOrderRecursivo(NodeBST<K,T> subTree, ListaEnlazada<T> lista){

        if(subTree.getLeft() != null){
            postOrderRecursivo(subTree.getLeft(), lista);
        }

        if(subTree.getRight() != null){
            postOrderRecursivo(subTree.getRight(), lista);
        }

        lista.add(subTree.getValue());
    }

    public ListaEnlazada<T> porNivel() throws Exception {
        ListaEnlazada<T> lista = new ListaEnlazada<>();
        porNivel(root, lista);
        return lista;
    }


    public ListaEnlazada<T> porNivel(NodeBST<K,T> subTree, ListaEnlazada<T> lista) throws Exception {
        MyQueue<NodeBST<K,T>> colaAuxiliar = new QueueConListaEnlazada<>();
        lista.add(root.getValue());
        colaAuxiliar.enqueue(subTree);
        while (colaAuxiliar.getSize() != 0) {
            NodeBST<K,T> temp = colaAuxiliar.dequeue();
            if(temp.getLeft() != null){
                colaAuxiliar.enqueue(temp.getLeft());
                lista.add(temp.getLeft().getValue());
            }
            if(temp.getRight() != null){
                colaAuxiliar.enqueue(temp.getRight());
                lista.add(temp.getRight().getValue());
            }
            }
        return lista;
    }



    public static void main(String[] args) throws Exception {
        MyBinarySearchTreeImpl<Integer, Integer> prueba = new MyBinarySearchTreeImpl<>();
        prueba.insert(4,4);
        prueba.insert(7,7);
        prueba.insert(2,2);
        prueba.insert(99,99);
        prueba.insert(5,5);
        prueba.insert(6,6);
        prueba.insert(9,9);
        prueba.insert(1,1);
        prueba.insert(87,87);
        prueba.insert(0,0);
        prueba.insert(3,3);
        prueba.insert(245,245);
        prueba.insert(65,65);
        prueba.insert(13,13);
        if (prueba.size() == 14){
            System.out.println("La prueba 1 esta correcta");
        }else{
            System.out.println("Hay un problema en el insert");
        }
        prueba.insert(245,245); // No debe dejar insertar elementos iguales
        prueba.insert(65,65);
        prueba.insert(13,13);
        if (prueba.size() == 14){
            System.out.println("La prueba 2 esta correcta");
        }else{
            System.out.println(prueba.size());
            System.out.println("me esta agrenado objetos iguales");
        }

        System.out.println(prueba.find(4)); //Debe darme 4
        System.out.println(prueba.find(99)); //Debe darme 99
        System.out.println(prueba.find(65)); //Debe darme 65
        System.out.println(prueba.find(13)); //Debe darme 13
        System.out.println(prueba.find(277)); // Debe imprimirme null

        System.out.println("Pruebas para countCompleteElements 1");
        System.out.println(prueba.countCompleteElements());
        if(prueba.countCompleteElements() == 4){
            System.out.println("La prueba de countCompleteElements esta bien");
        }else{
            System.out.println("La prueba de countCompleteElements esta mal");
        }

        //Pruebas Delete general
        System.out.println("Pruebas del delete");
        System.out.println(prueba.size());
        prueba.delete(4); // Estoy probando borrar la raiz
        System.out.println(prueba.size());
        prueba.delete(9);
        System.out.println(prueba.size());
        prueba.delete(0);
        System.out.println(prueba.size());
        prueba.delete(245);
        System.out.println(prueba.size());
        prueba.delete(7);
        System.out.println(prueba.size());
        if(prueba.size() == 9){
            System.out.println("El delete parece funcionar bien");
        }else{
            System.out.println("Parece que el delete me esta funcionando mal");
        }

        //Hago un delete de algo que no existe
        System.out.println("Debe imprimir que no hay elementos en el arbol");
        prueba.delete(120101); //Me deberia imprimir que estoy intentando eliminar un objeto que no existe

        System.out.println("Pruebas para countLeaf");
        System.out.println(prueba.countLeaf()); //Deberia imprimir tres

        System.out.println("Pruebas para sizeRecursivo");
        System.out.println(prueba.sizeRecursivo());
        if(prueba.getSize() == prueba.sizeRecursivo()){
            System.out.println("La prueba de size recurivo esta bien");
        }else{
            System.out.println("La prueba de size recurivo esta mal");
        }

        System.out.println("Pruebas para countCompleteElements 2");
        System.out.println(prueba.countCompleteElements());
        if(prueba.countCompleteElements() == 2){
            System.out.println("La prueba de countCompleteElements esta bien");
        }else{
            System.out.println("La prueba de countCompleteElements esta mal");
        }

        System.out.println("Pruebas para la recorrida en preorder");
        System.out.println("El arbol recorrido en preorder es:");
        for (int i = 0; i < prueba.preOrder().getsize(); i++){
            System.out.println(prueba.preOrder().get(i));
        }

        System.out.println("Pruebas para la recorrida en postOrder");
        System.out.println("El arbol recorrido en postOrder es:");
        for (int i = 0; i < prueba.postOrder().getsize(); i++){
            System.out.println(prueba.postOrder().get(i));
        }

        System.out.println("Pruebas para la recorrida en inOrder");
        System.out.println("El arbol recorrido en inOrder es:");
        for (int i = 0; i < prueba.inOrder().getsize(); i++){
            System.out.println(prueba.inOrder().get(i));
        }

        System.out.println("Pruebas para la recorrida en porNivel");
        System.out.println("El arbol recorrido en porNivel es:");
        for (int i = 0; i < prueba.porNivel().getsize(); i++){
            System.out.println(prueba.porNivel().get(i));
        }

    }
}
