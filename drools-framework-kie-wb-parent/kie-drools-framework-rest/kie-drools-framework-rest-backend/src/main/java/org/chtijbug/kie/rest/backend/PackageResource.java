package org.chtijbug.kie.rest.backend;

import org.apache.camel.Headers;
import org.drools.guvnor.server.jaxrs.jaxb.*;
import org.drools.guvnor.server.jaxrs.jaxb.Package;
import org.guvnor.common.services.project.model.Project;
import org.guvnor.common.services.project.service.ProjectService;
import org.guvnor.structure.organizationalunit.OrganizationalUnit;
import org.guvnor.structure.organizationalunit.OrganizationalUnitService;
import org.guvnor.structure.repositories.Repository;
import org.guvnor.structure.repositories.RepositoryService;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.slf4j.LoggerFactory;
import org.uberfire.backend.server.util.*;
import org.uberfire.backend.server.util.Paths;
import org.uberfire.backend.vfs.*;

import org.uberfire.io.IOService;
import org.kie.workbench.common.services.shared.project.KieProjectService;
import org.uberfire.java.nio.file.*;
import org.uberfire.java.nio.file.DirectoryStream;
import org.uberfire.java.nio.file.FileSystem;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.*;
import javax.ws.rs.Path;
import javax.ws.rs.Path;
import javax.ws.rs.Path;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.*;

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

    @Inject
    private KieProjectService kieProjectService;

    private RestTypeDefinition dotFileFilter = new RestTypeDefinition();

    @GET
    @Path("{organizationalUnitName}/{repositoryName}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Collection<Project> getPackagesAsJAXB( @PathParam("organizationalUnitName") String organizationalUnitName, @PathParam("repositoryName") String repositoryName) {
        OrganizationalUnit organizationalUnit = organizationalUnitService.getOrganizationalUnit(organizationalUnitName);
        Collection<Repository> repositories = organizationalUnit.getRepositories();
        for (Repository repository : repositories){
            if (repository.getAlias().equals(repositoryName)) {
                String branch = repository.getCurrentBranch();
                Set<Project> projects = projectService.getProjects(repository, branch);
                return projects;
            }
        }
        return null;
    }

    @GET
    @Path("{organizationalUnitName}/{repositoryName}/{packageName}/assets")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Collection<Asset> getAssetsAsJAXB(
            @PathParam("organizationalUnitName") String organizationalUnitName, @PathParam("repositoryName") String repositoryName,
            @PathParam("packageName") String packageName,
            @QueryParam("format") List<String> formats) {
        try {
            List<Asset> ret = new ArrayList<Asset>();



            OrganizationalUnit organizationalUnit = organizationalUnitService.getOrganizationalUnit(organizationalUnitName);
            Collection<Repository> repositories = organizationalUnit.getRepositories();
            for (Repository repository : repositories){
                if (repository.getAlias().equals(repositoryName)) {
                    String branch = repository.getCurrentBranch();
                    Set<Project> projects = projectService.getProjects(repository, branch);
                    for (Project project : projects) {
                        if (project.getProjectName().equals(packageName)) {

                            org.uberfire.backend.vfs.Path rootPath = project.getRootPath();
                            org.uberfire.java.nio.file.Path goodRootPath = Paths.convert(rootPath);
                            DirectoryStream<org.uberfire.java.nio.file.Path> directoryStream = ioService.newDirectoryStream(goodRootPath);
                            for (org.uberfire.java.nio.file.Path elementPath : directoryStream){
                                if (org.uberfire.java.nio.file.Files.isDirectory(elementPath)){

                                }else {
                                    if (dotFileFilter.accept(Paths.convert(elementPath))) {
                                        Map<String, Object> listAttributes = ioService.readAttributes(elementPath);
                                        //ioService.
                                    }
                                }
                            }

                        }
                        System.out.print("hh");
                    }
                }
            }

            return ret;
        } catch (RuntimeException e) {
            throw new WebApplicationException(e);
        }
    }
    @GET
    @Path("{organizationalUnitName}/{repositoryName}/{packageName}/assets/{assetName}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})

    public Asset getAssetAsJaxB(
            @PathParam("organizationalUnitName") String organizationalUnitName, @PathParam("repositoryName") String repositoryName,
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
    @Path("{organizationalUnitName}/{repositoryName}/{packageName}/assets/{assetName}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public void updateAssetFromJAXB(
            @PathParam("organizationalUnitName") String organizationalUnitName, @PathParam("repositoryName") String repositoryName,
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
    @Path("{organizationalUnitName}/{repositoryName}/{packageName}/assets")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Asset createAssetFromBinaryAndJAXB(
            @PathParam("organizationalUnitName") String organizationalUnitName, @PathParam("repositoryName") String repositoryName,
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
    @Path("{organizationalUnitName}/{repositoryName}/{packageName}/assets/{assetName}/source")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_PLAIN})
    @Produces({MediaType.WILDCARD})
    public void updateAssetSource(
            @PathParam("organizationalUnitName") String organizationalUnitName, @PathParam("repositoryName") String repositoryName,
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
