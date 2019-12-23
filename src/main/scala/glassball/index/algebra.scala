package glassball.index

import glassball.fileOps.TextFileReader
import glassball.indexers.Indexer
import glassball.tokenizers.Tokenizer

trait Index[A[_]] {
  type Indx = Map[String, Set[String]]

  def apply(filePaths: Iterable[String])(
    implicit tokenizer: Tokenizer[A],
    indexer: Indexer[A],
    fileReader: TextFileReader[A]
  ): A[Indx]
}

object Index {
  def apply[A[_]](filePaths: Iterable[String])(
    implicit instance: Index[A],
    tokenizer: Tokenizer[A],
    indexer: Indexer[A],
    fileReader: TextFileReader[A]
    ): A[Map[String, Set[String]]] = {
    instance(filePaths)
  }
}
