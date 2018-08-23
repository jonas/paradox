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

import com.vladsch.flexmark.ext.anchorlink.AnchorLinkExtension
import com.vladsch.flexmark.html.HtmlRenderer
import com.vladsch.flexmark.parser.Parser
import com.vladsch.flexmark.profiles.pegdown.Extensions
import com.vladsch.flexmark.profiles.pegdown.PegdownOptionsAdapter
import com.vladsch.flexmark.util.options.DataHolder

import com.lightbend.paradox.directive.DirectiveExtension

object FlexmarkOptions {
  final val PegdownOptions = PegdownOptionsAdapter.flexmarkOptions(
    true,
    Extensions.ALL ^ Extensions.HARDWRAPS /* disable hard wraps, see #31 */
  )

  def createOptions = {
    PegdownOptions
      .toMutable()
      .set(AnchorLinkExtension.ANCHORLINKS_ANCHOR_CLASS, "anchor")
      .set(AnchorLinkExtension.ANCHORLINKS_TEXT_PREFIX, """<span class="anchor-link"></span>""")
      .set(Parser.EXTENSIONS, java.util.Arrays.asList(DirectiveExtension.create()))
  }

  def createParser = Parser.builder(createOptions).build()
  def createRenderer = HtmlRenderer.builder(createOptions).build()

  // use the PARSER to parse and RENDERER to render with pegdown compatibility
}

