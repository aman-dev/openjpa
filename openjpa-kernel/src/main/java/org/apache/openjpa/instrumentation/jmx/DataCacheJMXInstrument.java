/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.    
 */
package org.apache.openjpa.instrumentation.jmx;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.AttributeNotFoundException;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.ObjectName;
import javax.management.DynamicMBean;
import javax.management.ReflectionException;

import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.datacache.DataCache;
import org.apache.openjpa.datacache.DataCacheManager;
import org.apache.openjpa.instrumentation.AbstractDataCacheInstrument;
import org.apache.openjpa.lib.conf.Configurations;
import org.apache.openjpa.lib.instrumentation.InstrumentationLevel;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.lib.util.Options;
import org.apache.openjpa.util.UserException;

/**
 * A JMX-specific instrument for the data cache
 */
public class DataCacheJMXInstrument extends AbstractDataCacheInstrument 
    implements JMXInstrument, DataCacheJMXInstrumentMBean {
    
    private static Localizer _loc = Localizer.forPackage(DataCacheJMXInstrument.class);

    private static final String MBEAN_TYPE = "DataCache";
    private ObjectName _objName = null;
    
    @Override
    public String getName() {
        return MBEAN_TYPE;
    }

    @Override
    public InstrumentationLevel getLevel() {
        return InstrumentationLevel.FACTORY;
    }

    @Override
    public void initialize() {
        Options opts = new Options();
        if (getOptions() != null) {
            opts = Configurations.parseProperties(getOptions());
        }
        
        String cacheName = opts.getProperty("name",null);
        OpenJPAConfiguration conf = (OpenJPAConfiguration)getProvider().getConfiguration();
        DataCacheManager dcm = conf.getDataCacheManagerInstance();
        DataCache dc = null;
        if (cacheName == null || cacheName.trim().length() == 0) {
            dc = dcm.getSystemDataCache();
        } else {
            dc = dcm.getDataCache(cacheName);
        }
        if (dc == null) {
            throw new UserException(_loc.get("data-cache-not-found"));
        }
        
        setDataCache(dc);
        setConfigId(conf.getId());
        setContextRef(Integer.toString(System.identityHashCode(getContext())));
    }

    public ObjectName getObjectName() {
        if (_objName != null) {
            return _objName;
        }
        
        try {
            _objName = JMXProvider.createObjectName(this, null);
            return _objName;
        } catch (Throwable t) {
            throw new UserException(_loc.get("unable-to-create-object-name", getName()), t);
        }
    }

    public void start() {
        getProvider().startInstrument(this);
    }

    public void stop() {
        getProvider().stopInstrument(this);
    }

    public Object getAttribute(String attribute)
        throws AttributeNotFoundException, MBeanException, ReflectionException {
        // TODO Auto-generated method stub
        return null;
    }

    public AttributeList getAttributes(String[] attributes) {
        // TODO Auto-generated method stub
        return null;
    }

    public MBeanInfo getMBeanInfo() {
        // TODO Auto-generated method stub
        return null;
    }

    public Object invoke(String actionName, Object[] params, String[] signature)
        throws MBeanException, ReflectionException {
        // TODO Auto-generated method stub
        return null;
    }

    public void setAttribute(Attribute attribute)
        throws AttributeNotFoundException, InvalidAttributeValueException,
        MBeanException, ReflectionException {
        // TODO Auto-generated method stub
        
    }

    public AttributeList setAttributes(AttributeList attributes) {
        // TODO Auto-generated method stub
        return null;
    }
}
