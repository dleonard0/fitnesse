// Copyright (C) 2003-2009 by Object Mentor, Inc. All rights reserved.
// Released under the terms of the CPL Common Public License version 1.0.
package fitnesse.updates;

import java.util.Properties;

import fitnesse.components.FitNesseTraversalListener;
import fitnesse.wiki.FileSystemPage;
import fitnesse.wiki.WikiPage;

public abstract class PageTraversingUpdate implements FitNesseTraversalListener, Update {
  private Properties properties;
  private WikiPage root;

  public PageTraversingUpdate(UpdaterImplementation updater) {
    properties = updater.getProperties();
    root = updater.getRoot();
  }

  public void doUpdate() throws Exception {
    root.getPageCrawler().traverse(root, this);
    properties.setProperty(getName(), "applied");
  }

  public abstract void processPage(WikiPage currentPage) throws Exception;

  public boolean shouldBeApplied() throws Exception {
    boolean usesFileSystem = root instanceof FileSystemPage;
    boolean hasBeenApplied = properties.getProperty(getName()) != null;

    return usesFileSystem && !hasBeenApplied;
  }

  public String getSearchPattern() throws Exception {
    return ".*";
  }
}
