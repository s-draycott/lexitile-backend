package com.sdraycott.lexitile

import play.api.libs.json._

case class PuzzleState(board: Vector[Vector[Char]], emptyRow: Int, emptyCol: Int)

object PuzzleState {
  implicit val charFormat: Format[Char] = Format(
    Reads.of[String].collect(JsonValidationError("Expected single character")) {
      case s if s.length == 1 => s.charAt(0)
    },
    Writes(c => JsString(c.toString))
  )

  implicit val format: OFormat[PuzzleState] = Json.format[PuzzleState]
}

case class MoveRequest(state: PuzzleState, direction: String)

object MoveRequest {
  implicit val format: OFormat[MoveRequest] = Json.format[MoveRequest]
}
