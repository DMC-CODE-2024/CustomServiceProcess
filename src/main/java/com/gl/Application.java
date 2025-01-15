/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gl;

import com.gl.Config.MySQLConnection;
import com.gl.P5Process.CustomImeiPairProcess;


public class Application {
    public static void main(String[] args) {
        try (var c = new MySQLConnection().getConnection()) {
            CustomImeiPairProcess.p5(c);
        } catch (Exception e) {  
        }
       // CustomImeiPairProcess.p5(new MySQLConnection().getConnection());
        System.exit(0);
    }
}
