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
import model.Cliente;
import model.Compra;
import model.Evento;
import model.Ingresso;
import model.TipoPagamento;

/**
 *
 * @author alunoinf
 */
public class CompraGravacaoHelper implements ServiceHelper<Compra>{
    
    private final String ARQUIVO = "compras.csv";
    private CSVToFile gerenciadorDeArquivo;
    private final String ARQUIVOEXTERNO = "comprasimport.csv";
    private CSVToFile gerenciadorDeArquivoExterno;
    
    
    public CompraGravacaoHelper(){
       this.gerenciadorDeArquivo = new CSVToFile(ARQUIVO);
       this.gerenciadorDeArquivoExterno = new CSVToFile(ARQUIVOEXTERNO);
    }

    @Override
    public boolean gravarObjeto(Compra compra) {
        
        String codigoIngresso = Integer.toString(compra.getIngresso().getCodigo());
        List<String> ingressosIguais = gerenciadorDeArquivo.getObjetosPorAtributo(codigoIngresso, 2);
        if(!gerenciadorDeArquivo.contem(Integer.toString(compra.getCodigo())) && ingressosIguais.isEmpty()){            
            return gerenciadorDeArquivo.gravarLinha(toLine(compra));

        }else{
            return false;
        }
    }

    @Override
    public void gravarObjetos(List<Compra> compras) {
        for(Compra compra : compras){
           this.gravarObjeto(compra);
       }
    }

    @Override
    public Compra getObjetoPorId(String id) {
        String linha = gerenciadorDeArquivo.getLinhaPorId(id);
        if(linha != null){
            Compra resultado = getObject(linha);
            return resultado;
        }else{
            return null;
        }
    }

    @Override
    public List<Compra> getTodosObjetos() {
        List<String> listaObjetos = gerenciadorDeArquivo.getLinhas();
        List<Compra> resultado = new ArrayList<>();
        for(String entrada : listaObjetos){
            Compra compra = getObject(entrada);
            resultado.add(compra);
        }
        return resultado;
    }
    
    public List<Compra> getComprasPorEvento(Evento evento){
        
        String codigoEvento = Integer.toString(evento.getCodigo());
        List<String> ingressos = new ArrayList<>();
        ingressos = gerenciadorDeArquivo.getObjetosPorAtributo(codigoEvento, 2);
        List<Ingresso> listaIngressos = new ArrayList<>();
        IngressoGravacaoHelper igh = new IngressoGravacaoHelper();
        
        for(String ingresso : ingressos){
            Ingresso objetoIngresso = igh.getObject(ingresso);
            listaIngressos.add(objetoIngresso);
        }
        
        List<String> compras = new ArrayList<>();
        List<Compra> listaCompras = new ArrayList<>();
        CompraGravacaoHelper cgh = new CompraGravacaoHelper();
        for(Ingresso ingresso : listaIngressos){
            compras = gerenciadorDeArquivo.getObjetosPorAtributo(Integer.toString(ingresso.getCodigo()), 2);
        }
        
        for(String compra : compras){
            Compra objetoCompra = getObject(compra);
            listaCompras.add(objetoCompra);
        }

        return listaCompras;
    }

    
    public Boolean contemCliente(Cliente cliente){
        List<String> clientes = gerenciadorDeArquivo.getObjetosPorAtributo(cliente.getCpf(), 1);
        if(clientes.isEmpty()){
            return false;
        }else{
            return true;
        }
    }
    

    private String toLine(Compra c){
        StringBuilder sb = new StringBuilder();
        sb.append(c.getCodigo());
        sb.append(ServiceHelper.SEPARADOR);
        sb.append(c.getCliente().getCpf());
        sb.append(ServiceHelper.SEPARADOR);
        sb.append(c.getIngresso().getCodigo());
        sb.append(ServiceHelper.SEPARADOR);
        sb.append(c.getValor().toString());
        sb.append(ServiceHelper.SEPARADOR);
        sb.append(c.getTipoPagamento().toString());
        sb.append(ServiceHelper.SEPARADOR);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String date = sdf.format(c.getData().getTime());
        sb.append(date);
        return sb.toString();
    }
    
     private Compra getObject(String line){
        String[] compra = line.split(
                String.valueOf(ServiceHelper.SEPARADOR));
        String codigo = compra[0];
        ClienteGravacaoHelper cgh = new ClienteGravacaoHelper();
        Cliente cliente = cgh.getObjetoPorId(compra[1]);
        IngressoGravacaoHelper igh = new IngressoGravacaoHelper();
        Ingresso ingresso = igh.getObjetoPorId(compra[2]);
        Float valor = Float.valueOf(compra[3]);
        TipoPagamento tipoPagamento = TipoPagamento.valueOf(compra[4]);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar data = Calendar.getInstance();
        try {
            data.setTime(sdf.parse(compra[5]));
        } catch (ParseException ex) {
            Logger.getLogger(EventoGravacaoHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public List<Compra> getTodosObjetosArquivoExterno() {
        List<String> listaObjetos = gerenciadorDeArquivoExterno.getLinhas();
        List<Compra> resultado = new ArrayList<>();
        for(String entrada : listaObjetos){
            Compra compra = getObject(entrada);
            resultado.add(compra);
        }
        return resultado;
    }
      
      /*public Boolean importarComprasDeArquivo(){
         List<Compra> comprasImportadas = new ArrayList<>();
          comprasImportadas = getTodosObjetosArquivoExterno();
         this.gravarObjetos(comprasImportadas);
         return true;
     }*/
    public Boolean importarComprasDeArquivo(){
         List<Compra> comprasImportadas = new ArrayList<>();
         comprasImportadas = getTodosObjetosArquivoExterno();
         if(comprasImportadas.isEmpty()){
             throw new RuntimeException("Não foi possível carregar a lista de compras!");
         }
         this.gravarObjetos(comprasImportadas);
         return true;
     }
    
    @Override
    public boolean remove(Compra compra) {
        return gerenciadorDeArquivo.removerLinha(Integer.toString(compra.getCodigo()));
    }
    
    
}
