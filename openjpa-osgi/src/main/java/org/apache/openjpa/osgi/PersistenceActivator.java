/**
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.apache.openjpa.osgi;

import java.util.Hashtable;

import javax.persistence.spi.PersistenceProvider;

import org.apache.openjpa.persistence.PersistenceProviderImpl;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;


/**
 * Used to discover/resolve JPA providers in an OSGi environment.
 *
 * @version $Rev$ $Date$
 */
public class PersistenceActivator implements BundleActivator {

    public static final String PERSISTENCE_PROVIDER = PersistenceProvider.class.getName();
    public static final String PERSISTENCE_PROVIDER_ARIES = "javax.persistence.provider";
    public static final String OSGI_PERSISTENCE_PROVIDER = PersistenceProviderImpl.class.getName();
    private static BundleContext ctx = null;

    /* (non-Javadoc)
     * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
     */
    public void start(BundleContext arg0) throws Exception {
        ctx = arg0;
        PersistenceProvider provider = new PersistenceProviderImpl();
        Hashtable<String, String> props = new Hashtable<String, String>();
        props.put(PERSISTENCE_PROVIDER, OSGI_PERSISTENCE_PROVIDER);
        props.put(PERSISTENCE_PROVIDER_ARIES, OSGI_PERSISTENCE_PROVIDER);
        ctx.registerService(PERSISTENCE_PROVIDER, provider, props);
    }

    /* (non-Javadoc)
     * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
     */
    public void stop(BundleContext arg0) throws Exception {
        // TODO Auto-generated method stub

    }

}
