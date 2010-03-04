package org.eclipse.xtext.mwe;

import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.resource.containers.DelegatingIAllContainerAdapter;
import org.eclipse.xtext.resource.containers.IAllContainersState;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class RuntimeResourceSetInitializer {

  @Inject
  private Provider<ResourceSet> resourceSetProvider;
  @Inject
  private ResourceDescriptionsProvider provider;
  @Inject
  private ContainersStateFactory factory;
  @Inject
  private PathTraverser traverser;
  @Inject
  private IResourceServiceProvider.Registry registry;

  public List<String> getClassPathEntries() {
    List<String> pathes = Lists.newArrayList();
    String classPath = System.getProperty("java.class.path");
    String separator = System.getProperty("path.separator");
    String[] strings = classPath.split(separator);
    for (String path : strings) {
      pathes.add(path);
    }
    return pathes;
  }

  protected Multimap<String, URI> getPathToUriMap(List<String> pathes) {
    return traverser.resolvePathes(pathes, new Predicate<URI>() {
      public boolean apply(URI input) {
        return registry.getResourceServiceProvider(input) != null;
      }
    });
  }

  public ResourceSet getInitializedResourceSet(List<String> pathes) {
    ResourceSet resourceSet = resourceSetProvider.get();
    Multimap<String, URI> pathToUriMap = getPathToUriMap(pathes);
    IAllContainersState containersState = factory.getContainersState(pathes,
        pathToUriMap);
    resourceSet.eAdapters().add(
        new DelegatingIAllContainerAdapter(containersState));
    for (URI uri : pathToUriMap.values()) {
      resourceSet.createResource(uri);
    }
    return resourceSet;
  }

  public IResourceDescriptions getDescriptions(ResourceSet resourceSet) {
    return provider.get(resourceSet);
  }

}
