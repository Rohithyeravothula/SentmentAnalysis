package sentimentAnalysis

import org.apache.spark.streaming.twitter.TwitterUtils
import sentimentAnalysis.Resources._
/**
  * Created by rohith on 9/12/16.
  */
object TwitterSent {
  val twitterStream = TwitterUtils.createStream(sparkStreaming, None)
  val tweetBody = twitterStream.map {
    case tags if tags.getLang == "en" => tags.getText
    case _ => ""
  }
  tweetBody.foreachRDD{ tweetRDD =>
    tweetRDD.foreach{ tweet =>
      val sentiment = sentimentEngine.sentiment(tweet)
      println(s"sentiment for $tweet is $sentiment")
    }
  }
  startStreaming()
}


