package org.falcon.util

import java.util.Properties
import twitter4j.conf.{Configuration, ConfigurationBuilder}
import java.io.FileInputStream

/**
 * Project: falcon
 * Package: org.falcon.util
 *
 * Author: Sergio Álvarez
 * Date: 09/2013
 */
object Util {

  def config: Configuration = {
    val in = new FileInputStream("twitter.properties")
    val properties = new Properties()
    properties.load(in)
    in.close()

    new twitter4j.conf.ConfigurationBuilder()
      .setOAuthConsumerKey(properties.getProperty("consumer_key"))
      .setOAuthConsumerSecret(properties.getProperty("consumer_secret"))
      .setOAuthAccessToken(properties.getProperty("access_token"))
      .setOAuthAccessTokenSecret(properties.getProperty("access_token_secret"))
      .build
  }
}
