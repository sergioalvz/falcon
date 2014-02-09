package org.falcon.streaming.filter

import twitter4j.FilterQuery
import org.falcon.util.Util

/**
 * Project: falcon
 * Package: org.falcon.streaming.filter
 *
 * Author: Sergio Álvarez
 * Date: 02/2014
 */
class LocationFilter extends Filter{
  private[this] val _filter = new FilterQuery().locations(Util.locations)
  def filterQuery: FilterQuery = _filter
}
