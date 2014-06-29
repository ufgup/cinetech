/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.ClienteGravacaoHelper;
import controller.CompraGravacaoHelper;
import controller.EventoGravacaoHelper;
import controller.IngressoGravacaoHelper;
import controller.ServiceHelper;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Cliente;
import model.Compra;
import model.Evento;
import model.Ingresso;
import model.Secao;
import model.TipoPagamento;
import sun.misc.Cache;

/**
 *
 * @author alunoinf
 */
public class InterfaceUsuario {
    
    private static Scanner leitor = new Scanner(System.in);

    public static void exibirMenuCliente() {
        int opcao = 0;
        do{
            System.out.println("\n\nCliente\n\n"
                            + "1 - Cadastrar Cliente\n"
                            + "2 - Listar clientes cadastrados\n"
                            + "3 - Importar clientes para a base de dados\n"
                            + "4 - Excluir cliente\n"
                            + "5 - Voltar ao menu principal\n");
            opcao = leitor.nextInt();
            
            if(opcao == 1){
                cadastrarCliente();
            }else if(opcao == 2){
                imprimeClientesDoArquivo();
            }else if(opcao == 3){
                importarClientesDoArquivo();
            }
            else if(opcao == 4){
                excluirCliente();
            }else if(opcao == 5){
                menu();
            }else{
                System.out.println("Opçao inválida.");
            }
            
        }while(opcao<1||opcao>4);
    }

    public static void exibirMenuEvento() {
        
        int opcao = 0;
        do{
            System.out.println("\n\nEvento\n\n"
                            + "1 - Cadastrar Evento\n"
                            + "2 - Listar eventos cadastrados\n"
                            + "3 - Quantidade de ingressos vendidos por evento\n"
                            + "4 - Renda total por evento\n" 
                            + "5 - Voltar ao menu principal\n");
            opcao = leitor.nextInt();
            
            if(opcao == 1){
                cadastrarEvento();
            }else if(opcao == 2){
                imprimeEventosDoArquivo();
            }else if(opcao == 3){
                ingressosVendidosPorEvento();
            }
            else if(opcao == 4){
                calcularRendaTotalDoEvento();
            }
            else if(opcao == 5){
                menu();
            }else{
                System.out.println("Opçao inválida.");
            }
            
        }while(opcao<1||opcao>32);
        
    }
    
    private static void exibirMenuCompra() {
        int opcao = 0;
        do{
            System.out.println("\n\nCompra\n\n"
                            + "1 - Efetuar uma compra\n"
                            + "2 - Importar compras para a base de dados\n"
                            + "3 - Renda total por evento\n" 
                            + "4 - Voltar ao menu principal\n");
            opcao = leitor.nextInt();
            
            if(opcao == 1){
                efetuarCompra();
            }else if(opcao == 2){
                importarComprasDoArquivo();
            }
            else if(opcao == 3){
                calcularRendaTotalDoEvento();
            }
            else if(opcao == 4){
                menu();
            }else{
                System.out.println("Opçao inválida.");
            }
            
        }while(opcao<1||opcao>32);
    }

    public static void cadastrarCliente() {
        Cliente cliente = lerCliente();
        inserirClienteNoArquivo(cliente);
        exibirMenuCliente();
    }

    public static void excluirCliente() {
        System.out.println("Digite o CPF do cliente a ser excluído: ");
        String cpf = leitor.next();
        ClienteGravacaoHelper cgh = new ClienteGravacaoHelper();
        Cliente cliente = cgh.getObjetoPorId(cpf);
        try{
            cgh.remove(cliente);
            System.out.println("Cliente removido com sucesso.");
            exibirMenuCliente();
        }catch(RuntimeException ex){
            System.err.println("Não foi possível remover o cliente: " + ex.getMessage());
            exibirMenuCliente();
        }
    }

    
    
    public static int lerInteiro(){
        
        int inteiro = leitor.nextInt();
        return inteiro;
    }
    
    public static float lerFloat(){
        
        float flutuante = leitor.nextFloat();
        return flutuante;
    }
    
