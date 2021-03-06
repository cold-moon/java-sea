package com.iee.mongodb.opc.utgard.test;

import org.openscada.opc.lib.common.ConnectionInformation;
import org.openscada.opc.lib.da.*;

import java.util.concurrent.Executors;

/**
 * @ClassName ItemPublishSubscribe
 * @Description Item的发布订阅查询
 * @Author longxiaonan@163.com
 * @Date 2018/9/7 0007 11:25
 */
public class ItemPublishSubscribe {
    private static final int PERIOD = 100;

    private static final int SLEEP = 2000;

    public static void main(String[] args) throws Exception {

        ConnectionInformation ci = new ConnectionInformation();
        ci.setHost("10.1.5.123");
        ci.setDomain("");
        ci.setUser("freud");
        ci.setPassword("password");
        ci.setClsid("F8582CF2-88FB-11D0-B850-00C0F0104305");

        Server server = new Server(ci,
                Executors.newSingleThreadScheduledExecutor());

        server.connect();

        AccessBase access = new Async20Access(server, PERIOD, false);

        access.addItem("Random.Real5", new DataCallback() {

            private int count;

            public void changed(Item item, ItemState itemstate) {
                System.out.println("[" + (++count) + "],ItemName:["
                        + item.getId() + "],value:" + itemstate.getValue());
            }
        });

        access.bind();
        Thread.sleep(SLEEP);
        access.unbind();
        server.dispose();
    }
}
