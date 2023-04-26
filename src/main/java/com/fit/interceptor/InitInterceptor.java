package com.fit.interceptor;

import com.fit.plugin.websocketInstantMsg.ChatServer;
import com.fit.plugin.websocketOnline.OnlineChatServer;
import com.fit.util.FileUtil;
import com.fit.util.Const;
import com.fit.util.DbFH;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import java.net.UnknownHostException;
import java.sql.SQLException;

/**
 * @className: InitInterceptor
 * @description:
 * @author: Aim
 * @date: 2023/4/25
 **/
@Slf4j
@Component
public class InitInterceptor {

    @PostConstruct
    public void init() throws ServletException {
        log.info("-------------- 启动初始化的服务 --------------");
        this.startWebsocket();
        this.reductionDbBackupQuartzState();
        log.info("---------------- 初始化完成 -----------------");
    }

    /**
     * 启动Websocket服务
     */
    public void startWebsocket() {
        OnlineChatServer online;
        ChatServer chatServer;
        try {
            String strWEBSOCKET = FileUtil.readTxtFile(Const.WEBSOCKET);
            if (null != strWEBSOCKET && !"".equals(strWEBSOCKET)) {
                String strIW[] = strWEBSOCKET.split(",fh,");
                if (strIW.length == 5) {
                    //启动即时聊天服务
                    chatServer = new ChatServer(Integer.parseInt(strIW[1]));
                    chatServer.start();
                    //启动在线管理服务
                    online = new OnlineChatServer(Integer.parseInt(strIW[3]));
                    online.start();
                }
            }
            // System.out.println( "websocket服务器启动,端口" + s.getPort() );
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    /**
     * web容器重启时，所有定时备份状态关闭
     */
    public void reductionDbBackupQuartzState() {
        try {
            DbFH.executeUpdateFH("update DB_TIMINGBACKUP set STATUS = '2'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
