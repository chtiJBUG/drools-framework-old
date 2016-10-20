package org.chtijbug.drools.guvnor.rest;


import java.io.InputStream;

public interface GuvnorRestClient {
    InputStream get(String assetPath, String mediaType);

    void put(String assetPath, String applicationXml, String newAssetVersion);

    <T> T post(String assetPath, String contentType, String acceptedType, Class<T> entryClass, String newAssetVersion);

    void post(String path, String applicationXml, String object);

    <T> T get(String path, String applicationAtomXml, Class<T> expectedClass);
}
