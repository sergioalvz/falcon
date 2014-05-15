package org.falcon.util

import java.util.Properties
import scala.io.Source
import scala.Array
import scala.collection.JavaConverters._
import scala.concurrent.duration._
import java.util.concurrent.TimeUnit
import scala.xml.XML
import scala.collection.mutable.ListBuffer

import org.falcon.main.Configuration

/**
 * Project: falcon
 * Package: org.falcon.util
 *
 * Author: Sergio Álvarez
 * Date: 09/2013
 */
object Util {
  private[this] val AccessTokenSecretPropertyKey    = "access_token_secret"
  private[this] val AccessTokenPropertyKey          = "access_token"
  private[this] val ConsumerSecretPropertyKey       = "consumer_secret"
  private[this] val ConsumerKeyPropertyKey          = "consumer_key"

  private[this] val _locations: Array[Array[Double]] = null
  private[this] var configuration: Configuration     = null

  def loadConfiguration(config: Configuration): Unit = this.configuration = config

  def twitterConfiguration: twitter4j.conf.Configuration = {
    val properties = new Properties()
    properties.load(Source.fromFile(configuration.credentials).bufferedReader())

    new twitter4j.conf.ConfigurationBuilder()
      .setOAuthConsumerKey(properties.getProperty(ConsumerKeyPropertyKey))
      .setOAuthConsumerSecret(properties.getProperty(ConsumerSecretPropertyKey))
      .setOAuthAccessToken(properties.getProperty(AccessTokenPropertyKey))
      .setOAuthAccessTokenSecret(properties.getProperty(AccessTokenSecretPropertyKey))
      .build
  }

  def stopWords: Array[String] = Source.fromFile(configuration.stopWords).getLines().toArray

  def locations: Array[Array[Double]] = if(_locations != null) _locations else getBoundingBoxes

  def areBoundingBoxesProvided: Boolean = configuration.boundingBoxes != null

  def language:Array[String] = Array(configuration.language)

  def timeToCollect: Long = {
    val timeMeasure = configuration.timeMeasure
    val timeToCollect = configuration.timeToCollect
    Duration(timeToCollect, TimeUnit.valueOf(timeMeasure)).toMillis
  }

  def fileName: String = configuration.output

  def areCoordinatesMandatory: Boolean = configuration.coordinatesMandatory

  private[this] def getBoundingBoxes: Array[Array[Double]] = {
    val root = XML.loadFile(configuration.boundingBoxes)
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
}
