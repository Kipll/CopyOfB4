import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;
import java.util.Iterator;

public class Monster extends Entity{
	public Point2D.Double pos;
	public double size;
	public Circle hitbox;
	public double hp;
	public double maxHp;
	public boolean dead;
	public Game game;
	
	public Projectile lastHitBy;
	public TreeMap<Projectile, Double> immunities;
	
	private Random random;
	
	public Monster(Game game){
		super();
		random = new Random();
		this.game = game;
		pos = new Point2D.Double(random.nextDouble()*game.roomW, random.nextDouble()*game.roomH);
		size = 0.2;
		hp = 100;
		maxHp = hp;
		dead = false;
		hitbox = new Circle(size, pos);
		lastHitBy = null;
		immunities = new TreeMap<Projectile, Double>();
		
		addBehaviour(new LinearHome(0.18, game.player.pos));
	}
	
	public void hit(Projectile p){
		if(!immunities.containsKey(p)){
			hp -= p.damage;
			immunities.put(p, new Double(p.immunityTime));
		}
		lastHitBy = p;
	}
	
	public boolean disposable(){
		return dead;
	}
	
	public Point2D.Double getPos(){
		return pos;
	}
	
	public void update(double delta, Game game){
		if(hp<=0){
			dead = true; 
			game.score++;
			try{
				game.spawnEntity(new Fairy(7.0, 0.1, 10, 0.2, 2.8, new Point2D.Double(pos.x, pos.y), lastHitBy.player, game));
			}
			catch(NullPointerException e){}
		}
		
		super.update(delta, game);
		
		Iterator<Map.Entry<Projectile, Double>> pIt = immunities.entrySet().iterator();
		LinkedList<Projectile> imtorem = new LinkedList<Projectile>();
		while(pIt.hasNext()){
			Map.Entry<Projectile, Double> e = pIt.next();
			e.setValue(e.getValue() - delta);
			if(e.getValue()<=0)imtorem.add(e.getKey());
		}
		Iterator<Projectile> imtoremit = imtorem.iterator();
		while(imtoremit.hasNext())immunities.remove(imtoremit.next());
	}
	
	public void draw(Graphics2D g, Viewport viewport){
		double hpBarH = 0.08;
	
		viewport.drawCircle(pos, size, (Color.WHITE), g);
		Point coord = viewport.toScreenCoord(new Point2D.Double(pos.x-size, pos.y-size));
		
		g.setColor(Color.BLACK);
		g.fillRect(coord.x, coord.y-viewport.scaleToScreen(hpBarH), viewport.scaleToScreen(2*size), viewport.scaleToScreen(hpBarH));
		g.setColor(Color.RED);
		g.fillRect(coord.x, coord.y-viewport.scaleToScreen(hpBarH), viewport.scaleToScreen(2*size*hp/maxHp), viewport.scaleToScreen(hpBarH));
}
}
