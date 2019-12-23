package glassball

object Main extends App {
  import scala.util.Try

  import glassball.app.DefaultGlassBallApp._
  import glassball.app.GlassBallApp
  import glassball.fileOps.DefaultListDir._
  import glassball.fileOps.DefaultReader._
  import glassball.fileOps.ListDir
  import glassball.index.DefaultIndex._
  import glassball.index.Index
  import glassball.indexers.DefaultIndexer._
  import glassball.tokenizers.DefaultTokenizer._
  
  for {
    filesPaths <- ListDir[Try](args.toIterable)
    index <- Index[Try](filesPaths)
    _ <- GlassBallApp[Try](index)
  } yield ()
}