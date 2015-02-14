/*
 * Copyright 2015 the original author or authors.
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
package net.kuujo.copycat.atomic;

import net.kuujo.copycat.atomic.internal.DefaultAsyncLong;
import net.kuujo.copycat.cluster.ClusterConfig;
import net.kuujo.copycat.resource.Resource;
import net.kuujo.copycat.resource.ResourceContext;

import java.util.concurrent.Executor;

/**
 * Asynchronous atomic long.
 *
 * @author <a href="http://github.com/kuujo">Jordan Halterman</a>
 */
public interface AsyncLong extends AsyncLongProxy, Resource<AsyncLong> {

  /**
   * Creates a new asynchronous atomic long with the default cluster configuration.<p>
   *
   * The atomic long will be constructed with the default cluster configuration. The default cluster configuration
   * searches for two resources on the classpath - {@code cluster} and {cluster-defaults} - in that order. Configuration
   * options specified in {@code cluster.conf} will override those in {cluster-defaults.conf}.<p>
   *
   * Additionally, the atomic long will be constructed with an atomic long configuration that searches the classpath for
   * three configuration files - {@code {name}}, {@code atomic}, {@code atomic-defaults}, {@code resource}, and
   * {@code resource-defaults} - in that order. The first resource is a configuration resource with the same name
   * as the atomic long resource. If the resource is namespaced - e.g. `longs.my-long.conf` - then resource
   * configurations will be loaded according to namespaces as well; for example, `longs.conf`.
   *
   * @param name The asynchronous atomic long name.
   * @return The asynchronous atomic long.
   */
  static AsyncLong create(String name) {
    return create(name, new ClusterConfig(String.format("%s-cluster", name)), new AsyncLongConfig(name));
  }

  /**
   * Creates a new asynchronous atomic long with the default cluster configuration.<p>
   *
   * The atomic long will be constructed with the default cluster configuration. The default cluster configuration
   * searches for two resources on the classpath - {@code cluster} and {cluster-defaults} - in that order. Configuration
   * options specified in {@code cluster.conf} will override those in {cluster-defaults.conf}.<p>
   *
   * Additionally, the atomic long will be constructed with an atomic long configuration that searches the classpath for
   * three configuration files - {@code {name}}, {@code atomic}, {@code atomic-defaults}, {@code resource}, and
   * {@code resource-defaults} - in that order. The first resource is a configuration resource with the same name
   * as the atomic long resource. If the resource is namespaced - e.g. `longs.my-long.conf` - then resource
   * configurations will be loaded according to namespaces as well; for example, `longs.conf`.
   *
   * @param name The asynchronous atomic long name.
   * @param executor An executor on which to execute long callbacks.
   * @return The asynchronous atomic long.
   */
  static AsyncLong create(String name, Executor executor) {
    return create(name, new ClusterConfig(String.format("%s-cluster", name)), new AsyncLongConfig(name), executor);
  }

  /**
   * Creates a new asynchronous atomic long with the default atomic long configuration.<p>
   *
   * The atomic long will be constructed with an atomic long configuration that searches the classpath for three
   * configuration files - {@code {name}}, {@code atomic}, {@code atomic-defaults}, {@code resource}, and
   * {@code resource-defaults} - in that order. The first resource is a configuration resource with the same name
   * as the atomic long resource. If the resource is namespaced - e.g. `longs.my-long.conf` - then resource
   * configurations will be loaded according to namespaces as well; for example, `longs.conf`.
   *
   * @param name The asynchronous atomic long name.
   * @param cluster The cluster configuration.
   * @return The asynchronous atomic long.
   */
  static AsyncLong create(String name, ClusterConfig cluster) {
    return create(name, cluster, new AsyncLongConfig(name));
  }

  /**
   * Creates a new asynchronous atomic long with the default atomic long configuration.<p>
   *
   * The atomic long will be constructed with an atomic long configuration that searches the classpath for three
   * configuration files - {@code {name}}, {@code atomic}, {@code atomic-defaults}, {@code resource}, and
   * {@code resource-defaults} - in that order. The first resource is a configuration resource with the same name
   * as the atomic long resource. If the resource is namespaced - e.g. `longs.my-long.conf` - then resource
   * configurations will be loaded according to namespaces as well; for example, `longs.conf`.
   *
   * @param name The asynchronous atomic long name.
   * @param cluster The cluster configuration.
   * @param executor An executor on which to execute long callbacks.
   * @return The asynchronous atomic long.
   */
  static AsyncLong create(String name, ClusterConfig cluster, Executor executor) {
    return create(name, cluster, new AsyncLongConfig(name), executor);
  }

  /**
   * Creates a new asynchronous atomic long.
   *
   * @param name The asynchronous atomic long name.
   * @param cluster The cluster configuration.
   * @param config The atomic long configuration.
   * @return The asynchronous atomic long.
   */
  @SuppressWarnings({"unchecked", "rawtypes"})
  static AsyncLong create(String name, ClusterConfig cluster, AsyncLongConfig config) {
    return new DefaultAsyncLong(new ResourceContext(name, config, cluster));
  }

  /**
   * Creates a new asynchronous atomic long.
   *
   * @param name The asynchronous atomic long name.
   * @param cluster The cluster configuration.
   * @param config The atomic long configuration.
   * @param executor An executor on which to execute long callbacks.
   * @return The asynchronous atomic long.
   */
  @SuppressWarnings({"unchecked", "rawtypes"})
  static AsyncLong create(String name, ClusterConfig cluster, AsyncLongConfig config, Executor executor) {
    return new DefaultAsyncLong(new ResourceContext(name, config, cluster, executor));
  }

}
