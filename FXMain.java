package App;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Paint;
 

/**
 *
 * @author Anna Simankova
 */
public class FXMain extends Application {

    Point2D offset; 
    Node selected; 
    Point2D translateStart; 
   
 //------------------------    
//Metoda vytváření cihel
    
    static Rectangle createBricks(int x, int y) {
        Rectangle brick = new Rectangle(x, y, 84, 25);
        
        setColor col = new setColor();  //inicializace tridy setColor
        
        int colorScheme = 1;            //int volby barvy
        Paint barva = null;             //proměnná typu Paint
        
        if (colorScheme ==1)            //podmínka výběru barevného schématu
        {
            barva = col.brickColorScheme1;
        }
        if (colorScheme ==2)
        {
             barva= col.brickColorScheme2;
        }
         if (colorScheme ==3)
        {
             barva= col.brickColorScheme3;
        }
          if (colorScheme ==4)
        {
             barva= col.brickColorScheme4;
        }
    
        brick.setFill(barva);               //vyplnění cihel barvou
        return brick;                       //návratová hodnota metody
        
      }
//------------------------------------------       

    @Override
    public void start(Stage stage) throws Exception {
   
        Pane root = FXMLLoader.load(getClass().getResource("FXML.fxml"));  //načtení pane ROOT z FXML souboru
        Pane brickPane = FXMLLoader.load(getClass().getResource("FXML.fxml"));  //načtení pane brickpane z FXML souboru
        Pane ballPane = FXMLLoader.load(getClass().getResource("FXML.fxml"));  //načtení pane brickpane z FXML souboru
       
       
        Scene scene = new Scene(root);  //incializace nové scény root
        Circle ball = new Circle(15, Color.YELLOW);  //nastavení barvy kuličky
        Rectangle rect = new Rectangle(200,505, 200, 30);  //nastavení parametrů objektu Rectangle - v tomto případě paddle
        Rectangle base = new Rectangle (0,540, 800, 15); 
        Label xlabel = new Label ("xxxxxx GAME OVER xxxxxx");
        xlabel.setTextFill(Color.web("#FF76a3"));
        xlabel.setStyle("-fx-font-family: Arial; -fx-font-size: 25");
        xlabel.setLayoutX(230);
        xlabel.setLayoutY(250);
        xlabel.setVisible(false);
       
        rect.relocate (300,500);//umístění objektu rect na obrazovce
        rect.setFill(Color.DEEPSKYBLUE);    //nastavení barvy objektu rect - paddle
        base.setFill(Color.RED);    //nastavení barvy objektu base
        ball.relocate(400,300);  //umístění objektu base na obrazovce
        base.setVisible(true);
        
         


//Pole cihel
         
        Rectangle brick [] = new Rectangle[30];  //vytvoření pole brick s poctem 
        int i=0;   //int i - vychoz hodnota counteru pro for cyklus
        int x = 0; //výchozí hodnota x souřadnice
        int y = 30;  //výchozí hodnota y souřadnice
        for(i=0, x = 5; x<=797; x=x+88,i=i+1 ){
            brick[i] = createBricks(x,y);
            brickPane.getChildren().add(brick[i]);
           
            if(x==797){
                for( x = 5; x<=797; x=x+88,i=i+1 ) {
                    brick[i] = createBricks(x,y+29);
                    brickPane.getChildren().add(brick[i]);
                        
                    
                if(x==797){
                for( x = 5; x<=797; x=x+88,i=i+1 ) {
                    brick[i] = createBricks(x,y+58);
                    brickPane.getChildren().add(brick[i]);
                    
                
                    }
                }
            }
        
        
        root.getChildren().addAll(ballPane,brickPane,rect,base,ball, xlabel); //definice členů pro root pane
        
  
        // ---------------------------------------------------------------------
        
        
        stage.getIcons().add(new Image("file:c:\\brick_breaker\\img\\icon.png"));
        stage.setTitle("Brick_Breaker");  //titiule aplikace - název okna
        stage.setResizable(false);
       
       
     //------------------------------------------------------------------------
     //Pohyb objektu rect po stisknutí klávesy LEFT a RIGHT o hodnotu 80 a nastavením rozsahu pro levý a pravý roh
      
      root.getScene().setOnKeyPressed((final KeyEvent e) -> {
          if (e.getCode() == KeyCode.LEFT && rect.getLayoutX()>=-150)
              rect.setLayoutX( rect.getLayoutX() - 80 );
          
           if (e.getCode() == KeyCode.RIGHT && rect.getLayoutX()<=350)
              rect.setLayoutX( rect.getLayoutX() + 80 );
        });
      
      
        
     //-----------------------------------------------------------------------

        stage.setScene(scene);
        stage.show();
     //-----------------------------------------------------------------------
        

     
     Timeline timeline = null; 
        timeline = new Timeline(new KeyFrame(Duration.millis(20), 
                new EventHandler<ActionEvent>() {
                    
                    double dx = 7; //Pohyb na x
                    double dy = 7; //Pohyb na y
            
                    
      //---------------------------------------------------------------------              
      @Override
                    public void handle(ActionEvent t) {
                        
                        
                        //Pohyb kuličky
                        ball.setLayoutX(ball.getLayoutX() + dx);
                        ball.setLayoutY(ball.getLayoutY() + dy);
                        
                        Bounds bounds = ballPane.getLayoutBounds();
                        
                        if ( rect.getBoundsInParent().intersects(ball.getBoundsInParent()) ) dy = -dy;
                        
                         
                        //Pokud klička dosáhne levého nebo pravého okraje, změní směr.
                        if(ball.getLayoutX() <= (bounds.getMinX() + ball.getRadius()) ||
                                ball.getLayoutX() >= (bounds.getMaxX() - ball.getRadius()) ){
                            
                            dx = -dx; 
                        }
                        //Pokud klička dosáhne horního nebo dolního okraje, změní směr.
                        if((ball.getLayoutY() >= (bounds.getMaxY() - ball.getRadius())) ||
                                ball.getLayoutY() <= (bounds.getMinY() + ball.getRadius()))
                        {
                            dy = -dy;
                        }                
                        
                        
                        
                        
  for( Rectangle brick: brick) {

  if( brick == null)
    continue;

  if( ball.getBoundsInParent().intersects(brick.getBoundsInParent())) {
      brickPane.getChildren().remove(brick) ;
  }
  
 if( ball.getBoundsInParent().intersects(base.getBoundsInParent())) {
      xlabel.setVisible(true); 
  }
    
                    
  } 
  } 
                    } 
  
        
                
        ));
        
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        
       
       
    }
              }
          
        
          }
       
        
      
  
  //---------------------------------------------------------------------------  
                

    public static void main(String[] args) {
        launch(args);
    

}
}

  
