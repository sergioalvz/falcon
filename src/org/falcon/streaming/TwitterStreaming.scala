package org.falcon.streaming

import twitter4j._
import org.falcon.model.Tweet
import org.falcon.writer.Writer
import org.falcon.util.Util

/**
 * Project: falcon
 * Package: org.falcon.streaming
 *
 * Author: Sergio Álvarez
 * Date: 09/2013
 */
class TwitterStreaming(fileName: String) {

  private var twitter: TwitterStream = _

  def run() = {
    twitter = new TwitterStreamFactory(Util.config).getInstance()
    twitter.addListener(myTwitterStatusListener)
    twitter.filter(new FilterQuery().locations(Array(Array(-18.1590, 27.6363), Array(4.3279, 43.7900))))
  }

  def close() = {
    if (twitter != null) {
      twitter.shutdown()
    }
  }

  private def myTwitterStatusListener = new StatusListener {
    def onStatus(status: Status) {
      if (status.getGeoLocation != null) {
        val username: String = status.getUser.getScreenName
        val location: String = status.getUser.getLocation
        val timezone: String = status.getUser.getTimeZone
        val latitude: String = status.getGeoLocation.getLatitude.toString
        val longitude: String = status.getGeoLocation.getLongitude.toString
        val text: String = status.getText

        val tweet = new Tweet(username, location, timezone, latitude, longitude, text)
        Writer.open(fileName)
        Writer.write(tweet.toXML.toString())
        Writer.close()
      }
    }

    def onStallWarning(p1: StallWarning) {}

    def onException(p1: Exception) {}

    def onDeletionNotice(p1: StatusDeletionNotice) {}

    def onScrubGeo(p1: Long, p2: Long) {}

    def onTrackLimitationNotice(p1: Int) {}
  }
}
