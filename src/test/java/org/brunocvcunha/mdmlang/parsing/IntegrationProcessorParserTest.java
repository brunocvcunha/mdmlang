/**
 * Copyright (C) 2015 Bruno Candido Volpato da Cunha (brunocvcunha@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.brunocvcunha.mdmlang.parsing;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.brunocvcunha.mdmlang.processor.MDMProcessorContext;
import org.junit.Test;

/**
 * Test that parses remove chars characters
 * 
 * @author Bruno Candido Volpato da Cunha
 *
 */
public class IntegrationProcessorParserTest {

  @Test
  public void trimParseSimple() throws IOException {
    String testMapping =
        "itemCode:\n" + 
            "   remove 3 chars from right of it-codigo\n" +
            "   remove 4 left chars from it-codigo\n" + 
            "   trim it-codigo\n" + 
            "   substring 0 to 2 from it-codigo\n" +
            "   return it-codigo\n";

    System.out.println(testMapping);
    
    MDMProcessorContext ctx =
        MDMProcessorContext.buildContext(new ByteArrayInputStream(testMapping.getBytes()));

    Map<String, Object> values = new HashMap<>();
    values.put("it-codigo", "Item Code 123");

    Map<String, Object> golden = ctx.process(values);

    assertEquals("Co", golden.get("itemCode"));

  }
  
  @Test
  public void trimParseThen() throws IOException {
    String testMapping =
        "familyCode:\n" + 
            "   trim fm-codigo and remove 1 right char from fm-codigo then return fm-codigo\n" ;

    System.out.println(testMapping);
    
    MDMProcessorContext ctx =
        MDMProcessorContext.buildContext(new ByteArrayInputStream(testMapping.getBytes()));

    Map<String, Object> values = new HashMap<>();
    values.put("fm-codigo", " Family ");

    Map<String, Object> golden = ctx.process(values);

    assertEquals("Famil", golden.get("familyCode"));

  }
}
