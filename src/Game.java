import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

public final class Game extends JFrame implements KeyListener
{ 
    int s = 0;
    int alfa = 0;
    int gama = 0;
    int Width = 690;
    int Height = 360;    
    int LevelAt = 0;
    private int userRow, userCol, boxRow, boxCol, ptRow, ptCol, boxRow2, boxCol2, ptRow2, ptCol2, boxRow3, boxCol3, ptRow3, ptCol3;
    private int msElapsed;
    private int timesGet = 0;
    private int timesAvoid = 0;
    private int keyAnt;
    final private int [] wallVetRow = new int[100];
    final private int [] wallVetCol = new int[100]; 
    public int nivel = 10;
    public int Over = 0;
    public int Score = 0;       
    private boolean b = false;
    
    BufferedImage imgBuff;
    private Grid grid;
    Menu menu = new Menu(10, 310, 170, true);
  
    //Opções do menu (Não é o menu principal)
    public void opts(){
        Graphics fundo = imgBuff.getGraphics();
        fundo.setFont(new Font("Arial",Font.BOLD,20));
        
        //Selecionando "Jogar":
        if(menu.page == 0){
            nivel = 0;
            setVisible(false);
            Score = 0;
            game();
            
            //Game Over caso tenha iniciado o jogo pela opção "Jogar"
            if(Over == 15 || Over == 25 || Over == 35 || Over == 45) {
                alfa = 4;
                menu.a = 4;
            }
            else{
                alfa = 1;
            }
        }
        
        //Selecionando "Níveis":
        if(menu.page == 1 && alfa == 0){
            
            fundo.setColor(java.awt.Color.black);
            fundo.fillRect(0, 0, Width, Height);
            
            menu.font.setColor(menu.colorG);
            menu.font.setFont(menu.fonte3);
            menu.font.drawString("Boxzeira", 230, 100);
        
            menu.font.setFont(menu.fonte2);
            menu.font.drawString("Aperte a tecla ESC para voltar", 100, 300);
            menu.menuDN();           
            if(menu.a == 3){
                nivel = 0;
                
            if(menu.selectedItem-3 == 1){
                Score = 0;
            }
            if(menu.selectedItem-3 == 2){
                Score = 10;
            }
            if(menu.selectedItem-3 == 3){
                Score = 20;
            }
            if(menu.selectedItem-4 == 3){
                Score = 30;
            }
            setVisible(false);
           
            game();
            alfa++;
            if(Over == 15 || Over == 25 || Over == 35 || Over == 45) alfa = 4;
            if(Over != 15 && Over != 25 && Over != 35 && Over != 45)  alfa = 1;
            }                        
        }
        
        //Selecionando "Ajuda":
        if(menu.page == 2){
            
            fundo.setColor(java.awt.Color.black);
            fundo.fillRect(0, 0, Width, Height);
            menu.font.setColor(menu.colorG);
            menu.font.setFont(menu.fonte4);
            menu.font.drawString("Ajuda", 290, 100);
            menu.font.setFont(menu.fonte2);
            menu.font.setColor(menu.colorG);
            menu.font.drawString("Você deve posicionar as caixas no local correspondente a cor da caixa.", 100, 180);
            menu.font.drawString("Por exemplo: Caixa vermelha no quadrado com X vermelho", 100, 200);
            menu.font.drawString("Aperte a tecla ESC para voltar", 100, 300);
        }
        
        //Selecionando "Sair":
        if(menu.page == 3){
            System.exit(0);
        }
        
        //Final do programa ao passar por todas as fases
        if(menu.page == 4){
            setVisible(true);
            menu.font.setFont(menu.fonte);
            fundo.setColor(java.awt.Color.black);
            fundo.fillRect(0, 0, Width, Height);
            menu.font.setColor(menu.colorG);
            menu.font.drawString("Parabéns você conseguiu!", 220, 180);
            menu.font.setFont(menu.fonte2);
            menu.font.drawString("Aperte a tecla ESC para voltar ao menu principal", 100, 300);
        }
        
        //Game Over
        if(menu.page == 5){
            menu.font.setFont(menu.fonte3);
            fundo.setColor(java.awt.Color.black);
            fundo.fillRect(0, 0, Width, Height);
            menu.font.setColor(menu.colorR);
            menu.font.drawString("Game Over!", 190, 100);   
            menu.font.setFont(menu.fonte2);
            menu.font.setColor(menu.colorG);
            menu.font.drawString("Aperte a tecla ESC para voltar ao menu principal", 100, 300);
            menu.menuGO();
            
            if(menu.a == 5){
                if(menu.selectedItem == 9){
                    if(Over == 15){
                        nivel = 0;
                        Score = 0;
                        Over = 0;
                    }
                    if(Over == 25){
                        nivel = 0;
                        Score = 10;
                        Over = 0;
                    }
                    if(Over == 35){
                        nivel = 0;
                        Score = 20;
                        Over = 0;
                    }
                    if(Over == 45){
                        nivel = 0;
                        Score = 30;
                        Over = 0;
                    }
                    setVisible(false);
                    game(); 
                    menu.a--;
                }
                if(menu.selectedItem == 3)  System.exit(0);
                
                
                if(Over != 15 && Over != 25 && Over != 35 && Over != 45)  alfa = 1;
            }
        }
    }
    
    //Fecha o Grid do nível anterior
    public void close(){
        grid.dispose();
    }
    
    //Método para rodar os menus
    public void gDsn(){
        //Declaração para o menu principal
        Graphics g = getGraphics();
        Graphics fundo = imgBuff.getGraphics();
        fundo.setColor(java.awt.Color.black);
        fundo.fillRect(0, 0, Width, Height);
        
        menu.font.setColor(menu.colorG);
        menu.font.setFont(menu.fonte3);
        menu.font.drawString("Boxzeira", 230, 100);
        menu.menuD();

        if(alfa == 1){
            menu.page = 4;
            setVisible(true);
            alfa = 0;
        }
        if(alfa == 4){
            if(s == 0){
                URL sound = null;
                try {
                    sound = new URL("file:src/sound.wav");
                } catch (MalformedURLException erro) {
                    Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, erro);
                }
                    AudioClip som = Applet.newAudioClip(sound);
                    som.play();
            }
            s = 0;
            menu.page = 5;
            menu.selectedItem = 9;
            menu.ativo = true;
            setVisible(true);
            alfa = 0;
        }     
        
        opts();

