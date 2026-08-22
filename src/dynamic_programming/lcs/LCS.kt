package dynamic_programming.lcs

import kotlin.math.max

class LCS(private var firstText: String,private var secondText: String) {
    private var lcsArray:Array<IntArray>
    private var solution: StringBuilder = StringBuilder()
    init {
        firstText = " $firstText"
        secondText = " $secondText"
        lcsArray =
            Array(firstText.length) { IntArray(secondText.length) }
        initialization()
        fillMatrix()
        getSolution()
        println(solution.reverse().toString())
    }
    private fun initialization(){
        for(row in lcsArray.indices) {
            for(col in secondText.indices) {
                lcsArray[row][col] = 0
            }
        }
    }
    private fun fillMatrix() {
        for (rowIndex in 1..<firstText.length) {
            for (columnIndex in 1..<secondText.length) {
                val currentFirstCharacter = firstText[rowIndex]
                val currentSecondCharacter = secondText[columnIndex]

                if (currentFirstCharacter == currentSecondCharacter) {
                    val upperLeftValue = lcsArray[rowIndex - 1][columnIndex - 1]
                    lcsArray[rowIndex][columnIndex] = upperLeftValue + 1
                }else{
                    val upperValue = lcsArray[rowIndex - 1][columnIndex]
                    val leftValue = lcsArray[rowIndex][columnIndex - 1]
                    lcsArray[rowIndex][columnIndex] = max(upperValue, leftValue)
                }
            }
        }
    }
    fun printMatrix() {
        for(row in lcsArray.indices) {
            for(col in secondText.indices) {
                print("${lcsArray[row][col]} ")
            }
            println()
        }
    }
    private fun getSolution(){
        var firstTextIndex = firstText.lastIndex
        var secondTextIndex = secondText.lastIndex
        while(firstTextIndex != 0 && secondTextIndex != 0){
            if (lcsArray[firstTextIndex][secondTextIndex] > lcsArray[firstTextIndex][secondTextIndex-1]){
                if (lcsArray[firstTextIndex][secondTextIndex] == lcsArray[firstTextIndex-1][secondTextIndex])
                    firstTextIndex--
                else{
                    solution.append(firstText[firstTextIndex])
                    firstTextIndex--
                    secondTextIndex--
                }
            }else{
                secondTextIndex--
            }
        }
    }
    fun getFinalSolutionLcs(): String = solution.reverse().toString()
}
