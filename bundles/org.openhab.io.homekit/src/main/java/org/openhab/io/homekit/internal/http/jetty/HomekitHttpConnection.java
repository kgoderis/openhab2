package org.openhab.io.homekit.internal.http.jetty;

import org.eclipse.jetty.http.HttpCompliance;
import org.eclipse.jetty.http.HttpGenerator;
import org.eclipse.jetty.io.Connection;
import org.eclipse.jetty.io.EndPoint;
import org.eclipse.jetty.server.ConnectionFactory;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HomekitHttpConnection extends HttpConnection {

    protected static final Logger logger = LoggerFactory.getLogger(HomekitHttpConnection.class);

    protected boolean upgradable = true;

    public HomekitHttpConnection(HttpConfiguration config, Connector connector, EndPoint endPoint,
            HttpCompliance compliance, boolean recordComplianceViolations) {
        super(config, connector, endPoint, compliance, recordComplianceViolations);
    }

    @Override
    protected HttpGenerator newHttpGenerator() {
        return new HomekitHttpGenerator();
    }

    @Override
    public void onCompleted() {
        Connection newConnection = null;

        if (upgradable && getHttpChannel().getRequest().getAttribute("HomekitEncryptionEnabled") != null) {
            logger.debug("Upgrading {} to a secured Connection", this.toString());
            ConnectionFactory factory = getConnector().getConnectionFactory("HOMEKIT");
            newConnection = factory.newConnection(getConnector(), getEndPoint());
        }

        super.onCompleted();

        if (newConnection != null) {
            getEndPoint().upgrade(newConnection);
        }

    }

    public void setUpgradable(boolean b) {
        this.upgradable = b;
    }
}