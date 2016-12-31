package sentimentAnalysis.engine

import edu.stanford.nlp.ling.CoreAnnotations
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations
import edu.stanford.nlp.pipeline.Annotation
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations
import sentimentAnalysis.helpers.SentimentType
import sentimentAnalysis.helpers.SentimentType.Sentiment
import sentimentAnalysis.Resources._
import sentimentAnalysis.api.SentimentEngine
import scala.collection.convert.wrapAll._

/**
  * Created by rohith on 9/12/16.
  */

class StanfordNLPSentimentEngine extends SentimentEngine {

  // in case of multiple sentiments, choose sentiment of lengthiest sentence as main sentiment
  private def extractSentiment(text: String): Int = {
    extractSentiments(text) match {
      case list if list.nonEmpty =>
        val (_, senti) = list.maxBy{
        case (sentence, _) => sentence.length
      }
        senti
      case _ => 2
    }
  }

  private def extractSentiments(text: String): List[(String, Int)] = {
    val annotation: Annotation = pipeline.process(text)
    val sentences = annotation.get(classOf[CoreAnnotations.SentencesAnnotation])
    val annotationTree = sentences.map(sentence => (sentence, sentence.get(classOf[SentimentCoreAnnotations.SentimentAnnotatedTree])))
    val sentimentVal = annotationTree.map{case (sentence, tree) => (sentence.toString, RNNCoreAnnotations.getPredictedClass(tree))}.toList
    sentimentVal
  }

  def sentiment(input:String): Sentiment = {
    input.isEmpty match {
      case true => SentimentType.toSentiment(extractSentiment(input))
      case false => SentimentType.toSentiment(2)
    }
  }
}

