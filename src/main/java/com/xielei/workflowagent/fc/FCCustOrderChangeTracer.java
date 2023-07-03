package com.xielei.workflowagent.fc;

import java.sql.SQLException;

import com.alibaba.fastjson.JSONObject;
import com.xielei.workflowagent.util.DBUtil;
import com.xielei.workflowagent.util.ThreadPoolUtil;

/**
 * FC记录CustOrder当前数据
 *
 * @author xielei
 * @date 2023/6/25 10:18
 */
public final class FCCustOrderChangeTracer {

    private FCCustOrderChangeTracer() {
        // empty
    }

    public static void trace(String fcName, Object orderData, int flag, Long custOrderId, Long orderItemId) {
        ThreadPoolUtil.apply(new CustOrderChangeTask(custOrderId, orderItemId, fcName, JSONObject.toJSONStringWithDateFormat(orderData, "yyyy-MM-dd HH:mm:ss"), flag));
    }

    private static final class CustOrderChangeTask implements Runnable {

        public CustOrderChangeTask(Long custOrderId, Long orderItemId, String fcName, String orderData, int flag) {
            this.custOrderId = custOrderId;
            this.orderItemId = orderItemId;
            this.fcName = fcName;
            this.orderData = orderData;
            this.flag = flag;
        }

        private final Long custOrderId;

        private final Long orderItemId;

        private final String fcName;

        private final String orderData;

        private final int flag;

        @Override
        public void run() {
            try {
                DBUtil.DB.execute("insert into fc_compare values(?, ?, ?, ?, ?)", custOrderId, orderItemId, fcName, orderData, flag);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }



}
