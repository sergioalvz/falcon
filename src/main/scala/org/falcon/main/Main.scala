package org.falcon.main

import org.falcon.streaming.Collector

/**
 * Project: falcon
 * Package: org.falcon.main
 *
 * Author: Sergio Álvarez
 * Date: 09/2013
 */
object Main {
  def main(args: Array[String]) = {
    run()
  }

  def run() = {
    println("========================================")
    println("                 falcon                 ")
    println("========================================")
    println()

    val collector = new Collector()
    collector.collect
  }
}
