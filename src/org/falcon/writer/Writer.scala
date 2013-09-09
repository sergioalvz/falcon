package org.falcon.writer

import java.io.{PrintWriter, BufferedWriter, FileWriter}

/**
 * Project: falcon
 * Package: org.falcon.writer
 *
 * Author: Sergio Álvarez
 * Date: 09/2013
 */
object Writer {
  private var writer: PrintWriter = null

  def open(file: String) = if (writer == null) writer = new PrintWriter(new BufferedWriter(new FileWriter(file, true)))

  def close() = {
    if (writer != null) {
      writer.close()
      writer = null
    }
  }

  def write(string: String) = if (writer != null) writer.println(string)
}
