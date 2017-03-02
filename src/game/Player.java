package game;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.ListIterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import game.Animation.AnimationMode;

import audio.SFX;

public class Player extends Entity {
	public KeyboardInput keyboard;
	public MouseInput mouse;
	public Viewport viewport;

	public Point2D.Double pos;
	public double size;
	public double speed;
	public Game game;
	public Circle hitbox;

	public double maxHp;
	public double hp;
	public double immunityTime;
	public double maxImmunityTime;
	public Animation anim;

	public Weapon[] weapon;

	public SFX sword_swing, pew_pew;
	
	private static final double sqrt2 = Math.sqrt(2);


	public Player(Game game, KeyboardInput keyboard, MouseInput mouse) {
		super();
		this.game = game;
		this.mouse = mouse;
		// this.viewport = viewport;
		this.keyboard = keyboard;
		pos = new Point2D.Double(2.0, 2.0);
		size = 0.14; // used to be 0.28
		speed = 1.0;
		weapon = new Weapon[2];
		weapon[0] = new Sword(game, this);
		weapon[1] = new Gun(game, this);
		hitbox = new Circle(size, pos);
		maxHp = 10000;
		hp = maxHp;
		maxImmunityTime = 0.7;
		immunityTime = 0;
		anim = new Animation(SpritesheetEnum.PLAYER, 0, 0, 0.1, Animation.AnimationMode.LOOP);

		sword_swing = new SFX(50, "/Music/SFX_Swoosh.mp3");
		pew_pew = new SFX(50, "/Music/SFX_Hit_2.wav");
	}

	@Override
	public void update(double delta, Game game) {
		super.update(delta, game);
		keyboard.poll();
		mouse.poll();

		viewport.update(delta);

		if (keyboard.keyDown(KeyEvent.VK_UP))
			viewport.ppu += 20.0 * delta;
		if (keyboard.keyDown(KeyEvent.VK_DOWN))
			viewport.ppu -= 20.0 * delta;

		if (hp > 0) {
			Point2D.Double oldPos = new Point2D.Double(pos.x, pos.y);
			
			double dx = 0;
			double dy = 0;
			// adjust speed so it's the same in all directions
			if (keyboard.keyDown(KeyEvent.VK_W)) {
				dy += Math.max(pos.y - speed * delta, size) - pos.y;
			}
			if (keyboard.keyDown(KeyEvent.VK_A)) {
				dx += Math.max(pos.x - speed * delta, size) - pos.x;
				anim.setSet(1);
			}
			if (keyboard.keyDown(KeyEvent.VK_S)) {
				dy += Math.min(pos.y + speed * delta, game.roomH - size) - pos.y;
			}
			if (keyboard.keyDown(KeyEvent.VK_D)) {
				dx += Math.min(pos.x + speed * delta, game.roomW - size) - pos.x;
				anim.setSet(0);
			}
			
			//if moving diagonally, adjust speed
			if((dy != 0) && (dx != 0)){
				dy = dy/sqrt2;
				dx = dx/sqrt2;
			}
			
			pos.x += dx;
			pos.y += dy;			
			if (!game.getLevel().validPos(hitbox)){
				pos.x = oldPos.x;
				if(!game.getLevel().validPos(hitbox)){
					pos.x = oldPos.x + dx;
					pos.y = oldPos.y;
					if(!game.getLevel().validPos(hitbox)){
						pos.x = oldPos.x;
					}
				}
			}
			
			/*// adjust speed so it's the same in all directions
			if (keyboard.keyDown(KeyEvent.VK_W)) {
				pos.y = Math.max(pos.y - speed * delta, size);
			}
			if (keyboard.keyDown(KeyEvent.VK_A)) {
				pos.x = Math.max(pos.x - speed * delta, size);
				anim.setSet(1);
			}
			if (keyboard.keyDown(KeyEvent.VK_S)) {
				pos.y = Math.min(pos.y + speed * delta, game.roomH - size);
			}
			if (keyboard.keyDown(KeyEvent.VK_D)) {
				pos.x = Math.min(pos.x + speed * delta, game.roomW - size);
				anim.setSet(0);
			}*/
			
			
			if (!game.keyboard.keyDown(KeyEvent.VK_W) && !game.keyboard.keyDown(KeyEvent.VK_A)
					&& !game.keyboard.keyDown(KeyEvent.VK_S) && !game.keyboard.keyDown(KeyEvent.VK_D)) {
				anim.setSet(2);
			}

			if (mouse.isPressed(0) && hp > 0) {
				weapon[0].use(viewport.toGameCoord(mouse.getPos()));

				sword_swing.play();
			}
			if (mouse.isPressed(1) && hp > 0) {
				weapon[1].use(viewport.toGameCoord(mouse.getPos()));

				pew_pew.play();
			}

			int l = weapon.length;
			for (int i = 0; i < l; i++)
				weapon[i].update(delta);

			if (immunityTime > 0)
				immunityTime -= delta;
			ListIterator<Monster> mit = game.monsters.listIterator(0);
			while (mit.hasNext()) {
				Monster m = mit.next();
				if (hitbox.intersects(m.hitbox)) {
					if (immunityTime <= 0) {
						hp -= 10;
						immunityTime = maxImmunityTime;
					}
				}
			}
		} else {
			SFX scream = new SFX(50, "/Music/SFX_Man_Scream_1.wav");
			scream.play();
		}
	}

	@Override
	public void draw(Graphics2D g, Viewport vp) {

		vp.drawCircleSprite(pos, size, anim, g);
	}

	@Override
	public Point2D.Double getPos() {
		return pos;
	}

	@Override
	public boolean disposable() {
		return false;
	}
}
