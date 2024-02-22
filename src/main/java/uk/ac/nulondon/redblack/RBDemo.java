package uk.ac.nulondon.redblack;

import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.IntStream;

public class RBDemo {
    public static void main(String[] args) throws IOException, URISyntaxException {
        int N = 12;
        List<Integer> list = new ArrayList<>(IntStream.range(0, N).boxed().toList());
          Collections.shuffle(list);

        RBTree<Integer, Integer> bst = new RBTree<>();
        for (Integer i : list) {
            bst.put(i, i);
        }

        visualize(bst);
    }

    private static void visualize(RBTree<Integer, Integer> bst) throws IOException, URISyntaxException {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        pw.println("digraph G {");
        pw.println("  ordering=\"out\"");
        pw.println("  node[shape=box,style=rounded]");
        HashSet<TreeNode<Integer,Integer>> set = new HashSet<>();
        bst.visit(treeNode -> {
            System.out.println(treeNode.key);
            pw.printf("  N%d[label=\"%d\",style=\"rounded,filled\",color=%s,fontcolor=%s]%n",
                    treeNode.key,
                    treeNode.key,
                    treeNode.color == Color.RED ? "red" : "black",
                    treeNode.color == Color.RED ? "black" : "white");
            if (treeNode.left != null) {
                pw.printf("  N%d->N%d%n", treeNode.key, treeNode.left.key);
                if (treeNode.left.parent !=null){
                    pw.printf("  N%d->N%d[dir=back]%n", treeNode.left.parent.key, treeNode.left.key);
                }
            } else {
                pw.printf("  N%dL[style=invis]%n", treeNode.key);
                pw.printf("  N%d->N%dL[%s]%n", treeNode.key, treeNode.key,
                        treeNode.right == null ? "style=invis" : "");
            }
            if (treeNode.right != null) {
                pw.printf("  N%d->N%d%n", treeNode.key, treeNode.right.key);
                if (treeNode.right.parent !=null){
                    pw.printf("  N%d->N%d[dir=back]%n", treeNode.right.parent.key, treeNode.right.key);
                }
            } else {
                pw.printf("  N%dR[style=invis]%n", treeNode.key);
                pw.printf("  N%d->N%dR[%s]%n", treeNode.key, treeNode.key,
                        treeNode.left == null ? "style=invis" : "");
            }
        });
        pw.println("}");
        String dot = sw.toString();
        show(dot);
    }

    private static void show(String dot) throws IOException, URISyntaxException {
        String encoded = URLEncoder.encode(dot, "UTF8")
                .replaceAll("\\+", "%20");
        Desktop.getDesktop().browse(
                new URI("https://dreampuf.github.io/GraphvizOnline/#"
                        + encoded));
    }
}
