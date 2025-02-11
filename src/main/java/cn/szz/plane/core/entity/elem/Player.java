package cn.szz.plane.core.entity.elem;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import cn.szz.plane.config.UIConfig;
import cn.szz.plane.core.Animator;
import cn.szz.plane.core.Mover;
import cn.szz.plane.core.Sounder;
import cn.szz.plane.core.entity.User;
import cn.szz.plane.core.entity.em.ImageEnum;
import cn.szz.plane.core.entity.em.PartAlignEnum;
import cn.szz.plane.core.entity.em.SoundPathEnum;
import cn.szz.plane.core.entity.paint.Coordinate;
import cn.szz.plane.core.entity.paint.CoordinateImage;
import cn.szz.plane.core.entity.paint.CoordinatePart;
import cn.szz.plane.core.entity.paint.Image;
import cn.szz.plane.core.entity.paint.Rect;
import cn.szz.plane.core.entity.record.TimesRecord;
import cn.szz.plane.core.entity.sound.DefaultSound;
import cn.szz.plane.core.entity.sound.Sound;
import cn.szz.plane.ui.event.MouseListenerEvent;

public class Player extends FlyObj implements Animator, Mover, Sounder, MouseListenerEvent {

	private static final Image[][] IMAGE_ARRAY = {
			{ new Image(ImageEnum.PLAYER, 393, 102, 115, 92), new Image(ImageEnum.PLAYER, 126, 107, 117, 93),
					new Image(ImageEnum.PLAYER, 392, 0, 118, 99), new Image(ImageEnum.PLAYER, 136, 0, 130, 104) } };

	private static final Coordinate[][][] BULLET_COORDINATE_ARRAY = { { new Coordinate[] { new Coordinate(57, 0) },
			new Coordinate[] { new Coordinate(31, 8), new Coordinate(86, 8) },
			new Coordinate[] { new Coordinate(46, 0), new Coordinate(72, 0) }, new Coordinate[] {
					new Coordinate(20, 35), new Coordinate(51, 0), new Coordinate(79, 0), new Coordinate(110, 35) } } };

	private static final Coordinate[][][] FIRE_COORDINATE_ARRAY = { { new Coordinate[] { new Coordinate(57, -8) },
			new Coordinate[] { new Coordinate(41, -18), new Coordinate(75, -18) },
			new Coordinate[] { new Coordinate(33, -24), new Coordinate(42, -22), new Coordinate(76, -22),
					new Coordinate(85, -24) },
			new Coordinate[] { new Coordinate(37, -24), new Coordinate(48, -22), new Coordinate(82, -22),
					new Coordinate(93, -24) } } };

	private static final Image[][] FIRE_IMAGE_ARRAY = {
			{ new Image(ImageEnum.PLAYER_FIRE_01, 0, 0, 21, 50), new Image(ImageEnum.PLAYER_FIRE_01, 21, 0, 21, 50) },
			{ new Image(ImageEnum.PLAYER_FIRE_02, 0, 0, 21, 50), new Image(ImageEnum.PLAYER_FIRE_02, 21, 0, 21, 50) },
			{ new Image(ImageEnum.PLAYER_FIRE_03, 0, 0, 21, 50), new Image(ImageEnum.PLAYER_FIRE_03, 21, 0, 21, 50) } };

	private static final Image[] SCORE_IMAGE_ARRAY = { new Image(ImageEnum.SCORE, 2, 0, 66, 86),
			new Image(ImageEnum.SCORE, 68, 0, 60, 86), new Image(ImageEnum.SCORE, 128, 0, 65, 86),
			new Image(ImageEnum.SCORE, 193, 0, 64, 86), new Image(ImageEnum.SCORE, 257, 0, 63, 86),
			new Image(ImageEnum.SCORE, 320, 0, 64, 86), new Image(ImageEnum.SCORE, 384, 0, 64, 86),
			new Image(ImageEnum.SCORE, 448, 0, 64, 86), new Image(ImageEnum.SCORE, 512, 0, 64, 86),
			new Image(ImageEnum.SCORE, 576, 0, 64, 86) };

	private static final Image TROUGH_IMAGE = new Image(ImageEnum.ENEMY, 0, 200, 258, 16);
	private static final Image BLOOD_IMAGE = new Image(ImageEnum.ENEMY, 3, 222, 250, 7);
	private static final Image STAR_IMAGE = new Image(ImageEnum.GAME_UI, 308, 80, 63, 63);

	protected final TimesRecord timesRecord = new TimesRecord(); // 时间记录器
	protected int olife; // 原始生命
	protected int score; // 得分
	protected int star; // 得星
	protected int level; // 等级
	protected int levelTime; // 等级倒计时
	protected User user; // 用户信息
	protected Coordinate[] bulletCoordinates; // 子弹坐标
	protected Coordinate[] fireCoordinates; // 尾喷坐标
	protected CoordinatePart[][] fireParts; // 尾喷图片
	protected int firePartIndex; // 尾喷图片下标
	protected CoordinateImage heartImage; // 心图片
	protected CoordinateImage troughImage; // 血槽图片
	protected CoordinateImage bloodImage; // 血量图片
	protected Set<Sound> soundSet; // 音效

