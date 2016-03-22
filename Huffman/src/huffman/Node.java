/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huffman;

/**
 *
 * @author nickbijmoer
 */
public class Node {
    public char character;
    public int value;
    public Node  leftChild;
    public Node  rightChild;

    public Node(Character character, Integer value)
    {
     this.character = character;
     this.value = value;
    }
    
    public Node(Character character, Integer value, Node leftChild, Node rightChild)
    {
     this.character = character;
     this.value = value;
     this.leftChild = leftChild;
     this.rightChild = rightChild;
    }
}


