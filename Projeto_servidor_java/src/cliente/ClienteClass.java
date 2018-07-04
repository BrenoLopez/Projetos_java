package cliente;


import java.io.PrintStream;
import java.net.*;
import java.util.Scanner;




public class ClienteClass {
    final int porta;
    final String enderecoIp; 
    Socket cliente;
    
    /*
     *construtor do socket do cliente 
     *inicializando as variaveis do endereço ip onde o socket irá se conectar e a porta
    */
    public ClienteClass() {
        this.porta  = 12345;
        this.enderecoIp = "127.0.0.1";
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
    public static void main (String arg[])  {
     
       ClienteClass cliente = new ClienteClass();
       cliente.Mensagem();
       cliente.FechaConexao();
        
    
    }

    
       
   
}
