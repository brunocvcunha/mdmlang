package com.totvs.mdmlang.parsing;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.totvs.mdmlang.processor.MDMProcessorContext;

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