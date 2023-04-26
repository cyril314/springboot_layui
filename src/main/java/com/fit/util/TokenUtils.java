package com.fit.util;

import java.util.Map;

import com.fit.entity.epsm.ActorVo;

public class TokenUtils {

	/**
	 * 移除无效的token
	 */
	static public void removeInvalidToken() {
		for (Map.Entry<String, Object> tokenmap : Const.TOKENMAP.entrySet()) {
			ActorVo userInfo = (ActorVo) tokenmap.getValue();
			if (Long.parseLong(userInfo.getTokenExpire()) < Long.parseLong(DateUtil.getCurTimeStamp())) {// 如果过期了的话，就移除掉
				Const.TOKENMAP.remove(tokenmap.getKey());
			}
		}
	}

	/**
	 * 保存token
	 * 
	 * @param token
	 *            actorInfo
	 */
	static public void save(String token, ActorVo actorInfo) {
		Const.TOKENMAP.put(token, actorInfo);
	}

	/**
	 * 验证token是否有效
	 * 
	 * @param token
	 * @return 无效的话返回 null对象
	 */
	static public ActorVo validation(String token) {
		ActorVo actorVo = null;
		for (Map.Entry<String, Object> tokenmap : Const.TOKENMAP.entrySet()) {
			String saveToken = tokenmap.getKey();
			if (saveToken.equals(token)) {// 如果找到对应的token,必须得判断是失效
				ActorVo actorInfo = (ActorVo) tokenmap.getValue();
				if (Long.parseLong(actorInfo.getTokenExpire()) >= Long.parseLong(DateUtil.getCurTimeStamp())) {// 如果过期了的话，就移除掉
					actorVo = actorInfo;
					break;
				}
			}
		}
		return actorVo;
	}
}
