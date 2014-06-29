/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Calendar;
import model.TipoPagamento;

/**
 *
 * @author alunoinf
 */
public class Compra {
    
    private Integer codigo;
    private Cliente cliente;
    private Ingresso ingresso;
    private Float valor;
    private TipoPagamento tipoPagamento;
    private Calendar data;

    public Compra(){
        
    }
    
    public Compra(TipoPagamento tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    public Compra(Integer codigo, Cliente cliente, Ingresso ingresso, Float valor, TipoPagamento tipoPagamento, Calendar data) {
        this.codigo = codigo;
        this.cliente = cliente;
        this.ingresso = ingresso;
        this.valor = valor;
        this.tipoPagamento = tipoPagamento;
        this.data = data;
    }
    
    

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Calendar getData() {
        return data;
    }

    public void setData(Calendar data) {
        this.data = data;
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public TipoPagamento getTipoPagamento() {
        return tipoPagamento;
    }

    public void setTipoPagamento(TipoPagamento tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }
    
    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Ingresso getIngresso() {
        return ingresso;
    }

    public void setIngresso(Ingresso ingresso) {
        this.ingresso = ingresso;
    }
    
    
}