        g.drawImage(imgBuff, 0, 0, this);
    }
    
    //Inicializador da interface na qual serão exibidos os menus
    public void init() {
        setTitle("Boxzeira");
        setSize(Width, Height);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setVisible(true);
        imgBuff = new BufferedImage(Width, Height, BufferedImage.TYPE_INT_RGB);
        addKeyListener(this);
  
        menu.itens[0] = "Jogar";
        menu.itens[1] = "Níveis";
        menu.itens[2] = "Ajuda";
        menu.itens[3] = "Sair";
        menu.itens[4] = "Nível 01";
        menu.itens[5] = "Nível 02";
        menu.itens[6] = "Nível 03";
        menu.itens[7] = "Nível 04";
        menu.itens[8] = "Sair";
        menu.itens[9] = "Tentar novamente";
        menu.font = imgBuff.getGraphics();
    }
    
    //Métodos necessarios para execução do KeyListener, pois ele monitora as teclas
    @Override
    public void keyPressed(KeyEvent e) {
        menu.exec(e);
        menu.menuR(e);
    }
    
    @Override
    public void keyTyped(KeyEvent e) {       
    }

    @Override
    public void keyReleased(KeyEvent e) {        
    }  
  
    //Método principal do Jogo
    public void game(){
        if(nivel == 0){ //Inicializa o nível 1.
            if(gama == 1) Clear();
            Level01();
            while (Score < 10){
                Grid.pause(100);
                handleKeyPress();
                if (msElapsed % 200 == 0){
                    updateGameBoard(); 
                }
                updateTitle();
                msElapsed += 100;
                
                //Carrega a imagem do ponto novamente, caso o usuário passe por ele
                if(userRow+1 == ptRow && boxRow != ptRow || userRow+1 == ptRow && boxCol != ptCol || userRow-1 == ptRow && 
                    boxRow != ptRow || userRow-1 == ptRow && boxCol != ptCol || userCol+1 == ptCol && boxRow != ptRow || 
                    userCol+1 == ptCol && boxCol != ptCol || userCol-1 == ptCol && boxRow != ptRow || userCol-1 == ptCol &&
                        boxCol != ptCol){
                        grid.setImage(new Location(ptRow, ptCol), "point.gif");
                }
                
                //Movimentação das caixas
                if(userCol == boxCol && userRow == boxRow) {
                    //Direita
                    if(keyAnt == 39 && boxCol < 30){
                        boxCol++;          
                        grid.setImage(new Location(boxRow, boxCol), "box.gif");
                    }          
                    //Esquerda
                    if(keyAnt == 37 && boxCol > 0){
                        boxCol--;         
                        grid.setImage(new Location(boxRow, boxCol), "box.gif");
                    }
                    //Cima
                    if(keyAnt == 38 & boxRow > 0) { 
                        boxRow--;
                        grid.setImage(new Location(boxRow, boxCol), "box.gif");
                    }
                    //Baixo
                    if(keyAnt == 40 & boxRow < 15) {
                        boxRow++;
                        grid.setImage(new Location(boxRow, boxCol), "box.gif");
                    } 
                }
                if(boxRow == ptRow && boxCol == ptCol || boxRow == ptRow2 && boxCol == ptCol2 || boxRow == ptRow3 && boxCol == ptCol3){
                    grid.setImage(new Location(boxRow, boxCol), "box.gif");
                }
                if(boxRow == ptRow && boxCol == ptCol){
                    Score = 10;
                }

                //Game Over ao encostar a caixa na parede e não ter mais como tirar
                if(boxRow-1 == 4 && boxCol >= 10 && boxCol <= 22 || boxCol+1 == 22 && boxRow >= 4 && boxRow <= 11 || 
                        boxRow+1 == 11 && boxCol >= 10 && boxCol <= 22 || boxCol-1 == 10 && boxRow >= 4 && boxRow <= 11){
                    Over = 15;
                    nivel = 3;
                    Score = 40;
                    alfa = 4;
                }
            }
            nivel = 1;
            if(Score == 40) nivel = 4;
        }    
        if(nivel == 1){
            Clear();
            Level02();
            while(Score == 10) {        
                Grid.pause(100);
                handleKeyPress();
                if (msElapsed % 200 == 0){
                    updateGameBoard(); 
                }
                updateTitle();
                msElapsed += 100;
                
                //Carrega as imagens dos pontos novamente, caso o usuário passe por ele
                if(userRow+1 == ptRow && boxRow != ptRow || userRow+1 == ptRow && boxCol != ptCol || userRow-1 == ptRow && 
                      boxRow != ptRow || userRow-1 == ptRow && boxCol != ptCol || userCol+1 == ptCol && boxRow != ptRow || 
                      userCol+1 == ptCol && boxCol != ptCol || userCol-1 == ptCol && boxRow != ptRow || userCol-1 == ptCol &&
                      boxCol != ptCol){
                    grid.setImage(new Location(ptRow, ptCol), "point.gif");
                }
                if(userRow+1 == ptRow2 && boxRow2 != ptRow2 || userRow+1 == ptRow2 && boxCol2 != ptCol2 || userRow-1 == ptRow2 && 
                      boxRow2 != ptRow2 || userRow-1 == ptRow2 && boxCol2 != ptCol2 || userCol+1 == ptCol2 && boxRow2 != ptRow2 || 
                      userCol+1 == ptCol2 && boxCol2 != ptCol2 || userCol-1 == ptCol2 && boxRow2 != ptRow2 || userCol-1 == ptCol2 &&
                      boxCol2 != ptCol2){
                    grid.setImage(new Location(ptRow2, ptCol2), "point2.gif");
                }
                
                //Movimentação das caixas
                if(userCol == boxCol && userRow == boxRow) {
                    //Direita
                    if(keyAnt == 39 && boxCol < 30){
                        boxCol++;          
                        grid.setImage(new Location(boxRow, boxCol), "box.gif");
                    }          
                    //Esquerda
                    if(keyAnt == 37 && boxCol > 0){
                        boxCol--;         
                        grid.setImage(new Location(boxRow, boxCol), "box.gif");
                    }
                    //Cima
                    if(keyAnt == 38 & boxRow > 0) { 
                        boxRow--;
                        grid.setImage(new Location(boxRow, boxCol), "box.gif");
                    }
                    //Baixo
                    if(keyAnt == 40 & boxRow < 15) {
                        boxRow++;
                        grid.setImage(new Location(boxRow, boxCol), "box.gif");
                    } 
                }
                if(userCol == boxCol2 && userRow == boxRow2) {
                    //Direita
                    if(keyAnt == 39 && boxCol2 < 30){
                        boxCol2++;          
                        grid.setImage(new Location(boxRow2, boxCol2), "box2.gif");
                    }          
                    //Esquerda
                    if(keyAnt == 37 && boxCol2 > 0){
                        boxCol2--;         
                        grid.setImage(new Location(boxRow2, boxCol2), "box2.gif");
                    }
                    //Cima
                    if(keyAnt == 38 & boxRow2 > 0) { 
                        boxRow2--;
                        grid.setImage(new Location(boxRow2, boxCol2), "box2.gif");
                    }
                    //Baixo
                    if(keyAnt == 40 & boxRow2 < 15) {
                        boxRow2++;
                        grid.setImage(new Location(boxRow2, boxCol2), "box2.gif");
                    } 
                }
                
                
                if(boxRow == ptRow && boxCol == ptCol || boxRow == ptRow2 && boxCol == ptCol2 || boxRow == ptRow3 && boxCol == ptCol3){
                    grid.setImage(new Location(boxRow, boxCol), "box.gif");
                }
                if(boxRow2 == ptRow && boxCol2 == ptCol || boxRow2 == ptRow2 && boxCol2 == ptCol2 || boxRow2 == ptRow3 && boxCol2 == ptCol3){
                    grid.setImage(new Location(boxRow2, boxCol2), "box2.gif");
                }
                if(boxRow == ptRow && boxCol == ptCol && boxRow2 == ptRow2 && boxCol2 == ptCol2){
                    Score = 20;
                }
                
                //Game Over ao encostar a caixa na parede e não ter mais como tirar
                if(boxRow-1 == 3 && boxCol >= 10 && boxCol <= 22 ||  boxCol+1 == 22 && boxRow >= 3 && boxCol+1 == 22 && boxRow <= 11 || 
                        boxRow+1 == 11 && boxCol <= 22 && boxCol >= 17 || boxCol-1 == 10 && boxRow <= 7 && boxRow >= 4 || 
                        boxRow2-1 == 3 && boxCol2 >= 10 && boxCol2 <= 22 ||  boxCol2+1 == 22 && boxRow2 >= 3 && boxRow2 <= 11 || 
                        boxRow2+1 == 11 && boxCol2 <= 22 && boxCol2 >= 17 || boxCol2-1 == 10 && boxRow2 <= 7 && boxRow2 >= 4 || 
                        boxCol-1 == 17 && boxCol == boxCol2 && boxRow+1 == boxRow2 && boxRow <= 11 && boxRow >= 7 || 
                        boxCol-1 == 17 && boxCol == boxCol2 && boxRow-1 == boxRow2 && boxRow <= 11 && boxRow >= 7 || 
                        boxRow+1 == 7 && boxRow == boxRow2 && boxCol+1 == boxCol2 || boxRow+1 == 7 && boxRow == boxRow2 && boxCol-1 == boxCol2){
                    Over = 25;
                    Score = 40;
                    alfa = 4;
                }
            }
        nivel = 2;
        if(Score == 40) nivel = 4;
        }    
    
        if(nivel == 2){
        Clear();
        Level03();
        while(Score == 20) {        
            Grid.pause(100);
            handleKeyPress();
            if (msElapsed % 200 == 0){
                updateGameBoard(); 
            }
            updateTitle();
            msElapsed += 100;
            if(userRow+1 == ptRow && boxRow != ptRow || userRow+1 == ptRow && boxCol != ptCol || userRow-1 == ptRow && 
                      boxRow != ptRow || userRow-1 == ptRow && boxCol != ptCol || userCol+1 == ptCol && boxRow != ptRow || 
                      userCol+1 == ptCol && boxCol != ptCol || userCol-1 == ptCol && boxRow != ptRow || userCol-1 == ptCol &&
                      boxCol != ptCol){
                grid.setImage(new Location(ptRow, ptCol), "point.gif");
            }
            if(userRow+1 == ptRow2 && boxRow2 != ptRow2 || userRow+1 == ptRow2 && boxCol2 != ptCol2 || userRow-1 == ptRow2 && 
                      boxRow2 != ptRow2 || userRow-1 == ptRow2 && boxCol2 != ptCol2 || userCol+1 == ptCol2 && boxRow2 != ptRow2 || 
                      userCol+1 == ptCol2 && boxCol2 != ptCol2 || userCol-1 == ptCol2 && boxRow2 != ptRow2 || userCol-1 == ptCol2 &&
                      boxCol2 != ptCol2){
                grid.setImage(new Location(ptRow2, ptCol2), "point2.gif");
            }
            if(userRow+1 == ptRow3 && boxRow3 != ptRow3 || userRow+1 == ptRow3 && boxCol3 != ptCol3 || userRow-1 == ptRow3 && 
                      boxRow3 != ptRow3 || userRow-1 == ptRow3 && boxCol3 != ptCol3 || userCol+1 == ptCol3 && boxRow3 != ptRow3 || 
                      userCol+1 == ptCol3 && boxCol3 != ptCol3 || userCol-1 == ptCol3 && boxRow2 != ptRow3 || userCol-1 == ptCol3 &&
                      boxCol3 != ptCol3){
                grid.setImage(new Location(ptRow3, ptCol3), "point3.gif");
            }
            if(userCol == boxCol && userRow == boxRow) {
                //Direita
                if(keyAnt == 39 && boxCol < 30){
                    boxCol++;          
                    grid.setImage(new Location(boxRow, boxCol), "box.gif");
                }          
                //Esquerda
                if(keyAnt == 37 && boxCol > 0){
                    boxCol--;         
                    grid.setImage(new Location(boxRow, boxCol), "box.gif");
                }
                //Cima
                if(keyAnt == 38 & boxRow > 0) { 
                    boxRow--;
                    grid.setImage(new Location(boxRow, boxCol), "box.gif");
                }
                //Baixo
                if(keyAnt == 40 & boxRow < 15) {
                    boxRow++;
                    grid.setImage(new Location(boxRow, boxCol), "box.gif");
                } 
            }
            if(userCol == boxCol2 && userRow == boxRow2) {
                //Direita
                if(keyAnt == 39 && boxCol2 < 30){
                    boxCol2++;          
                    grid.setImage(new Location(boxRow2, boxCol2), "box2.gif");
                }          
                //Esquerda
                if(keyAnt == 37 && boxCol2 > 0){
                    boxCol2--;         
                    grid.setImage(new Location(boxRow2, boxCol2), "box2.gif");
                }
                //Cima
                if(keyAnt == 38 & boxRow2 > 0) { 
                    boxRow2--;
                    grid.setImage(new Location(boxRow2, boxCol2), "box2.gif");
                }
                //Baixo
                if(keyAnt == 40 & boxRow2 < 15) {
                    boxRow2++;
                    grid.setImage(new Location(boxRow2, boxCol2), "box2.gif");
                } 
            }
          
            if(userCol == boxCol3 && userRow == boxRow3) {
                //Direita
                if(keyAnt == 39 && boxCol3 < 30){
                    boxCol3++;          
                    grid.setImage(new Location(boxRow3, boxCol3), "box3.gif");
                }          
                //Esquerda
                if(keyAnt == 37 && boxCol3 > 0){
                    boxCol3--;         
                    grid.setImage(new Location(boxRow3, boxCol3), "box3.gif");
                }
                //Cima
                if(keyAnt == 38 & boxRow3 > 0) { 
                    boxRow3--;
                    grid.setImage(new Location(boxRow3, boxCol3), "box3.gif");
                }
                //Baixo
                if(keyAnt == 40 & boxRow3 < 15) {
                    boxRow3++;
                    grid.setImage(new Location(boxRow3, boxCol3), "box3.gif");
                } 
            }
            if(boxRow == ptRow && boxCol == ptCol || boxRow == ptRow2 && boxCol == ptCol2 || boxRow == ptRow3 && boxCol == ptCol3){
                grid.setImage(new Location(boxRow, boxCol), "box.gif");
            }
            if(boxRow2 == ptRow && boxCol2 == ptCol || boxRow2 == ptRow2 && boxCol2 == ptCol2 || boxRow2 == ptRow3 && boxCol2 == ptCol3){
                grid.setImage(new Location(boxRow2, boxCol2), "box2.gif");
            }
            if(boxRow3 == ptRow && boxCol3 == ptCol || boxRow3 == ptRow2 && boxCol3 == ptCol2 || boxRow3 == ptRow3 && boxCol3 == ptCol3){
                grid.setImage(new Location(boxRow3, boxCol3), "box3.gif");
            }
          
            if(boxRow == ptRow && boxCol == ptCol && boxRow2 == ptRow2 && boxCol2 == ptCol2 && boxRow3 == ptRow3 && boxCol3 == ptCol3){
                Score = 30;
            }
            
            //Game Over ao encostar a caixa na parede e não ter mais como tirar
            if(boxRow-1 == 3 && boxCol >= 10 && boxCol <= 18 || boxRow-1 == 7 && boxCol >= 19 && boxCol <= 22 || 
                    boxCol+1 == 22 && boxRow >= 7 && boxRow <= 11 || boxRow+1 == 11 && boxCol <= 22 && boxCol >= 13 ||
                    boxCol-1 == 10 && boxRow >= 4 && boxRow <= 7 || boxRow2-1 == 3 && boxCol2 >= 10 && boxCol2 <= 18 ||  
                    boxRow2-1 == 7 && boxCol2 >= 21 && boxCol2 <= 22 || boxCol2+1 == 22 && boxRow2 >= 7 && boxRow2 <= 11 || 
                    boxRow2+1 == 11 && boxCol2 <= 22 && boxCol2 >= 13 || boxCol2-1 == 10 && boxRow2 >= 4 && boxRow2 <= 7 || 
                    boxRow3-1 == 3 && boxCol3 >= 10 && boxCol3 <= 18 ||  boxRow3-1 == 7 && boxCol3 >= 19 && boxCol3 <= 22 || 
                    boxCol3+1 == 22 && boxRow3 >= 7 && boxRow3 <= 11 || boxRow3+1 == 11 && boxCol3 <= 22 && boxCol3 >= 13 || 
                    boxCol3-1 == 10 && boxRow3 >= 4 && boxRow3 <= 7){
                Over = 35;
                Score = 40;
                alfa = 4;                
            }
        }
        nivel = 3;
        if(Score == 40) nivel = 4;
        }
        
        if(nivel == 3){
        Clear(); 
        try {
                Thread.sleep(100);
        } catch (InterruptedException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        Level04();
        while(Score == 30) {        
            Grid.pause(100);
            handleKeyPress();
            if (msElapsed % 200 == 0){
                updateGameBoard(); 
            }
            updateTitle();
            msElapsed += 100;
            if(userRow+1 == ptRow && boxRow != ptRow || userRow+1 == ptRow && boxCol != ptCol || userRow-1 == ptRow && 
                      boxRow != ptRow || userRow-1 == ptRow && boxCol != ptCol || userCol+1 == ptCol && boxRow != ptRow || 
                      userCol+1 == ptCol && boxCol != ptCol || userCol-1 == ptCol && boxRow != ptRow || userCol-1 == ptCol &&
                      boxCol != ptCol){
                grid.setImage(new Location(ptRow, ptCol), "point.gif");
            }
            if(userRow+1 == ptRow2 && boxRow2 != ptRow2 || userRow+1 == ptRow2 && boxCol2 != ptCol2 || userRow-1 == ptRow2 && 
                      boxRow2 != ptRow2 || userRow-1 == ptRow2 && boxCol2 != ptCol2 || userCol+1 == ptCol2 && boxRow2 != ptRow2 || 
                      userCol+1 == ptCol2 && boxCol2 != ptCol2 || userCol-1 == ptCol2 && boxRow2 != ptRow2 || userCol-1 == ptCol2 &&
                      boxCol2 != ptCol2){
                grid.setImage(new Location(ptRow2, ptCol2), "point2.gif");
            }
            if(userRow+1 == ptRow3 && boxRow3 != ptRow3 || userRow+1 == ptRow3 && boxCol3 != ptCol3 || userRow-1 == ptRow3 && 
                      boxRow3 != ptRow3 || userRow-1 == ptRow3 && boxCol3 != ptCol3 || userCol+1 == ptCol3 && boxRow3 != ptRow3 || 
                      userCol+1 == ptCol3 && boxCol3 != ptCol3 || userCol-1 == ptCol3 && boxRow2 != ptRow3 || userCol-1 == ptCol3 &&
                      boxCol3 != ptCol3){
                grid.setImage(new Location(ptRow3, ptCol3), "point3.gif");
            }
            if(userCol == boxCol && userRow == boxRow) {
                //Direita
                if(keyAnt == 39 && boxCol < 30){
                    boxCol++;          
                    grid.setImage(new Location(boxRow, boxCol), "box.gif");
                }          
                //Esquerda
                if(keyAnt == 37 && boxCol > 0){
                    boxCol--;         
                    grid.setImage(new Location(boxRow, boxCol), "box.gif");
                }
                //Cima
                if(keyAnt == 38 & boxRow > 0) { 
                    boxRow--;
                    grid.setImage(new Location(boxRow, boxCol), "box.gif");
                }
                //Baixo
                if(keyAnt == 40 & boxRow < 15) {
                    boxRow++;
                    grid.setImage(new Location(boxRow, boxCol), "box.gif");
                } 
            }
            if(userCol == boxCol2 && userRow == boxRow2) {
                //Direita
                if(keyAnt == 39 && boxCol2 < 30){
                    boxCol2++;          
                    grid.setImage(new Location(boxRow2, boxCol2), "box2.gif");
                }          
                //Esquerda
                if(keyAnt == 37 && boxCol2 > 0){
                    boxCol2--;         
                    grid.setImage(new Location(boxRow2, boxCol2), "box2.gif");
                }
                //Cima
                if(keyAnt == 38 & boxRow2 > 0) { 
                    boxRow2--;
                    grid.setImage(new Location(boxRow2, boxCol2), "box2.gif");
                }
                //Baixo
                if(keyAnt == 40 & boxRow2 < 15) {
                    boxRow2++;
                    grid.setImage(new Location(boxRow2, boxCol2), "box2.gif");
                } 
            }
          
            if(userCol == boxCol3 && userRow == boxRow3) {
                //Direita
                if(keyAnt == 39 && boxCol3 < 30){
                    boxCol3++;          
                    grid.setImage(new Location(boxRow3, boxCol3), "box3.gif");
                }          
                //Esquerda
                if(keyAnt == 37 && boxCol3 > 0){
                    boxCol3--;         
                    grid.setImage(new Location(boxRow3, boxCol3), "box3.gif");
                }
                //Cima
                if(keyAnt == 38 & boxRow3 > 0) { 
                    boxRow3--;
                    grid.setImage(new Location(boxRow3, boxCol3), "box3.gif");
                }
                //Baixo
                if(keyAnt == 40 & boxRow3 < 15) {
                    boxRow3++;
                    grid.setImage(new Location(boxRow3, boxCol3), "box3.gif");
                } 
            }
            if(boxRow == ptRow && boxCol == ptCol || boxRow == ptRow2 && boxCol == ptCol2 || boxRow == ptRow3 && boxCol == ptCol3){
                grid.setImage(new Location(boxRow, boxCol), "box.gif");
            }
            if(boxRow2 == ptRow && boxCol2 == ptCol || boxRow2 == ptRow2 && boxCol2 == ptCol2 || boxRow2 == ptRow3 && boxCol2 == ptCol3){
                grid.setImage(new Location(boxRow2, boxCol2), "box2.gif");
            }
            if(boxRow3 == ptRow && boxCol3 == ptCol || boxRow3 == ptRow2 && boxCol3 == ptCol2 || boxRow3 == ptRow3 && boxCol3 == ptCol3){
                grid.setImage(new Location(boxRow3, boxCol3), "box3.gif");
            }
          
            if(boxRow == ptRow && boxCol == ptCol && boxRow2 == ptRow2 && boxCol2 == ptCol2 && boxRow3 == ptRow3 && boxCol3 == ptCol3){
                Score = 40;
                gama = 1;
                menu.soundM(0);
            }
            
            //Game Over ao encostar a caixa na parede e não ter mais como tirar
            if(boxRow3 == 9 && boxCol3 == 17 || boxRow == 3 && boxCol == 15 || boxRow2 == 3 && boxCol2 == 15 || 
                    boxRow3 == 3 && boxCol3 == 15 || boxRow == 7 && boxCol == 17 || boxRow2 == 7 && boxCol2 == 17 || 
                    boxRow3 == 7 && boxCol3 == 17|| boxCol2+1 == 20 || boxCol3+1 == 20 || boxRow+1 == 13 || 
                    boxRow2+1 == 13 || boxRow3+1 == 13 || boxCol-1 == 12 || boxCol2-1 == 12 && boxRow2 >= 5 || 
                    boxCol3-1 == 12 || boxRow == ptRow2 && boxCol == ptCol2 || boxRow == ptRow3 && boxCol == ptCol3 || 
                    boxRow2 == ptRow && boxCol2 == ptCol || boxRow2 == ptRow3 && boxCol2 == ptCol3 ||
                    boxRow3 == ptRow && boxCol3 == ptCol || boxRow3 == ptRow2 && boxCol3 == ptCol2 ||
                    boxRow == 8 && boxCol == 14 || boxRow2 == 8 && boxCol2 == 14 || boxRow3 == 8 && boxCol3 == 14 || 
                    boxCol+1 == 17 && boxCol2-1 == 16 && boxRow == boxRow2 || 
                    boxCol2+1 == 17 && boxCol-1 == 16 && boxRow == boxRow2 || 
                    boxCol+1 == 18 && boxCol2-1 == 17 && boxRow == boxRow2 ||
                    boxCol2+1 == 18 && boxCol-1 == 17 && boxRow == boxRow2){
                Over = 45;
                Score = 50;
                alfa = 4;                
            }
        }
        nivel = 3;
        ptRow = 0;
        ptCol = 0;
        ptRow2 = 0;
        ptCol2 = 0;
        ptRow3 = 0;
        ptCol3 = 0;
        }        
        close();
    }
 
    public void play(){    
        init();
        while (true) {
            gDsn();
        }
    }  
  
    public void handleKeyPress(){
        int key = grid.checkLastKeyPressed();

        //Direita
        if(key == 39 && userCol < 30){
            if(boxCol < 30 || userCol+1 != boxCol || userRow != boxRow) {
                if(boxRow3 == ptRow3 && boxCol3 == ptCol3 && userRow == ptRow3 && userCol+1 == ptCol3){
                    b = true;
                }
                if(boxRow2 == ptRow2 && boxCol2 == ptCol2 && userRow == ptRow2 && userCol+1 == ptCol2){
                    b = true;
                }
                if(boxRow == ptRow && boxCol == ptCol && userRow == ptRow && userCol+1 == ptCol){
                    b = true;
                }
                else {
                    for(int i = 0; i < 100; i++) {
                            if(userCol+1 == wallVetCol[i] && userRow == wallVetRow[i] || boxCol+1 == wallVetCol[i] && 
                                        userCol+1 == wallVetCol[i]-1 && userRow == wallVetRow[i] || boxCol+1 == boxCol2 && 
                                        userRow == boxRow2 && boxRow == boxRow2 && userCol+1 == boxCol || boxCol+1 == boxCol3 && 
                                        userRow == boxRow3 && boxRow == boxRow3 && userCol+1 == boxCol) {
                                    b = true;
                                if(userRow != boxRow){
                                    b = true;
                                    if(userCol+1 == wallVetCol[i]){
                                        b = true;
                                    }
                                    else {
                                        b = false;
                                    }
                                }
                            }
                            if(userCol+1 == wallVetCol[i] && userRow == wallVetRow[i] || boxCol2+1 == wallVetCol[i] && 
                                    userCol+1 == wallVetCol[i]-1 && userRow == wallVetRow[i] || boxCol2+1 == boxCol && 
                                    userRow == boxRow2 && boxRow == boxRow2 && userCol+1 == boxCol2 || boxCol2+1 == boxCol3 && 
                                    userRow == boxRow3 && boxRow2 == boxRow3 && userCol+1 == boxCol2) {
                                b = true;
                                if(userRow != boxRow2){
                                    b = true;
                                    if(userCol+1 == wallVetCol[i] || userCol+2 == wallVetCol[i] && userRow == boxRow && 
                                            userCol+1 == boxCol || userCol+2 == wallVetCol[i] && userRow == boxRow3 && 
                                            userCol+1 == boxCol3){
                                        b = true;
                                    }
                                    else {
                                        b = false;
                                    }
                                }
                            }
                            if(userCol+1 == wallVetCol[i] && userRow == wallVetRow[i] || boxCol3+1 == wallVetCol[i] && 
                                    userCol+1 == wallVetCol[i]-1 && userRow == wallVetRow[i] || boxCol3+1 == boxCol && 
                                    userRow == boxRow3 && boxRow == boxRow3 && userCol+1 == boxCol3 || boxCol3+1 == boxCol2 && 
                                    userRow == boxRow3 && boxRow2 == boxRow3 && userCol+1 == boxCol3) {
                                b = true;
                                if(userRow != boxRow3){
                                    b = true;
                                    if(userCol+1 == wallVetCol[i] || userCol+2 == wallVetCol[i] && userRow == boxRow &&
                                            userCol+1 == boxCol || userCol+2 == wallVetCol[i] && userRow == boxRow2 &&
                                            userCol+1 == boxCol2){
                                        b = true;
                                    }
                                    else {
                                        b = false;
                                    }
                                }
                            }
                            
                    }
                
                }
                if(b == false) {
                    grid.setImage(new Location(userRow, userCol), null);
                    userCol++;
                    keyAnt = 39;
                    grid.setImage(new Location(userRow, userCol), "user.gif");
                }
                b = false;
            }
        }     
      
        //Esquerda
        if(key == 37 && userCol > 0) {
            if(boxCol > 0 || userCol-1 != boxCol || userRow != boxRow) {
                if(boxRow3 == ptRow3 && boxCol3 == ptCol3 && userRow == ptRow3 && userCol-1 == ptCol3){
                    b = true;
                }
                if(boxRow2 == ptRow2 && boxCol2 == ptCol2 && userRow == ptRow2 && userCol-1 == ptCol2){
                    b = true;
                }
                if(boxRow == ptRow && boxCol == ptCol && userRow == ptRow && userCol-1 == ptCol){
                    b = true;
                }
                else{
                    for(int i = 0; i < 100; i++){
                        if(userCol-1 == wallVetCol[i] && userRow == wallVetRow[i] || boxCol-1 == wallVetCol[i] && 
                                    userCol-1 == wallVetCol[i]+1 && userRow == wallVetRow[i] || boxCol-1 == boxCol2 && 
                                    userRow == boxRow2 && boxRow == boxRow2 && userCol-1 == boxCol || boxCol-1 == boxCol3 && 
                                    userRow == boxRow3 && boxRow == boxRow3 && userCol-1 == boxCol){
                            b = true;
                            if(userRow != boxRow){
                                b = true;
                                if(userCol-1 == wallVetCol[i]){
                                    b = true;
                                }
                                else{
                                    b = false;
                                }
                            }
                        }
                        if(userCol-1 == wallVetCol[i] && userRow == wallVetRow[i] || boxCol2-1 == wallVetCol[i] && 
                                    userCol-1 == wallVetCol[i]+1 && userRow == wallVetRow[i] || boxCol2-1 == boxCol && 
                                    userRow == boxRow2 && boxRow == boxRow2 && userCol-1 == boxCol2 || boxCol2-1 == boxCol3 && 
                                    userRow == boxRow3 && boxRow2 == boxRow3 && userCol-1 == boxCol2){
                            b = true;
                            if(userRow != boxRow2){
                                b = true;
                                if(userCol-1 == wallVetCol[i] || userCol-2 == wallVetCol[i] && userRow == boxRow && 
                                            userCol-1 == boxCol || userCol-2 == wallVetCol[i] && userRow == boxRow3 && 
                                            userCol-1 == boxCol3){
                                    b = true;
                                }
                                else {
                                    b = false;
                                }
                            }
                        }
                        if(userCol-1 == wallVetCol[i] && userRow == wallVetRow[i] || boxCol3-1 == wallVetCol[i] && 
                                    userCol-1 == wallVetCol[i]+1 && userRow == wallVetRow[i] || boxCol3-1 == boxCol && 
                                    userRow == boxRow3 && boxRow == boxRow3 && userCol-1 == boxCol3 || boxCol3-1 == boxCol2 && 
                                    userRow == boxRow3 && boxRow2 == boxRow3 && userCol-1 == boxCol3){
                            b = true;
                            if(userRow != boxRow3){
                                b = true;
                                if(userCol-1 == wallVetCol[i] || userCol-2 == wallVetCol[i] && userRow == boxRow  &&
                                        userCol-1 == boxCol || userCol-2 == wallVetCol[i] && userRow == boxRow2 && 
                                        userCol-1 == boxCol2){
                                    b = true;
                                }
                                else {
                                    b = false;
                                }
                            }
                        }
                    }
                }
              
                if(b == false) {
                    grid.setImage(new Location(userRow, userCol), null);
                    userCol--;
                    keyAnt = 37;
                    grid.setImage(new Location(userRow, userCol), "user.gif");
                }
                b = false;
            }
        }
      
        //Cima
        if(key == 38 & userRow > 0) {
            if (boxRow > 0 || userRow-1 != boxRow || userCol != boxCol) {
                if(boxRow3 == ptRow3 && boxCol3 == ptCol3 && userRow-1 == ptRow3 && userCol == ptCol3){
                    b = true;
                }
                if(boxRow2 == ptRow2 && boxCol2 == ptCol2 && userRow-1 == ptRow2 && userCol == ptCol2){
                    b = true;
                }
                if(boxRow == ptRow && boxCol == ptCol && userRow-1 == ptRow && userCol == ptCol){
                    b = true;
                }
                else{
                    for(int i = 0; i < 100; i++){
                        if(userRow-1 == wallVetRow[i] && userCol == wallVetCol[i] || boxRow-1 == wallVetRow[i] && 
                                    userRow-1 == wallVetRow[i]+1 && userCol == wallVetCol[i] || boxRow-1 == boxRow2 && 
                                    userCol == boxCol2 && boxCol == boxCol2 && userRow-1 == boxRow || boxRow-1 == boxRow3 && 
                                    userCol == boxCol3 && boxCol == boxCol3 && userRow-1 == boxRow){
                            b = true;
                            if(userCol != boxCol){
                                b = true;
                                if(userRow-1 == wallVetRow[i] || userRow-2 == wallVetRow[i] && userCol == boxCol && 
                                        userRow-1 == boxRow || userRow-2 == wallVetRow[i] && userCol == boxCol3 && 
                                        userRow-1 == boxRow3){
                                    b = true;
                                }
                                else {
                                    b = false;
                                }
                            }
                        }
                        if(userRow-1 == wallVetRow[i] && userCol == wallVetCol[i] || boxRow2-1 == wallVetRow[i] && 
                                    userRow-1 == wallVetRow[i]+1 && userCol == wallVetCol[i] || boxRow2-1 == boxRow && 
                                    userCol == boxCol2 && boxCol == boxCol2 && userRow-1 == boxRow2 || boxRow2-1 == boxRow3 && 
                                    userCol == boxCol3 && boxCol2 == boxCol3 && userRow-1 == boxRow2){
                            b = true;
                            if(userCol != boxCol2){
                                b = true;
                                if(userRow-1 == wallVetRow[i] || userRow-2 == wallVetRow[i] && userCol == boxCol &&
                                           userRow-1 == boxRow || userRow-2 == wallVetRow[i] && userCol == boxCol3 && 
                                            userRow-1 == boxRow2){
                                    b = true;
                                }
                                else {
                                    b = false;
                                }
                            }
                        }
                        if(userRow-1 == wallVetRow[i] && userCol == wallVetCol[i] || boxRow3-1 == wallVetRow[i] && 
                                    userRow-1 == wallVetRow[i]+1 && userCol == wallVetCol[i] || boxRow3-1 == boxRow && 
                                    userCol == boxCol3 && boxCol == boxCol3 && userRow-1 == boxRow3 || boxRow3-1 == boxRow2 && 
                                    userCol == boxCol3 && boxCol2 == boxCol3 && userRow-1 == boxRow3){
                            b = true;
                            if(userCol != boxCol3){
                                b = true;
                                if(userRow-1 == wallVetRow[i] || userRow-2 == wallVetRow[i] && userCol == boxCol &&
                                        userRow-1 == boxRow || userRow-2 == wallVetRow[i] && userCol == boxCol2 &&
                                        userRow-1 == boxRow2){
                                    b = true;
                                }
                                else {
                                    b = false;
                                }
                            }
                        }
                    }
                }
            }
                        
            if(b == false){
                grid.setImage(new Location(userRow, userCol), null);
                userRow--;
                keyAnt = 38;
                grid.setImage(new Location(userRow, userCol), "user.gif");
            }
            b = false;
        }
      
      
        //Baixo
        if(key == 40 & userRow < 14) {  
            if(boxRow < 14 || userRow+1 != boxRow || boxCol != userCol || boxRow2 < 14 || userRow+1 != boxRow2) {
                if(boxRow3 == ptRow3 && boxCol3 == ptCol3 && userRow+1 == ptRow3 && userCol == ptCol3){
                    b = true;
                }
                if(boxRow2 == ptRow2 && boxCol2 == ptCol2 && userRow+1 == ptRow2 && userCol == ptCol2){
                    b = true;
                }
                if(boxRow == ptRow && boxCol == ptCol && userRow+1 == ptRow && userCol == ptCol){
                    b = true;
                }
                else{
                    for(int i = 0; i < 100; i++){
                        if(userRow+1 == wallVetRow[i] && userCol == wallVetCol[i] || boxRow+1 == wallVetRow[i] && 
                                    userRow+1 == wallVetRow[i]-1 && userCol == wallVetCol[i] || boxRow+1 == boxRow2 && 
                                    userCol == boxCol2 && boxCol == boxCol2 && userRow+1 == boxRow || boxRow+1 == boxRow3 && 
                                    userCol == boxCol3 && boxCol == boxCol3 && userRow+1 == boxRow){
                          b = true;
                          if(userCol != boxCol){
                                b = true;
                                if(userRow+1 == wallVetRow[i] || userRow+2 == wallVetRow[i] && userCol == boxCol && 
                                            userRow+1 == boxRow || userRow+2 == wallVetRow[i] && userCol == boxCol3 && 
                                            userRow+1 == boxRow3){
                                    b = true;
                                }
                                else {
                                    b = false;
                                }
                          }
                        }
                        
                        if(userRow+1 == wallVetRow[i] && userCol == wallVetCol[i] || boxRow2+1 == wallVetRow[i] && 
                                    userRow+1 == wallVetRow[i]-1 && userCol == wallVetCol[i] || boxRow2+1 == boxRow && 
                                    userCol == boxCol2 && boxCol == boxCol2 && userRow+1 == boxRow2 || boxRow2+1 == boxRow3 && 
                                    userCol == boxCol3 && boxCol2 == boxCol3 && userRow+1 == boxRow2){
                            b = true;
                            if(userCol != boxCol2){
                                b = true;
                                if(userRow+1 == wallVetRow[i] || userRow+2 == wallVetRow[i] && userCol == boxCol &&
                                        userRow+1 == boxRow || userRow+2 == wallVetRow[i] && userCol == boxCol3 && 
                                        userRow+1 == boxRow2){
                                    b = true;
                                }
                                else {
                                    b = false;
                                }
                            }
                        }
                        if(userRow+1 == wallVetRow[i] && userCol == wallVetCol[i] || boxRow3+1 == wallVetRow[i] && 
                                    userRow+1 == wallVetRow[i]-1 && userCol == wallVetCol[i] || boxRow3+1 == boxRow && 
                                    userCol == boxCol3 && boxCol == boxCol3 && userRow+1 == boxRow3 || boxRow3+1 == boxRow2 && 
                                    userCol == boxCol3 && boxCol2 == boxCol3 && userRow+1 == boxRow3){
                            b = true;
                            if(userCol != boxCol3){
                                b = true;
                                if(userRow+1 == wallVetRow[i] || userRow+2 == wallVetRow[i] && userCol == boxCol &&
                                            userRow+1 == boxRow || userRow+2 == wallVetRow[i] && userCol == boxCol2 &&
                                            userRow+1 == boxRow2){
                                    b = true;
                                }
                                else {
                                    b = false;
                                }
                            }
                        }
                    }
                }
              
                if(b == false){
                    grid.setImage(new Location(userRow, userCol), null);
                    userRow++;
                    keyAnt = 40;
                    grid.setImage(new Location(userRow, userCol), "user.gif");
                }
                b = false;
            }
        }	  
    }
  
    public void updateGameBoard(){
    }  
 
    public void handleCollision(Location loc){
    }
  
    public int getNivel(){
        return nivel+1;
    }
  
    public int getScore(){
        return Score;
    }
  
    public void updateTitle(){
        grid.setTitle("Nível  " + getNivel());
    }
  
    public boolean isGameOver(){
        return false;
    }
  
    public void Clear(){
        for(int i = 0; i < 54; i++) {
            wallVetRow[i] = 0;
            wallVetCol[i] = 0;
        }
        grid.close(15, 31);
    }
    
    //Níveis
    public void Level01() {
        grid = new Grid(15, 31);
        userRow = 7;    userCol = 14;
        boxRow = 7;    boxCol = 17;
        ptRow = 7;    ptCol = 19;
        wallVetRow[0] = 4;  wallVetCol[0] = 10;
        wallVetRow[1] = 4;  wallVetCol[1] = 11;
        wallVetRow[2] = 4;  wallVetCol[2] = 12;
        wallVetRow[3] = 4;  wallVetCol[3] = 13;
        wallVetRow[4] = 4;  wallVetCol[4] = 14;
        wallVetRow[5] = 4;  wallVetCol[5] = 15;
        wallVetRow[6] = 4;  wallVetCol[6] = 16;
        wallVetRow[7] = 4;  wallVetCol[7] = 17;
        wallVetRow[8] = 4;  wallVetCol[8] = 18;
        wallVetRow[9] = 4;  wallVetCol[9] = 19;
        wallVetRow[10] = 4;  wallVetCol[10] = 20;
        wallVetRow[11] = 4;  wallVetCol[11] = 21;
        wallVetRow[12] = 4;  wallVetCol[12] = 22;
        wallVetRow[13] = 11;  wallVetCol[13] = 10;
        wallVetRow[14] = 11;  wallVetCol[14] = 11;
        wallVetRow[15] = 11;  wallVetCol[15] = 12;
        wallVetRow[16] = 11;  wallVetCol[16] = 13;
        wallVetRow[17] = 11;  wallVetCol[17] = 14;
        wallVetRow[18] = 11;  wallVetCol[18] = 15;
        wallVetRow[19] = 11;  wallVetCol[19] = 16;
        wallVetRow[20] = 11;  wallVetCol[20] = 17;
        wallVetRow[21] = 11;  wallVetCol[21] = 18;
        wallVetRow[22] = 11;  wallVetCol[22] = 19;
        wallVetRow[23] = 11;  wallVetCol[23] = 20;
        wallVetRow[24] = 11;  wallVetCol[24] = 21;
        wallVetRow[25] = 11;  wallVetCol[25] = 22;
        wallVetRow[26] = 8;  wallVetCol[26] = 22;
        wallVetRow[27] = 4;  wallVetCol[27] = 10;
        wallVetRow[28] = 5;  wallVetCol[28] = 10;
        wallVetRow[29] = 6;  wallVetCol[29] = 10;
        wallVetRow[30] = 7;  wallVetCol[30] = 10;
        wallVetRow[31] = 8;  wallVetCol[31] = 10;
        wallVetRow[32] = 9;  wallVetCol[32] = 10;
        wallVetRow[33] = 10;  wallVetCol[33] = 10;
        wallVetRow[34] = 11;  wallVetCol[34] = 10;
        wallVetRow[35] = 4;  wallVetCol[35] = 22;
        wallVetRow[36] = 5;  wallVetCol[36] = 22;
        wallVetRow[37] = 6;  wallVetCol[37] = 22;
        wallVetRow[38] = 7;  wallVetCol[38] = 22;
        wallVetRow[39] = 8;  wallVetCol[39] = 22;
        wallVetRow[40] = 9;  wallVetCol[40] = 22;
        wallVetRow[41] = 10;  wallVetCol[41] = 22;
        wallVetRow[42] = 11;  wallVetCol[42] = 22;
    
        msElapsed = 0;
        timesGet = 0;
        timesAvoid = 0;
        updateTitle();
        grid.setImage(new Location(userRow, userCol), "user.gif");
        grid.setImage(new Location(ptRow, ptCol), "point.gif");
        grid.setImage(new Location(boxRow, boxCol), "box.gif");
        for(int i = 0; i < 43; i++) {
            grid.setImage(new Location(wallVetRow[i], wallVetCol[i]), "wall.gif");
        }        
    }
  
    public void Level02() {
        userRow = 5;    userCol = 19;
        boxRow = 5;    boxCol = 14;
        ptRow = 5;    ptCol = 12;
        boxRow2 = 7;    boxCol2 = 20;
        ptRow2 = 9;    ptCol2 = 20;
        wallVetRow[0] = 3;  wallVetCol[0] = 10;
        wallVetRow[1] = 3;  wallVetCol[1] = 11;
        wallVetRow[2] = 3;  wallVetCol[2] = 12;
        wallVetRow[3] = 3;  wallVetCol[3] = 13;
        wallVetRow[4] = 3;  wallVetCol[4] = 14;
        wallVetRow[5] = 3;  wallVetCol[5] = 15;
        wallVetRow[6] = 3;  wallVetCol[6] = 16;
        wallVetRow[7] = 3;  wallVetCol[7] = 17;
        wallVetRow[8] = 3;  wallVetCol[8] = 18;
        wallVetRow[9] = 3;  wallVetCol[9] = 19;
        wallVetRow[10] = 3;  wallVetCol[10] = 20;
        wallVetRow[11] = 3;  wallVetCol[11] = 21;
        wallVetRow[12] = 3;  wallVetCol[12] = 22;
        wallVetRow[13] = 4;  wallVetCol[13] = 22;
        wallVetRow[14] = 5;  wallVetCol[14] = 22;
        wallVetRow[15] = 6;  wallVetCol[15] = 22;
        wallVetRow[16] = 7;  wallVetCol[16] = 22;
        wallVetRow[17] = 8;  wallVetCol[17] = 22;
        wallVetRow[18] = 9;  wallVetCol[18] = 22;
        wallVetRow[19] = 10;  wallVetCol[19] = 22;
        wallVetRow[20] = 11;  wallVetCol[20] = 22;
        wallVetRow[21] = 11;  wallVetCol[21] = 21;
        wallVetRow[22] = 11;  wallVetCol[22] = 20;
        wallVetRow[23] = 11;  wallVetCol[23] = 19;
        wallVetRow[24] = 11;  wallVetCol[24] = 18;
        wallVetRow[25] = 11;  wallVetCol[25] = 17;
        wallVetRow[26] = 10;  wallVetCol[26] = 17;
        wallVetRow[27] = 9;  wallVetCol[27] = 17;
        wallVetRow[28] = 8;  wallVetCol[28] = 17;
        wallVetRow[29] = 7;  wallVetCol[29] = 17;
        wallVetRow[30] = 7;  wallVetCol[30] = 16;
        wallVetRow[31] = 7;  wallVetCol[31] = 15;
        wallVetRow[32] = 7;  wallVetCol[32] = 14;
        wallVetRow[33] = 7;  wallVetCol[33] = 13;
        wallVetRow[34] = 7;  wallVetCol[34] = 12;
        wallVetRow[35] = 7;  wallVetCol[35] = 11;
        wallVetRow[36] = 7;  wallVetCol[36] = 10;
        wallVetRow[37] = 6;  wallVetCol[37] = 10;
        wallVetRow[38] = 5;  wallVetCol[38] = 10;
        wallVetRow[39] = 4;  wallVetCol[39] = 10;
        
        msElapsed = 0;
        timesGet = 0;
        timesAvoid = 0;
        updateTitle();
        grid.setImage(new Location(userRow, userCol), "user.gif");
        grid.setImage(new Location(ptRow, ptCol), "point.gif");
        grid.setImage(new Location(boxRow, boxCol), "box.gif");
        grid.setImage(new Location(ptRow2, ptCol2), "point2.gif");
        grid.setImage(new Location(boxRow2, boxCol2), "box2.gif");
        for(int i = 0; i < 40; i++) {
            grid.setImage(new Location(wallVetRow[i], wallVetCol[i]), "wall.gif");
        }
    }
  
    public void Level03() {
        userRow = 5;    userCol = 17;
        boxRow = 7;    boxCol = 14;
        ptRow = 6;    ptCol = 12;
        boxRow2 = 7;    boxCol2 = 17;
        ptRow2 = 8;    ptCol2 = 19;
        boxRow3 = 5;    boxCol3 = 15;
        ptRow3 = 8;    ptCol3 = 16;
        wallVetRow[0] = 3;  wallVetCol[0] = 10;
        wallVetRow[1] = 3;  wallVetCol[1] = 11;
        wallVetRow[2] = 3;  wallVetCol[2] = 12;
        wallVetRow[3] = 3;  wallVetCol[3] = 13;
        wallVetRow[4] = 3;  wallVetCol[4] = 14;
        wallVetRow[5] = 3;  wallVetCol[5] = 15;
        wallVetRow[6] = 3;  wallVetCol[6] = 16;
        wallVetRow[7] = 3;  wallVetCol[7] = 17;
        wallVetRow[8] = 3;  wallVetCol[8] = 18;
        wallVetRow[9] = 4;  wallVetCol[9] = 18;
        wallVetRow[10] = 5;  wallVetCol[10] = 18;
        wallVetRow[11] = 6;  wallVetCol[11] = 18;
        wallVetRow[12] = 7;  wallVetCol[12] = 18;
        wallVetRow[13] = 8;  wallVetCol[13] = 18;
        wallVetRow[14] = 7;  wallVetCol[14] = 19;
        wallVetRow[15] = 7;  wallVetCol[15] = 20;
        wallVetRow[16] = 7;  wallVetCol[16] = 21;
        wallVetRow[17] = 7;  wallVetCol[17] = 22;
        wallVetRow[18] = 8;  wallVetCol[18] = 22;
        wallVetRow[19] = 9;  wallVetCol[19] = 22;
        wallVetRow[20] = 10;  wallVetCol[20] = 22;
        wallVetRow[21] = 11;  wallVetCol[21] = 22;
        wallVetRow[22] = 11;  wallVetCol[22] = 21;
        wallVetRow[23] = 11;  wallVetCol[23] = 20;
        wallVetRow[24] = 11;  wallVetCol[24] = 19;
        wallVetRow[25] = 11;  wallVetCol[25] = 18;
        wallVetRow[26] = 11;  wallVetCol[26] = 17;
        wallVetRow[27] = 11;  wallVetCol[27] = 16;
        wallVetRow[28] = 11;  wallVetCol[28] = 15;
        wallVetRow[29] = 11;  wallVetCol[29] = 14;
        wallVetRow[30] = 11;  wallVetCol[30] = 13;
        wallVetRow[31] = 10;  wallVetCol[31] = 13;
        wallVetRow[32] = 9;  wallVetCol[32] = 13;
        wallVetRow[33] = 8;  wallVetCol[33] = 13;
        wallVetRow[34] = 7;  wallVetCol[34] = 13;
        wallVetRow[35] = 6;  wallVetCol[35] = 13;
        wallVetRow[36] = 7;  wallVetCol[36] = 12;
        wallVetRow[37] = 7;  wallVetCol[37] = 11;
        wallVetRow[38] = 7;  wallVetCol[38] = 10;
        wallVetRow[39] = 6;  wallVetCol[39] = 10;
        wallVetRow[40] = 5;  wallVetCol[40] = 10;
        wallVetRow[41] = 4;  wallVetCol[41] = 10;
        
        msElapsed = 0;
        timesGet = 0;
        timesAvoid = 0;
        updateTitle();
        grid.setImage(new Location(userRow, userCol), "user.gif");
        grid.setImage(new Location(ptRow, ptCol), "point.gif");
        grid.setImage(new Location(boxRow, boxCol), "box.gif");
        grid.setImage(new Location(ptRow2, ptCol2), "point2.gif");
        grid.setImage(new Location(boxRow2, boxCol2), "box2.gif");
        grid.setImage(new Location(ptRow3, ptCol3), "point3.gif");
        grid.setImage(new Location(boxRow3, boxCol3), "box3.gif");
        for(int i = 0; i < 42; i++) {
            grid.setImage(new Location(wallVetRow[i], wallVetCol[i]), "wall.gif");
        }
    }
    
    public void Level04() {
        userRow = 8;    userCol = 15;
        boxRow = 6;    boxCol = 14;
        ptRow = 3;    ptCol = 19;
        boxRow2 = 6;    boxCol2 = 17;
        ptRow2 = 3;    ptCol2 = 13;
        boxRow3 = 10;    boxCol3 = 17;
        ptRow3 = 3;    ptCol3 = 17;
        wallVetRow[0] = 2;  wallVetCol[0] = 12;
        wallVetRow[1] = 2;  wallVetCol[1] = 13;
        wallVetRow[2] = 2;  wallVetCol[2] = 14;
        wallVetRow[3] = 2;  wallVetCol[3] = 15;
        wallVetRow[4] = 2;  wallVetCol[4] = 16;
        wallVetRow[5] = 2;  wallVetCol[5] = 17;
        wallVetRow[6] = 2;  wallVetCol[6] = 18;
        wallVetRow[7] = 2;  wallVetCol[7] = 19;
        wallVetRow[8] = 2;  wallVetCol[8] = 20;
        wallVetRow[9] = 3;  wallVetCol[9] = 20;
        wallVetRow[10] = 4;  wallVetCol[10] = 20;
        wallVetRow[11] = 5;  wallVetCol[11] = 20;
        wallVetRow[12] = 6;  wallVetCol[12] = 20;
        wallVetRow[13] = 7;  wallVetCol[13] = 20;
        wallVetRow[14] = 8;  wallVetCol[14] = 20;
        wallVetRow[15] = 9;  wallVetCol[15] = 20;
        wallVetRow[16] = 10;  wallVetCol[16] = 20;
        wallVetRow[17] = 11;  wallVetCol[17] = 20;
        wallVetRow[18] = 12;  wallVetCol[18] = 20;
        wallVetRow[19] = 13;  wallVetCol[19] = 20;
        wallVetRow[20] = 13;  wallVetCol[20] = 19;
        wallVetRow[21] = 13;  wallVetCol[21] = 18;
        wallVetRow[22] = 13;  wallVetCol[22] = 17;
        wallVetRow[23] = 13;  wallVetCol[23] = 16;
        wallVetRow[24] = 13;  wallVetCol[24] = 15;
        wallVetRow[25] = 13;  wallVetCol[25] = 14;
        wallVetRow[26] = 13;  wallVetCol[26] = 13;
        wallVetRow[27] = 13;  wallVetCol[27] = 12;
        wallVetRow[28] = 12;  wallVetCol[28] = 12;
        wallVetRow[29] = 11;  wallVetCol[29] = 12;
        wallVetRow[30] = 10;  wallVetCol[30] = 12;
        wallVetRow[31] = 9;  wallVetCol[31] = 12;
        wallVetRow[32] = 8;  wallVetCol[32] = 12;
        wallVetRow[33] = 7;  wallVetCol[33] = 12;
        wallVetRow[34] = 6;  wallVetCol[34] = 12;
        wallVetRow[35] = 5;  wallVetCol[35] = 12;
        wallVetRow[36] = 4;  wallVetCol[36] = 12;
        wallVetRow[37] = 3;  wallVetCol[37] = 12;
        wallVetRow[38] = 3;  wallVetCol[38] = 14;
        wallVetRow[39] = 3;  wallVetCol[39] = 16;
        wallVetRow[40] = 3;  wallVetCol[40] = 18;
        wallVetRow[41] = 5;  wallVetCol[41] = 14;
        wallVetRow[42] = 5;  wallVetCol[42] = 16;
        wallVetRow[43] = 5;  wallVetCol[43] = 18;
        wallVetRow[44] = 7;  wallVetCol[44] = 14;
        wallVetRow[45] = 7;  wallVetCol[45] = 16;
        wallVetRow[46] = 7;  wallVetCol[46] = 18;
        wallVetRow[47] = 8;  wallVetCol[47] = 13;
        wallVetRow[48] = 8;  wallVetCol[48] = 16;
        wallVetRow[49] = 8;  wallVetCol[49] = 17;
        wallVetRow[50] = 8;  wallVetCol[50] = 18;
        wallVetRow[51] = 9;  wallVetCol[51] = 16;
        wallVetRow[52] = 10;  wallVetCol[52] = 16;
        wallVetRow[53] = 10;  wallVetCol[53] = 18;
        
        msElapsed = 0;
        timesGet = 0;
        timesAvoid = 0;
        updateTitle();
        grid.setImage(new Location(userRow, userCol), "user.gif");
        grid.setImage(new Location(ptRow, ptCol), "point.gif");
        grid.setImage(new Location(boxRow, boxCol), "box.gif");
        grid.setImage(new Location(ptRow2, ptCol2), "point2.gif");
        grid.setImage(new Location(boxRow2, boxCol2), "box2.gif");
        grid.setImage(new Location(ptRow3, ptCol3), "point3.gif");
        grid.setImage(new Location(boxRow3, boxCol3), "box3.gif");
        for(int i = 0; i < 54; i++) {
            grid.setImage(new Location(wallVetRow[i], wallVetCol[i]), "wall.gif");
        }
    }
  
    public static void test(){
        Game game = new Game();
        game.play();
    }
  
    public static void main(String[] args){
      test();
    }  
}