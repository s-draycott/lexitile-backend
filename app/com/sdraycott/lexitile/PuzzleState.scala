package com.sdraycott.lexitile

import play.api.libs.json._

case class PuzzleState(board: Vector[Vector[Char]], emptyRow: Int, emptyCol: Int, solved: Boolean)

object PuzzleState {
  implicit val charFormat: Format[Char] = Format(
    Reads.of[String].collect(JsonValidationError("Expected single character")) {
      case s if s.length == 1 => s.charAt(0)
    },
    Writes(c => JsString(c.toString))
  )

  implicit val format: OFormat[PuzzleState] = Json.format[PuzzleState]
}



case class MoveRequest(row: Int, col: Int)

object MoveRequest {
  implicit val reads: Reads[MoveRequest] = Json.reads[MoveRequest]
  implicit val writes: OWrites[MoveRequest] = Json.writes[MoveRequest]
  implicit val format: OFormat[MoveRequest] = Json.format[MoveRequest]
}

