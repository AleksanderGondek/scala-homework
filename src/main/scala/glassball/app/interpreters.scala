package glassball.app

object DefaultGlassBallApp {
  import scala.collection.immutable.ListMap
  import scala.util.Try

  import glassball.tokenizers.Tokenizer

  def getRanks(index: Map[String, Set[String]], tokens: Set[String]): ListMap[String, Int] = {
    val hitsByFilename = tokens.toList.map(
      token => index.getOrElse(token, Set.empty)
    ).flatMap(
      fileNames => fileNames.toList
    ).groupBy(identity).mapValues(_.size)
    ListMap(hitsByFilename.toSeq.sortWith(_._2 > _._2):_*)
  }

  def printOutRanks(ranks: ListMap[String, Int], tokensCount: Int): Unit = {
    ranks.take(10).map {
      case (fileName: String, hitsCount: Int) => {
        val percentageRank = (hitsCount.toFloat/tokensCount) * 100
        println(f"$fileName - Rank: $percentageRank%1.2f%%")
      }
      case _ => ()
    }
    ()
  }

  def loop(index: Map[String, Set[String]])(implicit tokenizer: Tokenizer[Try]): Unit = {
    println("Glassball search cli")
    print(s"search>  ")
    
    val searchString = scala.io.StdIn.readLine()
    if(searchString == ":quit") {
      return
    }

    val tokens = Tokenizer[Try](searchString).getOrElse(Set(""))
    val ranks = getRanks(index, tokens)
    if(ranks.isEmpty) {
      println("no matches found")
    }
    else {
      printOutRanks(ranks, tokens.size)
    }

    loop(index)
  }

  implicit val defaultBApp: GlassBallApp[Try] = new GlassBallApp[Try] {
    def apply(index: Map[String, Set[String]])(implicit tokenizer: Tokenizer[Try]): Try[Unit] = Try{
      loop(index)
    }
  }
}
