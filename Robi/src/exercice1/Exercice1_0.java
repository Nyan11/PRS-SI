package exercice1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

import graphicLayer.GRect;
import graphicLayer.GSpace;

public class Exercice1_0 {
	GSpace space = new GSpace("Exercice 1", new Dimension(200, 150));
	GRect robi = new GRect();
	float speed = 100; // pixel per sec
	int updateTime = 60;
	float pixelMov = (float)speed * (float)updateTime / 1000;

	public Exercice1_0() {
		Point transPoint;
		int roundPixel = 0;
		int roundDep = 0;
		int goal = 0;
		float truePixel = 0;
		float deplacement = 0;
		
		space.addElement(robi);
		space.open();
		
		/* Déplacement de robi jusqu'au bord droit */
		roundPixel = 0;
		roundDep = 0;
		truePixel = 0;
		deplacement = 0;
		while(robi.getPosition().x + robi.getWidth() < space.getWidth()) {
			truePixel += pixelMov;
			deplacement = truePixel - roundPixel;
			roundDep = Math.round(deplacement);
			roundPixel += roundDep;
			transPoint = new Point(roundDep, 0);
			robi.translate(transPoint);
			try {
			Thread.sleep(updateTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		/* Déplacement de robi jusqu'au bord bas */
		roundPixel = 0;
		roundDep = 0;
		truePixel = 0;
		deplacement = 0;
		while(robi.getPosition().y + robi.getHeight() < space.getHeight()) {
			truePixel += pixelMov;
			deplacement = truePixel - roundPixel;
			roundDep = Math.round(deplacement);
			roundPixel += roundDep;
			transPoint = new Point(0, roundDep);
			robi.translate(transPoint);
			try {
			Thread.sleep(updateTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		/* Déplacement de robi jusqu'au bord gauche */
		roundPixel = 0;
		roundDep = 0;
		truePixel = 0;
		deplacement = 0;
		while(robi.getPosition().x > 0) {
			truePixel += pixelMov;
			deplacement = truePixel - roundPixel;
			roundDep = Math.round(deplacement);
			roundPixel += roundDep;
			transPoint = new Point(-roundDep, 0);
			robi.translate(transPoint);
			try {
			Thread.sleep(updateTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		/* Déplacement de robi jusqu'au bord haut */
		roundPixel = 0;
		roundDep = 0;
		truePixel = 0;
		deplacement = 0;
		while(robi.getPosition().y > 0) {
			truePixel += pixelMov;
			deplacement = truePixel - roundPixel;
			roundDep = Math.round(deplacement);
			roundPixel += roundDep;
			transPoint = new Point(0, -roundDep);
			robi.translate(transPoint);
			try {
			Thread.sleep(updateTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		/* Changement de couleur avec une couleur aléatoire */
		robi.setColor(new Color((int) (Math.random() * 0x1000000)));
	}

	public static void main(String[] args) {
		new Exercice1_0();
	}

}