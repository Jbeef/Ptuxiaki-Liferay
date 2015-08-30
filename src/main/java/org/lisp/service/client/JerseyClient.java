package org.lisp.service.client;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import javax.ws.rs.core.MediaType;
import org.apache.commons.io.IOUtils;

/**
 * Jersey REST client generated for REST resource:RestService [service]<br>
 * USAGE:
 * <pre>
 *        JerseyClient client = new JerseyClient();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author jbeef
 */
public class JerseyClient {

    private WebResource webResource;
    private Client client;
    private static final String BASE_URI = "http://192.168.1.200:8080/mavenproject1/resources";

    public JerseyClient() {
        ClientConfig config = new DefaultClientConfig();
        client = Client.create(config);
        webResource = client.resource(BASE_URI).path("service");
    }

    public void putJson(Object requestEntity) throws UniformInterfaceException {
        webResource.type(MediaType.APPLICATION_JSON).put(requestEntity);
    }

    public ClientResponse executeFile(InputStream inputstream) throws UniformInterfaceException, IOException {
        return webResource.path("file").type(MediaType.APPLICATION_OCTET_STREAM).post(ClientResponse.class, inputstream);
    }

    /*

     public String executeCommand(String package, String command) throws com.sun.jersey.api.client.UniformInterfaceException {
     return webResource.path(java.text.MessageFormat.format("execute/{0}/{1}", new Object[]{(ERROR)}));
     (ERROR);
     }

     */
    public String getHomePackage() throws UniformInterfaceException {
        WebResource resource = webResource;
        resource = resource.path("homePackage");
        return resource.accept(MediaType.APPLICATION_JSON).get(String.class);
    }

    public void close() {
        client.destroy();
    }

}
