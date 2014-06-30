package org.chtijbug.drools.guvnor.rest;

import ch.lambdaj.function.convert.Converter;
import org.apache.abdera.Abdera;
import org.apache.abdera.model.Document;
import org.apache.abdera.model.Element;
import org.apache.abdera.model.Entry;
import org.apache.abdera.model.Feed;
import org.apache.abdera.parser.stax.FOMExtensibleElement;
import org.apache.cxf.jaxrs.client.WebClient;
import org.chtijbug.drools.guvnor.GuvnorConnexionConfiguration;
import org.chtijbug.drools.guvnor.rest.model.Asset;
import org.chtijbug.drools.guvnor.rest.model.AssetCategory;
import org.chtijbug.drools.guvnor.rest.model.AssetPropertyType;
import org.chtijbug.drools.guvnor.rest.model.AssetType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import static ch.lambdaj.Lambda.*;
import static java.lang.String.format;

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
        String assetPath = format("%s/rest/packages/%s/assets/%s", configuration.getWebappName(), packageName, assetName);
        //_____ Extract the current version of the asset
        InputStream inputStream = configuration.webClient()
                .path(assetPath)
                .accept(MediaType.APPLICATION_XML)
                .get(InputStream.class);
        //_____ Replace the property value
        String newAssetVersion = null;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(org.drools.guvnor.server.jaxrs.jaxb.Asset.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            org.drools.guvnor.server.jaxrs.jaxb.Asset asset = (org.drools.guvnor.server.jaxrs.jaxb.Asset) unmarshaller.unmarshal(inputStream);

            org.drools.guvnor.server.jaxrs.jaxb.AssetMetadata assetMetadata = asset.getMetadata();
            if (assetMetadata != null) {
                if (AssetPropertyType.STATE.toString().equals(assetPropertyType.toString())) {
                    assetMetadata.setState(propertyValue);
                }
            }
            Marshaller marshaller = jaxbContext.createMarshaller();
            StringWriter stringWriter = new StringWriter();
            marshaller.marshal(asset, stringWriter);
            newAssetVersion = stringWriter.toString();
        } catch (Exception e) {
            //____ if an exception occurs, return an empty String (No support at the moment)
            newAssetVersion = null;
        }
        if (newAssetVersion == null) {
            return;
        }
        //____ Put the new version of the Asset
        configuration.webClient()
                .path(assetPath)
                .type(MediaType.APPLICATION_XML)
                .put(newAssetVersion);

    }

    public org.drools.guvnor.server.jaxrs.providers.atom.Entry createAsset(String packageName, Asset asset, AssetType assetType) throws ChtijbugDroolsRestException {
        String assetPath = format("%s/rest/packages/%s/assets", configuration.getWebappName(), packageName);
        org.drools.guvnor.server.jaxrs.providers.atom.Entry entry = new org.drools.guvnor.server.jaxrs.providers.atom.Entry();
        entry.setTitle(asset.getName());
        entry.setSummary(asset.getSummary());

        org.drools.guvnor.server.jaxrs.jaxb.AtomAssetMetadata atomAssetMetadata = new org.drools.guvnor.server.jaxrs.jaxb.AtomAssetMetadata();
        entry.setAnyOtherJAXBObject(atomAssetMetadata);
        atomAssetMetadata.setState(asset.getStatus());
        atomAssetMetadata.setFormat(assetType.getId());
        String[] categories = new String[]{};
        List<String> listCategories = new ArrayList<String>();
        for (AssetCategory assetCategory : asset.getCategories()) {
            listCategories.add(assetCategory.getName());
        }
        categories = listCategories.toArray(categories);
        atomAssetMetadata.setCategories(categories);
        String newAssetVersion = null;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(org.drools.guvnor.server.jaxrs.providers.atom.Entry.class, org.drools.guvnor.server.jaxrs.jaxb.AtomAssetMetadata.class, org.drools.guvnor.server.jaxrs.jaxb.Categories.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            StringWriter stringWriter = new StringWriter();
            marshaller.marshal(entry, stringWriter);
            newAssetVersion = stringWriter.toString();

            org.drools.guvnor.server.jaxrs.providers.atom.Entry output = configuration.webClient()
                    .path(assetPath)
                    .type(MediaType.APPLICATION_ATOM_XML)
                    .accept(MediaType.APPLICATION_ATOM_XML)
                    .post(newAssetVersion, org.drools.guvnor.server.jaxrs.providers.atom.Entry.class);
            return output;
        } catch (Exception e) {
            throw new ChtijbugDroolsRestException(e);
        }
    }

    public List<Asset> getAllBusinessAssets(final String packageName) {
        List<Asset> result;
        InputStream inputStream = configuration.webClient()
                .path(format("%s/rest/packages/%s/assets", configuration.getWebappName(), packageName))
                .accept(MediaType.APPLICATION_ATOM_XML)
                .get(InputStream.class);
        Document<Element> atomDocument = Abdera.getInstance().getParser().parse(inputStream);
        Feed feed = (Feed) atomDocument.getRoot();
        final XPath xPath = XPathFactory.newInstance().newXPath();
        result = convert(feed.getEntries(), new Converter<Entry, Asset>() {
            @Override
            public Asset convert(Entry entry) {
                Asset asset = new Asset();
                String assetName = entry.getTitle();
                asset.setSummary(entry.getSummary());
                asset.setName(assetName);
                asset.setPackageName(packageName);
                Element metadata = entry.getExtension(QName.valueOf("metadata"));


                String metadataContent = ((FOMExtensibleElement) metadata).toFormattedString();
                String format = null;
                String status = null;
                String versionNumber = null;
                try {

                    format = xPath.evaluate("/metadata/format/value", new InputSource(new StringReader(metadataContent)));
                    asset.setType(format);
                    status = xPath.evaluate("/metadata/state/value", new InputSource(new StringReader(metadataContent)));
                    asset.setStatus(status);
                    versionNumber = xPath.evaluate("/metadata/state/value", new InputSource(new StringReader(metadataContent)));
                    asset.setVersionNumber(versionNumber);
                    NodeList nodeList = (NodeList) xPath.evaluate("//categories/value", new InputSource(new StringReader(metadataContent)), XPathConstants.NODESET);
                    List<AssetCategory> assetCategoryList = new ArrayList<AssetCategory>();
                    for (int i = 0; i < nodeList.getLength(); i++) {
                        Node node = nodeList.item(i);
                        String categorieName = node.getFirstChild().getNodeValue();
                        AssetCategory assetCategory = new AssetCategory();
                        assetCategory.setName(categorieName);
                        assetCategoryList.add(assetCategory);
                    }
                    asset.setCategories(assetCategoryList);
                } catch (XPathExpressionException e) {
                    //____ Let the null value by default
                }
                return asset;
            }
        });
        return result;
    }

    public List<Asset> getAllPackagesInGuvnorRepo() {
        List<Asset> result;
        InputStream inputStream = configuration.webClient()
                .path(format("%s/rest/packages", configuration.getWebappName()))
                .accept(MediaType.APPLICATION_ATOM_XML)
                .get(InputStream.class);
        Document<Element> atomDocument = Abdera.getInstance().getParser().parse(inputStream);
        Feed feed = (Feed) atomDocument.getRoot();
        final XPath xPath = XPathFactory.newInstance().newXPath();
        result = convert(feed.getEntries(), new Converter<Entry, Asset>() {
            @Override
            public Asset convert(Entry entry) {
                String assetName = entry.getTitle();

                return new Asset(assetName, assetName, "", "");
            }
        });
        return result;
    }


    public Integer getAssetVersion(String packageName, String assetName) {
        InputStream inputStream = configuration.webClient()
                .path(configuration.assetVersionPath(packageName, assetName))
                .accept(MediaType.APPLICATION_ATOM_XML)
                .get(InputStream.class);

        Document<Element> atomDocument = Abdera.getInstance().getParser().parse(inputStream);
        Feed feed = (Feed) atomDocument.getRoot();
        List<Integer> allVersions = convert(feed.getEntries(), new Converter<Entry, Integer>() {
            public Integer convert(Entry from) {
                return Integer.valueOf(from.getTitle());
            }
        });
        return selectMax(allVersions, on(Integer.class));
    }

    public InputStream getPojoModel(String packageName) {
        String path = this.configuration.getWebappName() + "/org.drools.guvnor.Guvnor/package/" + packageName + "/LATEST/MODEL";
        WebClient client = configuration.webClient();
        Response stream = client.path(path).accept(MediaType.APPLICATION_OCTET_STREAM_TYPE).get();
        InputStream inputStream = (InputStream) stream.getEntity();
        return inputStream;
    }

    public void updateAssetCodeFromXML(String packageName, String assetName, String newCode) throws ChtijbugDroolsRestException {
        WebClient client = configuration.webClient();
        configuration.noTimeout(client);
        client.path(configuration.assetBinaryPath(packageName, assetName)).accept("application/xml").put(newCode);

    }

    public String getAssetCodeInXML(String packageName, String dtName) throws ChtijbugDroolsRestException {
        String content = configuration.webClient().path(configuration.assetBinaryPath(packageName, dtName)).accept("text/plain").get(String.class);
        return content;
    }
}
