package org.falcon.main

import scala.Console
import java.io.IOException
import org.falcon.writer.Writer
import org.falcon.streaming.TwitterStreaming
import org.falcon.util.Util

/**
 * Project: falcon
 * Package: org.falcon.main
 *
 * Author: Sergio Álvarez
 * Date: 09/2013
 */
object Main {
  def main(args: Array[String]) = {
    val fileName = Util.fileName
    if (fileName.isEmpty)
      Console.err.println("ERROR: Must be provided a file where to save the tweets' collection")
    else
      run(fileName)
  }

  def run(fileName: String) = {
    println("========================================")
    println("                 falcon                 ")
    println("========================================")
    println()
    println(s"File to save the collection: $fileName")

    var twitterStreaming: TwitterStreaming = null

    try {
      Writer.open(fileName)
      Writer.write("<tweets>\n")
      Writer.close()

      twitterStreaming = new TwitterStreaming(fileName)
      twitterStreaming.run()
      val top = System.currentTimeMillis() + Util.timeToCollect
      while(System.currentTimeMillis() <= top) {}
      twitterStreaming.close()

      Writer.open(fileName)
      Writer.write("</tweets>")
      Writer.close()
    } catch {
      case ex: IOException => {
        Console.err.println(s"ERROR: ${ex.getMessage}. The file might be corrupted.")
      }
    } finally {
      Writer.close()
      twitterStreaming.close()
    }
  }
}
