package glassball.tokenizers

import org.scalatest._
import org.scalatest.propspec.AnyPropSpec
import org.scalatest.matchers.should.Matchers
import prop._

class DefaultTokenizerSpec extends AnyPropSpec 
with TableDrivenPropertyChecks with Matchers {
  val testTextLines = Table(
    "textLine",
    "The quick brown fox jumps over the lazy dog",
    "!T\"h#e$ %q&u'i(c)k* +b,ro-w.n/ :f;o<x= >j?u@m[p]s^ _o`v{e|r} ~the, lazy dog",
    "𝒯𝒽𝑒 𝓆𝓊𝒾𝒸𝓀 𝒷𝓇𝑜𝓌𝓃 𝒻𝑜𝓍 𝒿𝓊𝓂𝓅𝓈 𝑜𝓋𝑒𝓇 𝓉𝒽𝑒 𝓁𝒶𝓏𝓎 𝒹𝑜𝑔",
  )

  property("DefaultTokenizer should properly handle text with punctuaction and unicode symbols") {
    forAll(testTextLines) { textLine =>
      DefaultTokenizer.tokenizer(textLine).getOrElse(Set.empty) shouldBe Set(
        "the", "quick", "brown", "fox", "jumps", "over", "the", "lazy", "dog"
      )
    }
  }
}