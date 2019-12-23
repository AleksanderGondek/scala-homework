package glassball.index

object DefaultIndex {
  import scala.collection.immutable.Map
  import scala.util.Try

  import glassball.indexers.Indexer
  import glassball.fileOps.TextFileReader
  import glassball.tokenizers.Tokenizer

  implicit val defaultIndex: Index[Try] = new Index[Try] {
    def apply(filePaths: Iterable[String])(
      implicit tokenizer: Tokenizer[Try],
      indexer: Indexer[Try],
      fileReader: TextFileReader[Try]
      ): Try[Map[String,Set[String]]] = Try {
        val partialIndicies = for {
          filePath <- filePaths
          lines = TextFileReader[Try](filePath).fold(
            _ => Iterable.empty,
            lines => lines.toIterable
          )
          partialIndex: Map[String, Set[String]] = Indexer[Try](
            filePath, lines
            ).fold(
              _ => Map.empty,
              partialIndx => partialIndx
            )
        } yield (partialIndex)
        val index = partialIndicies.reduce((partialA, partialB) => {
          partialA ++ partialB.map {
            case(token, files) => 
              token -> (files ++ partialA.getOrElse(token, Set.empty))
          }
        })
      index
    }
  }
}
