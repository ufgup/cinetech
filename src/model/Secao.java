package model;

/**
 *
 * @author alunoinf
 */
public enum Secao {
    
    INFERIOR (30f, 1, "Inferior"),
    CENTRAL(50f, 2, "Central"),
    SUPERIOR(70f, 3, "Superior");
    
    private final Float preco;
    private final Integer codigo;
    private final String nome;
    private Secao(Float preco, Integer codigo, String nome){
        this.preco = preco;
        this.codigo = codigo;
        this.nome = nome;
        
    }
    
    public Float getPreco(){
        return this.preco;
    }
    
    public int getCodigo(){
        return this.codigo;
    }
    
    public String toString(){
        return this.nome;
    }
    
    public static Secao valueOf(Integer codigo){
        for(Secao secao : values()){
            if(codigo == secao.getCodigo())
                return secao;
        }
        return null;
    }
    
}