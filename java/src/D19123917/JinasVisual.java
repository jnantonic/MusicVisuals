package D19123917;

import ie.tudublin.*;
import processing.core.PImage;


public class JinasVisual extends Visual
{
    Star [] stars = new Star[800]; // Create an array named stars
    PImage [] images = new PImage[7];
    Monster m;

    float hh, hw;
    float speed;

    int cols, rows;
    int scl = 20;
    int w = 2000;
    int h = 1600;

    float flying = 0;

    float[][] terrain;


    public void settings()
    {
        size(800, 800, P3D);
        //size(400, 400);
        hw = width / 2; // Half width
        hh = height / 2; // Half height

        for(int i = 0; i < stars.length; i++)
        {
            stars[i] = new Star(this);
        }

        cols = w / scl;
        rows = h/ scl;
        terrain = new float[cols][rows];
    }   

    public void setup()
    {
        startMinim();
        surface.setResizable(true);
        loadAudio("POPSTAR.mp3");
        getAudioPlayer().play();
        colorMode(HSB);

        m = new Monster(this, hw, hh, 0, 0);

        images[0] = loadImage("1.png");
        images[1] = loadImage("2.png");
        images[2] = loadImage("3.png");
        images[3] = loadImage("4.png");
        images[4] = loadImage("5.png");
        images[5] = loadImage("6.png");

        
    }

    public void mousePressed()
    {
        startMinim();
        getAudioPlayer().play();
        
        pushMatrix();
        translate(120,200);
        rotate(radians(200));
        stroke(255);
        strokeWeight(4);
       
        fill(255);
        ellipse(0,0,20,10);
        ellipse(0,20,20,10);
       
        ellipse(30,0,20,10);
        ellipse(30,20,20,10);
       
        ellipse(60,0,20,10);
        ellipse(60,20,20,10);
        popMatrix();
    }
       
    int which = 0;
    public void keyPressed()
    {
        if(keyCode >= '0' && keyCode <= '5')
        {
            which = keyCode - '0';
        }
        if(keyCode == ' ') // If i push the space bar 
        {
            if(getAp().isPlaying()) // If audioplayer is already playing
            { 
                getAp().pause(); // Pause the audio
            }
            else
            {
                getAp().rewind(); // Rewind
                getAp().play(); // Play 
            }
        }
    }

    float la = 0;

    public void draw()
    {   
        float average = 0;
        float sum = 0;

        // Iterate over all the elementsthe audio buffer
        for(int i = 0; i < getAb().size(); i++) // ab is an array list of audio buffer so ab.size() gives us the size of array buffer
        {
            sum += abs(getAb().get(i)); // This is how you look inside the arraylist and get element i out of the arraylist
        }
        
        average = sum / (float) getAb().size();
        la = lerp(la, average, 0.1f); // Lerped average

        switch(which)
        {
            case 0: 
            {
                speed = map(mouseX, 0, width, 0, 20);
                background(0);
                //translate(hw, hh);
                //float c = map(average, 0, 1, 0, 255);
                for(int i = 0; i < stars.length; i++)
                {
                    stars[i].update();
                    stars[i].show();
                }

                float c = map(average, 0, 1, 0, 255);
                
                break;
            }
            case 1:
            {
                flying -= 0.1;

                float yoff = flying;
                for (int y = 0; y < rows; y++) {
                    float xoff = 0;
                    for (int x = 0; x < cols; x++) {
                    terrain[x][y] = map(noise(xoff, yoff), 0, 1, -100, 100);
                    xoff += 0.2;
                    }
                    yoff += 0.2;
                }

                background(0);
                stroke(255);
                noFill();
              
                translate(width/2, height/2+50);
                rotateX(PI/3);
                translate(-w/2, -h/2);
                for (int y = 0; y < rows-1; y++) {
                  beginShape(TRIANGLE_STRIP);
                  for (int x = 0; x < cols; x++) {
                    vertex(x*scl, y*scl, terrain[x][y]);
                    vertex(x*scl, (y+1)*scl, terrain[x][y+1]);
                    //rect(x*scl, y*scl, scl, scl);
                  }
                  endShape();
                }
                break;

            }
            case 2:
            {
                int s = mouseX / 5 + 1;
                frameRate(s);
                background(255);
                int imageNum = frameCount % 6;
                //translate(hw, hh);
                image(images[imageNum], 0, 0);
                
                break;
            }
            case 3:
            {
                //float c = map(average, 0, 1, 0, 255);
                background(0);
                noStroke();
                
                //body
                fill(15, 188, 250);
                ellipse(250,390,180,200);
                
                //head
                fill(15, 188, 250);
                ellipse(250,250,150,160);
                
                //eye
                fill(0);
                ellipse(200,220,5,5);
                ellipse(300,220,5,5);

                pushMatrix();
                translate(240,260);
                rotate(radians(60));
                //stroke(200);
                
                fill(255);
                ellipse(0,0,30,40);
                popMatrix();
                
                pushMatrix();
                translate(260,260);
                rotate(radians(120));
                // stroke(200);
                
                fill(255);
                ellipse(0,0,30,40);
                popMatrix();
                
                //noStroke();
                fill(255);
                ellipse(250,260,30,10);
                
                //nose
                noStroke();
                fill(0);
                ellipse(250,250,22,22);
                
                //f
                stroke(0);
                fill(255,0,0);
                line(210, 255, 230, 260);
                line(230, 265, 200, 270);
                line(210, 280, 230, 270);
                
                pushMatrix();
                translate(390,590);
                rotate(radians(200));
                stroke(0);
                
                fill(255,0,0);
                line(210, 255, 230, 260);
                line(230, 265, 200, 270);
                line(210, 280, 230, 270);
                popMatrix();

                break;
            }
            case 4:
            {
                float r = 1f; // radius
                int numPoints = 3; // no. of points that we are gonna calculate on the outside of a spiral
                float thetaInc = TWO_PI / (float)numPoints;
                strokeWeight(2);
                stroke(255);
                float lastX = width / 2; 
                float lastY = height / 2;
                for(int i = 0; i < 1000; i++) // Calculate 1000 points on the outside of a spiral -> doing that for 1000 times
                {
                    float c = map(i, 0, 300, 0, 255) % 255.0f;
                    stroke(c, 255, 255, 100);
                    float theta = i * (thetaInc + la * 5);
                    float x = width / 2 + sin(theta) * r; // Calculating the points on the outside of the circle 
                    float y = height / 2 - cos(theta) * r;
                    r += 0.5f + la; // Increase the radius by some small amount
                    numPoints++;
                    //point(x, y);
                    line(lastX, lastY, x, y); // Drawing a line from previously calculated to the current calculated point
                    lastX = x;
                    lastY = y;
                }
                break;
            }
            case 5:
            {
                
            }
        }
    }

    

}