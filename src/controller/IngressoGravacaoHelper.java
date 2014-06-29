/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import java.util.List;
import model.Compra;
import model.Evento;
import model.Ingresso;
import model.Secao;

/**
 *
 * @author alunoinf
 */
public class IngressoGravacaoHelper implements ServiceHelper<Ingresso>{
    
    private final String ARQUIVO = "ingressos.csv";
    private CSVToFile gerenciadorDeArquivo;
    private String ARQUIVOEXTERNO = "ingressosimport.csv";
    private CSVToFile gerenciadorDeArquivoExterno;
    
    public IngressoGravacaoHelper(){
        this.gerenciadorDeArquivo = new CSVToFile(ARQUIVO);
        this.gerenciadorDeArquivoExterno = new CSVToFile(ARQUIVOEXTERNO);
    }

    @Override
    public boolean gravarObjeto(Ingresso ingresso) {
        if(!gerenciadorDeArquivo.contem(Integer.toString(ingresso.getCodigo()))){
            return gerenciadorDeArquivo.gravarLinha(toLine(ingresso));
        }else{
            return false;
        }
    }

    @Override
    public void gravarObjetos(List<Ingresso> ingressos) {
       for(Ingresso ingresso : ingressos){
           this.gravarObjeto(ingresso);
       }
    }

    @Override
    public Ingresso getObjetoPorId(String id) {
        String linha = gerenciadorDeArquivo.getLinhaPorId(id);
        if(linha != null){
            Ingresso resultado = getObject(linha);
            return resultado;
        }else{
            return null;
        }
        
    }

    @Override
    public List<Ingresso> getTodosObjetos() {
        List<String> listaObjetos = gerenciadorDeArquivo.getLinhas();
        List<Ingresso> resultado = new ArrayList<Ingresso>();
        for(String entrada : listaObjetos){
            Ingresso cliente = getObject(entrada);
            resultado.add(cliente);
        }
        return resultado;
    }
    
    public List<Ingresso> getIngressosDisponiveis(){
        List<String> ingressosDisoniveis = gerenciadorDeArquivo.getObjetosPorAtributo(Boolean.FALSE.toString(), 3);
        List<Ingresso> listaIngressos = new ArrayList<>();
        
        for(String ingresso : ingressosDisoniveis){
            Ingresso ingressoObject = getObject(ingresso);
            listaIngressos.add(ingressoObject);
        }
        return listaIngressos;
        
    }
    
    public List<String> getIngressosDisponiveisString(){
        List<String> ingressosDisoniveis = gerenciadorDeArquivo.getObjetosPorAtributo(Boolean.FALSE.toString(), 3);
        return ingressosDisoniveis;
        
    }
    
    private String toLine(Ingresso i){
        StringBuilder sb = new StringBuilder();
        sb.append(i.getCodigo().toString());
        sb.append(ServiceHelper.SEPARADOR);
        sb.append(Integer.toString(i.getSecao().getCodigo()));
        sb.append(ServiceHelper.SEPARADOR);
        sb.append(i.getEvento().getCodigo());
        sb.append(ServiceHelper.SEPARADOR);
        sb.append(i.isVendido());
        return sb.toString();
    }
    
    public Ingresso getObject(String line){
        String[] ingresso = line.split(
                String.valueOf(ServiceHelper.SEPARADOR));
        Integer codigo = Integer.parseInt(ingresso[0]);
        Secao secao = Secao.valueOf(Integer.parseInt(ingresso[1]));
        EventoGravacaoHelper egh = new EventoGravacaoHelper();
        Evento evento = egh.getObjetoPorId(ingresso[2]);
        Boolean vendido = Boolean.parseBoolean(ingresso[3]);
        Ingresso resultado = new Ingresso(codigo,secao, evento, vendido);
        return resultado;
    }
    
    public List<Ingresso> getIngressosPorEvento(Integer codEvento){
        
        String codigoEvento = Integer.toString(codEvento);
        List<String> ingressos = new ArrayList<>();
        ingressos = gerenciadorDeArquivo.getObjetosPorAtributo(codigoEvento, 2);
        List<Ingresso> listaIngressos = new ArrayList<>();
        IngressoGravacaoHelper igh = new IngressoGravacaoHelper();
        
        for(String ingresso : ingressos){
            Ingresso objetoIngresso = igh.getObject(ingresso);
            listaIngressos.add(objetoIngresso);
        }
        
        return listaIngressos;
    }
    
    public List<Ingresso> getIngressosDisponiveisPorEvento(Evento evento){
        List<String> ingressos = gerenciadorDeArquivo.getObjetosPorAtributo(Integer.toString(evento.getCodigo()), 2);
        List<Ingresso> listaIngressos = new ArrayList<>();
        for(String ingresso : ingressos){
            Ingresso ingressoObject = getObject(ingresso);
            if(!ingressoObject.isVendido()){
                listaIngressos.add(ingressoObject);
            }
                
        }
        
        return listaIngressos;
    }
    
    public List<Ingresso> getIngressosVendidosPorEvento(Evento evento){
        List<String> ingressos = gerenciadorDeArquivo.getObjetosPorAtributo(Integer.toString(evento.getCodigo()), 2);
        List<Ingresso> listaIngressos = new ArrayList<>();
        for(String ingresso : ingressos){
            Ingresso ingressoObject = getObject(ingresso);
            if(ingressoObject.isVendido()){
                listaIngressos.add(ingressoObject);
            }
            
        }
        
        return listaIngressos;
    }
    
    public List<Ingresso> getTodosObjetosArquivoExterno() {
        List<String> listaObjetos = gerenciadorDeArquivoExterno.getLinhas();
        List<Ingresso> resultado = new ArrayList<Ingresso>();
        for(String entrada : listaObjetos){
            Ingresso ingresso = getObject(entrada);
            resultado.add(ingresso);
        }
        return resultado;
    }
    


    public Boolean importarIngressosDeArquivo(){
         List<Ingresso> ingressosImportados = new ArrayList<>();
         ingressosImportados = getTodosObjetosArquivoExterno();
         this.gravarObjetos(ingressosImportados);
         return true;
     }
    
    
    
    @Override
    public boolean remove(Ingresso ingresso) {
        return gerenciadorDeArquivo.removerLinha(Integer.toString(ingresso.getCodigo()));
    }
    
}
