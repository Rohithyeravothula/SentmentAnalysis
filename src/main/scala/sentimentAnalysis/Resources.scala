package sentimentAnalysis

import java.util.Properties

import edu.stanford.nlp.pipeline.StanfordCoreNLP
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import sentimentAnalysis.engine.StanfordNLPSentimentEngine

/**
  * Created by rohith on 9/12/16.
  */
object Resources {
  val config = new SparkConf().setMaster("local[*]").setAppName("Twitter sentiment analysis")
  val spark = new SparkContext(config)
  val sparkStreaming = new StreamingContext(spark, Seconds(5))

  spark.setLogLevel("ERROR")

  System.setProperty("twitter4j.oauth.consumerKey", "dq2Hm3vO89zomk6uEBxkGg6An")
  System.setProperty("twitter4j.oauth.consumerSecret", "0GYPacynoZZNundOdqWEG2YoOCoZGtug8GjnnaSpTev0Hv4hfL")
  System.setProperty("twitter4j.oauth.accessToken", "3134452938-OLsip2fdeotetNnJ2G5Bcel6adjXhyQebML3SKt")
  System.setProperty("twitter4j.oauth.accessTokenSecret", "wiptVFdACdNG74Vd2EkutT25AYZ2rcMmh6QLl9F0aT9Eg")


  val props = new Properties()
  props.setProperty("annotators", "tokenize, ssplit, parse, sentiment")
  val pipeline: StanfordCoreNLP = new StanfordCoreNLP(props)

  val sentimentEngine = new StanfordNLPSentimentEngine

  def startStreaming(): Unit = {
    sparkStreaming.start()
    sparkStreaming.awaitTermination()
  }
}
