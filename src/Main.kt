import huffman.Huffman

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
   val text = "BEEP POB"
   val huffman = Huffman(text)
   println(huffman.frequency)
   println(huffman.codes)
   println(huffman.encodedText)
}