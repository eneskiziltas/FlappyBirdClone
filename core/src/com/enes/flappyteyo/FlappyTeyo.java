package com.enes.flappyteyo;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;

import java.util.Random;

public class FlappyTeyo extends ApplicationAdapter {

	SpriteBatch batch;
	Texture background;
	Texture bird;
	Texture ufo;
	Texture ufo2;
	Texture ufo3;
	float birdX = 0;
	float birdY = 0;
	int gameState = 0;
	float velocity = 0;
	float gravity = 0.8f;
	float enemyVelocity = 10;
	Random random;
	Circle birdCircle;
	int score = 0;
	int scoredEnemy = 0;
	BitmapFont font;
	BitmapFont font2;


	int numberOfEnemies = 4;
	float[] enemyX = new float[numberOfEnemies];
	float distance = 0;
	float[] enemyOfSet = new float[numberOfEnemies];
	float[] enemyOfSet2 = new float[numberOfEnemies];
	float[] enemyOfset3 = new float[numberOfEnemies];

	Circle[] enemyCircles;
	Circle[] enemyCircles2;
	Circle[] enemyCircles3;

	ShapeRenderer shapeRenderer;


	@Override
	public void create() {
		batch = new SpriteBatch();
		background = new Texture("background.png");
		bird = new Texture("bird.png");
		birdX = Gdx.graphics.getWidth() / 3;
		birdY = Gdx.graphics.getHeight() / 2;

		shapeRenderer = new ShapeRenderer();

		birdCircle = new Circle();
		enemyCircles = new Circle[numberOfEnemies];
		enemyCircles2 = new Circle[numberOfEnemies];
		enemyCircles3 = new Circle[numberOfEnemies];

		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(4);

		font2 = new BitmapFont();
		font2.setColor(Color.WHITE);
		font2.getData().setScale(6);

		ufo = new Texture("ufo.png");
		ufo2 = new Texture("ufo.png");
		ufo3 = new Texture("ufo.png");

		distance = Gdx.graphics.getWidth() / 2;

		random = new Random();


		for (int i = 0; i < numberOfEnemies; i++) {

			enemyOfSet[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
			enemyOfSet2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
			enemyOfset3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);

			enemyX[i] = Gdx.graphics.getWidth() - ufo.getWidth() / 2 + i * distance;

			enemyCircles[i] = new Circle();
			enemyCircles2[i] = new Circle();
			enemyCircles3[i] = new Circle();

		}

	}

	@Override
	public void render() {

		batch.begin();
		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		if (gameState == 1) {

			if(enemyX[scoredEnemy] < Gdx.graphics.getWidth() / 3){
				score++;

				if(scoredEnemy < numberOfEnemies-1){
					scoredEnemy++;
				}else {
					scoredEnemy = 0;
				}
			}


			if (Gdx.input.justTouched()) {

				velocity = -17;
				System.out.println(Gdx.graphics.getHeight() - 200);


			}


			for (int i = 0; i < numberOfEnemies; i++) {

				if (enemyX[i] < Gdx.graphics.getWidth() / 15) {
					enemyX[i] = enemyX[i] + numberOfEnemies * distance;

					enemyOfSet[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					enemyOfSet2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					enemyOfset3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
				} else {
					enemyX[i] = enemyX[i] - enemyVelocity;
				}


				batch.draw(ufo, enemyX[i], Gdx.graphics.getHeight() / 2 + enemyOfSet[i], Gdx.graphics.getWidth() / 18, Gdx.graphics.getHeight() / 14);
				batch.draw(ufo2, enemyX[i], Gdx.graphics.getHeight() / 2 + enemyOfSet2[i], Gdx.graphics.getWidth() / 18, Gdx.graphics.getHeight() / 14);
				batch.draw(ufo3, enemyX[i], Gdx.graphics.getHeight() / 2 + enemyOfset3[i], Gdx.graphics.getWidth() / 18, Gdx.graphics.getHeight() / 14);

				enemyCircles[i] = new Circle(enemyX[i] + Gdx.graphics.getHeight() / 30, Gdx.graphics.getHeight() / 2 + enemyOfSet[i] + Gdx.graphics.getHeight() / 28, Gdx.graphics.getWidth() / 18 / 2);
				enemyCircles2[i] = new Circle(enemyX[i] + Gdx.graphics.getHeight() / 30, Gdx.graphics.getHeight() / 2 + enemyOfSet2[i] + Gdx.graphics.getHeight() / 28, Gdx.graphics.getWidth() / 18 / 2);
				enemyCircles3[i] = new Circle(enemyX[i] + Gdx.graphics.getHeight() / 30, Gdx.graphics.getHeight() / 2 + enemyOfset3[i] + Gdx.graphics.getHeight() / 28, Gdx.graphics.getWidth() / 18 / 2);

			}

			if (birdY > 0 ) {
				velocity = velocity + gravity;
				birdY = birdY - velocity;
			}else {
				gameState = 2;
			}

		} else if (gameState == 0) {
			if (Gdx.input.justTouched()) {
				gameState = 1;
			}
		} else if (gameState == 2) {

			font2.draw(batch,"Game Over! Tap To Play Again",100,Gdx.graphics.getHeight()/2);

			if (Gdx.input.justTouched()) {
				gameState = 1;

				birdY = Gdx.graphics.getHeight() / 2;

				for (int i = 0; i < numberOfEnemies; i++) {

					enemyOfSet[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					enemyOfSet2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					enemyOfset3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);

					enemyX[i] = Gdx.graphics.getWidth() - ufo.getWidth() / 2 + i * distance;

					enemyCircles[i] = new Circle();
					enemyCircles2[i] = new Circle();
					enemyCircles3[i] = new Circle();

				}

				velocity = 0;
				scoredEnemy = 0;
				score = 0;

			}

		}


			batch.draw(bird, birdX, birdY, Gdx.graphics.getWidth() / 18, Gdx.graphics.getHeight() / 14);

			font.draw(batch,String.valueOf(score),100,200);

			batch.end();

			birdCircle.set(birdX + Gdx.graphics.getWidth() / 30, birdY + Gdx.graphics.getHeight() / 30, Gdx.graphics.getWidth() / 30);

			//shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
			//shapeRenderer.setColor(Color.BLACK);
			///shapeRenderer.circle(birdCircle.x,birdCircle.y,birdCircle.radius);

			for (int i = 0; i < numberOfEnemies; i++) {
				//	shapeRenderer.circle(enemyX[i] + Gdx.graphics.getHeight()/30, Gdx.graphics.getHeight()/2 + enemyOfSet[i]+ Gdx.graphics.getHeight()/28,Gdx.graphics.getWidth()/18 / 2);
				//	shapeRenderer.circle(enemyX[i] + Gdx.graphics.getHeight()/30, Gdx.graphics.getHeight()/2 + enemyOfSet2[i]+ Gdx.graphics.getHeight()/28,Gdx.graphics.getWidth()/18 / 2);
				//	shapeRenderer.circle(enemyX[i] + Gdx.graphics.getHeight()/30, Gdx.graphics.getHeight()/2 + enemyOfset3[i]+ Gdx.graphics.getHeight()/28,Gdx.graphics.getWidth()/18 / 2);

				if (Intersector.overlaps(birdCircle, enemyCircles[i]) || Intersector.overlaps(birdCircle, enemyCircles2[i]) || Intersector.overlaps(birdCircle, enemyCircles3[i])) {
					gameState = 2;
				}

			}

			shapeRenderer.end();
		}

		@Override
		public void dispose () {

		}
	}
