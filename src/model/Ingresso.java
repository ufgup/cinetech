/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author alunoinf
 */
public class Ingresso {
    
    private Integer codigo;
    private Boolean vendido;
    private Evento evento;
    private Secao secao;
    
    public Ingresso(){
        
    }
    
    public Ingresso(Integer codigo, Secao secao, Evento evento, Boolean vendido) {
        this.codigo = codigo;
        this.secao = secao;
        this.evento = evento;
        this.vendido = vendido;
        
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Boolean isVendido() {
        return vendido;
    }

    public void setVendido(Boolean vendido) {
        this.vendido = vendido;
    }

    public Secao getSecao() {
        return secao;
    }

    public void setSecao(Secao secao) {
        this.secao = secao;
    }
    
    public String estaVendido(){
        if(this.vendido){
            return "Vendido";
        }
        else{
            return "Disponível";
        }
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }
    
    
    
}
