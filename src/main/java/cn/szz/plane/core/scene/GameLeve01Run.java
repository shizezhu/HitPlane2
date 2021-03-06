package cn.szz.plane.core.scene;

import java.util.List;

import cn.szz.plane.config.UIConfig;
import cn.szz.plane.core.entity.elem.Enemy;
import cn.szz.plane.core.entity.elem.GameBg;
import cn.szz.plane.core.entity.elem.Player;
import cn.szz.plane.core.entity.em.ImageEnum;
import cn.szz.plane.core.entity.em.SceneNameEnum;
import cn.szz.plane.core.entity.em.SoundPathEnum;
import cn.szz.plane.core.entity.paint.Image;
import cn.szz.plane.core.entity.sound.DefaultSound;
import cn.szz.plane.ui.Window;
import cn.szz.plane.utils.RandomUtils;

public class GameLeve01Run extends GameRun {

	public GameLeve01Run(List<Player> playerList) {
		super(SceneNameEnum.GAME_LEVEL_01);
		this.bgImage = new GameBg(new Image(ImageEnum.GAME_BG_01, 0, 0, UIConfig.INSTANCE.getWindowWidth(),
				UIConfig.INSTANCE.getWindowHeight()));
		this.bgSound = new DefaultSound(SoundPathEnum.GAME_LEVEL_01_BG, true);
		this.playerList.addAll(playerList);
	}

	@Override
	protected void produceEnemy(int refreshTimes) {
		if (refreshTimes > 0 && refreshTimes % 7000 == 0) {
			enemyList.add(new Enemy(0, RandomUtils.nextInt(1, 6), RandomUtils.nextInt(2, 5)));
		}
		if (refreshTimes <= 2000) {
			if (refreshTimes % 500 == 0) {
				enemyList.add(new Enemy(1, RandomUtils.nextInt(1, 15) == 1 ? 1 : 0, 0));
			}
			if (refreshTimes % 800 == 0) {
				enemyList.add(new Enemy(2, RandomUtils.nextInt(1, 10) == 1 ? 2 : 0, 0));
			}
			if (refreshTimes % 1100 == 0) {
				enemyList.add(new Enemy(3, RandomUtils.nextInt(1, 5) == 1 ? 3 : 0, 0));
			}
		} else if (refreshTimes <= 4000) {
			if (refreshTimes % 500 == 0) {
				enemyList.add(new Enemy(2, RandomUtils.nextInt(1, 15) == 1 ? 1 : 0, 0));
			}
			if (refreshTimes % 800 == 0) {
				enemyList.add(new Enemy(3, RandomUtils.nextInt(1, 10) == 1 ? 2 : 0, 0));
			}
			if (refreshTimes % 1100 == 0) {
				enemyList.add(new Enemy(4, RandomUtils.nextInt(1, 5) == 1 ? 3 : 0, 0));
			}
		} else if (refreshTimes <= 6000) {
			if (refreshTimes % 500 == 0) {
				enemyList.add(new Enemy(3, RandomUtils.nextInt(1, 15) == 1 ? 1 : 0, 0));
			}
			if (refreshTimes % 800 == 0) {
				enemyList.add(new Enemy(4, RandomUtils.nextInt(1, 10) == 1 ? 2 : 0, 0));
			}
			if (refreshTimes % 1100 == 0) {
				enemyList.add(new Enemy(5, RandomUtils.nextInt(1, 5) == 1 ? 3 : 0, 0));
			}
		} else if (refreshTimes <= 8000) {
			if (refreshTimes % 500 == 0) {
				enemyList.add(new Enemy(4, RandomUtils.nextInt(1, 15) == 1 ? 1 : 0, 0));
			}
			if (refreshTimes % 800 == 0) {
				enemyList.add(new Enemy(5, RandomUtils.nextInt(1, 10) == 1 ? 2 : 0, 0));
			}
			if (refreshTimes % 1100 == 0) {
				enemyList.add(new Enemy(6, RandomUtils.nextInt(1, 5) == 1 ? 3 : 0, 0));
			}
		} else if (refreshTimes <= 10000) {
			if (refreshTimes % 500 == 0) {
				enemyList.add(new Enemy(5, RandomUtils.nextInt(1, 15) == 1 ? 1 : 0, 0));
			}
			if (refreshTimes % 800 == 0) {
				enemyList.add(new Enemy(6, RandomUtils.nextInt(1, 10) == 1 ? 2 : 0, 0));
			}
			if (refreshTimes % 1100 == 0) {
				enemyList.add(new Enemy(7, RandomUtils.nextInt(1, 5) == 1 ? 3 : 0, 0));
			}
		} else if (refreshTimes <= 12000) {
			if (refreshTimes % 500 == 0) {
				enemyList.add(new Enemy(6, RandomUtils.nextInt(1, 15) == 1 ? 1 : 0, 0));
			}
			if (refreshTimes % 800 == 0) {
				enemyList.add(new Enemy(7, RandomUtils.nextInt(1, 10) == 1 ? 2 : 0, 0));
			}
			if (refreshTimes % 1100 == 0) {
				enemyList.add(new Enemy(8, RandomUtils.nextInt(1, 5) == 1 ? 3 : 0, 0));
			}
		} else if (refreshTimes <= 14000) {
			if (refreshTimes % 500 == 0) {
				enemyList.add(new Enemy(7, RandomUtils.nextInt(1, 15) == 1 ? 1 : 0, 0));
			}
			if (refreshTimes % 800 == 0) {
				enemyList.add(new Enemy(8, RandomUtils.nextInt(1, 10) == 1 ? 2 : 0, 0));
			}
			if (refreshTimes % 1100 == 0) {
				enemyList.add(new Enemy(9, RandomUtils.nextInt(1, 5) == 1 ? 3 : 0, 0));
			}
		} else {
			if (enemyList.isEmpty() && blastList.isEmpty()) {
				Window.INSTANCE.showScene(new GameLeve02Run(playerList), true);
			}
		}
	}
}
