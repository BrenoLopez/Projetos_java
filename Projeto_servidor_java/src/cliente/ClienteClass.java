package cliente;




import java.io.IOException;
import java.io.PrintStream;
import java.io.Serializable;
import java.net.*;
import java.util.Scanner;




public class ClienteClass implements Serializable{
  
    final int porta;
    final String enderecoIp; 
    Socket cliente;
    private String login;
    private String senha;
    /*
     *construtor do socket do cliente 
     *inicializando as variaveis do endereço ip onde o socket irá se conectar e a porta
    */
    public ClienteClass() {
        this.porta  = 12345;
        this.enderecoIp = "192.168.2.111";
        try{
        System.out.println("Iniciando conexão do cliente ...");
        this.cliente = new Socket(enderecoIp , porta);
        System.out.println("Conexão do cliente iniciada com sucesso!");
        System.out.println("No endereço de Ip: " + cliente.getInetAddress().getHostAddress());
        }catch(Exception e ){
            System.out.println("Erro ao criar conexão do cliente com o servidor!");
            System.out.println("Erro: " + e.getMessage());
            
        } 
    } 

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    
        /*
        lê as mensagens que o usuario escrever através do teclado e envia para o servidor
        */
    public void Mensagem(){
      
        try{
            
           System.out.println("Envie mensagens para o servidor:");          
           Scanner teclado = new Scanner(System.in);
           PrintStream saida = new PrintStream(cliente.getOutputStream());
            while(teclado.hasNextLine()){
            saida.println(teclado.nextLine());
          }                                   
          System.out.println("Enviando mensagem:"  + saida);
      
        }catch(Exception e){
            System.out.println("Problemas ao enviar mesagem do cliente");
            System.out.println("Erro: " + e.getMessage());
            
        }
    }
    
      public void RecebeResultado() throws IOException{
       Scanner lerResultado = new Scanner(cliente.getInputStream());
       String resultado = lerResultado.nextLine();           
        while(resultado.equals("false")){
            this.Login();
          }
        this.Mensagem();
          
      }
    /*ler login e senha do usuario depois recebe o resultado do servidor 
      indicando se o login é valido ou nao*/ 
    public void Login() throws IOException{
            
        Scanner ler = new Scanner(System.in);
        System.out.println("Login: ");        
        this.setLogin(ler.nextLine());
        PrintStream saida = new PrintStream(cliente.getOutputStream());
        saida.println(this.getLogin());
        
        System.out.println("Senha:");        
        this.setSenha(ler.nextLine()); 
        PrintStream saida2 = new PrintStream(cliente.getOutputStream());
        saida2.println(this.getSenha());   
        
        this.RecebeResultado();
     
    }
    
    
         /*
        Fecha conexão do cliente com o servidor
        */
    public void FechaConexao(){
       
        try{
            cliente.close();
        }catch(Exception e){
            System.out.println("Erro ao encerrar conexão com o servidor");
            System.out.println("Erro: " + e.getMessage());
        }
    }
    public static void main (String arg[]) throws IOException  {
     
       ClienteClass cliente = new ClienteClass();
       cliente.Login();             
       cliente.FechaConexao();
        
    
    }

    
       
   
}
