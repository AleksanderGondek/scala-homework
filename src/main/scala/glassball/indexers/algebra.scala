package glassball.indexers

import glassball.tokenizers.Tokenizer

trait Indexer[A[_]] {
  type Index = Map[String, Set[String]]

  def apply(fileName: String, text: Iterable[String])(
    implicit tokenizer: Tokenizer[A]
    ): A[Index]
}

object Indexer {
  def apply[A[_]](fileName: String, text: Iterable[String])(
    implicit instance: Indexer[A], tokenizer: Tokenizer[A]
    ): A[Map[String, Set[String]]] = {
    instance(fileName, text)
  }
}
