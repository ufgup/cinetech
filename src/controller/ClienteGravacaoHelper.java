/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.Cliente;
import java.util.ArrayList;
import java.util.List;
import model.Compra;
import model.Evento;

/**
 * Permitir que gravemos clientes em algum lugar.
 * @author marceloquinta
 */
public class ClienteGravacaoHelper implements ServiceHelper<Cliente>{

    private final String ARQUIVO = "clientes.csv";
    private CSVToFile gerenciadorDeArquivo;
    private CSVToFile gerenciadorDeArquivoExterno;
    private String ARQUIVOEXTERNO = "clientesimport.csv";
    
    public ClienteGravacaoHelper(){
       this.gerenciadorDeArquivo = new CSVToFile(ARQUIVO);
       this.gerenciadorDeArquivoExterno = new CSVToFile(ARQUIVOEXTERNO);
    }
    
    @Override
    public boolean gravarObjeto(Cliente cliente) {
        if(!gerenciadorDeArquivo.contem(cliente.getCpf())){
            return gerenciadorDeArquivo.gravarLinha(toLine(cliente));
        }else{
            throw new RuntimeException("Já existe um cliente com esse CPF.");
        }
    }
    
    @Override
    public void gravarObjetos(List<Cliente> clientes) {
       for(Cliente individuo : clientes){
           this.gravarObjeto(individuo);
       }
    }
    
    @Override
    public Cliente getObjetoPorId(String id) {
        String linha = gerenciadorDeArquivo.getLinhaPorId(id);
        if(linha != null){
            Cliente resultado = getObject(linha);
            return resultado;
        }else{
            return null;
        }
        
    }

    @Override
    public List<Cliente> getTodosObjetos() {
        List<String> listaObjetos = gerenciadorDeArquivo.getLinhas();
        List<Cliente> resultado = new ArrayList<Cliente>();
        for(String entrada : listaObjetos){
            Cliente cliente = getObject(entrada);
            resultado.add(cliente);
        }
        return resultado;
    }
    
    
    public List<Cliente> getClientesPorEvento(Evento evento){
        
        CompraGravacaoHelper cgh = new CompraGravacaoHelper();
        List<Compra> listaCompras = new ArrayList<>();
        List<Cliente> listaClientes = new ArrayList<>();
        
        for(Compra compra : listaCompras){
            Cliente cliente = compra.getCliente();
            if(!listaClientes.contains(cliente)){
                listaClientes.add(cliente);
            }
        }
        
        return listaClientes;
    }
    
    
    
    private String toLine(Cliente p){
        StringBuilder sb = new StringBuilder();
        sb.append(p.getCpf());
        sb.append(ServiceHelper.SEPARADOR);
        sb.append(p.getNome());
        sb.append(ServiceHelper.SEPARADOR);
        sb.append(p.getEndereco());
        sb.append(ServiceHelper.SEPARADOR);
        sb.append(p.getTelefone());
        return sb.toString();
    }
    
    private Cliente getObject(String line){
        String[] cliente = line.split(
                String.valueOf(ServiceHelper.SEPARADOR));
        String cpf = cliente[0];
        String nome = cliente[1];
        String endereco = cliente[2];
        String telefone = cliente[3];
        Cliente resultado = new Cliente(cpf,nome, endereco, telefone);
        return resultado;
    }
    
    public List<Cliente> getTodosObjetosArquivoExterno() {
        List<String> listaObjetos = gerenciadorDeArquivoExterno.getLinhas();
        List<Cliente> resultado = new ArrayList<Cliente>();
        for(String entrada : listaObjetos){
            Cliente cliente = getObject(entrada);
            resultado.add(cliente);
        }
        return resultado;
    }
     
     public Boolean importarClientesDeArquivo(){
         List<Cliente> clientesImportados = new ArrayList<>();
         clientesImportados = getTodosObjetosArquivoExterno();
         if(clientesImportados.isEmpty()){
             throw new RuntimeException("Não foi possível carregar a lista de clientes");
         }
         this.gravarObjetos(clientesImportados);
         return true;
     }
     
     public Boolean contemCliente(String cpf){
         return gerenciadorDeArquivo.contem(cpf);
     }

    @Override
    public boolean remove(Cliente cliente) {
        
        CompraGravacaoHelper cgh = new CompraGravacaoHelper();
        if(cgh.contemCliente(cliente)){
            throw new RuntimeException("O cliente efetuou uma compra.");
        }
        else if(!gerenciadorDeArquivo.contem(cliente.getCpf())){
            throw new RuntimeException("Não existe cliente cadastrado com esse CPF.");
            
        }else{
            return gerenciadorDeArquivo.removerLinha(cliente.getCpf());
            
        }
        
        
    }
    
    public boolean removeByCPF(String cpf){
        return gerenciadorDeArquivo.removerLinha(cpf);
    }
}
