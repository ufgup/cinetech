/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Evento;
import java.lang.RuntimeException;
import model.Ingresso;

/**
 *
 * @author alunoinf
 */
public class EventoGravacaoHelper implements ServiceHelper<Evento>{
    
    private final String ARQUIVO = "eventos.csv";
    private CSVToFile gerenciadorDeArquivo;
    
    public EventoGravacaoHelper(){
       this.gerenciadorDeArquivo = new CSVToFile(ARQUIVO);
    }

    @Override
    public boolean gravarObjeto(Evento evento) {
        
        if(!gerenciadorDeArquivo.contem(Integer.toString(evento.getCodigo()))){
            return gerenciadorDeArquivo.gravarLinha(toLine(evento));
        }else{
            throw new RuntimeException("Já existe um evento com esse código.");
        }
    }

    @Override
    public void gravarObjetos(List<Evento> eventos) {
       for(Evento evento : eventos){
           this.gravarObjeto(evento);
       }
    }
    
    @Override
    public Evento getObjetoPorId(String id) {
        String linha = gerenciadorDeArquivo.getLinhaPorId(id);
        if(linha != null){
            Evento resultado = getObject(linha);
            return resultado;
        }else{
            return null;
        }
        
    }

    @Override
    public List<Evento> getTodosObjetos() {
        List<String> listaObjetos = gerenciadorDeArquivo.getLinhas();
        List<Evento> resultado = new ArrayList<>();
        for(String entrada : listaObjetos){
            Evento evento = getObject(entrada);
            resultado.add(evento);
        }
        return resultado;
    }
    
    public List<Evento> getListaDeEventosDisponiveis(){
        
        List<String> listaIngressos = new ArrayList<>();
        IngressoGravacaoHelper igh = new IngressoGravacaoHelper();
        listaIngressos = igh.getIngressosDisponiveisString();
        List<Evento> listaEventos = new ArrayList<>();
        for(String ingresso : listaIngressos){
            String[] vetor = ingresso.split(String.valueOf(
                                ServiceHelper.SEPARADOR));
            Evento evento = getObjetoPorId(vetor[2]);
            if(!listaEventos.contains(evento)){
                listaEventos.add(evento);
            }else{
                
            }
            
        }
        
        return listaEventos;
        
    }

    private String toLine(Evento e){
        
        StringBuilder sb = new StringBuilder();
        sb.append(e.getCodigo());
        sb.append(ServiceHelper.SEPARADOR);
        sb.append(e.getNome());
        sb.append(ServiceHelper.SEPARADOR);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String date = sdf.format(e.getData().getTime());
        sb.append(date);
        sb.append(ServiceHelper.SEPARADOR);
        sb.append(e.getDescricao());
        sb.append(ServiceHelper.SEPARADOR);
        sb.append(e.getLocal());
        return sb.toString();
    }
    
    private Evento getObject(String line){
        String[] evento = line.split(
                String.valueOf(ServiceHelper.SEPARADOR));
        String codigo = evento[0];
        String nome = evento[1];
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar data = Calendar.getInstance();
        try {
            data.setTime(sdf.parse(evento[2]));
        } catch (ParseException ex) {
            Logger.getLogger(EventoGravacaoHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        String descricao = evento[3];
        String local = evento[4];        

        Evento resultado = new Evento(Integer.parseInt(codigo), nome, data, descricao, local);
        
        return resultado;
    }
    
    @Override
    public boolean remove(Evento evento) {
        return false;
    }
    
}
