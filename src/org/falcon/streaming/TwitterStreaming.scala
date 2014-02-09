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
    twitter = new TwitterStreamFactory(Util.twitterConfiguration).getInstance()
    twitter.addListener(myTwitterStatusListener)
    twitter.filter(Util.filter.filterQuery)
  }

  def close() = {
    if (twitter != null) {
      twitter.shutdown()
    }
  }

  private def myTwitterStatusListener = new StatusListener {
    def onStatus(status: Status) {
        val username: String = status.getUser.getScreenName
        val location: String = status.getUser.getLocation
        val timezone: String = status.getUser.getTimeZone
        val text: String = status.getText

        var latitude: String = ""
        var longitude: String = ""
        if(status.getGeoLocation != null){
          latitude = status.getGeoLocation.getLatitude.toString
          longitude = status.getGeoLocation.getLongitude.toString
        }

        val tweet = new Tweet(username, location, timezone, latitude, longitude, text)
        Writer.open(fileName)
        Writer.write(tweet.toXML.toString())
        Writer.close()
    }

    def onStallWarning(p1: StallWarning) {}

    def onException(p1: Exception) {}

    def onDeletionNotice(p1: StatusDeletionNotice) {}

    def onScrubGeo(p1: Long, p2: Long) {}

    def onTrackLimitationNotice(p1: Int) {}
  }
}
