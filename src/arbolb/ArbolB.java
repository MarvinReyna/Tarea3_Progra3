/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package arbolb;

/**
 *
 * @author gio10
 */
class ArbolB {
    private Nodo root;
    private int t; // grado mínimo

    ArbolB(int t) {
        this.t = t;
        root = null;
    }

    void insert(int key) {
    if (root == null) {
        root = new Nodo(t, true);
        root.keys.add(key);
    } else {
        if (root.keys.size() == (2 * t) - 1) {
            Nodo newRoot = new Nodo(t, false);
            newRoot.children.add(root);
            splitChild(newRoot, 0, root);
            int i = 0;
            if (newRoot.keys.get(0) < key)
                i++;
            insertNonFull(newRoot.children.get(i), key);
            root = newRoot;
        } else
            insertNonFull(root, key);
    }
}


    void insertNonFull(Nodo node, int key) {
    int i = node.keys.size() - 1;
    if (node.leaf) {
        while (i >= 0 && node.keys.get(i) > key) {
            i--;
        }
        node.keys.add(i + 1, key);
    } else {
        while (i >= 0 && node.keys.get(i) > key)
            i--;
        i++;
        if (i < node.children.size() && node.children.get(i).keys.size() == (2 * t) - 1) {
            splitChild(node, i, node.children.get(i));
            if (node.keys.get(i) < key)
                i++;
        }
        if (i < 0) { // La clave es menor que todas las claves en el nodo
            i = 0;
        }
        insertNonFull(node.children.get(i), key);
    }
}






    void splitChild(Nodo parent, int i, Nodo child) {
        Nodo newNode = new Nodo(child.t, child.leaf);
        for (int j = 0; j < t - 1; j++)
            newNode.keys.add(child.keys.remove(t));
        if (!child.leaf) {
            for (int j = 0; j < t; j++)
                newNode.children.add(child.children.remove(t));
        }
        parent.keys.add(i, child.keys.remove(t - 1));
        parent.children.add(i + 1, newNode);
    }

    void traverse() {
        if (root != null)
            traverse(root);
    }

    void traverse(Nodo node) {
        int i;
        for (i = 0; i < node.keys.size(); i++) {
            if (!node.leaf)
                traverse(node.children.get(i));
            System.out.print(" " + node.keys.get(i));
        }
        if (!node.leaf)
            traverse(node.children.get(i));
    }

    boolean search(int key) {
        return search(root, key);
    }

    boolean search(Nodo node, int key) {
        int i = 0;
        while (i < node.keys.size() && key > node.keys.get(i))
            i++;
        if (i < node.keys.size() && key == node.keys.get(i))
            return true;
        if (node.leaf)
            return false;
        else
            return search(node.children.get(i), key);
    }

    void delete(int key) {
        if (root == null)
            return;
        delete(root, key);
        if (root.keys.size() == 0) {
            if (root.leaf)
                root = null;
            else
                root = root.children.get(0);
        }
    }

    void delete(Nodo node, int key) {
        int i = 0;
        while (i < node.keys.size() && key > node.keys.get(i))
            i++;

        if (i < node.keys.size() && key == node.keys.get(i)) {
            System.out.println("Eliminando clave " + key);
            if (node.leaf)
                node.keys.remove(i);
            else {  
                int k = node.keys.get(i);
                if (node.children.get(i).keys.size() >= t) {
                    int predecessor = getPredecessor(node, i);
                    node.keys.set(i, predecessor);
                    delete(node.children.get(i), predecessor);
                } else if (node.children.get(i + 1).keys.size() >= t) {
                    int successor = getSuccessor(node, i);
                    node.keys.set(i, successor);
                    delete(node.children.get(i + 1), successor);
                } else {
                    merge(node, i);
                    delete(node.children.get(i), k);
                }
            }
        } else {
            if (node.leaf)
                return;
            boolean flag = (i == node.keys.size());
            if (node.children.get(i).keys.size() < t)
                fill(node, i);
            if (flag && i > node.keys.size())
                delete(node.children.get(i - 1), key);
            else
                delete(node.children.get(i), key);
        }
    }

    int getPredecessor(Nodo node, int i) {
        Nodo current = node.children.get(i);
        while (!current.leaf)
            current = current.children.get(current.keys.size());
        return current.keys.get(current.keys.size() - 1);
    }

    int getSuccessor(Nodo node, int i) {
        Nodo current = node.children.get(i + 1);
        while (!current.leaf)
            current = current.children.get(0);
        return current.keys.get(0);
    }

    void fill(Nodo node, int i) {
        if (i != 0 && node.children.get(i - 1).keys.size() >= t)
            borrowFromPrev(node, i);
        else if (i != node.keys.size() && node.children.get(i + 1).keys.size() >= t)
            borrowFromNext(node, i);
        else {
            if (i != node.keys.size())
                merge(node, i);
            else
                merge(node, i - 1);
        }
    }

    void borrowFromPrev(Nodo node, int i) {
        Nodo child = node.children.get(i);
        Nodo sibling = node.children.get(i - 1);

        child.keys.add(0, node.keys.get(i - 1));
        if (!child.leaf) {
            child.children.add(0, sibling.children.remove(sibling.children.size() - 1));
        }

        node.keys.set(i - 1, sibling.keys.remove(sibling.keys.size() - 1));
    }

    void borrowFromNext(Nodo node, int i) {
        Nodo child = node.children.get(i);
        Nodo sibling = node.children.get(i + 1);

        child.keys.add(node.keys.get(i));
        if (!child.leaf) {
            child.children.add(sibling.children.remove(0));
        }

        node.keys.set(i, sibling.keys.remove(0));
    }

    void merge(Nodo node, int i) {
        Nodo child = node.children.get(i);
        Nodo sibling = node.children.get(i + 1);

        child.keys.add(node.keys.get(i));

        for (int j = 0; j < sibling.keys.size(); j++) {
            child.keys.add(sibling.keys.get(j));
        }

        if (!child.leaf) {
            for (int j = 0; j < sibling.children.size(); j++) {
                child.children.add(sibling.children.get(j));
            }
        }

        node.keys.remove(i);
        node.children.remove(i + 1);
    }
}