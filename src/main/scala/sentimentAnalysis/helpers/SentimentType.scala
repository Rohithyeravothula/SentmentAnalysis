package sentimentAnalysis.helpers

/**
  * Created by rohith on 31/12/16.
  */
object SentimentType extends Enumeration {
  type Sentiment = Value
  val POSITIVE, NEGATIVE, NEUTRAL = Value

  def toSentiment(sentiment: Int) : Sentiment = {
    sentiment match {
      case x if x == 0 || x == 1 => SentimentType.NEGATIVE
      case x if x == 2 => SentimentType.NEUTRAL
      case x if x == 3 || x == 4 => SentimentType.POSITIVE
    }
  }
}