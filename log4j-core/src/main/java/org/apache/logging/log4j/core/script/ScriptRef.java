/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache license, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the license for the specific language governing permissions and
 * limitations under the license.
 */
package org.apache.logging.log4j.core.script;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.Node;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginConfiguration;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.config.plugins.PluginValue;
import org.apache.logging.log4j.status.StatusLogger;

/**
 * Contains a reference to a script defined elsewhere in the configuration.
 */
@Plugin(name = "ScriptRef", category = Node.CATEGORY, printObject = true)
public class ScriptRef extends AbstractScript {

    private static final Logger logger = StatusLogger.getLogger();
    private final ScriptManager scriptManager;

    public ScriptRef(String name, ScriptManager scriptManager) {
        super(name, null, null);
        this.scriptManager = scriptManager;
    }

    @Override
    public String getLanguage() {
        AbstractScript script = this.scriptManager.getScript(getName());
        return script != null ? script.getLanguage() : null;
    }


    @Override
    public String getScriptText() {
        AbstractScript script = this.scriptManager.getScript(getName());
        return script != null ? script.getScriptText() : null;
    }

    @PluginFactory
    public static ScriptRef createReference(
            // @formatter:off
            @PluginAttribute("ref") final String name,
            @PluginConfiguration Configuration configuration) {
            // @formatter:on
        if (name == null) {
            logger.error("No script name provided");
            return null;
        }
        return new ScriptRef(name, configuration.getScriptManager());

    }

    @Override
    public String toString() {
        return "ref=" + getName();
    }
}
