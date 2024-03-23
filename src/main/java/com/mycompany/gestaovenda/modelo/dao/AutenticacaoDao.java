/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestaovenda.modelo.dao;

import javax.swing.JOptionPane;
import com.mycompany.gestaovenda.modelo.dominio.Usuario;
import com.mycompany.gestaovenda.view.imagens.modelo.LoginDTO;
import com.mycompany.gestaovenda.modelo.dominio.Perfil;
import com.mycompany.gestaovenda.modelo.exception.NegocioException;

public class AutenticacaoDao {
    
    private final UsuarioDao usuarioDao;
    
    public AutenticacaoDao(){
        this.usuarioDao = new UsuarioDao();

    }
    
    public boolean temPermissao(Usuario usuario){
        try{
            permissao(usuario);
            return true;
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Usuario sem permissao", 0);
            return false;
        }
    }
    
    private void permissao(Usuario usuario){
        if(!Perfil.ADMIN.equals(usuario.getPerfil())){
            throw new NegocioException("Sem permissao para realizar essa acao");
        }
    }
    
    public Usuario login(LoginDTO Login){
        Usuario usuario = usuarioDao.buscarUsuarioPeloUsuario(Login.getUsuario());
        if(usuario == null || !usuario.isEstado())
            return null;
        
        if(usuario.isEstado() && validarSenha(usuario.getSenha(), Login.getSenha())){
            return usuario;
        }
        
        return null;
    }

    private boolean validarSenha(String senhaUsuario, String senhaLogin){
        return senhaUsuario.equals(senhaLogin);
    }
}
