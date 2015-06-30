package org.chtijbug.drools.runtime.impl;

import org.chtijbug.drools.entity.DroolsFactObject;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: samuel
 * Date: 28/09/12
 * Time: 17:55
 */
public class RuleBaseStatefulSessionTestCase {
    private static RuleBaseSingleton ruleBase ;

    private RuleBaseStatefulSession session;

    @Before
    public void before() throws Exception {
        ruleBase = new RuleBaseSingleton();
        // TODO Créer une kbase
        ruleBase.createKBase();
        // TODO créer une session à partir dela KBase
        session = (RuleBaseStatefulSession) ruleBase.createRuleBaseSession();
    }

    @Test
    public void runInsertByReflection() throws Exception {

        DummyFact sibling = new DummyFact("sibling");
        DummyFact item1 = new DummyFact("items 1");
        DummyFact item2 = new DummyFact("items 2");
        DummyFact root = new DummyFact("ROOT", sibling, Arrays.asList(item1, item2));
        root.setNumber(new Long("1"));
        root.setaDate(new Date());
        root.setaBigDecimal(new BigDecimal("12.03"));
        root.setaDouble(new Double("12.3"));
        root.setSimpleDouble(12.3);
        root.setSimpleLong(12);
        try {
            //_____ Execute reflection insertion
            session.insertByReflection(root);
            //_____ Assert that all fact handle have been correctly created
            Collection<DroolsFactObject> droolsFactObjects = session.listLastVersionObjects();
            assertEquals(4, droolsFactObjects.size());


            DroolsFactObject rootFactObject = session.getLastFactObjectVersion(root);
            assertNotNull(rootFactObject);
            DroolsFactObject siblingFactObject = session.getLastFactObjectVersion(sibling);
            assertNotNull(siblingFactObject);
            DroolsFactObject item1FactObject = session.getLastFactObjectVersion(item1);
            assertNotNull(item1FactObject);
            DroolsFactObject item2FactObject = session.getLastFactObjectVersion(item2);
            assertNotNull(item2FactObject);

        } catch(Exception e) {
            e.printStackTrace();
            fail();
        }

    }


    public class DummyFact {
        private String name;
        private DummyFact property;
        private List<DummyFact> listOfFacts;
        private Long number;
        private Date aDate;
        private BigDecimal aBigDecimal;
        private Double aDouble;
        private long simpleLong;
        private double simpleDouble;


        DummyFact(String name) {
            this.name = name;
        }

        DummyFact(String name, DummyFact property) {
            this(name);
            this.property = property;
        }

        DummyFact(String name, DummyFact property, List<DummyFact> listOfFacts) {
            this(name, property);
            this.listOfFacts = listOfFacts;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public DummyFact getProperty() {
            return property;
        }

        public void setProperty(DummyFact property) {
            this.property = property;
        }

        public List<DummyFact> getListOfFacts() {
            return listOfFacts;
        }

        public void setListOfFacts(List<DummyFact> listOfFacts) {
            this.listOfFacts = listOfFacts;
        }

        public Long getNumber() {
            return number;
        }

        public void setNumber(Long number) {
            this.number = number;
        }

        public Date getaDate() {
            return aDate;
        }

        public void setaDate(Date aDate) {
            this.aDate = aDate;
        }

        public BigDecimal getaBigDecimal() {
            return aBigDecimal;
        }

        public void setaBigDecimal(BigDecimal aBigDecimal) {
            this.aBigDecimal = aBigDecimal;
        }

        public Double getaDouble() {
            return aDouble;
        }

        public void setaDouble(Double aDouble) {
            this.aDouble = aDouble;
        }

        public long getSimpleLong() {
            return simpleLong;
        }

        public void setSimpleLong(long simpleLong) {
            this.simpleLong = simpleLong;
        }

        public double getSimpleDouble() {
            return simpleDouble;
        }

        public void setSimpleDouble(double simpleDouble) {
            this.simpleDouble = simpleDouble;
        }
    }

}
