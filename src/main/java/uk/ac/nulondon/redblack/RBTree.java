package uk.ac.nulondon.redblack;

import java.util.function.Consumer;

import static uk.ac.nulondon.redblack.Color.BLACK;
import static uk.ac.nulondon.redblack.Color.RED;

public class RBTree<K extends Comparable<K>, V> {
    TreeNode<K, V> root;

    public void visit(Consumer<? super TreeNode<K, V>> visitor) {
        if (root != null)
            root.visit(visitor);
    }

    private void rotateLeft(TreeNode<K, V> p) {
        if (p != null) {
            TreeNode<K, V> r = p.right;
            p.right = r.left;
            if (r.left != null)
                r.left.parent = p;
            r.parent = p.parent;
            if (p.parent == null)
                root = r;
            else if (p.parent.left == p)
                p.parent.left = r;
            else
                p.parent.right = r;
            r.left = p;
            p.parent = r;
        }
    }

    private void rotateRight(TreeNode<K, V> p) {
        if (p != null) {
            TreeNode<K, V> l = p.left;
            p.left = l.right;
            if (l.right != null) l.right.parent = p;
            l.parent = p.parent;
            if (p.parent == null)
                root = l;
            else if (p.parent.right == p)
                p.parent.right = l;
            else p.parent.left = l;
            l.right = p;
            p.parent = l;
        }
    }

    public void put(K key, V value) {
        TreeNode<K, V> t = root;
        if (t == null) {
            root = new TreeNode<>(key, value);
            return;
        }
        int cmp;
        TreeNode<K, V> parent;
        do {
            parent = t;
            cmp = key.compareTo(t.key);
            if (cmp < 0)
                t = t.left;
            else if (cmp > 0)
                t = t.right;
            else {
                t.value = value;
            }
        } while (t != null);
        addEntry(key, value, parent, cmp < 0);
    }

    private void addEntry(K key, V value, TreeNode<K, V> parent, boolean addToLeft) {
        TreeNode<K, V> e = new TreeNode<>(key, value);
        e.parent = parent;
        if (addToLeft)
            parent.left = e;
        else
            parent.right = e;
        fixAfterInsertion(e);
    }

    private void fixAfterInsertion(TreeNode<K, V> x) {
        x.color = RED;

        while (x != null && x != root && x.parent.color == RED) {
            if (parentOf(x) == leftOf(parentOf(parentOf(x)))) {
                TreeNode<K, V> y = rightOf(parentOf(parentOf(x)));
                if (colorOf(y) == RED) {
                    setColor(parentOf(x), BLACK);
                    setColor(y, BLACK);
                    setColor(parentOf(parentOf(x)), RED);
                    x = parentOf(parentOf(x));
                } else {
                    if (x == rightOf(parentOf(x))) {
                        x = parentOf(x);
                        rotateLeft(x);
                    }
                    setColor(parentOf(x), BLACK);
                    setColor(parentOf(parentOf(x)), RED);
                    rotateRight(parentOf(parentOf(x)));
                }
            } else {
                TreeNode<K, V> y = leftOf(parentOf(parentOf(x)));
                if (colorOf(y) == RED) {
                    setColor(parentOf(x), BLACK);
                    setColor(y, BLACK);
                    setColor(parentOf(parentOf(x)), RED);
                    x = parentOf(parentOf(x));
                } else {
                    if (x == leftOf(parentOf(x))) {
                        x = parentOf(x);
                        rotateRight(x);
                    }
                    setColor(parentOf(x), BLACK);
                    setColor(parentOf(parentOf(x)), RED);
                    rotateLeft(parentOf(parentOf(x)));
                }
            }
        }
        root.color = BLACK;
    }

    private static <K extends Comparable<K>, V> TreeNode<K, V> parentOf(TreeNode<K, V> p) {
        return (p == null ? null : p.parent);
    }

    private static <K extends Comparable<K>, V> TreeNode<K, V> leftOf(TreeNode<K, V> p) {
        return (p == null) ? null : p.left;
    }

    private static <K extends Comparable<K>, V> TreeNode<K, V> rightOf(TreeNode<K, V> p) {
        return (p == null) ? null : p.right;
    }

    private static <K extends Comparable<K>, V> Color colorOf(TreeNode<K, V> p) {
        return (p == null ? BLACK : p.color);
    }

    private static <K extends Comparable<K>, V> void setColor(TreeNode<K, V> p, Color c) {
        if (p != null)
            p.color = c;
    }
}