    public static String lerString(){
        
        String string = leitor.next();
        return string;
    }
    
    
    public static void imprimir(Object string){
        System.out.println(string);
    }
    
    public static void imprimeCliente(Cliente cliente){
        System.out.println("Nome: " + cliente.getNome()
                        + "\nCPF: " + cliente.getCpf()
                        + "\nTelefone: " + cliente.getTelefone()
                        + "\nEndereço: " + cliente.getEndereco() 
                        + ".\n");
    }
    
    public static Cliente lerCliente(){
        
        Cliente cliente = new Cliente();
        System.out.println("Digite o nome: ");
        cliente.setNome(lerString());
        System.out.println("Digite o cpf: ");
        cliente.setCpf(lerString());
        System.out.println("Digite o telefone: ");
        cliente.setTelefone(lerString());
        System.out.println("Digite o endereço: ");
        cliente.setEndereco(lerString());
        return cliente;
    }
    
    public static void imprimeClientesDoArquivo(){
        ClienteGravacaoHelper gerenciaClientes = 
                new ClienteGravacaoHelper();
        List<Cliente> clientesGuardados = 
                gerenciaClientes.getTodosObjetos();
        for(Cliente cliente : clientesGuardados){
            imprimeCliente(cliente);
            
        }
        exibirMenuCliente();
    }
    
    public static void imprimeIngressosDoArquivo(){
        IngressoGravacaoHelper gerenciadorDeIngressos = new IngressoGravacaoHelper();
        List<Ingresso> ingressosGuardados = gerenciadorDeIngressos.getTodosObjetos();
        for(Ingresso ingresso : ingressosGuardados){
            System.out.println("Ingresso de número: " + ingresso.getCodigo() 
                            + ", da seção " + ingresso.getSecao().toString() 
                            + " está " + ingresso.estaVendido() 
                            + ".");
        }
    }
    
    public static void imprimeListaDeEventos(List<Evento> listaEventos){
        for(Evento evento : listaEventos){
            imprimeEvento(evento);
        }
    }
    
    public static void imprimeListaDeIngressos(List<Ingresso> listaIngressos){
        for(Ingresso ingresso : listaIngressos){
            imprimeIngresso(ingresso);
        }
    }
    
    public static void imprimeEventosDoArquivo(){
        EventoGravacaoHelper gerenciaEventos = 
                new EventoGravacaoHelper();
        List<Evento> EventosGuardados = 
                gerenciaEventos.getTodosObjetos();
        imprimeListaDeEventos(EventosGuardados);
        exibirMenuEvento();
    }
    
    public static void imprimeComprasDoArquivo(){
        CompraGravacaoHelper gerenciaCompras = 
                new CompraGravacaoHelper();
        List<Compra> ComprasGuardadas = 
                gerenciaCompras.getTodosObjetos();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        for(Compra compra : ComprasGuardadas){
            System.out.println("Codigo: " + compra.getCodigo()
                            + "\nCliente: " + compra.getCliente()
                            + "\nIngresso: " + compra.getIngresso().getCodigo()
                            + "\nValor: " + compra.getValor()
                            + "\nTipo de Pagamento: " + compra.getTipoPagamento()
                            + "\nData: " + sdf.format(compra.getData().getTime())+ "\n");
        }
        
        
    }
    
    public static void imprimeListaDeClientes(List<Cliente> listaClientes){
        for(Cliente cliente : listaClientes){
            imprimeCliente(cliente);
        }
    }
    
    public static void menu(){
        
        int opcao = 0;
        
            System.out.println("Digite uma opção: \n1 - Cliente\n"
                            + "2 - Evento\n"
                            + "3 - Compra\n"
                            + "4 - Ingresso\n");
            Scanner leitor = new Scanner(System.in);
            opcao = leitor.nextInt();

            if(opcao == 1){
                exibirMenuCliente();
                
            }else if(opcao == 2){
                exibirMenuEvento();
                
            }else if(opcao == 3){
                exibirMenuCompra();
                
            }else if(opcao == 4){
                
                
                        
            }
            else{
                System.out.println("Opção inválida\n");
            }
        
    }
    
    
    
