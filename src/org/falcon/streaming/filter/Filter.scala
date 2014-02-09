package org.falcon.streaming.filter

import twitter4j.FilterQuery

/**
 * Project: falcon
 * Package: org.falcon.streaming.filter
 *
 * Author: Sergio Álvarez
 * Date: 02/2014
 */
trait Filter {
  def filterQuery: FilterQuery
}
