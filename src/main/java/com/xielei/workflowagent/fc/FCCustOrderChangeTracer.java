package com.xielei.workflowagent.fc;

import java.sql.SQLException;

import com.alibaba.fastjson.JSONObject;
import com.xielei.workflowagent.util.DBUtil;

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

    static {
        try {
            DBUtil.DB.execute("delete from fc_compare");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void trace(String fcName, Object obj, int flag, Long custOrderId, Long orderItemId) {
        try {
            // todo 后续考虑使用连接池方式改造
            DBUtil.DB.execute("INSERT INTO fc_compare VALUES(?, ?, ?, ?, ?)", custOrderId, orderItemId, fcName, JSONObject.toJSONStringWithDateFormat(obj, "yyyy-MM-dd HH:mm:ss"), flag);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
