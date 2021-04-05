package lab9;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Stack;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author Your name here
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        /* (K, V) pair stored in this Node. */
        private K key;
        private V value;

        /* Children of this Node. */
        private Node left;
        private Node right;

        private Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /**for test remove*/
    /*public Node searchNode(K key, Node p) {
        if(p == null) {
            return null;
        } else if(p.key.equals(key)) {
            return p;
        } else if(p.key.compareTo(key) < 0){
            return searchNode(key, p.right);
        } else {
            return searchNode(key, p.left);
        }
    }*/
    private Node root;  /* Root node of the tree. */
    private int size; /* The number of key-value pairs in the tree */

    /* Creates an empty BSTMap. */
    public BSTMap() {
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }


    /** Returns the value mapped to by KEY in the subtree rooted in P.
     *  or null if this map contains no mapping for the key.
     */
    private V getHelper(K key, Node p) {

        //throw new UnsupportedOperationException();
        if(p == null) {
            return null;
        } else if(p.key.equals(key)) {
            return p.value;
        } else if(p.key.compareTo(key) < 0){
            return getHelper(key, p.right);
        } else {
            return getHelper(key, p.left);
        }
    }

    /** Returns the value to which the specified key is mapped, or null if this
     *  map contains no mapping for the key.
     */
    @Override
    public V get(K key) {

        //throw new UnsupportedOperationException();
        return getHelper(key, root);
    }

    /** Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping.
      * Or if p is null, it returns a one node BSTMap containing (KEY, VALUE).
     */
    private Node putHelper(K key, V value, Node p) {
        //throw new UnsupportedOperationException();
        if( p == null) {
            p = new Node(key, value);
            size += 1;
        } else if(p.key.equals(key)) {
            p.value = value;
        } else if(p.key.compareTo(key) < 0){
            p.right = putHelper(key, value, p.right);
        } else {
            p.left = putHelper(key, value, p.left);
        }
        return p;
    }

    /** Inserts the key KEY
     *  If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
        //throw new UnsupportedOperationException();
        root = putHelper(key, value, root);

    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        //throw new UnsupportedOperationException();
        return size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    private void helpKeySet(Set<K> x, Node p) {
        if(p == null) {
            return;
        } else {
            x.add(p.key);
            helpKeySet(x, p.left);
            helpKeySet(x, p.right);
        }

    }
    @Override
    public Set<K> keySet() {
        //throw new UnsupportedOperationException();
        Set<K> ans = new HashSet<>();
        helpKeySet(ans, root);
        return ans;

    }

    /**
     * 找到并返回指定非空节点为root的树中最右边的节点的父节点
     */
    private Node findMaxChildNodesFather(Node p) {
        if(p.right == null) {
            return p;
        } else if(p.right.right == null) {
            return p;
        } else {
            return findMaxChildNodesFather(p.right);
        }
    }

    /**
     * 删除节点p（p就是这棵树的根节点）并返回删除后的树的根节点
     */
    private Node helpRemoveRoot(Node p, K key) {
        if(p == null) {
            return null;
        } else if(p.left == null && p.right == null) {
            return null;
        } else if(p.left != null && p.right == null) {
            p = p.left;
        } else if(p.left == null && p.right != null) {
            p = p.right;
        } else {
            Node tempFather = findMaxChildNodesFather(p.left);
            Node temp = findMaxChildNodesFather(p.left).right;

            if(temp == null) {
                Node t = p.right;
                p = p.left;
                p.right = t;
                t = null;
            } else {
                p.key = temp.key;
                p.value = temp.value;
                tempFather.right = temp.left;
            }

        }
        return p;
    }

    /**
     * 删除根节点为r的树中指定key值的节点，并返回根节点
     * @param r
     * @param key
     * @return
     */
    private Node helpRemove(Node r, K key) {
        if(r == null) {
            return null;
        } else if(r.key.equals(key)) {
            return helpRemoveRoot(r, key);
        } else if(r.key.compareTo(key) < 0){
            r.right = helpRemove(r.right, key);
        } else if(r.key.compareTo(key) > 0) {
            r.left = helpRemove(r.left, key);
        }
        return r;
    }

    /** Removes KEY from the tree if present
     *  returns VALUE removed,
     *  null on failed removal.
     */
    @Override
    public V remove(K key) {
        //throw new UnsupportedOperationException();

        V ans = get(key);
        root = helpRemove(root, key);

        if(ans != null) {
            size -= 1;
        }
        return ans;

    }

    /** Removes the key-value entry for the specified key only if it is
     *  currently mapped to the specified value.  Returns the VALUE removed,
     *  null on failed removal.
     **/
    @Override
    public V remove(K key, V value) {
        //throw new UnsupportedOperationException();
        V ans = get(key);
        if(ans.equals(value)) {
            root = helpRemove(root, key);
            size -= 1;
            return ans;
        }
        return null;

    }

    private class BSTIterator<K> implements Iterator<K> {
        Stack<Node> store = new Stack<>();

        public BSTIterator() {
           store.push(root);
        }
        @Override
        public boolean hasNext() {
            return !(store.isEmpty());
        }
        @Override
        public K next() {
            if(store == null || root == null) {
                return null;
            } else {
                Node temp = store.pop();
                if(temp.right != null) {
                    store.push(temp.right);
                }
                if(temp.left != null) {
                    store.push(temp.left);
                }
                return (K)temp.key;
            }
        }
    }
    @Override
    public Iterator<K> iterator() {
        //throw new UnsupportedOperationException();
        Iterator<K> ans = new BSTIterator<K>();
        return ans;

    }
}
