package glassball.fileOps

trait TextFileReader[A[_]] {
  def apply(filePath: String): A[Iterator[String]]
}

object TextFileReader {
  def apply[A[_]](filePath: String)(
    implicit instance: TextFileReader[A]
    ): A[Iterator[String]] = {
    instance(filePath)
  }
}

trait ListDir[A[_]] {
  def apply(dirsPaths: Iterable[String]): A[Iterable[String]]
}

object ListDir {
  def apply[A[_]](dirsPaths: Iterable[String])(
    implicit instance: ListDir[A]
    ): A[Iterable[String]] = {
    instance(dirsPaths)
  }
}