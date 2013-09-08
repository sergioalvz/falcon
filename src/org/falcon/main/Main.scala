package org.falcon.main

import scala.Console
import java.io.IOException
import org.falcon.writer.Writer

/**
 * Project: falcon
 * Package: org.falcon.main
 *
 * Author: Sergio Álvarez
 * Date: 09/2013
 */
object Main {
  def main(args: Array[String]): Unit = {
    if(!(args.length > 0))
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

    try{
      Writer.open(fileName)
      Writer.write("<tweets>")
      Writer.write("</tweets>")
    }catch{
      case ex: IOException => {
        Console.err.println("ERROR: " + ex.getMessage)
      }
    }finally{
      Writer.close()
    }
  }
}
