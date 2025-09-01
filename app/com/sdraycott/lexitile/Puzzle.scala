package com.sdraycott.lexitile

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import scala.util.Random

class Puzzle {
  val dictionary = WordDictionary.words
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
    val randomFromDate = new Random(dayCode)
    val fourLetterWords = dictionary.filter(_.length == 4).toSeq
    val threeLetterWords = dictionary.filter(_.length == 3).toSeq

    //GETS RANDOM WORDS AND TURNS INTO ROWS OF CHAR ARRAYS AND APPENDS A SPACE TO 3 LETTER WORD
    val rowsFourLetter = randomFromDate.shuffle(fourLetterWords).take(3).map(_.toCharArray).toArray
    val lastRow = randomFromDate.shuffle(threeLetterWords).head.toCharArray :+ ' '
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
  def slide(direction: String): Boolean = {
    val (targetRow, targetCol) = direction.toLowerCase match {
      case "up"    => (emptyRow - 1, emptyCol)
      case "down"  => (emptyRow + 1, emptyCol)
      case "right" => (emptyRow, emptyCol + 1)
      case "left"  => (emptyRow, emptyCol - 1)
      case _       => (emptyRow, emptyCol)
    }

    if (targetRow >= 0 && targetRow < 4 && targetCol >= 0 && targetCol < 4) {
      val targetPosition = puzzle(targetRow)(targetCol)
      puzzle(targetRow)(targetCol) = puzzle(emptyRow)(emptyCol)
      puzzle(emptyRow)(emptyCol) = targetPosition

      emptyRow = targetRow
      emptyCol = targetCol
      printPuzzle
      true
    } else false

  }
}
