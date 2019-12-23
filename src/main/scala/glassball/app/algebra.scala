package glassball.app

import glassball.tokenizers.Tokenizer

trait GlassBallApp[A[_]] {
  def apply(index: Map[String, Set[String]])(
    implicit tokenizer: Tokenizer[A],
  ): A[Unit]
}

object GlassBallApp {
  def apply[A[_]](index: Map[String, Set[String]])(
    implicit instance: GlassBallApp[A],
    tokenizer: Tokenizer[A]
    ): A[Unit] = {
    instance(index)
  }
}
