package org.chtijbug.drools.guvnor.rest;

import ch.lambdaj.function.convert.Converter;
import org.apache.cxf.jaxrs.client.WebClient;
import org.chtijbug.drools.guvnor.GuvnorConnexionConfiguration;
import org.chtijbug.drools.guvnor.rest.model.Asset;
import org.chtijbug.drools.guvnor.rest.model.AssetCategory;
import org.chtijbug.drools.guvnor.rest.model.AssetPropertyType;
import org.chtijbug.drools.guvnor.rest.model.AssetType;
import org.drools.guvnor.server.jaxrs.jaxb.AssetMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static ch.lambdaj.Lambda.convert;
import static org.chtijbug.drools.guvnor.rest.model.AssetPropertyType.STATE;

/**
 * Created by IntelliJ IDEA.
 * Date: 25/03/13
 * Time: 14:33
 * To change this template use File | Settings | File Templates.
 */
class AssetManager {

    private static Logger logger = LoggerFactory.getLogger(AssetManager.class);

    private GuvnorConnexionConfiguration configuration = null;

    public AssetManager(GuvnorConnexionConfiguration configuration) {
        this.configuration = configuration;
    }


    public void changeAssetPropertyValue(String packageName, String assetName, AssetPropertyType assetPropertyType, String propertyValue) {
        GuvnorRestApi guvnorService = configuration.getGuvnorRestApiService();
        //_____ Extract the current version of the asset
        org.drools.guvnor.server.jaxrs.jaxb.Asset asset = guvnorService.getAsset(packageName, assetName);
        //_____ Replace the property value
        org.drools.guvnor.server.jaxrs.jaxb.AssetMetadata assetMetadata = asset.getMetadata();
        if (assetMetadata != null) {
            if (STATE.toString().equals(assetPropertyType.toString())) {
                assetMetadata.setState(propertyValue);
            }
            assetMetadata.setCheckInComment("Updated attribute: " + assetPropertyType);
        }
        if (asset.getDescription() == null) {
            asset.setDescription("Default Description");
        }
        //____ Put the new version of the Asset
        guvnorService.updateAsset(packageName, assetName, asset);
    }

    public void createAsset(String packageName, Asset newAsset, AssetType assetType) throws ChtijbugDroolsRestException {
        GuvnorRestApi guvnorService = configuration.getGuvnorRestApiService();
        org.drools.guvnor.server.jaxrs.jaxb.Asset asset = new org.drools.guvnor.server.jaxrs.jaxb.Asset();
        asset.setTitle(newAsset.getName());
        asset.setDescription(newAsset.getSummary());

        AssetMetadata metadata = new AssetMetadata();
        asset.setMetadata(metadata);

        metadata.setState(newAsset.getStatus());
        metadata.setFormat(assetType.getId());
        String[] categories = new String[]{};
        List<String> listCategories = new ArrayList<String>();
        for (AssetCategory assetCategory : newAsset.getCategories()) {
            listCategories.add(assetCategory.getName());
        }
        categories = listCategories.toArray(categories);
        metadata.setCategories(categories);

        guvnorService.createAsset(packageName, asset);
    }

    public List<Asset> getAllBusinessAssets(final String packageName) {
        List<Asset> result;
        GuvnorRestApi guvnorService = configuration.getGuvnorRestApiService();
        result = convert(guvnorService.getAllAssets(packageName), new AssetConverter(packageName));
        return result;
    }

    public List<Asset> getAllPackagesInGuvnorRepo() {
        GuvnorRestApi guvnorService = configuration.getGuvnorRestApiService();
        return convert(guvnorService.getAllPackages(), new Converter<org.drools.guvnor.server.jaxrs.jaxb.Asset, Asset>() {
            @Override
            public Asset convert(org.drools.guvnor.server.jaxrs.jaxb.Asset entry) {
                return new Asset(entry.getTitle(), entry.getTitle(), "", "");
            }
        });
    }


    public Asset getAsset(final String packageName, String assetName) {
        GuvnorRestApi guvnorService = configuration.getGuvnorRestApiService();
        //_____ Extract the current version of the asset
        org.drools.guvnor.server.jaxrs.jaxb.Asset asset = guvnorService.getAsset(packageName, assetName);
        return convert(asset, new AssetConverter(packageName)).get(0);
    }

    public InputStream getPojoModel(String packageName) {
        String path = this.configuration.getWebappName() + "/org.drools.guvnor.Guvnor/package/" + packageName + "/LATEST/MODEL";
        WebClient client = configuration.webClient();
        Response stream = client.path(path).accept(MediaType.APPLICATION_OCTET_STREAM_TYPE).get();
        return (InputStream) stream.getEntity();
    }

    public void updateAssetCodeFromXML(String packageName, String assetName, String newCode) throws ChtijbugDroolsRestException {
        GuvnorRestApi guvnorService = configuration.getGuvnorRestApiService();
        guvnorService.updateAssetFromSource(packageName, assetName, newCode);
    }

    public String getAssetCodeInXML(String packageName, String dtName) throws ChtijbugDroolsRestException {
        return configuration.webClient().path(configuration.assetBinaryPath(packageName, dtName)).accept("text/plain").get(String.class);
    }

    protected class AssetConverter implements Converter<org.drools.guvnor.server.jaxrs.jaxb.Asset, Asset> {
        private final String packageName;

        public AssetConverter(String packageName) {
            this.packageName = packageName;
        }
        @Override
        public Asset convert(org.drools.guvnor.server.jaxrs.jaxb.Asset asset) {
            Asset result = new Asset();
            String assetName = asset.getTitle();
            result.setSummary(asset.getDescription());
            result.setName(assetName);
            result.setPackageName(this.packageName);
            result.setType(asset.getMetadata().getFormat());
            result.setStatus(asset.getMetadata().getState());
            result.setVersionNumber(Long.toString(asset.getMetadata().getVersionNumber()));
            for (String category : asset.getMetadata().getCategories()) {
                AssetCategory assetCategory = new AssetCategory();
                assetCategory.setName(category);
                result.addCategory(assetCategory);
            }
            return result;
        }
    }
}
