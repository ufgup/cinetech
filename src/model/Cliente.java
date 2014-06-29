/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Objects;

/**
 *
 * @author alunoinf
 */
public class Cliente implements Comparable<Cliente>{
    
    private String cpf;
    private String nome;
    private String endereco;
    private String telefone;

    public Cliente(){
        
    }
    
    public Cliente(String cpf, String nome) {
        this.cpf = cpf;
        this.nome = nome;
    }

    public Cliente(String cpf, String nome, String endereco, String telefone) {
        this.cpf = cpf;
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
    }
    
    

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    
    @Override
    public String toString(){
        String string = "Nome: " + this.getNome() + ", CPF: " + this.getCpf() + ", Telefone: " + this.getTelefone() + ", Endereço: " + this.getEndereco() + ".";
        return string;
    
}

    @Override
    public int compareTo(Cliente comparado) {
        
        return Integer.parseInt(comparado.getCpf()) - Integer.parseInt(this.getCpf());
        
        
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Cliente other = (Cliente) obj;
        if (!Objects.equals(this.cpf, other.cpf)) {
            return false;
        }
        return true;
    }
    
    
    
}