	public Player(User user) {
		this.life = 100;
		this.damage = Integer.MAX_VALUE;
		this.olife = life;
		this.score = 0;
		this.level = 1;
		this.user = user;
		this.firePartIndex = 0;
		this.rect = getCoordinateImage(user.getPlayerImageType(), level);
		this.bulletCoordinates = getBulletCoordinates(user.getPlayerImageType(), level);
		this.fireCoordinates = getFireCoordinates(user.getPlayerImageType(), level);
		this.fireParts = getFireCoordinatePart(getFireCoordinates(), user.getPlayerFireImageType(), level);
		this.heartImage = new CoordinateImage(new Image(ImageEnum.GAME_UI, 374, 21, 54, 54), 10, 16, 16, 16);
		this.troughImage = new CoordinateImage(TROUGH_IMAGE, 26, 18, 104, 12);
		this.bloodImage = new CoordinateImage(BLOOD_IMAGE, 28, 20, 100, 8);
		this.soundSet = new HashSet<>();
	}

	@Override
	public void animation() {
		if (timesRecord.getAnimationTimes() % 80 == 0) {
			this.firePartIndex = timesRecord.getAnimationTimes() / 80 % getFireParts().length;
		}
		if (getLife() != getOlife()) {
			bloodImage.setWidth(new BigDecimal(troughImage.getWidth() - 4)
					.multiply(new BigDecimal(life).divide(new BigDecimal(olife), 10, BigDecimal.ROUND_HALF_DOWN))
					.intValue());
		}
		timesRecord.plusAnimationTimes();
	}

	@Override
	public void move() {
		if (timesRecord.getRefreshTimes() <= 500) {
			if (timesRecord.getMoveTimes(true) % 2 == 0) {
				Rect rect = getRect();
				rect.setY(rect.getY() - 1);
			}
		}
	}

	@Override
	public void draw(Graphics g) {
		timesRecord.plusRefreshTimes();
		getRect().draw(g);
		CoordinatePart[][] fireParts = getFireParts();
		if (fireParts.length > firePartIndex) {
			Arrays.asList(fireParts[firePartIndex]).forEach(firePart -> firePart.draw(g));
		}
		getHeartImage().draw(g);
		getTroughImage().draw(g);
		getBloodImage().draw(g);
		getScoreCoordinateImage(getScore()).forEach(coordinateImage -> coordinateImage.draw(g));
		getStarCoordinateImage(getStar()).forEach(coordinateImage -> coordinateImage.draw(g));

	}

	@Override
	public void sound() {
		Set<Sound> soundCache = soundSet;
		soundSet = new HashSet<>();
		soundCache.forEach(sound -> {
			sound.play();
		});
	}

	@Override
	public void mouseCallback(MouseEvent event) {
		if (timesRecord.getRefreshTimes() >= 500) {
			Rect rect = getRect();
			rect.setX(event.getX() - rect.getWidth() / 2);
			rect.setY(event.getY() - rect.getHeight() / 2);
		}
	}

	public void addLife(int life) {
		this.life = getLife() + life;
	}

	public void addScore(int score) {
		this.score = getScore() + score;
	}

	public void addStar(int star) {
		this.star = getStar() + star;
	}

	@Override
	public void checkHit(FlyObj flyObj) {
		if (!isHit(flyObj)) {
			return;
		}
		subLife(flyObj.getDamage());
		if (flyObj instanceof Star) {
			Star star = (Star) flyObj;
			addStar(star.getValue());
			soundSet.add(new DefaultSound(SoundPathEnum.STAR));
		} else if (flyObj instanceof Level) {
			Level level = (Level) flyObj;
			setLevel(level.getValue());
			soundSet.add(new DefaultSound(SoundPathEnum.LEVEL));
		}
	}

	public void setLevel(int level) {
		User user = getUser();
		Rect rect = getRect();
		this.level = level;
		this.rect = getCoordinateImage(user.getPlayerImageType(), level, new Coordinate(rect.getX(), rect.getY()));
		this.bulletCoordinates = getBulletCoordinates(user.getPlayerImageType(), level);
		this.fireCoordinates = getFireCoordinates(user.getPlayerImageType(), level);
		this.fireParts = getFireCoordinatePart(getFireCoordinates(), user.getPlayerFireImageType(), level);
	}

	public List<PlayerBullet> shoot() {
		if (timesRecord.getShootTimes(true) % 20 == 0) {
			Coordinate[] coordinates = getBulletCoordinates();
			int type = getUser().getPlayerBulletImageType();
			return Stream.of(coordinates).map(coordinate -> new PlayerBullet(this, coordinate, type))
					.collect(Collectors.toList());
		}
		return new ArrayList<>();
	}

	protected CoordinateImage getCoordinateImage(int type, int level) {
		Image image = getImage(type, level);
		return new CoordinateImage(image, getDefaultImageCoordinate(image));
	}

