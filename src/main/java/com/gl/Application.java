/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gl;

import com.gl.Audit.ModulesAudit;
import com.gl.Config.MySQLConnection;
import com.gl.Config.PropertyReader;
import com.gl.P5Process.CustomImeiPairProcess;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Application {
    public static PropertyReader propertyReader = new PropertyReader();
    static Logger log = LogManager.getLogger(Application.class);
    public static String audDb;
    public static String serverName;

    public static void main(String[] args) {
        int auditId = 0;
        long startTime = System.currentTimeMillis();
        try {
            audDb = propertyReader.getConfigPropValue("auddbName");
            serverName = propertyReader.getConfigPropValue("serverName");
        } catch (Exception e) {
            log.error("Not able to fetch information for serverName " + e);
        }
        try (var c = new MySQLConnection().getConnection()) {
            auditId = ModulesAudit.insertModuleAudit(c, "customs_duty_module", "Process for customs_duty_module", serverName);
            CustomImeiPairProcess.p5(c);

        } catch (Exception e) {  
        }
       // CustomImeiPairProcess.p5(new MySQLConnection().getConnection());
        System.exit(0);
    }
}
