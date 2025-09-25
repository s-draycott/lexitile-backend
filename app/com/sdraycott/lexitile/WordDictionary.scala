package com.sdraycott.lexitile

import scala.io.Source

object WordDictionary {

  //Used refined words to set to have more common word set but used full list to check as any accepted if correct
  //Lazy val ensures it's only called upon when required
  lazy val words: Set[String] = {
    val source = Source.fromResource("words-refined.txt") //reads from resource folder
    val wordSet = source.getLines().map(_.trim.toLowerCase).toSet
    source.close() //close after creating the wordset as no longer required
    wordSet
  }

  lazy val fullWords: Set[String] = {
    val source = Source.fromResource("words.txt")
    val fullWordSet = source.getLines().map(_.trim.toLowerCase).toSet
    source.close()
    fullWordSet
  }

  def isValidWord(word: String): Boolean = fullWords.contains(word.toLowerCase)
}
