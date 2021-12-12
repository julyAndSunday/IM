//package com.github.chat.service;
//
//import com.google.inject.Inject;
//import com.it.im.conn.ConnectorConn;
//import com.it.im.conn.ConnectorConnContext;
//
//import java.io.IOException;
//
//public class TransferService {
//
//    private ConnectorConnContext connContext;
//
//    @Inject
//    public TransferService(ConnectorConnContext connContext) {
//        this.connContext = connContext;
//    }
//
//    public void doChat(IM.Msg msg) throws IOException {
//        ConnectorConn conn = connContext.getConnByUserId(msg.getDestId());
//
//        if (conn != null) {
//            conn.getCtx().writeAndFlush(msg);
//        } else {
//           // doOffline(msg);
//        }
//    }
//}