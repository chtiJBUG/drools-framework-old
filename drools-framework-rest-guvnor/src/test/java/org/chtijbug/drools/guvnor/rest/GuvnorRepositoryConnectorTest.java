package org.chtijbug.drools.guvnor.rest;

import org.apache.commons.httpclient.HttpClient;
import org.chtijbug.drools.guvnor.GuvnorConnexionConfiguration;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.List;
import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(PowerMockRunner.class)
@PrepareForTest(HttpClient.class)
public class GuvnorRepositoryConnectorTest {

    GuvnorConnexionConfiguration configuration = new GuvnorConnexionConfiguration("http://mock-server","drools-guvnor","test","tomcat","tomcat");
    GuvnorRepositoryConnector guvnorRepositoryConnector = new GuvnorRepositoryConnector(configuration);
    private HttpClient mockWebClient;

    @Before
    public void setUp() throws Exception {
        //mockWebClient = mockWebClient();
    }

    @Test
    @Ignore
    public void should_get_latest_asset_version() throws Exception {
        Integer latestAssetVersion = guvnorRepositoryConnector.getAssetVersion("AssetWithVersions");

        assertThat(latestAssetVersion).isNotNull();
        assertThat(latestAssetVersion).isEqualTo(71);

    }

    @Test
    @Ignore
    public void should_get_template_model() throws Exception {
        Map<String, List<String>> table = guvnorRepositoryConnector.getTemplateTable("MyTemplateRule");

        assertThat(table).hasSize(3);
        assertThat(table.get("CONTRACT_A")).hasSize(4);

        //verify(mockWebClient).header("Authorization", "Basic dG9tY2F0OnRvbWNhdA==");
    }

    @Test
    @Ignore
    public void should_put_template_table() throws Exception {
        Map<String,List<String>> table = newHashMap();
        table.put("CONTRACT_A" , asList("false","false","false","false"));
        table.put("CONTRACT_B" , asList("true" ,"false","true" ,"false"));
        table.put("CONTRACT_C" , asList("true" ,"true" ,"false","false"));

        guvnorRepositoryConnector.putTemplateTable("MyTemplateRule", table);

        //assertExpectedXmlContent(mockWebClient);
    }


    @Test(expected = ChtijbugDroolsRestException.class)
    @Ignore
    public void should_detect_wrong_column_number() throws Exception {
        Map<String,List<String>> table = newHashMap();
        table.put("CONTRACT_A" , asList("false","false","false","false"));
        table.put("CONTRACT_C" , asList("true" ,"true" ,"false","false"));

        guvnorRepositoryConnector.putTemplateTable("MyTemplateRule", table);
    }

/**
    private void assertExpectedXmlContent(WebClient mockWebClient) throws SAXException, IOException {
        ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);
        verify(mockWebClient).put(argument.capture());
        XMLUnit.setIgnoreWhitespace(true);
        Diff diff = compareXML(readResource("/ExpectedTemplateRule.xml"), argument.getValue());
        if (!diff.similar()) {
            fail(diff.toString());
        }
    }

    private HttpClient mockWebClient() {
        try {
            mockStatic(HttpClient.class);
            HttpClient mock = mock(HttpClient.class);//, withSettings().verboseLogging());
            when(mock.get(String.class)).thenReturn(readResource("/MyTemplateRule.xml"));
            when(mock.get(InputStream.class)).thenReturn(IOUtils.toInputStream(readResource("/asset-atom.xml")));
            when(mock.accept(anyString())).thenReturn(mock);
            when(mock.type(anyString())).thenReturn(mock);
            when(mock.path("drools-guvnor/rest/packages/test/assets/MyTemplateRule/source")).thenReturn(mock);
            when(mock.path("drools-guvnor/rest/packages/test/assets/AssetWithVersions/versions")).thenReturn(mock);
            return mock;
        } catch (IOException e) {
            throw propagate(e);
        }
    }

    private String readResource(String resource) throws IOException {
        return IOUtils.toString(this.getClass().getResource(resource).openStream());
    }

 **/
}
