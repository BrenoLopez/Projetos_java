
    package servidor;


import java.io.PrintStream;
import java.net.*;
import java.util.Scanner;



    public class ServidorClass {
        final int porta;
        ServerSocket servidor;
        Socket cliente;
        String loginServ;
        String senhaServ;

 
        
        //construtor inicializando as variaveis e inicia o servidor
        public ServidorClass() {
        this.loginServ = "breno";
        this.senhaServ = "123";
        this.porta = 12345;
        try{
        this.servidor = new ServerSocket(porta);
        System.out.println("O servidor foi inializado...");
        System.out.println("Inicializado na porta: " + servidor.getLocalPort());
        }catch(Exception e){
            System.out.println("Erro ao iniciar o servidor");
            System.out.println("Erro: " + e.getMessage());
        }
    }
  
        //fornecer conexão para o cliente 
        public void ConexaoSocket(){
          
            try{
                //O socket do cliente recebe a aceitação do servidor para se comunicar                 
                cliente = servidor.accept();  
                System.out.println("Novo Socket conectado no IP: " + cliente.getInetAddress().getHostAddress());
            }catch(Exception e){
                System.out.println("Não foi possivel estabelecer comunicação com o socket do cliente");
                System.out.println("Erro: " + e.getMessage());
            }
           
        }
    
        //lê as informações que o cliente envia  
        public void RecebeMensagem(){        
       
              try{
                    Scanner ler = new Scanner(cliente.getInputStream());
                    while(ler.hasNextLine()){
                    String entrada = ler.nextLine();
                    System.out.println("Recebi: "+ entrada);
                    }
                    
                }catch(Exception e){
                    System.out.println("Erro de comunicação");
                    System.out.println("Erro:"+e.getMessage());
                    return;
                }
       
        
        }

        /*recebe os dados de login e valida 
          após validar as informações de login retorna um resultado para que
          o metodo do servidor dê permissões ao cliente
        */
         public String ValidaLogin(){
            
             String resultado = null;
                   try{
                Scanner ler = new Scanner(cliente.getInputStream());
                String entrada = ler.nextLine();
                String entrada2 = ler.nextLine();                
                
                
                if(entrada.equals(this.loginServ)&&entrada2.equals(this.senhaServ)){  

                    resultado = "true";  
                    PrintStream saida = new PrintStream(cliente.getOutputStream());
                    saida.println(resultado);                    
                    this.RecebeMensagem();
                }    
                else{
                    resultado = "false";                      
                    PrintStream saida = new PrintStream(cliente.getOutputStream());
                    saida.println(resultado);                    
                    this.ValidaLogin();
                }
               
          }catch(Exception e){
              e.printStackTrace();              
          }
                   return resultado;
         }
         
        
            public void FecharConexao(){
            //fecha conexão do servidor            
            try{
            System.out.println("Fechando conexão do servidor ...");
            servidor.close();
            System.out.println("Conexão fechada!");
            }catch(Exception e){
                System.out.println("Erro ao encerrar conexão do servidor!");
                System.out.println("Erro: " + e.getMessage());
            }
            
        }  
        public static void main(String arg[]){
            
        ServidorClass servidor = new ServidorClass();
        servidor.ConexaoSocket();
        servidor.ValidaLogin();        
        servidor.FecharConexao();
       
    }
    }