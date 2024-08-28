package lab3;

public class BinarySearchTree<T extends Comparable<T>> {
    Node root;

    public BinarySearchTree() {
        root = null;
    }

    public BinarySearchTree(T key) {
        root = new Node(key);
    }

    private class Node {
        T key;
        Node left;
        Node right;

        Node(T key) {
            this.key = key;
            left = null;
            right = null;
        }
    }

    public void inorderTreeWalk() {
        inorderTreeWalk(root);
        System.out.println();
    }

    private void inorderTreeWalk(Node node) {
        if (node != null) {
            inorderTreeWalk(node.left);
            System.out.println(node.key.toString());
            inorderTreeWalk(node.right);
        }
    }

    public void preorderTreeWalk() {
        preorderTreeWalk(root);
        System.out.println();
    }

    private void preorderTreeWalk(Node node) {
        if (node != null) {
            System.out.println(node.key);
            preorderTreeWalk(node.left);
            preorderTreeWalk(node.right);
        }
    }

    public void postorderTreeWalk() {
        postorderTreeWalk(root);
        System.out.println();
    }

    private void postorderTreeWalk(Node node) {
        if (node != null) {
            postorderTreeWalk(node.left);
            postorderTreeWalk(node.right);
            System.out.println(node.key);
        }
    }

    public boolean search(T key) {
        if (key == null) {
            throw new IllegalArgumentException("key is null");
        }
        //return search(root, key) != null;
        return iterativeTreeSearch(root, key) != null;
    }

    private Node search(Node x, T key) {
        if (x == null || key.compareTo(x.key) == 0) {
            return x;
        }
        if (key.compareTo(x.key) < 0) {
            return search(x.left, key);
        } else {
            return search(x.right, key);
        }
    }

    private Node iterativeTreeSearch(Node x, T key) {
        while (x != null && key.compareTo(x.key) != 0) {
            if (key.compareTo(x.key) < 0) {
                x = x.left;
            } else {
                x = x.right;
            }
        }
        return x;
    }

    public T minimum() {
        Node x = root;
        while (x.left != null) {
            x = x.left;
        }
        return x.key;
    }

    public T maximum() {
        Node x = root;
        while (x.right != null) {
            x = x.right;
        }
        return x.key;
    }

    public void insert(T key) throws IllegalArgumentException {
        if (key == null) {
            throw new IllegalArgumentException("can't insert null in tree");
        }
        root = insert(root, key);
    }

    private Node insert(Node node, T key) {

        Node z = new Node(key);
        if (node == null) {
            node = z;
            return node;
        } else if (key.compareTo(node.key) < 0) {
            node.left = insert(node.left, key);
        } else if (key.compareTo(node.key) > 0) {
            node.right = insert(node.right, key);
        }
        return node;
    }

    public void delete(T key) {

        root = delete(root, key);
    }

    private Node delete(Node node, T key) {
        if (node == null) {
            return null;
        }
        if (key.compareTo(node.key) < 0) {
            node.left = delete(node.left, key);
        } else if (key.compareTo(node.key) > 0) {
            node.right = delete(node.right, key);
        } else {
            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            } else {
                Node oneLeftAlwaysRight = node.left;
                while (oneLeftAlwaysRight.right != null) {
                    oneLeftAlwaysRight = oneLeftAlwaysRight.right;
                }
                T tKey = oneLeftAlwaysRight.key;
                delete(root, oneLeftAlwaysRight.key);
                node.key = tKey;
                return node;
            }
        }
        return node;
    }
}
