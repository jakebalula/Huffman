public class HuffmanNode {
  char ch;
  int freq;
  HuffmanNode left, right;

  public HuffmanNode(char ch, int freq){
    this.ch = ch;
    this.freq = freq;
  }

  public HuffmanNode(int freq, HuffmanNode left, HuffmanNode right){
    this.ch = '\0';
    this.freq = freq;
    this.left = left;
    this.right = right;
  }
}
