package uk.ac.nulondon.redblack;

import java.util.function.Consumer;

import static uk.ac.nulondon.redblack.Color.BLACK;
import static uk.ac.nulondon.redblack.Color.RED;

public class TreeNode<K extends Comparable<K>, V> {
    final K key;
    V value;
    Color color;

    TreeNode<K, V> left;
    TreeNode<K, V> right;

    TreeNode<K, V> parent;

    public TreeNode(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public TreeNode<K, V> get(K key) {
        int compare = this.key.compareTo(key);
        if (compare == 0) {
            return this;
        } else if (compare > 0) {
            return left == null ? null : left.get(key);
        } else {
            return right == null ? null : right.get(key);
        }
    }

    public void visit(Consumer<? super TreeNode<K, V>> visitor) {
        if (left != null)
            left.visit(visitor);
        if (right != null)
            right.visit(visitor);
        visitor.accept(this);
    }
}


