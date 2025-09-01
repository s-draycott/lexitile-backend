package com.sdraycott.lexitile

import scala.io.Source

object WordDictionary {

  //Lazy val ensures it's only called upon when required
  lazy val words: Set[String] = {
    val source = Source.fromResource("words-refined.txt") //reads from resource folder
    val wordSet = source.getLines().map(_.trim.toLowerCase).toSet
    source.close() //close after creating the wordset as no longer required
    wordSet
  }

  def isValidWord(word: String): Boolean = words.contains(word.toLowerCase)
}