	protected CoordinateImage getCoordinateImage(int type, int level, Coordinate coordinate) {
		Image image = getImage(type, level);
		return new CoordinateImage(image, coordinate);
	}

	protected CoordinatePart[][] getFireCoordinatePart(Coordinate[] fireCoordinates, int type, int level) {
		Image[] fireImages = getFireImages(type);
		return Stream.of(fireImages).map(fireImage -> Stream.of(fireCoordinates)
				.map(fireCoordinate -> new CoordinatePart(fireImage, fireCoordinate, getRect(), PartAlignEnum.BOTTOM_CENTER))
				.toArray(CoordinatePart[]::new)).toArray(CoordinatePart[][]::new);
	}

	protected Image getImage(int type, int level) {
		switch (type) {
		case 1:
			switch (level) {
			case 1:
				return IMAGE_ARRAY[0][0];
			case 2:
				return IMAGE_ARRAY[0][1];
			case 3:
				return IMAGE_ARRAY[0][2];
			case 4:
				return IMAGE_ARRAY[0][3];
			default:
				return getImage(type, 1);
			}
		default:
			return getImage(1, level);
		}
	}

	protected Coordinate getDefaultImageCoordinate(Image image) {
		return new Coordinate((UIConfig.INSTANCE.getWindowWidth() - image.getWidth()) / 2,
				UIConfig.INSTANCE.getWindowHeight());
	}

	protected Image[] getFireImages(int type) {
		switch (type) {
		case 1:
			return FIRE_IMAGE_ARRAY[0];
		case 2:
			return FIRE_IMAGE_ARRAY[1];
		case 3:
			return FIRE_IMAGE_ARRAY[2];
		default:
			return getFireImages(1);
		}
	}

	protected Coordinate[] getFireCoordinates(int type, int level) {
		switch (type) {
		case 1:
			switch (level) {
			case 1:
				return FIRE_COORDINATE_ARRAY[0][0];
			case 2:
				return FIRE_COORDINATE_ARRAY[0][1];
			case 3:
				return FIRE_COORDINATE_ARRAY[0][2];
			case 4:
				return FIRE_COORDINATE_ARRAY[0][3];
			default:
				return getFireCoordinates(type, 1);
			}
		default:
			return getFireCoordinates(1, level);
		}
	}

	protected Coordinate[] getBulletCoordinates(int type, int level) {
		switch (type) {
		case 1:
			switch (level) {
			case 1:
				return BULLET_COORDINATE_ARRAY[0][0];
			case 2:
				return BULLET_COORDINATE_ARRAY[0][1];
			case 3:
				return BULLET_COORDINATE_ARRAY[0][2];
			case 4:
				return BULLET_COORDINATE_ARRAY[0][3];
			default:
				return getBulletCoordinates(type, 1);
			}
		default:
			return getBulletCoordinates(1, level);
		}
	}

	protected List<CoordinateImage> getScoreCoordinateImage(int score) {
		List<CoordinateImage> coordinateImageList = new ArrayList<>();
		List<Integer> numList = Stream.of(String.valueOf(score).split("")).map(Integer::parseInt)
				.collect(Collectors.toList());
		Collections.reverse(numList);
		for (int i = 1; i <= numList.size(); i++) {
			int num = numList.get(i - 1);
			coordinateImageList.add(new CoordinateImage(SCORE_IMAGE_ARRAY[num],
					UIConfig.INSTANCE.getWindowWidth() - 80 - 30 * i, 10, 30, 40));
		}
		return coordinateImageList;
	}

	protected List<CoordinateImage> getStarCoordinateImage(int star) {
		List<CoordinateImage> coordinateImageList = new ArrayList<>();
		List<Integer> numList = Stream.of(String.valueOf(star).split("")).map(Integer::parseInt)
				.collect(Collectors.toList());
		Collections.reverse(numList);
		for (int i = 1; i <= numList.size(); i++) {
			int num = numList.get(i - 1);
			coordinateImageList.add(new CoordinateImage(SCORE_IMAGE_ARRAY[num],
					UIConfig.INSTANCE.getWindowWidth() - 10 - 15 * i, 70, 15, 20));
		}
		coordinateImageList.add(new CoordinateImage(STAR_IMAGE,
				UIConfig.INSTANCE.getWindowWidth() - 35 - 15 * numList.size(), 70, 20, 20));
		return coordinateImageList;
	}

	public int getOlife() {
		return olife;
	}

	public int getScore() {
		return score;
	}

	public int getStar() {
		return star;
	}

	public User getUser() {
		return user;
	}

	public Coordinate[] getBulletCoordinates() {
		return bulletCoordinates;
	}

	public Coordinate[] getFireCoordinates() {
		return fireCoordinates;
	}

	public CoordinatePart[][] getFireParts() {
		return fireParts;
	}

	public CoordinateImage getHeartImage() {
		return heartImage;
	}

	public CoordinateImage getTroughImage() {
		return troughImage;
	}

	public CoordinateImage getBloodImage() {
		return bloodImage;
	}
}