    public static void main(String[] args){

        /*Cliente cliente1 = new Cliente("123456", "Bruno", "UFG", "32323232");
        Cliente cliente2 = new Cliente("123457", "Thais", "UFG", "32323232");
        Calendar data = Calendar.getInstance();
        Evento evento = new Evento(12, "Show", data, "mimimi", "UFG");
        Ingresso ingresso = new Ingresso(1, Secao.CENTRAL, evento, Boolean.TRUE);
        Ingresso ingresso2 = new Ingresso(2, Secao.CENTRAL, evento, Boolean.TRUE);
        Ingresso ingresso3 = new Ingresso(2, Secao.CENTRAL, evento, Boolean.TRUE);
        Compra compra1 = new Compra(1, cliente1, ingresso, 15f, TipoPagamento.DINHEIRO, data);
        Compra compra2 = new Compra(2, cliente1, ingresso2, 15f, TipoPagamento.CARTAO, data);
        Compra compra3 = new Compra(3, cliente2, ingresso3, 15f, TipoPagamento.CARTAO, data);
        
        ClienteGravacaoHelper cgh = new ClienteGravacaoHelper();
        List<Cliente> listaClientes = new ArrayList<>();
        listaClientes = cgh.getClientesPorEvento(evento);
        CompraGravacaoHelper compraGHelper = new CompraGravacaoHelper();
        List<Compra> listaCompras = compraGHelper.getComprasPorEvento(evento);
        System.out.println(listaCompras.size());*/
        
        /*Cliente cliente = new Cliente("123", "Bruno", "hsjdfhskl", "123456");
        Cliente cliente2 = new Cliente("456", "Thais", "hsjdfhskl", "123456");
        ClienteGravacaoHelper cgh = new ClienteGravacaoHelper();
        cgh.gravarObjeto(cliente);
        cgh.gravarObjeto(cliente2);
        Calendar data = Calendar.getInstance();
        Evento evento = new Evento(12, "Show", data, "mimimi", "UFG");
        Ingresso ingresso = new Ingresso(1, Secao.CENTRAL, evento, Boolean.TRUE);
        Compra compra1 = new Compra(1, cliente2, ingresso, 15f, TipoPagamento.DINHEIRO, data);
        CompraGravacaoHelper compraGHelper = new CompraGravacaoHelper();
        compraGHelper.gravarObjeto(compra1);
        cgh.remove(cliente2);*/
        
        menu();
        
        
       
        
        
        
        /*Calendar data = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String date1 = sdf.format(data.getTime());            
        System.out.println("Data: " + date1);*/
 
    }

    private static void inserirClienteNoArquivo(Cliente cliente) {
        ClienteGravacaoHelper cgh = new ClienteGravacaoHelper();
        try{
            cgh.gravarObjeto(cliente);
            System.out.println("Cliente cadastrado com sucesso.");
        }catch(RuntimeException ex){
            System.err.println("Não foi possível cadastrar o cliente. " + ex.getMessage());
        }
    }

    private static void cadastrarEvento() {
        Evento evento;
        try {
            evento = lerEvento();
            inserirEventoNoArquivo(evento);
            System.out.println("\nEvento cadastrado\n");
        } catch (ParseException ex) {
            System.err.println("Data inválida: " + ex.getMessage());
        }
        exibirMenuEvento();
    }

    private static Evento lerEvento() throws ParseException {
        Evento evento = new Evento();
        System.out.println("Digite o nome do evento: ");
        evento.setNome(InterfaceUsuario.lerString());
        System.out.println("Digite o código do evento: ");
        evento.setCodigo(Integer.parseInt(InterfaceUsuario.lerString()));
        System.out.println("Digite a descrição do evento: ");
        evento.setDescricao(InterfaceUsuario.lerString());
        System.out.println("Digite a data do evento no formato dd/mm/aaaa: ");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");        
        Calendar data = Calendar.getInstance();
        data.setTime(sdf.parse(InterfaceUsuario.lerString()));
        evento.setData(data);
        System.out.println("Digite o local do evento: ");
        evento.setLocal(InterfaceUsuario.lerString());
        return evento;
    }

