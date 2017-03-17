package game;
import javax.swing.JFrame;
import javax.swing.JPanel;

import levels.Level;
import levels.TestLevel;

import network.ClientListener;
import network.Server;
import network.MainServer;
import weapons.*;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.event.KeyEvent;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.List;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Iterator;
import java.util.NoSuchElementException;

import java.util.concurrent.ConcurrentLinkedQueue;


public class Game{
	
	
	public boolean isRunning;
	public KeyboardInput keyboard;
	public MouseInput mouse;
	
	public double roomW;//in game units
	public double roomH;//in game units
	public ConcurrentLinkedQueue<Player> players;
	public LinkedList<Monster> monsters;
	public LinkedList<Entity> entities;
	public LinkedList<Monster> monstersWaiting;
	public LinkedList<Entity> entitiesWaiting;
	
	public Server server;
	public static int port = 4444;
	
	//private Viewport viewport;
	
	Animation arena;
	
	private Level level = new TestLevel(roomW, roomH);
	
	public Random rand;
	
	public double maxSpawnTime;
	public double minSpawnTime;
	public double timeUntilSpawn;
	
	public int score = 0;
	
	public void spawnEntity(Entity e){
		entitiesWaiting.add(e);
	}
	public void spawnMonster(Monster e){
		monstersWaiting.add(e);
	}
	
	//private levels.Level level = new levels.TestLevel(this);
	//java is dumb //lol
	
	public Game(KeyboardInput keyboard, MouseInput mouse){
		isRunning = true;
		
		this.keyboard = keyboard;
		this.mouse = mouse;
		
		players = new ConcurrentLinkedQueue<Player>();
		monsters = new LinkedList<Monster>();
		entities = new LinkedList<Entity>();
		monstersWaiting = new LinkedList<Monster>();
		entitiesWaiting = new LinkedList<Entity>();
		
		roomW = 5.0;
		roomH = 3.0;
		
		
		/*Player p = new Player(this, keyboard, mouse);
		p.viewport = new Viewport(this, p);
		players.add(p);
		
		
		
		server = new Server();
		
		
		p.viewport.server = server;
		
		server.listen(port, p);*/
		
		new MainServer(port, this).start();
		
		arena = new Animation(SpritesheetEnum.ARENA,0,0,1,Animation.AnimationMode.LOOP);
		
		rand = new Random();
		maxSpawnTime = 4.3;
		minSpawnTime = 1.1;
	}
	
	public void update(double delta){
		
		monsters.addAll(monstersWaiting);
		monstersWaiting = new LinkedList<Monster>();
		entities.addAll(entitiesWaiting);
		entitiesWaiting = new LinkedList<Entity>();
		
		Iterator<Player> pit = players.iterator();
		while(pit.hasNext()){Player p = pit.next(); p.update(delta, this); if(p.disposable()) pit.remove();}
		ListIterator<Monster> mit = monsters.listIterator(0);
		while(mit.hasNext()){Monster m = mit.next(); m.update(delta, this); if(m.disposable()) mit.remove();}
		ListIterator<Entity> eit = entities.listIterator(0);
		while(eit.hasNext()){Entity e = eit.next(); e.update(delta, this); if(e.disposable()) eit.remove();}
		
		
		if(!players.isEmpty()){
			if(timeUntilSpawn <= 0)
			{
				timeUntilSpawn = minSpawnTime + rand.nextDouble()*(maxSpawnTime - minSpawnTime);
				spawnMonster(new DefaultMonster(this, new Point2D.Double(rand.nextDouble()*roomW, rand.nextDouble()*roomH)));
			}
			else timeUntilSpawn -= delta;
		}
	}
	
	public Level getLevel(){
		return level;
	}
	
	public void render(Graphics2D g){
		Iterator<Player> pit = players.iterator();
		while(pit.hasNext()){drawOnViewport(g, pit.next().viewport);}
	}
	
	public void drawOnViewport(Graphics2D g, Viewport viewport){
		int[] input = new int[ClientListener.inputSize];
		for(int i=0;i<ClientListener.inputSize;i++)
			input[i] = -1;
		viewport.server.addToQueue(input);
	
	
		viewport.drawRectAbsolute(new Point(0,0), viewport.screenW, viewport.screenH, Color.BLACK, g);
		
		
		//g.setColor(Color.GREEN);
		//Point roomCoord = viewport.toScreenCoord(new Point2D.Double(0,0));
		//g.fillRect(roomCoord.x, roomCoord.y, (int)(roomW*viewport.ppu), (int)(roomH*viewport.ppu));
		
		viewport.drawSprite(new Rectangle.Double(0,0,roomW,roomH), arena, g);
		
		level.draw(g, viewport);
		
		Iterator<Player> pit = players.iterator();
		while(pit.hasNext())pit.next().draw(g, viewport);
		ListIterator<Monster> mit = monsters.listIterator(0);
		while(mit.hasNext())mit.next().draw(g, viewport);
		ListIterator<Entity> eit = entities.listIterator(0);
		while(eit.hasNext())eit.next().draw(g, viewport);
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
		g.drawString(Integer.toString(score), 30, 30);
		
		viewport.drawRectAbsolute(new Point(30,60),100,10, Color.BLACK, g);
		viewport.drawRectAbsolute(new Point(30,60),(int)(100 * viewport.p.hp / viewport.p.maxHp),10, Color.RED, g);
	}
}
