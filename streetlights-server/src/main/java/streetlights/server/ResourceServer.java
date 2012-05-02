/*
 * Copyright (c) 2004-2012.  NMCB B.V.  All Rights Reserved.
 */

package streetlights.server;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.bio.SocketConnector;
import org.eclipse.jetty.webapp.WebAppContext;
import streetlights.service.web.RoadProvider;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Marco Borst
 * @since 04/03/12
 */
public class ResourceServer implements Runnable
{
  public static final int SERVER_PORT = 8666;
  public static final int SERVER_CONNECTION_TIMEOUT = 1000 * 60 * 60;
  public static final int SERVER_CONNECTION_LINGER_DISABLED = -1;

  private ExecutorService executor = Executors.newSingleThreadExecutor();

  private Server delegate;

  private String home;

  public final static void main(String[] args)
  {
    new ResourceServer().start();
  }

  public void run()
  {
    try
    {
      initServerDelegate();
      initSocketConnector();
      initApplication();
      delegate.start();
      delegate.join();
    }
    catch (Exception e)
    {
      e.printStackTrace();
      System.exit(-1);
    }
  }

  public void start()
  {
    executor.submit(this);
  }

  public void stop()
  {
    try
    {
      delegate.stop();
    }
    catch (Exception e)
    {
      throw new RuntimeException("Unable to stop server delegate", e);
    }
  }

  private void initSocketConnector()
  {
    SocketConnector connector = new SocketConnector();
    connector.setMaxIdleTime(SERVER_CONNECTION_TIMEOUT);
    connector.setSoLingerTime(SERVER_CONNECTION_LINGER_DISABLED);
    connector.setPort(SERVER_PORT);
    delegate.setConnectors(new Connector[]{connector});
  }

  private void initApplication()
  {
    WebAppContext application = new WebAppContext();
    application.setServer(delegate);
    application.setContextPath("/");
    application.setWar(home + "/src/main/webapp");
    delegate.setHandler(application);
  }

  protected void initServerDelegate()
  {
    delegate = new Server();
    try
    {
      home = new File(RoadProvider.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile().getParent();
      System.setProperty("jetty.home", home);
    }
    catch (Exception e)
    {
      throw new RuntimeException("Unable to determine server home", e);
    }
  }
}
