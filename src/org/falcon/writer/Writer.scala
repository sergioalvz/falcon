package org.falcon.writer

import java.io._
import java.nio.charset.Charset

/**
 * Project: falcon
 * Package: org.falcon.writer
 *
 * Author: Sergio Álvarez
 * Date: 09/2013
 */
object Writer {
  private var writer: BufferedWriter = null

  def open(file: String) = if (writer == null) writer = new BufferedWriter(new OutputStreamWriter(
    new FileOutputStream(file, true), Charset.forName("UTF-8").newEncoder()))

  def close() = {
    if (writer != null) {
      writer.close()
      writer = null
    }
  }

  def write(string: String) = {
    if (writer != null) {
      writer.flush()
      writer.write(string)
    }
  }
}
