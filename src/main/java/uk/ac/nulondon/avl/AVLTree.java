package uk.ac.nulondon.avl;

import java.util.function.Consumer;

public class AVLTree<K extends Comparable<K>, V> {
    TreeNode<K, V> root;


    public void put(K key, V value) {
        TreeNode<K, V> newNode = new TreeNode<>(key, value);
        if (root == null) {
            root = newNode;
        } else {
            root = root.insert(newNode);
        }
    }

    public void visit(Consumer<? super TreeNode<K, V>> visitor) {
        if (root != null)
            root.visit(visitor);
    }
}
