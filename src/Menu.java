import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
 
public class Menu {
    int a = 0;
    int page = -1;
    int selectedItem = 0;
    int selectedItemAnt = 0;
    String itens[];
    Graphics font;
    boolean ativo;
    int x;
    int y;
    int fontSize = 20;
    int fontSize2 = 15;
    int fontSize3 = 60;
    int fontSize4 = 40;
    int itensD = 15;
    Font fonte = new Font("Arial", Font.BOLD, fontSize);
    Font fonte2 = new Font("Arial", Font.BOLD, fontSize2);
    Font fonte3 = new Font("Arial", Font.BOLD, fontSize3);
    Font fonte4 = new Font("Arial", Font.BOLD, fontSize4);
    Color colorR = new Color(255, 0, 0);
    Color colorG = new Color(35, 142, 35);
 
    //Declaração das variáveis internas de acordo com o que foi inserido no Game.java ao chamar o Menu
    public Menu(int numeroDeItens, int x, int y, boolean ativo) {
        itens = new String[numeroDeItens];
        this.x = x;
        this.y = y;
        this.ativo = ativo;
    }
    
    //Verifica se o Menu está em execução
    public void exec(KeyEvent e) {
        if (ativo) {
            menuE(e);
        }
    }
    
    public void soundM(int o){
        if(o == 0){
            URL sound = null;
            try {
            sound = new URL("file:src/win.wav");
            } catch (MalformedURLException erro) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, erro);
            }
            AudioClip som = Applet.newAudioClip(sound);
            som.play();
            }
        if(o == 1){
            URL sound = null;
            try {
            sound = new URL("file:src/button.wav");
            } catch (MalformedURLException erro) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, erro);
            }
            AudioClip som = Applet.newAudioClip(sound);
            som.play();
        }
    }


    //Método para retornar à tela do menu anterior
    public void menuR(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
            selectedItem = 0;
            page = -1;
            a = 0;
            ativo = true;
            soundM(1);
        }

    }

    //Método para movimentação da seleção do Menu
    private void menuE(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            selectedItem -= 1;
            //soundM(0);            
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            selectedItem += 1;
            //soundM(0);
        }
        if(page == 1){  
            if (selectedItem >= 8) {
                selectedItem = 4;       
            }
            if(selectedItem < 4){
                selectedItem = 7;
            }
        }
        if(page == -1){
            if(selectedItem > 3){
                selectedItem = 0;
            }
            if (selectedItem < 0) {
                selectedItem = 3;
            }
        }
        if(page == 5){
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                selectedItem = 9;
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                selectedItem = 3;
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            if(a == 0){
                page = selectedItem;
            }
            if (page == 1 && a == 1){
                ativo = false;
                a++;
            }
            else if (page == 1){
                selectedItem = 4;
            }
            else if (page == 5 && a == 3){
                ativo = false;
                a++;
            }
            soundM(1);
            a++;   
        }
    }

    //Método para desenhar e atualizar o Menu Principal
    public void menuD() {
        font.setFont(fonte);
        for (int i = 0; i < 4; i++) {
            if(selectedItem == i){
                font.setColor(colorR);
                font.drawString(itens[i], x, y+(i*(fontSize+itensD)));
            }
            else{
                font.setColor(colorG);
                font.drawString(itens[i], x, y+(i*(fontSize+itensD)));
            }
        }
    }

    //Método para desenhar o menu de níveis
    public void menuDN() {
        font.setFont(fonte);
        for (int i = 4; i < 8; i++) {
            if(selectedItem == i){
                font.setColor(colorR);
                font.drawString(itens[i], x, y+((i-4)*(fontSize+itensD)));
            }
            else{
                font.setColor(colorG);
                font.drawString(itens[i], x, y+((i-4)*(fontSize+itensD)));
            }
        }
    }

    //Método para desenhar o menu Game Over
    public void menuGO() {
        font.setFont(fonte);
        if(selectedItem == 9){
                font.setColor(colorR);
                font.drawString(itens[9], 100, y+(fontSize+itensD));
            }
        else{
            font.setColor(colorG);
            font.drawString(itens[9], 100, y+(fontSize+itensD));
        }
        if(selectedItem == 3){
                font.setColor(colorR);
                font.drawString(itens[3], 100, y+(2*(fontSize+itensD)));
            }
        else{
            font.setColor(colorG);
            font.drawString(itens[3], 100, y+(2*(fontSize+itensD)));
        }
    }
}


