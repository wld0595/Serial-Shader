package com.mulshader.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class MyMulshaderGame extends ApplicationAdapter{
	SpriteBatch batch;
	Texture img;
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		batch.draw(img,0,0);
		batch.end();

	}

}
/*
public class MyMulshaderGame extends ApplicationAdapter {

	SpriteBatch batch;
	Texture img;
	String vertexShader,vertexShader1;
	String fragmentShader,fragmentShader1;
	ShaderProgram shaderProgram,shaderProgram1;

	FrameBuffer frameBufferA;
	FrameBuffer frameBufferB;

	float time=0.0f;

	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");

		frameBufferA = new FrameBuffer(Pixmap.Format.RGB888, 960, 540, false);
		frameBufferB = new FrameBuffer(Pixmap.Format.RGB888, 960, 540, false);

		vertexShader = Gdx.files.internal("vertex.glsl").readString();
		fragmentShader = Gdx.files.internal("fragment.glsl").readString();
		shaderProgram = new ShaderProgram(vertexShader,fragmentShader);

		vertexShader1 = Gdx.files.internal("vertexNew.glsl").readString();
		fragmentShader1 = Gdx.files.internal("fragmentNew.glsl").readString();
		shaderProgram1 = new ShaderProgram(vertexShader1,fragmentShader1);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		time += Gdx.graphics.getDeltaTime();

		frameBufferA.begin();
		batch.begin();
		//batch.setShader(null);
		batch.draw(img,0,0);
		frameBufferA.end();

		frameBufferB.begin();
		shaderProgram.begin();
		batch.setShader(shaderProgram);
		batch.draw(frameBufferA.getColorBufferTexture(),0,0);
		frameBufferB.end();
		shaderProgram.end();

		//shaderProgram1.begin();
		batch.setShader(shaderProgram1);
		shaderProgram1.setUniformf("time",time);
		batch.draw(frameBufferB.getColorBufferTexture(),0,0,img.getWidth(),img.getHeight(),0,0,img.getWidth(),img.getHeight(),false,true);
		//shaderProgram1.end();

		batch.end();


		/*frameBufferA.begin();
		batch.begin();
		batch.setShader(shaderProgram1);
		shaderProgram1.setUniformf("time",time);
		batch.draw(img,0,0);
		frameBufferA.end();

		//batch.draw(frameBufferA.getColorBufferTexture(),0,0);

		frameBufferB.begin();
		batch.setShader(shaderProgram);
		batch.draw(frameBufferA.getColorBufferTexture(),0,0);
		frameBufferB.end();

		batch.draw(frameBufferB.getColorBufferTexture(),0,0);

		batch.end();*/
	//}
//}

/*package com.mulshader.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

public class MyMulshaderGame extends ApplicationAdapter  {
	private SpriteBatch blurBatch;
	private ShaderProgram shader,newshader;            // 自定义着色器
	private FrameBuffer frameBufferA;        // 纹理缓存A，实际上就是用来存放上一次纹理缓存的拷贝
	private FrameBuffer frameBufferB;        // 纹理缓存B
	private float radius = 0.0F;                // 初始的模糊系数
	private int fbo_size = 512;    // 纹理缓存大小
	private float  blur = 5.0F;        // 模糊系数
	private float xOffset = 0.8F;    // x轴偏移，水平渲染
	private float yOffset = 0.8F;    // y轴偏移，垂直渲染
	private Texture texture;
	private float time=0.0f;

	String vertexShader,NewVertexShader;
	String fragmentShader,NewFragmentShader;

	@Override
	public void create(){
		blurBatch = new SpriteBatch();
		texture=new Texture("badlogic.jpg");

		vertexShader = Gdx.files.internal("vertexBlur.glsl").readString();
		fragmentShader = Gdx.files.internal("fragmentBlur.glsl").readString();
		shader = new ShaderProgram(vertexShader,fragmentShader);

		NewVertexShader = Gdx.files.internal("vertexBlurOld.glsl").readString();
		NewFragmentShader = Gdx.files.internal("fragmentBlurOld.glsl").readString();
		newshader = new ShaderProgram(NewVertexShader,NewFragmentShader);

		frameBufferA = new FrameBuffer(Pixmap.Format.RGB888, 960, 540, false);
		frameBufferB = new FrameBuffer(Pixmap.Format.RGB888, 960, 540, false);
	}

	@Override
	public void render(){
		Gdx.gl.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if(blur<0){
			blur=0.0f;
		}
		else{
			blur -= 0.01f;
		}

		frameBufferA.begin();
		blurBatch.begin();
		//shader.begin();
		blurBatch.draw(texture,0,0);
		frameBufferA.end();

		frameBufferB.begin();
		blurBatch.setShader(shader);
		//===//
		shader.begin();
		shader.setUniformf("dir", xOffset, 0f);
		shader.setUniformf("radius",blur );
		shader.setUniformf("resolution", fbo_size);
		blurBatch.draw(frameBufferA.getColorBufferTexture(), 0,0);
		frameBufferB.end();
		shader.end();

		blurBatch.setShader(newshader);
		newshader.begin();
		shader.setUniformf("resolution", fbo_size);
		newshader.setUniformf("dir", 0f, yOffset);
		newshader.setUniformf("radius",blur );
		blurBatch.draw(frameBufferB.getColorBufferTexture(), 0,0,texture.getWidth(),texture.getHeight(),0,0,texture.getWidth(),texture.getHeight(),false,true);
		blurBatch.end();
		newshader.end();

	}

	@Override
	public void dispose() {
		blurBatch.dispose();
		shader.dispose();
		texture.dispose();
		newshader.dispose();
	}
}*/





