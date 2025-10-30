import java.io.*;
import java.util.*;


public class HuffmanCoding {
  static class Node implements Comparable<Node>{
    char ch;
    int freq;
    Node left, right;
    Node(char ch, int freq){
      this.ch = ch;
      this.freq = freq;
    }
    public int compareTo(Node other){
      return this.freq - other.freq;
    }
  }
  static void buildCode(Node root, String code, Map<Character, String> map){
    if (root == null) return;
    if (root.left == null && root.right == null){
      map.put(root.ch, code);
      return;
    }
    buildCode(root.left, code + "0", map);
    buildCode(root.right, code + "1", map);
  }
  public static void main(String[] args) throws IOException{
    File file = new File("hoffman_input.txt");
    BufferedReader br = new BufferedReader(new FileReader(file));

    StringBuilder sb = new StringBuilder();
    String line;
    while ((line = br.readLine()) != null) {
      sb.append(line).append("\n");
    }
    br.close();

    String text = sb.toString();

    Map<Character, Integer> freq = new HashMap<>();
    for (char c : text.toCharArray()) freq.put(c, freq.getOrDefault(c,0) + 1);

    PriorityQueue<Node> pq = new PriorityQueue<>();
    for (var i : freq.entrySet()) pq.add(new Node(i.getKey(), i.getValue()));

    while (pq.size() > 1){
      Node a = pq.poll(), b = pq.poll();
      Node parent = new Node('\0', a.freq + b.freq);
      parent.left = a;
      parent.right = b;
      pq.add(parent);
    }
    Node root = pq.poll();

    Map<Character, String> codes = new HashMap<>();
    buildCode(root, "", codes);

    StringBuilder encoded = new StringBuilder();
    for (char c : text.toCharArray()) encoded.append(codes.get(c));

    StringBuilder decoded = new StringBuilder();
    Node curr = root;
    for (char bit : encoded.toString().toCharArray()){
      curr = (bit == '0') ? curr.left : curr.right;
      if (curr.left == null && curr.right == null){
        decoded.append(curr.ch);
        curr = root;
      }
    }

    int originalBits = text.length() * 8;
    int compressedBits = encoded.length();
    double ratio = (double) originalBits / compressedBits;

    System.out.println("Character Frequency Huffman Code");
    for (var e : codes.entrySet()) {
      System.out.println(e.getKey() + " " + freq.get(e.getKey()) + " " + e.getValue());
    }
    System.out.println("\nEncoded: " + encoded);
    System.out.println("Original Bits: " + originalBits);
    System.out.println("Compressed Bits: " + compressedBits);
    System.out.printf("Compression Ratio: %.2f : 1\n", ratio);
    System.out.println("Decoded Text: " + decoded);
  }
}
