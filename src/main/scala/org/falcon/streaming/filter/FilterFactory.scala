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
object FilterFactory {

  def createFilterQuery: FilterQuery = {
    val filterQuery = new FilterQuery()
    Util.filterProperties.toArray.foreach(property => {
      if(property == Util.BoundingBoxesPropertyKey) {
        filterQuery.locations(Util.locations)
      } else if(property == Util.LanguagePropertyKey) {
        filterQuery.language(Util.language).track(Util.stopWords)
      }
    })
    filterQuery
  }
}
