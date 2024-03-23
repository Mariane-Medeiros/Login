/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestaovenda.modelo.dao;
import com.mycompany.gestaovenda.modelo.conexao.Conexao;
import com.mycompany.gestaovenda.modelo.conexao.ConexaoMysql;
import com.mycompany.gestaovenda.modelo.dominio.Usuario;
import com.mycompany.gestaovenda.modelo.dominio.Perfil;

import java.time.LocalDateTime;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
/**
 *
 * @author maria
 */
public class UsuarioDao {
    
    
    private final Conexao conexao;
    
    public UsuarioDao(){
        this.conexao = new ConexaoMysql();
    }
    public String salvar(Usuario Usuario){
        return Usuario.getId() == 0L ? adicionar(Usuario) : editar(Usuario);
    }

public String adicionar(Usuario usuario) {
    String sql = "INSERT INTO usuario(nome, usuario, senha, perfil, estado) VALUES (?, ?, ?, ?, ?)";
    Usuario usuarioTemp = buscarUsuarioPeloUsuario(usuario.getUsuario());
    
    if(usuarioTemp != null){
       return String.format("Error: Username %s já existe no banco de dados", usuario.getUsuario());
    }
    try {
        PreparedStatement preparedStatement = conexao.obterConexao().prepareStatement(sql);

        preencherValoresPreperedStatement(preparedStatement, usuario);
        int resultado = preparedStatement.executeUpdate();

        return resultado == 1 ? "Usuario adicionado com sucesso" : "Não foi possível adicionar";
    } catch (SQLException e) {
        return String.format("Erro: %s", e.getMessage());
    }
}

public String editar(Usuario usuario) {
    String sql = "UPDATE usuario SET nome = ?, usuario = ?, senha = ?, perfil = ?, estado = ? WHERE id = ?";
    try {
        PreparedStatement preparedStatement = conexao.obterConexao().prepareStatement(sql);

        preencherValoresPreperedStatement(preparedStatement, usuario);
        int resultado = preparedStatement.executeUpdate();

        return resultado == 1 ? "Usuario editado com sucesso" : "Não foi possível editar";
    } catch (SQLException e) {
        return String.format("Erro: %s", e.getMessage());
    }
}


    private void preencherValoresPreperedStatement(PreparedStatement preparedStatement, Usuario usuario) throws SQLException {
          
        preparedStatement.setString(1, usuario.getNome());
        preparedStatement.setString(2, usuario.getUsuario());
        preparedStatement.setString(3, usuario.getSenha());
        preparedStatement.setString(4, usuario.getPerfil().name());
        preparedStatement.setBoolean(5, usuario.isEstado());

        
        if(usuario.getId() != 0L){
            preparedStatement.setLong(6, usuario.getId());
        }
    }
    
    public List<Usuario> buscarTodosUsuarios(){
        String sql = "SELECT * FROM usuario";
        List<Usuario> usuarios = new ArrayList<>();
        try{
            ResultSet result = conexao.obterConexao().prepareStatement(sql).executeQuery();
        
            while(result.next()){
                usuarios.add(getUsuario(result));
            }
        }catch(SQLException e){
            System.out.println(String.format("Erro: ", e.getMessage()));
        }
        return usuarios;
    }
    
    private Usuario getUsuario(ResultSet result) throws SQLException{
        Usuario usuario = new Usuario();
        usuario.setId(result.getLong("id"));
        usuario.setNome(result.getString("nome"));
        usuario.setUsuario(result.getString("usuario"));
        usuario.setSenha(result.getString("senha"));
        usuario.setPerfil(Perfil.valueOf(result.getString("perfil")));
        usuario.setEstado(result.getBoolean("estado"));
        usuario.setDataHoraCriacao(result.getObject("data_hora_criacao", LocalDateTime.class));
        usuario.setUltimoLogin(result.getObject("ultimo_login", LocalDateTime.class));
        return usuario;
    }
    
        public Usuario buscarUsuarioPeloId(long id){
        String sql = String.format("SELECT * FROM usuario WHERE id = %d", id);
        List<Usuario> usuarios = new ArrayList<>();
        try{
            ResultSet result = conexao.obterConexao().prepareStatement(sql).executeQuery();
        
            while(result.next()){
               return getUsuario(result);
            }
        }catch(SQLException e){
            System.out.println(String.format("Erro: ", e.getMessage()));
        }
        return null;
    
        }
    
        public Usuario buscarUsuarioPeloUsuario(String usuario){
        String sql = String.format("SELECT * FROM usuario WHERE usuario = '%s'", usuario);
        System.out.println(sql);
        List<Usuario> usuarios = new ArrayList<>();
        
        
        try{
            ResultSet result = conexao.obterConexao().prepareStatement(sql).executeQuery();
        
            while(result.next()){
               return getUsuario(result);
            }
        }catch(SQLException e){
            e.printStackTrace();
            System.out.println(String.format("Erro: ", e.getMessage()));
        }
        return null;
        }
        
}


