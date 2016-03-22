/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huffman;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 *
 * @author nickbijmoer
 */
public class Huffman {

    public static String huffmanWoord = "bananen";

    public static void main(String[] args) {
        // Count the character in a word.
        Map<Character, Integer> countedMap = countCharacters(huffmanWoord);

        //sort the list by value
        Queue<Node> sortedMap = sortByValue(countedMap);

        //build the tree
        Node buildedTree = buildTree(sortedMap);

        //get the codes for the characters
        Map<Character, String> codes = new HashMap<>();
        getCodes(buildedTree, codes, "");
        System.out.println(codes);

        //code the message
        String codedMessage = codeMessage(huffmanWoord, codes);

        //decode the message
        String s = decodeMessage(codedMessage, buildedTree);
        System.out.println(s);

    }

    //step 1 count the characters in the word
    public static Map<Character, Integer> countCharacters(String huffmanWoord) {
        Map<Character, Integer> characterCount = new HashMap<>();

        for (Character c : huffmanWoord.toCharArray()) {
            if (!characterCount.containsKey(c)) {
                characterCount.put(c, 1);
            } else {
                characterCount.put(c, characterCount.get(c) + 1);

            }
        }
        return characterCount;
    }

    public static Queue<Node> sortByValue(Map<Character, Integer> countedCharacters) {

        // step 2 make new priority queue with custom comparator
        Queue<Node> sortList = new PriorityQueue(new Comparator<Node>() {

            @Override
            public int compare(Node o1, Node o2) {
                if (o1.value > o2.value) {
                    return 1;
                } else if (o1.value < o2.value) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });

        //For each Entry in map, make a new Node and put it in the priority queue
        for (Map.Entry<Character, Integer> entry : countedCharacters.entrySet()) {
            sortList.add(new Node(entry.getKey(), entry.getValue()));
        }

        return sortList;

    }

    // step 3 Build the tree
    public static Node buildTree(Queue<Node> sortedMap) {
        //while sortedmap has more than 1 Node
        while (sortedMap.size() > 1) {
            // remove right and left node from the Queue and retrieve the nodes
            Node left = sortedMap.remove();
            Node right = sortedMap.remove();

            // add a new node with the nodes that have been removed and add them as left and right node
            sortedMap.add(new Node('\0', left.value + right.value, left, right));
        }
        //get the node
        Node nodeTree = sortedMap.remove();

        return nodeTree;

    }

    // step 4 code the tree    
    public static void getCodes(Node node, Map<Character, String> code, String root) {
        if (node.leftChild == null && node.rightChild == null) {
            code.put(node.character, root);
            return;
        }

        getCodes(node.leftChild, code, root + "0");
        getCodes(node.rightChild, code, root + "1");
    }

    //step 5 code the message
    public static String codeMessage(String message, Map<Character, String> codes) {
        String output = "";
        for (Character c : message.toCharArray()) {
            output += codes.get(c);

        }
        return output;
    }

    //step 6 decode the message
    public static String decodeMessage(String codedMessage, Node tree) {

        Node currentNode = tree;
        String output = "";

        for (Character c : codedMessage.toCharArray()) {
            if (c == '0') {
                currentNode = currentNode.leftChild;
            }
            if (c == '1') {
                currentNode = currentNode.rightChild;
            }

            //in short.. if node has a character (got error on currentNode.character != null)
            if (currentNode.leftChild == null && currentNode.rightChild == null) {
                output += currentNode.character;
                currentNode = tree;
            }
        }

        return output;

    }

}
