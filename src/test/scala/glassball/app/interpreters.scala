package glassball.app

import scala.collection.immutable.ListMap

import org.scalatest._
import org.scalatest.propspec.AnyPropSpec
import org.scalatest.matchers.should.Matchers
import prop._

class DefaultGlassBallAppSpec extends AnyPropSpec 
with TableDrivenPropertyChecks with Matchers {
  val tests = Table(
    ("index", "tokens", "output"),
    (
        Map("a" -> Set("filename0")),
        Set("a"),
        ListMap("filename0" -> 1)
    ),
    (
        Map("a" -> Set("filename0")),
        Set("b"),
        ListMap.empty
    ),
    (
        Map("a" -> Set("filename0"), "b" -> Set("filename0", "filename1")),
        Set("b"),
        ListMap("filename0" -> 1, "filename1" -> 1)
    ),
    (
        Map(
            "a" -> Set("filename0"),
            "b" -> Set("filename0", "filename1"),
            "c" -> Set("filename0", "filename1", "filename2")
        ),
        Set("b", "a"),
        ListMap("filename0" -> 2, "filename1" -> 1)
    )
  )

  property("getRanks should count token hits in index") {
    forAll(tests) { (index, tokens, output) =>
      DefaultGlassBallApp.getRanks(index, tokens) shouldBe output
    }
  }
}