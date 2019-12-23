package glassball.indexers

object DefaultIndexer {
  import scala.collection.immutable.Map
  import scala.util.Try

  import glassball.tokenizers.Tokenizer

  implicit val indexer: Indexer[Try] = new Indexer[Try] {
    def apply(fileName: String, text: Iterable[String])(
      implicit tokenizer: Tokenizer[Try]
      ): Try[Map[String, Set[String]]] = Try {
      val tokensFromFile = text
        .map(line => tokenizer(line).fold(_ => Set.empty, tokens => tokens))
        .reduce((tokensA, tokensB) => tokensA ++ tokensB)      
      tokensFromFile.map(token => token -> Set(fileName)).toMap
    }
  }
}
