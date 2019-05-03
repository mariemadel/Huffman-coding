/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms;

/**
 *
 * @author Mariem Adel
 */
public class Node implements Comparable<Node> {

    private int freq;
    private int value;
    private Node leftChild;
    private Node RightChild;

    public Node(int freq, int value, Node leftChild, Node RightChild) {
        this.freq = freq;
        this.value = value;
        this.leftChild = leftChild;
        this.RightChild = RightChild;
    }

    boolean isLeaf() {
        return this.leftChild == null && this.RightChild == null;
    }

    public int getFreq() {
        return freq;
    }

    public int getValue() {
        return value;
    }

    public Node getLeftChild() {
        return leftChild;
    }

    public Node getRightChild() {
        return RightChild;
    }

    @Override
    public int compareTo(Node t) {
        int temp = Integer.compare(this.freq, t.freq);
        if (temp != 0) {
            return temp;
        }
        return (Integer.compare(this.value, t.value));
    }

}