    private static void inserirEventoNoArquivo(Evento evento) {
        
        EventoGravacaoHelper egh = new EventoGravacaoHelper();
        try{
            egh.gravarObjeto(evento);
        }catch(RuntimeException e){
            System.out.println("\nNão foi possível cadastrar o evento.\n" + e.getMessage());
        }
    }

    private static void imprimeEvento(Evento evento) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String date = sdf.format(evento.getData().getTime());
        System.out.println("\nNome: " + evento.getNome()
                        + "\nCódigo: " + evento.getCodigo()
                        + "\nDescrição: " + evento.getDescricao()
                        + "\nData: " + date
                        + "\nLocal: " + evento.getLocal()
                        + ".\n");
    }
    
    private static void imprimeIngresso(Ingresso ingresso) {
        System.out.println("Assento: " + ingresso.getCodigo()
                        + "\nEvento: " + Integer.toString(ingresso.getEvento().getCodigo())
                        + "\nSeção: " + ingresso.getSecao().toString()
                        + "\nValor: " + ingresso.getSecao().getPreco()
                        + "\n" + ingresso.estaVendido()
                        + ".\n");
    }
    
    private static void calcularRendaTotalDoEvento() {
        
    }

    private static void importarClientesDoArquivo() {
        ClienteGravacaoHelper cgh = new ClienteGravacaoHelper();
        try{
            cgh.importarClientesDeArquivo();
            System.out.println("\nImportação realizada com êxito");
            exibirMenuCliente();
        }catch(RuntimeException ex){
            System.err.println(ex.getMessage());
        }
    }


    private static void efetuarCompra() {
        
        Cliente cliente = new Cliente();
        TipoPagamento tp = TipoPagamento.CARTAO;
        try{
            cliente = checarClienteCadastrado();
        }catch(RuntimeException ex){
            System.err.println("Não é possível realizar compra. " + ex.getMessage());
            exibirMenuCliente();
        }

        exibirEventosDisponiveis();
        System.out.println("Digite o código do evento: ");
        
        String codigoEvento = leitor.next();
        imprimirIngressosDisponiveis(codigoEvento);
        System.out.println("\nDigite a posição do assento do ingresso: ");
        Integer codigoIngresso = leitor.nextInt();
        IngressoGravacaoHelper igh = new IngressoGravacaoHelper();
        Ingresso ingresso = igh.getObjetoPorId(Integer.toString(codigoIngresso));
        
        System.out.println("Selecione a forma de pagamento:\n1 - Dinheiro\n2 - Cartão\n");
        try{
            tp = definirTipoPagamento();
        }catch(RuntimeException ex){
            System.err.println(ex.getMessage());
        }
        
        String codigoCompra = Integer.toString(ingresso.getCodigo()) + Integer.toString(ingresso.getEvento().getCodigo());
        Integer codCompra = Integer.parseInt(codigoCompra);
        Calendar data = Calendar.getInstance();
        Float preco = 0f;
        Compra compra = new Compra(codCompra, cliente, ingresso, preco, tp, data);
        Float valor = calcularValor(compra);
        compra.setValor(valor);
        
        CompraGravacaoHelper compraGHelper = new CompraGravacaoHelper();
        compraGHelper.gravarObjeto(compra);
        
        System.out.println("\nCompra efetuada com sucesso.");
        imprimeCompra(compra);
        
        
    }
    
    public static Float calcularValor(Compra compra){
        
        Float valor = compra.getIngresso().getSecao().getPreco();
        
        if(compra.getTipoPagamento().equals(TipoPagamento.DINHEIRO)){
            if(valor>50f){
            valor -= valor * 0.02f;
            }
            else{
                valor = valor - 1;
            }
        }
        
        return valor;
    }

    private static Cliente checarClienteCadastrado() {
        
        System.out.println("\nDigite o cpf: ");
        String cpf = lerString();
        ClienteGravacaoHelper cgh = new ClienteGravacaoHelper();
        if(!cgh.contemCliente(cpf)){
            throw new RuntimeException("O cliente não está cadastrado. Efetue o cadastro.");
        }else{
            return cgh.getObjetoPorId(cpf);
        }
        
        
        
    }

    private static void exibirEventosDisponiveis() {
        EventoGravacaoHelper egh = new EventoGravacaoHelper();
        List<Evento> eventos = new ArrayList<>();
        eventos = egh.getListaDeEventosDisponiveis();
        imprimeListaDeEventos(eventos);
    }

    private static void imprimirIngressosDisponiveis(String codigoEvento) {
        EventoGravacaoHelper egh = new EventoGravacaoHelper();
        Evento evento = egh.getObjetoPorId(codigoEvento);
        IngressoGravacaoHelper igh = new IngressoGravacaoHelper();
        List<Ingresso> listaIngressos = new ArrayList<>();
        listaIngressos = igh.getIngressosDisponiveisPorEvento(evento);
        imprimeListaDeIngressos(listaIngressos);
    }
    
    private static void imprimirIngressosVendidos(String codigoEvento) {
        EventoGravacaoHelper egh = new EventoGravacaoHelper();
        Evento evento = egh.getObjetoPorId(codigoEvento);
        IngressoGravacaoHelper igh = new IngressoGravacaoHelper();
        List<Ingresso> listaIngressos = new ArrayList<>();
        listaIngressos = igh.getIngressosVendidosPorEvento(evento);
        List<Ingresso> inferior = new ArrayList<>();
        List<Ingresso> superior = new ArrayList<>();
        List<Ingresso> central = new ArrayList<>();
        for(Ingresso ingresso : listaIngressos){
            if(ingresso.getSecao().getCodigo() == 1){
                inferior.add(ingresso);
            }else if(ingresso.getSecao().getCodigo() == 2){
                central.add(ingresso);
            }else if(ingresso.getSecao().getCodigo() == 3){
                superior.add(ingresso);
            }
        }
        
        System.out.println("Quantidade total de ingressos vendidos: " + listaIngressos.size()
                        + "\nInferior: " + inferior.size()
                        + "\nCentral: " + central.size()
                        + "\nSuperior: " + superior.size());
        
    }

    private static TipoPagamento definirTipoPagamento() {
        Integer opcaoPagamento = leitor.nextInt();
        TipoPagamento tp = null;
        if(opcaoPagamento == 1){
            tp = TipoPagamento.DINHEIRO;
        }else if(opcaoPagamento == 2){
            tp = TipoPagamento.CARTAO;
        }else{
            throw new RuntimeException("Inválido para tipo de pagamento.");
        }
        return tp;
    }

    private static void imprimeCompra(Compra compra) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date = compra.getData().getTime();
        String data = sdf.format(date);
        System.out.println("::Informações da Compra:: \n\nCódigo: " + compra.getCodigo()
                        + "\nEvento: " + compra.getIngresso().getEvento().getNome()
                        + "\nCliente: " + compra.getCliente().getNome()
                        + "\nSeção: " + compra.getIngresso().getSecao().toString()
                        + "\nValor: " + compra.getValor().toString()
                        + "\nTipo de Pagamento: " + compra.getTipoPagamento().toString()
                        + "\nData: " + data
                        + ".\n");
    }

    private static void ingressosVendidosPorEvento() {
        System.out.println("\nDigite o código do evento: ");
        String codigoEvento = leitor.next();
        imprimirIngressosVendidos(codigoEvento);
        exibirMenuEvento();
    }

    private static void importarComprasDoArquivo() {
        CompraGravacaoHelper cgh = new CompraGravacaoHelper();
        try{
            cgh.importarComprasDeArquivo();
            System.out.println("\nImportação realizada com êxito");
            exibirMenuCompra();
        }catch(RuntimeException ex){
            System.err.println(ex.getMessage());
        }
    }
    
    
    
    
}
