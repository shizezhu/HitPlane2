package cn.szz.plane.core.scene;

import cn.szz.plane.config.UIConfig;
import cn.szz.plane.core.entity.elem.img.GameBg;
import cn.szz.plane.core.entity.em.ImageEnum;
import cn.szz.plane.core.entity.em.SoundPathEnum;
import cn.szz.plane.core.entity.paint.Image;
import cn.szz.plane.core.entity.sound.DefaultBgSound;
import cn.szz.plane.core.entity.sound.DefaultSound;
import cn.szz.plane.factory.EnemyFactory;
import cn.szz.plane.factory.PlayerFactory;

public class GameLeve02Run extends GameRun {
    
    

    public GameLeve02Run() {
        super(new GameBg(new Image(ImageEnum.GAME_BG_02, 0, 0, UIConfig.INSTANCE.getWindowWidth(), UIConfig.INSTANCE.getWindowHeight())),
                new DefaultBgSound(SoundPathEnum.GAME_LEVEL_02_BG));
        playerList.add(PlayerFactory.produce(getUser(), 2));
    }

    @Override
    protected void produceEnemy() {
        if (timesRecord.getTimes() % 5000 == 0) {
            difficulty++;
            soundSet.add(new DefaultSound(SoundPathEnum.UPGRADE));
        }
        switch (difficulty) {
        case 1:
            if (timesRecord.getTimes() % 200 == 0) {
                enemyList.add(EnemyFactory.produce(2, 1));
            }
            if (timesRecord.getTimes() % 300 == 0) {
                enemyList.add(EnemyFactory.produce(3, 1));
            }
            break;
        case 2:
            if (timesRecord.getTimes() % 100 == 0) {
                enemyList.add(EnemyFactory.produce(2, 1));
            }
            if (timesRecord.getTimes() % 200 == 0) {
                enemyList.add(EnemyFactory.produce(3, 1));
            }
            break;
        case 3:
            if (timesRecord.getTimes() % 300 == 0) {
                enemyList.add(EnemyFactory.produce(2, 1));
            }
            if (timesRecord.getTimes() % 400 == 0) {
                enemyList.add(EnemyFactory.produce(3, 1));
            }
            if (timesRecord.getTimes() % 500 == 0) {
                enemyList.add(EnemyFactory.produce(4, 1));
            }
            break;
        case 4:
            if (timesRecord.getTimes() % 200 == 0) {
                enemyList.add(EnemyFactory.produce(2, 1));
            }
            if (timesRecord.getTimes() % 300 == 0) {
                enemyList.add(EnemyFactory.produce(3, 1));
            }
            if (timesRecord.getTimes() % 400 == 0) {
                enemyList.add(EnemyFactory.produce(4, 1));
            }
            break;
        case 5:
            if (timesRecord.getTimes() % 100 == 0) {
                enemyList.add(EnemyFactory.produce(2, 1));
            }
            if (timesRecord.getTimes() % 200 == 0) {
                enemyList.add(EnemyFactory.produce(3, 1));
            }
            if (timesRecord.getTimes() % 300 == 0) {
                enemyList.add(EnemyFactory.produce(4, 1));
            }
            break;
        case 6:
            if (timesRecord.getTimes() % 300 == 0) {
                enemyList.add(EnemyFactory.produce(3, 1));
            }
            if (timesRecord.getTimes() % 400 == 0) {
                enemyList.add(EnemyFactory.produce(4, 1));
            }
            if (timesRecord.getTimes() % 500 == 0) {
                enemyList.add(EnemyFactory.produce(5, 1));
            }
            break;
        case 7:
            if (timesRecord.getTimes() % 200 == 0) {
                enemyList.add(EnemyFactory.produce(3, 1));
            }
            if (timesRecord.getTimes() % 300 == 0) {
                enemyList.add(EnemyFactory.produce(4, 1));
            }
            if (timesRecord.getTimes() % 400 == 0) {
                enemyList.add(EnemyFactory.produce(5, 1));
            }
            break;
        case 8:
            if (timesRecord.getTimes() % 100 == 0) {
                enemyList.add(EnemyFactory.produce(3, 1));
            }
            if (timesRecord.getTimes() % 200 == 0) {
                enemyList.add(EnemyFactory.produce(4, 1));
            }
            if (timesRecord.getTimes() % 300 == 0) {
                enemyList.add(EnemyFactory.produce(5, 1));
            }
        }
    }
}
