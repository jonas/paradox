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

package com.lightbend.paradox.directive

import com.vladsch.flexmark.Extension
import com.vladsch.flexmark.html.HtmlRenderer
import com.vladsch.flexmark.parser.Parser
import com.vladsch.flexmark.util.options.MutableDataHolder

import java.util.regex.Pattern

class DirectiveExtension extends Parser.ParserExtension with HtmlRenderer.HtmlRendererExtension {

  override def rendererOptions(options: MutableDataHolder): Unit = {}

  override def parserOptions(options: MutableDataHolder): Unit = {}

  override def extend(parserBuilder: Parser.Builder): Unit = {
    parserBuilder.customInlineParserExtensionFactory(new DirectiveInlineParserExtension.Factory())
  }

  override def extend(rendererBuilder: HtmlRenderer.Builder, rendererType: String): Unit = {
    /*
    if ("HTML".equals(rendererType)) {
      rendererBuilder.nodeRendererFactory(new DirectiveNodeRenderer.Factory())
    }
    */
  }
}

object DirectiveExtension {
  def create(): Extension =
    new DirectiveExtension()
}