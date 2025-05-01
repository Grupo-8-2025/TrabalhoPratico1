package com.davi.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class TelaMenu implements Screen{
    
    // Aqui estamos declarando as variáveis
    // Textura é usada para carregar imagens
    private Texture imgLogoAMI;
    private Texture imgCarregando;
    // FitViewport é usado para definir a área visível da tela
    private FitViewport portTela;
    // SpriteBatch é usado para desenhar imagens na tela
    private SpriteBatch spriteBatch;
    // FreeTypeFontGenerator é usado para gerar fontes a partir de arquivos TTF
    // FreeTypeFontParameter é usado para definir parâmetros de fonte
    // BitmapFont é usado para desenhar texto na tela
    private FreeTypeFontGenerator fontGenerator;
    private FreeTypeFontParameter fontParameter;
    private BitmapFont font;
    
    private boolean apertarEnter = false;
    private float tempo = 0;

    //apontando o menu para a tela de jogo
    private Main game;
    public TelaMenu(Main game) {
        this.game = game;
    }

    //metodo que cria os objetos
    private void create() {

        // Carregando a fonte TrueType
        // FreeTypeFontGenerator é usado para gerar fontes a partir de arquivos TTF
        // FreeTypeFontParameter é usado para definir parâmetros de fonte
        // Aqui, estamos carregando a fonte "font.ttf" e definindo o tamanho da fonte como 12
        // Você deve ter o arquivo "font.ttf" no diretório "core/assets"
        // Se você não tiver esse arquivo, você pode usar qualquer fonte TTF que você tenha
        // ou baixar uma da internet e colocar no diretório "core/assets"
        // Se você não quiser usar uma fonte personalizada, você pode comentar essas linhas
        // e usar a fonte padrão do LibGDX
        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
        fontParameter = new FreeTypeFontParameter();
        fontParameter.size = 12;
        font = fontGenerator.generateFont(fontParameter); 

        // Carregando as texturas
        // Aqui, estamos carregando a textura "logo.png" e "winXPLoading.jpeg"
        // Você deve ter esses arquivos no diretório "core/assets"
        // Se você não tiver esses arquivos, você pode usar qualquer imagem que você tenha
        // ou baixar uma da internet e colocar no diretório "core/assets"
        // Se você não quiser usar essas imagens, você pode comentar essas linhas
        // e usar qualquer outra imagem que você tenha
        // ou usar a textura padrão do LibGDX
        spriteBatch = new SpriteBatch();
        // Aqui, estamos criando a viewport
        // A viewport é usada para definir a área visível da tela
        // Aqui, estamos definindo a largura e altura da viewport como 800 e 500, respectivamente
        portTela = new FitViewport(800, 500);
        imgLogoAMI = new Texture("logo.png");
        imgCarregando = new Texture("winXPLoading.jpeg");
    }

    //metodo que desenha a tela inicial
    void desenharTelaInicial(BitmapFont font, SpriteBatch spriteBatch, Texture imgLogoAMI, float worldHeight) {
        // Desenhando a tela inicial
        // Aqui, estamos desenhando a imagem "logo.png" na tela
        // e desenhando o texto "AMIBIOS(C)2007 American Megatrends, Inc." abaixo da imagem
        // Você pode mudar o texto e a posição da imagem como quiser
        // Se você não quiser desenhar a imagem e o texto, você pode comentar essas linhas
        // e usar qualquer outra imagem que você tenha
        // ou usar a textura padrão do LibGDX

        /*
         * Desenha a imagem em imgLogoAMI na tela
         * A imagem é desenhada na posição (-15, worldHeight - 80)
         * A largura e altura da imagem são 174 e 100, respectivamente
         */
        spriteBatch.draw(imgLogoAMI, -15, worldHeight - 80, 174, 100);
        //Aqui so estou definindo a cor do texto como branco
        font.setColor(Color.WHITE);
        /*
         * Desenha o texto "AMIBIOS(C)2007 American Megatrends, Inc." na tela
         * O texto é desenhado na posição (0, worldHeight - 80)
         * A largura e altura do texto são 0 e worldHeight - 80, respectivamente
         * O texto é desenhado seguindo o padrao definido em Create()
         */
        font.draw(spriteBatch, "AMIBIOS(C)2007 American Megatrends, Inc.", 0, worldHeight - 80);
        font.draw(spriteBatch, "ASUS P5KPL ACPI BIOS Revision 0603", 0, worldHeight - 100);
        font.draw(spriteBatch, "CPU : Intel(R) Pentium(R) Dual CPU E2180 @ 2.00GHz", 0, worldHeight - 120);
        font.draw(spriteBatch, " Speed : 2.51 GHz    Count : 2", 0, worldHeight - 140);
        font.draw(spriteBatch, " ", 0, worldHeight - 160);
        font.draw(spriteBatch, "APERTE ENTER PARA COMECAR", 0, worldHeight - 180);
        font.draw(spriteBatch, "APERTE ESC PARA ABRIR AS CONFIGURACOES", 0, worldHeight - 200);
        font.draw(spriteBatch, "DDR2-667 in Dual Channel Inter leaved Mode", 0, worldHeight - 220);
        font.draw(spriteBatch, "Initializing USB Controllers .. Done.", 0, worldHeight - 240);
        font.draw(spriteBatch, "3584MB OK", 0, worldHeight - 260);
        font.draw(spriteBatch, "(C) American Megatrends, Inc.", 0, 20);
        font.draw(spriteBatch, "64-0603-000001-00101111-022908-Bearlake-A0B20000-Y2KC", 0, 40);       
    }

    //metodo que verifica o tempo de carregamento
    //se o tempo for maior que 2 segundos, ele muda para a tela de jogo
    //se o tempo for menor que 2 segundos, ele desenha a tela de carregamento
    //se o tempo for igual a 2 segundos, ele desenha a tela de carregamento
    void verificadorTempoCarregando(float tempo, boolean apertarEnter, Main game, SpriteBatch spriteBatch, Texture imgCarregando, float worldWidth, float worldHeight) {
        if (tempo > 2) {
            tempo = 0;
            apertarEnter = false;
            //O setScreen muda a tela atual para a tela de jogo, percebe que na classe TelaJogo
            //temos o implemento de Screen, ou seja, ela é uma tela
            //e por isso podemos mudar a tela atual para a tela de jogo
            //O game.setScreen(new TelaJogo(game)) muda a tela atual para a tela de jogo
            //e o new TelaJogo(game) cria uma nova tela de jogo
            //O game é o objeto da classe Main, que é a classe principal do jogo
            game.setScreen(new TelaJogo(game));
        } else {
            //Desenhando a tela de carregamento
            //O funcionamento é o mesmo que o da tela inicial
            spriteBatch.draw(imgCarregando, 0, 0, worldWidth, worldHeight);
        } 
    }

    //metodo que desenha a tela
    private void draw() {
        /*
         * Aqui estamos fazendo o procendimento padrao de limpesa da tela
         * O ScreenUtils.clear(Color.BLACK) limpa a tela com a cor preta
         * O portTela.apply() aplica a viewport na tela
         * O Gdx.gl.glClearColor(0, 0, 0, 1) define a cor de limpeza da tela
         * O Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT) limpa a tela
         * O spriteBatch.begin() inicia o desenho na tela
         * O spriteBatch.end() finaliza o desenho na tela
         * O porTela.getWorldWidth() e o portTela.getWorldHeight() 
         * pegam a largura e altura da tela segundo o ajuste do usuario
         */
        ScreenUtils.clear(Color.BLACK);
        portTela.apply();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);
        spriteBatch.begin();

        float worldWidth = portTela.getWorldWidth();
        float worldHeight = portTela.getWorldHeight();

        if (apertarEnter) {
            //Aqui eu pego o tempo atual da tela
            tempo += Gdx.graphics.getDeltaTime();
            //Aqui eu aciono uma funcao ja explicada acima
            verificadorTempoCarregando(tempo, apertarEnter, game, spriteBatch, imgCarregando, worldWidth, worldHeight);
        } else {
            //Aqui eu aciono uma funcao ja explicada acima
            desenharTelaInicial(font, spriteBatch, imgLogoAMI, worldHeight);
        }


        spriteBatch.end();
    }

    /*
     * !!!PARTE IMPORTANTE DO CODIGO!!!
     * Aqui estamos implementando a interface Screen
     * TUDO que estiver dentro dessa INTERFACE
     * deve ser implementado aqui COM O @OVERRIDE
     * O @Override é uma anotação que indica que o método está sobrescrevendo um método da superclasse
     * Aqui estamos implementando os métodos da interface Screen
     */

    // Aqui estamos implementando o método show()
    // O show() é chamado quando a tela é exibida pela primeira vez
    // Aqui estamos chamando o método create(), ja explicado acima
    @Override
    public void show() {
        create();
    }

    // Aqui estamos implementando o método render()
    // O render() é chamado a cada quadro
    // Aqui estamos chamando o método draw(), ja explicado acima
    // O delta é o tempo que passou desde o último quadro
    // Aqui estamos verificando se o usuário pressionou a tecla ENTER
    // Se o usuário pressionou a tecla ENTER, o tempo de carregamento é iniciado
    // Se o usuário não pressionou a tecla ENTER, a tela inicial é desenhada
    // O Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.ENTER) verifica se a tecla ENTER foi pressionada
    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.ENTER)) {
            apertarEnter = true;
        }
        draw();
    }

    // Aqui estamos implementando o método resize()
    // O resize() é chamado quando a tela é redimensionada
    // Aqui estamos atualizando a viewport com a nova largura e altura
    // O portTela.update(width, height, true) atualiza a viewport com a nova largura e altura
    // O true indica que a viewport deve ser atualizada com a nova largura e altura
    // Esse update() é importante para garantir que a tela seja desenhada corretamente
    // quando a janela é redimensionada
    @Override
    public void resize(int width, int height) {
        portTela.update(width, height, true);
    }

    // Aqui estamos implementando o método pause()
    // O pause() é chamado quando a tela é pausada
    // Aqui estamos fazendo o procedimento padrão de pausa
    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    // Aqui estamos implementando o método resume()
    // O resume() é chamado quando a tela é retomada
    // Aqui estamos fazendo o procedimento padrão de retomada
    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    // Aqui estamos implementando o método hide()
    // O hide() é chamado quando a tela é substituída por outra tela
    // Aqui estamos fazendo o procedimento padrão de ocultação
    @Override
    public void hide() {
        // This method is called when another screen replaces this one.
    }

    // Aqui estamos implementando o método dispose()
    // O dispose() é chamado quando a tela é descartada
    // Aqui estamos fazendo o procedimento padrão de descarte
    /*
     * !!!MUITO IMPORTANTE!!!
     * 
     * O dispose() é usado para liberar os recursos usados pela tela
     * ENTAO NUNCA ESQUECA DE CHAMAR O dispose() NO FINAL DO JOGO
     */

    @Override
    public void dispose() {
        spriteBatch.dispose();
        imgLogoAMI.dispose();
        fontGenerator.dispose();
    }

}
