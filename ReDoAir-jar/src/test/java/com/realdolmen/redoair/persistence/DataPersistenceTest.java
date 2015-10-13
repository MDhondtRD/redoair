package com.realdolmen.redoair.persistence;

import com.realdolmen.redoair.ejb.AirportRepository;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.DatabaseSequenceFilter;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.FilteredDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.mysql.MySqlDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

/**
 * Loads a DBUnit test set before every unit test.
 */
public abstract class DataPersistenceTest extends PersistenceTest {
    private static final Logger logger = LoggerFactory.getLogger(DataPersistenceTest.class);

    @Before
    public void loadDataSet() throws Exception {
        logger.info("Loading dataset");
        IDataSet dataSet = new FlatXmlDataSetBuilder().build(getClass().getResource("/data.xml"));
        IDatabaseConnection connection = new DatabaseConnection(newConnection());
        connection.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new MySqlDataTypeFactory()); // Set factorytype in dbconfig to remove warning
        DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
        connection.close();
        PopulateDatabase pd = new PopulateDatabase();
        pd.setup(entityManager());
    }

    @After
    public void emptyDataBase() throws Exception {
        logger.info("Deleting stuff");
        IDataSet dataSet = new FlatXmlDataSetBuilder().build(getClass().getResource("/data.xml"));
        IDatabaseConnection connection = new DatabaseConnection(newConnection());
        connection.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new MySqlDataTypeFactory()); // Set factorytype in dbconfig to remove warning
        DatabaseOperation.DELETE_ALL.execute(connection, dataSet);
        entityManager().flush();
        connection.close();
    }
}
