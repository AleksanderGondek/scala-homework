package glassball.tokenizers

trait Tokenizer[A[_]] {
    def apply(text: String): A[Set[String]]
  }
  
  object Tokenizer {
    def apply[A[_]](text: String)(
      implicit instance: Tokenizer[A]
      ): A[Set[String]] = {
      instance(text)
    }
  }
  