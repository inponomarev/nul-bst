package uk.ac.nulondon.avl;

import java.util.function.Consumer;

public class TreeNode<K extends Comparable<K>, V> {
    final K key;
    V value;
    int bf;

    int height;

    TreeNode<K, V> left;
    TreeNode<K, V> right;

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

    public TreeNode<K, V> insert(TreeNode<K, V> node) {
        int compare = this.key.compareTo(node.key);
        if (compare == 0) {
            //Just updating the value, nothing else happens
            this.value = node.value;
            return this;
        } else if (compare > 0) {
            if (left == null)
                left = node;
            else left = left.insert(node);
        } else {
            if (right == null)
                right = node;
            else right = right.insert(node);
        }
        updateHeight();
        //return this;
        return this.rebalance();
    }


    /**
     * Null-safe height calculation
     */
    private int height(TreeNode<K, V> node) {
        return node == null ? -1 : node.height;
    }

    public void updateHeight() {
        int leftHeight = height(left);
        int rightHeight = height(right);
        height = Math.max(leftHeight, rightHeight) + 1;
        bf = rightHeight - leftHeight;
    }


    TreeNode<K, V> rotateRight() {
        // https://www.baeldung.com/wp-content/uploads/2020/02/R-Large-1.png
        var x = this.left;
        var z = x.right;
        x.right = this;
        this.left = z;
        updateHeight();
        x.updateHeight();
        return x;
    }

    TreeNode<K, V> rotateLeft() {
        // https://www.baeldung.com/wp-content/uploads/2020/02/L-Large-1.png
        var x = this.right;
        var z = x.left;
        x.left = this;
        this.right = z;
        updateHeight();
        x.updateHeight();
        return x;
    }

    //https://www.baeldung.com/java-avl-trees
    TreeNode<K, V> rebalance() {
        TreeNode<K, V> result = this;
        if (bf > 1) {
            if (height(right.right) > height(right.left)) {
                System.out.println("LEFT");
                result = rotateLeft();
            } else {
                System.out.println("RIGHT,LEFT");
                right = right.rotateRight();
                result = rotateLeft();
            }
        } else if (bf < -1) {
            if (height(left.left) > height(left.right)) {
                System.out.println("RIGHT");
                result = rotateRight();
            } else {
                System.out.println("LEFT,RIGHT");
                left = left.rotateLeft();
                result = rotateRight();
            }
        }
        return result;
    }

    public void visit(Consumer<? super TreeNode<K, V>> visitor) {
        if (left != null)
            left.visit(visitor);
        if (right != null)
            right.visit(visitor);
        visitor.accept(this);
    }
}


