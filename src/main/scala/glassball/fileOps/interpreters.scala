package glassball.fileOps

object DefaultReader {
  import scala.io.Source
  import scala.util.Try

  implicit val reader: TextFileReader[Try] = new TextFileReader[Try] {
    def apply(filePath: String): Try[Iterator[String]] = Try {
        Source.fromFile(filePath).getLines()
    }
  }
}

object DefaultListDir {
  import java.io.File
  import scala.util.Try

  def getFilesPaths(dirPath: String): Iterable[String] = {
    val possibleDir = new File(dirPath)
    if(!possibleDir.exists() || !possibleDir.isDirectory()) {
      List("").toIterable
    }
    else {
      possibleDir
        .listFiles
        .filter(_.isFile)
        .map(_.getAbsolutePath)
        .toIterable
    }
  }

  implicit val listDir: ListDir[Try] = new ListDir[Try] {
    def apply(dirsPaths: Iterable[String]): Try[Iterable[String]] = Try {
      val filePaths = for {
        dirPath <- dirsPaths
        filePath <- getFilesPaths(dirPath)
      } yield(filePath)
      filePaths.toIterable
    }
  }
}
