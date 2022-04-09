package cn.szz.plane.core.entity.em;

import cn.szz.plane.utils.CheckUtils;

public enum SceneNameEnum {

	READY("READY"),
	GAME_READY("GAME_READY"),
	GAME_WARN("GAME_WARN"),
	GAME_LEVEL_01("GAME_LEVEL_01"),
	GAME_LEVEL_01_BOSS("GAME_LEVEL_01_BOSS"),
	GAME_LEVEL_02("GAME_LEVEL_02"),
	GAME_LEVEL_02_BOSS("GAME_LEVEL_02_BOSS"),
	GAME_LEVEL_03("GAME_LEVEL_03"),
	GAME_LEVEL_03_BOSS("GAME_LEVEL_03_BOSS"),
	GAME_LEVEL_04("GAME_LEVEL_04"),
	GAME_LEVEL_04_BOSS("GAME_LEVEL_04_BOSS"),
	GAME_LEVEL_05("GAME_LEVEL_05"),
	GAME_LEVEL_05_BOSS("GAME_LEVEL_05_BOSS"),
	PAUSE("PAUSE"),
	OVER("OVER");
	
	private String value;

	private SceneNameEnum(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public static SceneNameEnum getByValue(String value) {
		for (SceneNameEnum v : SceneNameEnum.values()) {
			if (CheckUtils.isEquals(v.getValue(), value)) {
				return v;
			}
		}
		return null;
	}
}
