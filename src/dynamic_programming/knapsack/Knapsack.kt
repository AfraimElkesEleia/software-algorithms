package dynamic_programming.knapsack

import kotlin.math.max

class Knapsack(val profits : MutableList<Int>,val weights : MutableList<Int>,val maxWeight: Int) {
    private val matrixDp : Array<IntArray>
    private val solution = mutableListOf<Int>()
    init {
        if (profits.size != weights.size) {
            throw Exception("Weights List and Profits List are not equal")
        }
        profits.add(0,0)
        weights.add(0,0)
        matrixDp = Array(weights.size){
            IntArray(maxWeight + 1){0}
        }
        fillMatrix()
        getSolution()
    }
    private fun fillMatrix() {
        for (itemNumber in 1..matrixDp.lastIndex) {
            val itemWeight = weights[itemNumber]
            val itemProfit = profits[itemNumber]

            for (capacity in 1..maxWeight) {
                val profitWithoutItem = matrixDp[itemNumber - 1][capacity]
                matrixDp[itemNumber][capacity] = if (itemWeight <= capacity) {
                    val remainingCapacity = capacity - itemWeight
                    val profitWithItem = itemProfit + matrixDp[itemNumber - 1][remainingCapacity]

                    max(profitWithoutItem, profitWithItem)
                } else {
                    profitWithoutItem
                }
            }
        }
    }
    private fun getSolution() {
        var currentItemIndex = weights.lastIndex
        var currentCapacityIndex = maxWeight
        var remainingCapacity = maxWeight
        while (remainingCapacity>0 && currentItemIndex > 0){
            val currentItem = matrixDp[currentItemIndex][currentCapacityIndex]
            val topCellValue = matrixDp[currentItemIndex-1][currentCapacityIndex]
            if (currentItem > topCellValue){
                solution.add(currentItemIndex)
                remainingCapacity -= weights[currentItemIndex]
                currentItemIndex--
                currentCapacityIndex = remainingCapacity
            }else{
                currentItemIndex--
            }
        }
        solution.reverse()
    }
    fun printSolution() {
        println("Selected items:")
        solution.forEach { itemNumber ->
            println("  Item $itemNumber: weight=${weights[itemNumber]}, profit=${profits[itemNumber]}")
        }
        println("Maximum profit: ${matrixDp.last()[maxWeight]}")
    }
}
