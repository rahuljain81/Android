package com.rahuljain81.flappybird;

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
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	Texture[] birds;
	Texture gameover;
	//ShapeRenderer shapeRenderer;
	int score = 0;
	int scoringTube = 0;
	BitmapFont font;
	int chances=5;

	Texture topTube, bottomTube;
	int width, height;
	int flapstate = 0;
	String logtag = "flappybird";
	float birdY = 0;
	float velocity = 0;
	Circle birdCircle;

	int gameState = 0;
	float gravity = 2;
	float gap = 800;
	float maxTubeOffset = 0;
	Random randomGenerator;
	float tubeVelocity = 4;
	int numberOfTubes = 4;
	float[] tubeX = new float[numberOfTubes];
	float[] tubeOffset = new float[numberOfTubes];

	float distanceBetweenTubes;

	Rectangle[] topTubeRectangles;
	Rectangle[] bottomTubeRectangles;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("bg.png");
		birds = new Texture[2];
		birds[0] = new Texture("bird.png");
		birds[1] = new Texture("bird2.png");
		topTube = new Texture("toptube.png");
		bottomTube = new Texture("bottomtube.png");
		gameover = new Texture("gameover.png");
		//shapeRenderer = new ShapeRenderer();
		birdCircle = new Circle();


		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		maxTubeOffset = height / 2 - gap / 2 - 100;

		birdY = height / 2 - birds[0].getHeight() / 2;

		randomGenerator = new Random();

		distanceBetweenTubes = width * 3 / 4;
		topTubeRectangles = new Rectangle[numberOfTubes];
		bottomTubeRectangles = new Rectangle[numberOfTubes];

		startGame();

		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(10);
	}


	public void startGame() {

		birdY = height / 2 - birds[0].getHeight() / 2;

		for (int i = 0; i < numberOfTubes; i++) {
			//Height of tube
			tubeOffset[i] = (randomGenerator.nextFloat() - 0.5f) * (height - gap - 200);
			tubeX[i] = width / 2 - topTube.getWidth() / 2 +  width + i * distanceBetweenTubes;

			topTubeRectangles[i] = new Rectangle();
			bottomTubeRectangles[i] = new Rectangle();
		}
		score = 0;
		scoringTube = 0;
		velocity = 0;
		chances = 5;
	}
	@Override
	public void render () {
		batch.begin();
		batch.draw(background, 0, 0, width, height);

		if (gameState == 1) {

			Gdx.app.log(logtag, "Score = " +  String.valueOf(score));
			if (tubeX[scoringTube] < width) {
				score++;

				if (scoringTube < numberOfTubes - 1)
					scoringTube++;
				else
					scoringTube = 0;
			}

			if (Gdx.input.justTouched()) {
				//Move Up
				velocity = -30;
			}

			for (int i = 0; i < numberOfTubes; i++) {
				//Out of screen on left side
				if (tubeX[i] < - topTube.getWidth()) {
					tubeX[i] += numberOfTubes * distanceBetweenTubes;
					tubeOffset[i] = (randomGenerator.nextFloat() - 0.5f) * (height - gap - 200);
				} else {
					tubeX[i] -= tubeVelocity;


				}
				batch.draw(topTube, tubeX[i], height / 2 + gap / 2 + tubeOffset[i]);
				batch.draw(bottomTube, tubeX[i], height / 2 - gap / 2 - bottomTube.getHeight() + tubeOffset[i]);

				topTubeRectangles[i] = new Rectangle(tubeX[i], height / 2 + gap / 2 + tubeOffset[i], topTube.getWidth(), topTube.getHeight());
				bottomTubeRectangles[i] = new Rectangle(tubeX[i],height / 2 - gap / 2 - bottomTube.getHeight() + tubeOffset[i] , bottomTube.getWidth(), bottomTube.getHeight());
			}
			//Make sure bird doesn't go out of screen
			if (birdY > 0) {
				velocity += gravity;
				birdY -= velocity;
			} else
				gameState = 2;

		} else if (gameState == 0) {
			if (Gdx.input.justTouched()) {
				gameState = 1;
			}
		} else if (gameState == 2) {
			batch.draw(gameover, width / 2 - gameover.getWidth() / 2 , height / 2 - gameover.getHeight() / 2);
			if (Gdx.input.justTouched()) {
				gameState = 1;
				startGame();
			}

		}

		if (flapstate == 0)
			flapstate = 1;
		else
			flapstate = 0;



		batch.draw(birds[flapstate], width / 2 - birds[flapstate].getWidth() / 2, birdY);

		font.draw(batch, String.valueOf(score), 100, 200);

		birdCircle.set(Gdx.graphics.getWidth() / 2, birdY + birds[flapstate].getHeight() / 2, birds[flapstate].getWidth() / 2);



		//shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		//shapeRenderer.setColor(Color.RED);
		//shapeRenderer.circle(birdCircle.x, birdCircle.y, birdCircle.radius);
		for (int i = 0; i < numberOfTubes; i++) {
			//shapeRenderer.rect(tubeX[i], height / 2 + gap / 2 + tubeOffset[i], topTube.getWidth(), topTube.getHeight());
			//shapeRenderer.rect(tubeX[i],height / 2 - gap / 2 - bottomTube.getHeight() + tubeOffset[i] , bottomTube.getWidth(), bottomTube.getHeight());

			if (Intersector.overlaps(birdCircle, topTubeRectangles[i]) || Intersector.overlaps(birdCircle, bottomTubeRectangles[i])) {
				gameState = 2;//GameOver
			}
		}
		//shapeRenderer.end();

		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		background.dispose();
		birds[0].dispose();
		birds[1].dispose();
	}
}
