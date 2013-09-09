package org.falcon.main

import scala.Console
import java.io.IOException
import org.falcon.writer.Writer
import org.falcon.streaming.TwitterStreaming

/**
 * Project: falcon
 * Package: org.falcon.main
 *
 * Author: Sergio Álvarez
 * Date: 09/2013
 */
object Main {
  def main(args: Array[String]) = {
    if (!(args.length > 0))
      Console.err.println("ERROR: Must be provided a file where to save the tweets' collection")
    else
      run(args(0))
  }

  def run(fileName: String) = {
    println("========================================")
    println("                 falcon                 ")
    println("========================================")
    println()
    println("File to save the collection: " + fileName)

    var twitterStreaming: TwitterStreaming = null

    try {
      Writer.open(fileName)
      Writer.write("<tweets>")
      Writer.close()

      twitterStreaming = new TwitterStreaming(fileName)
      twitterStreaming.run()

      println(" ---------------- ")
      println("| 0. Stop & Exit |")
      println(" ---------------- ")

      Iterator.continually(Console.readLine()).takeWhile(_ != "0").foreach(line => println("Press 0 for Stop & Exit"))

      twitterStreaming.close()

      Writer.open(fileName)
      Writer.write("</tweets>")
      Writer.close()
    } catch {
      case ex: IOException => {
        Console.err.println("ERROR: " + ex.getMessage + ". The file might be corrupted.")
      }
    } finally {
      Writer.close()
      twitterStreaming.close()
    }
  }
}
