/*
 * Proof of concept depicting a restful specification of access to
 * infrastructure related data graphs.
 *
 * Copyright (C) 2012 N.M.C. Borst
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 */

package streetlights.server;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.bio.SocketConnector;
import org.eclipse.jetty.webapp.WebAppContext;
import streetlights.service.web.RoadXmlProvider;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Marco Borst
 * @since 04/03/12
 */
public class ResourceServer implements Runnable
{
    public static final ResourceServer instance = new ResourceServer();

    public static final int SERVER_PORT = 8666;
    public static final int SERVER_CONNECTION_TIMEOUT = 1000 * 60 * 60;
    public static final int SERVER_CONNECTION_LINGER_DISABLED = -1;

    private ExecutorService executor = Executors.newSingleThreadExecutor();

    private Server delegate;

    private String home;

    private ResourceServer()
    {
        // singleton constructor;
    }

    public static void main(String[] args)
    {
        if (args.length != 0)
        {
            // TODO remove or filter development environment specific location
            String uri = args[0];
            new Database().load(uri);
        }
        instance.start();
    }

    public static ResourceServer getInstance()
    {
        return instance;
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
        // TODO breaks integration test when run with 'mvn verify' from within modules directory, should deploy the war artifact
        application.setWar(home + "/src/main/webapp");
        delegate.setHandler(application);
    }

    protected void initServerDelegate()
    {
        delegate = new Server();
        try
        {
            home = new File(RoadXmlProvider.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile().getParent();
            System.setProperty("jetty.home", home);
        }
        catch (Exception e)
        {
            throw new RuntimeException("Unable to determine server home", e);
        }
    }
}
