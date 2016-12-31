package sentimentAnalysis.api

/**
  * Created by rohith on 31/12/16.
  */

trait SentimentEngine {
  def sentiment(input:String) : Int
}
