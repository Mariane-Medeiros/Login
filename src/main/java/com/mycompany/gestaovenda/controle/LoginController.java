/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestaovenda.controle;

import com.mycompany.gestaovenda.modelo.dao.AutenticacaoDao;
import com.mycompany.gestaovenda.modelo.dominio.Usuario;
import com.mycompany.gestaovenda.view.formulario.Login;
import com.mycompany.gestaovenda.view.imagens.modelo.LoginDTO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author maria
 */
public class LoginController implements ActionListener{
    private final Login login;
    
    private AutenticacaoDao autenticacaoDao;
    
    public LoginController(Login login){
        this.login = login;
        this.autenticacaoDao = new AutenticacaoDao();
    }
    
    @Override
    public void actionPerformed(ActionEvent ao){
        String accao = ao.getActionCommand().toLowerCase();
        
        switch(accao){
            case "login": login(); break;
            case "cancelar": cancelar(); break;
        }
    }

    private void login() {
        String usuario = this.login.getTxtLoginUsuario().getText();
        String senha = this.login.getTxtLoginSenha().getText();
        
        if(usuario.equals("") || senha.equals("")){
            this.login.getLabelLoginMensagem().setText("Usuario e senha devem ser preenchidos.");
            return;
        }
        
        LoginDTO loginDto = new LoginDTO(usuario, senha);
        
        Usuario usuarioTemp = this.autenticacaoDao.login(loginDto);
        
        if(usuarioTemp != null){
            JOptionPane.showConfirmDialog(null, usuarioTemp.getNome());
        }else{
            this.login.getLabelLoginMensagem().setText("Usuario e senha incorretos");
        }
        
        
      }

    private void cancelar() {
        int confirmar = JOptionPane.showConfirmDialog(login, "Certeza que deseja sair?", "Sair do Sistema", JOptionPane.YES_NO_OPTION);
       
         if(confirmar == JOptionPane.YES_OPTION){
        System.exit(8);
        }   
    }
    
    private void LimpaCampos(){
        this.login.getTxtLoginUsuario().setText("");
        this.login.getTxtLoginSenha().setText("");
        this.login.getLabelLoginMensagem().setText("");
    }
    
    
}

