package glassball.tokenizers

object DefaultTokenizer {
    import scala.util.Try
  
    implicit val tokenizer: Tokenizer[Try] = new Tokenizer[Try] {
      def apply(line: String): Try[Set[String]] = Try {
        raw"\p{Punct}".r.replaceAllIn(line, "")
          .split(" ")
          .map(word => raw"\s+".r.replaceAllIn(word, ""))
          .map(word => 
            java.text.Normalizer.normalize(
              word, java.text.Normalizer.Form.NFKC
            ).toLowerCase()
          ).toSet
      }
    }
  }
  