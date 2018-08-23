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

package com.lightbend.paradox.directive;

import com.vladsch.flexmark.ast.Node
import com.vladsch.flexmark.ast.NodeIterable
import com.vladsch.flexmark.util.collection.iteration.ReversiblePeekingIterable

sealed trait Format
object Format {
  case object Inline extends Format
  case object LeafBlock extends Format
  case object ContainerBlock extends Format
}

class DirectiveNode(
    format:     Format,
    name:       String,
    label:      String,
    source:     String,
    attributes: Map[String, String],
    contents:   String,
    children:   List[Node]) extends Node {

  /*
    public ReversiblePeekingIterable[Node] getChildren() {
        return new NodeIterable(child, child, false);
    }
    */

  override def getSegments() = Node.EMPTY_SEGMENTS
}

object DirectiveNode {
  def inlineNode(
    name:       String,
    label:      String,
    source:     String,
    attributes: Map[String, String]
  ) = new DirectiveNode(Format.Inline, name, label, source, attributes, label, Nil)
}