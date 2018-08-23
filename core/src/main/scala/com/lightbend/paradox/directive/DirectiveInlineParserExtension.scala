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

import com.vladsch.flexmark.parser.InlineParser
import com.vladsch.flexmark.parser.InlineParserExtension
import com.vladsch.flexmark.parser.InlineParserExtensionFactory
import com.vladsch.flexmark.util.sequence.BasedSequence

class DirectiveInlineParserExtension(inlineParser: InlineParser) extends InlineParserExtension {
  override def finalizeDocument(inlineParser: InlineParser): Unit = {}
  override def finalizeBlock(inlineParser: InlineParser): Unit = {}

  override def parse(inlineParser: InlineParser): Boolean = {
    Option(inlineParser.matcher(DirectiveInline)) match {
      case None => false
      case Some(matcher) =>
        val name = matcher.group(1)
        val label = matcher.group(2)
        val source = matcher.group(3)
        println("name=" + name)
        println("label=" + label)
        println("source=" + source)
        /*
        DirectiveAttributes attributes = (DirectiveAttributes) pop();
        DirectiveNode.Source source = (DirectiveNode.Source) pop();
        Node labelChild = popAsNode();
        String label = extractLabelText(labelChild);
        String name = popAsString();
        return new DirectiveNode(DirectiveNode.Format.Inline, name, label, source, attributes, labelChild);
        */
        val directive = DirectiveNode.inlineNode(
          name,
          label,
          source,
          Map.empty
        )
        inlineParser.flushTextNode()
        inlineParser.getBlock().appendChild(directive)
        true
    }
    /*
        if (inlineParser.peek(1) == '%' && (inlineParser.peek(2) == ' ' || inlineParser.peek(2) == '\t')) {
            BasedSequence input = inlineParser.getInput();
            Matcher matcher = inlineParser.matcher(parsing.MACRO_TAG);
            if (matcher != null) {
                BasedSequence tag = input.subSequence(matcher.start(), matcher.end());
                BasedSequence tagName = input.subSequence(matcher.start(1), matcher.end(1));
                BasedSequence parameters = input.subSequence(matcher.end(1), matcher.end() - 2).trim();
                JekyllTag macro = new JekyllTag(tag.subSequence(0, 2), tagName, parameters, tag.endSequence(2));
                macro.setCharsFromContent();

                //noinspection EqualsBetweenInconvertibleTypes
                if (!listIncludesOnly || tagName.equals(JekyllTagBlockParser.INCLUDE_TAG)) {
                    List<JekyllTag> tagList = JekyllTagExtension.TAG_LIST.getFrom(inlineParser.getDocument());
                    tagList.add(macro);
                }

                inlineParser.flushTextNode();
                inlineParser.getBlock().appendChild(macro);
                return true;
            }
        }
        */
  }
}

object DirectiveInlineParserExtension {
  class Factory extends InlineParserExtensionFactory {
    override def getAfterDependents(): java.util.Set[Class[_ <: InlineParserExtensionFactory]] =
      null

    override def getCharacters(): CharSequence =
      DirectiveMarkerString

    override def getBeforeDependents(): java.util.Set[Class[_ <: InlineParserExtensionFactory]] =
      null

    override def create(inlineParser: InlineParser): InlineParserExtension =
      new DirectiveInlineParserExtension(inlineParser);

    override def affectsGlobalScope(): Boolean =
      false
  }
}