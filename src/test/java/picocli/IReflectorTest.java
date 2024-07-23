/*
   Copyright 2017 Remko Popma

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package picocli;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Field;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import picocli.CommandLine.Command;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Model.IReflector;
import picocli.CommandLine.Model.OptionSpec;
import picocli.CommandLine.Option;

/**
 * Tests for {@link IReflector}.
 */
public class IReflectorTest {

    @Before public void setUp() { System.clearProperty("picocli.trace"); }
    @After public void tearDown() { System.clearProperty("picocli.trace"); }

    @Command(name = "reflector-test")
    static class ReflectorTest {
        @Option(names = "-a") int a;
        @Option(names = "-b") long b;

    }

    @Test
    public void testFieldReflector() throws Exception {
        ReflectorTest command = new ReflectorTest();
        CommandLine commandLine = new CommandLine(command);
        CommandSpec spec = commandLine.getCommandSpec();
        for (OptionSpec option: spec.options()) {
        	assertTrue(option.setter() instanceof IReflector);
        	assertTrue(option.getter() instanceof IReflector);
        	
        	assertTrue(((IReflector) option.setter()).getAnnotatedElement() instanceof Field);
        	assertTrue(((IReflector) option.getter()).getAnnotatedElement() instanceof Field);
        	        	
        	assertEquals(ReflectorTest.class, ((Field) ((IReflector) option.setter()).getAnnotatedElement()).getDeclaringClass());
        	assertEquals(ReflectorTest.class, ((Field) ((IReflector) option.getter()).getAnnotatedElement()).getDeclaringClass());        	
        }
    }
}
