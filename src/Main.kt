import dynamic_programming.knapsack.Knapsack

fun main() {
   val knapsack  = Knapsack(
      weights = mutableListOf(1,3,5,4),
      profits = mutableListOf(4,9,12,11),
      maxWeight = 8
   )
   knapsack.printSolution()
}