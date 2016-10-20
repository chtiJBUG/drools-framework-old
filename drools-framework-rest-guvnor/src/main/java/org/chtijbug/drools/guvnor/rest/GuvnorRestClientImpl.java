package org.chtijbug.drools.guvnor.rest;

import com.google.common.base.Throwables;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.chtijbug.drools.common.jaxb.JAXBContextUtils;
import org.chtijbug.drools.guvnor.GuvnorConnexionConfiguration;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import static java.lang.String.valueOf;

public class GuvnorRestClientImpl implements GuvnorRestClient {

    private final CloseableHttpClient httpClient;
    private final HttpHost targetHost;
    private final HttpClientContext context;

    public GuvnorRestClientImpl(GuvnorConnexionConfiguration conf) {
        RequestConfig defaultRequestConfig = RequestConfig.custom()
                .setConnectTimeout(0)
                .setSocketTimeout(0)
                .setConnectionRequestTimeout(0)
                .build();
        this.httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(defaultRequestConfig)
                .build();
        try {
            URI uri = new URI(conf.getHostname());
            this.targetHost = new HttpHost(uri.getHost(), uri.getPort(), uri.getScheme());

            CredentialsProvider credsProvider = new BasicCredentialsProvider();
            credsProvider.setCredentials(
                    new AuthScope(this.targetHost.getHostName(), this.targetHost.getPort()),
                    new UsernamePasswordCredentials(conf.getUsername(), conf.getPassword()));

            // Create AuthCache instance
            AuthCache authCache = new BasicAuthCache();
            // Generate BASIC scheme object and add it to the local auth cache
            BasicScheme basicAuth = new BasicScheme();
            authCache.put(this.targetHost, basicAuth);

            // Add AuthCache to the execution context
            this.context = HttpClientContext.create();
            this.context.setCredentialsProvider(credsProvider);
            this.context.setAuthCache(authCache);
        } catch (URISyntaxException e) {
            throw Throwables.propagate(e);
        }
    }

    @Override
    public InputStream get(String assetPath, String mediaType) {
        try {
            HttpGet httpGet = new HttpGet(assetPath);
            httpGet.addHeader("accept", mediaType);
            CloseableHttpResponse response = this.httpClient.execute(targetHost, httpGet, context);
            if (!valueOf(response.getStatusLine().getStatusCode()).startsWith("2"))
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
            return response.getEntity().getContent();
        } catch (IOException e) {
            throw new RuntimeException("Failed ", e);
        }
    }

    @Override
    public void put(String assetPath, String contentType, String newAssetVersion) {
        try {
            HttpPut httpPut = new HttpPut(assetPath);
            httpPut.addHeader("Content-Type", contentType);
            httpPut.setEntity(new StringEntity(newAssetVersion));
            CloseableHttpResponse response = this.httpClient.execute(targetHost, httpPut, context);
            if (!valueOf(response.getStatusLine().getStatusCode()).startsWith("2"))
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
        } catch (IOException e) {
            throw new RuntimeException("Failed ", e);
        }
    }

    @Override
    public <T> T post(String assetPath, String contentType, String acceptedType, Class<T> expectedClass, String newAssetVersion) {
        try {
            HttpPost httpPost = new HttpPost(assetPath);
            httpPost.addHeader("Content-Type", contentType);
            httpPost.addHeader("accept", acceptedType);
            httpPost.setEntity(new StringEntity(newAssetVersion));
            CloseableHttpResponse response = this.httpClient.execute(targetHost, httpPost, context);
            if (!valueOf(response.getStatusLine().getStatusCode()).startsWith("2"))
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
            return JAXBContextUtils.unmarshallObject(expectedClass, response.getEntity().getContent());
        } catch (IOException | JAXBException e) {
            throw new RuntimeException("Failed ", e);
        }
    }

    @Override
    public void post(String path, String contentType, String object) {
        try {
            HttpPost httpPost = new HttpPost(path);
            httpPost.addHeader("Content-Type", contentType);
            httpPost.setEntity(new StringEntity(object));
            CloseableHttpResponse response = this.httpClient.execute(targetHost, httpPost, context);
            if (!valueOf(response.getStatusLine().getStatusCode()).startsWith("2"))
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
        } catch (IOException e) {
            throw new RuntimeException("Failed ", e);
        }
    }

    @Override
    public <T> T get(String path, String mediaType, Class<T> expectedClass) {
        try {
            HttpGet httpGet = new HttpGet(path);
            httpGet.addHeader("accept", mediaType);
            CloseableHttpResponse response = this.httpClient.execute(targetHost, httpGet, context);
            if (response.getStatusLine().getStatusCode() != 200)
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
            return JAXBContextUtils.unmarshallObject(expectedClass, response.getEntity().getContent());
        } catch (IOException | JAXBException e) {
            throw new RuntimeException("Failed ", e);
        }
    }
}
