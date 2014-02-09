package org.falcon.util

import java.util.Properties
import twitter4j.conf.Configuration
import scala.io.Source
import scala.Array
import org.falcon.streaming.filter.Filter

/**
 * Project: falcon
 * Package: org.falcon.util
 *
 * Author: Sergio Álvarez
 * Date: 09/2013
 */
object Util {

  private[this] val LanguagePropertyKey             = "language"
  private[this] val BoundingBoxesPropertyKey        = "bounding_boxes"
  private[this] val StopWordsFilePropertyKey        = "stop_words_file"
  private[this] val FilterPropertyKey               = "filter"
  private[this] val ConfigurationPropertiesFileName = "configuration.properties"

  private[this] val AccessTokenSecretPropertyKey    = "access_token_secret"
  private[this] val AccessTokenPropertyKey          = "access_token"
  private[this] val ConsumerSecretPropertyKey       = "consumer_secret"
  private[this] val ConsumerKeyPropertyKey          = "consumer_key"
  private[this] val TwitterPropertiesFileName       = "/twitter.properties"

  def twitterConfiguration: Configuration = {
    val properties = new Properties()
    properties.load(Util.getClass.getResourceAsStream(TwitterPropertiesFileName))

    new twitter4j.conf.ConfigurationBuilder()
      .setOAuthConsumerKey(properties.getProperty(ConsumerKeyPropertyKey))
      .setOAuthConsumerSecret(properties.getProperty(ConsumerSecretPropertyKey))
      .setOAuthAccessToken(properties.getProperty(AccessTokenPropertyKey))
      .setOAuthAccessTokenSecret(properties.getProperty(AccessTokenSecretPropertyKey))
      .build
  }

  def filter:Filter = {
    val properties = new Properties()
    properties.load(Source.fromFile(getExecutableAbsolutePath + ConfigurationPropertiesFileName).bufferedReader())
    Class.forName(properties.getProperty(FilterPropertyKey)).newInstance().asInstanceOf[Filter]
  }

  private[this] def stopWordsFile: String = {
    val properties = new Properties()
    properties.load(Source.fromFile(getExecutableAbsolutePath + ConfigurationPropertiesFileName).bufferedReader())
    properties.getProperty(StopWordsFilePropertyKey)
  }
  def stopWords: Array[String] = Source.fromFile(stopWordsFile).getLines().toArray

  def locations: Array[Array[Double]] = {
    val properties = new Properties()
    properties.load(Source.fromFile(getExecutableAbsolutePath + ConfigurationPropertiesFileName).bufferedReader())
    val boundingBoxesString = properties.getProperty(BoundingBoxesPropertyKey)

    val boundingBoxes = boundingBoxesString.split("@@")
    val locations = Array.ofDim[Double](boundingBoxes.size, 2)

    boundingBoxes.indices.foreach(index => {
      val coordinates = boundingBoxes(index).split(",")
      locations(index) = Array(coordinates(0).toDouble, coordinates(1).toDouble)
    })

    locations
  }

  def language:Array[String] = {
    val properties = new Properties()
    properties.load(Source.fromFile(getExecutableAbsolutePath + ConfigurationPropertiesFileName).bufferedReader())
    Array(properties.getProperty(LanguagePropertyKey))
  }

  def getExecutableAbsolutePath: String = {
    val absolutePath = Util.getClass.getProtectionDomain.getCodeSource.getLocation.getPath
    absolutePath.substring(0, absolutePath.lastIndexOf('/') + 1)
  }
}
