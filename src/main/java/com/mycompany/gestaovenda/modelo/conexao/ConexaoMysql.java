/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestaovenda.modelo.conexao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author maria
 */
public class ConexaoMysql implements Conexao{
    
    private final String USUARIO = "root";
    private final String SENHA = "!Mariane123";
    private final String URL = "jdbc:mysql://localhost:3306/gestao_venda";
    private Connection conectar;
    
    @Override
    public Connection obterConexao() throws SQLException{
     if(conectar == null){
         conectar = DriverManager.getConnection(URL, USUARIO, SENHA);
     }
     return conectar;
    }
}
