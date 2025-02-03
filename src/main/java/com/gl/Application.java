/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gl;

import com.gl.AlertAudit.ModulesAudit;
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
    public static int passcount;
    public static int failcount;

    public static void main(String[] args) {
        try {
            audDb = propertyReader.getConfigPropValue("auddbName");
            serverName = propertyReader.getConfigPropValue("serverName");
            passcount = 0;
            failcount = 0;
        } catch (Exception e) {
            log.error("Not able to fetch information for serverName " + e);
        }
        try (var c = new MySQLConnection().getConnection()) {
            int auditId = com.gl.AlertAudit.ModulesAudit.insertModuleAudit(c, "Custom Duty Process");
            CustomImeiPairProcess.p5(c);
            com.gl.AlertAudit.ModulesAudit.updateModuleAudit(c, 9, passcount, failcount, auditId);
        } catch (Exception e) {
            log.error("Error in Service  " + e);
        }
        System.exit(0);
    }
}
