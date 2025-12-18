/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exemplo.jpa;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MASC
 */
public class DbUnitUtil {
    
    private static final Logger logger = Logger.getLogger(DbUnitUtil.class.getName());
    private static final String XML_FILE = "/dbunit/dataset.xml";

    /**
     * MÉTODO ESTÁTICO PARA FOTO
     * Usado no dataset.xml para popular colunas BLOB/LOB de forma estática.
     */
    public static String getFotoBase64(String nomeArquivo) {
        // Retorna um placeholder simples que o DBUnit e o JPA aceitarão como BLOB/byte[].
        return "AAAA"; 
    }

    public static void inserirDados() {
        Connection conn = null;
        IDatabaseConnection db_conn = null;
        try {
            conn = DriverManager.getConnection(
                    "jdbc:derby://localhost:1527/PetConnect", "app", "app");
            db_conn = new DatabaseConnection(conn);
            FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
            builder.setColumnSensing(true);
            
            InputStream in = DbUnitUtil.class.getResourceAsStream(XML_FILE);
            if (in == null) {
                throw new RuntimeException("Arquivo dataset.xml não encontrado: " + XML_FILE);
            }
            IDataSet dataSet = builder.build(in);
            DatabaseOperation.CLEAN_INSERT.execute(db_conn, dataSet);
          
            // AUTOCREMENTO (Incluindo TB_NOTIFICACAO)
            try (var stmt = conn.createStatement()) {
                stmt.execute("ALTER TABLE TB_USUARIO ALTER COLUMN ID RESTART WITH 10"); 
                stmt.execute("ALTER TABLE TB_PET ALTER COLUMN ID RESTART WITH 4");
                stmt.execute("ALTER TABLE TB_NOTIFICACAO ALTER COLUMN ID_NOTIFICACAO RESTART WITH 2"); 
                stmt.execute("ALTER TABLE TB_SERVICO ALTER COLUMN ID_SERVICO RESTART WITH 3");
            }
        } catch (SQLException | DatabaseUnitException ex) {
            logger.log(Level.SEVERE, "Erro ao inserir dados com DBUnit", ex);
            throw new RuntimeException("Erro ao inserir dados com DBUnit: " + ex.getMessage(), ex);
        } finally{
            try {
                if (conn != null) {
                    conn.close();
                }

                if (db_conn != null) {
                    db_conn.close();
                }
            } catch (SQLException ex) {
                logger.log(Level.SEVERE, "Erro ao fechar conexão", ex);
                throw new RuntimeException(ex.getMessage(), ex);
            }
        }
    }
}
