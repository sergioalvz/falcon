package org.falcon.model

/**
 * Project: falcon
 * Package: org.falcon.model
 *
 * Author: Sergio Álvarez
 * Date: 09/2013
 */
class Tweet(username: String, location: String, timezone: String, latitude: String, longitude: String, text: String) {
  def toXML =
    <tweet>
      <username>
        {username}
      </username>
      <location>
        {location}
      </location>
      <timezone>
        {timezone}
      </timezone>
      <latitude>
        {latitude}
      </latitude>
      <longitude>
        {longitude}
      </longitude>
      <text>
        {text}
      </text>
    </tweet>
}
