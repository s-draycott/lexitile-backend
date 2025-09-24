package com.sdraycott.lexitile

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import scala.util.Random

class Puzzle {
  val dictionary = WordDictionary.words //loads the refined word dictionary

  //sets empty square
  var emptyRow: Int = 3
  var emptyCol: Int = 3
  var puzzle: Array[Array[Char]] = generatePuzzle

  //Date coder to ensure same words each day
  private def dayCode: Int = {
    val today = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
    val dateStr = today.format(formatter)
    dateStr.toInt
  }

  //GENERATE THE PUZZLE
  def generatePuzzle: Array[Array[Char]] = {
    //sets a random number from the date so as long as the dates the same the random number will be the same
    val randomFromDate = new Random(dayCode)
    val fourLetterWords = dictionary.filter(_.length == 4).toSeq
    val threeLetterWords = dictionary.filter(_.length == 3).toSeq

    //GETS RANDOM WORDS AND TURNS INTO ROWS OF CHAR ARRAYS AND APPENDS A SPACE TO 3 LETTER WORD
    val rowsFourLetter = randomFromDate.shuffle(fourLetterWords).take(3).map(_.toUpperCase.toCharArray).toArray
    val lastRow = randomFromDate.shuffle(threeLetterWords).head.toUpperCase.toCharArray :+ ' '
    val puzzle = rowsFourLetter :+ lastRow
    puzzle
  }

  //LOGIC FOR SOLVING PUZZLE
  def isSolved = {
    puzzle.map(_.mkString("").trim).sorted.mkString(" ").trim.toLowerCase.split(" ").toList.
      forall(word => if (dictionary.contains(word)) true else false)
  }

  //LOGIC FOR PRINTING GRID
  def printPuzzle: Unit = {
    puzzle.foreach { row => println(row.mkString(" ")) }
  }

  //LOGIC FOR SLIDING EMPTY SPACE AROUND
  def slideTo(targetRow: Int, targetCol: Int): Boolean = {
    val rowDiff = targetRow - emptyRow
    val colDiff = targetCol - emptyCol

    // Only allow moving tiles that are directly adjacent
    if ((Math.abs(rowDiff) == 1 && colDiff == 0) || (Math.abs(colDiff) == 1 && rowDiff == 0)) {
      val temp = puzzle(targetRow)(targetCol)
      puzzle(targetRow)(targetCol) = puzzle(emptyRow)(emptyCol)
      puzzle(emptyRow)(emptyCol) = temp
      emptyRow = targetRow
      emptyCol = targetCol
      printPuzzle
      true
    } else false
  }

  def shufflePuzzle(moves: Int = 100): Unit = {
    val random = new Random()
    var count = 0

    while (count < moves) {
      val directions = Seq((-1,0), (1, 0), (0, -1), (0, 1))
      val (dr, dc) = directions(random.nextInt(directions.length))

      val newRow = emptyRow + dr
      val newCol = emptyCol + dc

      if(newRow >= 0 && newRow < puzzle.length && newCol >= 0 && newCol < puzzle(0).length) {
        slideTo(newRow, newCol)
        count +=1
      }
    }
  }
}
