/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Calendar;
import java.util.Objects;

/**
 *
 * @author alunoinf
 */
public class Evento {
    
    private Integer codigo;
    private String nome;
    private String descricao;
    private Calendar data;
    private String local;
    
    public Evento(){
        this.codigo = null;
        this.codigo = null;
        this.data = null;
        this.descricao = null;
        this.local = null;
    }

    public Evento(Integer codigo, String nome) {
        this.codigo = codigo;
        this.nome = nome;
    }

    public Evento(Integer codigo, String nome, Calendar data, String descricao, String local) {
        this.codigo = codigo;
        this.nome = nome;
        this.descricao = descricao;
        this.data = data;
        this.local = local;
    }
    
    

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Calendar getData() {
        return data;
    }
    
    

    public void setData(Calendar data) {
        this.data = data;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }
    
    public void ingressosVendidos(){
        
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Evento other = (Evento) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return true;
    }
    
    

}
