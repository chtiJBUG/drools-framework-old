package org.chtijbug.drools.guvnor.rest;


import org.drools.guvnor.server.jaxrs.jaxb.Asset;
import retrofit.http.*;

import java.util.List;

public interface GuvnorRestApi {

    @GET("/rest/packages")
    @Headers({"Accept: application/xml"})
    List<Package> getAllPackages();

    @GET("/rest/packages/{packageName}/assets")
    @Headers({"Accept: application/xml"})
    List<Asset> getAllAssets(@Path("packageName") String packageName);

    @GET("/rest/packages/{packageName}/assets/{assetName}")
    @Headers({"Accept: application/xml"})
    Asset getAsset(@Path("packageName") String packageName, @Path("assetName") String assetName);

    @PUT("/rest/packages/{packageName}/assets/{assetName}")
    void updateAsset(@Path("packageName") String packageName, @Path("assetName") String assetName, @Body Asset asset);

    @PUT("/rest/packages/{packageName}/assets/{assetName}")
    @Headers({"Accept: application/xml"})
    void createAsset(@Path("packageName") String packageName, @Body Asset asset);

    @PUT("/rest/packages/{packageName}/assets/{assetName}/binary")
    @Headers({"Accept: application/xml"})
    void updateAssetFromSource(@Path("packageName") String packageName, @Path("assetName") String assetName, @Body String content);


}
