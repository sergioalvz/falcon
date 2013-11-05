package org.falcon.util

import java.util.Properties
import twitter4j.conf.Configuration
import java.io.FileInputStream
import scala.io.Source

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
}
