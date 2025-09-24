package controllers

import javax.inject._
import play.api.mvc._
import play.api.libs.json._
import com.sdraycott.lexitile._

@Singleton
class PuzzleController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  private var currentPuzzle = new Puzzle()

  implicit val puzzleStateFormat = PuzzleState.format

  private def puzzleToState(puzzle: Puzzle, solved: Boolean): PuzzleState =
    PuzzleState(puzzle.puzzle.map(_.toVector).toVector, puzzle.emptyRow, puzzle.emptyCol, puzzle.isSolved)

  /** GET /puzzle/new */
  def newPuzzle() = Action {
    currentPuzzle = new Puzzle()
    currentPuzzle.shufflePuzzle(200)
    Ok(Json.toJson(puzzleToState(currentPuzzle, currentPuzzle.isSolved)))
  }

  /** POST /puzzle/move */
  def move() = Action(parse.json) { request =>
    request.body.validate[MoveRequest].fold(
      errors => BadRequest(JsError.toJson(errors)),
      moveReq => {
        val moved = currentPuzzle.slideTo(moveReq.row, moveReq.col)
        if (moved) {
          val isSolved = currentPuzzle.isSolved // implement this in your puzzle class
          Ok(Json.obj(
            "board" -> currentPuzzle.puzzle.map(_.map(_.toString)),
            "solved" -> isSolved
          ))
        } else {
          BadRequest(Json.obj("error" -> "Invalid move"))
        }
      }
    )
  }
}
