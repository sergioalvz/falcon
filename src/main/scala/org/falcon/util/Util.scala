package org.falcon.util

import java.util.Properties
import twitter4j.conf.Configuration
import scala.io.Source
import scala.Array
import scala.collection.JavaConverters._
import scala.concurrent.duration._
import java.util.concurrent.TimeUnit
import scala.xml.XML
import scala.collection.mutable.ListBuffer

/**
 * Project: falcon
 * Package: org.falcon.util
 *
 * Author: Sergio Álvarez
 * Date: 09/2013
 */
object Util {

  val LanguagePropertyKey      = "filter_language"
  val BoundingBoxesPropertyKey = "filter_bounding_boxes_file"

  private[this] val CoordinatesMandatoryPropertyKey = "coordinates_mandatory"
  private[this] val StopWordsFilePropertyKey        = "stop_words_file"
  private[this] val TimeToCollectPropertyKey        = "time_to_collect"
  private[this] val TimeInPropertyKey               = "time_in"
  private[this] val FileNamePropertyKey             = "file_name"
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

  def stopWords: Array[String] = Source.fromFile(stopWordsFile).getLines().toArray
  private[this] def stopWordsFile: String = {
    val properties = new Properties()
    properties.load(Source.fromFile(getExecutableAbsolutePath + ConfigurationPropertiesFileName).bufferedReader())
    properties.getProperty(StopWordsFilePropertyKey)
  }

  def locations: Array[Array[Double]] = {
    val properties = new Properties()
    properties.load(Source.fromFile(getExecutableAbsolutePath + ConfigurationPropertiesFileName).bufferedReader())
    val fileName = properties.getProperty(BoundingBoxesPropertyKey)
    getBoundingBoxes(fileName)
  }

  private[this] def getBoundingBoxes(fileName: String): Array[Array[Double]] = {
    val root = XML.loadFile(fileName)
    var boundingBoxes = new ListBuffer[Array[Double]]
    (root \\ "boundingBox").foreach(b => {
      val sw_long = (b \ "sw" \ "longitude").text
      val sw_lat = (b \ "sw" \ "latitude").text
      val ne_long = (b \ "ne" \ "longitude").text
      val ne_lat = (b \ "ne" \ "latitude").text

      boundingBoxes += Array(sw_long.toDouble, sw_lat.toDouble)
      boundingBoxes += Array(ne_long.toDouble, ne_lat.toDouble)
    })
    boundingBoxes.toArray
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

  def timeToCollect: Long = {
    val properties = new Properties()
    properties.load(Source.fromFile(getExecutableAbsolutePath + ConfigurationPropertiesFileName).bufferedReader())
    val timeMeasure = properties.getProperty(TimeInPropertyKey)
    val timeToCollect = properties.getProperty(TimeToCollectPropertyKey).toDouble
    Duration(timeToCollect, TimeUnit.valueOf(timeMeasure)).toMillis
  }

  def fileName: String = {
    val properties = new Properties()
    properties.load(Source.fromFile(getExecutableAbsolutePath + ConfigurationPropertiesFileName).bufferedReader())
    properties.getProperty(FileNamePropertyKey)
  }

  def filterProperties: Set[String] = {
    val properties = new Properties()
    properties.load(Source.fromFile(getExecutableAbsolutePath + ConfigurationPropertiesFileName).bufferedReader())
    properties.stringPropertyNames.asScala.filter(property => property.startsWith("filter_")).toSet
  }

  def areCoordinatesMandatory: Boolean = {
    val properties = new Properties()
    properties.load(Source.fromFile(getExecutableAbsolutePath + ConfigurationPropertiesFileName).bufferedReader())
    properties.getProperty(CoordinatesMandatoryPropertyKey) == "true"
  }
}
