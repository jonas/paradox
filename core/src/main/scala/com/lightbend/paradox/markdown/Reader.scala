/*
 * Copyright © 2015 - 2017 Lightbend, Inc. <http://www.lightbend.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lightbend.paradox.markdown

import org.parboiled.Parboiled
import org.pegdown.ast.RootNode
import org.pegdown.plugins.PegDownPlugins
import org.pegdown.{ Extensions, Parser, ParserWithDirectives }
import com.vladsch.flexmark.parser.{ Parser => FlexmarkParser }
import scala.concurrent.duration._

/**
 * A configured markdown parser.
 */
class Reader(parser: Parser, flexmark: FlexmarkParser) {

  def this() =
    this(
      Parboiled.createParser[ParserWithDirectives, AnyRef](
        classOf[ParserWithDirectives],
        Extensions.ALL ^ Extensions.HARDWRAPS /* disable hard wraps, see #31 */ : java.lang.Integer,
        2.seconds.toMillis: java.lang.Long,
        Parser.DefaultParseRunnerProvider,
        PegDownPlugins.NONE),
      FlexmarkOptions.createParser)

  /**
   * Parse markdown text into a pegdown AST.
   */
  def read(text: String): RootNode = {
    val document = flexmark.parse(text + "\n\n")
    val html = FlexmarkOptions.createRenderer.render(document)
    println(html)
    read(text.toArray)
  }

  /**
   * Parse markdown text into a pegdown AST.
   */
  def read(text: Array[Char]): RootNode = parser.parse(prepare(text))

  /**
   * Add two trailing newlines to the text.
   */
  def prepare(text: Array[Char]): Array[Char] = text ++ Array('\n', '\n')

}