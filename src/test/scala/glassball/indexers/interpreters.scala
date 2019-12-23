package glassball.indexers

import org.scalatest._
import org.scalatest.propspec.AnyPropSpec
import org.scalatest.matchers.should.Matchers
import prop._

class DefaultIndexerSpec extends AnyPropSpec 
with TableDrivenPropertyChecks with Matchers {
  import scala.util.Failure
  import scala.util.Try
  import glassball.tokenizers.Tokenizer

  implicit val testTokenizer: Tokenizer[Try] = new Tokenizer[Try] {
    def apply(line: String): Try[Set[String]] = Try {
      line.split(" ").toSet
    }
  }

  val failingTestTokenizer: Tokenizer[Try] = new Tokenizer[Try] {
    def apply(line: String): Try[Set[String]] = Failure(
      new RuntimeException("This should not happen")
    )
  }

  val testText = Table(
    ("text", "outputKeys"),
    (List.empty.toIterable, Set.empty),
    (List("a").toIterable, Set("a")),
    (List("a", "b").toIterable, Set("a", "b")),
    (List("a b c", "d e f").toIterable, Set("a", "b", "c", "d", "e", "f")),
    (
      List("a b c d e f g", "h i j k l m n ", "o p r s t").toIterable, 
      Set("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "r", "s", "t"),
    )
  )

  val exceptionHandlingTest = Table(
    ("text", "outputKeys"),
    (List("a", "b").toIterable, Set.empty),
  )

  property("DefaultIndexer should create a map of all tokens from given lines") {
    forAll(testText) { (text, outputKeys) =>
      // For empty input, exception is thrown and swallowed by Try, works as expected (thus getOrElse empty)
      val index = DefaultIndexer.indexer("fileName.txt", text)(testTokenizer).getOrElse(Map.empty)
      index.keys.toSet shouldBe outputKeys
      index.values.forall(value => value == Set("fileName.txt")) shouldBe true
    }

    forAll(exceptionHandlingTest) { (text, outputKeys) =>
      val index = DefaultIndexer.indexer("fileName.txt", text)(failingTestTokenizer).getOrElse(Map("x" -> "x"))
      index.keys.toSet shouldBe outputKeys
      index.values.forall(value => value == Set("fileName.txt")) shouldBe true
    }
  }
}