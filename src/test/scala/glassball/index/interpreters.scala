package glassball.index

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class DefaultIndexSpec extends AnyFlatSpec with Matchers {
  import scala.util.Try
  import glassball.indexers.Indexer
  import glassball.fileOps.TextFileReader
  import glassball.tokenizers.Tokenizer

  val noOpTokenizer: Tokenizer[Try] = new Tokenizer[Try] {
    def apply(line: String): Try[Set[String]] = Try { Set.empty }
  }

  val testIndexer: Indexer[Try] = new Indexer[Try] {
    def apply(fileName: String, text: Iterable[String])(
      implicit tokenizer: Tokenizer[Try]
      ): Try[Map[String, Set[String]]] = Try {
        if(fileName == "fileTest0.txt") {
          Map(
            "a" -> Set(fileName),
            "b" -> Set(fileName),
            "c" -> Set(fileName),
          )
        } else {
          Map(
            "d" -> Set(fileName),
            "e" -> Set(fileName),
            "f" -> Set(fileName),
          )
        }
      }
  }

  val testReader: TextFileReader[Try] = new TextFileReader[Try] {
    def apply(filePath: String): Try[Iterator[String]] = Try {
      List("fileTest0.txt", "fileTest1.txt").toIterator
    }
  }

  "DefaultIndex" should "create token index from all given files" in {
    val createdIndex = DefaultIndex.defaultIndex(
      List("fileTest0.txt", "fileTest1.txt")
    )(noOpTokenizer, testIndexer, testReader)
    .getOrElse(Map.empty)

    createdIndex shouldBe Map(
      "a" -> Set("fileTest0.txt"),
      "b" -> Set("fileTest0.txt"),
      "c" -> Set("fileTest0.txt"),
      "d" -> Set("fileTest1.txt"),
      "e" -> Set("fileTest1.txt"),
      "f" -> Set("fileTest1.txt"),
    )
  }
}