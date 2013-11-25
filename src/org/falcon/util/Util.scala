package org.falcon.util

import java.util.Properties
import twitter4j.conf.Configuration
import java.io.FileInputStream
import scala.io.Source
import scala.Array

/**
 * Project: falcon
 * Package: org.falcon.util
 *
 * Author: Sergio Álvarez
 * Date: 09/2013
 */
object Util {

  def twitterConfiguration: Configuration = {
    val properties = new Properties()
    properties.load(Util.getClass.getResourceAsStream("/twitter.properties"))

    new twitter4j.conf.ConfigurationBuilder()
      .setOAuthConsumerKey(properties.getProperty("consumer_key"))
      .setOAuthConsumerSecret(properties.getProperty("consumer_secret"))
      .setOAuthAccessToken(properties.getProperty("access_token"))
      .setOAuthAccessTokenSecret(properties.getProperty("access_token_secret"))
      .build
  }

  private def stopWordsFile: String = {
    val properties = new Properties()
    properties.load(Util.getClass.getResourceAsStream("/configuration.properties"))
    properties.getProperty("stop_words_file")
  }

  def stopWords: Array[String] = Source.fromFile(stopWordsFile).getLines().toArray

  def locations: Array[Array[Double]] = {
    val properties = new Properties()
    properties.load(Util.getClass.getResourceAsStream("/configuration.properties"))
    val boundingBoxesString = properties.getProperty("bounding_boxes")

    val boundingBoxes = boundingBoxesString.split("@@")
    val locations = Array.ofDim[Double](boundingBoxes.size, 2)

    for(i <- 0 to boundingBoxes.length -1 ){
      val coordinates = boundingBoxes(i).split(",")
      locations(i) = Array(coordinates(0).toDouble, coordinates(1).toDouble)
    }

    locations
  }
}
