package org.chtijbug.kie.rest.backend;

import org.apache.camel.Headers;
import org.drools.guvnor.server.jaxrs.jaxb.*;
import org.drools.guvnor.server.jaxrs.jaxb.Package;
import org.guvnor.common.services.project.model.Project;
import org.guvnor.common.services.project.service.ProjectService;
import org.guvnor.structure.organizationalunit.OrganizationalUnitService;
import org.guvnor.structure.repositories.RepositoryService;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.slf4j.LoggerFactory;
import org.uberfire.io.IOService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Path("/packages")
@Named
@ApplicationScoped
public class PackageResource {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(PackageResource.class);

    @Context
    protected UriInfo uriInfo;

    @Inject
    @Named("ioStrategy")
    private IOService ioService;

    @Inject
    private OrganizationalUnitService organizationalUnitService;

    @Inject
    private RepositoryService repositoryService;

    @Inject
    private ProjectService<? extends Project> projectService;


    @GET
    @Path("{organizationalUNit}/{repositoryName}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Collection<Package> getPackagesAsJAXB( @PathParam("organizationalUNit") String organizationalUNit, @PathParam("repositoryName") String repositoryName) {
        List<Package> ret = new ArrayList<Package>();
        //ModuleIterator iter = rulesRepository.listModules();
       // while (iter.hasNext()) {
            //REVIST: Do not return detailed package info here. Package title and link should be enough.
          //  ret.add(toPackage(iter.next(), uriInfo));
        //}
        return ret;
    }

    @GET
    @Path("{organizationalUNit}/{repositoryName}/{packageName}/assets")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Collection<Asset> getAssetsAsJAXB(
            @PathParam("organizationalUNit") String organizationalUNit, @PathParam("repositoryName") String repositoryName,
            @PathParam("packageName") String packageName,
            @QueryParam("format") List<String> formats) {
        try {
            List<Asset> ret = new ArrayList<Asset>();
            /**
           ModuleItem p = rulesRepository.loadModule(packageName);

            Iterator<AssetItem> iter = null;

            if (formats.isEmpty()){
                //no format specified? Return all assets
                iter = p.getAssets();
            }else{
                //if the format is specified, return only the assets of
                //the specified formats.
                iter = p.listAssetsByFormat(formats);
            }

            while (iter.hasNext()) {
                ret.add(toAsset(iter.next(), uriInfo));
            }
             **/
            return ret;
        } catch (RuntimeException e) {
            throw new WebApplicationException(e);
        }
    }
    @GET
    @Path("{organizationalUNit}/{repositoryName}/{packageName}/assets/{assetName}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})

    public Asset getAssetAsJaxB(
            @PathParam("organizationalUNit") String organizationalUNit, @PathParam("repositoryName") String repositoryName,
            @PathParam("packageName") String packageName, @PathParam("assetName") String assetName) {
       /**
        if (!assetExists(packageName, assetName)) {
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
                    .entity("Asset [" + assetName + "] of package [" + packageName + "] does not exist").build());
        }
        try {
            //Throws RulesRepositoryException if the package or asset does not exist
            AssetItem asset = rulesRepository.loadModule(packageName).loadAsset(assetName);
            return toAsset(asset, uriInfo);
        } catch (RuntimeException e) {
            throw new WebApplicationException(e);
        }
        **/
        return null;
    }
    @PUT
    @Path("{organizationalUNit}/{repositoryName}/{packageName}/assets/{assetName}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public void updateAssetFromJAXB(
            @PathParam("organizationalUNit") String organizationalUNit, @PathParam("repositoryName") String repositoryName,
            @PathParam("packageName") String packageName,
            @PathParam("assetName") String assetName, Asset asset) {
        /**
        try {
            Throws RulesRepositoryException if the package or asset does not exist
            AssetItem ai = rulesRepository.loadModule(packageName).loadAsset(assetName);

            ai.checkout();
            ai.updateTitle(asset.getTitle());
            ai.updateDescription(asset.getDescription());
            ai.updateValid(assetValidator.validate(ai));
            if (asset.getMetadata() != null){
                AssetMetadata assetMetadata = asset.getMetadata();
                if (assetMetadata.getState() != null) {
                    ai.updateState(assetMetadata.getState());
                }
                if (assetMetadata.getCategories()!= null){
                    ai.updateCategoryList(assetMetadata.getCategories());
                }
                ai.updateDisabled(assetMetadata.isDisabled());
            }
            if (AssetFormats.affectsBinaryUpToDate(ai.getFormat())) {
                ModuleItem pkg = ai.getModule();
                pkg.updateBinaryUpToDate(false);
                RuleBaseCache.getInstance().remove(pkg.getUUID());
            }
            ai.checkin(asset.getMetadata().getCheckInComment());
            rulesRepository.save();
        } catch (RuntimeException e) {
            throw new WebApplicationException(e);
        }
         **/
    }
    @POST
    @Path("{organizationalUNit}/{repositoryName}/{packageName}/assets")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Asset createAssetFromBinaryAndJAXB(
            @PathParam("organizationalUNit") String organizationalUNit, @PathParam("repositoryName") String repositoryName,
            @PathParam("packageName") String packageName, @MultipartForm AssetMultipartForm assetMultipartForm) {
        /* Verify passed in asset object */
        /**
        if (assetMultipartForm == null || assetMultipartForm.getAsset() == null || assetMultipartForm.getAsset().getMetadata() == null ){
            throw new WebApplicationException(Response.status(500).entity("Request must contain asset and metadata").build());
        }
        final String assetName = assetMultipartForm.getAsset().getTitle();


        if (assetName == null) {
            throw new WebApplicationException(Response.status(500).entity("Asset name must be specified (Asset.metadata.title)").build());
        }

        AssetItem ai = rulesRepository.loadModule(packageName).addAsset(assetName, assetMultipartForm.getAsset().getDescription());
        ai.checkout();
        ai.updateBinaryContentAttachmentFileName(assetMultipartForm.getAsset().getBinaryContentAttachmentFileName());
        ai.updateFormat(assetMultipartForm.getAsset().getMetadata().getFormat());
        ai.updateBinaryContentAttachment(assetMultipartForm.getBinary());
        ai.getModule().updateBinaryUpToDate(false);
        ai.updateValid(assetValidator.validate(ai));
        ai.checkin(assetMultipartForm.getAsset().getMetadata().getCheckInComment());
        rulesRepository.save();
        return assetMultipartForm.getAsset();
         **/
        return null;
    }
    @PUT
    @Path("{organizationalUNit}/{repositoryName}/{packageName}/assets/{assetName}/source")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_PLAIN})
    @Produces({MediaType.WILDCARD})
    public void updateAssetSource(
            @PathParam("organizationalUNit") String organizationalUNit, @PathParam("repositoryName") String repositoryName,
            @PathParam("packageName") String packageName, @PathParam("assetName") String assetName, String content) {
        try {
            //Throws RulesRepositoryException if the package or asset does not exist
            /**
            AssetItem asset = rulesRepository.loadModule(packageName).loadAsset(assetName);
            asset.checkout();
            asset.updateContent(content);
            asset.updateValid(assetValidator.validate(asset));
            if (AssetFormats.affectsBinaryUpToDate(asset.getFormat())) {
                ModuleItem pkg = asset.getModule();
                pkg.updateBinaryUpToDate(false);
                RuleBaseCache.getInstance().remove(pkg.getUUID());
            }
            asset.checkin("Updated asset source from REST interface");
            rulesRepository.save();
             **/
        } catch (RuntimeException e) {
            throw new WebApplicationException(e);
        }

    }

}
